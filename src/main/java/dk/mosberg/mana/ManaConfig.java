package dk.mosberg.mana;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import dk.mosberg.MAM;

/**
 * Configuration for the mana system, loaded from mam.properties.
 */
public class ManaConfig {
    private static final Properties properties = new Properties();
    private static boolean loaded = false;

    // Personal pool settings
    private static double personalMaxPool = 1000.0;
    private static double personalRegenRate = 0.5;

    // Aura pool settings
    private static double auraMaxPool = 500.0;
    private static double auraRegenRate = 0.2;

    // Reserve pool settings
    private static double reserveMaxPool = 3000.0;
    private static double reserveRegenRate = 0.05;

    /**
     * Load configuration from the properties file.
     */
    public static void load() {
        if (loaded)
            return;

        try {
            // Try to load from run directory
            Path configPath = Paths.get("mam.properties");

            // If not found, try to load from resources
            InputStream input;
            if (Files.exists(configPath)) {
                input = new FileInputStream(configPath.toFile());
                MAM.LOGGER.info("Loading mana configuration from: {}", configPath.toAbsolutePath());
            } else {
                input = ManaConfig.class.getClassLoader().getResourceAsStream("mam.properties");
                MAM.LOGGER.info("Loading mana configuration from resources");
            }

            if (input != null) {
                properties.load(input);
                input.close();

                // Load personal pool settings
                personalMaxPool = getDouble("mana.personal.max_pool", 1000.0);
                personalRegenRate = getDouble("mana.personal.regen_rate", 0.5);

                // Load aura pool settings
                auraMaxPool = getDouble("mana.aura.max_pool", 500.0);
                auraRegenRate = getDouble("mana.aura.regen_rate", 0.2);

                // Load reserve pool settings
                reserveMaxPool = getDouble("mana.reserve.max_pool", 3000.0);
                reserveRegenRate = getDouble("mana.reserve.regen_rate", 0.05);

                loaded = true;
                MAM.LOGGER.info("Mana configuration loaded successfully");
            } else {
                MAM.LOGGER.warn("Could not find mam.properties, using defaults");
                loaded = true;
            }
        } catch (IOException e) {
            MAM.LOGGER.error("Failed to load mana configuration", e);
            loaded = true; // Use defaults
        }
    }

    private static double getDouble(String key, double defaultValue) {
        String value = properties.getProperty(key);
        if (value != null) {
            try {
                return Double.parseDouble(value);
            } catch (NumberFormatException e) {
                MAM.LOGGER.warn("Invalid value for {}: {}, using default: {}", key, value,
                        defaultValue);
            }
        }
        return defaultValue;
    }

    // Getters
    public static double getPersonalMaxPool() {
        return personalMaxPool;
    }

    public static double getPersonalRegenRate() {
        return personalRegenRate;
    }

    public static double getAuraMaxPool() {
        return auraMaxPool;
    }

    public static double getAuraRegenRate() {
        return auraRegenRate;
    }

    public static double getReserveMaxPool() {
        return reserveMaxPool;
    }

    public static double getReserveRegenRate() {
        return reserveRegenRate;
    }

    /**
     * Reload configuration from disk.
     */
    public static void reload() {
        loaded = false;
        load();
    }
}
