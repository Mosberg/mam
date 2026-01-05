package dk.mosberg.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import dk.mosberg.MAM;
import dk.mosberg.mana.ManaComponent;
import dk.mosberg.mana.ManaManager;
import dk.mosberg.mana.ManaPoolType;
import dk.mosberg.registry.MagicRegistry;
import dk.mosberg.spell.Spell;
import dk.mosberg.spell.SpellCaster;
import dk.mosberg.spell.SpellSchool;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.CommandSource;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

/**
 * Comprehensive magic command system under /magic root command. Provides spell, ritual, mana pool
 * management, and system reload functionality.
 */
public class MagicCommands {

    // Suggestion provider for spell IDs
    private static final SuggestionProvider<ServerCommandSource> SPELL_SUGGESTIONS =
            (context, builder) -> CommandSource.suggestMatching(
                    MagicRegistry.getAllSpells().stream().map(Spell::getId), builder);

    // Suggestion provider for spell schools
    private static final SuggestionProvider<ServerCommandSource> SCHOOL_SUGGESTIONS =
            (context, builder) -> CommandSource.suggestMatching(
                    java.util.Arrays.stream(SpellSchool.values()).map(s -> s.name().toLowerCase()),
                    builder);

    /**
     * Register all magic commands under /magic root.
     */
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher,
            CommandRegistryAccess registryAccess,
            CommandManager.RegistrationEnvironment environment) {

        dispatcher.register(CommandManager.literal("magic")
                .then(CommandManager.literal("help").executes(MagicCommands::showHelp))

                // === SPELL COMMANDS ===
                .then(CommandManager.literal("spell")
                        .then(CommandManager.literal("list").executes(MagicCommands::listAllSpells)
                                .then(CommandManager.argument("school", StringArgumentType.word())
                                        .suggests(SCHOOL_SUGGESTIONS)
                                        .executes(MagicCommands::listSpellsBySchool)))
                        .then(CommandManager.literal("cast").then(CommandManager
                                .argument("spell_id", StringArgumentType.string())
                                .suggests(SPELL_SUGGESTIONS).executes(MagicCommands::castSpell)))
                        .then(CommandManager.literal("info").then(CommandManager
                                .argument("spell_id", StringArgumentType.string())
                                .suggests(SPELL_SUGGESTIONS).executes(MagicCommands::spellInfo))))

                // === POOL (MANA) COMMANDS ===
                .then(CommandManager.literal("pool")
                        .then(CommandManager.literal("show").executes(MagicCommands::showPools))
                        .then(CommandManager.literal("set")
                                .then(CommandManager.literal("personal").then(CommandManager
                                        .argument("amount", DoubleArgumentType.doubleArg(0))
                                        .executes(ctx -> setPool(ctx, ManaPoolType.PERSONAL))))
                                .then(CommandManager.literal("aura")
                                        .then(CommandManager
                                                .argument("amount", DoubleArgumentType.doubleArg(0))
                                                .executes(ctx -> setPool(ctx, ManaPoolType.AURA))))
                                .then(CommandManager.literal("reserve").then(CommandManager
                                        .argument("amount", DoubleArgumentType.doubleArg(0))
                                        .executes(ctx -> setPool(ctx, ManaPoolType.RESERVE)))))
                        .then(CommandManager.literal("add")
                                .then(CommandManager.literal("personal").then(CommandManager
                                        .argument("amount", DoubleArgumentType.doubleArg(0))
                                        .executes(ctx -> addPool(ctx, ManaPoolType.PERSONAL))))
                                .then(CommandManager.literal("aura")
                                        .then(CommandManager
                                                .argument("amount", DoubleArgumentType.doubleArg(0))
                                                .executes(ctx -> addPool(ctx, ManaPoolType.AURA))))
                                .then(CommandManager.literal("reserve").then(CommandManager
                                        .argument("amount", DoubleArgumentType.doubleArg(0))
                                        .executes(ctx -> addPool(ctx, ManaPoolType.RESERVE)))))
                        .then(CommandManager.literal("restore")
                                .executes(MagicCommands::restorePools)))

                // === RITUAL COMMANDS ===
                .then(CommandManager.literal("ritual")
                        .then(CommandManager.literal("list").executes(MagicCommands::listRituals))
                        .then(CommandManager.literal("info")
                                .then(CommandManager
                                        .argument("ritual_id", StringArgumentType.string())
                                        .executes(MagicCommands::ritualInfo))))

                // === RELOAD COMMAND ===
                .then(CommandManager.literal("reload").executes(MagicCommands::reloadRegistry)));

        MAM.LOGGER.info("Registered magic commands");
    }

    /**
     * Show help for magic commands.
     */
    private static int showHelp(CommandContext<ServerCommandSource> ctx) {
        ctx.getSource().sendFeedback(
                () -> Text.literal("=== Magic Commands ===").formatted(Formatting.GOLD), false);
        ctx.getSource().sendFeedback(() -> Text.literal("/magic spell list [school] - List spells")
                .formatted(Formatting.AQUA), false);
        ctx.getSource().sendFeedback(() -> Text
                .literal("/magic spell cast <spell_id> - Cast a spell").formatted(Formatting.AQUA),
                false);
        ctx.getSource().sendFeedback(
                () -> Text.literal("/magic spell info <spell_id> - View spell details")
                        .formatted(Formatting.AQUA),
                false);
        ctx.getSource().sendFeedback(() -> Text.literal("/magic pool show - Show mana pools")
                .formatted(Formatting.GREEN), false);
        ctx.getSource().sendFeedback(() -> Text
                .literal("/magic pool set <pool> <amount> - Set pool").formatted(Formatting.GREEN),
                false);
        ctx.getSource()
                .sendFeedback(() -> Text.literal("/magic pool add <pool> <amount> - Add to pool")
                        .formatted(Formatting.GREEN), false);
        ctx.getSource().sendFeedback(() -> Text.literal("/magic pool restore - Restore all pools")
                .formatted(Formatting.GREEN), false);
        ctx.getSource().sendFeedback(() -> Text.literal("/magic ritual list - List rituals")
                .formatted(Formatting.LIGHT_PURPLE), false);
        ctx.getSource().sendFeedback(() -> Text.literal("/magic reload - Reload spells/rituals")
                .formatted(Formatting.YELLOW), false);
        return 1;
    }

    // === SPELL COMMANDS IMPLEMENTATION ===

    private static int listAllSpells(CommandContext<ServerCommandSource> ctx) {
        var spells = MagicRegistry.getAllSpells();

        ctx.getSource().sendFeedback(() -> Text
                .literal("=== All Spells (" + spells.size() + ") ===").formatted(Formatting.GOLD),
                false);

        for (SpellSchool school : SpellSchool.values()) {
            var schoolSpells = MagicRegistry.getSpellsBySchool(school);
            if (!schoolSpells.isEmpty()) {
                ctx.getSource()
                        .sendFeedback(() -> Text.literal(school.getDisplayName() + ": ")
                                .formatted(Formatting.AQUA)
                                .append(Text.literal(String.valueOf(schoolSpells.size()))
                                        .formatted(Formatting.WHITE)),
                                false);
            }
        }

        return 1;
    }

    private static int listSpellsBySchool(CommandContext<ServerCommandSource> ctx) {
        String schoolName = StringArgumentType.getString(ctx, "school").toUpperCase();

        try {
            SpellSchool school = SpellSchool.valueOf(schoolName);
            var spells = MagicRegistry.getSpellsBySchool(school);

            ctx.getSource().sendFeedback(
                    () -> Text.literal("=== " + school.getDisplayName() + " Spells ===")
                            .formatted(Formatting.GOLD),
                    false);

            for (Spell spell : spells) {
                ctx.getSource().sendFeedback(() -> Text.literal("• " + spell.getId())
                        .formatted(Formatting.AQUA)
                        .append(Text.literal(
                                " (Tier " + spell.getTier() + ", " + spell.getManaCost() + " mana)")
                                .formatted(Formatting.GRAY)),
                        false);
            }

            return spells.size();
        } catch (IllegalArgumentException e) {
            ctx.getSource().sendError(Text.literal("Unknown school: " + schoolName));
            return 0;
        }
    }

    private static int castSpell(CommandContext<ServerCommandSource> ctx) {
        ServerPlayerEntity player = ctx.getSource().getPlayer();
        if (player == null) {
            ctx.getSource().sendError(Text.literal("Only players can cast spells"));
            return 0;
        }

        String spellId = StringArgumentType.getString(ctx, "spell_id");
        Spell spell = MagicRegistry.getSpell(spellId);

        if (spell == null) {
            ctx.getSource().sendError(Text.literal("Unknown spell: " + spellId));
            return 0;
        }

        if (SpellCaster.castSpell(player, spell)) {
            return 1;
        }

        return 0;
    }

    private static int spellInfo(CommandContext<ServerCommandSource> ctx) {
        String spellId = StringArgumentType.getString(ctx, "spell_id");
        Spell spell = MagicRegistry.getSpell(spellId);

        if (spell == null) {
            ctx.getSource().sendError(Text.literal("Unknown spell: " + spellId));
            return 0;
        }

        ctx.getSource().sendFeedback(
                () -> Text.literal("=== " + spell.getId() + " ===").formatted(Formatting.GOLD),
                false);
        ctx.getSource()
                .sendFeedback(() -> Text.literal("School: " + spell.getSchool().getDisplayName())
                        .formatted(Formatting.AQUA), false);
        ctx.getSource().sendFeedback(() -> Text.literal("Cast Type: " + spell.getCastType().name())
                .formatted(Formatting.YELLOW), false);
        ctx.getSource().sendFeedback(
                () -> Text.literal("Mana Cost: " + spell.getManaCost()).formatted(Formatting.GREEN),
                false);
        ctx.getSource().sendFeedback(
                () -> Text.literal("Tier: " + spell.getTier()).formatted(Formatting.LIGHT_PURPLE),
                false);

        if (spell.getDamage() > 0) {
            ctx.getSource().sendFeedback(
                    () -> Text.literal("Damage: " + spell.getDamage()).formatted(Formatting.RED),
                    false);
        }

        return 1;
    }

    // === POOL (MANA) COMMANDS IMPLEMENTATION ===

    private static int showPools(CommandContext<ServerCommandSource> ctx) {
        ServerPlayerEntity player = ctx.getSource().getPlayer();
        if (player == null) {
            ctx.getSource().sendError(Text.literal("Only players can check mana pools"));
            return 0;
        }

        ManaComponent mana = ManaManager.getComponent(player);

        ctx.getSource().sendFeedback(
                () -> Text.literal("=== Mana Pools ===").formatted(Formatting.GOLD), false);

        for (ManaPoolType type : ManaPoolType.values()) {
            double current = mana.getPool(type).getCurrent();
            double max = mana.getPool(type).getMax();
            double percent = (current / max) * 100;

            Formatting color = percent > 75 ? Formatting.GREEN
                    : percent > 25 ? Formatting.YELLOW : Formatting.RED;

            ctx.getSource().sendFeedback(() -> Text.literal(type.getDisplayName() + ": ")
                    .formatted(Formatting.AQUA)
                    .append(Text.literal(String.format("%.1f/%.1f", current, max)).formatted(color))
                    .append(Text.literal(String.format(" (%.1f%%)", percent))
                            .formatted(Formatting.GRAY)),
                    false);
        }

        return 1;
    }

    private static int setPool(CommandContext<ServerCommandSource> ctx, ManaPoolType poolType) {
        ServerPlayerEntity player = ctx.getSource().getPlayer();
        if (player == null) {
            ctx.getSource().sendError(Text.literal("Only players can set mana"));
            return 0;
        }

        double amount = DoubleArgumentType.getDouble(ctx, "amount");
        ManaComponent mana = ManaManager.getComponent(player);
        mana.getPool(poolType).setCurrent(amount);

        ctx.getSource().sendFeedback(
                () -> Text.literal("Set " + poolType.getDisplayName() + " to " + amount)
                        .formatted(Formatting.GREEN),
                true);

        return 1;
    }

    private static int addPool(CommandContext<ServerCommandSource> ctx, ManaPoolType poolType) {
        ServerPlayerEntity player = ctx.getSource().getPlayer();
        if (player == null) {
            ctx.getSource().sendError(Text.literal("Only players can add mana"));
            return 0;
        }

        double amount = DoubleArgumentType.getDouble(ctx, "amount");
        ManaComponent mana = ManaManager.getComponent(player);
        mana.getPool(poolType).add(amount);

        ctx.getSource().sendFeedback(
                () -> Text.literal("Added " + amount + " to " + poolType.getDisplayName())
                        .formatted(Formatting.GREEN),
                true);

        return 1;
    }

    private static int restorePools(CommandContext<ServerCommandSource> ctx) {
        ServerPlayerEntity player = ctx.getSource().getPlayer();
        if (player == null) {
            ctx.getSource().sendError(Text.literal("Only players can restore mana"));
            return 0;
        }

        ManaComponent mana = ManaManager.getComponent(player);
        for (ManaPoolType type : ManaPoolType.values()) {
            mana.getPool(type).restore();
        }

        ctx.getSource().sendFeedback(
                () -> Text.literal("Restored all mana pools").formatted(Formatting.GREEN), true);

        return 1;
    }

    // === RITUAL COMMANDS IMPLEMENTATION ===

    private static int listRituals(CommandContext<ServerCommandSource> ctx) {
        var rituals = MagicRegistry.getAllRituals();

        ctx.getSource().sendFeedback(() -> Text.literal("=== Rituals (" + rituals.size() + ") ===")
                .formatted(Formatting.LIGHT_PURPLE), false);

        for (var ritual : rituals) {
            ctx.getSource()
                    .sendFeedback(() -> Text.literal("• " + ritual.getId())
                            .formatted(Formatting.LIGHT_PURPLE)
                            .append(Text.literal(" (" + ritual.getCategory().getDisplayName() + ")")
                                    .formatted(Formatting.GRAY)),
                            false);
        }

        return rituals.size();
    }

    private static int ritualInfo(CommandContext<ServerCommandSource> ctx) {
        String ritualId = StringArgumentType.getString(ctx, "ritual_id");
        var ritual = MagicRegistry.getRitual(ritualId);

        if (ritual == null) {
            ctx.getSource().sendError(Text.literal("Unknown ritual: " + ritualId));
            return 0;
        }

        ctx.getSource().sendFeedback(() -> Text.literal("=== " + ritual.getId() + " ===")
                .formatted(Formatting.LIGHT_PURPLE), false);
        ctx.getSource().sendFeedback(
                () -> Text.literal("Category: " + ritual.getCategory().getDisplayName())
                        .formatted(Formatting.AQUA),
                false);
        ctx.getSource()
                .sendFeedback(() -> Text
                        .literal("Required Items: " + String.join(", ", ritual.getRitualItems()))
                        .formatted(Formatting.YELLOW), false);

        return 1;
    }

    // === RELOAD COMMAND IMPLEMENTATION ===

    private static int reloadRegistry(CommandContext<ServerCommandSource> ctx) {
        try {
            MagicRegistry.reload();
            ctx.getSource().sendFeedback(() -> Text.literal("Reloaded spell and ritual registry")
                    .formatted(Formatting.GREEN), true);
            return 1;
        } catch (Exception e) {
            ctx.getSource().sendError(Text.literal("Failed to reload registry: " + e.getMessage()));
            MAM.LOGGER.error("Failed to reload registry", e);
            return 0;
        }
    }
}
