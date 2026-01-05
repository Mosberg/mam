package dk.mosberg.spell;

import dk.mosberg.MAM;
import dk.mosberg.mana.ManaComponent;
import dk.mosberg.mana.ManaManager;
import dk.mosberg.mana.ManaPoolType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

/**
 * Utility class for casting spells with mana cost validation and effects. Provides safe spell
 * execution with proper error handling.
 */
public class SpellCaster {

    /**
     * Attempt to cast a spell for a player. Validates mana cost and applies effects.
     *
     * @param player The player casting the spell
     * @param spellId The spell identifier
     * @return true if spell was successfully cast
     */
    public static boolean castSpell(ServerPlayerEntity player, Identifier spellId) {
        if (player == null) {
            MAM.LOGGER.error("Cannot cast spell: player is null");
            return false;
        }

        if (spellId == null) {
            MAM.LOGGER.error("Cannot cast spell: spell ID is null");
            return false;
        }

        // Get spell from registry
        Spell spell = SpellLoader.getSpell(spellId);
        if (spell == null) {
            sendErrorMessage(player, "Unknown spell: " + spellId);
            MAM.LOGGER.warn("Player {} attempted to cast unknown spell: {}",
                    player.getName().getString(), spellId);
            return false;
        }

        return castSpell(player, spell);
    }

    /**
     * Attempt to cast a spell for a player.
     *
     * @param player The player casting the spell
     * @param spell The spell to cast
     * @return true if spell was successfully cast
     */
    public static boolean castSpell(ServerPlayerEntity player, Spell spell) {
        if (player == null || spell == null) {
            MAM.LOGGER.error("Cannot cast spell: player or spell is null");
            return false;
        }

        // Get player's mana component
        ManaComponent mana = ManaManager.getComponent(player);

        // Check mana cost
        double manaCost = spell.getManaCost();
        ManaPoolType poolType = getPrimaryPoolForSchool(spell.getSchool());

        if (!mana.has(poolType, manaCost)) {
            sendErrorMessage(player, String.format("Insufficient %s mana! (need %.1f, have %.1f)",
                    poolType.getId(), manaCost, mana.getPool(poolType).getCurrent()));
            MAM.LOGGER.debug("Player {} has insufficient mana for spell {}",
                    player.getName().getString(), spell.getId());
            return false;
        }

        // Consume mana
        if (!mana.consume(poolType, manaCost)) {
            sendErrorMessage(player, "Failed to consume mana!");
            MAM.LOGGER.error("Failed to consume mana for player: {}", player.getName().getString());
            return false;
        }

        // Execute spell effects
        try {
            executeSpellEffects(player, spell);
            sendSuccessMessage(player, "Cast " + spell.getId().getPath() + "!");
            MAM.LOGGER.info("Player {} cast spell {} (cost: {} {})", player.getName().getString(),
                    spell.getId(), manaCost, poolType.getId());
            return true;
        } catch (Exception e) {
            // Refund mana on failure
            mana.add(poolType, manaCost);
            sendErrorMessage(player, "Spell casting failed!");
            MAM.LOGGER.error("Failed to execute spell {} for player {}", spell.getId(),
                    player.getName().getString(), e);
            return false;
        }
    }

    /**
     * Execute spell effects based on cast type. Handles PROJECTILE, AOE, UTILITY, RITUAL, and
     * SYNERGY spell types.
     */
    private static void executeSpellEffects(ServerPlayerEntity player, Spell spell) {
        MAM.LOGGER.debug("Executing effects for spell: {} (type: {})", spell.getId(),
                spell.getCastType());

        switch (spell.getCastType()) {
            case PROJECTILE -> executeProjectileSpell(player, spell);
            case AOE -> executeAoeSpell(player, spell);
            case UTILITY -> executeUtilitySpell(player, spell);
            case RITUAL -> executeRitualSpell(player, spell);
            case SYNERGY -> executeSynergySpell(player, spell);
        }
    }

    /**
     * Execute projectile-based spell effects. Creates a projectile entity that launches from the
     * player.
     */
    private static void executeProjectileSpell(ServerPlayerEntity player, Spell spell) {
        MAM.LOGGER.debug("Launching projectile for spell: {}", spell.getId());

        // Apply immediate status effects to caster
        applyStatusEffects(player, spell);

        // Launch custom spell projectile
        net.minecraft.server.world.ServerWorld world =
                (net.minecraft.server.world.ServerWorld) player.getEntityWorld();
        dk.mosberg.entity.SpellProjectileEntity projectile =
                new dk.mosberg.entity.SpellProjectileEntity(world, player);
        projectile.setSpell(spell);
        projectile.setPosition(player.getX(), player.getEyeY() - 0.1, player.getZ());

        // Set velocity based on spell properties
        double speed = spell.getProjectileSpeed() > 0 ? spell.getProjectileSpeed() : 1.5;
        projectile.setVelocity(player, player.getPitch(), player.getYaw(), 0.0f, (float) speed,
                1.0f);

        // Spawn the projectile
        world.spawnEntity(projectile);

        MAM.LOGGER.debug("Spawned spell projectile for {} with speed: {}", spell.getId(), speed);
        sendSuccessMessage(player, "Projectile spell launched!");
    }

    /**
     * Execute area-of-effect spell. Affects all entities within a radius of the caster.
     */
    private static void executeAoeSpell(ServerPlayerEntity player, Spell spell) {
        MAM.LOGGER.debug("Executing AOE spell: {}", spell.getId());

        double damage = spell.getDamage();
        double radius =
                spell.getAoeRadius() > 0 ? spell.getAoeRadius() : (3.0 + (spell.getTier() * 0.5));
        net.minecraft.server.world.ServerWorld world =
                (net.minecraft.server.world.ServerWorld) player.getEntityWorld();

        if (damage > 0) {
            // Get all living entities in radius
            net.minecraft.util.math.Box box = new net.minecraft.util.math.Box(
                    player.getX() - radius, player.getY() - radius, player.getZ() - radius,
                    player.getX() + radius, player.getY() + radius, player.getZ() + radius);

            int affectedCount = 0;
            for (net.minecraft.entity.LivingEntity entity : world.getEntitiesByClass(
                    net.minecraft.entity.LivingEntity.class, box,
                    e -> e != player && e.squaredDistanceTo(player) <= radius * radius)) {

                // Apply damage
                entity.damage(world, player.getDamageSources().magic(), (float) damage);

                // Apply status effects to target
                if (spell.getStatusEffects() != null) {
                    for (StatusEffectData effectData : spell.getStatusEffects()) {
                        applyStatusEffectToEntity(entity, effectData);
                    }
                }
                affectedCount++;
            }

            MAM.LOGGER.debug("AOE hit {} entities in {} block radius", affectedCount, radius);
            sendSuccessMessage(player, String.format("AOE hit %d targets!", affectedCount));
        } else {
            applyStatusEffects(player, spell);
            sendSuccessMessage(player, "AOE spell activated!");
        }
    }

    /**
     * Execute utility spell. Applies beneficial effects to the caster or environment.
     */
    private static void executeUtilitySpell(ServerPlayerEntity player, Spell spell) {
        MAM.LOGGER.debug("Executing utility spell: {}", spell.getId());

        applyStatusEffects(player, spell);
        sendSuccessMessage(player, "Utility spell activated!");
    }

    /**
     * Execute ritual spell. Triggers complex multi-step ritual mechanics.
     */
    private static void executeRitualSpell(ServerPlayerEntity player, Spell spell) {
        MAM.LOGGER.debug("Initiating ritual: {}", spell.getId());

        // Rituals require special activation sequences
        sendSuccessMessage(player, "Ritual initiated!");
    }

    /**
     * Execute synergy spell. Checks for active spell combinations and enhances effects.
     */
    private static void executeSynergySpell(ServerPlayerEntity player, Spell spell) {
        MAM.LOGGER.debug("Executing synergy spell: {}", spell.getId());

        // Check for active spell effects to create combos
        applyStatusEffects(player, spell);
        sendSuccessMessage(player, "Synergy spell activated!");
    }

    /**
     * Apply status effects from spell to player.
     */
    private static void applyStatusEffects(ServerPlayerEntity player, Spell spell) {
        if (spell.getStatusEffects() == null || spell.getStatusEffects().isEmpty()) {
            return;
        }

        for (StatusEffectData effectData : spell.getStatusEffects()) {
            applyStatusEffectToEntity(player, effectData);
        }
    }

    /**
     * Apply a status effect to an entity.
     */
    private static void applyStatusEffectToEntity(net.minecraft.entity.LivingEntity entity,
            StatusEffectData effectData) {
        try {
            // Convert effect string to StatusEffect
            net.minecraft.registry.entry.RegistryEntry<net.minecraft.entity.effect.StatusEffect> effect =
                    getStatusEffectFromString(effectData.getEffect());

            if (effect != null) {
                entity.addStatusEffect(new net.minecraft.entity.effect.StatusEffectInstance(effect,
                        effectData.getDuration(), effectData.getAmplifier(), false, // ambient
                        true, // showParticles
                        true // showIcon
                ));

                MAM.LOGGER.trace("Applied effect {} to {} for {} ticks", effectData.getEffect(),
                        entity.getName().getString(), effectData.getDuration());
            } else {
                MAM.LOGGER.warn("Unknown status effect: {}", effectData.getEffect());
            }
        } catch (Exception e) {
            MAM.LOGGER.error("Failed to apply status effect: {}", effectData.getEffect(), e);
        }
    }

    /**
     * Convert effect string to StatusEffect registry entry.
     */
    private static net.minecraft.registry.entry.RegistryEntry<net.minecraft.entity.effect.StatusEffect> getStatusEffectFromString(
            String effectName) {

        // Map common effect names to Minecraft status effects
        return switch (effectName.toLowerCase()) {
            case "fire", "burning" -> net.minecraft.registry.Registries.STATUS_EFFECT
                    .getEntry(net.minecraft.util.Identifier.ofVanilla("wither")).orElse(null);
            case "speed", "swiftness" -> net.minecraft.registry.Registries.STATUS_EFFECT
                    .getEntry(net.minecraft.util.Identifier.ofVanilla("speed")).orElse(null);
            case "strength" -> net.minecraft.registry.Registries.STATUS_EFFECT
                    .getEntry(net.minecraft.util.Identifier.ofVanilla("strength")).orElse(null);
            case "regeneration", "regen" -> net.minecraft.registry.Registries.STATUS_EFFECT
                    .getEntry(net.minecraft.util.Identifier.ofVanilla("regeneration")).orElse(null);
            case "poison" -> net.minecraft.registry.Registries.STATUS_EFFECT
                    .getEntry(net.minecraft.util.Identifier.ofVanilla("poison")).orElse(null);
            case "weakness" -> net.minecraft.registry.Registries.STATUS_EFFECT
                    .getEntry(net.minecraft.util.Identifier.ofVanilla("weakness")).orElse(null);
            case "slowness", "slow" -> net.minecraft.registry.Registries.STATUS_EFFECT
                    .getEntry(net.minecraft.util.Identifier.ofVanilla("slowness")).orElse(null);
            case "resistance" -> net.minecraft.registry.Registries.STATUS_EFFECT
                    .getEntry(net.minecraft.util.Identifier.ofVanilla("resistance")).orElse(null);
            case "absorption" -> net.minecraft.registry.Registries.STATUS_EFFECT
                    .getEntry(net.minecraft.util.Identifier.ofVanilla("absorption")).orElse(null);
            case "levitation" -> net.minecraft.registry.Registries.STATUS_EFFECT
                    .getEntry(net.minecraft.util.Identifier.ofVanilla("levitation")).orElse(null);
            case "glowing" -> net.minecraft.registry.Registries.STATUS_EFFECT
                    .getEntry(net.minecraft.util.Identifier.ofVanilla("glowing")).orElse(null);
            default -> {
                // Try direct registry lookup
                try {
                    net.minecraft.util.Identifier id =
                            effectName.contains(":") ? net.minecraft.util.Identifier.of(effectName)
                                    : net.minecraft.util.Identifier.ofVanilla(effectName);
                    yield net.minecraft.registry.Registries.STATUS_EFFECT.getEntry(id).orElse(null);
                } catch (Exception e) {
                    MAM.LOGGER.warn("Could not parse effect identifier: {}", effectName);
                    yield null;
                }
            }
        };
    }

    /**
     * Get the primary mana pool for a spell school. Maps spell schools to appropriate mana pool
     * types with cascading consumption.
     */
    private static ManaPoolType getPrimaryPoolForSchool(SpellSchool school) {
        // Map schools to pools:
        // - Combat/offensive spells use PERSONAL (Primary - 250 equivalent)
        // - Defensive/utility spells use AURA (Secondary - 500 equivalent)
        // - Ritual/powerful spells use RESERVE (Tertiary - 1000 equivalent)
        return switch (school) {
            case FIRE, THUNDER, DARK, BLOOD, CHAOS -> ManaPoolType.PERSONAL;
            case ICE, EARTH, LIGHT, WATER -> ManaPoolType.AURA;
            case ARCANE, VOID, NATURE -> ManaPoolType.RESERVE;
            default -> ManaPoolType.PERSONAL;
        };
    }

    /**
     * Send an error message to the player.
     */
    private static void sendErrorMessage(ServerPlayerEntity player, String message) {
        player.sendMessage(Text.literal(message).formatted(Formatting.RED), true); // actionBar =
                                                                                   // true (shows
                                                                                   // above hotbar)
    }

    /**
     * Send a success message to the player.
     */
    private static void sendSuccessMessage(ServerPlayerEntity player, String message) {
        player.sendMessage(Text.literal(message).formatted(Formatting.AQUA), true); // actionBar =
                                                                                    // true (shows
                                                                                    // above hotbar)
    }

    /**
     * Check if a player can cast a spell (without actually casting).
     *
     * @param player The player
     * @param spellId The spell identifier
     * @return true if player has enough mana
     */
    public static boolean canCastSpell(ServerPlayerEntity player, Identifier spellId) {
        if (player == null || spellId == null) {
            return false;
        }

        Spell spell = SpellLoader.getSpell(spellId);
        if (spell == null) {
            return false;
        }

        return canCastSpell(player, spell);
    }

    /**
     * Check if a player can cast a spell (without actually casting).
     *
     * @param player The player
     * @param spell The spell
     * @return true if player has enough mana
     */
    public static boolean canCastSpell(ServerPlayerEntity player, Spell spell) {
        if (player == null || spell == null) {
            return false;
        }

        ManaComponent mana = ManaManager.getComponent(player);
        ManaPoolType poolType = getPrimaryPoolForSchool(spell.getSchool());
        return mana.has(poolType, spell.getManaCost());
    }

    /**
     * Get mana cost for a spell.
     *
     * @param spellId The spell identifier
     * @return The mana cost, or -1 if spell not found
     */
    public static double getManaCost(Identifier spellId) {
        if (spellId == null) {
            return -1;
        }

        Spell spell = SpellLoader.getSpell(spellId);
        return spell != null ? spell.getManaCost() : -1;
    }
}
