package dk.mosberg.client.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dk.mosberg.MAM;
import dk.mosberg.client.hud.ManaHudOverlay;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

/**
 * Client-side configuration for HUD display. Manages scale, position, transparency, and visibility
 * settings.
 */
@Environment(EnvType.CLIENT)
public class HudConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH = Paths.get("config", "mam_hud.json");

    private static HudSettings settings = new HudSettings();

    /**
     * Load HUD configuration from file.
     */
    public static void load() {
        try {
            if (Files.exists(CONFIG_PATH)) {
                String json = Files.readString(CONFIG_PATH);
                settings = GSON.fromJson(json, HudSettings.class);
                MAM.LOGGER.info("Loaded HUD configuration");
            } else {
                // Create default config
                save();
                MAM.LOGGER.info("Created default HUD configuration");
            }

            // Apply settings
            applySettings();
        } catch (IOException e) {
            MAM.LOGGER.error("Failed to load HUD configuration", e);
        }
    }

    /**
     * Save HUD configuration to file.
     */
    public static void save() {
        try {
            Files.createDirectories(CONFIG_PATH.getParent());
            String json = GSON.toJson(settings);
            Files.writeString(CONFIG_PATH, json);
            MAM.LOGGER.debug("Saved HUD configuration");
        } catch (IOException e) {
            MAM.LOGGER.error("Failed to save HUD configuration", e);
        }
    }

    /**
     * Apply settings to HUD overlay.
     */
    private static void applySettings() {
        ManaHudOverlay.setEnabled(settings.enabled);
        ManaHudOverlay.setScale(settings.scale);
        ManaHudOverlay.setPosition(settings.xOffset, settings.yOffset);
        ManaHudOverlay.setTransparency(settings.transparency);
    }

    /**
     * Get current settings.
     */
    public static HudSettings getSettings() {
        return settings;
    }

    /**
     * Update settings and save.
     */
    public static void updateSettings(HudSettings newSettings) {
        settings = newSettings;
        applySettings();
        save();
    }

    /**
     * HUD settings data class.
     */
    public static class HudSettings {
        public boolean enabled = true;
        public float scale = 1.0f;
        public int xOffset = 10;
        public int yOffset = 10;
        public float transparency = 1.0f;

        public boolean showHealth = true;
        public boolean showMana = true;
        public boolean showStatusEffects = true;

        public String position = "TOP_LEFT"; // TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT
    }
}
