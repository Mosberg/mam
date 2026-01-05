package dk.mosberg.client.network;

import dk.mosberg.MAM;
import dk.mosberg.client.hud.ManaHudOverlay;
import dk.mosberg.network.ManaNetworkHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

/**
 * Client-side network handler for receiving mana sync packets.
 */
@Environment(EnvType.CLIENT)
public class ClientManaNetworkHandler {

    /**
     * Register client-side packet receivers.
     */
    public static void register() {
        ClientPlayNetworking.registerGlobalReceiver(ManaNetworkHandler.ManaSyncPayload.ID,
                (payload, context) -> {
                    // Update client-side mana display
                    context.client().execute(() -> {
                        // Update current mana values
                        ManaHudOverlay.updateManaValues(payload.personalCurrent(),
                                payload.auraCurrent(), payload.reserveCurrent());

                        // Update max mana values
                        ManaHudOverlay.updateMaxManaValues(payload.personalMax(), payload.auraMax(),
                                payload.reserveMax());

                        MAM.LOGGER.trace("Received mana sync: P={}/{}, A={}/{}, R={}/{}",
                                payload.personalCurrent(), payload.personalMax(),
                                payload.auraCurrent(), payload.auraMax(), payload.reserveCurrent(),
                                payload.reserveMax());
                    });
                });

        MAM.LOGGER.info("Registered client mana network handlers");
    }
}
