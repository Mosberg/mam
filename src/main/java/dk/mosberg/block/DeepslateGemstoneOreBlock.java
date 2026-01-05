package dk.mosberg.block;

import net.minecraft.util.math.intprovider.UniformIntProvider;

/**
 * Deepslate Gemstone Ore - Deepslate variant of gemstone ore.
 */
public class DeepslateGemstoneOreBlock extends GemstoneOreBlock {

    public DeepslateGemstoneOreBlock(Settings settings) {
        super(settings, UniformIntProvider.create(3, 7));
    }

    public DeepslateGemstoneOreBlock(Settings settings, UniformIntProvider experienceDropped) {
        super(settings, experienceDropped);
    }
}
