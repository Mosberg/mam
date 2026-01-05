package dk.mosberg.item;

import java.util.List;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

/**
 * Mana Shard - Crafting component for magical items.
 */
public class ManaShardItem extends Item {

    public ManaShardItem(Settings settings) {
        super(settings);
    }

    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip,
            TooltipType type) {
        tooltip.add(Text.translatable("item.mam.mana_shard.tooltip").formatted(Formatting.AQUA));
    }
}
