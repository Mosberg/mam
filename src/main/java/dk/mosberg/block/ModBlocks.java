package dk.mosberg.block;

import java.util.HashMap;
import java.util.Map;
import dk.mosberg.MAM;
import dk.mosberg.mana.ManaPoolType;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;

/**
 * Registry for all mod blocks including mana crystals and ritual blocks.
 */
public class ModBlocks {
        private static final Map<String, Block> BLOCKS = new HashMap<>();

        // Mana Crystal Blocks - emit light and can be used in rituals
        public static final Block PERSONAL_MANA_CRYSTAL = registerBlock("personal_mana_crystal",
                        new ManaCrystalBlock(ManaPoolType.PERSONAL, AbstractBlock.Settings.create()
                                        .registryKey(RegistryKey.of(RegistryKeys.BLOCK,
                                                        Identifier.of(MAM.MOD_ID,
                                                                        "personal_mana_crystal")))
                                        .mapColor(MapColor.CYAN).luminance(state -> 12)
                                        .strength(3.0f, 9.0f).sounds(BlockSoundGroup.AMETHYST_BLOCK)
                                        .pistonBehavior(PistonBehavior.BLOCK)));

        public static final Block AURA_MANA_CRYSTAL = registerBlock("aura_mana_crystal",
                        new ManaCrystalBlock(ManaPoolType.AURA, AbstractBlock.Settings.create()
                                        .registryKey(RegistryKey.of(RegistryKeys.BLOCK,
                                                        Identifier.of(MAM.MOD_ID,
                                                                        "aura_mana_crystal")))
                                        .mapColor(MapColor.PURPLE).luminance(state -> 12)
                                        .strength(3.0f, 9.0f).sounds(BlockSoundGroup.AMETHYST_BLOCK)
                                        .pistonBehavior(PistonBehavior.BLOCK)));

        public static final Block RESERVE_MANA_CRYSTAL = registerBlock("reserve_mana_crystal",
                        new ManaCrystalBlock(ManaPoolType.RESERVE, AbstractBlock.Settings.create()
                                        .registryKey(RegistryKey.of(RegistryKeys.BLOCK,
                                                        Identifier.of(MAM.MOD_ID,
                                                                        "reserve_mana_crystal")))
                                        .mapColor(MapColor.ORANGE).luminance(state -> 12)
                                        .strength(3.0f, 9.0f).sounds(BlockSoundGroup.AMETHYST_BLOCK)
                                        .pistonBehavior(PistonBehavior.BLOCK)));

        // Ritual Pedestal - central block for ritual casting
        public static final Block RITUAL_PEDESTAL = registerBlock("ritual_pedestal",
                        new RitualPedestalBlock(AbstractBlock.Settings.create()
                                        .registryKey(RegistryKey.of(RegistryKeys.BLOCK,
                                                        Identifier.of(MAM.MOD_ID,
                                                                        "ritual_pedestal")))
                                        .mapColor(MapColor.BLACK).luminance(state -> 8)
                                        .strength(5.0f, 12.0f).sounds(BlockSoundGroup.STONE)
                                        .nonOpaque().pistonBehavior(PistonBehavior.BLOCK)));

        // Ritual Candles - decorative blocks that enhance rituals
        public static final Block RITUAL_CANDLE = registerBlock("ritual_candle",
                        new RitualCandleBlock(AbstractBlock.Settings.create()
                                        .registryKey(RegistryKey.of(RegistryKeys.BLOCK,
                                                        Identifier.of(MAM.MOD_ID, "ritual_candle")))
                                        .mapColor(MapColor.WHITE)
                                        .luminance(state -> state.get(RitualCandleBlock.LIT) ? 14
                                                        : 0)
                                        .strength(0.5f).sounds(BlockSoundGroup.CANDLE)
                                        .nonOpaque()));

        // Mana Infused Stones - building blocks for magical structures
        public static final Block MANA_INFUSED_STONE = registerBlock("mana_infused_stone",
                        new Block(AbstractBlock.Settings.create()
                                        .registryKey(RegistryKey.of(RegistryKeys.BLOCK,
                                                        Identifier.of(MAM.MOD_ID,
                                                                        "mana_infused_stone")))
                                        .mapColor(MapColor.LAPIS_BLUE).luminance(state -> 4)
                                        .strength(2.0f, 6.0f).sounds(BlockSoundGroup.STONE)));

        public static final Block MANA_INFUSED_STONE_BRICKS = registerBlock(
                        "mana_infused_stone_bricks",
                        new Block(AbstractBlock.Settings.create().registryKey(
                                        RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(MAM.MOD_ID,
                                                        "mana_infused_stone_bricks")))
                                        .mapColor(MapColor.LAPIS_BLUE).luminance(state -> 4)
                                        .strength(2.5f, 7.0f).sounds(BlockSoundGroup.STONE)));

        // Arcane Altar - crafting block for magical items
        public static final Block ARCANE_ALTAR = registerBlock("arcane_altar",
                        new ArcaneAltarBlock(AbstractBlock.Settings.create()
                                        .registryKey(RegistryKey.of(RegistryKeys.BLOCK,
                                                        Identifier.of(MAM.MOD_ID, "arcane_altar")))
                                        .mapColor(MapColor.PURPLE).luminance(state -> 10)
                                        .strength(4.0f, 10.0f).sounds(BlockSoundGroup.STONE)
                                        .nonOpaque()));

        // Gemstone Ores - Epic Tier
        public static final Block RUBY_ORE = registerBlock("ruby_ore", new GemstoneOreBlock(
                        AbstractBlock.Settings.create()
                                        .registryKey(RegistryKey.of(RegistryKeys.BLOCK,
                                                        Identifier.of(MAM.MOD_ID, "ruby_ore")))
                                        .mapColor(MapColor.DARK_RED).strength(3.0f, 3.0f)
                                        .sounds(BlockSoundGroup.STONE).requiresTool(),
                        UniformIntProvider.create(5, 10)));

        public static final Block DEEPSLATE_RUBY_ORE = registerBlock("deepslate_ruby_ore",
                        new GemstoneOreBlock(
                                        AbstractBlock.Settings.create().registryKey(RegistryKey.of(
                                                        RegistryKeys.BLOCK,
                                                        Identifier.of(MAM.MOD_ID,
                                                                        "deepslate_ruby_ore")))
                                                        .mapColor(MapColor.DARK_RED)
                                                        .strength(4.5f, 3.0f)
                                                        .sounds(BlockSoundGroup.DEEPSLATE)
                                                        .requiresTool(),
                                        UniformIntProvider.create(5, 10)));

        public static final Block SAPPHIRE_ORE = registerBlock("sapphire_ore", new GemstoneOreBlock(
                        AbstractBlock.Settings.create()
                                        .registryKey(RegistryKey.of(RegistryKeys.BLOCK,
                                                        Identifier.of(MAM.MOD_ID, "sapphire_ore")))
                                        .mapColor(MapColor.BLUE).strength(3.0f, 3.0f)
                                        .sounds(BlockSoundGroup.STONE).requiresTool(),
                        UniformIntProvider.create(5, 10)));

        public static final Block DEEPSLATE_SAPPHIRE_ORE = registerBlock("deepslate_sapphire_ore",
                        new GemstoneOreBlock(
                                        AbstractBlock.Settings.create().registryKey(RegistryKey.of(
                                                        RegistryKeys.BLOCK,
                                                        Identifier.of(MAM.MOD_ID,
                                                                        "deepslate_sapphire_ore")))
                                                        .mapColor(MapColor.BLUE)
                                                        .strength(4.5f, 3.0f)
                                                        .sounds(BlockSoundGroup.DEEPSLATE)
                                                        .requiresTool(),
                                        UniformIntProvider.create(5, 10)));

        public static final Block TANZANITE_ORE = registerBlock("tanzanite_ore",
                        new GemstoneOreBlock(AbstractBlock.Settings.create()
                                        .registryKey(RegistryKey.of(RegistryKeys.BLOCK,
                                                        Identifier.of(MAM.MOD_ID, "tanzanite_ore")))
                                        .mapColor(MapColor.PURPLE).strength(3.0f, 3.0f)
                                        .sounds(BlockSoundGroup.STONE).requiresTool(),
                                        UniformIntProvider.create(5, 10)));

        public static final Block DEEPSLATE_TANZANITE_ORE = registerBlock("deepslate_tanzanite_ore",
                        new GemstoneOreBlock(
                                        AbstractBlock.Settings.create().registryKey(RegistryKey.of(
                                                        RegistryKeys.BLOCK,
                                                        Identifier.of(MAM.MOD_ID,
                                                                        "deepslate_tanzanite_ore")))
                                                        .mapColor(MapColor.PURPLE)
                                                        .strength(4.5f, 3.0f)
                                                        .sounds(BlockSoundGroup.DEEPSLATE)
                                                        .requiresTool(),
                                        UniformIntProvider.create(5, 10)));

        // Gemstone Ores - Rare Tier
        public static final Block APATITE_ORE = registerBlock("apatite_ore", new GemstoneOreBlock(
                        AbstractBlock.Settings.create()
                                        .registryKey(RegistryKey.of(RegistryKeys.BLOCK,
                                                        Identifier.of(MAM.MOD_ID, "apatite_ore")))
                                        .mapColor(MapColor.CYAN).strength(3.0f, 3.0f)
                                        .sounds(BlockSoundGroup.STONE).requiresTool(),
                        UniformIntProvider.create(3, 7)));

        public static final Block AQUAMARINE_ORE = registerBlock("aquamarine_ore",
                        new GemstoneOreBlock(
                                        AbstractBlock.Settings.create().registryKey(RegistryKey.of(
                                                        RegistryKeys.BLOCK,
                                                        Identifier.of(MAM.MOD_ID,
                                                                        "aquamarine_ore")))
                                                        .mapColor(MapColor.CYAN)
                                                        .strength(3.0f, 3.0f)
                                                        .sounds(BlockSoundGroup.STONE)
                                                        .requiresTool(),
                                        UniformIntProvider.create(3, 7)));

        public static final Block MOONSTONE_ORE = registerBlock("moonstone_ore",
                        new GemstoneOreBlock(AbstractBlock.Settings.create()
                                        .registryKey(RegistryKey.of(RegistryKeys.BLOCK,
                                                        Identifier.of(MAM.MOD_ID, "moonstone_ore")))
                                        .mapColor(MapColor.WHITE).strength(3.0f, 3.0f)
                                        .sounds(BlockSoundGroup.STONE).requiresTool(),
                                        UniformIntProvider.create(3, 7)));

        public static final Block RHODONITE_ORE = registerBlock("rhodonite_ore",
                        new GemstoneOreBlock(AbstractBlock.Settings.create()
                                        .registryKey(RegistryKey.of(RegistryKeys.BLOCK,
                                                        Identifier.of(MAM.MOD_ID, "rhodonite_ore")))
                                        .mapColor(MapColor.PINK).strength(3.0f, 3.0f)
                                        .sounds(BlockSoundGroup.STONE).requiresTool(),
                                        UniformIntProvider.create(3, 7)));

        public static final Block TOPAZ_ORE = registerBlock("topaz_ore", new GemstoneOreBlock(
                        AbstractBlock.Settings.create()
                                        .registryKey(RegistryKey.of(RegistryKeys.BLOCK,
                                                        Identifier.of(MAM.MOD_ID, "topaz_ore")))
                                        .mapColor(MapColor.ORANGE).strength(3.0f, 3.0f)
                                        .sounds(BlockSoundGroup.STONE).requiresTool(),
                        UniformIntProvider.create(3, 7)));

        public static final Block TOURMALINE_ORE = registerBlock("tourmaline_ore",
                        new GemstoneOreBlock(
                                        AbstractBlock.Settings.create().registryKey(RegistryKey.of(
                                                        RegistryKeys.BLOCK,
                                                        Identifier.of(MAM.MOD_ID,
                                                                        "tourmaline_ore")))
                                                        .mapColor(MapColor.GREEN)
                                                        .strength(3.0f, 3.0f)
                                                        .sounds(BlockSoundGroup.STONE)
                                                        .requiresTool(),
                                        UniformIntProvider.create(3, 7)));

        // Gemstone Ores - Uncommon Tier
        public static final Block CARNELIAN_ORE = registerBlock("carnelian_ore",
                        new GemstoneOreBlock(AbstractBlock.Settings.create()
                                        .registryKey(RegistryKey.of(RegistryKeys.BLOCK,
                                                        Identifier.of(MAM.MOD_ID, "carnelian_ore")))
                                        .mapColor(MapColor.ORANGE).strength(3.0f, 3.0f)
                                        .sounds(BlockSoundGroup.STONE).requiresTool(),
                                        UniformIntProvider.create(2, 5)));

        public static final Block CITRINE_ORE = registerBlock("citrine_ore", new GemstoneOreBlock(
                        AbstractBlock.Settings.create()
                                        .registryKey(RegistryKey.of(RegistryKeys.BLOCK,
                                                        Identifier.of(MAM.MOD_ID, "citrine_ore")))
                                        .mapColor(MapColor.YELLOW).strength(3.0f, 3.0f)
                                        .sounds(BlockSoundGroup.STONE).requiresTool(),
                        UniformIntProvider.create(2, 5)));

        public static final Block JADE_ORE = registerBlock("jade_ore", new GemstoneOreBlock(
                        AbstractBlock.Settings.create()
                                        .registryKey(RegistryKey.of(RegistryKeys.BLOCK,
                                                        Identifier.of(MAM.MOD_ID, "jade_ore")))
                                        .mapColor(MapColor.GREEN).strength(3.0f, 3.0f)
                                        .sounds(BlockSoundGroup.STONE).requiresTool(),
                        UniformIntProvider.create(2, 5)));

        public static final Block PERIDOT_ORE = registerBlock("peridot_ore", new GemstoneOreBlock(
                        AbstractBlock.Settings.create()
                                        .registryKey(RegistryKey.of(RegistryKeys.BLOCK,
                                                        Identifier.of(MAM.MOD_ID, "peridot_ore")))
                                        .mapColor(MapColor.LIME).strength(3.0f, 3.0f)
                                        .sounds(BlockSoundGroup.STONE).requiresTool(),
                        UniformIntProvider.create(2, 5)));

        public static final Block SODALITE_ORE = registerBlock("sodalite_ore", new GemstoneOreBlock(
                        AbstractBlock.Settings.create()
                                        .registryKey(RegistryKey.of(RegistryKeys.BLOCK,
                                                        Identifier.of(MAM.MOD_ID, "sodalite_ore")))
                                        .mapColor(MapColor.BLUE).strength(3.0f, 3.0f)
                                        .sounds(BlockSoundGroup.STONE).requiresTool(),
                        UniformIntProvider.create(2, 5)));

        // Gemstone Ores - Common Tier
        public static final Block HEMATITE_ORE = registerBlock("hematite_ore", new GemstoneOreBlock(
                        AbstractBlock.Settings.create()
                                        .registryKey(RegistryKey.of(RegistryKeys.BLOCK,
                                                        Identifier.of(MAM.MOD_ID, "hematite_ore")))
                                        .mapColor(MapColor.GRAY).strength(3.0f, 3.0f)
                                        .sounds(BlockSoundGroup.STONE).requiresTool(),
                        UniformIntProvider.create(0, 2)));

        public static final Block DEEPSLATE_HEMATITE_ORE = registerBlock("deepslate_hematite_ore",
                        new GemstoneOreBlock(
                                        AbstractBlock.Settings.create().registryKey(RegistryKey.of(
                                                        RegistryKeys.BLOCK,
                                                        Identifier.of(MAM.MOD_ID,
                                                                        "deepslate_hematite_ore")))
                                                        .mapColor(MapColor.GRAY)
                                                        .strength(4.5f, 3.0f)
                                                        .sounds(BlockSoundGroup.DEEPSLATE)
                                                        .requiresTool(),
                                        UniformIntProvider.create(0, 2)));

        // Special blocks
        public static final Block GIFT_BOX = registerBlock("gift_box",
                        new GiftBoxBlock(AbstractBlock.Settings.create()
                                        .registryKey(RegistryKey.of(RegistryKeys.BLOCK,
                                                        Identifier.of(MAM.MOD_ID, "gift_box")))
                                        .mapColor(MapColor.RED).strength(1.0f, 1.0f)
                                        .sounds(BlockSoundGroup.WOOD), false));

        // Utility blocks - TODO: Add custom textures
        public static final Block MANA_CRYSTAL_BLOCK = registerBlock("mana_crystal_block",
                        new Block(AbstractBlock.Settings.create()
                                        .registryKey(RegistryKey.of(RegistryKeys.BLOCK,
                                                        Identifier.of(MAM.MOD_ID,
                                                                        "mana_crystal_block")))
                                        .mapColor(MapColor.CYAN).strength(3.0f, 9.0f)
                                        .sounds(BlockSoundGroup.AMETHYST_BLOCK)
                                        .luminance(state -> 10)));

        public static final Block MANA_INFUSER = registerBlock("mana_infuser",
                        new Block(AbstractBlock.Settings.create()
                                        .registryKey(RegistryKey.of(RegistryKeys.BLOCK,
                                                        Identifier.of(MAM.MOD_ID, "mana_infuser")))
                                        .mapColor(MapColor.PURPLE).strength(2.0f, 6.0f)
                                        .sounds(BlockSoundGroup.METAL)));

        public static final Block MANA_LAMP = registerBlock("mana_lamp",
                        new Block(AbstractBlock.Settings.create()
                                        .registryKey(RegistryKey.of(RegistryKeys.BLOCK,
                                                        Identifier.of(MAM.MOD_ID, "mana_lamp")))
                                        .mapColor(MapColor.YELLOW).strength(1.5f, 3.0f)
                                        .sounds(BlockSoundGroup.LANTERN).luminance(state -> 15)));

        public static final Block MANA_LEY_LINE = registerBlock("mana_ley_line",
                        new Block(AbstractBlock.Settings.create()
                                        .registryKey(RegistryKey.of(RegistryKeys.BLOCK,
                                                        Identifier.of(MAM.MOD_ID, "mana_ley_line")))
                                        .mapColor(MapColor.MAGENTA).strength(1.0f, 1.0f)
                                        .sounds(BlockSoundGroup.NETHER_WOOD)));

        public static final Block MANA_NODE_BLOCK = registerBlock("mana_node_block",
                        new Block(AbstractBlock.Settings.create()
                                        .registryKey(RegistryKey.of(RegistryKeys.BLOCK,
                                                        Identifier.of(MAM.MOD_ID,
                                                                        "mana_node_block")))
                                        .mapColor(MapColor.LIGHT_BLUE).strength(2.0f, 6.0f)
                                        .sounds(BlockSoundGroup.AMETHYST_BLOCK)
                                        .luminance(state -> 12)));

        public static final Block RITUAL_CIRCLE_BLOCK = registerBlock("ritual_circle_block",
                        new Block(AbstractBlock.Settings.create()
                                        .registryKey(RegistryKey.of(RegistryKeys.BLOCK,
                                                        Identifier.of(MAM.MOD_ID,
                                                                        "ritual_circle_block")))
                                        .mapColor(MapColor.DARK_RED).strength(1.5f, 3.0f)
                                        .sounds(BlockSoundGroup.STONE)));

        public static final Block SPELL_ALTAR = registerBlock("spell_altar",
                        new Block(AbstractBlock.Settings.create()
                                        .registryKey(RegistryKey.of(RegistryKeys.BLOCK,
                                                        Identifier.of(MAM.MOD_ID, "spell_altar")))
                                        .mapColor(MapColor.TERRACOTTA_PURPLE).strength(2.0f, 6.0f)
                                        .sounds(BlockSoundGroup.STONE)));

        public static final Block SPELL_AMPLIFIER = registerBlock("spell_amplifier",
                        new Block(AbstractBlock.Settings.create()
                                        .registryKey(RegistryKey.of(RegistryKeys.BLOCK,
                                                        Identifier.of(MAM.MOD_ID,
                                                                        "spell_amplifier")))
                                        .mapColor(MapColor.BRIGHT_TEAL).strength(2.0f, 6.0f)
                                        .sounds(BlockSoundGroup.AMETHYST_BLOCK)));

        // Natural blocks - TODO: Add custom textures
        public static final Block ARCANE_TREE_LOG = registerBlock("arcane_tree_log",
                        new Block(AbstractBlock.Settings.create()
                                        .registryKey(RegistryKey.of(RegistryKeys.BLOCK,
                                                        Identifier.of(MAM.MOD_ID,
                                                                        "arcane_tree_log")))
                                        .mapColor(MapColor.BROWN).strength(2.0f, 2.0f)
                                        .sounds(BlockSoundGroup.WOOD)));

        public static final Block ARCANE_TREE_LEAVES = registerBlock("arcane_tree_leaves",
                        new Block(AbstractBlock.Settings.create()
                                        .registryKey(RegistryKey.of(RegistryKeys.BLOCK,
                                                        Identifier.of(MAM.MOD_ID,
                                                                        "arcane_tree_leaves")))
                                        .mapColor(MapColor.TEAL).strength(0.2f, 0.2f)
                                        .sounds(BlockSoundGroup.AZALEA_LEAVES).nonOpaque()));

        public static final Block ARCANE_WORKBENCH = registerBlock("arcane_workbench",
                        new Block(AbstractBlock.Settings.create()
                                        .registryKey(RegistryKey.of(RegistryKeys.BLOCK,
                                                        Identifier.of(MAM.MOD_ID,
                                                                        "arcane_workbench")))
                                        .mapColor(MapColor.BROWN).strength(2.5f, 2.5f)
                                        .sounds(BlockSoundGroup.WOOD)));

        /**
         * Register a block.
         */
        private static Block registerBlock(String name, Block block) {
                Identifier id = Identifier.of(MAM.MOD_ID, name);
                Block registered = Registry.register(Registries.BLOCK, id, block);
                BLOCKS.put(name, registered);

                return registered;
        }

        /**
         * Get a block by name.
         */
        public static Block getBlock(String name) {
                return BLOCKS.get(name);
        }

        /**
         * Get all registered blocks.
         */
        public static Map<String, Block> getAllBlocks() {
                return Map.copyOf(BLOCKS);
        }

        /**
         * Initialize all blocks. Called from MAM.onInitialize()
         */
        public static void initialize() {
                MAM.LOGGER.info("Registering {} blocks", BLOCKS.size());
        }
}
