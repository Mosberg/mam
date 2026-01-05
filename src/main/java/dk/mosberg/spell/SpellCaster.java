package dk.mosberg.spell;

import dk.mosberg.MAM;
import dk.mosberg.mana.ManaComponent;
import dk.mosberg.mana.ManaManager;
import dk.mosberg.mana.ManaPoolType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

/**
 * Utility class for casting spells with mana cost validation and effects. Provides safe spell
 * execution with proper error handling.
 */
public class SpellCaster {

    /**
     * Attempt to cast a spell for a player. Validates mana cost and applies effects.
     *
     * @param player The player casting the spell
     * @param spellId The spell identifier
     * @return true if spell was successfully cast
     */
    public static boolean castSpell(ServerPlayerEntity player, Identifier spellId) {
        if (player == null) {
            MAM.LOGGER.error("Cannot cast spell: player is null");
            return false;
        }

        if (spellId == null) {
            MAM.LOGGER.error("Cannot cast spell: spell ID is null");
            return false;
        }

        // Get spell from registry
        Spell spell = SpellLoader.getSpell(spellId);
        if (spell == null) {
            sendErrorMessage(player, "Unknown spell: " + spellId);
            MAM.LOGGER.warn("Player {} attempted to cast unknown spell: {}",
                    player.getName().getString(), spellId);
            return false;
        }

        return castSpell(player, spell);
    }

    /**
     * Attempt to cast a spell for a player.
     *
     * @param player The player casting the spell
     * @param spell The spell to cast
     * @return true if spell was successfully cast
     */
    public static boolean castSpell(ServerPlayerEntity player, Spell spell) {
        if (player == null || spell == null) {
            MAM.LOGGER.error("Cannot cast spell: player or spell is null");
            return false;
        }

        // Get player's mana component
        ManaComponent mana = ManaManager.getComponent(player);

        // Check mana cost
        double manaCost = spell.getManaCost();
        ManaPoolType poolType = getPrimaryPoolForSchool(spell.getSchool());

        if (!mana.has(poolType, manaCost)) {
            sendErrorMessage(player, String.format("Insufficient %s mana! (need %.1f, have %.1f)",
                    poolType.getId(), manaCost, mana.getPool(poolType).getCurrent()));
            MAM.LOGGER.debug("Player {} has insufficient mana for spell {}",
                    player.getName().getString(), spell.getId());
            return false;
        }

        // Consume mana
        if (!mana.consume(poolType, manaCost)) {
            sendErrorMessage(player, "Failed to consume mana!");
            MAM.LOGGER.error("Failed to consume mana for player: {}", player.getName().getString());
            return false;
        }

        // Execute spell effects
        try {
            executeSpellEffects(player, spell);
            sendSuccessMessage(player, "Cast " + spell.getId().getPath() + "!");
            MAM.LOGGER.info("Player {} cast spell {} (cost: {} {})", player.getName().getString(),
                    spell.getId(), manaCost, poolType.getId());
            return true;
        } catch (Exception e) {
            // Refund mana on failure
            mana.add(poolType, manaCost);
            sendErrorMessage(player, "Spell casting failed!");
            MAM.LOGGER.error("Failed to execute spell {} for player {}", spell.getId(),
                    player.getName().getString(), e);
            return false;
        }
    }

    /**
     * Execute spell effects (placeholder for actual implementation). TODO: Implement spell effect
     * system
     */
    private static void executeSpellEffects(ServerPlayerEntity player, Spell spell) {
        // This is where actual spell effects would be implemented
        // For now, just log the cast
        MAM.LOGGER.debug("Executing effects for spell: {} (type: {})", spell.getId(),
                spell.getCastType());

        // TODO: Implement based on cast type:
        // - PROJECTILE: Launch projectile entity
        // - AOE: Apply effects in area
        // - UTILITY: Apply utility effects
        // - RITUAL: Start ritual sequence
        // - SYNERGY: Check for combo effects
    }

    /**
     * Get the primary mana pool for a spell school. Maps spell schools to appropriate mana pool
     * types.
     */
    private static ManaPoolType getPrimaryPoolForSchool(SpellSchool school) {
        // All spells use Personal mana pool for now
        // TODO: More sophisticated mapping if needed
        return ManaPoolType.PERSONAL;
    }

    /**
     * Send an error message to the player.
     */
    private static void sendErrorMessage(ServerPlayerEntity player, String message) {
        player.sendMessage(Text.literal(message).formatted(Formatting.RED), true); // actionBar =
                                                                                   // true (shows
                                                                                   // above hotbar)
    }

    /**
     * Send a success message to the player.
     */
    private static void sendSuccessMessage(ServerPlayerEntity player, String message) {
        player.sendMessage(Text.literal(message).formatted(Formatting.AQUA), true); // actionBar =
                                                                                    // true (shows
                                                                                    // above hotbar)
    }

    /**
     * Check if a player can cast a spell (without actually casting).
     *
     * @param player The player
     * @param spellId The spell identifier
     * @return true if player has enough mana
     */
    public static boolean canCastSpell(ServerPlayerEntity player, Identifier spellId) {
        if (player == null || spellId == null) {
            return false;
        }

        Spell spell = SpellLoader.getSpell(spellId);
        if (spell == null) {
            return false;
        }

        return canCastSpell(player, spell);
    }

    /**
     * Check if a player can cast a spell (without actually casting).
     *
     * @param player The player
     * @param spell The spell
     * @return true if player has enough mana
     */
    public static boolean canCastSpell(ServerPlayerEntity player, Spell spell) {
        if (player == null || spell == null) {
            return false;
        }

        ManaComponent mana = ManaManager.getComponent(player);
        ManaPoolType poolType = getPrimaryPoolForSchool(spell.getSchool());
        return mana.has(poolType, spell.getManaCost());
    }

    /**
     * Get mana cost for a spell.
     *
     * @param spellId The spell identifier
     * @return The mana cost, or -1 if spell not found
     */
    public static double getManaCost(Identifier spellId) {
        if (spellId == null) {
            return -1;
        }

        Spell spell = SpellLoader.getSpell(spellId);
        return spell != null ? spell.getManaCost() : -1;
    }
}
