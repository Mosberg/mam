package dk.mosberg.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

/**
 * Essence Vial - Contains magical essence extracted from spells.
 */
public class EssenceVialItem extends Item {

    public EssenceVialItem(Settings settings) {
        super(settings);
    }

    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip,
            TooltipType type) {
        tooltip.add(Text.translatable("item.mam.essence_vial.tooltip")
                .formatted(Formatting.LIGHT_PURPLE));
    }
}
