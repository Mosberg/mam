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
    private static net.minecraft.client.option.KeyBinding openSpellKey;
    private static net.minecraft.client.option.KeyBinding toggleHudKey;

    /**
     * Register client event handlers.
     */
    public static void register() {
        openSpellKey =
                net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper.registerKeyBinding(
                        new net.minecraft.client.option.KeyBinding("key.mam.open_spell_selection",
                                net.minecraft.client.util.InputUtil.Type.KEYSYM,
                                org.lwjgl.glfw.GLFW.GLFW_KEY_R,
                                net.minecraft.client.option.KeyBinding.Category.MISC));

        toggleHudKey = net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
                .registerKeyBinding(new net.minecraft.client.option.KeyBinding("key.mam.toggle_hud",
                        net.minecraft.client.util.InputUtil.Type.KEYSYM,
                        org.lwjgl.glfw.GLFW.GLFW_KEY_H,
                        net.minecraft.client.option.KeyBinding.Category.MISC));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client == null) {
                return;
            }

            if (openSpellKey != null) {
                while (openSpellKey.wasPressed()) {
                    openSpellSelectionScreen(client);
                }
            }

            if (toggleHudKey != null) {
                while (toggleHudKey.wasPressed()) {
                    toggleHUD();
                }
            }
        });

        MAM.LOGGER.info("Registered client keybindings");
    }

    /**
     * Open the spell selection screen.
     */
    public static void openSpellSelectionScreen(MinecraftClient client) {
        if (client == null || client.player == null || client.currentScreen != null) {
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
