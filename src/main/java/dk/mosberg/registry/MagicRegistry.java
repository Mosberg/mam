package dk.mosberg.registry;

import java.util.Collection;
import dk.mosberg.MAM;
import dk.mosberg.ritual.Ritual;
import dk.mosberg.ritual.RitualCategory;
import dk.mosberg.ritual.RitualLoader;
import dk.mosberg.spell.Spell;
import dk.mosberg.spell.SpellLoader;
import dk.mosberg.spell.SpellSchool;
import net.minecraft.util.Identifier;

/**
 * Central registry providing access to all spells and rituals.
 */
public class MagicRegistry {

    /**
     * Initialize the magic registry by loading all spells and rituals.
     */
    public static void initialize() {
        MAM.LOGGER.info("Initializing Magic Registry...");

        // Load spells
        SpellLoader.loadSpells();

        // Load rituals
        RitualLoader.loadRituals();

        MAM.LOGGER.info("Magic Registry initialized with {} spells and {} rituals",
                SpellLoader.getAllSpells().size(), RitualLoader.getAllRituals().size());
    }

    /**
     * Reload all magic data from disk.
     */
    public static void reload() {
        MAM.LOGGER.info("Reloading Magic Registry...");
        SpellLoader.reload();
        RitualLoader.reload();
        MAM.LOGGER.info("Magic Registry reloaded");
    }

    // ===== SPELL METHODS =====

    /**
     * Get a spell by its identifier.
     */
    public static Spell getSpell(Identifier id) {
        return SpellLoader.getSpell(id);
    }

    /**
     * Get a spell by namespace and path.
     */
    public static Spell getSpell(String namespace, String path) {
        return getSpell(Identifier.of(namespace, path));
    }

    /**
     * Get all spells.
     */
    public static Collection<Spell> getAllSpells() {
        return SpellLoader.getAllSpells().values();
    }

    /**
     * Get all spells of a specific school.
     */
    public static Collection<Spell> getSpellsBySchool(SpellSchool school) {
        return SpellLoader.getSpellsBySchool(school).values();
    }

    /**
     * Check if a spell exists.
     */
    public static boolean hasSpell(Identifier id) {
        return SpellLoader.getSpell(id) != null;
    }

    // ===== RITUAL METHODS =====

    /**
     * Get a ritual by its identifier.
     */
    public static Ritual getRitual(Identifier id) {
        return RitualLoader.getRitual(id);
    }

    /**
     * Get a ritual by namespace and path.
     */
    public static Ritual getRitual(String namespace, String path) {
        return getRitual(Identifier.of(namespace, path));
    }

    /**
     * Get all rituals.
     */
    public static Collection<Ritual> getAllRituals() {
        return RitualLoader.getAllRituals().values();
    }

    /**
     * Get all rituals of a specific category.
     */
    public static Collection<Ritual> getRitualsByCategory(RitualCategory category) {
        return RitualLoader.getRitualsByCategory(category).values();
    }

    /**
     * Check if a ritual exists.
     */
    public static boolean hasRitual(Identifier id) {
        return RitualLoader.getRitual(id) != null;
    }
}
