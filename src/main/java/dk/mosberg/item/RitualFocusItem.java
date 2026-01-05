package dk.mosberg.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

/**
 * Ritual Focus - Used to focus ritual energy and improve ritual outcomes.
 */
public class RitualFocusItem extends Item {

    public RitualFocusItem(Settings settings) {
        super(settings);
    }

    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip,
            TooltipType type) {
        tooltip.add(Text.translatable("item.mam.ritual_focus.tooltip").formatted(Formatting.GOLD));
    }
}
