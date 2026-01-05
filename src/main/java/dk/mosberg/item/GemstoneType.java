package dk.mosberg.item;

/**
 * Represents the 15 gemstone types in the magic system. Each gemstone has a tier, color, shape,
 * affinity, and bindings to spell schools and ritual categories.
 */
public enum GemstoneType {
    // Epic Tier
    RUBY("ruby", GemstoneRarity.EPIC, 0xE63946, "Round", "Fire"), SAPPHIRE("sapphire",
            GemstoneRarity.EPIC, 0x2952A3, "Round",
            "Ice"), TANZANITE("tanzanite", GemstoneRarity.EPIC, 0x6B4B9E, "Princess", "Void"),

    // Rare Tier
    APATITE("apatite", GemstoneRarity.RARE, 0x2DD4DB, "Round", "Water"), AQUAMARINE("aquamarine",
            GemstoneRarity.RARE, 0x7DD3E8, "Diamond", "Water"), MOONSTONE("moonstone",
                    GemstoneRarity.RARE, 0xE8E5E0, "Oval", "Lunar"), RHODONITE("rhodonite",
                            GemstoneRarity.RARE, 0xD66B88, "Round", "Healing"), TOPAZ("topaz",
                                    GemstoneRarity.RARE, 0xD98736, "Oval",
                                    "Solar"), TOURMALINE("tourmaline", GemstoneRarity.RARE,
                                            0x3A7C59, "Round", "Balance"),

    // Uncommon Tier
    CARNELIAN("carnelian", GemstoneRarity.UNCOMMON, 0xE86938, "Round", "Fire"), CITRINE("citrine",
            GemstoneRarity.UNCOMMON, 0xF4B942, "Octagon",
            "Light"), JADE("jade", GemstoneRarity.UNCOMMON, 0x5FA777, "Round", "Nature"), PERIDOT(
                    "peridot", GemstoneRarity.UNCOMMON, 0xA4D65E, "Round", "Nature"), SODALITE(
                            "sodalite", GemstoneRarity.UNCOMMON, 0x3D5A9C, "Round", "Mind"),

    // Common Tier
    HEMATITE("hematite", GemstoneRarity.COMMON, 0x5A5A5A, "Round", "Earth");

    private final String id;
    private final GemstoneRarity rarity;
    private final int color;
    private final String shape;
    private final String affinity;

    GemstoneType(String id, GemstoneRarity rarity, int color, String shape, String affinity) {
        this.id = id;
        this.rarity = rarity;
        this.color = color;
        this.shape = shape;
        this.affinity = affinity;
    }

    public String getId() {
        return id;
    }

    public GemstoneRarity getRarity() {
        return rarity;
    }

    public int getColor() {
        return color;
    }

    public String getShape() {
        return shape;
    }

    public String getAffinity() {
        return affinity;
    }

    public static GemstoneType fromId(String id) {
        for (GemstoneType type : values()) {
            if (type.id.equals(id)) {
                return type;
            }
        }
        return null;
    }
}
