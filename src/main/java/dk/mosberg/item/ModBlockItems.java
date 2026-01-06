package dk.mosberg.item;

import java.util.HashMap;
import java.util.Map;
import dk.mosberg.MAM;
import dk.mosberg.block.ModBlocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

/**
 * Registry for block items - items that place blocks.
 */
public class ModBlockItems {
        private static final Map<String, BlockItem> BLOCK_ITEMS = new HashMap<>();

        // Mana Crystal Block Items
        public static final BlockItem PERSONAL_MANA_CRYSTAL =
                        registerBlockItem("personal_mana_crystal", ModBlocks.PERSONAL_MANA_CRYSTAL);
        public static final BlockItem AURA_MANA_CRYSTAL =
                        registerBlockItem("aura_mana_crystal", ModBlocks.AURA_MANA_CRYSTAL);
        public static final BlockItem RESERVE_MANA_CRYSTAL =
                        registerBlockItem("reserve_mana_crystal", ModBlocks.RESERVE_MANA_CRYSTAL);

        // Ritual Block Items
        public static final BlockItem RITUAL_PEDESTAL =
                        registerBlockItem("ritual_pedestal", ModBlocks.RITUAL_PEDESTAL);
        public static final BlockItem RITUAL_CANDLE =
                        registerBlockItem("ritual_candle", ModBlocks.RITUAL_CANDLE);

        // Building Block Items
        public static final BlockItem MANA_INFUSED_STONE =
                        registerBlockItem("mana_infused_stone", ModBlocks.MANA_INFUSED_STONE);
        public static final BlockItem MANA_INFUSED_STONE_BRICKS = registerBlockItem(
                        "mana_infused_stone_bricks", ModBlocks.MANA_INFUSED_STONE_BRICKS);

        // Crafting Block Items
        public static final BlockItem ARCANE_ALTAR =
                        registerBlockItem("arcane_altar", ModBlocks.ARCANE_ALTAR);

        // Special Block Items
        public static final BlockItem GIFT_BOX = registerBlockItem("gift_box", ModBlocks.GIFT_BOX);

        // Utility Block Items - TODO: Add custom textures
        public static final BlockItem MANA_CRYSTAL_BLOCK =
                        registerBlockItem("mana_crystal_block", ModBlocks.MANA_CRYSTAL_BLOCK);
        public static final BlockItem MANA_INFUSER =
                        registerBlockItem("mana_infuser", ModBlocks.MANA_INFUSER);
        public static final BlockItem MANA_LAMP =
                        registerBlockItem("mana_lamp", ModBlocks.MANA_LAMP);
        public static final BlockItem MANA_LEY_LINE =
                        registerBlockItem("mana_ley_line", ModBlocks.MANA_LEY_LINE);
        public static final BlockItem MANA_NODE_BLOCK =
                        registerBlockItem("mana_node_block", ModBlocks.MANA_NODE_BLOCK);
        public static final BlockItem RITUAL_CIRCLE_BLOCK =
                        registerBlockItem("ritual_circle_block", ModBlocks.RITUAL_CIRCLE_BLOCK);
        public static final BlockItem SPELL_ALTAR =
                        registerBlockItem("spell_altar", ModBlocks.SPELL_ALTAR);
        public static final BlockItem SPELL_AMPLIFIER =
                        registerBlockItem("spell_amplifier", ModBlocks.SPELL_AMPLIFIER);

        // Natural Block Items - TODO: Add custom textures
        public static final BlockItem ARCANE_TREE_LOG =
                        registerBlockItem("arcane_tree_log", ModBlocks.ARCANE_TREE_LOG);
        public static final BlockItem ARCANE_TREE_LEAVES =
                        registerBlockItem("arcane_tree_leaves", ModBlocks.ARCANE_TREE_LEAVES);
        public static final BlockItem ARCANE_WORKBENCH =
                        registerBlockItem("arcane_workbench", ModBlocks.ARCANE_WORKBENCH);

        /**
         * Register a block item.
         */
        private static BlockItem registerBlockItem(String name, net.minecraft.block.Block block) {
                Identifier id = Identifier.of(MAM.MOD_ID, name);
                RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, id);

                Item.Settings settings = new Item.Settings().registryKey(key);
                BlockItem item = Registry.register(Registries.ITEM, key,
                                new BlockItem(block, settings));
                BLOCK_ITEMS.put(name, item);

                return item;
        }

        /**
         * Get all registered block items.
         */
        public static Map<String, BlockItem> getAllBlockItems() {
                return Map.copyOf(BLOCK_ITEMS);
        }

        /**
         * Initialize all block items.
         */
        public static void initialize() {
                MAM.LOGGER.info("Registering {} block items", BLOCK_ITEMS.size());
        }
}
