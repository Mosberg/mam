package dk.mosberg.network;

import dk.mosberg.MAM;
import dk.mosberg.mana.ManaComponent;
import dk.mosberg.mana.ManaManager;
import dk.mosberg.mana.ManaPoolType;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

/**
 * Network handler for syncing mana data between server and client. Uses Fabric's networking API for
 * reliable packet transmission.
 */
public class ManaNetworkHandler {
    public static final Identifier MANA_SYNC_ID = Identifier.of(MAM.MOD_ID, "mana_sync");

    /**
     * Register network handlers.
     */
    public static void register() {
        // Register payload type
        PayloadTypeRegistry.playS2C().register(ManaSyncPayload.ID, ManaSyncPayload.CODEC);

        MAM.LOGGER.info("Registered mana network handlers");
    }

    /**
     * Send mana update to client.
     *
     * @param player The player to send to
     */
    public static void sendManaUpdate(ServerPlayerEntity player) {
        ManaComponent mana = ManaManager.getComponent(player);

        double personal = mana.getPool(ManaPoolType.PERSONAL).getCurrent();
        double aura = mana.getPool(ManaPoolType.AURA).getCurrent();
        double reserve = mana.getPool(ManaPoolType.RESERVE).getCurrent();

        double personalMax = mana.getPool(ManaPoolType.PERSONAL).getMax();
        double auraMax = mana.getPool(ManaPoolType.AURA).getMax();
        double reserveMax = mana.getPool(ManaPoolType.RESERVE).getMax();

        ManaSyncPayload payload =
                new ManaSyncPayload(personal, aura, reserve, personalMax, auraMax, reserveMax);

        ServerPlayNetworking.send(player, payload);
    }

    /**
     * Payload for mana sync packets.
     */
    public record ManaSyncPayload(double personalCurrent, double auraCurrent, double reserveCurrent,
            double personalMax, double auraMax, double reserveMax) implements CustomPayload {

        public static final CustomPayload.Id<ManaSyncPayload> ID =
                new CustomPayload.Id<>(MANA_SYNC_ID);

        public static final PacketCodec<RegistryByteBuf, ManaSyncPayload> CODEC =
                PacketCodec.tuple(PacketCodecs.DOUBLE, ManaSyncPayload::personalCurrent,
                        PacketCodecs.DOUBLE, ManaSyncPayload::auraCurrent, PacketCodecs.DOUBLE,
                        ManaSyncPayload::reserveCurrent, PacketCodecs.DOUBLE,
                        ManaSyncPayload::personalMax, PacketCodecs.DOUBLE, ManaSyncPayload::auraMax,
                        PacketCodecs.DOUBLE, ManaSyncPayload::reserveMax, ManaSyncPayload::new);

        @Override
        public Id<? extends CustomPayload> getId() {
            return ID;
        }
    }
}
