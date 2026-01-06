// Implement the SpellCastPayload class and its codec
// Handle potential exceptions during spell casting
// ...existing code...
package dk.mosberg.network;

import dk.mosberg.MAM;
import dk.mosberg.spell.SpellCaster;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

/**
 * Network handler for spell casting. Allows clients to request spell casts on the server.
 */
public class SpellCastNetworkHandler {
    public static final Identifier SPELL_CAST_ID = Identifier.of(MAM.MOD_ID, "spell_cast");

    /**
     * Register network handlers.
     */
    public static void register() {
        // Register payload type
        PayloadTypeRegistry.playC2S().register(SpellCastPayload.ID, SpellCastPayload.CODEC);

        // Register server-side receiver
        ServerPlayNetworking.registerGlobalReceiver(SpellCastPayload.ID, (payload, context) -> {
            context.server().execute(() -> {
                ServerPlayerEntity player = context.player();
                Identifier spellId = Identifier.of(payload.spellId());

                MAM.LOGGER.debug("Received spell cast request from {}: {}",
                        player.getName().getString(), spellId);

                // Attempt to cast the spell
                boolean success = SpellCaster.castSpell(player, spellId);

                if (success) {
                    MAM.LOGGER.info("Player {} successfully cast spell {}",
                            player.getName().getString(), spellId);
                } else {
                    MAM.LOGGER.debug("Player {} failed to cast spell {}",
                            player.getName().getString(), spellId);
                }
            });
        });

        MAM.LOGGER.info("Registered spell cast network handlers");
    }

    /**
     * Payload for spell cast packets (client to server).
     */
    public record SpellCastPayload(String spellId) implements CustomPayload {
        public static final CustomPayload.Id<SpellCastPayload> ID =
                new CustomPayload.Id<>(SPELL_CAST_ID);

        public static final PacketCodec<RegistryByteBuf, SpellCastPayload> CODEC = PacketCodec
                .tuple(PacketCodecs.STRING, SpellCastPayload::spellId, SpellCastPayload::new);

        @Override
        public CustomPayload.Id<? extends CustomPayload> getId() {
            return ID;
        }
    }
}
