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
        int errors = 0;

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

                    // Validate spell data
                    if (validateSpell(spell)) {
                        SPELLS.put(spell.getId(), spell);
                        count++;
                        MAM.LOGGER.debug("Loaded spell: {} (School: {}, Tier: {})", spell.getId(),
                                spell.getSchool().getId(), spell.getTier());
                    } else {
                        errors++;
                        MAM.LOGGER.warn("Spell validation failed: {}", spell.getId());
                    }
                } else {
                    MAM.LOGGER.trace("Spell file not found: {}", resourcePath);
                }
            } catch (IOException e) {
                errors++;
                MAM.LOGGER.error("I/O error loading spell: {}", resourcePath, e);
            } catch (Exception e) {
                errors++;
                MAM.LOGGER.error("Error parsing spell: {}", resourcePath, e);
            }
        }

        if (errors > 0) {
            MAM.LOGGER.warn("Loaded {} spells for {} with {} errors", count, school.getId(),
                    errors);
        }

        return count;
    }

    /**
     * Validate spell data for consistency.
     */
    private static boolean validateSpell(Spell spell) {
        if (spell == null || spell.getId() == null) {
            return false;
        }
        if (spell.getManaCost() < 0) {
            MAM.LOGGER.warn("Spell {} has negative mana cost", spell.getId());
            return false;
        }
        if (spell.getTier() < 1) {
            MAM.LOGGER.warn("Spell {} has invalid tier", spell.getId());
            return false;
        }
        return true;
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

    /**
     * Get the number of loaded spells.
     * 
     * @return Count of loaded spells
     */
    public static int getSpellCount() {
        return SPELLS.size();
    }

    /**
     * Check if a spell exists.
     * 
     * @param id The spell identifier
     * @return true if the spell is loaded
     */
    public static boolean hasSpell(Identifier id) {
        return SPELLS.containsKey(id);
    }
}
