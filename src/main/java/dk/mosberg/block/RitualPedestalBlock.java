package dk.mosberg.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

/**
 * Ritual Pedestal block - central piece for ritual casting. Can store items and acts as the focus
 * point for ritual magic.
 */
public class RitualPedestalBlock extends Block {
    public static final BooleanProperty ACTIVATED = BooleanProperty.of("activated");
    private static final VoxelShape SHAPE =
            Block.createCuboidShape(2.0, 0.0, 2.0, 14.0, 14.0, 14.0);

    public RitualPedestalBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(ACTIVATED, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(ACTIVATED);
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos,
            ShapeContext context) {
        return SHAPE;
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player,
            BlockHitResult hit) {
        if (world.isClient()) {
            return ActionResult.SUCCESS;
        }

        // Toggle activation state
        boolean activated = !state.get(ACTIVATED);
        world.setBlockState(pos, state.with(ACTIVATED, activated));

        player.sendMessage(
                Text.literal("Ritual Pedestal " + (activated ? "Activated" : "Deactivated"))
                        .formatted(activated ? Formatting.GREEN : Formatting.GRAY),
                true);

        world.playSound(null, pos, SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.BLOCKS,
                1.0f, activated ? 1.2f : 0.8f);

        return ActionResult.SUCCESS;
    }
}
