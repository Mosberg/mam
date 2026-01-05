package dk.mosberg.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import dk.mosberg.MAM;
import dk.mosberg.mana.ManaComponent;
import dk.mosberg.mana.ManaManager;
import dk.mosberg.mana.ManaPoolType;
import dk.mosberg.registry.MagicRegistry;
import dk.mosberg.spell.Spell;
import dk.mosberg.spell.SpellCaster;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

/**
 * Commands for managing and testing mana and spell systems. Provides /mana and /spell commands for
 * players and admins.
 */
public class ManaCommands {

    /**
     * Register all mana-related commands.
     */
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher,
            CommandRegistryAccess registryAccess,
            CommandManager.RegistrationEnvironment environment) {

        // /mana - Display current mana
        dispatcher.register(CommandManager.literal("mana").executes(ManaCommands::displayMana)

                // /mana set <pool> <amount>
                .then(CommandManager.literal("set").then(CommandManager.literal("personal")
                        .then(CommandManager.argument("amount", DoubleArgumentType.doubleArg(0))
                                .executes(ctx -> setMana(ctx, ManaPoolType.PERSONAL))))
                        .then(CommandManager.literal("aura")
                                .then(CommandManager
                                        .argument("amount", DoubleArgumentType.doubleArg(0))
                                        .executes(ctx -> setMana(ctx, ManaPoolType.AURA))))
                        .then(CommandManager.literal("reserve")
                                .then(CommandManager
                                        .argument("amount", DoubleArgumentType.doubleArg(0))
                                        .executes(ctx -> setMana(ctx, ManaPoolType.RESERVE)))))

                // /mana add <pool> <amount>
                .then(CommandManager.literal("add").then(CommandManager.literal("personal")
                        .then(CommandManager.argument("amount", DoubleArgumentType.doubleArg(0))
                                .executes(ctx -> addMana(ctx, ManaPoolType.PERSONAL))))
                        .then(CommandManager.literal("aura")
                                .then(CommandManager
                                        .argument("amount", DoubleArgumentType.doubleArg(0))
                                        .executes(ctx -> addMana(ctx, ManaPoolType.AURA))))
                        .then(CommandManager.literal("reserve")
                                .then(CommandManager
                                        .argument("amount", DoubleArgumentType.doubleArg(0))
                                        .executes(ctx -> addMana(ctx, ManaPoolType.RESERVE)))))

                // /mana restore - Restore all mana pools
                .then(CommandManager.literal("restore").executes(ManaCommands::restoreMana)));

        // /spell - Spell management commands
        dispatcher.register(CommandManager.literal("spell")

                // /spell list [school] - List available spells
                .then(CommandManager.literal("list").executes(ManaCommands::listSpells))

                // /spell cast <spell_id> - Cast a spell
                .then(CommandManager.literal("cast")
                        .then(CommandManager.argument("spell", IntegerArgumentType.integer(0))
                                .executes(ManaCommands::castSpellByIndex)))

                // /spell info - Registry information
                .then(CommandManager.literal("info").executes(ManaCommands::spellInfo)));

        MAM.LOGGER.info("Registered mana and spell commands");
    }

    /**
     * Display current mana pools.
     */
    private static int displayMana(CommandContext<ServerCommandSource> ctx) {
        ServerPlayerEntity player = ctx.getSource().getPlayer();
        if (player == null) {
            ctx.getSource().sendError(Text.literal("Only players can use this command"));
            return 0;
        }

        ManaComponent mana = ManaManager.getComponent(player);

        ctx.getSource().sendFeedback(
                () -> Text.literal("=== Mana Pools ===").formatted(Formatting.GOLD), false);

        for (ManaPoolType type : ManaPoolType.values()) {
            double current = mana.getPool(type).getCurrent();
            double max = mana.getPool(type).getMax();
            double percent = (current / max) * 100;
            String displayName = type.getDisplayName();

            ctx.getSource()
                    .sendFeedback(
                            () -> Text.literal(String.format("%s: %.1f / %.1f (%.0f%%)",
                                    displayName, current, max, percent)).formatted(Formatting.AQUA),
                            false);
        }

        return 1;
    }

    /**
     * Set mana for a specific pool.
     */
    private static int setMana(CommandContext<ServerCommandSource> ctx, ManaPoolType poolType) {
        ServerPlayerEntity player = ctx.getSource().getPlayer();
        if (player == null) {
            ctx.getSource().sendError(Text.literal("Only players can use this command"));
            return 0;
        }

        double amount = DoubleArgumentType.getDouble(ctx, "amount");
        ManaComponent mana = ManaManager.getComponent(player);
        mana.getPool(poolType).set(amount);
        String displayName = poolType.getDisplayName();

        ctx.getSource()
                .sendFeedback(() -> Text
                        .literal(String.format("Set %s mana to %.1f", displayName, amount))
                        .formatted(Formatting.GREEN), true);

        return 1;
    }

    /**
     * Add mana to a specific pool.
     */
    private static int addMana(CommandContext<ServerCommandSource> ctx, ManaPoolType poolType) {
        ServerPlayerEntity player = ctx.getSource().getPlayer();
        if (player == null) {
            ctx.getSource().sendError(Text.literal("Only players can use this command"));
            return 0;
        }

        double amount = DoubleArgumentType.getDouble(ctx, "amount");
        ManaComponent mana = ManaManager.getComponent(player);
        mana.add(poolType, amount);
        String displayName = poolType.getDisplayName();

        ctx.getSource()
                .sendFeedback(() -> Text
                        .literal(String.format("Added %.1f to %s mana", amount, displayName))
                        .formatted(Formatting.GREEN), true);

        return 1;
    }

    /**
     * Restore all mana pools to maximum.
     */
    private static int restoreMana(CommandContext<ServerCommandSource> ctx) {
        ServerPlayerEntity player = ctx.getSource().getPlayer();
        if (player == null) {
            ctx.getSource().sendError(Text.literal("Only players can use this command"));
            return 0;
        }

        ManaComponent mana = ManaManager.getComponent(player);
        mana.restoreAllPools();

        ctx.getSource().sendFeedback(
                () -> Text.literal("Restored all mana pools!").formatted(Formatting.GREEN), true);

        return 1;
    }

    /**
     * List all available spells.
     */
    private static int listSpells(CommandContext<ServerCommandSource> ctx) {
        ctx.getSource().sendFeedback(
                () -> Text.literal("=== Available Spells ===").formatted(Formatting.GOLD), false);

        int index = 0;
        for (Spell spell : MagicRegistry.getAllSpells()) {
            final int spellIndex = index;
            final String spellPath = spell.getId().getPath();
            final String schoolName = spell.getSchool().getDisplayName();
            final int tier = spell.getTier();
            final double manaCost = spell.getManaCost();
            ctx.getSource()
                    .sendFeedback(() -> Text
                            .literal(String.format("[%d] %s (%s, Tier %d, %.1f mana)", spellIndex,
                                    spellPath, schoolName, tier, manaCost))
                            .formatted(Formatting.AQUA), false);
            index++;
        }

        final int totalSpells = index;
        ctx.getSource().sendFeedback(() -> Text
                .literal(String.format("Total: %d spells", totalSpells)).formatted(Formatting.GRAY),
                false);

        return 1;
    }

    /**
     * Cast a spell by index from list.
     */
    private static int castSpellByIndex(CommandContext<ServerCommandSource> ctx) {
        ServerPlayerEntity player = ctx.getSource().getPlayer();
        if (player == null) {
            ctx.getSource().sendError(Text.literal("Only players can use this command"));
            return 0;
        }

        int index = IntegerArgumentType.getInteger(ctx, "spell");

        // Get spell by index
        var spells = MagicRegistry.getAllSpells().stream().toList();
        if (index < 0 || index >= spells.size()) {
            ctx.getSource().sendError(
                    Text.literal("Invalid spell index! Use /spell list to see available spells"));
            return 0;
        }

        Spell spell = spells.get(index);
        boolean success = SpellCaster.castSpell(player, spell);

        if (success) {
            ctx.getSource().sendFeedback(
                    () -> Text.literal("Successfully cast " + spell.getId().getPath() + "!")
                            .formatted(Formatting.GREEN),
                    true);
            return 1;
        } else {
            // Error message already sent by SpellCaster
            return 0;
        }
    }

    /**
     * Display spell registry information.
     */
    private static int spellInfo(CommandContext<ServerCommandSource> ctx) {
        int spellCount = MagicRegistry.getSpellCount();
        int ritualCount = MagicRegistry.getRitualCount();

        ctx.getSource().sendFeedback(
                () -> Text.literal("=== Magic Registry Info ===").formatted(Formatting.GOLD),
                false);
        ctx.getSource().sendFeedback(() -> Text
                .literal(String.format("Spells Loaded: %d", spellCount)).formatted(Formatting.AQUA),
                false);
        ctx.getSource()
                .sendFeedback(() -> Text.literal(String.format("Rituals Loaded: %d", ritualCount))
                        .formatted(Formatting.AQUA), false);
        ctx.getSource().sendFeedback(() -> Text
                .literal(String.format("Total Content: %d", MagicRegistry.getTotalContentCount()))
                .formatted(Formatting.GREEN), false);

        return 1;
    }
}
