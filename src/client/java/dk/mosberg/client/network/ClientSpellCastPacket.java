package dk.mosberg.client.network;

import dk.mosberg.MAM;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.util.Identifier;

/**
 * Client-side spell casting packet sender. Sends spell cast requests to the server.
 */
@Environment(EnvType.CLIENT)
public class ClientSpellCastPacket {
    public static final Identifier SPELL_CAST_PACKET_ID = Identifier.of(MAM.MOD_ID, "spell_cast");

    /**
     * Send a spell cast request to the server.
     *
     * @param spellId The spell identifier
     */
    public static void send(Identifier spellId) {
        if (spellId == null) {
            MAM.LOGGER.warn("Attempted to cast null spell");
            return;
        }

        // TODO: Implement proper packet sending once packet infrastructure is in place
        MAM.LOGGER.debug("Spell cast packet would be sent: {}", spellId);
    }
}
