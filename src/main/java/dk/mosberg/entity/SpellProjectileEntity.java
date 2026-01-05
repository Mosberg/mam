package dk.mosberg.entity;

import dk.mosberg.MAM;
import dk.mosberg.spell.Spell;
import dk.mosberg.spell.StatusEffectData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

/**
 * Custom spell projectile entity that can apply spell effects on hit.
 */
public class SpellProjectileEntity extends ThrownItemEntity {
    private Spell spell;
    private ParticleEffect particleType = ParticleTypes.WITCH;

    public SpellProjectileEntity(EntityType<? extends SpellProjectileEntity> entityType,
            World world) {
        super(entityType, world);
    }

    public SpellProjectileEntity(World world, LivingEntity owner) {
        super(ModEntities.SPELL_PROJECTILE, owner, world);
    }

    public void setSpell(Spell spell) {
        this.spell = spell;

        // Set particle type based on spell school
        if (spell != null) {
            this.particleType = switch (spell.getSchool()) {
                case FIRE -> ParticleTypes.FLAME;
                case ICE -> ParticleTypes.SNOWFLAKE;
                case NATURE -> ParticleTypes.HAPPY_VILLAGER;
                case DARK -> ParticleTypes.SMOKE;
                case LIGHT -> ParticleTypes.END_ROD;
                case THUNDER -> ParticleTypes.ELECTRIC_SPARK;
                case WATER -> ParticleTypes.DRIPPING_WATER;
                default -> ParticleTypes.WITCH;
            };
        }
    }

    @Override
    protected Item getDefaultItem() {
        return Items.SNOWBALL; // Fallback item
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);

        if (this.getWorld().isClient || spell == null) {
            return;
        }

        // Apply spell effects to hit entity
        if (entityHitResult.getEntity() instanceof LivingEntity target) {
            ServerWorld world = (ServerWorld) this.getWorld();

            // Apply damage
            if (spell.getDamage() > 0) {
                target.damage(world, this.getDamageSources().magic(), (float) spell.getDamage());
            }

            // Apply status effects
            if (spell.getStatusEffects() != null) {
                for (StatusEffectData effectData : spell.getStatusEffects()) {
                    applyStatusEffect(target, effectData);
                }
            }

            MAM.LOGGER.debug("Spell projectile hit {}, applied {} damage",
                    target.getName().getString(), spell.getDamage());
        }

        // Remove projectile on hit
        this.discard();
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);

        if (!this.getWorld().isClient) {
            // Create particle effect on impact
            ServerWorld world = (ServerWorld) this.getWorld();
            world.spawnParticles(particleType, this.getX(), this.getY(), this.getZ(), 10, 0.3, 0.3,
                    0.3, 0.1);

            this.discard();
        }
    }

    @Override
    public void tick() {
        super.tick();

        // Spawn particle trail
        if (this.getWorld().isClient && spell != null) {
            for (int i = 0; i < 2; i++) {
                this.getWorld().addParticle(particleType, this.getX(), this.getY(), this.getZ(), 0,
                        0, 0);
            }
        }
    }

    /**
     * Apply status effect to entity.
     */
    private void applyStatusEffect(LivingEntity entity, StatusEffectData effectData) {
        try {
            var effect = getStatusEffectFromString(effectData.getEffect());

            if (effect != null) {
                entity.addStatusEffect(new net.minecraft.entity.effect.StatusEffectInstance(effect,
                        effectData.getDuration(), effectData.getAmplifier(), false, true, true));
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
            default -> {
                try {
                    net.minecraft.util.Identifier id =
                            effectName.contains(":") ? net.minecraft.util.Identifier.of(effectName)
                                    : net.minecraft.util.Identifier.ofVanilla(effectName);
                    yield net.minecraft.registry.Registries.STATUS_EFFECT.getEntry(id).orElse(null);
                } catch (Exception e) {
                    yield null;
                }
            }
        };
    }
}
