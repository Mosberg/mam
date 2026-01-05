package dk.mosberg.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
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

            // TODO: Add loot table logic here
            // For now, just remove the block
            world.removeBlock(pos, false);

            return ActionResult.SUCCESS;
        }
        return ActionResult.SUCCESS;
    }

    public boolean isOpen() {
        return isOpen;
    }
}
