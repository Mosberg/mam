package dk.mosberg.item;

import java.util.HashMap;
import java.util.Map;
import dk.mosberg.MAM;
import dk.mosberg.item.MagicWandItem.WandTier;
import dk.mosberg.spell.SpellSchool;
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

    // === SPELL BOOKS ===
    public static final SpellBookItem FIRE_SPELL_BOOK =
            registerSpellBook("fire_spell_book", SpellSchool.FIRE);
    public static final SpellBookItem ICE_SPELL_BOOK =
            registerSpellBook("ice_spell_book", SpellSchool.ICE);
    public static final SpellBookItem ARCANE_SPELL_BOOK =
            registerSpellBook("arcane_spell_book", SpellSchool.ARCANE);
    public static final SpellBookItem NATURE_SPELL_BOOK =
            registerSpellBook("nature_spell_book", SpellSchool.NATURE);
    public static final SpellBookItem DARK_SPELL_BOOK =
            registerSpellBook("dark_spell_book", SpellSchool.DARK);
    public static final SpellBookItem LIGHT_SPELL_BOOK =
            registerSpellBook("light_spell_book", SpellSchool.LIGHT);

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

        GemstoneItem item =
                Registry.register(Registries.ITEM, key, new GemstoneItem(type, settings));
        GEMSTONES.put(type, item);

        return item;
    }

    /**
     * Register a magic wand item.
     */
    private static MagicWandItem registerWand(String name, WandTier tier, SpellSchool school) {
        Identifier id = Identifier.of(MAM.MOD_ID, name);
        RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, id);

        Item.Settings settings =
                new Item.Settings().registryKey(key).maxCount(1).maxDamage(tier.getDurability());

        MagicWandItem wand =
                Registry.register(Registries.ITEM, key, new MagicWandItem(tier, school, settings));
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

        SpellBookItem book =
                Registry.register(Registries.ITEM, key, new SpellBookItem(school, settings));
        SPELL_BOOKS.put(name, book);

        return book;
    }

    /**
     * Get a gemstone item by its type.
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

