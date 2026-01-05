package dk.mosberg.mana;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import dk.mosberg.MAM;
import net.minecraft.server.network.ServerPlayerEntity;

/**
 * Thread-safe manager for player mana components. Provides centralized access to player mana data
 * with automatic cleanup.
 */
public class ManaManager {
    private static final Map<UUID, ManaComponent> components = new ConcurrentHashMap<>();

    /**
     * Get or create a mana component for a player. Thread-safe operation.
     *
     * @param player The server player
     * @return The mana component for this player
     */
    public static ManaComponent getComponent(ServerPlayerEntity player) {
        if (player == null) {
            throw new IllegalArgumentException("Player cannot be null");
        }
        return components.computeIfAbsent(player.getUuid(), uuid -> {
            MAM.LOGGER.debug("Creating new mana component for player: {}",
                    player.getName().getString());
            return new ManaComponent(player);
        });
    }

    /**
     * Remove a player's mana component (called when player leaves).
     * 
     * @param playerUuid The player's UUID
     * @return true if component was removed, false if it didn't exist
     */
    public static boolean removeComponent(UUID playerUuid) {
        ManaComponent removed = components.remove(playerUuid);
        if (removed != null) {
            MAM.LOGGER.debug("Removed mana component for UUID: {}", playerUuid);
            return true;
        }
        return false;
    }

    /**
     * Tick all player mana components (regeneration). Should be called once per server tick.
     */
    public static void tickAll() {
        components.values().forEach(ManaComponent::tick);
    }

    /**
     * Clear all components (for server shutdown/restart).
     */
    public static void clear() {
        int count = components.size();
        components.clear();
        MAM.LOGGER.info("Cleared {} mana components", count);
    }

    /**
     * Get the number of active mana components.
     */
    public static int getActiveComponentCount() {
        return components.size();
    }

    /**
     * Check if a player has a mana component.
     */
    public static boolean hasComponent(UUID playerUuid) {
        return components.containsKey(playerUuid);
    }
}
