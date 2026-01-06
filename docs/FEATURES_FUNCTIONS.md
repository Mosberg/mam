# 🔮 Mana & Magic - Features & Functions Reference

**Version:** 1.0.0
**Last Updated:** January 6, 2026
**Mod ID:** `mam`

---

## 📖 Table of Contents

- [Mana Pool System](#mana-pool-system)
- [Spell System](#spell-system)
- [Ritual System](#ritual-system)
- [Gemstone System](#gemstone-system)
- [Enchantment System](#enchantment-system)
- [Progression System](#progression-system)
- [World Generation](#world-generation)
- [HUD & UI Systems](#hud--ui-systems)
- [Commands & Debug](#commands--debug)
- [Asset Generation Utilities](#asset-generation-utilities)

---

## 🛠️ Asset Generation Utilities

Palette-driven Python generators for refreshing all textures quickly. Requires Pillow installed.

- [texture_palettes.py](texture_palettes.py) centralizes school and gemstone colors plus ore base settings.
- Run `python generate_all_textures.py` to build everything (use `--overwrite` to rebuild existing files).
- Scope to a category with `--items`, `--blocks`, `--entities`, or `--gui`.
- Category generators: [generate_item_textures.py](generate_item_textures.py), [generate_block_textures.py](generate_block_textures.py), [generate_entity_textures.py](generate_entity_textures.py), [generate_gui_textures.py](generate_gui_textures.py).
- Outputs land in `src/main/resources/assets/mam/textures/`.

## 🌊 Mana Pool System

### Architecture

The mana system implements a **three-tier cascading pool** architecture with independent regeneration rates and capacity limits.

#### Pool Specifications

| Pool          | Max Capacity | Regen Rate | Priority   | Use Case                     |
| ------------- | ------------ | ---------- | ---------- | ---------------------------- |
| **Primary**   | 250          | 1.0/sec    | 1 (First)  | Low-cost spells (1-30 mana)  |
| **Secondary** | 500          | 0.75/sec   | 2 (Second) | Medium spells (31-100 mana)  |
| **Tertiary**  | 1000         | 0.5/sec    | 3 (Last)   | High-cost spells (100+ mana) |

#### Consumption Logic

```
Cast Spell (Cost: 75 mana)
┌─────────────────────────────────────────┐
│ Step 1: Check Primary (250 current)    │
│   ✓ Has enough, consume 75              │
│   Result: Primary = 175                 │
└─────────────────────────────────────────┘

Cast Spell (Cost: 200 mana, Primary: 150)
┌─────────────────────────────────────────┐
│ Step 1: Consume Primary (150)           │
│   Primary = 0, Remaining: 50            │
│ Step 2: Consume Secondary (500)         │
│   Secondary = 450, Remaining: 0         │
│   ✓ Cast successful                     │
└─────────────────────────────────────────┘

Cast Spell (Cost: 800 mana, All pools low)
┌─────────────────────────────────────────┐
│ Step 1: Primary (100) → 0               │
│   Remaining: 700                        │
│ Step 2: Secondary (200) → 0             │
│   Remaining: 500                        │
│ Step 3: Tertiary (400) → 0              │
│   ✗ Insufficient! Need 100 more         │
└─────────────────────────────────────────┘
```

#### Regeneration Logic

```
Regeneration Tick (Every 20 game ticks = 1 second)
┌─────────────────────────────────────────┐
│ Primary:   +1.0  (if < 250)             │
│ Secondary: +0.75 (if < 500)             │
│ Tertiary:  +0.5  (if < 1000)            │
│ Total:     +2.25 mana/sec maximum       │
└─────────────────────────────────────────┘

Example: 10 seconds from empty
  Primary:   0 → 10 mana
  Secondary: 0 → 7.5 mana
  Tertiary:  0 → 5 mana
  Total:     22.5 mana restored
```

### Java API Methods

#### ManaPool Class

```java
public class ManaPool {
    // Getters
    public double getPrimaryMana()
    public double getSecondaryMana()
    public double getTertiaryMana()
    public double getTotalMana()

    public double getPrimaryMaxMana()
    public double getSecondaryMaxMana()
    public double getTertiaryMaxMana()
    public double getTotalMaxMana()

    public double getPrimaryPercent()    // 0.0 to 1.0
    public double getSecondaryPercent()
    public double getTertiaryPercent()

    // Consumption
    public boolean consumeMana(double amount)
    public boolean canConsumeMana(double amount)

    // Restoration
    public void restoreMana(double amount)
    public void restorePool(ManaPoolType type)
    public void restoreAll()

    // Regeneration
    public void regenerate()  // Called every tick

    // Serialization
    public void writeNbt(NbtCompound nbt)
    public void readNbt(NbtCompound nbt)
}
```

#### ManaPoolHelper Utility

```java
public class ManaPoolHelper {
    // Player operations
    public static boolean tryConsumeMana(ServerPlayerEntity player, double amount)
    public static void restoreMana(ServerPlayerEntity player, double amount)
    public static boolean hasEnoughMana(ServerPlayerEntity player, double amount)

    // Formatting
    public static String formatMana(ServerPlayerEntity player, ManaPoolType type)
    // Example: "120.5 / 250.0"

    public static String formatTotalMana(ServerPlayerEntity player)
    // Example: "670.5 / 1750.0"

    public static String formatPercent(double percent)
    // Example: "48.3%"
}
```

### NBT Data Structure

```json
{
  "mam:mana": {
    "primary": 120.5,
    "secondary": 300.0,
    "tertiary": 750.0,
    "primary_max": 250.0,
    "secondary_max": 500.0,
    "tertiary_max": 1000.0,
    "regen_rate_primary": 1.0,
    "regen_rate_secondary": 0.75,
    "regen_rate_tertiary": 0.5,
    "last_tick": 1234567890
  }
}
```

---

## ✨ Spell System

### Spell Schools (13 Total)

#### Complete School Reference

| #   | School      | Symbol | Color       | Hex       | Focus       | Damage Type | Status Effects         |
| --- | ----------- | ------ | ----------- | --------- | ----------- | ----------- | ---------------------- |
| 1   | **Air**     | 🌀     | Light Gray  | `#C0C0C0` | Mobility    | Knockback   | Levitation, Speed      |
| 2   | **Arcane**  | 🔮     | Purple      | `#9966CC` | Utility     | Magic       | Glowing, Weaving       |
| 3   | **Blood**   | 🩸     | Dark Red    | `#8B0000` | Sacrifice   | True        | Wither, Weakness       |
| 4   | **Chaos**   | 🌪️     | Magenta     | `#FF00FF` | Random      | Variable    | Random Debuffs         |
| 5   | **Dark**    | 🌑     | Dark Purple | `#2D1B4E` | Corruption  | Shadow      | Blindness, Poison      |
| 6   | **Earth**   | 🌍     | Brown       | `#8B4513` | Defense     | Physical    | Resistance, Slowness   |
| 7   | **Fire**    | 🔥     | Orange      | `#FF4500` | Destruction | Fire        | Burning, Fire Res      |
| 8   | **Ice**     | ❄️     | Cyan        | `#00FFFF` | Control     | Frost       | Slowness, Freeze       |
| 9   | **Light**   | ✨     | Pale Yellow | `#FFF8DC` | Protection  | Holy        | Regen, Absorption      |
| 10  | **Nature**  | 🌿     | Green       | `#228B22` | Growth      | Natural     | Poison, Regen          |
| 11  | **Thunder** | ⚡     | Yellow      | `#FFD700` | Energy      | Electric    | Speed, Jump Boost      |
| 12  | **Void**    | 🕳️     | Black       | `#000000` | Dimensional | Void        | Nausea, Levitation     |
| 13  | **Water**   | 💧     | Blue        | `#1E90FF` | Healing     | Aquatic     | Regen, Water Breathing |

### Cast Types (5 Total)

| Cast Type                | Description                | Targeting       | Examples                              |
| ------------------------ | -------------------------- | --------------- | ------------------------------------- |
| **Projectile**           | Fired entity that travels  | Aimed direction | Fire Strike, Ice Bolt, Arcane Missile |
| **AoE (Area of Effect)** | Affects area around target | Point/Self      | Fireball, Blizzard, Inferno Nova      |
| **Utility**              | Non-combat effects         | Self/Target     | Heal, Teleport, Spell Weave           |
| **Channel**              | Sustained over time        | Held direction  | Flame Channel, Frost Beam             |
| **Ray**                  | Instant line effect        | Raycast         | Lightning Bolt, Celestial Beam        |

### Complete Spell List (50+ Spells)

#### Fire School (🔥) - 6 Spells

| Spell             | Type       | Level | Mana | Cooldown | Damage | Range | AoE | Effects                     | Description     |
| ----------------- | ---------- | ----- | ---- | -------- | ------ | ----- | --- | --------------------------- | --------------- |
| **fire_strike**   | Projectile | 1     | 20   | 1s       | 10     | 32m   | -   | Fire (3s)                   | Basic fire bolt |
| **flame_burst**   | AoE        | 15    | 40   | 5s       | 18     | Self  | 5m  | Fire (5s)                   | Ring of fire    |
| **fireball**      | Projectile | 25    | 50   | 8s       | 25     | 48m   | 3m  | Fire (8s), Knockback        | Explosive fire  |
| **inferno_nova**  | AoE        | 40    | 100  | 15s      | 40     | Self  | 8m  | Fire (10s), Mining Fatigue  | Massive blast   |
| **meteor_strike** | AoE        | 60    | 150  | 30s      | 60     | 64m   | 6m  | Fire (15s), Slowness        | Falling meteor  |
| **phoenix_rise**  | Utility    | 75    | 150  | 300s     | 0      | Self  | -   | Fire Res (60s), Regen (30s) | Resurrection    |

#### Ice School (❄️) - 5 Spells

| Spell              | Type       | Level | Mana | Cooldown | Damage | Range | AoE | Effects                        | Description         |
| ------------------ | ---------- | ----- | ---- | -------- | ------ | ----- | --- | ------------------------------ | ------------------- |
| **frost_bolt**     | Projectile | 1     | 25   | 2s       | 15     | 32m   | -   | Slowness II (5s)               | Chilling projectile |
| **ice_shard**      | Projectile | 10    | 35   | 4s       | 20     | 40m   | -   | Slowness III (8s)              | Sharp ice           |
| **ice_comet**      | Projectile | 30    | 75   | 12s      | 35     | 48m   | 2m  | Freeze (3s), Slowness IV (10s) | Massive ice bolt    |
| **blizzard_storm** | AoE        | 50    | 120  | 20s      | 50     | Self  | 10m | Freeze (5s), Blindness (8s)    | Area storm          |
| **glacial_prison** | Utility    | 65    | 90   | 25s      | 0      | 24m   | -   | Freeze (10s), Encased          | Ice trap            |

#### Arcane School (🔮) - 6 Spells

| Spell                | Type       | Level | Mana | Cooldown | Damage | Range | AoE | Effects             | Description    |
| -------------------- | ---------- | ----- | ---- | -------- | ------ | ----- | --- | ------------------- | -------------- |
| **arcane_missile**   | Projectile | 1     | 30   | 1.5s     | 18     | 40m   | -   | Glowing (5s)        | Homing magic   |
| **mana_shield**      | Utility    | 8     | 50   | 15s      | 0      | Self  | -   | Absorption (60s)    | Mana barrier   |
| **spell_weave**      | Utility    | 20    | 50   | 10s      | 0      | Self  | -   | Weaving (30s)       | Combo enhancer |
| **arcane_implosion** | AoE        | 35    | 90   | 18s      | 45     | 32m   | 5m  | Pull, Glowing (10s) | Gravity well   |
| **dimension_shift**  | Utility    | 55    | 100  | 45s      | 0      | 64m   | -   | -                   | Teleport       |
| **time_dilation**    | Utility    | 80    | 200  | 60s      | 0      | Self  | 15m | Slowness V (15s)    | Time slow      |

#### Light School (✨) - 5 Spells

| Spell                | Type    | Level | Mana | Cooldown | Damage | Range | AoE | Effects                   | Description   |
| -------------------- | ------- | ----- | ---- | -------- | ------ | ----- | --- | ------------------------- | ------------- |
| **heal**             | Utility | 1     | 40   | 8s       | 0      | Self  | -   | Regen II (10s), +6 hearts | Self heal     |
| **smite**            | Ray     | 12    | 55   | 6s       | 28     | 48m   | -   | Glowing (8s)              | Holy damage   |
| **holy_blast**       | AoE     | 28    | 60   | 10s      | 30     | Self  | 6m  | Regen I (10s)             | Radiant burst |
| **celestial_beam**   | Ray     | 48    | 110  | 20s      | 55     | 64m   | -   | Blindness (5s)            | Piercing ray  |
| **radiant_judgment** | AoE     | 70    | 150  | 35s      | 70     | 40m   | 12m | Regen III (20s), Glowing  | Area smite    |

#### Dark School (🌑) - 5 Spells

| Spell                 | Type       | Level | Mana | Cooldown | Damage | Range | AoE | Effects                            | Description     |
| --------------------- | ---------- | ----- | ---- | -------- | ------ | ----- | --- | ---------------------------------- | --------------- |
| **shadow_bolt**       | Projectile | 1     | 22   | 1.5s     | 12     | 32m   | -   | Blindness I (4s)                   | Dark projectile |
| **curse_of_weakness** | Utility    | 15    | 45   | 12s      | 0      | 24m   | -   | Weakness III (30s), Mining Fatigue | Debuff target   |
| **dark_pact**         | Utility    | 25    | 60   | 20s      | 0      | Self  | -   | Strength II (45s), -4 hearts       | Power sacrifice |
| **void_grasp**        | Ray        | 42    | 85   | 15s      | 38     | 40m   | -   | Slowness IV (8s), Pull             | Shadow pull     |
| **necrotic_plague**   | AoE        | 68    | 140  | 40s      | 55     | 32m   | 8m  | Wither III (15s), Poison II        | DOT cloud       |

#### Thunder School (⚡) - 4 Spells

| Spell               | Type | Level | Mana | Cooldown | Damage | Range | AoE | Effects                     | Description     |
| ------------------- | ---- | ----- | ---- | -------- | ------ | ----- | --- | --------------------------- | --------------- |
| **lightning_bolt**  | Ray  | 1     | 35   | 3s       | 22     | 48m   | -   | Speed II (5s)               | Instant strike  |
| **chain_lightning** | Ray  | 20    | 65   | 10s      | 30     | 40m   | -   | Jump 3 targets, Speed III   | Chaining bolt   |
| **thunder_clap**    | AoE  | 38    | 80   | 12s      | 42     | Self  | 7m  | Knockback, Deafened (10s)   | Sonic boom      |
| **storm_call**      | AoE  | 62    | 130  | 30s      | 58     | Self  | 15m | 10 random strikes, Speed IV | Lightning storm |

#### Nature School (🌿) - 5 Spells

| Spell             | Type       | Level | Mana | Cooldown | Damage | Range | AoE | Effects                     | Description  |
| ----------------- | ---------- | ----- | ---- | -------- | ------ | ----- | --- | --------------------------- | ------------ |
| **nature_strike** | Projectile | 1     | 18   | 1s       | 12     | 32m   | -   | Poison I (6s)               | Thorn bolt   |
| **growth_surge**  | Utility    | 10    | 50   | 15s      | 0      | Self  | 8m  | Grow crops, Regen I (15s)   | Plant growth |
| **verdant_surge** | AoE        | 24    | 45   | 8s       | 22     | 24m   | 5m  | Regen II (12s), Saturation  | Life wave    |
| **entangle**      | Utility    | 40    | 70   | 18s      | 0      | 32m   | -   | Root (8s), Slowness V       | Vine trap    |
| **nature_wrath**  | AoE        | 58    | 95   | 25s      | 38     | Self  | 10m | Poison III (20s), Root (6s) | Toxic thorns |

#### Water School (💧) - 4 Spells

| Spell            | Type       | Level | Mana | Cooldown | Damage | Range | AoE | Effects                           | Description     |
| ---------------- | ---------- | ----- | ---- | -------- | ------ | ----- | --- | --------------------------------- | --------------- |
| **water_jet**    | Projectile | 1     | 20   | 1.5s     | 10     | 32m   | -   | Knockback                         | Water blast     |
| **aqua_shield**  | Utility    | 12    | 55   | 20s      | 0      | Self  | -   | Water Breathing (60s), Absorption | Water barrier   |
| **tidal_wave**   | AoE        | 32    | 75   | 15s      | 35     | Self  | 12m | Knockback, Slow Falling           | Flood wave      |
| **purification** | Utility    | 50    | 80   | 30s      | 0      | Self  | 8m  | Remove debuffs, Regen III (15s)   | Cleansing water |

#### Earth School (🌍) - 4 Spells

| Spell                | Type       | Level | Mana | Cooldown | Damage | Range | AoE | Effects                           | Description      |
| -------------------- | ---------- | ----- | ---- | -------- | ------ | ----- | --- | --------------------------------- | ---------------- |
| **stone_spike**      | Projectile | 1     | 25   | 2s       | 16     | 24m   | -   | Slowness I (4s)                   | Rock projectile  |
| **earthen_armor**    | Utility    | 14    | 60   | 25s      | 0      | Self  | -   | Resistance III (60s), Slowness II | Stone skin       |
| **seismic_slam**     | AoE        | 30    | 70   | 12s      | 32     | Self  | 6m  | Knockback, Mining Fatigue         | Ground pound     |
| **mountain_bastion** | Utility    | 55    | 110  | 45s      | 0      | Self  | -   | Resistance V (90s), Absorption    | Ultimate defense |

#### Air School (🌀) - 4 Spells

| Spell          | Type       | Level | Mana | Cooldown | Damage | Range | AoE | Effects                        | Description |
| -------------- | ---------- | ----- | ---- | -------- | ------ | ----- | --- | ------------------------------ | ----------- |
| **wind_slash** | Projectile | 1     | 22   | 1s       | 14     | 40m   | -   | Knockback                      | Air blade   |
| **gust**       | Utility    | 10    | 35   | 8s       | 0      | Self  | -   | Speed IV (15s), Jump Boost III | Wind boost  |
| **cyclone**    | AoE        | 28    | 65   | 15s      | 28     | 32m   | 8m  | Levitation (8s), Knockback     | Tornado     |
| **wind_walk**  | Utility    | 52    | 90   | 35s      | 0      | Self  | -   | Speed V (45s), Slow Falling    | Air stride  |

#### Blood School (🩸) - 3 Spells

| Spell               | Type    | Level | Mana | Cooldown | Damage | Range | AoE | Effects                          | Description      |
| ------------------- | ------- | ----- | ---- | -------- | ------ | ----- | --- | -------------------------------- | ---------------- |
| **blood_siphon**    | Ray     | 18    | 40   | 8s       | 20     | 32m   | -   | Heal 50% damage, Wither I (5s)   | Life steal       |
| **blood_sacrifice** | Utility | 35    | 0    | 30s      | 0      | Self  | -   | -8 hearts, Strength IV (60s)     | Health for power |
| **crimson_rite**    | AoE     | 60    | 100  | 40s      | 48     | Self  | 10m | Heal 25% damage, Wither II (10s) | AoE life steal   |

#### Chaos School (🌪️) - 3 Spells

| Spell            | Type       | Level | Mana | Cooldown | Damage | Range | AoE | Effects        | Description    |
| ---------------- | ---------- | ----- | ---- | -------- | ------ | ----- | --- | -------------- | -------------- |
| **chaos_bolt**   | Projectile | 22    | 45   | 6s       | 10-40  | 32m   | -   | Random effect  | Unpredictable  |
| **reality_warp** | AoE        | 44    | 90   | 20s      | 25-50  | Self  | 8m  | Random debuffs | Chaos zone     |
| **entropy**      | Utility    | 72    | 150  | 60s      | 0-100  | 48m   | 12m | Random chaos   | Ultimate chaos |

#### Void School (🕳️) - 3 Spells

| Spell          | Type       | Level | Mana | Cooldown | Damage | Range | AoE | Effects                       | Description        |
| -------------- | ---------- | ----- | ---- | -------- | ------ | ----- | --- | ----------------------------- | ------------------ |
| **void_lance** | Projectile | 26    | 70   | 10s      | 38     | 48m   | -   | Nausea (8s), Weakness II      | Dimensional pierce |
| **rift_tear**  | AoE        | 50    | 120  | 30s      | 52     | 32m   | 6m  | Levitation (10s), Nausea      | Space tear         |
| **oblivion**   | AoE        | 85    | 200  | 90s      | 80     | 40m   | 15m | True damage, Nausea, Weakness | Void annihilation  |

### Spell JSON Schema

```json
{
  "id": "mam:fire_strike",
  "school": "fire",
  "type": "projectile",
  "level": 1,
  "mana_cost": 20,
  "cooldown": 20,
  "damage": 10.0,
  "range": 32.0,
  "aoe_radius": 0.0,
  "velocity": 1.5,
  "effects": [
    {
      "type": "minecraft:fire_resistance",
      "duration": 60,
      "amplifier": 0
    }
  ],
  "projectile": {
    "entity_type": "mam:fireball",
    "particle": "minecraft:flame",
    "sound": "minecraft:entity.blaze.shoot",
    "gravity": 0.0,
    "pierce": false
  }
}
```

---

## 🔷 Ritual System

### Ritual Categories (13 Total)

#### Category Overview

| #   | Category           | Symbol   | Color        | Focus         | Complexity  | Time Range | Examples                        |
| --- | ------------------ | -------- | ------------ | ------------- | ----------- | ---------- | ------------------------------- |
| 1   | **Ascension**      | 🔺       | Gold         | Transcendence | Very High   | 10-30 min  | Divine Ascension, Apotheosis    |
| 2   | **Circle**         | ⭕       | White        | Protection    | Medium      | 5-15 min   | Protective Circle, Ward Circle  |
| 3   | **Cosmic**         | 🌌       | Deep Purple  | Celestial     | Very High   | 15-45 min  | Cosmic Alignment, Star Calling  |
| 4   | **Elemental**      | 🔥💧🌿⚡ | Rainbow      | Fusion        | High        | 8-20 min   | Elemental Convergence           |
| 5   | **Fountain**       | ⛲       | Aqua         | Flow          | Medium      | 5-12 min   | Mana Fountain, Life Spring      |
| 6   | **Planar**         | 🌐       | Silver       | Dimensions    | Very High   | 10-25 min  | Portal Opening, Planar Shift    |
| 7   | **Reality**        | 🔄       | Prismatic    | Alteration    | Extreme     | 15-40 min  | Reality Bend, World Shaping     |
| 8   | **Resurrection**   | 💫       | Golden White | Life          | High        | 10-20 min  | Raise Dead, Soul Binding        |
| 9   | **Sacrifice**      | 🗡️       | Crimson      | Offering      | Low-High    | 2-15 min   | Blood Offering, Soul Sacrifice  |
| 10  | **Summoning**      | 👻       | Dark Purple  | Entities      | Medium-High | 5-18 min   | Summon Elemental, Call Familiar |
| 11  | **Temporal**       | ⏰       | Bronze       | Time          | Extreme     | 15-50 min  | Time Reversal, Age Acceleration |
| 12  | **Transformation** | 🦋       | Violet       | Form          | High        | 8-22 min   | Polymorph, Transmutation        |
| 13  | **Vortex**         | 🌀       | Storm Gray   | Force         | Medium      | 6-16 min   | Gravity Vortex, Storm Vortex    |

### Example Rituals

#### Ascension Rituals (🔺)

**Apotheosis Ritual**

- **ID:** `mam:apotheosis_ritual`
- **Category:** Ascension
- **Level:** 90
- **Duration:** 30 minutes
- **Mana Cost:** 5000 (drained over time)
- **Gemstones Required:**
  - 4x Tanzanite (Epic)
  - 8x Citrine (Uncommon)
  - 16x Topaz (Rare)
- **Pattern:**
  ```
       T       T = Tanzanite Block
     C   C     C = Citrine Block
    T     T    P = Central Altar
     C   C     A = Air (open space)
       P
  ```
- **Environmental Requirements:**
  - Time: Full Moon (Night 7/8)
  - Weather: Clear Sky
  - Biome: Mountains (Y > 200)
  - Surface: Must see sky
- **Effects:**
  - Grant 1 Ascension Level
  - Permanent +50 to all mana pools
  - Unlock Divine spell tier
  - 7-day cooldown per player
- **Particle Effects:** Golden spirals, celestial beams
- **Sound:** `minecraft:entity.ender_dragon.death` (on completion)

**Divine Blessing Ritual**

- **ID:** `mam:divine_blessing`
- **Level:** 75
- **Duration:** 15 minutes
- **Mana Cost:** 2000
- **Effects:**
  - Regen V (10 min)
  - Absorption X (10 min)
  - Resistance III (10 min)
  - Fire Resistance (10 min)

#### Cosmic Rituals (🌌)

**Cosmic Alignment**

- **ID:** `mam:cosmic_alignment`
- **Level:** 85
- **Duration:** 45 minutes
- **Mana Cost:** 8000
- **Gemstones:**
  - 8x Tanzanite
  - 12x Sapphire
  - 16x Moonstone
- **Pattern:** Complex star formation (radius 16 blocks)
- **Requirements:**
  - Time: Midnight (18000 ticks)
  - Weather: Thunderstorm preferred
  - Must align with constellation (randomized daily)
- **Effects:**
  - Summon Cosmic Entity (guardian)
  - Grant Cosmic Insight buff (24h)
  - +100% XP gain (2h)
  - Unlock cosmic spells

#### Elemental Rituals (🔥💧🌿⚡)

**Elemental Convergence**

- **ID:** `mam:elemental_convergence`
- **Level:** 60
- **Duration:** 20 minutes
- **Mana Cost:** 3000
- **Gemstones:**
  - 4x Ruby (Fire)
  - 4x Sapphire (Ice)
  - 4x Jade (Nature)
  - 4x Citrine (Thunder)
- **Pattern:** Cross formation with elemental altars
- **Effects:**
  - Summon Elemental Spirit
  - Grant Elemental Mastery (30 min)
  - -25% spell costs for all elements (1h)

#### Summoning Rituals (👻)

**Summon Arcane Golem**

- **ID:** `mam:summon_arcane_golem`
- **Level:** 45
- **Duration:** 10 minutes
- **Mana Cost:** 1500
- **Gemstones:**
  - 6x Tanzanite
  - 8x Hematite
- **Pattern:** Pentagon with central summoning circle
- **Effects:**
  - Summon Arcane Golem (follows player, 1h duration)
  - 250 HP, 20 attack damage
  - Immune to magic

**Call Familiar**

- **ID:** `mam:call_familiar`
- **Level:** 25
- **Duration:** 5 minutes
- **Mana Cost:** 500
- **Gemstones:** 4x Sodalite
- **Effects:**
  - Summon random familiar (cat/owl/raven)
  - Permanent until death
  - Provides passive bonuses

#### Resurrection Rituals (💫)

**Raise Dead**

- **ID:** `mam:raise_dead`
- **Level:** 70
- **Duration:** 20 minutes
- **Mana Cost:** 4000
- **Gemstones:**
  - 8x Rhodonite
  - 12x Citrine
  - 1x Diamond
- **Items Required:**
  - Player Skull (target player)
  - Golden Apple
- **Pattern:** Circle of life (radius 8)
- **Effects:**
  - Resurrect dead player at ritual location
  - Target respawns with 50% stats
  - Cannot be used on same player for 7 days

#### Temporal Rituals (⏰)

**Time Reversal**

- **ID:** `mam:time_reversal`
- **Level:** 95
- **Duration:** 50 minutes
- **Mana Cost:** 10000
- **Gemstones:** 16x Moonstone
- **Requirements:**
  - Time: Exact midnight
  - Must have Chronomancer specialization
- **Effects:**
  - Revert local area (32 block radius) by 1 hour
  - Restore destroyed blocks
  - Undo mob deaths
  - Cannot affect players or inventories

### Ritual JSON Schema

```json
{
  "id": "mam:apotheosis_ritual",
  "category": "ascension",
  "level": 90,
  "duration": 36000,
  "mana_cost": 5000,
  "gemstones": {
    "mam:tanzanite": 4,
    "mam:citrine": 8,
    "mam:topaz": 16
  },
  "pattern": {
    "type": "custom",
    "blocks": [
      {"pos": , "block": "mam:arcane_altar"},
      {"pos":, "block": "mam:tanzanite_block"},[1]
      {"pos": [-2, 0, 2], "block": "mam:tanzanite_block"},
      {"pos": [2, 0, -2], "block": "mam:tanzanite_block"},
      {"pos": [-2, 0, -2], "block": "mam:tanzanite_block"}
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
      "amount": 50,
      "permanent": true
    }
  ],
  "cooldown": 604800
}
```

---

## 💎 Gemstone System

### Complete Gemstone Reference (15 Types)

#### Epic Tier Gemstones (Levels 51+)

| Gemstone      | Symbol | Color     | Hex       | Rarity | Shape    | Schools      | Rituals                   | Ore Spawn   | Y-Level | Vein Size |
| ------------- | ------ | --------- | --------- | ------ | -------- | ------------ | ------------------------- | ----------- | ------- | --------- |
| **Ruby**      | 🔴     | Deep Red  | `#E63946` | Epic   | Round    | Fire, Blood  | Elemental, Sacrifice      | 1/8 chunks  | 5-20    | 2-4       |
| **Sapphire**  | 🔵     | Deep Blue | `#2952A3` | Epic   | Round    | Ice          | Elemental, Cosmic         | 1/8 chunks  | 5-20    | 2-4       |
| **Tanzanite** | 🟣     | Purple    | `#6B4B9E` | Epic   | Princess | Void, Arcane | Planar, Cosmic, Summoning | 1/10 chunks | 0-15    | 1-3       |

#### Rare Tier Gemstones (Levels 31-50)

| Gemstone       | Symbol | Color        | Hex       | Rarity | Shape     | Schools              | Rituals                            | Ore Spawn  | Y-Level | Vein Size |
| -------------- | ------ | ------------ | --------- | ------ | --------- | -------------------- | ---------------------------------- | ---------- | ------- | --------- |
| **Apatite**    | 🟦     | Teal Blue    | `#5DADE2` | Rare   | Oval      | Ice, Water           | Vortex                             | 1/6 chunks | 20-60   | 3-6       |
| **Aquamarine** | 💠     | Light Blue   | `#7FDBFF` | Rare   | Emerald   | Ice, Water           | Fountain                           | 1/6 chunks | 30-70   | 3-6       |
| **Moonstone**  | ⚪     | Pearly White | `#E8E5E0` | Rare   | Oval      | Air                  | Circle, Cosmic, Temporal           | 1/6 chunks | 40-80   | 3-5       |
| **Rhodonite**  | 🌸     | Pink         | `#EE99AC` | Rare   | Round     | Chaos, Blood         | Resurrection                       | 1/6 chunks | 10-50   | 3-5       |
| **Topaz**      | 🟠     | Amber Orange | `#D98736` | Rare   | Oval      | Light, Thunder, Fire | Ascension                          | 1/6 chunks | 30-70   | 4-7       |
| **Tourmaline** | 🎨     | Multicolor   | `#FF69B4` | Rare   | Elongated | Chaos, Fire, Nature  | Elemental, Reality, Transformation | 1/6 chunks | 15-55   | 3-6       |

#### Uncommon Tier Gemstones (Levels 11-30)

| Gemstone      | Symbol | Color      | Hex       | Rarity   | Shape | Schools        | Rituals                 | Ore Spawn  | Y-Level | Vein Size |
| ------------- | ------ | ---------- | --------- | -------- | ----- | -------------- | ----------------------- | ---------- | ------- | --------- |
| **Carnelian** | 🟥     | Red-Orange | `#FF6347` | Uncommon | Oval  | Fire, Blood    | Sacrifice               | 1/4 chunks | 20-80   | 4-8       |
| **Citrine**   | 🟡     | Yellow     | `#FFD700` | Uncommon | Oval  | Light, Thunder | Ascension, Resurrection | 1/4 chunks | 25-85   | 4-8       |
| **Jade**      | 🟢     | Green      | `#00A36C` | Uncommon | Oval  | Earth, Nature  | Transformation          | 1/4 chunks | 30-90   | 5-9       |
| **Peridot**   | 🍏     | Lime Green | `#9ACD32` | Uncommon | Oval  | Earth, Nature  | Transformation          | 1/4 chunks | 35-95   | 5-9       |
| **Sodalite**  | 🔷     | Deep Blue  | `#1C3A70` | Uncommon | Round | Dark, Void     | Summoning, Vortex       | 1/4 chunks | 15-75   | 4-8       |

#### Common Tier Gemstones (Levels 1-10)

| Gemstone     | Symbol | Color         | Hex       | Rarity | Shape | Schools           | Rituals              | Ore Spawn  | Y-Level | Vein Size |
| ------------ | ------ | ------------- | --------- | ------ | ----- | ----------------- | -------------------- | ---------- | ------- | --------- |
| **Hematite** | ⚫     | Metallic Gray | `#696969` | Common | Round | Earth, Dark, Void | Sacrifice, Summoning | 1/2 chunks | 10-120  | 6-12      |

### Gemstone Properties

#### School Affinity Matrix

| School      | Primary Gems                  | Secondary Gems | Bonus Effect           |
| ----------- | ----------------------------- | -------------- | ---------------------- |
| **Air**     | Moonstone                     | -              | +15% spell velocity    |
| **Arcane**  | Tanzanite                     | -              | +10% mana efficiency   |
| **Blood**   | Ruby, Carnelian               | Rhodonite      | +20% life steal        |
| **Chaos**   | Tourmaline                    | Rhodonite      | +25% effect variance   |
| **Dark**    | Sodalite, Hematite            | -              | +15% DOT duration      |
| **Earth**   | Hematite, Jade, Peridot       | -              | +20% defense bonus     |
| **Fire**    | Ruby, Carnelian, Topaz        | Tourmaline     | +15% burn damage       |
| **Ice**     | Sapphire, Aquamarine, Apatite | -              | +20% slow duration     |
| **Light**   | Citrine, Topaz                | -              | +15% healing power     |
| **Nature**  | Jade, Peridot                 | Tourmaline     | +15% growth effects    |
| **Thunder** | Citrine, Topaz                | -              | +20% chain targets     |
| **Void**    | Tanzanite, Hematite           | Sodalite       | +25% true damage       |
| **Water**   | Apatite, Aquamarine           | -              | +15% healing over time |

#### Ritual Affinity Matrix

| Ritual Category    | Required Gems                  | Optional Gems | Enhancement Effect    |
| ------------------ | ------------------------------ | ------------- | --------------------- |
| **Ascension**      | Citrine, Topaz                 | Tanzanite     | -15% mana cost        |
| **Circle**         | Moonstone                      | Any           | +20% duration         |
| **Cosmic**         | Tanzanite, Sapphire, Moonstone | Topaz         | +25% power            |
| **Elemental**      | Ruby, Sapphire, Tourmaline     | Jade, Citrine | +30% elemental damage |
| **Fountain**       | Aquamarine                     | Apatite       | +50% flow rate        |
| **Planar**         | Tanzanite                      | Moonstone     | +10 block range       |
| **Reality**        | Tourmaline                     | Tanzanite     | -20% cooldown         |
| **Resurrection**   | Rhodonite, Citrine             | Topaz         | -25% health penalty   |
| **Sacrifice**      | Ruby, Carnelian, Hematite      | Sodalite      | +30% power gain       |
| **Summoning**      | Sodalite, Tanzanite, Hematite  | Any Dark      | +1 summon             |
| **Temporal**       | Moonstone                      | Tanzanite     | +15% time effect      |
| **Transformation** | Jade, Peridot, Tourmaline      | Any           | +20% duration         |
| **Vortex**         | Apatite, Sodalite              | Moonstone     | +5 block radius       |

### Gemstone Items

#### Raw Gemstones

```java
// Dropped from ore, used in crafting
public class RawGemstoneItem extends Item {
    private final Gemstone gemstone;

    @Override
    public Text getName() {
        return Text.translatable("item.mam.raw_" + gemstone.getId());
    }
}
```

#### Cut Gemstones

```java
// Crafted from raw, used in spells/rituals
public class CutGemstoneItem extends Item implements MagicCatalyst {
    private final Gemstone gemstone;

    @Override
    public double getManaBonus() {
        return gemstone.getRarity().getManaBonus();
    }

    @Override
    public List<SpellSchool> getSchoolAffinity() {
        return gemstone.getSchools();
    }
}
```

#### Gemstone Blocks

```java
// Decorative and ritual components
public class GemstoneBlock extends Block {
    private final Gemstone gemstone;

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state,
                         @Nullable LivingEntity placer, ItemStack itemStack) {
        // Check if part of ritual pattern
        RitualPattern.checkPattern(world, pos);
    }
}
```

### Crafting Recipes

#### Cut Gemstone Recipe

```json
{
  "type": "minecraft:crafting_shapeless",
  "ingredients": [
    { "item": "mam:raw_ruby" },
    { "item": "minecraft:iron_pickaxe" }
  ],
  "result": {
    "item": "mam:ruby",
    "count": 1
  }
}
```

#### Gemstone Block Recipe

```json
{
  "type": "minecraft:crafting_shaped",
  "pattern": ["GGG", "GGG", "GGG"],
  "key": {
    "G": { "item": "mam:ruby" }
  },
  "result": {
    "item": "mam:ruby_block",
    "count": 1
  }
}
```

#### Magic Staff Recipe (with Gemstone)

```json
{
  "type": "minecraft:crafting_shaped",
  "pattern": ["  G", " S ", "S  "],
  "key": {
    "G": { "tag": "mam:gemstones" },
    "S": { "item": "minecraft:stick" }
  },
  "result": {
    "item": "mam:magic_staff",
    "count": 1,
    "nbt": {
      "gemstone": "mam:ruby",
      "bonus_mana": 50
    }
  }
}
```

#### Spell Book Recipes (Corrected)

All 13 spell book recipes now return their respective spell book items via explicit `result.id`/`count` fields (fixed on January 6, 2026).

```json
{
  "type": "minecraft:crafting_shaped",
  "pattern": ["GBG", "BPB", "GBG"],
  "key": {
    "G": [{ "tag": "mam:gemstones_uncommon" }],
    "B": [{ "item": "minecraft:book" }],
    "P": [{ "item": "minecraft:paper" }]
  },
  "result": {
    "id": "mam:air_spell_book",
    "count": 1
  }
}
```

### Ore Generation

#### Gemstone Ore Features

```json
{
  "type": "minecraft:ore",
  "config": {
    "size": 4,
    "discard_chance_on_air_exposure": 0.0,
    "targets": [
      {
        "target": {
          "predicate_type": "minecraft:tag_match",
          "tag": "minecraft:stone_ore_replaceables"
        },
        "state": {
          "Name": "mam:ruby_ore"
        }
      },
      {
        "target": {
          "predicate_type": "minecraft:tag_match",
          "tag": "minecraft:deepslate_ore_replaceables"
        },
        "state": {
          "Name": "mam:deepslate_ruby_ore"
        }
      }
    ]
  }
}
```

#### Ore Distribution by Tier

**Epic Tier Distribution:**

```
Y-Level: 0-20
Veins per Chunk: 0.125 (1 per 8 chunks)
Vein Size: 2-4 ores
Biome Restriction: None
Total per Chunk Section: ~0.4 ores average
```

**Rare Tier Distribution:**

```
Y-Level: 10-80 (varies by type)
Veins per Chunk: 0.167 (1 per 6 chunks)
Vein Size: 3-6 ores
Biome Restriction: Some types
Total per Chunk Section: ~0.75 ores average
```

**Uncommon Tier Distribution:**

```
Y-Level: 15-95 (varies by type)
Veins per Chunk: 0.25 (1 per 4 chunks)
Vein Size: 4-9 ores
Biome Restriction: None
Total per Chunk Section: ~1.6 ores average
```

**Common Tier Distribution:**

```
Y-Level: 10-120
Veins per Chunk: 0.5 (1 per 2 chunks)
Vein Size: 6-12 ores
Biome Restriction: None
Total per Chunk Section: ~4.5 ores average
```

---

## ✨ Enchantment System

### Magic Enchantments (3 Types)

#### 1. Mana Capacity

**Enchantment ID:** `mam:capacity`

| Level | Max Mana Bonus    | Applied To         | Cost      |
| ----- | ----------------- | ------------------ | --------- |
| I     | +50 to all pools  | Armor (Chestplate) | 15 levels |
| II    | +100 to all pools | Armor (Chestplate) | 25 levels |
| III   | +150 to all pools | Armor (Chestplate) | 35 levels |

**Incompatible With:** None
**Max Level:** III
**Rarity:** Rare

**Crafting:**

```json
{
  "type": "minecraft:enchanting_table",
  "enchantment": "mam:capacity",
  "min_level": 15,
  "max_level": 35,
  "treasure": false,
  "discoverable": true,
  "tradeable": true
}
```

#### 2. Mana Efficiency

**Enchantment ID:** `mam:efficiency`

| Level | Mana Cost Reduction | Applied To    | Cost      |
| ----- | ------------------- | ------------- | --------- |
| I     | -10%                | Wands, Staves | 10 levels |
| II    | -20%                | Wands, Staves | 20 levels |
| III   | -30%                | Wands, Staves | 30 levels |
| IV    | -40%                | Wands, Staves | 40 levels |
| V     | -50%                | Wands, Staves | 50 levels |

**Incompatible With:** None
**Max Level:** V
**Rarity:** Very Rare

**Effect Stacking:**

```
Base Spell Cost: 100 mana
Efficiency IV (-40%): 100 * 0.6 = 60 mana
Config Multiplier (2.0): 60 * 2.0 = 120 mana final
```

#### 3. Spell Potency

**Enchantment ID:** `mam:potency`

| Level | Damage Bonus | Duration Bonus | Applied To    | Cost      |
| ----- | ------------ | -------------- | ------------- | --------- |
| I     | +10%         | +10%           | Wands, Staves | 12 levels |
| II    | +20%         | +20%           | Wands, Staves | 22 levels |
| III   | +30%         | +30%           | Wands, Staves | 32 levels |
| IV    | +40%         | +40%           | Wands, Staves | 42 levels |

**Incompatible With:** None
**Max Level:** IV
**Rarity:** Rare

**Effect Examples:**

```
Fireball Base Damage: 25
Potency III (+30%): 25 * 1.3 = 32.5 damage

Fire Effect Duration: 8 seconds
Potency III (+30%): 8 * 1.3 = 10.4 seconds
```

### Enchantment Synergies

| Combination                       | Effect                          | Optimal For   |
| --------------------------------- | ------------------------------- | ------------- |
| **Efficiency V + Potency IV**     | Powerful, cost-effective spells | Combat mages  |
| **Capacity III + Efficiency III** | Extended casting sessions       | Support mages |
| **Potency IV + Capacity II**      | Maximum damage potential        | DPS mages     |

---

## 📈 Progression System

### Mage Level System

#### Level Requirements

| Level Range | XP per Level | Total XP  | Unlocks                             |
| ----------- | ------------ | --------- | ----------------------------------- |
| 1-10        | 100 XP       | 1,000     | Basic spells, Common gemstones      |
| 11-20       | 250 XP       | 3,500     | Uncommon spells, Uncommon gemstones |
| 21-30       | 500 XP       | 8,500     | Rare spells, Basic rituals          |
| 31-40       | 1,000 XP     | 18,500    | Rare gemstones, Advanced spells     |
| 41-50       | 2,000 XP     | 38,500    | Epic spells, Complex rituals        |
| 51-60       | 4,000 XP     | 78,500    | Epic gemstones, Master spells       |
| 61-70       | 8,000 XP     | 158,500   | Legendary spells, Grand rituals     |
| 71-80       | 16,000 XP    | 318,500   | Ultimate spells, School mastery     |
| 81-90       | 32,000 XP    | 638,500   | Cosmic spells, Planar rituals       |
| 91-100      | 64,000 XP    | 1,278,500 | Divine spells, Ascension rituals    |

#### XP Sources

| Action                        | XP Gained | Notes                |
| ----------------------------- | --------- | -------------------- |
| **Cast Basic Spell**          | 5 XP      | Levels 1-20 spells   |
| **Cast Advanced Spell**       | 15 XP     | Levels 21-50 spells  |
| **Cast Master Spell**         | 30 XP     | Levels 51-75 spells  |
| **Cast Ultimate Spell**       | 60 XP     | Levels 76-100 spells |
| **Complete Ritual (Simple)**  | 100 XP    | Duration < 10 min    |
| **Complete Ritual (Complex)** | 300 XP    | Duration 10-30 min   |
| **Complete Ritual (Grand)**   | 600 XP    | Duration > 30 min    |
| **Discover New Spell**        | 50 XP     | First time only      |
| **Kill with Magic**           | 10 XP     | Per mob killed       |
| **Mine Gemstone Ore**         | 3 XP      | Per ore broken       |
| **Craft Magic Item**          | 20 XP     | Per item crafted     |

### Specialization System

#### School Specializations

Players can specialize in up to **3 schools** at Level 30, 50, and 70.

**Specialization Bonuses:**

| School      | Primary Bonus         | Secondary Bonus           | Ultimate Bonus (Mastery)                      |
| ----------- | --------------------- | ------------------------- | --------------------------------------------- |
| **Fire**    | +25% fire damage      | -15% mana cost            | Immune to fire, burning applies to attackers  |
| **Ice**     | +30% slow duration    | +20% freeze chance        | Immune to freeze, enemies in 8m radius slowed |
| **Arcane**  | +20% all magic damage | -20% cooldown             | Spell combos grant stacking damage buff       |
| **Light**   | +50% healing power    | +25% absorption           | Aura heals nearby allies, damages undead      |
| **Dark**    | +35% DOT damage       | +40% curse duration       | Life steal on all dark spells                 |
| **Thunder** | +30% burst damage     | +2 chain targets          | Lightning strikes nearby enemies randomly     |
| **Nature**  | +40% regen power      | +25% growth effects       | Summon healing wisps, passive regen           |
| **Water**   | +30% healing          | Permanent water breathing | Swimming grants Speed IV, water spells free   |
| **Earth**   | +50% defense bonus    | -25% damage taken         | Stone skin passive, immunity to knockback     |
| **Air**     | +40% movement speed   | +50% spell velocity       | Permanent slow falling, jump boost            |
| **Blood**   | +50% life steal       | -health costs halved      | Max health +20 hearts                         |
| **Chaos**   | +40% effect variance  | Random buffs on cast      | Every 10th spell guaranteed crit (3x damage)  |
| **Void**    | +50% true damage      | +25% dimensional effects  | Teleport has no cooldown, -50% cost           |

#### Mastery Progression

```
Level 30: Choose First Specialization
  ↓ Unlock Tier 1 Bonus

Level 40: Tier 1 → Tier 2 Bonus
  ↓ +10% bonus effectiveness

Level 50: Choose Second Specialization
  ↓ Unlock Tier 1 Bonus (2nd school)
  ↓ First school → Tier 3 Bonus

Level 60: Second school → Tier 2
  ↓ First school → Tier 4 Bonus

Level 70: Choose Third Specialization
  ↓ All schools active

Level 80: Second school → Tier 3
  ↓ First school → Tier 5 (Ultimate)

Level 90: Third school → Tier 2
  ↓ Second school → Tier 4

Level 100: All schools mastered
  ↓ All schools → Ultimate Bonus
  ↓ Unlock Archmagus title
```

### Skill Trees

#### Universal Skills (Available to All)

| Skill                      | Level | Effect                      | Cost     |
| -------------------------- | ----- | --------------------------- | -------- |
| **Mana Attunement I**      | 5     | +25 max mana (all pools)    | 1 point  |
| **Mana Attunement II**     | 15    | +50 max mana (all pools)    | 2 points |
| **Mana Attunement III**    | 30    | +100 max mana (all pools)   | 3 points |
| **Rapid Regeneration I**   | 10    | +0.25/sec regen (all pools) | 1 point  |
| **Rapid Regeneration II**  | 25    | +0.5/sec regen (all pools)  | 2 points |
| **Rapid Regeneration III** | 45    | +1.0/sec regen (all pools)  | 3 points |
| **Efficient Casting I**    | 8     | -5% mana cost (all spells)  | 1 point  |
| **Efficient Casting II**   | 20    | -10% mana cost (all spells) | 2 points |
| **Efficient Casting III**  | 35    | -15% mana cost (all spells) | 3 points |
| **Swift Casting I**        | 12    | -10% cooldown (all spells)  | 1 point  |
| **Swift Casting II**       | 28    | -20% cooldown (all spells)  | 2 points |
| **Swift Casting III**      | 50    | -30% cooldown (all spells)  | 3 points |
| **Gemstone Affinity**      | 18    | +50% gemstone bonuses       | 2 points |
| **Ritual Master**          | 40    | -25% ritual time            | 3 points |
| **Arcane Knowledge**       | 55    | +25% XP gain                | 3 points |

**Skill Points:**

- Earn 1 skill point per 5 levels (20 points total at level 100)
- Can respec for 50,000 mana (all pools consumed)

---

## 🌍 World Generation

### Generated Structures

#### 1. Gemstone Ores

**Distribution by Biome:**

| Biome Type    | Rare Multiplier | Epic Multiplier | Preferred Gems      |
| ------------- | --------------- | --------------- | ------------------- |
| **Mountains** | 1.5x            | 2.0x            | Sapphire, Moonstone |
| **Desert**    | 1.2x            | 1.0x            | Topaz, Citrine      |
| **Forest**    | 1.0x            | 1.0x            | Jade, Peridot       |
| **Ocean**     | 1.3x            | 1.5x            | Aquamarine, Apatite |
| **Nether**    | 2.0x            | 2.5x            | Ruby, Carnelian     |
| **End**       | 3.0x            | 4.0x            | Tanzanite           |

#### 2. Mana Nodes

**Structure:** Floating crystal formation (3x3x5 blocks)

**Components:**

- Central Mana Crystal (glowing, animated)
- 4 Orbiting Crystal Shards
- Particle effects (arcane sparkles)
- Light level: 15

**Spawn Rate:** 5% per chunk (configurable)

**Effects:**

- Standing within 8 blocks: +2.0 mana regen/sec bonus
- Breaking central crystal: Grants 100-500 mana instantly
- Respawn time: 10 Minecraft days (200 real minutes)

**Loot Table:**

```json
{
  "type": "minecraft:block",
  "pools": [
    {
      "rolls": 1,
      "entries": [
        {
          "type": "minecraft:item",
          "name": "mam:mana_crystal",
          "weight": 1,
          "functions": [
            {
              "function": "minecraft:set_count",
              "count": { "min": 1, "max": 3 }
            }
          ]
        }
      ]
    },
    {
      "rolls": 1,
      "entries": [
        {
          "type": "minecraft:item",
          "name": "mam:raw_gemstone",
          "weight": 1,
          "functions": [
            {
              "function": "minecraft:set_nbt",
              "tag": "{gemstone:'random'}"
            }
          ]
        }
      ],
      "conditions": [
        {
          "condition": "minecraft:random_chance",
          "chance": 0.3
        }
      ]
    }
  ]
}
```

#### 3. Ley Lines

**Structure:** Underground stream of energy blocks (Y: 5-40)

**Appearance:**

- Glowing blue-purple energy blocks
- Follows winding path between mana nodes
- Average length: 128-512 blocks
- Width: 1-3 blocks

**Mechanics:**

- Connects nearby mana nodes (max 512 blocks apart)
- Provides +0.5 mana regen/sec when standing on
- Can be harvested for Ley Line Essence (brewing ingredient)
- Used in advanced rituals for power boost

#### 4. Altar Ruins

**Structure:** Ancient stone platform (11x11x7 blocks)

**Components:**

- Cracked stone brick platform
- 4 Crumbling pillars (corners)
- Central arcane altar (functional)
- Overgrown with vines/moss
- Hidden chest with loot

**Spawn Rate:** 1 per 16 chunks

**Loot:**

```
Common:
  - Ancient Grimoire (spell discovery book)
  - Gemstone (uncommon tier)
  - Magic Catalyst
  - Mana Potion

Rare:
  - Enchanted Book (mana enchantment)
  - Gemstone (rare tier)
  - Ritual Components
  - Ancient Wand

Epic (5% chance):
  - Gemstone (epic tier)
  - Pre-enchanted Magic Staff
  - Ritual Scroll (complete ritual)
```

#### 5. Magic Trees

**Types:**

- **Mana Oak:** Blue-tinted leaves, drop mana berries
- **Arcane Willow:** Purple leaves, drop spell fragments
- **Ethereal Birch:** White glowing bark, drop ethereal wood

**Spawn Rate:** 0.5% per tree in magical biomes

**Special Properties:**

- Leaves emit light level 8
- Wood has +10% enchantability
- Berries restore 50 mana when eaten

#### 6. Mana Springs

**Structure:** Pool of liquid mana (5x5x2 blocks)

**Appearance:**

- Glowing blue liquid (custom fluid)
- Particle effects above surface
- Surrounded by arcane grass/flowers

**Mechanics:**

- Swimming in spring: Restore 10 mana/second
- Fill bottles to create Mana Potions
- Fish in spring: Chance for magical fish (buff items)
- Renewable: Spring refills over time

---

## 🎨 HUD & UI Systems

### Mana HUD Overlay

#### Components

**1. Mana Bars (Bottom Left)**

```
┌─────────────────────────────────┐
│ ♥♥♥♥♥♥♥♥♥♥                      │ ← Health (vanilla)
│ Primary   [████████░░]  90%     │ ← Primary Pool
│ Secondary [██████░░░░]  60%     │ ← Secondary Pool
│ Tertiary  [███░░░░░░░]  30%     │ ← Tertiary Pool
└─────────────────────────────────┘
```

**2. Selected Spell Display (Bottom Right)**

```
┌─────────────────────────┐
│   🔥 Fire Strike        │
│   Cost: 20 mana         │
│   Cooldown: Ready       │
└─────────────────────────┘
```

**3. Cooldown Overlay (Around Crosshair)**

```
      [Spell Icons with circular cooldown overlay]

   🔥      🧊      ⚡
  Ready  3.5s    Ready
```

**4. Buff/Debuff Icons (Top Right)**

```
[⚡ Speed IV]  [🛡️ Absorption]  [🔥 Fire Res]
   45s              120s             60s
```

### Configuration Options

```json
{
  "hud": {
    "manaBar": {
      "enabled": true,
      "position": "BOTTOM_LEFT",
      "xOffset": 5,
      "yOffset": 50,
      "scale": 1.0,
      "showPercentage": true,
      "showNumbers": true,
      "barWidth": 100,
      "barHeight": 8,
      "spacing": 2
    },
    "spellDisplay": {
      "enabled": true,
      "position": "BOTTOM_RIGHT",
      "xOffset": -120,
      "yOffset": 50,
      "scale": 1.0
    },
    "cooldownOverlay": {
      "enabled": true,
      "radius": 40,
      "iconSize": 24,
      "showText": true
    },
    "buffIcons": {
      "enabled": true,
      "position": "TOP_RIGHT",
      "xOffset": -5,
      "yOffset": 5,
      "iconSize": 20,
      "showTimer": true
    }
  }
}
```

---

## ⚙️ Commands & Debug

### Player Commands

#### `/mana` - Mana Management

```bash
# View current mana
/mana get
# Output: Primary: 120/250, Secondary: 300/500, Tertiary: 750/1000

# Set mana (requires OP)
/mana set <player> <pool> <amount>
/mana set @s primary 250
/mana set Notch secondary 500

# Add mana
/mana add <player> <amount>
/mana add @s 100

# Restore all pools
/mana restore <player>
/mana restore @s

# Reset mana system
/mana reset <player>
/mana reset @s
```

#### `/spell` - Spell Management

```bash
# List known spells
/spell list

# Learn spell
/spell learn <spell_id>
/spell learn mam:fireball

# Forget spell
/spell forget <spell_id>

# Cast spell directly (bypass UI)
/spell cast <spell_id>
/spell cast mam:fire_strike

# Get spell info
/spell info <spell_id>
/spell info mam:phoenix_rise
```

#### `/ritual` - Ritual Management

```bash
# List available rituals
/ritual list

# Check ritual pattern
/ritual check <ritual_id>
/ritual check mam:apotheosis_ritual

# Force start ritual (debug)
/ritual start <ritual_id> <pos>
/ritual start mam:cosmic_alignment ~ ~ ~

# Cancel active ritual
/ritual cancel
```

#### `/magelevel` - Progression

```bash
# View level
/magelevel get <player>
/magelevel get @s

# Set level (requires OP)
/magelevel set <player> <level>
/magelevel set @s 50

# Add XP
/magelevel addxp <player> <amount>
/magelevel addxp @s 1000

# View specializations
/magelevel spec list

# Choose specialization
/magelevel spec choose <school>
/magelevel spec choose fire
```

### Debug Commands

#### `/mamdebug` - Debug Tools

```bash
# Toggle debug HUD
/mamdebug hud

# Show nearby mana nodes
/mamdebug nodes

# Show ley lines
/mamdebug leylines

# Dump player NBT
/mamdebug nbt <player>

# Reload configuration
/mamdebug reload

# Test spell
/mamdebug testspell <spell_id>

# Generate gemstone ore
/mamdebug ore <gemstone> <amount>
/mamdebug ore ruby 10
```
