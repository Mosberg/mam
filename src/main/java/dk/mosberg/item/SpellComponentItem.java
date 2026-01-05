package dk.mosberg.item;

import java.util.List;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

/**
 * Spell Component - Generic crafting material for spells and magical items.
 */
public class SpellComponentItem extends Item {

    public SpellComponentItem(Settings settings) {
        super(settings);
    }

    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip,
            TooltipType type) {
        tooltip.add(
                Text.translatable("item.mam.spell_component.tooltip").formatted(Formatting.YELLOW));
    }
}
