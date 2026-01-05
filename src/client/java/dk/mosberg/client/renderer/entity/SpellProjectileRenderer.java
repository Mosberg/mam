package dk.mosberg.client.renderer.entity;

import dk.mosberg.entity.SpellProjectileEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;

/**
 * Renderer for spell projectile entities. Uses the item renderer as a base.
 */
@Environment(EnvType.CLIENT)
public class SpellProjectileRenderer extends FlyingItemEntityRenderer<SpellProjectileEntity> {
    public SpellProjectileRenderer(EntityRendererFactory.Context context) {
        super(context);
    }
}
