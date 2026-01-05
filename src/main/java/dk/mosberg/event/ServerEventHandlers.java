package dk.mosberg.event;

import dk.mosberg.MAM;
import dk.mosberg.mana.ManaComponent;
import dk.mosberg.mana.ManaManager;
import dk.mosberg.network.ManaNetworkHandler;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.network.ServerPlayerEntity;

/**
 * Registers server-side event handlers for the mod. Handles player join/leave events and server
 * ticking for mana regeneration.
 */
public class ServerEventHandlers {
    private static int tickCounter = 0;
    private static final int SYNC_INTERVAL = 20; // Sync every 20 ticks (1 second)

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

                // Send initial mana sync to client
                ManaNetworkHandler.sendManaUpdate(newPlayer);

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
            tickCounter++;

            // Tick mana regeneration for all online players
            for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
                ManaComponent mana = ManaManager.getComponent(player);
                mana.tick();

                // Send periodic mana sync to client
                if (tickCounter >= SYNC_INTERVAL) {
                    ManaNetworkHandler.sendManaUpdate(player);
                }
            }

            // Reset counter
            if (tickCounter >= SYNC_INTERVAL) {
                tickCounter = 0;
            }
        });

        MAM.LOGGER.info("Registered server event handlers");
    }
}
