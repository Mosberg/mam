package dk.mosberg.mana;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import net.minecraft.server.network.ServerPlayerEntity;

/**
 * Manager for player mana components. Provides centralized access to player mana data.
 */
public class ManaManager {
    private static final Map<UUID, ManaComponent> components = new HashMap<>();

    /**
     * Get or create a mana component for a player.
     */
    public static ManaComponent getComponent(ServerPlayerEntity player) {
        return components.computeIfAbsent(player.getUuid(), uuid -> new ManaComponent(player));
    }

    /**
     * Remove a player's mana component (called when player leaves).
     */
    public static void removeComponent(UUID playerUuid) {
        components.remove(playerUuid);
    }

    /**
     * Tick all player mana components (regeneration).
     */
    public static void tickAll() {
        components.values().forEach(ManaComponent::tick);
    }

    /**
     * Clear all components (for server shutdown/restart).
     */
    public static void clear() {
        components.clear();
    }
}
