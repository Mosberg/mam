package dk.mosberg.registry;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import dk.mosberg.MAM;
import dk.mosberg.ritual.Ritual;
import dk.mosberg.ritual.RitualCategory;
import dk.mosberg.ritual.RitualLoader;
import dk.mosberg.spell.CastType;
import dk.mosberg.spell.Spell;
import dk.mosberg.spell.SpellLoader;
import dk.mosberg.spell.SpellSchool;
import net.minecraft.util.Identifier;

/**
 * Central registry providing access to all spells and rituals. Provides caching, validation, and
 * query methods for magic content.
 */
public class MagicRegistry {
    private static boolean initialized = false;

    /**
     * Initialize the magic registry by loading all spells and rituals.
     */
    public static void initialize() {
        if (initialized) {
            MAM.LOGGER.warn("Magic Registry already initialized");
            return;
        }

        MAM.LOGGER.info("Initializing Magic Registry...");
        long startTime = System.currentTimeMillis();

        try {
            // Load spells
            SpellLoader.loadSpells();

            // Load rituals
            RitualLoader.loadRituals();

            int spellCount = SpellLoader.getAllSpells().size();
            int ritualCount = RitualLoader.getAllRituals().size();
            long duration = System.currentTimeMillis() - startTime;

            initialized = true;
            MAM.LOGGER.info("Magic Registry initialized with {} spells and {} rituals in {}ms",
                    spellCount, ritualCount, duration);

            logStatistics();
        } catch (Exception e) {
            MAM.LOGGER.error("Failed to initialize Magic Registry", e);
            throw new RuntimeException("Magic Registry initialization failed", e);
        }
    }

    /**
     * Log detailed statistics about loaded content.
     */
    private static void logStatistics() {
        MAM.LOGGER.info("=== Magic Registry Statistics ===");

        // Spell statistics
        for (SpellSchool school : SpellSchool.values()) {
            int count = getSpellsBySchool(school).size();
            if (count > 0) {
                MAM.LOGGER.info("  {} School: {} spells", school.getDisplayName(), count);
            }
        }

        // Ritual statistics
        for (RitualCategory category : RitualCategory.values()) {
            int count = getRitualsByCategory(category).size();
            if (count > 0) {
                MAM.LOGGER.info("  {} Rituals: {} rituals", category.getDisplayName(), count);
            }
        }
    }

    /**
     * Reload all magic data from disk.
     */
    public static void reload() {
        MAM.LOGGER.info("Reloading Magic Registry...");
        long startTime = System.currentTimeMillis();

        try {
            SpellLoader.reload();
            RitualLoader.reload();

            long duration = System.currentTimeMillis() - startTime;
            MAM.LOGGER.info("Magic Registry reloaded in {}ms", duration);
            logStatistics();
        } catch (Exception e) {
            MAM.LOGGER.error("Failed to reload Magic Registry", e);
        }
    }

    /**
     * Check if registry is initialized.
     */
    public static boolean isInitialized() {
        return initialized;
    }

    // ===== SPELL METHODS =====

    /**
     * Get a spell by its identifier.
     *
     * @param id The spell identifier
     * @return The spell, or null if not found
     */
    public static Spell getSpell(Identifier id) {
        if (id == null) {
            return null;
        }
        return SpellLoader.getSpell(id);
    }

    /**
     * Get a spell by namespace and path.
     *
     * @param namespace The namespace (e.g., "mam")
     * @param path The path (e.g., "fireball")
     * @return The spell, or null if not found
     */
    public static Spell getSpell(String namespace, String path) {
        if (namespace == null || path == null) {
            return null;
        }
        return getSpell(Identifier.of(namespace, path));
    }

    /**
     * Get a spell by string identifier (e.g., "mam:fireball" or "fireball"). If no namespace is
     * provided, defaults to "mam".
     *
     * @param idString The spell identifier as a string
     * @return The spell, or null if not found
     */
    public static Spell getSpell(String idString) {
        if (idString == null || idString.isEmpty()) {
            return null;
        }

        // If it contains a colon, parse as full identifier
        if (idString.contains(":")) {
            return getSpell(Identifier.of(idString));
        }

        // Otherwise, assume "mam" namespace
        return getSpell(MAM.MOD_ID, idString);
    }

    /**
     * Get all spells.
     *
     * @return Immutable collection of all spells
     */
    public static Collection<Spell> getAllSpells() {
        return SpellLoader.getAllSpells().values();
    }

    /**
     * Get all spells of a specific school.
     *
     * @param school The spell school
     * @return Collection of spells in this school
     */
    public static Collection<Spell> getSpellsBySchool(SpellSchool school) {
        if (school == null) {
            return List.of();
        }
        return SpellLoader.getSpellsBySchool(school).values();
    }

    /**
     * Get spells by cast type.
     *
     * @param castType The cast type to filter by
     * @return Collection of spells with this cast type
     */
    public static Collection<Spell> getSpellsByCastType(CastType castType) {
        if (castType == null) {
            return List.of();
        }
        return getAllSpells().stream().filter(spell -> spell.getCastType() == castType)
                .collect(Collectors.toList());
    }

    /**
     * Get spells by tier.
     *
     * @param tier The tier level (1-10)
     * @return Collection of spells at this tier
     */
    public static Collection<Spell> getSpellsByTier(int tier) {
        if (tier < 1 || tier > 10) {
            return List.of();
        }
        return getAllSpells().stream().filter(spell -> spell.getTier() == tier)
                .collect(Collectors.toList());
    }

    /**
     * Check if a spell exists.
     *
     * @param id The spell identifier
     * @return true if spell exists
     */
    public static boolean hasSpell(Identifier id) {
        return id != null && SpellLoader.hasSpell(id);
    }

    /**
     * Get total spell count.
     *
     * @return Number of loaded spells
     */
    public static int getSpellCount() {
        return SpellLoader.getSpellCount();
    }

    // ===== RITUAL METHODS =====

    /**
     * Get a ritual by its identifier.
     *
     * @param id The ritual identifier
     * @return The ritual, or null if not found
     */
    public static Ritual getRitual(Identifier id) {
        if (id == null) {
            return null;
        }
        return RitualLoader.getRitual(id);
    }

    /**
     * Get a ritual by namespace and path.
     *
     * @param namespace The namespace (e.g., "mam")
     * @param path The path (e.g., "apotheosis_ritual")
     * @return The ritual, or null if not found
     */
    public static Ritual getRitual(String namespace, String path) {
        if (namespace == null || path == null) {
            return null;
        }
        return getRitual(Identifier.of(namespace, path));
    }

    /**
     * Get a ritual by string identifier (e.g., "mam:apotheosis_ritual" or "apotheosis_ritual"). If
     * no namespace is provided, defaults to "mam".
     *
     * @param idString The ritual identifier as a string
     * @return The ritual, or null if not found
     */
    public static Ritual getRitual(String idString) {
        if (idString == null || idString.isEmpty()) {
            return null;
        }

        // If it contains a colon, parse as full identifier
        if (idString.contains(":")) {
            return getRitual(Identifier.of(idString));
        }

        // Otherwise, assume "mam" namespace
        return getRitual(MAM.MOD_ID, idString);
    }

    /**
     * Get all rituals.
     *
     * @return Immutable collection of all rituals
     */
    public static Collection<Ritual> getAllRituals() {
        return RitualLoader.getAllRituals().values();
    }

    /**
     * Get all rituals of a specific category.
     *
     * @param category The ritual category
     * @return Collection of rituals in this category
     */
    public static Collection<Ritual> getRitualsByCategory(RitualCategory category) {
        if (category == null) {
            return List.of();
        }
        return RitualLoader.getRitualsByCategory(category).values();
    }

    /**
     * Check if a ritual exists.
     *
     * @param id The ritual identifier
     * @return true if ritual exists
     */
    public static boolean hasRitual(Identifier id) {
        return id != null && RitualLoader.hasRitual(id);
    }

    /**
     * Get total ritual count.
     *
     * @return Number of loaded rituals
     */
    public static int getRitualCount() {
        return RitualLoader.getRitualCount();
    }

    // ===== UTILITY METHODS =====

    /**
     * Get total content count (spells + rituals).
     *
     * @return Total number of loaded magic content
     */
    public static int getTotalContentCount() {
        return getSpellCount() + getRitualCount();
    }
}
