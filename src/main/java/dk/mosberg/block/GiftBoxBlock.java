package dk.mosberg.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Gift Box Block - Can be opened to receive items.
 */
public class GiftBoxBlock extends Block {
    private final boolean isOpen;

    public GiftBoxBlock(Settings settings, boolean isOpen) {
        super(settings);
        this.isOpen = isOpen;
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player,
            BlockHitResult hit) {
        if (!world.isClient() && !isOpen) {
            // Open the gift box
            world.playSound(null, pos, SoundEvents.BLOCK_CHEST_OPEN, SoundCategory.BLOCKS, 1.0f,
                    1.0f);

            // Drop random gift items (placeholder until loot tables are added)
            int itemCount = world.getRandom().nextInt(3) + 1;
            for (int i = 0; i < itemCount; i++) {
                ItemStack gift = switch (world.getRandom().nextInt(5)) {
                    case 0 -> new ItemStack(Items.DIAMOND, world.getRandom().nextInt(3) + 1);
                    case 1 -> new ItemStack(Items.EMERALD, world.getRandom().nextInt(5) + 1);
                    case 2 -> new ItemStack(Items.GOLD_INGOT, world.getRandom().nextInt(8) + 1);
                    case 3 -> new ItemStack(Items.ENCHANTED_BOOK);
                    default -> new ItemStack(Items.EXPERIENCE_BOTTLE,
                            world.getRandom().nextInt(10) + 1);
                };
                Block.dropStack(world, pos, gift);
            }

            world.removeBlock(pos, false);
            return ActionResult.SUCCESS;
        }
        return ActionResult.SUCCESS;
    }

    public boolean isOpen() {
        return isOpen;
    }
}
