package dk.mosberg.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

/**
 * Mana Bottle - Contains liquid mana for restoring mana pools.
 */
public class ManaBottleItem extends Item {

    public ManaBottleItem(Settings settings) {
        super(settings);
    }

    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip,
            TooltipType type) {
        tooltip.add(Text.translatable("item.mam.mana_bottle.tooltip").formatted(Formatting.BLUE));
    }
}
