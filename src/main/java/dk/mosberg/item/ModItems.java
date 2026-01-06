package dk.mosberg.item;

import java.util.HashMap;
import java.util.Map;
import dk.mosberg.MAM;
import dk.mosberg.item.MagicWandItem.WandTier;
import dk.mosberg.spell.SpellSchool;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

/**
 * Registry for all items in the mod including gemstones, wands, and spell books.
 */
public class ModItems {
        // Map to store all gemstone items
        private static final Map<GemstoneType, GemstoneItem> GEMSTONES = new HashMap<>();
        private static final Map<String, MagicWandItem> WANDS = new HashMap<>();
        private static final Map<String, SpellBookItem> SPELL_BOOKS = new HashMap<>();

        // === GEMSTONES ===
        // Individual gemstone item references for easy access
        public static final GemstoneItem RUBY = registerGemstone(GemstoneType.RUBY);
        public static final GemstoneItem SAPPHIRE = registerGemstone(GemstoneType.SAPPHIRE);
        public static final GemstoneItem TANZANITE = registerGemstone(GemstoneType.TANZANITE);

        public static final GemstoneItem APATITE = registerGemstone(GemstoneType.APATITE);
        public static final GemstoneItem AQUAMARINE = registerGemstone(GemstoneType.AQUAMARINE);
        public static final GemstoneItem MOONSTONE = registerGemstone(GemstoneType.MOONSTONE);
        public static final GemstoneItem RHODONITE = registerGemstone(GemstoneType.RHODONITE);
        public static final GemstoneItem TOPAZ = registerGemstone(GemstoneType.TOPAZ);
        public static final GemstoneItem TOURMALINE = registerGemstone(GemstoneType.TOURMALINE);

        public static final GemstoneItem CARNELIAN = registerGemstone(GemstoneType.CARNELIAN);
        public static final GemstoneItem CITRINE = registerGemstone(GemstoneType.CITRINE);
        public static final GemstoneItem JADE = registerGemstone(GemstoneType.JADE);
        public static final GemstoneItem PERIDOT = registerGemstone(GemstoneType.PERIDOT);
        public static final GemstoneItem SODALITE = registerGemstone(GemstoneType.SODALITE);

        public static final GemstoneItem HEMATITE = registerGemstone(GemstoneType.HEMATITE);

        // === MAGIC WANDS ===
        // Fire School Wands
        public static final MagicWandItem FIRE_WAND_NOVICE =
                        registerWand("fire_wand_novice", WandTier.NOVICE, SpellSchool.FIRE);
        public static final MagicWandItem FIRE_WAND_MASTER =
                        registerWand("fire_wand_master", WandTier.MASTER, SpellSchool.FIRE);

        // Ice School Wands
        public static final MagicWandItem ICE_WAND_NOVICE =
                        registerWand("ice_wand_novice", WandTier.NOVICE, SpellSchool.ICE);
        public static final MagicWandItem ICE_WAND_MASTER =
                        registerWand("ice_wand_master", WandTier.MASTER, SpellSchool.ICE);

        // Arcane School Wands
        public static final MagicWandItem ARCANE_WAND_NOVICE =
                        registerWand("arcane_wand_novice", WandTier.NOVICE, SpellSchool.ARCANE);
        public static final MagicWandItem ARCANE_WAND_MASTER =
                        registerWand("arcane_wand_master", WandTier.MASTER, SpellSchool.ARCANE);

        // Air School Wands
        public static final MagicWandItem AIR_WAND_NOVICE =
                        registerWand("air_wand_novice", WandTier.NOVICE, SpellSchool.AIR);
        public static final MagicWandItem AIR_WAND_MASTER =
                        registerWand("air_wand_master", WandTier.MASTER, SpellSchool.AIR);

        // Blood School Wands
        public static final MagicWandItem BLOOD_WAND_NOVICE =
                        registerWand("blood_wand_novice", WandTier.NOVICE, SpellSchool.BLOOD);
        public static final MagicWandItem BLOOD_WAND_MASTER =
                        registerWand("blood_wand_master", WandTier.MASTER, SpellSchool.BLOOD);

        // Chaos School Wands
        public static final MagicWandItem CHAOS_WAND_NOVICE =
                        registerWand("chaos_wand_novice", WandTier.NOVICE, SpellSchool.CHAOS);
        public static final MagicWandItem CHAOS_WAND_MASTER =
                        registerWand("chaos_wand_master", WandTier.MASTER, SpellSchool.CHAOS);

        // Dark School Wands
        public static final MagicWandItem DARK_WAND_NOVICE =
                        registerWand("dark_wand_novice", WandTier.NOVICE, SpellSchool.DARK);
        public static final MagicWandItem DARK_WAND_MASTER =
                        registerWand("dark_wand_master", WandTier.MASTER, SpellSchool.DARK);

        // Earth School Wands
        public static final MagicWandItem EARTH_WAND_NOVICE =
                        registerWand("earth_wand_novice", WandTier.NOVICE, SpellSchool.EARTH);
        public static final MagicWandItem EARTH_WAND_MASTER =
                        registerWand("earth_wand_master", WandTier.MASTER, SpellSchool.EARTH);

        // Light School Wands
        public static final MagicWandItem LIGHT_WAND_NOVICE =
                        registerWand("light_wand_novice", WandTier.NOVICE, SpellSchool.LIGHT);
        public static final MagicWandItem LIGHT_WAND_MASTER =
                        registerWand("light_wand_master", WandTier.MASTER, SpellSchool.LIGHT);

        // Nature School Wands
        public static final MagicWandItem NATURE_WAND_NOVICE =
                        registerWand("nature_wand_novice", WandTier.NOVICE, SpellSchool.NATURE);
        public static final MagicWandItem NATURE_WAND_MASTER =
                        registerWand("nature_wand_master", WandTier.MASTER, SpellSchool.NATURE);

        // Thunder School Wands
        public static final MagicWandItem THUNDER_WAND_NOVICE =
                        registerWand("thunder_wand_novice", WandTier.NOVICE, SpellSchool.THUNDER);
        public static final MagicWandItem THUNDER_WAND_MASTER =
                        registerWand("thunder_wand_master", WandTier.MASTER, SpellSchool.THUNDER);

        // Void School Wands
        public static final MagicWandItem VOID_WAND_NOVICE =
                        registerWand("void_wand_novice", WandTier.NOVICE, SpellSchool.VOID);
        public static final MagicWandItem VOID_WAND_MASTER =
                        registerWand("void_wand_master", WandTier.MASTER, SpellSchool.VOID);

        // Water School Wands
        public static final MagicWandItem WATER_WAND_NOVICE =
                        registerWand("water_wand_novice", WandTier.NOVICE, SpellSchool.WATER);
        public static final MagicWandItem WATER_WAND_MASTER =
                        registerWand("water_wand_master", WandTier.MASTER, SpellSchool.WATER);

        // === SPELL BOOKS ===
        public static final SpellBookItem AIR_SPELL_BOOK =
                        registerSpellBook("air_spell_book", SpellSchool.AIR);
        public static final SpellBookItem ARCANE_SPELL_BOOK =
                        registerSpellBook("arcane_spell_book", SpellSchool.ARCANE);
        public static final SpellBookItem BLOOD_SPELL_BOOK =
                        registerSpellBook("blood_spell_book", SpellSchool.BLOOD);
        public static final SpellBookItem CHAOS_SPELL_BOOK =
                        registerSpellBook("chaos_spell_book", SpellSchool.CHAOS);
        public static final SpellBookItem DARK_SPELL_BOOK =
                        registerSpellBook("dark_spell_book", SpellSchool.DARK);
        public static final SpellBookItem EARTH_SPELL_BOOK =
                        registerSpellBook("earth_spell_book", SpellSchool.EARTH);
        public static final SpellBookItem FIRE_SPELL_BOOK =
                        registerSpellBook("fire_spell_book", SpellSchool.FIRE);
        public static final SpellBookItem ICE_SPELL_BOOK =
                        registerSpellBook("ice_spell_book", SpellSchool.ICE);
        public static final SpellBookItem LIGHT_SPELL_BOOK =
                        registerSpellBook("light_spell_book", SpellSchool.LIGHT);
        public static final SpellBookItem NATURE_SPELL_BOOK =
                        registerSpellBook("nature_spell_book", SpellSchool.NATURE);
        public static final SpellBookItem THUNDER_SPELL_BOOK =
                        registerSpellBook("thunder_spell_book", SpellSchool.THUNDER);
        public static final SpellBookItem VOID_SPELL_BOOK =
                        registerSpellBook("void_spell_book", SpellSchool.VOID);
        public static final SpellBookItem WATER_SPELL_BOOK =
                        registerSpellBook("water_spell_book", SpellSchool.WATER);

        // === MAGICAL ITEMS ===
        public static final Item MANA_BOTTLE = registerItem("mana_bottle",
                        new ManaBottleItem(new Item.Settings()
                                        .registryKey(RegistryKey.of(RegistryKeys.ITEM,
                                                        Identifier.of(MAM.MOD_ID, "mana_bottle")))
                                        .maxCount(16)));
        public static final Item MANA_SHARD = registerItem("mana_shard",
                        new ManaShardItem(new Item.Settings()
                                        .registryKey(RegistryKey.of(RegistryKeys.ITEM,
                                                        Identifier.of(MAM.MOD_ID, "mana_shard")))
                                        .maxCount(64)));
        public static final Item ESSENCE_VIAL = registerItem("essence_vial",
                        new EssenceVialItem(new Item.Settings()
                                        .registryKey(RegistryKey.of(RegistryKeys.ITEM,
                                                        Identifier.of(MAM.MOD_ID, "essence_vial")))
                                        .maxCount(16)));
        public static final Item RITUAL_FOCUS = registerItem("ritual_focus",
                        new RitualFocusItem(new Item.Settings()
                                        .registryKey(RegistryKey.of(RegistryKeys.ITEM,
                                                        Identifier.of(MAM.MOD_ID, "ritual_focus")))
                                        .maxCount(1)));
        public static final Item SPELL_COMPONENT = registerItem("spell_component",
                        new SpellComponentItem(new Item.Settings()
                                        .registryKey(RegistryKey.of(RegistryKeys.ITEM,
                                                        Identifier.of(MAM.MOD_ID,
                                                                        "spell_component")))
                                        .maxCount(64)));
        public static final Item SPELL_SCROLL = registerItem("spell_scroll",
                        new SpellScrollItem(new Item.Settings()
                                        .registryKey(RegistryKey.of(RegistryKeys.ITEM,
                                                        Identifier.of(MAM.MOD_ID, "spell_scroll")))
                                        .maxCount(16)));
        public static final Item GRIMOIRE = registerItem("grimoire",
                        new GrimoireItem(new Item.Settings()
                                        .registryKey(RegistryKey.of(RegistryKeys.ITEM,
                                                        Identifier.of(MAM.MOD_ID, "grimoire")))
                                        .maxCount(1)));
        public static final Item SPELL_TOME = registerItem("spell_tome",
                        new SpellTomeItem(new Item.Settings()
                                        .registryKey(RegistryKey.of(RegistryKeys.ITEM,
                                                        Identifier.of(MAM.MOD_ID, "spell_tome")))
                                        .maxCount(1)));
        public static final Item CATALYST_ARTIFACT = registerItem("catalyst_artifact",
                        new CatalystArtifactItem(new Item.Settings()
                                        .registryKey(RegistryKey.of(RegistryKeys.ITEM,
                                                        Identifier.of(MAM.MOD_ID,
                                                                        "catalyst_artifact")))
                                        .maxCount(1)));
        public static final Item ENCHANTED_GEM = registerItem("enchanted_gem",
                        new EnchantedGemItem(new Item.Settings()
                                        .registryKey(RegistryKey.of(RegistryKeys.ITEM,
                                                        Identifier.of(MAM.MOD_ID, "enchanted_gem")))
                                        .maxCount(16)));

        // === BLOCK ITEMS ===
        // Gemstone Ore Block Items - Epic Tier
        public static final Item RUBY_ORE = registerBlockItem("ruby_ore");
        public static final Item DEEPSLATE_RUBY_ORE = registerBlockItem("deepslate_ruby_ore");
        public static final Item SAPPHIRE_ORE = registerBlockItem("sapphire_ore");
        public static final Item DEEPSLATE_SAPPHIRE_ORE =
                        registerBlockItem("deepslate_sapphire_ore");
        public static final Item TANZANITE_ORE = registerBlockItem("tanzanite_ore");
        public static final Item DEEPSLATE_TANZANITE_ORE =
                        registerBlockItem("deepslate_tanzanite_ore");

        // Gemstone Ore Block Items - Rare Tier
        public static final Item APATITE_ORE = registerBlockItem("apatite_ore");
        public static final Item AQUAMARINE_ORE = registerBlockItem("aquamarine_ore");
        public static final Item MOONSTONE_ORE = registerBlockItem("moonstone_ore");
        public static final Item RHODONITE_ORE = registerBlockItem("rhodonite_ore");
        public static final Item TOPAZ_ORE = registerBlockItem("topaz_ore");
        public static final Item TOURMALINE_ORE = registerBlockItem("tourmaline_ore");

        // Gemstone Ore Block Items - Uncommon Tier
        public static final Item CARNELIAN_ORE = registerBlockItem("carnelian_ore");
        public static final Item CITRINE_ORE = registerBlockItem("citrine_ore");
        public static final Item JADE_ORE = registerBlockItem("jade_ore");
        public static final Item PERIDOT_ORE = registerBlockItem("peridot_ore");
        public static final Item SODALITE_ORE = registerBlockItem("sodalite_ore");

        // Gemstone Ore Block Items - Common Tier
        public static final Item HEMATITE_ORE = registerBlockItem("hematite_ore");
        public static final Item DEEPSLATE_HEMATITE_ORE =
                        registerBlockItem("deepslate_hematite_ore");

        /**
         * Register a gemstone item with appropriate settings based on its rarity.
         */
        private static GemstoneItem registerGemstone(GemstoneType type) {
                Identifier id = Identifier.of(MAM.MOD_ID, type.getId());
                RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, id);

                // Set max stack size based on rarity
                int maxCount = switch (type.getRarity()) {
                        case EPIC -> 16;
                        case RARE -> 32;
                        case UNCOMMON, COMMON -> 64;
                };

                Item.Settings settings = new Item.Settings().registryKey(key).maxCount(maxCount);

                GemstoneItem item = Registry.register(Registries.ITEM, key,
                                new GemstoneItem(type, settings));
                GEMSTONES.put(type, item);

                return item;
        }

        /**
         * Register a magic wand item.
         */
        private static MagicWandItem registerWand(String name, WandTier tier, SpellSchool school) {
                Identifier id = Identifier.of(MAM.MOD_ID, name);
                RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, id);

                Item.Settings settings = new Item.Settings().registryKey(key).maxCount(1)
                                .maxDamage(tier.getDurability());

                MagicWandItem wand = Registry.register(Registries.ITEM, key,
                                new MagicWandItem(tier, school, settings));
                WANDS.put(name, wand);

                return wand;
        }

        /**
         * Register a spell book item.
         */
        private static SpellBookItem registerSpellBook(String name, SpellSchool school) {
                Identifier id = Identifier.of(MAM.MOD_ID, name);
                RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, id);

                Item.Settings settings = new Item.Settings().registryKey(key).maxCount(16);

                SpellBookItem book = Registry.register(Registries.ITEM, key,
                                new SpellBookItem(school, settings));
                SPELL_BOOKS.put(name, book);

                return book;
        }

        /**
         * Register a generic item.
         */
        private static Item registerItem(String name, Item item) {
                Identifier id = Identifier.of(MAM.MOD_ID, name);
                return Registry.register(Registries.ITEM, id, item);
        }

        /**
         * * Register a block item.
         */
        private static Item registerBlockItem(String name) {
                Identifier id = Identifier.of(MAM.MOD_ID, name);
                RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, id);

                Block block = dk.mosberg.block.ModBlocks.getBlock(name);
                if (block == null) {
                        MAM.LOGGER.warn("Block {} not found when registering block item", name);
                        throw new IllegalStateException("Block " + name + " not found");
                }

                Item.Settings settings = new Item.Settings().registryKey(key);
                return Registry.register(Registries.ITEM, key,
                                new net.minecraft.item.BlockItem(block, settings));
        }

        /**
         * * Get a gemstone item by its type.
         *
         * @param type The gemstone type
         * @return The gemstone item, or null if not found
         */
        public static GemstoneItem getGemstone(GemstoneType type) {
                return GEMSTONES.get(type);
        }

        /**
         * Get all registered gemstone items.
         *
         * @return Unmodifiable map of gemstone types to items
         */
        public static Map<GemstoneType, GemstoneItem> getAllGemstones() {
                return Map.copyOf(GEMSTONES);
        }

        /**
         * Get all registered wands.
         */
        public static Map<String, MagicWandItem> getAllWands() {
                return Map.copyOf(WANDS);
        }

        /**
         * Get all registered spell books.
         */
        public static Map<String, SpellBookItem> getAllSpellBooks() {
                return Map.copyOf(SPELL_BOOKS);
        }

        /**
         * Check if a gemstone type is registered.
         */
        public static boolean hasGemstone(GemstoneType type) {
                return GEMSTONES.containsKey(type);
        }

        /**
         * Initialize all items. Called from MAM.onInitialize()
         */
        public static void initialize() {
                MAM.LOGGER.info("Registering {} gemstone items", GEMSTONES.size());
                MAM.LOGGER.info("Registering {} wands", WANDS.size());
                MAM.LOGGER.info("Registering {} spell books", SPELL_BOOKS.size());
        }
}

