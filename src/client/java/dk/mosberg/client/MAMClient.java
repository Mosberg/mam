package dk.mosberg.client;

import dk.mosberg.MAM;
import dk.mosberg.client.config.HudConfig;
import dk.mosberg.client.network.ClientManaNetworkHandler;
import net.fabricmc.api.ClientModInitializer;

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

		// TODO: Register HUD overlay when compatible rendering API is determined
		// HUD rendering temporarily disabled for API compatibility

		MAM.LOGGER.info("{} client initialized", MAM.MOD_NAME);
	}
}
