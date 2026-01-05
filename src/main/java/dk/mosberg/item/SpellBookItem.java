package dk.mosberg.item;

import java.util.List;
import dk.mosberg.registry.MagicRegistry;
import dk.mosberg.spell.Spell;
import dk.mosberg.spell.SpellSchool;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

/**
 * Spell book item that stores a specific spell and can be used to learn or cast it. Right-click to
 * cast the stored spell. Shift+Right-click to view spell details.
 */
public class SpellBookItem extends Item {
    private final SpellSchool school;

    public SpellBookItem(SpellSchool school, Settings settings) {
        super(settings);
        this.school = school;
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        if (world.isClient()) {
            return ActionResult.PASS;
        }

        if (!(user instanceof ServerPlayerEntity player)) {
            return ActionResult.FAIL;
        }

        String spellId = getStoredSpellId(stack);
        if (spellId == null) {
            player.sendMessage(Text.literal("Empty spell book").formatted(Formatting.GRAY), true);
            return ActionResult.PASS;
        }

        Spell spell = MagicRegistry.getSpell(spellId);
        if (spell == null) {
            player.sendMessage(Text.literal("Unknown spell: " + spellId).formatted(Formatting.RED),
                    true);
            return ActionResult.FAIL;
        }

        if (user.isSneaking()) {
            // Display spell information
            player.sendMessage(
                    Text.literal("=== " + spell.getId() + " ===").formatted(Formatting.GOLD),
                    false);
            player.sendMessage(Text.literal("School: " + spell.getSchool().getDisplayName())
                    .formatted(Formatting.AQUA), false);
            player.sendMessage(Text.literal("Cast Type: " + spell.getCastType().name())
                    .formatted(Formatting.YELLOW), false);
            player.sendMessage(
                    Text.literal("Mana Cost: " + spell.getManaCost()).formatted(Formatting.GREEN),
                    false);
            player.sendMessage(
                    Text.literal("Tier: " + spell.getTier()).formatted(Formatting.LIGHT_PURPLE),
                    false);

            if (spell.getDamage() > 0) {
                player.sendMessage(
                        Text.literal("Damage: " + spell.getDamage()).formatted(Formatting.RED),
                        false);
            }

            return ActionResult.SUCCESS;
        }

        // Cast the spell
        if (dk.mosberg.spell.SpellCaster.castSpell(player, spell)) {
            user.getItemCooldownManager().set(stack, 60); // 3 second cooldown
            return ActionResult.SUCCESS;
        }

        return ActionResult.FAIL;
    }

    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip,
            TooltipType type) {
        tooltip.add(Text.literal("School: " + school.getDisplayName()).formatted(Formatting.AQUA));

        String spellId = getStoredSpellId(stack);
        if (spellId != null) {
            Spell spell = MagicRegistry.getSpell(spellId);
            if (spell != null) {
                tooltip.add(Text.literal("Spell: " + spell.getId()).formatted(Formatting.YELLOW));
                tooltip.add(
                        Text.literal("Mana: " + spell.getManaCost()).formatted(Formatting.GREEN));
                tooltip.add(Text.literal("Tier: " + spell.getTier())
                        .formatted(Formatting.LIGHT_PURPLE));
            } else {
                tooltip.add(Text.literal("Spell: " + spellId).formatted(Formatting.RED));
            }
        } else {
            tooltip.add(Text.literal("Empty").formatted(Formatting.GRAY));
        }

        tooltip.add(Text.literal(""));
        tooltip.add(
                Text.literal("Right-click to cast").formatted(Formatting.GRAY, Formatting.ITALIC));
        tooltip.add(Text.literal("Shift+Right-click for details").formatted(Formatting.GRAY,
                Formatting.ITALIC));
    }

    /**
     * Create a spell book with a stored spell.
     */
    public static ItemStack withSpell(SpellBookItem book, String spellId) {
        ItemStack stack = new ItemStack(book);
        setStoredSpellId(stack, spellId);
        return stack;
    }

    /**
     * Get the stored spell ID from the item stack.
     */
    public static String getStoredSpellId(ItemStack stack) {
        NbtComponent nbt = stack.get(DataComponentTypes.CUSTOM_DATA);
        if (nbt != null && !nbt.isEmpty()) {
            var compound = nbt.copyNbt();
            if (compound.contains("spell_id")) {
                return compound.getString("spell_id").orElse(null);
            }
        }
        return null;
    }

    /**
     * Set the stored spell ID on the item stack.
     */
    public static void setStoredSpellId(ItemStack stack, String spellId) {
        NbtCompound nbt = new NbtCompound();
        nbt.putString("spell_id", spellId);
        stack.set(DataComponentTypes.CUSTOM_DATA, NbtComponent.of(nbt));
    }

    public SpellSchool getSchool() {
        return school;
    }
}
