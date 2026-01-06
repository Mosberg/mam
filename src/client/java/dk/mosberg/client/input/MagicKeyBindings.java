package dk.mosberg.client.input;

import dk.mosberg.MAM;
import dk.mosberg.client.hud.ManaHudOverlay;
import dk.mosberg.client.screen.SpellSelectionScreen;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;

/**
 * Client-side keybinding handler for Mana and Magic mod. Registers and handles key presses.
 *
 * Note: Full keybinding registration deferred - infrastructure is in place for future enhancement.
 */
@Environment(EnvType.CLIENT)
public class MagicKeyBindings {
    /**
     * Register client event handlers.
     */
    public static void register() {
        // Register client tick event to handle input
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            // TODO: Implement proper keybinding handling when API is finalized for 1.21.11
        });

        MAM.LOGGER.info("Registered client event infrastructure");
    }

    /**
     * Open the spell selection screen.
     */
    public static void openSpellSelectionScreen(MinecraftClient client) {
        if (client.player == null || client.currentScreen != null) {
            return;
        }

        if (client.player.isSpectator() || client.player.isDead()) {
            return;
        }

        MAM.LOGGER.debug("Opening spell selection screen");
        client.setScreen(new SpellSelectionScreen(client.currentScreen));
    }

    /**
     * Toggle HUD visibility.
     */
    public static void toggleHUD() {
        boolean newState = !ManaHudOverlay.isEnabled();
        ManaHudOverlay.setEnabled(newState);
        MAM.LOGGER.info("HUD visibility toggled: {}", newState ? "ON" : "OFF");
    }
}
