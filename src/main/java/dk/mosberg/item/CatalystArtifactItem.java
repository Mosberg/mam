package dk.mosberg.item;

import java.util.List;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

/**
 * Catalyst Artifact - Powerful artifact that enhances spell casting.
 */
public class CatalystArtifactItem extends Item {

    public CatalystArtifactItem(Settings settings) {
        super(settings);
    }

    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip,
            TooltipType type) {
        tooltip.add(Text.translatable("item.mam.catalyst_artifact.tooltip")
                .formatted(Formatting.GOLD, Formatting.BOLD));
    }
}
