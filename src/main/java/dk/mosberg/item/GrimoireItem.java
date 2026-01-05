package dk.mosberg.item;

import java.util.List;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

/**
 * Grimoire - Advanced spell book containing powerful spells.
 */
public class GrimoireItem extends Item {

    public GrimoireItem(Settings settings) {
        super(settings);
    }

    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip,
            TooltipType type) {
        tooltip.add(
                Text.translatable("item.mam.grimoire.tooltip").formatted(Formatting.DARK_PURPLE));
    }
}
