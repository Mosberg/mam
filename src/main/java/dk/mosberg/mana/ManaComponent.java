package dk.mosberg.mana;

import java.util.EnumMap;
import java.util.Map;
import dk.mosberg.MAM;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;

/**
 * Thread-safe component that stores mana data for a player. This is attached to players and
 * persists across sessions via NBT.
 */
public class ManaComponent {
    private final EnumMap<ManaPoolType, ManaPool> pools = new EnumMap<>(ManaPoolType.class);
    private final ServerPlayerEntity player;

    public ManaComponent(ServerPlayerEntity player) {
        if (player == null) {
            throw new IllegalArgumentException("Player cannot be null");
        }
        this.player = player;

        // Initialize all pool types
        for (ManaPoolType type : ManaPoolType.values()) {
            pools.put(type, new ManaPool(type));
        }
    }

    /**
     * Get a specific mana pool.
     *
     * @param type The pool type
     * @return The mana pool, never null
     * @throws IllegalArgumentException if type is null
     */
    public ManaPool getPool(ManaPoolType type) {
        if (type == null) {
            throw new IllegalArgumentException("Pool type cannot be null");
        }
        return pools.get(type);
    }

    /**
     * Consume mana from a specific pool.
     *
     * @param type The pool type
     * @param amount The amount to consume
     * @return true if mana was consumed, false if insufficient mana
     */
    public boolean consume(ManaPoolType type, double amount) {
        if (type == null || amount < 0) {
            MAM.LOGGER.warn("Invalid consume request: type={}, amount={}", type, amount);
            return false;
        }
        return pools.get(type).consume(amount);
    }

    /**
     * Add mana to a specific pool.
     *
     * @param type The pool type
     * @param amount The amount to add (must be >= 0)
     */
    public void add(ManaPoolType type, double amount) {
        if (type == null) {
            throw new IllegalArgumentException("Pool type cannot be null");
        }
        if (amount < 0) {
            MAM.LOGGER.warn("Cannot add negative mana: {}", amount);
            return;
        }
        pools.get(type).add(amount);
    }

    /**
     * Check if a pool has at least the specified amount.
     *
     * @param type The pool type
     * @param amount The amount to check
     * @return true if pool has at least this amount
     */
    public boolean has(ManaPoolType type, double amount) {
        if (type == null) {
            return false;
        }
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
     *
     * @param nbt The NBT compound to write to
     */
    public void writeNbt(NbtCompound nbt) {
        if (nbt == null) {
            MAM.LOGGER.error("Cannot write to null NBT compound");
            return;
        }

        try {
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
            MAM.LOGGER.trace("Wrote mana data for player: {}", player.getName().getString());
        } catch (Exception e) {
            MAM.LOGGER.error("Failed to write mana NBT for player: {}",
                    player.getName().getString(), e);
        }
    }

    /**
     * Read mana data from NBT.
     *
     * @param nbt The NBT compound to read from
     */
    public void readNbt(NbtCompound nbt) {
        if (nbt == null) {
            MAM.LOGGER.error("Cannot read from null NBT compound");
            return;
        }

        try {
            if (nbt.contains("mana")) {
                NbtCompound manaData = nbt.getCompound("mana").orElse(new NbtCompound());

                for (ManaPoolType type : ManaPoolType.values()) {
                    if (manaData.contains(type.getId())) {
                        NbtCompound poolData =
                                manaData.getCompound(type.getId()).orElse(new NbtCompound());
                        ManaPool pool = pools.get(type);

                        double max = poolData.contains("max")
                                ? poolData.getDouble("max").orElse(type.getMaxPool())
                                : type.getMaxPool();
                        double current = poolData.contains("current")
                                ? poolData.getDouble("current").orElse(max)
                                : max;

                        pool.setMax(max);
                        pool.set(Math.min(current, max)); // Ensure current <= max
                    }
                }

                MAM.LOGGER.trace("Read mana data for player: {}", player.getName().getString());
            } else {
                MAM.LOGGER.debug("No mana data found for player: {}, using defaults",
                        player.getName().getString());
            }
        } catch (Exception e) {
            MAM.LOGGER.error("Failed to read mana NBT for player: {}", player.getName().getString(),
                    e);
        }
    }

    /**
     * Get the player this component is attached to.
     *
     * @return The server player entity, never null
     */
    public ServerPlayerEntity getPlayer() {
        return player;
    }

    /**
     * Get all mana pools.
     *
     * @return Immutable copy of all mana pools
     */
    public Map<ManaPoolType, ManaPool> getAllPools() {
        return Map.copyOf(pools);
    }

    /**
     * Restore a specific pool to its maximum.
     *
     * @param type The pool type to restore
     */
    public void restorePool(ManaPoolType type) {
        if (type == null) {
            throw new IllegalArgumentException("Pool type cannot be null");
        }
        ManaPool pool = pools.get(type);
        pool.set(pool.getMax());
        MAM.LOGGER.debug("Restored {} pool for player: {}", type.getId(),
                player.getName().getString());
    }

    /**
     * Restore all pools to their maximum.
     */
    public void restoreAllPools() {
        for (ManaPool pool : pools.values()) {
            pool.set(pool.getMax());
        }
        MAM.LOGGER.debug("Restored all mana pools for player: {}", player.getName().getString());
    }

    /**
     * Get total mana across all pools.
     *
     * @return Sum of current mana in all pools
     */
    public double getTotalMana() {
        return pools.values().stream().mapToDouble(ManaPool::getCurrent).sum();
    }

    /**
     * Get total max mana across all pools.
     *
     * @return Sum of max mana in all pools
     */
    public double getTotalMaxMana() {
        return pools.values().stream().mapToDouble(ManaPool::getMax).sum();
    }
}
