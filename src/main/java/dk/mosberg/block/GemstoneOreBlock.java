package dk.mosberg.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.intprovider.UniformIntProvider;

/**
 * Gemstone Ore Block - Drops gemstones when mined.
 */
public class GemstoneOreBlock extends Block {
    public static final IntProperty AGE = IntProperty.of("age", 0, 3);
    private final UniformIntProvider experienceDropped;

    public GemstoneOreBlock(Settings settings) {
        this(settings, UniformIntProvider.create(3, 7));
    }

    public GemstoneOreBlock(Settings settings, UniformIntProvider experienceDropped) {
        super(settings);
        this.experienceDropped = experienceDropped;
        setDefaultState(getStateManager().getDefaultState().with(AGE, 0));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    @Override
    public void onStacksDropped(BlockState state, ServerWorld world, BlockPos pos, ItemStack tool,
            boolean dropExperience) {
        super.onStacksDropped(state, world, pos, tool, dropExperience);
        if (dropExperience) {
            this.dropExperienceWhenMined(world, pos, tool, this.experienceDropped);
        }
    }
}
