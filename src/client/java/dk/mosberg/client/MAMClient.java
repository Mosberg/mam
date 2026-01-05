package dk.mosberg.client;

import dk.mosberg.MAM;
import dk.mosberg.client.config.HudConfig;
import dk.mosberg.client.hud.ManaHudOverlay;
import dk.mosberg.client.network.ClientManaNetworkHandler;
import dk.mosberg.client.renderer.entity.SpellProjectileRenderer;
import dk.mosberg.entity.ModEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

/**
 * Client-side initialization for Mana And Magic mod. Registers HUD overlay, networking, and
 * client-specific features.
 */
public class MAMClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		MAM.LOGGER.info("Initializing {} client...", MAM.MOD_NAME);

		// Load HUD configuration
		HudConfig.load();

		// Register client-side networking
		ClientManaNetworkHandler.register();

		// Register entity renderers
		EntityRendererRegistry.register(ModEntities.SPELL_PROJECTILE, SpellProjectileRenderer::new);

		// Register HUD overlay (suppressing deprecation warning as HudRenderCallback is best
		// available for 1.21)
		@SuppressWarnings("deprecation")
		net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback callback =
				(drawContext, renderTickCounter) -> {
					// Pass 1.0f as default tickDelta since HUD rendering doesn't need interpolation
					ManaHudOverlay.render(drawContext, 1.0f);
				};
		HudRenderCallback.EVENT.register(callback);

		MAM.LOGGER.info("{} client initialized - HUD overlay registered", MAM.MOD_NAME);
	}
}
