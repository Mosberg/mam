package dk.mosberg.spell;

/**
 * Represents the different ways a spell can be cast.
 */
public enum CastType {
    PROJECTILE("projectile"), AOE("aoe"), UTILITY("utility"), RITUAL("ritual"), SYNERGY("synergy");

    private final String id;

    CastType(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public static CastType fromId(String id) {
        for (CastType type : values()) {
            if (type.id.equals(id)) {
                return type;
            }
        }
        return UTILITY; // Default fallback
    }
}
