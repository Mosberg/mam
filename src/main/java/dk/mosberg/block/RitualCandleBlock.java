package dk.mosberg.block;

import java.util.List;
import net.minecraft.block.AbstractCandleBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
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
    public static final BooleanProperty LIT = BooleanProperty.of("lit");

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
        if (world.isClient) {
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
        if (state.get(LIT)) {
            // Spawn flame particles
            double x = pos.getX() + 0.5;
            double y = pos.getY() + 0.7;
            double z = pos.getZ() + 0.5;

            world.addParticle(ParticleTypes.SMALL_FLAME, x, y, z, 0, 0, 0);

            if (random.nextInt(10) == 0) {
                world.addParticle(ParticleTypes.SMOKE, x, y, z, 0, 0.01, 0);
            }
        }
    }

    @Override
    protected Iterable<Vec3d> getParticleOffsets(BlockState state) {
        return List.of(new Vec3d(0.5, 0.7, 0.5));
    }
}
