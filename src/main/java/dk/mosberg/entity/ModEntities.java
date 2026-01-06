// Add additional entities as needed
// ...existing code...
package dk.mosberg.entity;

import dk.mosberg.MAM;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

/**
 * Registry for all custom entities in the mod.
 */
public class ModEntities {

    public static final EntityType<SpellProjectileEntity> SPELL_PROJECTILE = Registry.register(
            Registries.ENTITY_TYPE, Identifier.of(MAM.MOD_ID, "spell_projectile"),
            EntityType.Builder
                    .<SpellProjectileEntity>create(SpellProjectileEntity::new, SpawnGroup.MISC)
                    .dimensions(0.25f, 0.25f).build(RegistryKey.of(RegistryKeys.ENTITY_TYPE,
                            Identifier.of(MAM.MOD_ID, "spell_projectile"))));

    public static final EntityType<FireElementalEntity> FIRE_ELEMENTAL = Registry.register(
            Registries.ENTITY_TYPE, Identifier.of(MAM.MOD_ID, "fire_elemental"),
            EntityType.Builder
                    .<FireElementalEntity>create(FireElementalEntity::new, SpawnGroup.CREATURE)
                    .dimensions(0.6f, 1.8f).maxTrackingRange(64)
                    .build(RegistryKey.of(RegistryKeys.ENTITY_TYPE,
                            Identifier.of(MAM.MOD_ID, "fire_elemental"))));

    /**
     * Initialize all entities. Called from MAM.onInitialize()
     */
    public static void initialize() {
        MAM.LOGGER.info("Registering custom entities");

        // Register attributes for living entities
        FabricDefaultAttributeRegistry.register(FIRE_ELEMENTAL,
                FireElementalEntity.createFireElementalAttributes());
    }
}
