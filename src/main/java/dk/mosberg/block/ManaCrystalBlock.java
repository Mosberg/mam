package dk.mosberg.block;

import dk.mosberg.mana.ManaPoolType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

/**
 * Mana Crystal Block that stores and radiates magical energy. Can be used in rituals and provides a
 * visual mana storage.
 */
public class ManaCrystalBlock extends Block {
    private final ManaPoolType poolType;

    public ManaCrystalBlock(ManaPoolType poolType, Settings settings) {
        super(settings);
        this.poolType = poolType;
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player,
            BlockHitResult hit) {
        if (world.isClient()) {
            return ActionResult.SUCCESS;
        }

        // Display mana crystal information
        player.sendMessage(Text.literal("Mana Crystal: " + poolType.getDisplayName())
                .formatted(Formatting.AQUA), true);

        world.playSound(null, pos, SoundEvents.BLOCK_AMETHYST_BLOCK_CHIME, SoundCategory.BLOCKS,
                1.0f, 1.0f);

        return ActionResult.SUCCESS;
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (random.nextInt(10) == 0 && world.isClient()) {
            // Spawn particles - randomDisplayTick is called client-side only
            // Note: Due to split source sets, particle spawning handled by client code
        }
    }

    public ManaPoolType getPoolType() {
        return poolType;
    }
}
