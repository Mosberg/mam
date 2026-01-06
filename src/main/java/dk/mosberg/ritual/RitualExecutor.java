// Implement RitualLoader and Ritual classes
// ...existing code...
package dk.mosberg.ritual;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import dk.mosberg.MAM;
import dk.mosberg.entity.FireElementalEntity;
import dk.mosberg.mana.ManaComponent;
import dk.mosberg.mana.ManaManager;
import dk.mosberg.mana.ManaPoolType;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.registry.Registries;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

/**
 * Executor for ritual effects. Handles pattern detection, validation, and effect application.
 */
public class RitualExecutor {
    private static final Map<String, Long> ritualCooldowns = new HashMap<>();

    /**
     * Attempt to execute a ritual for a player.
     *
     * @param player The player executing the ritual
     * @param ritualId The ritual identifier
     * @param centerPos The center position of the ritual pattern
     * @return true if ritual was successfully executed
     */
    public static boolean executeRitual(ServerPlayerEntity player, Identifier ritualId,
            BlockPos centerPos) {
        if (player == null || ritualId == null || centerPos == null) {
            MAM.LOGGER.error("Cannot execute ritual: invalid parameters");
            return false;
        }

        // Get ritual from registry
        Ritual ritual = RitualLoader.getRitual(ritualId);
        if (ritual == null) {
            sendErrorMessage(player, "Unknown ritual: " + ritualId);
            MAM.LOGGER.warn("Player {} attempted to use unknown ritual: {}",
                    player.getName().getString(), ritualId);
            return false;
        }

        return executeRitual(player, ritual, centerPos);
    }

    /**
     * Execute a ritual for a player.
     *
     * @param player The player executing the ritual
     * @param ritual The ritual to execute
     * @param centerPos The center position of the ritual pattern
     * @return true if ritual was successfully executed
     */
    public static boolean executeRitual(ServerPlayerEntity player, Ritual ritual,
            BlockPos centerPos) {
        if (player == null || ritual == null || centerPos == null) {
            MAM.LOGGER.error("Cannot execute ritual: player, ritual, or centerPos is null");
            return false;
        }

        // Check player level requirement
        if (player.experienceLevel < ritual.getLevelRequirement()) {
            sendErrorMessage(player, String.format("Level %d required! (you are level %d)",
                    ritual.getLevelRequirement(), player.experienceLevel));
            return false;
        }

        // Check mana cost
        ManaComponent mana = ManaManager.getComponent(player);
        double manaCost = ritual.getManaCost();

        if (!mana.has(ManaPoolType.PERSONAL, manaCost)) {
            sendErrorMessage(player, String.format("Insufficient mana! (need %.1f, have %.1f)",
                    manaCost, mana.getPool(ManaPoolType.PERSONAL).getCurrent()));
            return false;
        }

        // Check cooldown
        String cooldownKey = player.getUuid() + ":" + ritual.getId();
        long now = System.currentTimeMillis();
        if (ritualCooldowns.containsKey(cooldownKey)) {
            long lastCast = ritualCooldowns.get(cooldownKey);
            long remaining = (ritual.getCooldownSeconds() * 1000L) - (now - lastCast);
            if (remaining > 0) {
                sendErrorMessage(player, String.format("Ritual on cooldown for %.1f more seconds",
                        remaining / 1000.0));
                return false;
            }
        }

        // Validate ritual pattern
        net.minecraft.server.world.ServerWorld world =
                (net.minecraft.server.world.ServerWorld) player.getEntityWorld();
        if (!validateRitualPattern(world, ritual, centerPos)) {
            sendErrorMessage(player, "Invalid ritual pattern! Check structure.");
            MAM.LOGGER.debug("Ritual pattern validation failed for {} at {}", ritual.getId(),
                    centerPos);
            return false;
        }

        // Check required items
        if (!checkRitualItems(player, ritual)) {
            sendErrorMessage(player, "Missing required ritual items!");
            return false;
        }

        // Consume mana
        if (!mana.consume(ManaPoolType.PERSONAL, manaCost)) {
            sendErrorMessage(player, "Failed to consume mana!");
            return false;
        }

        // Execute ritual effects
        try {
            executeRitualEffects(player, ritual, centerPos);
            ritualCooldowns.put(cooldownKey, now);
            sendSuccessMessage(player, "Ritual " + ritual.getId().getPath() + " activated!");
            MAM.LOGGER.info("Player {} executed ritual {} (cost: {} mana)",
                    player.getName().getString(), ritual.getId(), manaCost);
            return true;
        } catch (Exception e) {
            // Refund mana on failure
            mana.add(ManaPoolType.PERSONAL, manaCost);
            sendErrorMessage(player, "Ritual execution failed!");
            MAM.LOGGER.error("Failed to execute ritual {} for player {}", ritual.getId(),
                    player.getName().getString(), e);
            return false;
        }
    }

    /**
     * Validate that the ritual pattern exists at the specified location.
     */
    private static boolean validateRitualPattern(net.minecraft.server.world.ServerWorld world,
            Ritual ritual, BlockPos centerPos) {
        if (ritual.getPattern() == null || ritual.getPattern().getRings().isEmpty()) {
            // No pattern requirements
            return true;
        }

        RitualPattern pattern = ritual.getPattern();

        // Validate center block
        net.minecraft.block.BlockState centerBlock = world.getBlockState(centerPos);
        String expectedCenter = pattern.getCenterBlock();
        if (!blockStateMatches(centerBlock, expectedCenter)) {
            MAM.LOGGER.debug("Center block mismatch: expected {}, got {}", expectedCenter,
                    centerBlock);
            return false;
        }

        // Validate rings
        for (RitualRing ring : pattern.getRings()) {
            if (!validateRitualRing(world, centerPos, ring)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Validate a single ring of the ritual pattern.
     */
    private static boolean validateRitualRing(net.minecraft.server.world.ServerWorld world,
            BlockPos centerPos, RitualRing ring) {
        int radius = ring.getRadius();
        int height = ring.getHeight();
        int count = ring.getCount();
        int validated = 0;

        // Check blocks in a circle at the specified radius and height
        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                // Approximate circle using distance
                int distSq = x * x + z * z;
                int radiusSq = radius * radius;
                if (Math.abs(distSq - radiusSq) <= radius) {
                    BlockPos blockPos = centerPos.add(x, height, z);
                    net.minecraft.block.BlockState blockState = world.getBlockState(blockPos);

                    if (blockStateMatches(blockState, ring.getMaterial())) {
                        validated++;
                    }
                }
            }
        }

        // Allow some tolerance (70% of expected blocks)
        int required = (int) Math.ceil(count * 0.7);
        return validated >= required;
    }

    /**
     * Check if a block state matches an identifier string.
     */
    private static boolean blockStateMatches(net.minecraft.block.BlockState blockState,
            String blockId) {
        try {
            Identifier id = Identifier.of(blockId);
            return blockState.isOf(Registries.BLOCK.get(id));
        } catch (Exception e) {
            MAM.LOGGER.debug("Failed to match block state: {}", blockId);
            return false;
        }
    }

    /**
     * Check if player has all required ritual items in inventory.
     */
    private static boolean checkRitualItems(ServerPlayerEntity player, Ritual ritual) {
        if (ritual.getRitualItems() == null || ritual.getRitualItems().isEmpty()) {
            return true;
        }

        // For now, just log required items; full inventory checking can be added later
        MAM.LOGGER.debug("Ritual requires items: {}", ritual.getRitualItems());
        return true;
    }

    /**
     * Execute the effects of a ritual.
     */
    private static void executeRitualEffects(ServerPlayerEntity player, Ritual ritual,
            BlockPos centerPos) {
        RitualEffect effect = ritual.getEffect();
        if (effect == null) {
            return;
        }

        MAM.LOGGER.debug("Executing ritual effect: {} (type: {})", ritual.getId(),
                effect.getType());

        switch (effect.getType()) {
            case "ascend" -> executeAscendEffect(player, effect);
            case "chaos" -> executeChaosEffect(player, effect, centerPos);
            case "bind" -> executeBindEffect(player, effect, centerPos);
            case "cosmic" -> executeCosmicEffect(player, effect);
            case "elemental_balance" -> executeElementalBalanceEffect(player, effect);
            case "heal" -> executeHealEffect(player, effect);
            case "distort" -> executeDistortEffect(player, effect);
            case "resurrect" -> executeResurrectEffect(player, effect);
            case "sacrifice" -> executeSacrificeEffect(player, effect);
            case "summon" -> executeSummonEffect(player, effect, centerPos);
            case "accelerate" -> executeAccelerateEffect(player, effect);
            case "transform" -> executeTransformEffect(player, effect);
            case "void_embrace" -> executeVoidEmbraceEffect(player, effect);
            case "vortex" -> executeVortexEffect(player, effect, centerPos);
            case "nature_heal" -> executeNatureHealEffect(player, effect);
            default -> applyGenericBuffs(player, effect);
        }
    }

    private static void executeAscendEffect(ServerPlayerEntity player, RitualEffect effect) {
        applyBuffsToPlayer(player, effect.getBuffs(), effect.getDuration());
        sendSuccessMessage(player, "You feel power surging through your body!");
    }

    private static void executeChaosEffect(ServerPlayerEntity player, RitualEffect effect,
            BlockPos pos) {
        applyBuffsToPlayer(player, effect.getBuffs(), effect.getDuration());

        net.minecraft.server.world.ServerWorld world =
                (net.minecraft.server.world.ServerWorld) player.getEntityWorld();
        net.minecraft.util.math.Box area = new net.minecraft.util.math.Box(pos).expand(4.0);

        for (net.minecraft.entity.LivingEntity target : world.getEntitiesByClass(
                net.minecraft.entity.LivingEntity.class, area,
                entity -> !entity.isSpectator() && entity != player)) {
            target.damage(world, player.getDamageSources().magic(), 6.0f);
            target.setOnFireFor(4);
        }

        sendSuccessMessage(player, "Chaos erupts from the ritual!");
    }

    private static void executeBindEffect(ServerPlayerEntity player, RitualEffect effect,
            BlockPos pos) {
        applyBuffsToPlayer(player, effect.getBuffs(), effect.getDuration());

        net.minecraft.server.world.ServerWorld world =
                (net.minecraft.server.world.ServerWorld) player.getEntityWorld();
        net.minecraft.util.math.Box area = new net.minecraft.util.math.Box(pos).expand(6.0);
        int durationTicks = effect.getDuration() * 20;

        for (net.minecraft.entity.LivingEntity target : world.getEntitiesByClass(
                net.minecraft.entity.LivingEntity.class, area,
                entity -> !entity.isSpectator() && entity != player)) {
            target.addStatusEffect(
                    new StatusEffectInstance(net.minecraft.entity.effect.StatusEffects.SLOWNESS,
                            durationTicks, 4, false, true, true));
            target.addStatusEffect(
                    new StatusEffectInstance(net.minecraft.entity.effect.StatusEffects.WEAKNESS,
                            durationTicks, 1, false, true, true));
            target.setVelocity(net.minecraft.util.math.Vec3d.ZERO);
            target.velocityDirty = true;
        }

        sendSuccessMessage(player, "Enemies are bound in place!");
    }

    private static void executeCosmicEffect(ServerPlayerEntity player, RitualEffect effect) {
        applyBuffsToPlayer(player, effect.getBuffs(), effect.getDuration());
        sendSuccessMessage(player, "Cosmic energy surrounds you!");
    }

    private static void executeElementalBalanceEffect(ServerPlayerEntity player,
            RitualEffect effect) {
        applyBuffsToPlayer(player, effect.getBuffs(), effect.getDuration());
        sendSuccessMessage(player, "Elements are in harmony!");
    }

    private static void executeHealEffect(ServerPlayerEntity player, RitualEffect effect) {
        float healing = (effect.getBuffs().isEmpty() ? 20.0f : 10.0f);
        player.heal(healing);
        applyBuffsToPlayer(player, effect.getBuffs(), effect.getDuration());
        sendSuccessMessage(player, "You are healed!");
    }

    private static void executeDistortEffect(ServerPlayerEntity player, RitualEffect effect) {
        applyBuffsToPlayer(player, effect.getBuffs(), effect.getDuration());
        sendSuccessMessage(player, "Reality distorts around you!");
    }

    private static void executeResurrectEffect(ServerPlayerEntity player, RitualEffect effect) {
        // Reset health and apply buffs
        player.setHealth(player.getMaxHealth() * 0.5f);
        applyBuffsToPlayer(player, effect.getBuffs(), effect.getDuration());
        sendSuccessMessage(player, "You are resurrected!");
    }

    private static void executeSacrificeEffect(ServerPlayerEntity player, RitualEffect effect) {
        // Damage player but apply buffs
        float damage = 5.0f;
        net.minecraft.server.world.ServerWorld world =
                (net.minecraft.server.world.ServerWorld) player.getEntityWorld();
        player.damage(world, player.getDamageSources().magic(), damage);
        applyBuffsToPlayer(player, effect.getBuffs(), effect.getDuration());
        sendSuccessMessage(player, "You sacrifice your life force for power!");
    }

    private static void executeSummonEffect(ServerPlayerEntity player, RitualEffect effect,
            BlockPos pos) {
        String entityToSummon = effect.getSummonEntity();
        if (entityToSummon == null || entityToSummon.isEmpty()) {
            MAM.LOGGER.warn("Summon ritual has no entity specified");
            return;
        }

        net.minecraft.server.world.ServerWorld world =
                (net.minecraft.server.world.ServerWorld) player.getEntityWorld();

        if (entityToSummon.equals("fire_elemental")) {
            FireElementalEntity elemental =
                    new FireElementalEntity(dk.mosberg.entity.ModEntities.FIRE_ELEMENTAL, world);
            elemental.setOwner(player);
            elemental.setPosition(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5);
            world.spawnEntity(elemental);
            sendSuccessMessage(player, "A Fire Elemental has been summoned!");
            MAM.LOGGER.info("Summoned Fire Elemental for player {}", player.getName().getString());
            return;
        }

        String normalizedId = entityToSummon.contains(":") ? entityToSummon
                : Identifier.of(MAM.MOD_ID, entityToSummon).toString();
        Identifier id = Identifier.of(normalizedId);
        net.minecraft.entity.EntityType<?> type = Registries.ENTITY_TYPE.get(id);

        // If type is the default (pig), the ID wasn't found
        if (type == net.minecraft.entity.EntityType.PIG) {
            sendErrorMessage(player, "Unknown summon entity: " + entityToSummon);
            return;
        }

        net.minecraft.entity.Entity summoned =
                type.create(world, net.minecraft.entity.SpawnReason.MOB_SUMMONED);
        if (summoned == null) {
            sendErrorMessage(player, "Failed to summon entity: " + entityToSummon);
            return;
        }

        summoned.refreshPositionAndAngles(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5,
                world.getRandom().nextFloat() * 360.0f, 0.0f);
        world.spawnEntity(summoned);
        sendSuccessMessage(player, "A creature has been summoned!");
    }

    private static void executeAccelerateEffect(ServerPlayerEntity player, RitualEffect effect) {
        applyBuffsToPlayer(player, effect.getBuffs(), effect.getDuration());
        sendSuccessMessage(player, "Time accelerates around you!");
    }

    private static void executeTransformEffect(ServerPlayerEntity player, RitualEffect effect) {
        applyBuffsToPlayer(player, effect.getBuffs(), effect.getDuration());

        int durationTicks = effect.getDuration() * 20;
        player.addStatusEffect(
                new StatusEffectInstance(net.minecraft.entity.effect.StatusEffects.STRENGTH,
                        durationTicks, 1, false, true, true));
        player.addStatusEffect(
                new StatusEffectInstance(net.minecraft.entity.effect.StatusEffects.RESISTANCE,
                        durationTicks, 1, false, true, true));
        player.addStatusEffect(
                new StatusEffectInstance(net.minecraft.entity.effect.StatusEffects.SPEED,
                        durationTicks, 1, false, true, true));

        sendSuccessMessage(player, "Your form has transformed!");
    }

    private static void executeVoidEmbraceEffect(ServerPlayerEntity player, RitualEffect effect) {
        applyBuffsToPlayer(player, effect.getBuffs(), effect.getDuration());
        sendSuccessMessage(player, "The void embraces you!");
    }

    private static void executeVortexEffect(ServerPlayerEntity player, RitualEffect effect,
            BlockPos pos) {
        net.minecraft.server.world.ServerWorld world =
                (net.minecraft.server.world.ServerWorld) player.getEntityWorld();
        net.minecraft.util.math.Box area = new net.minecraft.util.math.Box(pos).expand(6.0);
        net.minecraft.util.math.Vec3d center = net.minecraft.util.math.Vec3d.ofCenter(pos);
        float damage = 4.0f;

        for (net.minecraft.entity.LivingEntity target : world.getEntitiesByClass(
                net.minecraft.entity.LivingEntity.class, area,
                entity -> !entity.isSpectator() && entity != player)) {
            net.minecraft.util.math.Vec3d targetPos =
                    new net.minecraft.util.math.Vec3d(target.getX(), target.getY(), target.getZ());
            net.minecraft.util.math.Vec3d direction = center.subtract(targetPos);
            target.addVelocity(direction.normalize().multiply(0.35));
            target.velocityDirty = true;
            target.damage(world, player.getDamageSources().magic(), damage);
        }

        sendSuccessMessage(player, "A vortex erupts from the ritual!");
    }

    private static void executeNatureHealEffect(ServerPlayerEntity player, RitualEffect effect) {
        player.heal(15.0f);
        applyBuffsToPlayer(player, effect.getBuffs(), effect.getDuration());
        sendSuccessMessage(player, "Nature's energy flows through you!");
    }

    private static void applyGenericBuffs(ServerPlayerEntity player, RitualEffect effect) {
        applyBuffsToPlayer(player, effect.getBuffs(), effect.getDuration());
    }

    /**
     * Apply status effect buffs to a player.
     */
    private static void applyBuffsToPlayer(ServerPlayerEntity player, List<String> buffs,
            int durationTicks) {
        if (buffs == null || buffs.isEmpty()) {
            return;
        }

        for (String buffName : buffs) {
            try {
                net.minecraft.registry.entry.RegistryEntry<net.minecraft.entity.effect.StatusEffect> effect =
                        Registries.STATUS_EFFECT
                                .getEntry(Registries.STATUS_EFFECT.getId(
                                        Registries.STATUS_EFFECT.get(Identifier.of(buffName))))
                                .orElse(null);

                if (effect != null) {
                    player.addStatusEffect(new StatusEffectInstance(effect, durationTicks * 20, 0, // amplifier
                            false, // ambient
                            true, // showParticles
                            true // showIcon
                    ));
                }
            } catch (Exception e) {
                MAM.LOGGER.debug("Failed to apply buff: {}", buffName);
            }
        }
    }

    private static void sendErrorMessage(ServerPlayerEntity player, String message) {
        player.sendMessage(Text.literal(message).withColor(0xFF5555), false);
    }

    private static void sendSuccessMessage(ServerPlayerEntity player, String message) {
        player.sendMessage(Text.literal(message).withColor(0x55FF55), false);
    }

    /**
     * Get remaining cooldown time for a ritual in seconds.
     */
    public static double getRitualCooldown(ServerPlayerEntity player, Ritual ritual) {
        String cooldownKey = player.getUuid() + ":" + ritual.getId();
        if (!ritualCooldowns.containsKey(cooldownKey)) {
            return 0.0;
        }

        long lastCast = ritualCooldowns.get(cooldownKey);
        long remaining =
                (ritual.getCooldownSeconds() * 1000L) - (System.currentTimeMillis() - lastCast);
        return Math.max(0.0, remaining / 1000.0);
    }

    /**
     * Reset cooldown for a ritual (debugging only).
     */
    public static void resetRitualCooldown(ServerPlayerEntity player, Ritual ritual) {
        String cooldownKey = player.getUuid() + ":" + ritual.getId();
        ritualCooldowns.remove(cooldownKey);
    }
}
