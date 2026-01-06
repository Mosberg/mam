package dk.mosberg.client;

import dk.mosberg.MAM;
import dk.mosberg.client.config.HudConfig;
import dk.mosberg.client.hud.BuffDisplayOverlay;
import dk.mosberg.client.hud.CooldownOverlay;
import dk.mosberg.client.hud.ManaHudOverlay;
import dk.mosberg.client.hud.ManaNodeIndicator;
import dk.mosberg.client.input.MagicKeyBindings;
import dk.mosberg.client.network.ClientManaNetworkHandler;
import dk.mosberg.client.renderer.entity.SpellProjectileRenderer;
import dk.mosberg.entity.ModEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

/**
 * Client-side initialization for Mana And Magic mod. Registers HUD overlays, networking, and
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

		// Register keybindings
		MagicKeyBindings.register();

		// Register entity renderers
		EntityRendererRegistry.register(ModEntities.SPELL_PROJECTILE, SpellProjectileRenderer::new);
		// TODO: Implement and register FireElementalRenderer with proper model

		// Register HUD overlays (suppressing deprecation warning)
		@SuppressWarnings("deprecation")
		net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback callback =
				(drawContext, renderTickCounter) -> {
					float tickDelta = 1.0f; // Use renderTickCounter for interpolation if needed

					// Render all HUD components
					ManaHudOverlay.render(drawContext, tickDelta);
					CooldownOverlay.render(drawContext, tickDelta);
					BuffDisplayOverlay.render(drawContext, tickDelta);
					ManaNodeIndicator.render(drawContext, tickDelta);
				};
		HudRenderCallback.EVENT.register(callback);

		MAM.LOGGER.info("{} client initialized - All HUD overlays registered", MAM.MOD_NAME);
	}
}
