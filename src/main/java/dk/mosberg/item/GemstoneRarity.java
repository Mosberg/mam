package dk.mosberg.item;

import net.minecraft.util.Formatting;

/**
 * Represents the rarity tier of a gemstone.
 */
public enum GemstoneRarity {
    COMMON("Common", Formatting.WHITE, 1), UNCOMMON("Uncommon", Formatting.GREEN, 2), RARE("Rare",
            Formatting.BLUE, 3), EPIC("Epic", Formatting.LIGHT_PURPLE, 4);

    private final String displayName;
    private final Formatting formatting;
    private final int tier;

    GemstoneRarity(String displayName, Formatting formatting, int tier) {
        this.displayName = displayName;
        this.formatting = formatting;
        this.tier = tier;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Formatting getFormatting() {
        return formatting;
    }

    public int getTier() {
        return tier;
    }
}
