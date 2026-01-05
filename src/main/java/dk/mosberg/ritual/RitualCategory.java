package dk.mosberg.ritual;

/**
 * Represents the 13 ritual categories in the magic system. Each category has a distinct color and
 * focus.
 */
public enum RitualCategory {
    ASCENSION("ascension", 0xFFD700, "Transcendence & Empowerment"), CIRCLE("circle", 0xFFFFFF,
            "Bounded Magic & Protection"), COSMIC("cosmic", 0x4B0082,
                    "Celestial Alignment"), ELEMENTAL("elemental", 0xFF6B6B,
                            "Multi-Element Fusion"), FOUNTAIN("fountain", 0x00CED1,
                                    "Continuous Flow"), PLANAR("planar", 0xC0C0C0,
                                            "Dimension Manipulation"), REALITY("reality", 0xE0E0E0,
                                                    "World Alteration"), RESURRECTION(
                                                            "resurrection", 0xFFF9E3,
                                                            "Life Restoration"), SACRIFICE(
                                                                    "sacrifice", 0xDC143C,
                                                                    "Power through Offering"), SUMMONING(
                                                                            "summoning", 0x6A0DAD,
                                                                            "Entity Calling"), TEMPORAL(
                                                                                    "temporal",
                                                                                    0xCD7F32,
                                                                                    "Time Manipulation"), TRANSFORMATION(
                                                                                            "transformation",
                                                                                            0x8F00FF,
                                                                                            "Form Alteration"), VORTEX(
                                                                                                    "vortex",
                                                                                                    0x708090,
                                                                                                    "Spiraling Force");

    private final String id;
    private final int color;
    private final String focus;

    RitualCategory(String id, int color, String focus) {
        this.id = id;
        this.color = color;
        this.focus = focus;
    }

    public String getId() {
        return id;
    }

    public int getColor() {
        return color;
    }

    public String getFocus() {
        return focus;
    }

    public static RitualCategory fromId(String id) {
        for (RitualCategory category : values()) {
            if (category.id.equals(id)) {
                return category;
            }
        }
        return null;
    }
}
