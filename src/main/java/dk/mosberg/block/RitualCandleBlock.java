package dk.mosberg.block;

import java.util.List;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.AbstractCandleBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

/**
 * Ritual Candle block - decorative lighting that enhances ritual magic. Can be lit or extinguished
 * by players.
 */
public class RitualCandleBlock extends AbstractCandleBlock {
    public static final MapCodec<RitualCandleBlock> CODEC = createCodec(RitualCandleBlock::new);
    public static final BooleanProperty LIT = BooleanProperty.of("lit");

    @Override
    protected MapCodec<? extends AbstractCandleBlock> getCodec() {
        return CODEC;
    }

    public RitualCandleBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(LIT, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(LIT);
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player,
            BlockHitResult hit) {
        if (world.isClient()) {
            return ActionResult.SUCCESS;
        }

        // Toggle lit state
        boolean lit = !state.get(LIT);
        world.setBlockState(pos, state.with(LIT, lit));

        world.playSound(null, pos,
                lit ? SoundEvents.ITEM_FLINTANDSTEEL_USE : SoundEvents.BLOCK_CANDLE_EXTINGUISH,
                SoundCategory.BLOCKS, 1.0f, 1.0f);

        return ActionResult.SUCCESS;
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (state.get(LIT) && world.isClient()) {
            // Particle spawning handled by client code due to split source sets
        }
    }

    @Override
    protected Iterable<Vec3d> getParticleOffsets(BlockState state) {
        return List.of(new Vec3d(0.5, 0.7, 0.5));
    }
}
