package dk.mosberg.item;

import dk.mosberg.MAM;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

/**
 * Defines creative mode item groups for the mod.
 */
public class ModItemGroups {

    public static final RegistryKey<ItemGroup> GEMSTONES_GROUP =
            RegistryKey.of(RegistryKeys.ITEM_GROUP, Identifier.of(MAM.MOD_ID, "gemstones"));

    public static final ItemGroup GEMSTONES =
            FabricItemGroup.builder().icon(() -> new ItemStack(ModItems.RUBY))
                    .displayName(Text.translatable("itemGroup.mam.gemstones")).build();

    /**
     * Initialize and register all item groups.
     */
    public static void initialize() {
        Registry.register(Registries.ITEM_GROUP, GEMSTONES_GROUP, GEMSTONES);

        // Add all gemstones to the group
        FabricItemGroup.builder().icon(() -> new ItemStack(ModItems.RUBY))
                .displayName(Text.translatable("itemGroup.mam.gemstones"))
                .entries((context, entries) -> {
                    // Epic tier
                    entries.add(ModItems.RUBY);
                    entries.add(ModItems.SAPPHIRE);
                    entries.add(ModItems.TANZANITE);

                    // Rare tier
                    entries.add(ModItems.APATITE);
                    entries.add(ModItems.AQUAMARINE);
                    entries.add(ModItems.MOONSTONE);
                    entries.add(ModItems.RHODONITE);
                    entries.add(ModItems.TOPAZ);
                    entries.add(ModItems.TOURMALINE);

                    // Uncommon tier
                    entries.add(ModItems.CARNELIAN);
                    entries.add(ModItems.CITRINE);
                    entries.add(ModItems.JADE);
                    entries.add(ModItems.PERIDOT);
                    entries.add(ModItems.SODALITE);

                    // Common tier
                    entries.add(ModItems.HEMATITE);
                }).build();

        MAM.LOGGER.info("Registered item groups");
    }
}
