package dk.mosberg.ritual;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dk.mosberg.MAM;
import net.minecraft.util.Identifier;

/**
 * Loads and validates ritual data from JSON files in the data/mam/rituals directory. Provides
 * thread-safe access to ritual registry.
 */
public class RitualLoader {
    private static final Map<Identifier, Ritual> RITUALS = new HashMap<>();
    private static boolean loaded = false;

    /**
     * Load all ritual JSON files from resources.
     */
    public static void loadRituals() {
        if (loaded) {
            MAM.LOGGER.warn("Rituals already loaded, skipping...");
            return;
        }

        MAM.LOGGER.info("Loading rituals from data files...");
        long startTime = System.currentTimeMillis();
        int loadedCount = 0;
        int errorCount = 0;

        // Get all ritual categories
        for (RitualCategory category : RitualCategory.values()) {
            String categoryPath = "data/" + MAM.MOD_ID + "/rituals/" + category.getId() + "/";

            try {
                int categoryLoaded = loadRitualsFromResources(categoryPath, category);
                loadedCount += categoryLoaded;
                MAM.LOGGER.debug("Loaded {} rituals from category: {}", categoryLoaded,
                        category.getId());
            } catch (Exception e) {
                errorCount++;
                MAM.LOGGER.error("Failed to load rituals for category: {}", category.getId(), e);
            }
        }

        loaded = true;
        long duration = System.currentTimeMillis() - startTime;

        if (errorCount > 0) {
            MAM.LOGGER.warn("Loaded {} rituals with {} errors in {}ms", loadedCount, errorCount,
                    duration);
        } else {
            MAM.LOGGER.info("Successfully loaded {} rituals in {}ms", loadedCount, duration);
        }
    }

    /**
     * Load rituals from the resources directory.
     */
    private static int loadRitualsFromResources(String basePath, RitualCategory category) {
        int count = 0;
        int errors = 0;

        String[] knownRituals = getKnownRitualsForCategory(category);
        for (String ritualFile : knownRituals) {
            String resourcePath = basePath + ritualFile + ".json";

            try (InputStream input =
                    RitualLoader.class.getClassLoader().getResourceAsStream(resourcePath)) {
                if (input == null) {
                    MAM.LOGGER.trace("Ritual file not found: {} (optional)", resourcePath);
                    continue;
                }

                JsonObject json =
                        JsonParser.parseReader(new InputStreamReader(input, StandardCharsets.UTF_8))
                                .getAsJsonObject();

                Ritual ritual = Ritual.fromJson(json);

                // Validate ritual data
                if (!validateRitual(ritual)) {
                    MAM.LOGGER.error("Ritual validation failed: {}", resourcePath);
                    errors++;
                    continue;
                }

                RITUALS.put(ritual.getId(), ritual);
                count++;
                MAM.LOGGER.trace("Successfully loaded ritual: {}", ritual.getId());

            } catch (IOException e) {
                errors++;
                MAM.LOGGER.error("I/O error loading ritual from: {}", resourcePath, e);
            } catch (Exception e) {
                errors++;
                MAM.LOGGER.error("Failed to parse ritual from: {}", resourcePath, e);
            }
        }

        if (errors > 0) {
            MAM.LOGGER.warn("Category {} had {} errors while loading", category.getId(), errors);
        }

        return count;
    }

    /**
     * Validate ritual data integrity.
     * 
     * @param ritual The ritual to validate
     * @return true if valid, false otherwise
     */
    private static boolean validateRitual(Ritual ritual) {
        if (ritual == null) {
            MAM.LOGGER.error("Ritual is null");
            return false;
        }

        if (ritual.getId() == null) {
            MAM.LOGGER.error("Ritual has null identifier");
            return false;
        }

        if (ritual.getCategory() == null) {
            MAM.LOGGER.error("Ritual {} has null category", ritual.getId());
            return false;
        }

        if (ritual.getRitualItems() == null || ritual.getRitualItems().isEmpty()) {
            MAM.LOGGER.warn("Ritual {} has no required items", ritual.getId());
        }

        if (ritual.getPattern() == null) {
            MAM.LOGGER.error("Ritual {} has null pattern", ritual.getId());
            return false;
        }

        if (ritual.getEffect() == null) {
            MAM.LOGGER.error("Ritual {} has null effect", ritual.getId());
            return false;
        }

        return true;
    }

    /**
     * Get known ritual file names for a category.
     */
    private static String[] getKnownRitualsForCategory(RitualCategory category) {
        return switch (category) {
            case ASCENSION -> new String[] {"apotheosis_ritual", "divine_ascension"};
            case CIRCLE -> new String[] {"protection_circle", "binding_circle"};
            case COSMIC -> new String[] {"cosmic_alignment", "stellar_convergence"};
            case ELEMENTAL -> new String[] {"elemental_convergence", "primal_fusion"};
            case FOUNTAIN -> new String[] {"mana_fountain", "eternal_spring"};
            case PLANAR -> new String[] {"planar_shift", "dimensional_gateway"};
            case REALITY -> new String[] {"reality_anchor", "world_reshape"};
            case RESURRECTION -> new String[] {"resurrection_circle", "rebirth_ritual"};
            case SACRIFICE -> new String[] {"ritual_sacrifice", "blood_offering"};
            case SUMMONING -> new String[] {"primordial_summoning", "demon_gate"};
            case TEMPORAL -> new String[] {"time_freeze", "temporal_shift"};
            case TRANSFORMATION -> new String[] {"metamorphosis_circle", "shape_change"};
            case VORTEX -> new String[] {"mana_vortex", "chaos_vortex"};
        };
    }

    /**
     * Get a ritual by its identifier. Thread-safe operation.
     * 
     * @param id The ritual identifier
     * @return The ritual, or null if not found
     */
    public static Ritual getRitual(Identifier id) {
        return RITUALS.get(id);
    }

    /**
     * Get all loaded rituals. Returns an immutable copy to prevent external modification.
     * 
     * @return Map of all loaded rituals
     */
    public static Map<Identifier, Ritual> getAllRituals() {
        return Map.copyOf(RITUALS);
    }

    /**
     * Get all rituals of a specific category.
     * 
     * @param category The ritual category
     * @return Map of rituals in this category
     */
    public static Map<Identifier, Ritual> getRitualsByCategory(RitualCategory category) {
        Map<Identifier, Ritual> categoryRituals = new HashMap<>();
        for (Map.Entry<Identifier, Ritual> entry : RITUALS.entrySet()) {
            if (entry.getValue().getCategory() == category) {
                categoryRituals.put(entry.getKey(), entry.getValue());
            }
        }
        return Map.copyOf(categoryRituals);
    }

    /**
     * Reload all rituals from disk.
     */
    public static void reload() {
        MAM.LOGGER.info("Reloading all rituals...");
        RITUALS.clear();
        loaded = false;
        loadRituals();
    }

    /**
     * Check if rituals have been loaded.
     * 
     * @return true if rituals are loaded
     */
    public static boolean isLoaded() {
        return loaded;
    }

    /**
     * Get the number of loaded rituals.
     * 
     * @return Count of loaded rituals
     */
    public static int getRitualCount() {
        return RITUALS.size();
    }

    /**
     * Check if a ritual exists.
     * 
     * @param id The ritual identifier
     * @return true if the ritual is loaded
     */
    public static boolean hasRitual(Identifier id) {
        return RITUALS.containsKey(id);
    }
}
