package dk.mosberg.entity;

import dk.mosberg.MAM;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
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
            Registries.ENTITY_TYPE,
            RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(MAM.MOD_ID, "spell_projectile")),
            FabricEntityTypeBuilder
                    .<SpellProjectileEntity>create(SpawnGroup.MISC, SpellProjectileEntity::new)
                    .dimensions(EntityDimensions.fixed(0.25f, 0.25f)).trackRangeChunks(4)
                    .trackedUpdateRate(10).build());

    /**
     * Initialize all entities. Called from MAM.onInitialize()
     */
    public static void initialize() {
        MAM.LOGGER.info("Registering custom entities");
    }
}
