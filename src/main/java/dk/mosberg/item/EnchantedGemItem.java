package dk.mosberg.item;

import java.util.List;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

/**
 * Enchanted Gem - A gemstone infused with magical energy.
 */
public class EnchantedGemItem extends Item {

    public EnchantedGemItem(Settings settings) {
        super(settings);
    }

    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip,
            TooltipType type) {
        tooltip.add(Text.translatable("item.mam.enchanted_gem.tooltip").formatted(Formatting.AQUA,
                Formatting.ITALIC));
    }
}
