package dk.mosberg.client.renderer.entity;

import dk.mosberg.MAM;
import dk.mosberg.entity.FireElementalEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;

/**
 * Placeholder renderer stub for fire elemental entities. Implement proper model and texture
 * rendering for fire elementals later; register in MAMClient once complete.
 */
@Environment(EnvType.CLIENT)
public class FireElementalRenderer {
    private static final Identifier TEXTURE =
            Identifier.of(MAM.MOD_ID, "textures/entity/fire_elemental.png");

    public FireElementalRenderer(EntityRendererFactory.Context context) {
        // Placeholder constructor
    }

    public Identifier getTexture(FireElementalEntity entity) {
        return TEXTURE;
    }
}
