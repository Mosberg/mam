package dk.mosberg;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import dk.mosberg.command.MagicCommands;
import dk.mosberg.event.ServerEventHandlers;
import dk.mosberg.item.ModItemGroups;
import dk.mosberg.item.ModItems;
import dk.mosberg.mana.ManaConfig;
import dk.mosberg.network.ManaNetworkHandler;
import dk.mosberg.network.SpellCastNetworkHandler;
import dk.mosberg.registry.MagicRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

/**
 * Main entry point for Mana And Magic mod. Initializes all systems, registers items, and sets up
 * event handlers.
 */
public class MAM implements ModInitializer {
	public static final String MOD_ID = "mam";
	public static final String MOD_NAME = "Mana And Magic";

	/** Centralized logger for the entire mod */
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		long startTime = System.currentTimeMillis();
		LOGGER.info("Initializing {} v{}...", MOD_NAME, getModVersion());

		try {
			// Phase 1: Load configuration
			ManaConfig.load();

			// Phase 2: Register game content
			dk.mosberg.block.ModBlocks.initialize();
			dk.mosberg.item.ModBlockItems.initialize();
			ModItems.initialize();
			ModItemGroups.initialize();
			dk.mosberg.entity.ModEntities.initialize();

			// Phase 3: Load data-driven content
			MagicRegistry.initialize();

			// Phase 4: Register networking
			ManaNetworkHandler.register();
			SpellCastNetworkHandler.register();

			// Phase 5: Register commands
			CommandRegistrationCallback.EVENT.register(MagicCommands::register);

			// Phase 6: Register server event handlers
			ServerEventHandlers.register();

			long duration = System.currentTimeMillis() - startTime;
			LOGGER.info("{} initialized successfully in {}ms", MOD_NAME, duration);
		} catch (Exception e) {
			LOGGER.error("Failed to initialize {}", MOD_NAME, e);
			throw new RuntimeException("Mod initialization failed", e);
		}
	}

	/**
	 * Get the mod version from build properties.
	 */
	private String getModVersion() {
		return "1.0.0"; // Could be loaded from gradle.properties
	}
}
