package dk.mosberg.spell;

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
 * Loads spell data from JSON files in the data/mam/spells directory.
 */
public class SpellLoader {
    private static final Map<Identifier, Spell> SPELLS = new HashMap<>();
    private static boolean loaded = false;

    /**
     * Load all spell JSON files from resources.
     */
    public static void loadSpells() {
        if (loaded) {
            MAM.LOGGER.warn("Spells already loaded, skipping...");
            return;
        }

        MAM.LOGGER.info("Loading spells from data files...");
        int loadedCount = 0;

        // Get all spell schools
        for (SpellSchool school : SpellSchool.values()) {
            String schoolPath = "data/" + MAM.MOD_ID + "/spells/" + school.getId() + "/";

            try {
                // Try to load from resources
                loadedCount += loadSpellsFromResources(schoolPath, school);
            } catch (Exception e) {
                MAM.LOGGER.error("Failed to load spells for school: {}", school.getId(), e);
            }
        }

        loaded = true;
        MAM.LOGGER.info("Successfully loaded {} spells", loadedCount);
    }

    /**
     * Load spells from the resources directory.
     */
    private static int loadSpellsFromResources(String basePath, SpellSchool school) {
        int count = 0;

        // In a real implementation, you would need to list resource files
        // For now, we'll try to load known spell files based on the JSON files that exist
        // This is a simplified version - a production version would use a resource walker

        String[] knownSpells = getKnownSpellsForSchool(school);
        for (String spellFile : knownSpells) {
            String resourcePath = basePath + spellFile + ".json";

            try (InputStream input =
                    SpellLoader.class.getClassLoader().getResourceAsStream(resourcePath)) {
                if (input != null) {
                    JsonObject json = JsonParser
                            .parseReader(new InputStreamReader(input, StandardCharsets.UTF_8))
                            .getAsJsonObject();

                    Spell spell = Spell.fromJson(json);
                    SPELLS.put(spell.getId(), spell);
                    count++;
                    MAM.LOGGER.debug("Loaded spell: {}", spell.getId());
                }
            } catch (IOException e) {
                MAM.LOGGER.error("Failed to load spell from: {}", resourcePath, e);
            } catch (Exception e) {
                MAM.LOGGER.error("Failed to parse spell from: {}", resourcePath, e);
            }
        }

        return count;
    }

    /**
     * Get known spell file names for a school. In production, this would use resource
     * introspection.
     */
    private static String[] getKnownSpellsForSchool(SpellSchool school) {
        return switch (school) {
            case FIRE -> new String[] {"fireball", "flame_wave", "inferno"};
            case ICE -> new String[] {"frost_nova", "ice_lance", "frozen_prison"};
            case WATER -> new String[] {"water_strike", "healing_wave", "tidal_force"};
            case THUNDER -> new String[] {"lightning_bolt", "storm_caller", "chain_lightning"};
            case EARTH -> new String[] {"stone_shield", "earthquake", "rock_barrage"};
            case AIR -> new String[] {"wind_blast", "cyclone", "levitate"};
            case NATURE -> new String[] {"verdant_surge", "entangle", "nature_rebirth"};
            case LIGHT -> new String[] {"holy_bolt", "divine_shield", "purify"};
            case DARK -> new String[] {"shadow_bolt", "curse", "drain_life"};
            case ARCANE -> new String[] {"arcane_missile", "mana_shield", "teleport"};
            case BLOOD -> new String[] {"blood_bolt", "life_tap", "sacrifice"};
            case CHAOS -> new String[] {"chaos_orb", "wild_magic", "reality_warp"};
            case VOID -> new String[] {"void_lance", "dimensional_rift", "entropy"};
        };
    }

    /**
     * Get a spell by its identifier.
     */
    public static Spell getSpell(Identifier id) {
        return SPELLS.get(id);
    }

    /**
     * Get all loaded spells.
     */
    public static Map<Identifier, Spell> getAllSpells() {
        return new HashMap<>(SPELLS);
    }

    /**
     * Get all spells of a specific school.
     */
    public static Map<Identifier, Spell> getSpellsBySchool(SpellSchool school) {
        Map<Identifier, Spell> schoolSpells = new HashMap<>();
        for (Map.Entry<Identifier, Spell> entry : SPELLS.entrySet()) {
            if (entry.getValue().getSchool() == school) {
                schoolSpells.put(entry.getKey(), entry.getValue());
            }
        }
        return schoolSpells;
    }

    /**
     * Reload all spells from disk.
     */
    public static void reload() {
        SPELLS.clear();
        loaded = false;
        loadSpells();
    }

    /**
     * Check if spells have been loaded.
     */
    public static boolean isLoaded() {
        return loaded;
    }
}
