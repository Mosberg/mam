package dk.mosberg.item;

import java.util.List;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

/**
 * Item class for gemstones that players can collect and use in spells/rituals.
 */
public class GemstoneItem extends Item {
    private final GemstoneType gemstoneType;

    public GemstoneItem(GemstoneType type, Settings settings) {
        super(settings);
        this.gemstoneType = type;
    }

    public GemstoneType getGemstoneType() {
        return gemstoneType;
    }

    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip,
            TooltipType type) {

        // Add rarity information
        tooltip.add(Text.literal(gemstoneType.getRarity().getDisplayName())
                .formatted(gemstoneType.getRarity().getFormatting()));

        // Add affinity information
        tooltip.add(
                Text.literal("Affinity: " + gemstoneType.getAffinity()).formatted(Formatting.GRAY));

        // Add shape information
        tooltip.add(
                Text.literal("Shape: " + gemstoneType.getShape()).formatted(Formatting.DARK_GRAY));
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        // Epic and Rare gems have enchantment glint
        return gemstoneType.getRarity() == GemstoneRarity.EPIC
                || gemstoneType.getRarity() == GemstoneRarity.RARE;
    }
}
