package dk.mosberg.mana;

/**
 * Represents the different types of mana pools.
 */
public enum ManaPoolType {
    PERSONAL("personal"), AURA("aura"), RESERVE("reserve");

    private final String id;

    ManaPoolType(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public double getMaxPool() {
        return switch (this) {
            case PERSONAL -> ManaConfig.getPersonalMaxPool();
            case AURA -> ManaConfig.getAuraMaxPool();
            case RESERVE -> ManaConfig.getReserveMaxPool();
        };
    }

    public double getRegenRate() {
        return switch (this) {
            case PERSONAL -> ManaConfig.getPersonalRegenRate();
            case AURA -> ManaConfig.getAuraRegenRate();
            case RESERVE -> ManaConfig.getReserveRegenRate();
        };
    }

    public String getDisplayName() {
        return switch (this) {
            case PERSONAL -> "Personal";
            case AURA -> "Aura";
            case RESERVE -> "Reserve";
        };
    }
}
