package dk.mosberg.mana;

import java.util.HashMap;
import java.util.Map;
import dk.mosberg.util.NBTHelper;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;

/**
 * Component that stores mana data for a player. This is attached to players and persists across
 * sessions.
 */
public class ManaComponent {
    private final Map<ManaPoolType, ManaPool> pools = new HashMap<>();
    private final ServerPlayerEntity player;

    public ManaComponent(ServerPlayerEntity player) {
        this.player = player;

        // Initialize all pool types
        for (ManaPoolType type : ManaPoolType.values()) {
            pools.put(type, new ManaPool(type));
        }
    }

    /**
     * Get a specific mana pool.
     */
    public ManaPool getPool(ManaPoolType type) {
        return pools.get(type);
    }

    /**
     * Consume mana from a specific pool.
     */
    public boolean consume(ManaPoolType type, double amount) {
        return pools.get(type).consume(amount);
    }

    /**
     * Add mana to a specific pool.
     */
    public void add(ManaPoolType type, double amount) {
        pools.get(type).add(amount);
    }

    /**
     * Check if a pool has at least the specified amount.
     */
    public boolean has(ManaPoolType type, double amount) {
        return pools.get(type).has(amount);
    }

    /**
     * Regenerate all mana pools. Should be called every tick for the player.
     */
    public void tick() {
        for (ManaPool pool : pools.values()) {
            pool.regenerate();
        }
    }

    /**
     * Write mana data to NBT for persistence.
     */
    public void writeNbt(NbtCompound nbt) {
        NbtCompound manaData = new NbtCompound();

        for (Map.Entry<ManaPoolType, ManaPool> entry : pools.entrySet()) {
            ManaPoolType type = entry.getKey();
            ManaPool pool = entry.getValue();

            NbtCompound poolData = new NbtCompound();
            poolData.putDouble("current", pool.getCurrent());
            poolData.putDouble("max", pool.getMax());

            manaData.put(type.getId(), poolData);
        }

        nbt.put("mana", manaData);
    }

    /**
     * Read mana data from NBT.
     */
    public void readNbt(NbtCompound nbt) {
        if (NBTHelper.contains(nbt, "mana")) {
            NbtCompound manaData = nbt.getCompound("mana");

            for (ManaPoolType type : ManaPoolType.values()) {
                if (NBTHelper.contains(manaData, type.getId())) {
                    NbtCompound poolData = manaData.getCompound(type.getId());
                    ManaPool pool = pools.get(type);

                    double max = NBTHelper.getDouble(poolData, "max", type.getMaxPool());
                    double current = NBTHelper.getDouble(poolData, "current", max);

                    pool.setMax(max);
                    pool.set(current);
                }
            }
        }
    }

    /**
     * Get the player this component is attached to.
     */
    public ServerPlayerEntity getPlayer() {
        return player;
    }
}
