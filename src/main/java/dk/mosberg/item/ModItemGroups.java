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

    public static final RegistryKey<ItemGroup> MAGIC_GROUP =
            RegistryKey.of(RegistryKeys.ITEM_GROUP, Identifier.of(MAM.MOD_ID, "magic"));

    public static final ItemGroup MAGIC_ITEMS = FabricItemGroup.builder()
            .icon(() -> new ItemStack(ModItems.ARCANE_WAND_MASTER))
            .displayName(Text.translatable("itemGroup.mam.magic")).entries((context, entries) -> {
                // === WANDS ===
                entries.add(ModItems.FIRE_WAND_NOVICE);
                entries.add(ModItems.FIRE_WAND_MASTER);
                entries.add(ModItems.ICE_WAND_NOVICE);
                entries.add(ModItems.ICE_WAND_MASTER);
                entries.add(ModItems.ARCANE_WAND_NOVICE);
                entries.add(ModItems.ARCANE_WAND_MASTER);

                // === SPELL BOOKS ===
                entries.add(ModItems.FIRE_SPELL_BOOK);
                entries.add(ModItems.ICE_SPELL_BOOK);
                entries.add(ModItems.ARCANE_SPELL_BOOK);
                entries.add(ModItems.NATURE_SPELL_BOOK);
                entries.add(ModItems.DARK_SPELL_BOOK);
                entries.add(ModItems.LIGHT_SPELL_BOOK);

                // === GEMSTONES ===
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

                // === MANA CRYSTAL BLOCKS ===
                entries.add(ModBlockItems.PERSONAL_MANA_CRYSTAL);
                entries.add(ModBlockItems.AURA_MANA_CRYSTAL);
                entries.add(ModBlockItems.RESERVE_MANA_CRYSTAL);

                // === RITUAL BLOCKS ===
                entries.add(ModBlockItems.RITUAL_PEDESTAL);
                entries.add(ModBlockItems.RITUAL_CANDLE);

                // === BUILDING BLOCKS ===
                entries.add(ModBlockItems.MANA_INFUSED_STONE);
                entries.add(ModBlockItems.MANA_INFUSED_STONE_BRICKS);

                // === CRAFTING BLOCKS ===
                entries.add(ModBlockItems.ARCANE_ALTAR);
            }).build();

    /**
     * Initialize and register all item groups.
     */
    public static void initialize() {
        Registry.register(Registries.ITEM_GROUP, MAGIC_GROUP, MAGIC_ITEMS);

        MAM.LOGGER.info("Registered item groups");
    }
}
