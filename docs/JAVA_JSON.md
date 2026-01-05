# 💻 Mana & Magic - Java & JSON Technical Reference

**Version:** 1.0.0
**Last Updated:** January 5, 2026
**Mod ID:** `mam`

---

## 📖 Table of Contents

- [Project Structure](#project-structure)
- [Core Java Classes](#core-java-classes)
- [JSON Data Formats](#json-data-formats)
- [NBT Data Structures](#nbt-data-structures)
- [Networking Protocol](#networking-protocol)
- [Event System](#event-system)
- [Registry System](#registry-system)
- [Rendering System](#rendering-system)
- [Configuration System](#configuration-system)
- [Code Patterns](#code-patterns)

---

## 📁 Project Structure

### Source Directory Layout

```
src/main/java/dk/mosberg/
├── MAM.java                              # Main mod class
├── config/
│   ├── MAMConfig.java                    # Configuration manager
│   └── ConfigHelper.java                 # Environment variables
├── mana/
│   ├── ManaPool.java                     # Three-tier pool implementation
│   ├── ManaComponent.java                # Player-attached component
│   ├── ManaComponents.java               # Component registry
│   └── ManaComponentProvider.java        # Component data provider
├── spell/
│   ├── Spell.java                        # Spell interface
│   ├── SpellRegistry.java                # Spell registration
│   ├── SpellCaster.java                  # Cast execution
│   ├── SpellCooldownTracker.java         # Cooldown management
│   └── school/
│       ├── FireSpells.java
│       ├── IceSpells.java
│       ├── ArcaneSpells.java
│       └── ... (10 more schools)
├── ritual/
│   ├── Ritual.java                       # Ritual interface
│   ├── RitualRegistry.java               # Ritual registration
│   ├── RitualPattern.java                # Pattern validation
│   ├── RitualState.java                  # Active ritual state
│   └── category/
│       ├── AscensionRituals.java
│       ├── CosmicRituals.java
│       └── ... (11 more categories)
├── gemstone/
│   ├── Gemstone.java                     # Gemstone data class
│   ├── GemstoneRegistry.java             # Gemstone registration
│   ├── GemstoneItem.java                 # Item implementation
│   ├── GemstoneBlock.java                # Block implementation
│   └── GemstoneOre.java                  # Ore block
├── item/
│   ├── MagicWand.java                    # Basic wand item
│   ├── MagicStaff.java                   # Advanced staff item
│   ├── CatalystItem.java                 # Magic catalyst
│   └── ManaPotion.java                   # Mana restoration item
├── block/
│   ├── ArcaneAltar.java                  # Ritual altar block
│   ├── ManaNode.java                     # Mana node block entity
│   └── ManaSpring.java                   # Liquid mana source
├── entity/
│   ├── projectile/
│   │   ├── FireballEntity.java
│   │   ├── IceSpearEntity.java
│   │   └── MagicBoltEntity.java
│   └── summon/
│       ├── ArcaneGolemEntity.java
│       └── ElementalSpiritEntity.java
├── enchantment/
│   ├── CapacityEnchantment.java
│   ├── EfficiencyEnchantment.java
│   └── PotencyEnchantment.java
├── progression/
│   ├── MageLevel.java                    # Level tracking
│   ├── MageSpecialization.java           # School specializations
│   └── ProgressionTracker.java           # XP and advancement
├── worldgen/
│   ├── OreFeatures.java                  # Gemstone ore generation
│   ├── ManaNodeFeature.java              # Mana node placement
│   └── LeyLineFeature.java               # Ley line generation
├── network/
│   ├── packet/
│   │   ├── CastSpellPayload.java         # C2S spell casting
│   │   ├── SyncManaPayload.java          # S2C mana sync
│   │   └── RitualUpdatePayload.java      # S2C ritual state
│   └── PacketHandler.java                # Packet registration
├── command/
│   ├── ManaCommand.java                  # /mana command
│   ├── SpellCommand.java                 # /spell command
│   └── DebugCommand.java                 # /mamdebug command
├── event/
│   ├── ManaEvents.java                   # Mana event callbacks
│   ├── SpellEvents.java                  # Spell event callbacks
│   └── RitualEvents.java                 # Ritual event callbacks
└── util/
    ├── ManaPoolHelper.java               # Mana utility methods
    ├── ParticleHelper.java               # Particle spawning
    ├── MathUtil.java                     # Math utilities
    └── NbtUtil.java                      # NBT serialization
```

### Resources Directory Layout

```
src/main/resources/
├── fabric.mod.json                       # Mod metadata
├── mam.mixins.json                       # Mixin configuration
├── assets/mam/
│   ├── blockstates/
│   │   └── *.json                        # Block states
│   ├── models/
│   │   ├── block/
│   │   │   └── *.json                    # Block models
│   │   └── item/
│   │       └── *.json                    # Item models
│   ├── textures/
│   │   ├── block/
│   │   │   └── *.png                     # Block textures
│   │   ├── item/
│   │   │   └── *.png                     # Item textures
│   │   ├── entity/
│   │   │   └── *.png                     # Entity textures
│   │   └── gui/
│   │       ├── mana_bar.png              # HUD texture
│   │       └── sprites/container/slot/
│   │           └── gemstone_slots/       # Slot overlays
│   ├── lang/
│   │   └── en_us.json                    # Translations
│   ├── sounds.json                       # Sound events
│   └── icon.png                          # Mod icon
└── data/mam/
    ├── loot_table/
    │   └── blocks/
    │       └── *.json                    # Block loot tables
    ├── recipe/
    │   └── *.json                        # Crafting recipes
    ├── rituals/
    │   ├── ascension/
    │   ├── cosmic/
    │   └── ... (13 categories)
    ├── spells/
    │   ├── fire/
    │   ├── ice/
    │   └── ... (13 schools)
    ├── tags/
    │   ├── block/
    │   │   └── gemstone_ores.json
    │   └── item/
    │       └── gemstones.json
    ├── worldgen/
    │   ├── configured_feature/
    │   │   └── ore_gemstone.json
    │   └── placed_feature/
    │       └── ore_gemstone.json
    └── advancement/
        └── magic/
            └── *.json                    # Advancements
```

---

## 🔷 Core Java Classes

### 1. Main Mod Class

**File:** `src/main/java/dk/mosberg/MAM.java`

```java
package dk.mosberg;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dk.mosberg.config.MAMConfig;
import dk.mosberg.mana.ManaComponents;
import dk.mosberg.spell.SpellRegistry;
import dk.mosberg.ritual.RitualRegistry;
import dk.mosberg.gemstone.GemstoneRegistry;
import dk.mosberg.network.PacketHandler;
import dk.mosberg.command.ManaCommand;

/**
 * Main entry point for Mana & Magic mod.
 * Initializes all systems and registers core components.
 */
public class MAM implements ModInitializer {

    // Mod Constants
    public static final String MOD_ID = "mam";
    public static final String MOD_NAME = "Mana And Magic";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing {} v{}", MOD_NAME, getModVersion());

        // Load configuration
        MAMConfig.load();
        LOGGER.info("Configuration loaded");

        // Register components
        ManaComponents.register();
        LOGGER.info("Mana components registered");

        // Register game content
        GemstoneRegistry.register();
        SpellRegistry.register();
        RitualRegistry.register();
        LOGGER.info("Game content registered");

        // Register network packets
        PacketHandler.register();
        LOGGER.info("Network packets registered");

        // Register commands
        ManaCommand.register();
        LOGGER.info("Commands registered");

        // Register event listeners
        registerEvents();
        LOGGER.info("Event listeners registered");

        LOGGER.info("{} initialization complete", MOD_NAME);
    }

    /**
     * Register event listeners for server tick, player join, etc.
     */
    private void registerEvents() {
        // Server tick event for mana regeneration
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            server.getPlayerManager().getPlayerList().forEach(player -> {
                ManaComponent component = MAM.getManaComponent(player);
                if (component != null) {
                    component.getManaPool().regenerate();
                }
            });
        });
    }

    /**
     * Get mana component from player.
     *
     * @param player Server player entity
     * @return ManaComponent or null if not found
     */
    public static ManaComponent getManaComponent(ServerPlayerEntity player) {
        return ManaComponents.MANA.getNullable(player);
    }

    /**
     * Get mod version from gradle.properties.
     *
     * @return Version string
     */
    public static String getModVersion() {
        return "1.0.0"; // Injected by build system
    }
}
```

### 2. Mana Pool Implementation

**File:** `src/main/java/dk/mosberg/mana/ManaPool.java`

```java
package dk.mosberg.mana;

import net.minecraft.nbt.NbtCompound;
import org.jetbrains.annotations.NotNull;

/**
 * Three-tier mana pool system with cascading consumption.
 *
 * Pool Hierarchy:
 * 1. Primary (250 max, 1.0/sec regen) - First consumed
 * 2. Secondary (500 max, 0.75/sec regen) - Second consumed
 * 3. Tertiary (1000 max, 0.5/sec regen) - Last consumed
 */
public class ManaPool {

    // Pool type enum
    public enum ManaPoolType {
        PRIMARY,
        SECONDARY,
        TERTIARY
    }

    // Default capacities
    private static final double DEFAULT_PRIMARY_MAX = 250.0;
    private static final double DEFAULT_SECONDARY_MAX = 500.0;
    private static final double DEFAULT_TERTIARY_MAX = 1000.0;

    // Default regeneration rates (per second)
    private static final double DEFAULT_PRIMARY_REGEN = 1.0;
    private static final double DEFAULT_SECONDARY_REGEN = 0.75;
    private static final double DEFAULT_TERTIARY_REGEN = 0.5;

    // Current mana values
    private double primaryMana;
    private double secondaryMana;
    private double tertiaryMana;

    // Maximum capacities
    private double primaryMaxMana;
    private double secondaryMaxMana;
    private double tertiaryMaxMana;

    // Regeneration rates
    private double primaryRegenRate;
    private double secondaryRegenRate;
    private double tertiaryRegenRate;

    /**
     * Create mana pool with default values.
     */
    public ManaPool() {
        this(DEFAULT_PRIMARY_MAX, DEFAULT_SECONDARY_MAX, DEFAULT_TERTIARY_MAX);
    }

    /**
     * Create mana pool with custom maximum values.
     */
    public ManaPool(double primaryMax, double secondaryMax, double tertiaryMax) {
        this.primaryMaxMana = primaryMax;
        this.secondaryMaxMana = secondaryMax;
        this.tertiaryMaxMana = tertiaryMax;

        this.primaryMana = primaryMax;
        this.secondaryMana = secondaryMax;
        this.tertiaryMana = tertiaryMax;

        this.primaryRegenRate = DEFAULT_PRIMARY_REGEN;
        this.secondaryRegenRate = DEFAULT_SECONDARY_REGEN;
        this.tertiaryRegenRate = DEFAULT_TERTIARY_REGEN;
    }

    // ═══════════════════════════════════════════════════════════════════════
    // Getters
    // ═══════════════════════════════════════════════════════════════════════

    public double getPrimaryMana() { return primaryMana; }
    public double getSecondaryMana() { return secondaryMana; }
    public double getTertiaryMana() { return tertiaryMana; }

    public double getPrimaryMaxMana() { return primaryMaxMana; }
    public double getSecondaryMaxMana() { return secondaryMaxMana; }
    public double getTertiaryMaxMana() { return tertiaryMaxMana; }

    public double getTotalMana() {
        return primaryMana + secondaryMana + tertiaryMana;
    }

    public double getTotalMaxMana() {
        return primaryMaxMana + secondaryMaxMana + tertiaryMaxMana;
    }

    public double getPrimaryPercent() {
        return primaryMaxMana > 0 ? primaryMana / primaryMaxMana : 0.0;
    }

    public double getSecondaryPercent() {
        return secondaryMaxMana > 0 ? secondaryMana / secondaryMaxMana : 0.0;
    }

    public double getTertiaryPercent() {
        return tertiaryMaxMana > 0 ? tertiaryMana / tertiaryMaxMana : 0.0;
    }

    // ═══════════════════════════════════════════════════════════════════════
    // Setters
    // ═══════════════════════════════════════════════════════════════════════

    public void setPrimaryMana(double amount) {
        this.primaryMana = Math.max(0, Math.min(amount, primaryMaxMana));
    }

    public void setSecondaryMana(double amount) {
        this.secondaryMana = Math.max(0, Math.min(amount, secondaryMaxMana));
    }

    public void setTertiaryMana(double amount) {
        this.tertiaryMana = Math.max(0, Math.min(amount, tertiaryMaxMana));
    }

    public void setPrimaryMaxMana(double max) {
        this.primaryMaxMana = Math.max(0, max);
        this.primaryMana = Math.min(this.primaryMana, this.primaryMaxMana);
    }

    public void setSecondaryMaxMana(double max) {
        this.secondaryMaxMana = Math.max(0, max);
        this.secondaryMana = Math.min(this.secondaryMana, this.secondaryMaxMana);
    }

    public void setTertiaryMaxMana(double max) {
        this.tertiaryMaxMana = Math.max(0, max);
        this.tertiaryMana = Math.min(this.tertiaryMana, this.tertiaryMaxMana);
    }

    // ═══════════════════════════════════════════════════════════════════════
    // Consumption (Cascading)
    // ═══════════════════════════════════════════════════════════════════════

    /**
     * Attempt to consume mana using cascading logic.
     * Priority: Primary → Secondary → Tertiary
     *
     * @param amount Amount of mana to consume
     * @return true if successful, false if insufficient mana
     */
    public boolean consumeMana(double amount) {
        if (amount < 0) return false;
        if (!canConsumeMana(amount)) return false;

        double remaining = amount;

        // Consume from primary first
        if (primaryMana > 0) {
            double consumed = Math.min(primaryMana, remaining);
            primaryMana -= consumed;
            remaining -= consumed;
        }

        // Consume from secondary if needed
        if (remaining > 0 && secondaryMana > 0) {
            double consumed = Math.min(secondaryMana, remaining);
            secondaryMana -= consumed;
            remaining -= consumed;
        }

        // Consume from tertiary if needed
        if (remaining > 0 && tertiaryMana > 0) {
            double consumed = Math.min(tertiaryMana, remaining);
            tertiaryMana -= consumed;
            remaining -= consumed;
        }

        return remaining == 0;
    }

    /**
     * Check if enough mana is available.
     *
     * @param amount Amount to check
     * @return true if enough mana available
     */
    public boolean canConsumeMana(double amount) {
        return getTotalMana() >= amount;
    }

    // ═══════════════════════════════════════════════════════════════════════
    // Restoration
    // ═══════════════════════════════════════════════════════════════════════

    /**
     * Restore mana using cascading logic.
     * Priority: Primary → Secondary → Tertiary
     *
     * @param amount Amount of mana to restore
     */
    public void restoreMana(double amount) {
        if (amount < 0) return;

        double remaining = amount;

        // Restore primary first
        if (primaryMana < primaryMaxMana) {
            double restored = Math.min(primaryMaxMana - primaryMana, remaining);
            primaryMana += restored;
            remaining -= restored;
        }

        // Restore secondary if needed
        if (remaining > 0 && secondaryMana < secondaryMaxMana) {
            double restored = Math.min(secondaryMaxMana - secondaryMana, remaining);
            secondaryMana += restored;
            remaining -= restored;
        }

        // Restore tertiary if needed
        if (remaining > 0 && tertiaryMana < tertiaryMaxMana) {
            double restored = Math.min(tertiaryMaxMana - tertiaryMana, remaining);
            tertiaryMana += restored;
            remaining -= restored;
        }
    }

    /**
     * Restore specific pool to maximum.
     *
     * @param type Pool type to restore
     */
    public void restorePool(@NotNull ManaPoolType type) {
        switch (type) {
            case PRIMARY -> primaryMana = primaryMaxMana;
            case SECONDARY -> secondaryMana = secondaryMaxMana;
            case TERTIARY -> tertiaryMana = tertiaryMaxMana;
        }
    }

    /**
     * Restore all pools to maximum.
     */
    public void restoreAll() {
        primaryMana = primaryMaxMana;
        secondaryMana = secondaryMaxMana;
        tertiaryMana = tertiaryMaxMana;
    }

    // ═══════════════════════════════════════════════════════════════════════
    // Regeneration
    // ═══════════════════════════════════════════════════════════════════════

    /**
     * Regenerate mana for all pools.
     * Called once per tick (20 times per second).
     * Actual regen is rate/20 per tick.
     */
    public void regenerate() {
        // Regenerate primary
        if (primaryMana < primaryMaxMana) {
            primaryMana = Math.min(
                primaryMana + (primaryRegenRate / 20.0),
                primaryMaxMana
            );
        }

        // Regenerate secondary
        if (secondaryMana < secondaryMaxMana) {
            secondaryMana = Math.min(
                secondaryMana + (secondaryRegenRate / 20.0),
                secondaryMaxMana
            );
        }

        // Regenerate tertiary
        if (tertiaryMana < tertiaryMaxMana) {
            tertiaryMana = Math.min(
                tertiaryMana + (tertiaryRegenRate / 20.0),
                tertiaryMaxMana
            );
        }
    }

    // ═══════════════════════════════════════════════════════════════════════
    // NBT Serialization
    // ═══════════════════════════════════════════════════════════════════════

    /**
     * Write mana pool data to NBT.
     *
     * @param nbt NBT compound to write to
     */
    public void writeNbt(@NotNull NbtCompound nbt) {
        nbt.putDouble("primary", primaryMana);
        nbt.putDouble("secondary", secondaryMana);
        nbt.putDouble("tertiary", tertiaryMana);

        nbt.putDouble("primary_max", primaryMaxMana);
        nbt.putDouble("secondary_max", secondaryMaxMana);
        nbt.putDouble("tertiary_max", tertiaryMaxMana);

        nbt.putDouble("primary_regen", primaryRegenRate);
        nbt.putDouble("secondary_regen", secondaryRegenRate);
        nbt.putDouble("tertiary_regen", tertiaryRegenRate);
    }

    /**
     * Read mana pool data from NBT.
     *
     * @param nbt NBT compound to read from
     */
    public void readNbt(@NotNull NbtCompound nbt) {
        primaryMana = nbt.getDouble("primary");
        secondaryMana = nbt.getDouble("secondary");
        tertiaryMana = nbt.getDouble("tertiary");

        primaryMaxMana = nbt.getDouble("primary_max");
        secondaryMaxMana = nbt.getDouble("secondary_max");
        tertiaryMaxMana = nbt.getDouble("tertiary_max");

        primaryRegenRate = nbt.getDouble("primary_regen");
        secondaryRegenRate = nbt.getDouble("secondary_regen");
        tertiaryRegenRate = nbt.getDouble("tertiary_regen");
    }
}
```

### 3. Spell Interface

**File:** `src/main/java/dk/mosberg/spell/Spell.java`

```java
package dk.mosberg.spell;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Base interface for all spells in the magic system.
 */
public interface Spell {

    /**
     * Get the unique identifier for this spell.
     * Format: "mam:spell_name"
     *
     * @return Spell identifier
     */
    @NotNull
    Identifier getId();

    /**
     * Get the school this spell belongs to.
     *
     * @return Spell school
     */
    @NotNull
    SpellSchool getSchool();

    /**
     * Get the cast type (projectile, AoE, utility, etc).
     *
     * @return Cast type
     */
    @NotNull
    CastType getCastType();

    /**
     * Get the level requirement for this spell.
     *
     * @return Minimum mage level
     */
    int getLevel();

    /**
     * Get the mana cost to cast this spell.
     *
     * @return Mana cost
     */
    double getManaCost();

    /**
     * Get the cooldown duration in ticks (20 ticks = 1 second).
     *
     * @return Cooldown in ticks
     */
    int getCooldown();

    /**
     * Get the base damage for this spell.
     *
     * @return Damage amount (0 for utility spells)
     */
    double getDamage();

    /**
     * Get the range of this spell in blocks.
     *
     * @return Range in blocks
     */
    double getRange();

    /**
     * Get the area of effect radius (if applicable).
     *
     * @return AoE radius in blocks (0 if not AoE)
     */
    double getAoeRadius();

    /**
     * Get the list of status effects this spell applies.
     *
     * @return List of spell effects
     */
    @NotNull
    List<SpellEffect> getEffects();

    /**
     * Cast this spell.
     *
     * @param caster Player casting the spell
     * @return true if cast successful, false otherwise
     */
    boolean cast(@NotNull ServerPlayerEntity caster);

    /**
     * Check if player can cast this spell.
     * Checks: level requirement, mana availability, cooldown
     *
     * @param caster Player attempting to cast
     * @return true if can cast
     */
    boolean canCast(@NotNull ServerPlayerEntity caster);

    /**
     * Get the translation key for this spell's name.
     *
     * @return Translation key
     */
    default String getTranslationKey() {
        return "spell." + getId().getNamespace() + "." + getId().getPath();
    }
}
```

### 4. Ritual Interface

**File:** `src/main/java/dk/mosberg/ritual/Ritual.java`

```java
package dk.mosberg.ritual;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * Base interface for all rituals in the magic system.
 */
public interface Ritual {

    /**
     * Get the unique identifier for this ritual.
     *
     * @return Ritual identifier
     */
    @NotNull
    Identifier getId();

    /**
     * Get the ritual category.
     *
     * @return Ritual category
     */
    @NotNull
    RitualCategory getCategory();

    /**
     * Get the level requirement for this ritual.
     *
     * @return Minimum mage level
     */
    int getLevel();

    /**
     * Get the ritual duration in ticks.
     *
     * @return Duration in ticks
     */
    int getDuration();

    /**
     * Get the total mana cost for this ritual.
     *
     * @return Mana cost
     */
    double getManaCost();

    /**
     * Get the required gemstones and quantities.
     *
     * @return Map of gemstone identifier to quantity
     */
    @NotNull
    Map<Identifier, Integer> getRequiredGemstones();

    /**
     * Get the ritual pattern.
     *
     * @return Block pattern for validation
     */
    @NotNull
    RitualPattern getPattern();

    /**
     * Check if ritual can be activated.
     * Validates: pattern, requirements, environment
     *
     * @param world World
     * @param pos Central position
     * @param player Player activating
     * @return true if can activate
     */
    boolean canActivate(@NotNull ServerWorld world, @NotNull BlockPos pos,
                       @NotNull ServerPlayerEntity player);

    /**
     * Activate the ritual.
     *
     * @param world World
     * @param pos Central position
     * @param player Player activating
     */
    void activate(@NotNull ServerWorld world, @NotNull BlockPos pos,
                 @NotNull ServerPlayerEntity player);

    /**
     * Tick the ritual (called every tick while active).
     *
     * @param state Current ritual state
     */
    void tick(@NotNull RitualState state);

    /**
     * Complete the ritual.
     *
     * @param state Final ritual state
     */
    void complete(@NotNull RitualState state);

    /**
     * Cancel the ritual.
     *
     * @param state Current ritual state
     */
    void cancel(@NotNull RitualState state);
}
```

---

## 📋 JSON Data Formats

### Spell JSON Schema

**File:** `data/mam/spells/<school>/<spell_name>.json`

```json
{
  "$schema": "https://mosberg.github.io/mam/schemas/spell.json",
  "id": "mam:fire_strike",
  "school": "fire",
  "type": "projectile",
  "level": 1,
  "mana_cost": 20.0,
  "cooldown": 20,
  "damage": 10.0,
  "range": 32.0,
  "aoe_radius": 0.0,
  "velocity": 1.5,
  "effects": [
    {
      "type": "minecraft:fire_resistance",
      "duration": 60,
      "amplifier": 0,
      "ambient": false,
      "show_particles": true,
      "show_icon": true
    }
  ],
  "projectile": {
    "entity_type": "mam:fireball",
    "particle": "minecraft:flame",
    "particle_count": 5,
    "sound": "minecraft:entity.blaze.shoot",
    "gravity": 0.0,
    "pierce": false,
    "homing": false,
    "bounce": false
  },
  "requirements": {
    "weather": ["clear", "rain"],
    "time": ["day", "night"],
    "biome": []
  }
}
```

**Field Descriptions:**

| Field          | Type    | Required | Description                                       |
| -------------- | ------- | -------- | ------------------------------------------------- |
| `id`           | string  | Yes      | Unique spell identifier                           |
| `school`       | string  | Yes      | Spell school (lowercase)                          |
| `type`         | string  | Yes      | Cast type: projectile, aoe, utility, channel, ray |
| `level`        | integer | Yes      | Minimum mage level (1-100)                        |
| `mana_cost`    | double  | Yes      | Mana required to cast                             |
| `cooldown`     | integer | Yes      | Cooldown in ticks (20 = 1 second)                 |
| `damage`       | double  | Yes      | Base damage (0 for utility)                       |
| `range`        | double  | Yes      | Range in blocks                                   |
| `aoe_radius`   | double  | No       | AoE radius (0 if not AoE)                         |
| `velocity`     | double  | No       | Projectile velocity (projectile only)             |
| `effects`      | array   | No       | Status effects to apply                           |
| `projectile`   | object  | No       | Projectile settings (projectile only)             |
| `requirements` | object  | No       | Environmental requirements                        |

### Ritual JSON Schema

**File:** `data/mam/rituals/<category>/<ritual_name>.json`

```json
{
  "$schema": "https://mosberg.github.io/mam/schemas/ritual.json",
  "id": "mam:apotheosis_ritual",
  "category": "ascension",
  "level": 90,
  "duration": 36000,
  "mana_cost": 5000.0,
  "gemstones": {
    "mam:tanzanite": 4,
    "mam:citrine": 8,
    "mam:topaz": 16
  },
  "items": {
    "minecraft:nether_star": 1,
    "minecraft:dragon_egg": 1
  },
  "pattern": {
    "type": "custom",
    "width": 9,
    "height": 5,
    "depth": 9,
    "blocks": [
      {
        "pos": ,
        "block": "mam:arcane_altar"
      },
      {
        "pos": ,
        "block": "mam:tanzanite_block"
      },
      {
        "pos": [-4, 0, 4],
        "block": "mam:tanzanite_block"
      },
      {
        "pos": [4, 0, -4],
        "block": "mam:tanzanite_block"
      },
      {
        "pos": [-4, 0, -4],
        "block": "mam:tanzanite_block"
      }
    ]
  },
  "requirements": {
    "time": {
      "type": "moon_phase",
      "value": 0
    },
    "weather": "clear",
    "biome": ["minecraft:mountains", "minecraft:mountain_peaks"],
    "min_y": 200,
    "see_sky": true
  },
  "effects": [
    {
      "type": "grant_ascension_level",
      "amount": 1
    },
    {
      "type": "increase_mana_pool",
      "pool": "all",
      "amount": 50.0,
      "permanent": true
    }
  ],
  "particles": {
    "type": "minecraft:end_rod",
    "count": 50,
    "speed": 0.1,
    "spread": [1.0, 1.0, 1.0]
  },
  "sounds": {
    "start": "minecraft:block.end_portal.spawn",
    "tick": "minecraft:block.beacon.ambient",
    "complete": "minecraft:entity.ender_dragon.death",
    "cancel": "minecraft:entity.generic.explode"
  },
  "cooldown": 604800
}
```

### Gemstone JSON Schema

**File:** `data/mam/gemstones/<gemstone_name>.json`

```json
{
  "$schema": "https://mosberg.github.io/mam/schemas/gemstone.json",
  "id": "mam:ruby",
  "name": "Ruby",
  "rarity": "epic",
  "color": "#E63946",
  "symbol": "🔴",
  "shape": "round",
  "schools": ["fire", "blood"],
  "rituals": ["elemental", "sacrifice"],
  "affinity": "fire",
  "bonuses": {
    "mana": 50.0,
    "damage": 1.15,
    "efficiency": 0.9
  },
  "ore": {
    "enabled": true,
    "vein_size": {
      "min": 2,
      "max": 4
    },
    "veins_per_chunk": 0.125,
    "y_level": {
      "min": 5,
      "max": 20
    },
    "biomes": [],
    "dimension": ["minecraft:overworld", "minecraft:the_nether"]
  }
}
```

---

## 💾 NBT Data Structures

### Player Mana Data

**NBT Path:** `playerdata/<uuid>.dat → mam:mana`

```nbt
{
  "mam:mana": {
    // Current mana values
    "primary": 120.5d,
    "secondary": 300.0d,
    "tertiary": 750.0d,

    // Maximum capacities
    "primary_max": 250.0d,
    "secondary_max": 500.0d,
    "tertiary_max": 1000.0d,

    // Regeneration rates (per second)
    "primary_regen": 1.0d,
    "secondary_regen": 0.75d,
    "tertiary_regen": 0.5d,

    // Bonuses from equipment/enchantments
    "bonuses": {
      "capacity": 150.0d,
      "efficiency": 0.8d,
      "regen": 0.5d
    },

    // Metadata
    "last_tick": 1234567890L,
    "version": 1
  }
}
```

**Java Serialization:**

```java
public void writeNbt(NbtCompound nbt) {
    NbtCompound manaData = new NbtCompound();

    // Current values
    manaData.putDouble("primary", primaryMana);
    manaData.putDouble("secondary", secondaryMana);
    manaData.putDouble("tertiary", tertiaryMana);

    // Maximum values
    manaData.putDouble("primary_max", primaryMaxMana);
    manaData.putDouble("secondary_max", secondaryMaxMana);
    manaData.putDouble("tertiary_max", tertiaryMaxMana);

    // Regen rates
    manaData.putDouble("primary_regen", primaryRegenRate);
    manaData.putDouble("secondary_regen", secondaryRegenRate);
    manaData.putDouble("tertiary_regen", tertiaryRegenRate);

    // Bonuses
    NbtCompound bonuses = new NbtCompound();
    bonuses.putDouble("capacity", capacityBonus);
    bonuses.putDouble("efficiency", efficiencyBonus);
    bonuses.putDouble("regen", regenBonus);
    manaData.put("bonuses", bonuses);

    // Metadata
    manaData.putLong("last_tick", lastTickTime);
    manaData.putInt("version", 1);

    nbt.put("mam:mana", manaData);
}
```

### Spell Cooldown Data

**NBT Path:** `playerdata/<uuid>.dat → mam:cooldowns`

```nbt
{
  "mam:cooldowns": {
    "mam:fire_strike": {
      "remaining": 15,        // Ticks remaining
      "total": 20,            // Total cooldown duration
      "started": 1234567890L  // Timestamp when started
    },
    "mam:fireball": {
      "remaining": 120,
      "total": 160,
      "started": 1234567750L
    }
  }
}
```

### Progression Data

**NBT Path:** `playerdata/<uuid>.dat → mam:progression`

```nbt
{
  "mam:progression": {
    // Level and XP
    "level": 45,
    "xp": 18500,
    "xp_to_next": 20000,

    // Specializations
    "specializations": ["fire", "arcane", "light"],
    "specialization_tiers": {
      "fire": 3,
      "arcane": 2,
      "light": 1
    },

    // Known spells
    "known_spells": [
      "mam:fire_strike",
      "mam:fireball",
      "mam:heal",
      "mam:arcane_missile"
    ],

    // Completed rituals
    "completed_rituals": [
      "mam:basic_circle",
      "mam:elemental_convergence"
    ],

    // Skill points
    "skill_points": {
      "available": 3,
      "spent": 6,
      "skills": [
        "mana_attunement_1",
        "mana_attunement_2",
        "efficient_casting_1"
      ]
    },

    // Statistics
    "stats": {
      "spells_cast": 1547,
      "rituals_completed": 12,
      "mana_consumed": 125478.5d,
      "enemies_killed": 342
    }
  }
}
```

### Ritual State Data

**NBT Path:** Block entity or temporary world data

```nbt
{
  "ritual_state": {
    "id": "mam:apotheosis_ritual",
    "pos": {
      "x": 100,
      "y": 200,
      "z": -50
    },
    "world": "minecraft:overworld",
    "initiator": "069a79f4-44e9-4726-a5be-fca90e38aaf5", // UUID
    "start_time": 1234567890L,
    "duration": 36000,
    "elapsed": 15000,
    "mana_consumed": 2500.0d,
    "mana_total": 5000.0d,
    "active": true,
    "validated": true,
    "participants": [
      "069a79f4-44e9-4726-a5be-fca90e38aaf5",
      "f84c6a79-0a4e-45e0-879b-cd49ebd4c4e2"
    ]
  }
}
```

---

## 🌐 Networking Protocol

### Packet Architecture

**Base Packet Interface:**

```java
package dk.mosberg.network.packet;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public interface MAMPayload extends CustomPayload {

    /**
     * Get the packet identifier.
     */
    @Override
    Id<? extends CustomPayload> getId();

    /**
     * Write packet data to buffer.
     */
    void write(PacketByteBuf buf);

    /**
     * Read packet data from buffer.
     */
    static <T extends MAMPayload> T read(PacketByteBuf buf) {
        throw new UnsupportedOperationException();
    }
}
```

### 1. Cast Spell Packet (C2S)

**Purpose:** Client → Server spell casting request

**File:** `src/main/java/dk/mosberg/network/packet/CastSpellPayload.java`

```java
package dk.mosberg.network.packet;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record CastSpellPayload(Identifier spellId, boolean mainHand) implements CustomPayload {

    public static final Id<CastSpellPayload> ID =
        new Id<>(Identifier.of("mam", "cast_spell"));

    public static final PacketCodec<PacketByteBuf, CastSpellPayload> CODEC =
        PacketCodec.of(CastSpellPayload::write, CastSpellPayload::read);

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public void write(PacketByteBuf buf) {
        buf.writeIdentifier(spellId);
        buf.writeBoolean(mainHand);
    }

    public static CastSpellPayload read(PacketByteBuf buf) {
        Identifier spellId = buf.readIdentifier();
        boolean mainHand = buf.readBoolean();
        return new CastSpellPayload(spellId, mainHand);
    }
}
```

**Packet Handler:**

```java
public static void registerC2SPackets() {
    PayloadTypeRegistry.playC2S().register(CastSpellPayload.ID, CastSpellPayload.CODEC);

    ServerPlayNetworking.registerGlobalReceiver(CastSpellPayload.ID, (payload, context) -> {
        ServerPlayerEntity player = context.player();
        Identifier spellId = payload.spellId();
        boolean mainHand = payload.mainHand();

        context.server().execute(() -> {
            Spell spell = SpellRegistry.get(spellId);
            if (spell != null && spell.canCast(player)) {
                spell.cast(player);
            }
        });
    });
}
```

**Packet Structure:**

```
Bytes 0-3:   Identifier namespace length (VarInt)
Bytes 4-N:   Identifier namespace (String)
Bytes N-M:   Identifier path length (VarInt)
Bytes M-O:   Identifier path (String)
Byte  O:     Main hand flag (Boolean)
```

### 2. Sync Mana Packet (S2C)

**Purpose:** Server → Client mana synchronization

**File:** `src/main/java/dk/mosberg/network/packet/SyncManaPayload.java`

```java
package dk.mosberg.network.packet;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record SyncManaPayload(
    double primaryMana,
    double secondaryMana,
    double tertiaryMana,
    double primaryMax,
    double secondaryMax,
    double tertiaryMax
) implements CustomPayload {

    public static final Id<SyncManaPayload> ID =
        new Id<>(Identifier.of("mam", "sync_mana"));

    public static final PacketCodec<PacketByteBuf, SyncManaPayload> CODEC =
        PacketCodec.of(SyncManaPayload::write, SyncManaPayload::read);

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public void write(PacketByteBuf buf) {
        buf.writeDouble(primaryMana);
        buf.writeDouble(secondaryMana);
        buf.writeDouble(tertiaryMana);
        buf.writeDouble(primaryMax);
        buf.writeDouble(secondaryMax);
        buf.writeDouble(tertiaryMax);
    }

    public static SyncManaPayload read(PacketByteBuf buf) {
        return new SyncManaPayload(
            buf.readDouble(),
            buf.readDouble(),
            buf.readDouble(),
            buf.readDouble(),
            buf.readDouble(),
            buf.readDouble()
        );
    }
}
```

**Packet Handler:**

```java
public static void registerS2CPackets() {
    PayloadTypeRegistry.playS2C().register(SyncManaPayload.ID, SyncManaPayload.CODEC);

    ClientPlayNetworking.registerGlobalReceiver(SyncManaPayload.ID, (payload, context) -> {
        context.client().execute(() -> {
            ClientPlayerEntity player = context.client().player;
            if (player != null) {
                ManaComponent component = MAM.getManaComponent(player);
                if (component != null) {
                    ManaPool pool = component.getManaPool();
                    pool.setPrimaryMana(payload.primaryMana());
                    pool.setSecondaryMana(payload.secondaryMana());
                    pool.setTertiaryMana(payload.tertiaryMana());
                    pool.setPrimaryMaxMana(payload.primaryMax());
                    pool.setSecondaryMaxMana(payload.secondaryMax());
                    pool.setTertiaryMaxMana(payload.tertiaryMax());
                }
            }
        });
    });
}
```

### 3. Ritual Update Packet (S2C)

**Purpose:** Server → Client ritual state updates

```java
public record RitualUpdatePayload(
    Identifier ritualId,
    BlockPos pos,
    int elapsed,
    int duration,
    boolean active
) implements CustomPayload {

    public static final Id<RitualUpdatePayload> ID =
        new Id<>(Identifier.of("mam", "ritual_update"));

    public static final PacketCodec<PacketByteBuf, RitualUpdatePayload> CODEC =
        PacketCodec.of(RitualUpdatePayload::write, RitualUpdatePayload::read);

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public void write(PacketByteBuf buf) {
        buf.writeIdentifier(ritualId);
        buf.writeBlockPos(pos);
        buf.writeVarInt(elapsed);
        buf.writeVarInt(duration);
        buf.writeBoolean(active);
    }

    public static RitualUpdatePayload read(PacketByteBuf buf) {
        return new RitualUpdatePayload(
            buf.readIdentifier(),
            buf.readBlockPos(),
            buf.readVarInt(),
            buf.readVarInt(),
            buf.readBoolean()
        );
    }
}
```

### Packet Registration

**File:** `src/main/java/dk/mosberg/network/PacketHandler.java`

```java
package dk.mosberg.network;

import dk.mosberg.network.packet.*;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public class PacketHandler {

    public static void register() {
        registerC2S();
        registerS2C();
    }

    private static void registerC2S() {
        // Cast Spell
        PayloadTypeRegistry.playC2S().register(
            CastSpellPayload.ID,
            CastSpellPayload.CODEC
        );
        ServerPlayNetworking.registerGlobalReceiver(
            CastSpellPayload.ID,
            CastSpellHandler::handle
        );

        // Select Spell
        PayloadTypeRegistry.playC2S().register(
            SelectSpellPayload.ID,
            SelectSpellPayload.CODEC
        );
        ServerPlayNetworking.registerGlobalReceiver(
            SelectSpellPayload.ID,
            SelectSpellHandler::handle
        );
    }

    private static void registerS2C() {
        // Sync Mana
        PayloadTypeRegistry.playS2C().register(
            SyncManaPayload.ID,
            SyncManaPayload.CODEC
        );

        // Ritual Update
        PayloadTypeRegistry.playS2C().register(
            RitualUpdatePayload.ID,
            RitualUpdatePayload.CODEC
        );

        // Cooldown Update
        PayloadTypeRegistry.playS2C().register(
            CooldownUpdatePayload.ID,
            CooldownUpdatePayload.CODEC
        );
    }
}
```

---

## 🎯 Event System

### Custom Event Framework

**Base Event Interface:**

```java
package dk.mosberg.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

/**
 * Base interface for all MAM events.
 */
public interface MAMEvent<T> {

    /**
     * Create event with default invoker.
     */
    static <T> Event<T> create(Class<T> type, T emptyInvoker) {
        return EventFactory.createArrayBacked(type, emptyInvoker);
    }
}
```

### Mana Events

**File:** `src/main/java/dk/mosberg/event/ManaEvents.java`

```java
package dk.mosberg.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.server.network.ServerPlayerEntity;
import dk.mosberg.mana.ManaPool;

public class ManaEvents {

    /**
     * Called when player's mana changes.
     *
     * Return true to allow, false to cancel.
     */
    public static final Event<ManaChanged> MANA_CHANGED =
        EventFactory.createArrayBacked(ManaChanged.class,
            (listeners) -> (player, pool, oldValue, newValue) -> {
                for (ManaChanged listener : listeners) {
                    if (!listener.onManaChanged(player, pool, oldValue, newValue)) {
                        return false;
                    }
                }
                return true;
            }
        );

    @FunctionalInterface
    public interface ManaChanged {
        boolean onManaChanged(ServerPlayerEntity player, ManaPool pool,
                            double oldValue, double newValue);
    }

    /**
     * Called when mana regenerates.
     */
    public static final Event<ManaRegenerated> MANA_REGENERATED =
        EventFactory.createArrayBacked(ManaRegenerated.class,
            (listeners) -> (player, pool, amount) -> {
                for (ManaRegenerated listener : listeners) {
                    listener.onManaRegenerated(player, pool, amount);
                }
            }
        );

    @FunctionalInterface
    public interface ManaRegenerated {
        void onManaRegenerated(ServerPlayerEntity player, ManaPool pool,
                              double amount);
    }

    /**
     * Called when player depletes mana completely.
     */
    public static final Event<ManaDepleted> MANA_DEPLETED =
        EventFactory.createArrayBacked(ManaDepleted.class,
            (listeners) -> (player, pool) -> {
                for (ManaDepleted listener : listeners) {
                    listener.onManaDepleted(player, pool);
                }
            }
        );

    @FunctionalInterface
    public interface ManaDepleted {
        void onManaDepleted(ServerPlayerEntity player, ManaPool pool);
    }
}
```

**Usage Example:**

```java
// Register event listener
ManaEvents.MANA_CHANGED.register((player, pool, oldValue, newValue) -> {
    if (newValue < oldValue) {
        MAM.LOGGER.debug("{} consumed {} mana",
            player.getName().getString(),
            oldValue - newValue);
    }
    return true; // Allow the change
});

// Trigger event
boolean allowed = ManaEvents.MANA_CHANGED.invoker()
    .onManaChanged(player, pool, oldMana, newMana);
if (allowed) {
    pool.setPrimaryMana(newMana);
}
```

### Spell Events

**File:** `src/main/java/dk/mosberg/event/SpellEvents.java`

```java
package dk.mosberg.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.server.network.ServerPlayerEntity;
import dk.mosberg.spell.Spell;

public class SpellEvents {

    /**
     * Called before spell is cast.
     * Return false to cancel cast.
     */
    public static final Event<BeforeSpellCast> BEFORE_SPELL_CAST =
        EventFactory.createArrayBacked(BeforeSpellCast.class,
            (listeners) -> (player, spell) -> {
                for (BeforeSpellCast listener : listeners) {
                    if (!listener.beforeSpellCast(player, spell)) {
                        return false;
                    }
                }
                return true;
            }
        );

    @FunctionalInterface
    public interface BeforeSpellCast {
        boolean beforeSpellCast(ServerPlayerEntity player, Spell spell);
    }

    /**
     * Called after spell is successfully cast.
     */
    public static final Event<SpellCast> SPELL_CAST =
        EventFactory.createArrayBacked(SpellCast.class,
            (listeners) -> (player, spell, success) -> {
                for (SpellCast listener : listeners) {
                    listener.onSpellCast(player, spell, success);
                }
            }
        );

    @FunctionalInterface
    public interface SpellCast {
        void onSpellCast(ServerPlayerEntity player, Spell spell, boolean success);
    }

    /**
     * Called when spell learns new spell.
     */
    public static final Event<SpellLearned> SPELL_LEARNED =
        EventFactory.createArrayBacked(SpellLearned.class,
            (listeners) -> (player, spell) -> {
                for (SpellLearned listener : listeners) {
                    listener.onSpellLearned(player, spell);
                }
            }
        );

    @FunctionalInterface
    public interface SpellLearned {
        void onSpellLearned(ServerPlayerEntity player, Spell spell);
    }
}
```

### Ritual Events

**File:** `src/main/java/dk/mosberg/event/RitualEvents.java`

```java
package dk.mosberg.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import dk.mosberg.ritual.Ritual;
import dk.mosberg.ritual.RitualState;

public class RitualEvents {

    /**
     * Called before ritual starts.
     * Return false to cancel.
     */
    public static final Event<BeforeRitualStart> BEFORE_RITUAL_START =
        EventFactory.createArrayBacked(BeforeRitualStart.class,
            (listeners) -> (world, pos, player, ritual) -> {
                for (BeforeRitualStart listener : listeners) {
                    if (!listener.beforeRitualStart(world, pos, player, ritual)) {
                        return false;
                    }
                }
                return true;
            }
        );

    @FunctionalInterface
    public interface BeforeRitualStart {
        boolean beforeRitualStart(ServerWorld world, BlockPos pos,
                                 ServerPlayerEntity player, Ritual ritual);
    }

    /**
     * Called when ritual ticks.
     */
    public static final Event<RitualTick> RITUAL_TICK =
        EventFactory.createArrayBacked(RitualTick.class,
            (listeners) -> (state) -> {
                for (RitualTick listener : listeners) {
                    listener.onRitualTick(state);
                }
            }
        );

    @FunctionalInterface
    public interface RitualTick {
        void onRitualTick(RitualState state);
    }

    /**
     * Called when ritual completes.
     */
    public static final Event<RitualComplete> RITUAL_COMPLETE =
        EventFactory.createArrayBacked(RitualComplete.class,
            (listeners) -> (world, pos, player, ritual) -> {
                for (RitualComplete listener : listeners) {
                    listener.onRitualComplete(world, pos, player, ritual);
                }
            }
        );

    @FunctionalInterface
    public interface RitualComplete {
        void onRitualComplete(ServerWorld world, BlockPos pos,
                             ServerPlayerEntity player, Ritual ritual);
    }

    /**
     * Called when ritual is cancelled.
     */
    public static final Event<RitualCancelled> RITUAL_CANCELLED =
        EventFactory.createArrayBacked(RitualCancelled.class,
            (listeners) -> (state, reason) -> {
                for (RitualCancelled listener : listeners) {
                    listener.onRitualCancelled(state, reason);
                }
            }
        );

    @FunctionalInterface
    public interface RitualCancelled {
        void onRitualCancelled(RitualState state, String reason);
    }
}
```

---

## 📚 Registry System

### Generic Registry Implementation

**File:** `src/main/java/dk/mosberg/registry/MAMRegistry.java`

```java
package dk.mosberg.registry;

import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Thread-safe registry for game content.
 *
 * @param <T> Type of registered objects
 */
public class MAMRegistry<T> {

    private final Map<Identifier, T> entries = new ConcurrentHashMap<>();
    private final String name;

    public MAMRegistry(String name) {
        this.name = name;
    }

    /**
     * Register an entry.
     *
     * @param id Entry identifier
     * @param entry Entry object
     * @throws IllegalArgumentException if id already registered
     */
    public void register(@NotNull Identifier id, @NotNull T entry) {
        if (entries.containsKey(id)) {
            throw new IllegalArgumentException(
                "Duplicate registration: " + id + " in " + name
            );
        }
        entries.put(id, entry);
    }

    /**
     * Get entry by identifier.
     *
     * @param id Entry identifier
     * @return Entry or null if not found
     */
    @Nullable
    public T get(@NotNull Identifier id) {
        return entries.get(id);
    }

    /**
     * Get entry by string identifier.
     *
     * @param id Entry identifier string
     * @return Entry or null if not found
     */
    @Nullable
    public T get(@NotNull String id) {
        return get(Identifier.of(id));
    }

    /**
     * Check if entry is registered.
     *
     * @param id Entry identifier
     * @return true if registered
     */
    public boolean contains(@NotNull Identifier id) {
        return entries.containsKey(id);
    }

    /**
     * Get all registered identifiers.
     *
     * @return Unmodifiable set of identifiers
     */
    @NotNull
    public Set<Identifier> getIds() {
        return Collections.unmodifiableSet(entries.keySet());
    }

    /**
     * Get all registered entries.
     *
     * @return Unmodifiable collection of entries
     */
    @NotNull
    public Collection<T> getEntries() {
        return Collections.unmodifiableCollection(entries.values());
    }

    /**
     * Get entry count.
     *
     * @return Number of registered entries
     */
    public int size() {
        return entries.size();
    }

    /**
     * Clear all entries (use with caution).
     */
    public void clear() {
        entries.clear();
    }

    /**
     * Get registry name.
     *
     * @return Registry name
     */
    @NotNull
    public String getName() {
        return name;
    }
}
```

### Spell Registry

**File:** `src/main/java/dk/mosberg/spell/SpellRegistry.java`

```java
package dk.mosberg.spell;

import dk.mosberg.MAM;
import dk.mosberg.registry.MAMRegistry;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Collectors;

public class SpellRegistry {

    private static final MAMRegistry<Spell> REGISTRY = new MAMRegistry<>("spells");

    /**
     * Register a spell.
     */
    public static void register(@NotNull Spell spell) {
        REGISTRY.register(spell.getId(), spell);
        MAM.LOGGER.debug("Registered spell: {}", spell.getId());
    }

    /**
     * Get spell by identifier.
     */
    @Nullable
    public static Spell get(@NotNull Identifier id) {
        return REGISTRY.get(id);
    }

    /**
     * Get spell by string identifier.
     */
    @Nullable
    public static Spell get(@NotNull String id) {
        return REGISTRY.get(id);
    }

    /**
     * Get all spells for a specific school.
     */
    @NotNull
    public static List<Spell> getBySchool(@NotNull SpellSchool school) {
        return REGISTRY.getEntries().stream()
            .filter(spell -> spell.getSchool() == school)
            .collect(Collectors.toList());
    }

    /**
     * Get spells available at or below a specific level.
     */
    @NotNull
    public static List<Spell> getByMaxLevel(int maxLevel) {
        return REGISTRY.getEntries().stream()
            .filter(spell -> spell.getLevel() <= maxLevel)
            .collect(Collectors.toList());
    }

    /**
     * Get all registered spell IDs.
     */
    @NotNull
    public static List<Identifier> getAllIds() {
        return REGISTRY.getIds().stream()
            .sorted()
            .collect(Collectors.toList());
    }

    /**
     * Initialize and register all spells.
     */
    public static void register() {
        MAM.LOGGER.info("Registering spells...");

        // Register fire spells
        FireSpells.register();

        // Register ice spells
        IceSpells.register();

        // Register arcane spells
        ArcaneSpells.register();

        // Register other schools...

        MAM.LOGGER.info("Registered {} spells", REGISTRY.size());
    }
}
```

---

## 🎨 Rendering System

### HUD Overlay Renderer

**File:** `src/client/java/dk/mosberg/client/overlay/ManaHudOverlay.java`

```java
package dk.mosberg.client.overlay;

import com.mojang.blaze3d.systems.RenderSystem;
import dk.mosberg.MAM;
import dk.mosberg.config.MAMConfig;
import dk.mosberg.mana.ManaComponent;
import dk.mosberg.mana.ManaPool;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.util.Identifier;

public class ManaHudOverlay implements HudRenderCallback {

    private static final Identifier MANA_BAR_TEXTURE =
        Identifier.of("mam", "textures/gui/mana_bar.png");

    private static final int BAR_WIDTH = 100;
    private static final int BAR_HEIGHT = 8;
    private static final int BAR_SPACING = 2;

    @Override
    public void onHudRender(DrawContext context, RenderTickCounter tickCounter) {
        MinecraftClient client = MinecraftClient.getInstance();

        if (client.player == null) return;
        if (!MAMConfig.isOverlayEnabled()) return;
        if (client.options.hudHidden) return;

        ManaComponent component = MAM.getManaComponent(client.player);
        if (component == null) return;

        ManaPool pool = component.getManaPool();

        int screenWidth = client.getWindow().getScaledWidth();
        int screenHeight = client.getWindow().getScaledHeight();

        // Calculate position
        int x = MAMConfig.getOverlayXOffset() + 5;
        int y = screenHeight - MAMConfig.getOverlayYOffset() - 50;

        // Apply scale
        float scale = MAMConfig.getOverlayScale();
        context.getMatrices().push();
        context.getMatrices().scale(scale, scale, 1.0f);

        x = (int) (x / scale);
        y = (int) (y / scale);

        // Render bars
        renderManaBar(context, x, y, pool.getPrimaryMana(),
                     pool.getPrimaryMaxMana(), 0x9966CC, "Primary");
        renderManaBar(context, x, y + BAR_HEIGHT + BAR_SPACING,
                     pool.getSecondaryMana(), pool.getSecondaryMaxMana(),
                     0x6644AA, "Secondary");
        renderManaBar(context, x, y + (BAR_HEIGHT + BAR_SPACING) * 2,
                     pool.getTertiaryMana(), pool.getTertiaryMaxMana(),
                     0x443388, "Tertiary");

        context.getMatrices().pop();
    }

    private void renderManaBar(DrawContext context, int x, int y,
                              double current, double max,
                              int color, String label) {
        // Background
        context.fill(x, y, x + BAR_WIDTH, y + BAR_HEIGHT, 0xFF000000);

        // Mana fill
        double percent = max > 0 ? current / max : 0;
        int fillWidth = (int) (BAR_WIDTH * percent);
        context.fill(x, y, x + fillWidth, y + BAR_HEIGHT, 0xFF000000 | color);

        // Border
        context.drawBorder(x, y, BAR_WIDTH, BAR_HEIGHT, 0xFFFFFFFF);

        // Text
        String text = String.format("%s: %.0f / %.0f (%.0f%%)",
            label, current, max, percent * 100);
        context.drawText(MinecraftClient.getInstance().textRenderer,
            text, x + BAR_WIDTH + 5, y, 0xFFFFFFFF, true);
    }

    public static void register() {
        HudRenderCallback.EVENT.register(new ManaHudOverlay());
    }
}
```

---

## ⚙️ Configuration System

**File:** `src/main/java/dk/mosberg/config/MAMConfig.java`

```java
package dk.mosberg.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dk.mosberg.MAM;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class MAMConfig {

    private static final Gson GSON = new GsonBuilder()
        .setPrettyPrinting()
        .create();

    private static final Path CONFIG_PATH =
        FabricLoader.getInstance().getConfigDir().resolve("mana.json");

    private static ConfigData config = new ConfigData();

    public static class ConfigData {
        public OverlayConfig overlay = new OverlayConfig();
        public MagicConfig magic = new MagicConfig();
        public RenderConfig render = new RenderConfig();
        public WorldGenConfig worldgen = new WorldGenConfig();
    }

    public static class OverlayConfig {
        public boolean enabled = true;
        public double scale = 1.0;
        public int xOffset = 0;
        public int yOffset = 0;
        public double transparency = 1.0;
    }

    public static class MagicConfig {
        public SpellConfig spell = new SpellConfig();
        public RitualConfig ritual = new RitualConfig();
    }

    public static class SpellConfig {
        public ManaCostConfig manaCost = new ManaCostConfig();
    }

    public static class ManaCostConfig {
        public double multiplier = 1.0;
    }

    public static class RitualConfig {
        public DifficultyConfig difficulty = new DifficultyConfig();
    }

    public static class DifficultyConfig {
        public double multiplier = 1.0;
    }

    public static class RenderConfig {
        public HudConfig hud = new HudConfig();
    }

    public static class HudConfig {
        public ManaBarConfig manaBar = new ManaBarConfig();
        public CooldownOverlayConfig cooldownOverlay = new CooldownOverlayConfig();
        public DebugInfoConfig debugInfo = new DebugInfoConfig();
    }

    public static class ManaBarConfig {
        public boolean enabled = true;
    }

    public static class CooldownOverlayConfig {
        public boolean enabled = true;
    }

    public static class DebugInfoConfig {
        public boolean enabled = false;
    }

    public static class WorldGenConfig {
        public GemstoneOresConfig gemstoneOres = new GemstoneOresConfig();
        public ManaNodesConfig manaNodes = new ManaNodesConfig();
        public LeyLinesConfig leyLines = new LeyLinesConfig();
    }

    public static class GemstoneOresConfig {
        public boolean enabled = true;
        public double densityMultiplier = 1.0;
    }

    public static class ManaNodesConfig {
        public boolean enabled = true;
        public double spawnRate = 0.05;
    }

    public static class LeyLinesConfig {
        public boolean enabled = true;
        public int maxDistance = 512;
    }

    // Getters
    public static boolean isOverlayEnabled() {
        return config.overlay.enabled;
    }

    public static float getOverlayScale() {
        return (float) config.overlay.scale;
    }

    public static int getOverlayXOffset() {
        return config.overlay.xOffset;
    }

    public static int getOverlayYOffset() {
        return config.overlay.yOffset;
    }

    public static double getSpellManaCostMultiplier() {
        return config.magic.spell.manaCost.multiplier;
    }

    public static double getRitualDifficultyMultiplier() {
        return config.magic.ritual.difficulty.multiplier;
    }

    // Load/Save
    public static void load() {
        if (Files.exists(CONFIG_PATH)) {
            try {
                String json = Files.readString(CONFIG_PATH);
                config = GSON.fromJson(json, ConfigData.class);
                MAM.LOGGER.info("Configuration loaded from {}", CONFIG_PATH);
            } catch (IOException e) {
                MAM.LOGGER.error("Failed to load configuration", e);
                save(); // Save default
            }
        } else {
            save(); // Create default
        }
    }

    public static void save() {
        try {
            String json = GSON.toJson(config);
            Files.writeString(CONFIG_PATH, json);
            MAM.LOGGER.info("Configuration saved to {}", CONFIG_PATH);
        } catch (IOException e) {
            MAM.LOGGER.error("Failed to save configuration", e);
        }
    }
}
```

---

## 🔧 Code Patterns

### Singleton Pattern (Registry)

```java
public class SpellRegistry {
    private static final MAMRegistry<Spell> INSTANCE = new MAMRegistry<>("spells");

    private SpellRegistry() {} // Prevent instantiation

    public static MAMRegistry<Spell> getInstance() {
        return INSTANCE;
    }
}
```

### Builder Pattern (Spell Construction)

```java
public class SpellBuilder {
    private Identifier id;
    private SpellSchool school;
    private int level;
    private double manaCost;

    public SpellBuilder id(Identifier id) {
        this.id = id;
        return this;
    }

    public SpellBuilder school(SpellSchool school) {
        this.school = school;
        return this;
    }

    public SpellBuilder level(int level) {
        this.level = level;
        return this;
    }

    public SpellBuilder manaCost(double cost) {
        this.manaCost = cost;
        return this;
    }

    public Spell build() {
        return new BasicSpell(id, school, level, manaCost);
    }
}

// Usage
Spell spell = new SpellBuilder()
    .id(Identifier.of("mam", "fire_strike"))
    .school(SpellSchool.FIRE)
    .level(1)
    .manaCost(20.0)
    .build();
```

### Factory Pattern (Spell Creation)

```java
public class SpellFactory {
    public static Spell createProjectileSpell(Identifier id, SpellSchool school,
                                             int level, double manaCost) {
        return new ProjectileSpell(id, school, level, manaCost);
    }

    public static Spell createAoESpell(Identifier id, SpellSchool school,
                                      int level, double manaCost, double radius) {
        return new AoESpell(id, school, level, manaCost, radius);
    }

    public static Spell createUtilitySpell(Identifier id, SpellSchool school,
                                          int level, double manaCost) {
        return new UtilitySpell(id, school, level, manaCost);
    }
}
```

### Strategy Pattern (Spell Cast Types)

```java
public interface CastStrategy {
    void execute(ServerPlayerEntity caster, Spell spell);
}

public class ProjectileCastStrategy implements CastStrategy {
    @Override
    public void execute(ServerPlayerEntity caster, Spell spell) {
        // Spawn projectile entity
    }
}

public class AoECastStrategy implements CastStrategy {
    @Override
    public void execute(ServerPlayerEntity caster, Spell spell) {
        // Apply effects in radius
    }
}

public class SpellCaster {
    private final Map<CastType, CastStrategy> strategies = Map.of(
        CastType.PROJECTILE, new ProjectileCastStrategy(),
        CastType.AOE, new AoECastStrategy()
    );

    public void cast(ServerPlayerEntity caster, Spell spell) {
        CastStrategy strategy = strategies.get(spell.getCastType());
        if (strategy != null) {
            strategy.execute(caster, spell);
        }
    }
}
```

### Observer Pattern (Event System)

```java
public interface ManaObserver {
    void onManaChanged(ServerPlayerEntity player, double oldValue, double newValue);
}

public class ManaSubject {
    private final List<ManaObserver> observers = new ArrayList<>();

    public void addObserver(ManaObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(ManaObserver observer) {
        observers.remove(observer);
    }

    protected void notifyObservers(ServerPlayerEntity player,
                                  double oldValue, double newValue) {
        for (ManaObserver observer : observers) {
            observer.onManaChanged(player, oldValue, newValue);
        }
    }
}
```
