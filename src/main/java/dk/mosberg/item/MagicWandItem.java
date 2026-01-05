package dk.mosberg.item;

import java.util.List;
import dk.mosberg.mana.ManaComponent;
import dk.mosberg.mana.ManaManager;
import dk.mosberg.registry.MagicRegistry;
import dk.mosberg.spell.Spell;
import dk.mosberg.spell.SpellCaster;
import dk.mosberg.spell.SpellSchool;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

/**
 * Magic wand item that allows casting spells by right-clicking. Different wand tiers have different
 * mana efficiency and power.
 */
public class MagicWandItem extends Item {
    private final WandTier tier;
    private final SpellSchool school;

    public MagicWandItem(WandTier tier, SpellSchool school, Settings settings) {
        super(settings);
        this.tier = tier;
        this.school = school;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (world.isClient) {
            return TypedActionResult.pass(user.getStackInHand(hand));
        }

        if (!(user instanceof ServerPlayerEntity player)) {
            return TypedActionResult.fail(user.getStackInHand(hand));
        }

        // Get the first spell of the wand's school
        Spell spell = MagicRegistry.getSpellsBySchool(school).stream().findFirst().orElse(null);

        if (spell == null) {
            player.sendMessage(Text.literal("No spells available for " + school.getDisplayName())
                    .formatted(Formatting.RED), true);
            return TypedActionResult.fail(user.getStackInHand(hand));
        }

        // Cast the spell with wand bonus
        ManaComponent mana = ManaManager.getComponent(player);
        double manaCost = spell.getManaCost() * tier.getManaCostMultiplier();

        if (!mana.hasEnoughMana(spell.getPrimaryPool(), manaCost)) {
            player.sendMessage(Text.literal("Not enough mana!").formatted(Formatting.RED), true);
            return TypedActionResult.fail(user.getStackInHand(hand));
        }

        if (SpellCaster.castSpell(player, spell)) {
            user.getItemCooldownManager().set(this, tier.getCooldownTicks());
            return TypedActionResult.success(user.getStackInHand(hand));
        }

        return TypedActionResult.fail(user.getStackInHand(hand));
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip,
            TooltipType type) {
        tooltip.add(Text.literal("School: " + school.getDisplayName()).formatted(Formatting.AQUA));
        tooltip.add(Text.literal("Tier: " + tier.name()).formatted(Formatting.GOLD));
        tooltip.add(Text.literal("Mana Cost: " + (int) (tier.getManaCostMultiplier() * 100) + "%")
                .formatted(Formatting.GREEN));
        tooltip.add(Text.literal("Cooldown: " + tier.getCooldownTicks() / 20.0 + "s")
                .formatted(Formatting.GRAY));
    }

    public WandTier getTier() {
        return tier;
    }

    public SpellSchool getSchool() {
        return school;
    }

    /**
     * Wand tier determines power and efficiency.
     */
    public enum WandTier {
        NOVICE(1.0, 40, 64), // 100% mana cost, 2s cooldown
        APPRENTICE(0.9, 30, 128), // 90% mana cost, 1.5s cooldown
        ADEPT(0.8, 20, 192), // 80% mana cost, 1s cooldown
        EXPERT(0.7, 15, 256), // 70% mana cost, 0.75s cooldown
        MASTER(0.6, 10, 320); // 60% mana cost, 0.5s cooldown

        private final double manaCostMultiplier;
        private final int cooldownTicks;
        private final int durability;

        WandTier(double manaCostMultiplier, int cooldownTicks, int durability) {
            this.manaCostMultiplier = manaCostMultiplier;
            this.cooldownTicks = cooldownTicks;
            this.durability = durability;
        }

        public double getManaCostMultiplier() {
            return manaCostMultiplier;
        }

        public int getCooldownTicks() {
            return cooldownTicks;
        }

        public int getDurability() {
            return durability;
        }
    }
}
