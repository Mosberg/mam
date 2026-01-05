package dk.mosberg.spell;

/**
 * Represents the 13 spell schools in the magic system. Each school has a distinct color and focus.
 */
public enum SpellSchool {
    AIR("air", 0xC0C0C0, "Mobility & Speed"), ARCANE("arcane", 0x9966CC,
            "Utility & Manipulation"), BLOOD("blood", 0x8B0000, "Sacrifice for Power"), CHAOS(
                    "chaos", 0xFF00FF, "Unpredictable Effects"), DARK("dark", 0x2D1B4E,
                            "DOT & Curses"), EARTH("earth", 0x8B4513, "Defense & Stability"), FIRE(
                                    "fire", 0xFF4500, "Damage & Destruction"), ICE("ice", 0x00FFFF,
                                            "Control & Freezing"), LIGHT("light", 0xFFF8DC,
                                                    "Holy Power & Protection"), NATURE("nature",
                                                            0x228B22, "Growth & Life"), THUNDER(
                                                                    "thunder", 0xFFD700,
                                                                    "Burst Damage & Energy"), VOID(
                                                                            "void", 0x000000,
                                                                            "Dimensional Magic"), WATER(
                                                                                    "water",
                                                                                    0x1E90FF,
                                                                                    "Healing & Purification");

    private final String id;
    private final int color;
    private final String focus;

    SpellSchool(String id, int color, String focus) {
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

    public String getDisplayName() {
        return switch (this) {
            case AIR -> "Air";
            case ARCANE -> "Arcane";
            case BLOOD -> "Blood";
            case CHAOS -> "Chaos";
            case DARK -> "Dark";
            case EARTH -> "Earth";
            case FIRE -> "Fire";
            case ICE -> "Ice";
            case LIGHT -> "Light";
            case NATURE -> "Nature";
            case THUNDER -> "Thunder";
            case VOID -> "Void";
            case WATER -> "Water";
        };
    }

    public static SpellSchool fromId(String id) {
        for (SpellSchool school : values()) {
            if (school.id.equals(id)) {
                return school;
            }
        }
        return null;
    }
}
