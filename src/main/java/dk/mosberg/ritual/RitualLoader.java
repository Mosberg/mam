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
 * Loads ritual data from JSON files in the data/mam/rituals directory.
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
        int loadedCount = 0;

        // Get all ritual categories
        for (RitualCategory category : RitualCategory.values()) {
            String categoryPath = "data/" + MAM.MOD_ID + "/rituals/" + category.getId() + "/";

            try {
                loadedCount += loadRitualsFromResources(categoryPath, category);
            } catch (Exception e) {
                MAM.LOGGER.error("Failed to load rituals for category: {}", category.getId(), e);
            }
        }

        loaded = true;
        MAM.LOGGER.info("Successfully loaded {} rituals", loadedCount);
    }

    /**
     * Load rituals from the resources directory.
     */
    private static int loadRitualsFromResources(String basePath, RitualCategory category) {
        int count = 0;

        String[] knownRituals = getKnownRitualsForCategory(category);
        for (String ritualFile : knownRituals) {
            String resourcePath = basePath + ritualFile + ".json";

            try (InputStream input =
                    RitualLoader.class.getClassLoader().getResourceAsStream(resourcePath)) {
                if (input != null) {
                    JsonObject json = JsonParser
                            .parseReader(new InputStreamReader(input, StandardCharsets.UTF_8))
                            .getAsJsonObject();

                    Ritual ritual = Ritual.fromJson(json);
                    RITUALS.put(ritual.getId(), ritual);
                    count++;
                    MAM.LOGGER.debug("Loaded ritual: {}", ritual.getId());
                }
            } catch (IOException e) {
                MAM.LOGGER.error("Failed to load ritual from: {}", resourcePath, e);
            } catch (Exception e) {
                MAM.LOGGER.error("Failed to parse ritual from: {}", resourcePath, e);
            }
        }

        return count;
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
     * Get a ritual by its identifier.
     */
    public static Ritual getRitual(Identifier id) {
        return RITUALS.get(id);
    }

    /**
     * Get all loaded rituals.
     */
    public static Map<Identifier, Ritual> getAllRituals() {
        return new HashMap<>(RITUALS);
    }

    /**
     * Get all rituals of a specific category.
     */
    public static Map<Identifier, Ritual> getRitualsByCategory(RitualCategory category) {
        Map<Identifier, Ritual> categoryRituals = new HashMap<>();
        for (Map.Entry<Identifier, Ritual> entry : RITUALS.entrySet()) {
            if (entry.getValue().getCategory() == category) {
                categoryRituals.put(entry.getKey(), entry.getValue());
            }
        }
        return categoryRituals;
    }

    /**
     * Reload all rituals from disk.
     */
    public static void reload() {
        RITUALS.clear();
        loaded = false;
        loadRituals();
    }

    /**
     * Check if rituals have been loaded.
     */
    public static boolean isLoaded() {
        return loaded;
    }
}
