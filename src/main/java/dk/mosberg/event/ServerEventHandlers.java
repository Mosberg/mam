package dk.mosberg.event;

import dk.mosberg.MAM;
import dk.mosberg.mana.ManaComponent;
import dk.mosberg.mana.ManaManager;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.network.ServerPlayerEntity;

/**
 * Registers server-side event handlers for the mod. Handles player join/leave events and server
 * ticking for mana regeneration.
 */
public class ServerEventHandlers {

    /**
     * Register all server event handlers.
     */
    public static void register() {
        // Player join event - initialize mana component
        ServerPlayerEvents.AFTER_RESPAWN.register((oldPlayer, newPlayer, alive) -> {
            if (alive) {
                // Transfer mana data on respawn
                // Data is automatically transferred through UUID lookup
                ManaManager.getComponent(newPlayer); // Ensure component exists
                MAM.LOGGER.debug("Player {} respawned, mana component restored",
                        newPlayer.getName().getString());
            }
        });

        // Player disconnect event - cleanup mana component
        ServerPlayerEvents.AFTER_RESPAWN.register((oldPlayer, newPlayer, alive) -> {
            if (!alive) {
                ManaManager.removeComponent(oldPlayer.getUuid());
                MAM.LOGGER.debug("Player {} disconnected, mana component removed",
                        oldPlayer.getName().getString());
            }
        });

        // Server tick event - regenerate mana for all players
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            // Tick mana regeneration for all online players
            for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
                ManaComponent mana = ManaManager.getComponent(player);
                mana.tick();
            }
        });

        MAM.LOGGER.info("Registered server event handlers");
    }
}
