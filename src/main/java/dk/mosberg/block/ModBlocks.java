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

/**
 * Registry for all mod blocks including mana crystals and ritual blocks.
 */
public class ModBlocks {
    private static final Map<String, Block> BLOCKS = new HashMap<>();

    // Mana Crystal Blocks - emit light and can be used in rituals
    public static final Block PERSONAL_MANA_CRYSTAL = registerBlock("personal_mana_crystal",
            new ManaCrystalBlock(ManaPoolType.PERSONAL, AbstractBlock.Settings.create()
                    .registryKey(RegistryKey.of(RegistryKeys.BLOCK,
                            Identifier.of(MAM.MOD_ID, "personal_mana_crystal")))
                    .mapColor(MapColor.CYAN).luminance(state -> 12).strength(3.0f, 9.0f)
                    .sounds(BlockSoundGroup.AMETHYST_BLOCK).pistonBehavior(PistonBehavior.BLOCK)));

    public static final Block AURA_MANA_CRYSTAL = registerBlock("aura_mana_crystal",
            new ManaCrystalBlock(ManaPoolType.AURA, AbstractBlock.Settings.create()
                    .registryKey(RegistryKey.of(RegistryKeys.BLOCK,
                            Identifier.of(MAM.MOD_ID, "aura_mana_crystal")))
                    .mapColor(MapColor.PURPLE).luminance(state -> 12).strength(3.0f, 9.0f)
                    .sounds(BlockSoundGroup.AMETHYST_BLOCK).pistonBehavior(PistonBehavior.BLOCK)));

    public static final Block RESERVE_MANA_CRYSTAL = registerBlock("reserve_mana_crystal",
            new ManaCrystalBlock(ManaPoolType.RESERVE, AbstractBlock.Settings.create()
                    .registryKey(RegistryKey.of(RegistryKeys.BLOCK,
                            Identifier.of(MAM.MOD_ID, "reserve_mana_crystal")))
                    .mapColor(MapColor.ORANGE).luminance(state -> 12).strength(3.0f, 9.0f)
                    .sounds(BlockSoundGroup.AMETHYST_BLOCK).pistonBehavior(PistonBehavior.BLOCK)));

    // Ritual Pedestal - central block for ritual casting
    public static final Block RITUAL_PEDESTAL = registerBlock("ritual_pedestal",
            new RitualPedestalBlock(AbstractBlock.Settings.create()
                    .registryKey(RegistryKey.of(RegistryKeys.BLOCK,
                            Identifier.of(MAM.MOD_ID, "ritual_pedestal")))
                    .mapColor(MapColor.BLACK).luminance(state -> 8).strength(5.0f, 12.0f)
                    .sounds(BlockSoundGroup.STONE).nonOpaque()
                    .pistonBehavior(PistonBehavior.BLOCK)));

    // Ritual Candles - decorative blocks that enhance rituals
    public static final Block RITUAL_CANDLE = registerBlock("ritual_candle",
            new RitualCandleBlock(AbstractBlock.Settings.create()
                    .registryKey(RegistryKey.of(RegistryKeys.BLOCK,
                            Identifier.of(MAM.MOD_ID, "ritual_candle")))
                    .mapColor(MapColor.WHITE)
                    .luminance(state -> state.get(RitualCandleBlock.LIT) ? 14 : 0).strength(0.5f)
                    .sounds(BlockSoundGroup.CANDLE).nonOpaque()));

    // Mana Infused Stones - building blocks for magical structures
    public static final Block MANA_INFUSED_STONE = registerBlock("mana_infused_stone",
            new Block(AbstractBlock.Settings.create()
                    .registryKey(RegistryKey.of(RegistryKeys.BLOCK,
                            Identifier.of(MAM.MOD_ID, "mana_infused_stone")))
                    .mapColor(MapColor.LAPIS_BLUE).luminance(state -> 4).strength(2.0f, 6.0f)
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block MANA_INFUSED_STONE_BRICKS = registerBlock("mana_infused_stone_bricks",
            new Block(AbstractBlock.Settings.create()
                    .registryKey(RegistryKey.of(RegistryKeys.BLOCK,
                            Identifier.of(MAM.MOD_ID, "mana_infused_stone_bricks")))
                    .mapColor(MapColor.LAPIS_BLUE).luminance(state -> 4).strength(2.5f, 7.0f)
                    .sounds(BlockSoundGroup.STONE)));

    // Arcane Altar - crafting block for magical items
    public static final Block ARCANE_ALTAR = registerBlock("arcane_altar",
            new ArcaneAltarBlock(AbstractBlock.Settings.create()
                    .registryKey(RegistryKey.of(RegistryKeys.BLOCK,
                            Identifier.of(MAM.MOD_ID, "arcane_altar")))
                    .mapColor(MapColor.PURPLE).luminance(state -> 10).strength(4.0f, 10.0f)
                    .sounds(BlockSoundGroup.STONE).nonOpaque()));

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
