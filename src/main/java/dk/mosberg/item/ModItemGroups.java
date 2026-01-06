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

                // === MAGICAL ITEMS ===
                entries.add(ModItems.MANA_BOTTLE);
                entries.add(ModItems.MANA_SHARD);
                entries.add(ModItems.MANA_NODE);
                entries.add(ModItems.ESSENCE_VIAL);
                entries.add(ModItems.RITUAL_FOCUS);
                entries.add(ModItems.SPELL_COMPONENT);
                entries.add(ModItems.SPELL_SCROLL);
                entries.add(ModItems.GRIMOIRE);
                entries.add(ModItems.SPELL_TOME);
                entries.add(ModItems.CATALYST_ARTIFACT);
                entries.add(ModItems.ENCHANTED_GEM);

                // === MANA CRYSTAL BLOCKS ===
                entries.add(ModBlockItems.PERSONAL_MANA_CRYSTAL);
                entries.add(ModBlockItems.AURA_MANA_CRYSTAL);
                entries.add(ModBlockItems.RESERVE_MANA_CRYSTAL);

                // === RITUAL BLOCKS ===
                entries.add(ModBlockItems.RITUAL_PEDESTAL);
                entries.add(ModBlockItems.RITUAL_CANDLE);
                entries.add(ModBlockItems.RITUAL_CIRCLE_BLOCK);

                // === BUILDING BLOCKS ===
                entries.add(ModBlockItems.MANA_INFUSED_STONE);
                entries.add(ModBlockItems.MANA_INFUSED_STONE_BRICKS);

                // === CRAFTING BLOCKS ===
                entries.add(ModBlockItems.ARCANE_ALTAR);

                // === UTILITY BLOCKS ===
                entries.add(ModBlockItems.GIFT_BOX);
                entries.add(ModBlockItems.MANA_CRYSTAL_BLOCK);
                entries.add(ModBlockItems.MANA_INFUSER);
                entries.add(ModBlockItems.MANA_LAMP);
                entries.add(ModBlockItems.MANA_LEY_LINE);
                entries.add(ModBlockItems.MANA_NODE_BLOCK);
                entries.add(ModBlockItems.SPELL_ALTAR);
                entries.add(ModBlockItems.SPELL_AMPLIFIER);

                // === NATURAL BLOCKS ===
                entries.add(ModBlockItems.ARCANE_TREE_LOG);
                entries.add(ModBlockItems.ARCANE_TREE_LEAVES);
                entries.add(ModBlockItems.ARCANE_WORKBENCH);
            }).build();

    /**
     * Initialize and register all item groups.
     */
    public static void initialize() {
        Registry.register(Registries.ITEM_GROUP, MAGIC_GROUP, MAGIC_ITEMS);

        MAM.LOGGER.info("Registered item groups");
    }
}
