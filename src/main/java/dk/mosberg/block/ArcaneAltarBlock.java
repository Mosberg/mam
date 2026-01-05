package dk.mosberg.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

/**
 * Arcane Altar block - used for crafting magical items and enchanting.
 */
public class ArcaneAltarBlock extends Block {
    private static final VoxelShape SHAPE =
            Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 12.0, 16.0);

    public ArcaneAltarBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos,
            ShapeContext context) {
        return SHAPE;
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player,
            BlockHitResult hit) {
        if (world.isClient) {
            return ActionResult.SUCCESS;
        }

        player.sendMessage(Text.literal("Arcane Altar").formatted(Formatting.LIGHT_PURPLE), true);
        player.sendMessage(
                Text.literal("Used for crafting magical items").formatted(Formatting.GRAY), false);

        world.playSound(null, pos, SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.BLOCKS,
                1.0f, 1.0f);

        return ActionResult.SUCCESS;
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        // Spawn enchantment glyphs
        if (random.nextInt(5) == 0) {
            for (int i = 0; i < 3; i++) {
                double x = pos.getX() + random.nextDouble();
                double y = pos.getY() + 0.75 + random.nextDouble() * 0.25;
                double z = pos.getZ() + random.nextDouble();

                world.addParticle(ParticleTypes.ENCHANT, x, y, z, (random.nextDouble() - 0.5) * 0.1,
                        random.nextDouble() * 0.1, (random.nextDouble() - 0.5) * 0.1);
            }
        }
    }
}
