package dk.mosberg.item;

import java.util.HashMap;
import java.util.Map;
import dk.mosberg.MAM;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

/**
 * Registry for all gemstone items in the mod.
 */
public class ModItems {
    // Map to store all gemstone items
    private static final Map<GemstoneType, GemstoneItem> GEMSTONES = new HashMap<>();

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
     * Get a gemstone item by its type.
     */
    public static GemstoneItem getGemstone(GemstoneType type) {
        return GEMSTONES.get(type);
    }

    /**
     * Initialize all items. Called from MAM.onInitialize()
     */
    public static void initialize() {
        MAM.LOGGER.info("Registering {} gemstone items", GEMSTONES.size());
    }
}
