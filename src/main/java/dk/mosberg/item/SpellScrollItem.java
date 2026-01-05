package dk.mosberg.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

/**
 * Spell Scroll - Single-use scroll containing a spell.
 */
public class SpellScrollItem extends Item {

    public SpellScrollItem(Settings settings) {
        super(settings);
    }

    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip,
            TooltipType type) {
        tooltip.add(Text.translatable("item.mam.spell_scroll.tooltip").formatted(Formatting.WHITE));
    }
}
