# Mana and Magic

**Version:** 1.0.0 | **Minecraft:** 1.21.11 | **Last Updated:** January 6, 2026

---

## 🧙‍♂️ What is Mana and Magic?

Mana and Magic is a data-driven, extensible Minecraft magic mod for Fabric. It features:

- Deep spellcasting system (projectile, AoE, utility, ritual, synergy)
- 13 spell schools, 13 ritual categories, 15 gemstone types
- All spells, rituals, and gemstones defined in JSON (no hardcoded content)
- Customizable progression, worldgen, and item/ritual logic
- Modern, robust codebase (Java 21+, Fabric 0.18.4, MC 1.21.11+)

---

## ✨ Features

### Core Systems ✅

- **✅ Three-Pool Mana System:** Personal (250), Aura (500), Reserve (1000) with configurable regen rates
- **✅ Spell Casting:** 5 cast types (Projectile, AoE, Utility, Ritual, Synergy) with 65+ spell JSON files
- **✅ Ritual System:** 13 categories with 34+ ritual definitions and pattern validation
- **✅ Client-Server Networking:** Automatic mana synchronization with custom packets
- **✅ HUD Overlay:** Three-tier mana bars with health display and customizable settings
- **✅ Configuration:** Server properties and client JSON config with hot-reload support

### Content ✅

- **✅ 15 Gemstone Types:** 4 rarity tiers with school/ritual bindings
- **✅ 26 Magic Wands:** Novice and Master tier for all 13 spell schools
- **✅ 13 Spell Books:** One for each school with lore and mechanics
- **✅ 19 Gemstone Ores:** With deepslate variants and worldgen placement
- **✅ Loot Tables:** Fortune-compatible ore drops with Silk Touch support
- **✅ Worldgen:** Configured and placed features for all ore types

### Data-Driven Architecture ✅

- **Data-Driven:** All spells, rituals, gemstones, and worldgen are defined in JSON. Java is only for registration and custom logic.
- **Extensible:** Add new content by editing JSON and assets—no code required for most features.
- **Modern Fabric:** Built for MC 1.21.11+, Fabric 0.18.4, Java 21. Uses best practices for NBT, Codecs, and persistent state.
- **Progression:** Tiered gemstone and spell system, with customizable advancement and worldgen.
- **Synergy:** Dual-hand/catalyst spell synergy, ritual synergies, and gemstone-school bindings.

### Project Stats

- **56 Java Classes:** Core systems and game logic
- **151 JSON Data Files:** Spells (65+), Rituals (34+), Worldgen (30+), Loot Tables (19+)
- **26 Wands:** 2 tiers × 13 schools
- **15 Gemstones:** Epic (3), Rare (6), Uncommon (5), Common (1)

---

## 🔮 Spell Schools

| Symbol | School      | Color       | Hex Code  | Focus                   | Primary Gemstones             |
| :----: | ----------- | ----------- | --------- | ----------------------- | ----------------------------- |
|   🌀   | **Air**     | Light Gray  | `#C0C0C0` | Mobility & Speed        | Moonstone                     |
|   🔮   | **Arcane**  | Purple      | `#9966CC` | Utility & Manipulation  | Tanzanite                     |
|   🩸   | **Blood**   | Dark Red    | `#8B0000` | Sacrifice for Power     | Ruby, Carnelian               |
|   🌪️   | **Chaos**   | Magenta     | `#FF00FF` | Unpredictable Effects   | Tourmaline, Rhodonite         |
|   🌑   | **Dark**    | Dark Purple | `#2D1B4E` | DOT & Curses            | Sodalite, Hematite            |
|   🌍   | **Earth**   | Brown       | `#8B4513` | Defense & Stability     | Hematite, Jade, Peridot       |
|   🔥   | **Fire**    | Orange      | `#FF4500` | Damage & Destruction    | Ruby, Carnelian, Topaz        |
|   ❄️   | **Ice**     | Cyan        | `#00FFFF` | Control & Freezing      | Sapphire, Aquamarine, Apatite |
|   ✨   | **Light**   | Pale Yellow | `#FFF8DC` | Holy Power & Protection | Citrine, Topaz                |
|   🌿   | **Nature**  | Green       | `#228B22` | Growth & Life           | Jade, Peridot                 |
|   ⚡   | **Thunder** | Yellow      | `#FFD700` | Burst Damage & Energy   | Citrine, Topaz                |
|   🕳️   | **Void**    | Black       | `#000000` | Dimensional Magic       | Tanzanite, Hematite           |
|   💧   | **Water**   | Blue        | `#1E90FF` | Healing & Purification  | Apatite, Aquamarine           |

---

## 🌟 Ritual Categories

|  Symbol  | Category           | Color        | Hex Code  | Focus                       | Primary Gemstones              |
| :------: | ------------------ | ------------ | --------- | --------------------------- | ------------------------------ |
|    🔺    | **Ascension**      | Gold         | `#FFD700` | Transcendence & Empowerment | Citrine, Topaz                 |
|    ⭕    | **Circle**         | White        | `#FFFFFF` | Bounded Magic & Protection  | Moonstone                      |
|    🌌    | **Cosmic**         | Deep Purple  | `#4B0082` | Celestial Alignment         | Tanzanite, Sapphire, Moonstone |
| 🔥💧🌿⚡ | **Elemental**      | Rainbow      | `#FF6B6B` | Multi-Element Fusion        | Tourmaline, Ruby, Sapphire     |
|    ⛲    | **Fountain**       | Aqua         | `#00CED1` | Continuous Flow             | Aquamarine                     |
|    🌐    | **Planar**         | Silver       | `#C0C0C0` | Dimension Manipulation      | Tanzanite                      |
|    🔄    | **Reality**        | Prismatic    | `#E0E0E0` | World Alteration            | Tourmaline                     |
|    💫    | **Resurrection**   | Golden White | `#FFF9E3` | Life Restoration            | Rhodonite, Citrine             |
|    🗡️    | **Sacrifice**      | Crimson      | `#DC143C` | Power through Offering      | Ruby, Carnelian, Hematite      |
|    👻    | **Summoning**      | Dark Purple  | `#6A0DAD` | Entity Calling              | Sodalite, Tanzanite, Hematite  |
|    ⏰    | **Temporal**       | Bronze       | `#CD7F32` | Time Manipulation           | Moonstone                      |
|    🦋    | **Transformation** | Violet       | `#8F00FF` | Form Alteration             | Jade, Peridot, Tourmaline      |
|    🌀    | **Vortex**         | Storm Gray   | `#708090` | Spiraling Force             | Apatite, Sodalite              |

---

## 💎 Gemstone Compendium

### Epic Tier

| Gemstone      | Color     | Hex       | Shape    | Affinity | Schools      | Rituals                   |
| ------------- | --------- | --------- | -------- | -------- | ------------ | ------------------------- |
| **Ruby**      | Deep Red  | `#E63946` | Round    | Fire     | Fire, Blood  | Elemental, Sacrifice      |
| **Sapphire**  | Deep Blue | `#2952A3` | Round    | Ice      | Ice          | Elemental, Cosmic         |
| **Tanzanite** | Purple    | `#6B4B9E` | Princess | Void     | Void, Arcane | Planar, Cosmic, Summoning |

### Rare Tier

| Gemstone       | Color        | Hex       | Shape   | Affinity | Schools              | Rituals                            |
| -------------- | ------------ | --------- | ------- | -------- | -------------------- | ---------------------------------- |
| **Apatite**    | Cyan Blue    | `#2DD4DB` | Round   | Water    | Water, Ice           | Vortex, Fountain                   |
| **Aquamarine** | Light Blue   | `#7DD3E8` | Diamond | Water    | Water, Ice           | Fountain                           |
| **Moonstone**  | Pearly White | `#E8E5E0` | Oval    | Lunar    | Air                  | Circle, Cosmic, Temporal           |
| **Rhodonite**  | Rose Pink    | `#D66B88` | Round   | Healing  | Chaos                | Resurrection                       |
| **Topaz**      | Amber Orange | `#D98736` | Oval    | Solar    | Light, Thunder, Fire | Ascension                          |
| **Tourmaline** | Forest Green | `#3A7C59` | Round   | Balance  | Chaos                | Elemental, Reality, Transformation |

### Uncommon Tier

| Gemstone      | Color         | Hex       | Shape   | Affinity | Schools        | Rituals                 |
| ------------- | ------------- | --------- | ------- | -------- | -------------- | ----------------------- |
| **Carnelian** | Orange Red    | `#E86938` | Round   | Fire     | Fire, Blood    | Sacrifice               |
| **Citrine**   | Golden Yellow | `#F4B942` | Octagon | Light    | Light, Thunder | Ascension, Resurrection |
| **Jade**      | Medium Green  | `#5FA777` | Round   | Nature   | Nature, Earth  | Transformation          |
| **Peridot**   | Lime Green    | `#A4D65E` | Round   | Nature   | Nature, Earth  | Transformation          |
| **Sodalite**  | Navy Blue     | `#3D5A9C` | Round   | Mind     | Dark           | Summoning, Vortex       |

### Common Tier

| Gemstone     | Color         | Hex       | Shape | Affinity | Schools           | Rituals              |
| ------------ | ------------- | --------- | ----- | -------- | ----------------- | -------------------- |
| **Hematite** | Metallic Gray | `#5A5A5A` | Round | Earth    | Earth, Dark, Void | Sacrifice, Summoning |

---

## 🔗 Gemstone-to-School Mapping

### By Spell School

- **Air**: Moonstone
- **Arcane**: Tanzanite
- **Blood**: Ruby, Carnelian
- **Chaos**: Tourmaline, Rhodonite
- **Dark**: Sodalite, Hematite
- **Earth**: Hematite, Jade, Peridot
- **Fire**: Ruby, Carnelian, Topaz
- **Ice**: Sapphire, Aquamarine, Apatite
- **Light**: Citrine, Topaz
- **Nature**: Jade, Peridot
- **Thunder**: Citrine, Topaz
- **Void**: Tanzanite, Hematite
- **Water**: Apatite, Aquamarine

---

## 🔗 Gemstone-to-Ritual Mapping

### By Ritual Category

- **Ascension**: Citrine, Topaz
- **Circle**: Moonstone
- **Cosmic**: Tanzanite, Sapphire, Moonstone
- **Elemental**: Tourmaline, Ruby, Sapphire
- **Fountain**: Aquamarine
- **Planar**: Tanzanite
- **Reality**: Tourmaline
- **Resurrection**: Rhodonite, Citrine
- **Sacrifice**: Ruby, Carnelian, Hematite
- **Summoning**: Sodalite, Tanzanite, Hematite
- **Temporal**: Moonstone
- **Transformation**: Jade, Peridot, Tourmaline
- **Vortex**: Apatite, Sodalite

---

# 🔮 Mana & Magic (MAM) - Complete Magic System for Minecraft

[![Minecraft](https://img.shields.io/badge/Minecraft-1.21.11-brightgreen.svg)](https://fabricmc.net/)
[![Fabric API](https://img.shields.io/badge/Fabric%20API-0.140.2-blue.svg)](https://fabricmc.net/use/)
[![Fabric Loader](https://img.shields.io/badge/Fabric%20Loader-0.18.4-blue.svg)](https://fabricmc.net/use/)
[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://adoptium.net/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

**Version:** 1.0.0
**Mod ID:** `mam`
**Author:** Mosberg
**Homepage:** [https://mosberg.github.io/mam](https://mosberg.github.io/mam)

---

## 📖 Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Quick Start](#quick-start)
- [System Architecture](#system-architecture)
- [Magic Systems](#magic-systems)
- [Configuration](#configuration)
- [API Usage](#api-usage)
- [File Structure](#file-structure)
- [Development](#development)
- [Contributing](#contributing)
- [License](#license)

---

## 🌟 Overview

**Mana & Magic (MAM)** is a comprehensive, data-driven magic mod for Minecraft Fabric 1.21.11+ that combines three integrated systems:

### Core Components

1. **Three-Tier Mana Pool System** - Independent mana pools with cascading consumption
2. **13 Spell Schools** - Complete elemental and arcane magic framework
3. **13 Ritual Categories** - Complex multi-block ritual structures
4. **15 Gemstone Types** - Tiered magical catalysts with worldgen
5. **50+ Spells** - Projectile, AoE, Utility, Channel, and Ray casting
6. **Advanced HUD** - Real-time mana, health, cooldown, and progression display
7. **World Generation** - Gemstone ores, mana nodes, ley lines, altar ruins

### Design Philosophy

- **Data-Driven:** All content defined in JSON - no hardcoded spells/rituals
- **Extensible:** Modpack-friendly with API for addon development
- **Performance:** Thread-safe, deterministic tick-based systems
- **Multiplayer:** Full dedicated server support with client sync
- **Modern:** Java 21, Fabric 0.18.4, best practices throughout

---

## ✨ Features

### Mana System

- **🎯 Three Independent Pools:**
  - Primary: 250 max, 1.0/sec regeneration
  - Secondary: 500 max, 0.75/sec regeneration
  - Tertiary: 1000 max, 0.5/sec regeneration
- **⚡ Cascading Consumption:** Primary → Secondary → Tertiary
- **💾 Persistent Storage:** UUID-based NBT serialization
- **🔄 Multiplayer-Safe:** Thread-safe, tick-based synchronization
- **📊 HUD Overlay:** Customizable position, scale, transparency

### Spell System

- **13 Spell Schools:** Air, Arcane, Blood, Chaos, Dark, Earth, Fire, Ice, Light, Nature, Thunder, Void, Water
- **5 Cast Types:** Projectile, AoE, Utility, Channel, Ray
- **Spell Progression:** 50+ spells across 5 difficulty tiers
- **Dual-Hand Synergy:** Combine spells for enhanced effects
- **Cooldown System:** Per-spell cooldown tracking with HUD display
- **Enchantments:** Capacity, Efficiency, Potency for spell enhancement

### Ritual System

- **13 Categories:** Ascension, Circle, Cosmic, Elemental, Fountain, Planar, Reality, Resurrection, Sacrifice, Summoning, Temporal, Transformation, Vortex
- **Multi-Block Structures:** Complex patterns with validation
- **Environmental Requirements:** Time, weather, biome, surface checks
- **Ritual Items:** Specific gemstone/item requirements
- **Particle Effects:** Custom VFX for active rituals

### Gemstone System

- **15 Gemstone Types:**
  - Epic (3): Ruby, Sapphire, Tanzanite
  - Rare (6): Apatite, Aquamarine, Moonstone, Rhodonite, Topaz, Tourmaline
  - Uncommon (5): Carnelian, Citrine, Jade, Peridot, Sodalite
  - Common (1): Hematite
- **School Affinity:** Each gemstone binds to 1-3 spell schools
- **Ritual Affinity:** Specific gemstones enable ritual categories
- **Worldgen:** Configurable ore generation with biome distribution

### World Generation

- **Gemstone Ores:** 15 ore types with rarity-based distribution
- **Mana Nodes:** Floating mana-infused crystals
- **Ley Lines:** Underground streams of arcane energy
- **Altar Ruins:** Ancient structures with loot and lore
- **Magic Trees:** Rare trees that grow mana-infused leaves
- **Mana Springs:** Liquid mana pools that restore player mana

### Progression System

- **Mage Levels:** 100 levels with XP from spell casting
- **Specializations:** Choose school specializations for bonuses
- **Advancements:** Custom advancement tree for magic progression
- **Skill Trees:** Unlock passive bonuses and spell upgrades
- **Grimoire:** In-game documentation and spell discovery

### UI/UX Features

- **Mage Menu:** Spell selection, progression tracking
- **Mana HUD:** Three-tier mana bars with health integration
- **Cooldown Overlay:** Circular cooldown indicators
- **Debug HUD:** F3-style debug info for mana/spell states
- **Tooltips:** Rich tooltips with mana costs, school info

---

## 🚀 Quick Start

### Installation (Players)

1. **Download Prerequisites:**

   - [Fabric Loader 0.18.4+](https://fabricmc.net/use/)
   - [Fabric API 0.140.2+](https://modrinth.com/mod/fabric-api)

2. **Install Mod:**

   ```bash
   # Download mam-1.0.0.jar
   # Place in .minecraft/mods/ folder
   # Launch Minecraft with Fabric profile
   ```

3. **In-Game:**
   - Press `K` to open Mage Menu
   - Press `R` to cast selected spell
   - Press `M` to toggle mana overlay

### Building from Source

```bash
# Clone repository
git clone https://github.com/mosberg/mam.git
cd mam

# Build mod
./gradlew build

# Output: build/libs/mam-1.0.0.jar
```

### Development Setup

```bash
# Generate IDE sources
./gradlew genSources

# Launch development client
./gradlew runClient

# Launch development server
./gradlew runServer

# Run tests
./gradlew test

# Clean build artifacts
./gradlew clean
```

---

## 🏛️ System Architecture

### Mana Pool Design

```
┌─────────────────────────────────────────┐
│      Three-Tier Cascading System       │
├─────────────────────────────────────────┤
│  Primary Pool (250)   [█████████░░] 90% │
│    Regen: 1.0/sec     Priority: 1       │
│                                          │
│  Secondary Pool (500) [███████░░░] 70%  │
│    Regen: 0.75/sec    Priority: 2       │
│                                          │
│  Tertiary Pool (1000) [████░░░░░░] 40%  │
│    Regen: 0.5/sec     Priority: 3       │
└─────────────────────────────────────────┘

Consumption: Primary → Secondary → Tertiary
Restoration: Primary → Secondary → Tertiary
```

### Data Flow

```
┌──────────────┐     ┌──────────────┐     ┌──────────────┐
│ JSON Data    │────▶│ Java Registry│────▶│ Game Runtime │
│ (spells/)    │     │ (SpellReg)   │     │ (SpellCast)  │
└──────────────┘     └──────────────┘     └──────────────┘
       │                     │                     │
       │                     ▼                     ▼
       │            ┌──────────────┐     ┌──────────────┐
       │            │ Validation   │     │ Client Sync  │
       │            │ & Codecs     │     │ (Packets)    │
       │            └──────────────┘     └──────────────┘
       ▼
┌──────────────┐
│ Live Reload  │
│ (/reload)    │
└──────────────┘
```

---

## 🔮 Magic Systems

### Spell Schools

| School      | Symbol | Color       | Hex       | Focus                   | Gemstones                     |
| ----------- | ------ | ----------- | --------- | ----------------------- | ----------------------------- |
| **Air**     | 🌀     | Light Gray  | `#C0C0C0` | Mobility & Speed        | Moonstone                     |
| **Arcane**  | 🔮     | Purple      | `#9966CC` | Utility & Manipulation  | Tanzanite                     |
| **Blood**   | 🩸     | Dark Red    | `#8B0000` | Sacrifice for Power     | Ruby, Carnelian               |
| **Chaos**   | 🌪️     | Magenta     | `#FF00FF` | Unpredictable Effects   | Tourmaline, Rhodonite         |
| **Dark**    | 🌑     | Dark Purple | `#2D1B4E` | DOT & Curses            | Sodalite, Hematite            |
| **Earth**   | 🌍     | Brown       | `#8B4513` | Defense & Stability     | Hematite, Jade, Peridot       |
| **Fire**    | 🔥     | Orange      | `#FF4500` | Damage & Destruction    | Ruby, Carnelian, Topaz        |
| **Ice**     | ❄️     | Cyan        | `#00FFFF` | Control & Freezing      | Sapphire, Aquamarine, Apatite |
| **Light**   | ✨     | Pale Yellow | `#FFF8DC` | Holy Power & Protection | Citrine, Topaz                |
| **Nature**  | 🌿     | Green       | `#228B22` | Growth & Life           | Jade, Peridot                 |
| **Thunder** | ⚡     | Yellow      | `#FFD700` | Burst Damage & Energy   | Citrine, Topaz                |
| **Void**    | 🕳️     | Black       | `#000000` | Dimensional Magic       | Tanzanite, Hematite           |
| **Water**   | 💧     | Blue        | `#1E90FF` | Healing & Purification  | Apatite, Aquamarine           |

### Spell Categories by School

#### Fire School (🔥)

- **fire_strike** - Basic fire bolt (10 damage, 20 mana)
- **fireball** - Explosive projectile (25 damage, 50 mana, 3m AoE)
- **inferno_nova** - Area blast (40 damage, 100 mana, 8m AoE)
- **phoenix_rise** - Resurrection on death (150 mana, 300s cooldown)

#### Ice School (❄️)

- **frost_bolt** - Slow projectile (15 damage, 25 mana, Slowness II)
- **ice_comet** - Heavy impact (35 damage, 75 mana, Freeze)
- **blizzard_storm** - AoE storm (50 damage, 120 mana, 10m AoE)

#### Arcane School (🔮)

- **arcane_missile** - Homing projectile (18 damage, 30 mana)
- **spell_weave** - Spell combo enhancer (50 mana utility)
- **arcane_implosion** - Gravity well (45 damage, 90 mana, Pull)

#### Light School (✨)

- **heal** - Self heal (0 damage, 40 mana, +6 hearts)
- **holy_blast** - Radiant damage (30 damage, 60 mana)
- **celestial_beam** - Piercing ray (55 damage, 110 mana)
- **radiant_judgment** - Area smite (70 damage, 150 mana)

#### Nature School (🌿)

- **nature_strike** - Thorn bolt (12 damage, 18 mana, Poison I)
- **verdant_surge** - Growth wave (22 damage, 45 mana, Regen)
- **nature_wrath** - Entanglement (38 damage, 80 mana, Roots)

_(See FEATURES_FUNCTIONS.md for complete spell list)_

### Ritual Categories

| Category           | Symbol   | Color        | Focus         | Primary Gemstones              |
| ------------------ | -------- | ------------ | ------------- | ------------------------------ |
| **Ascension**      | 🔺       | Gold         | Transcendence | Citrine, Topaz                 |
| **Circle**         | ⭕       | White        | Protection    | Moonstone                      |
| **Cosmic**         | 🌌       | Deep Purple  | Celestial     | Tanzanite, Sapphire, Moonstone |
| **Elemental**      | 🔥💧🌿⚡ | Rainbow      | Fusion        | Tourmaline, Ruby, Sapphire     |
| **Fountain**       | ⛲       | Aqua         | Flow          | Aquamarine                     |
| **Planar**         | 🌐       | Silver       | Dimensions    | Tanzanite                      |
| **Reality**        | 🔄       | Prismatic    | Alteration    | Tourmaline                     |
| **Resurrection**   | 💫       | Golden White | Life          | Rhodonite, Citrine             |
| **Sacrifice**      | 🗡️       | Crimson      | Offering      | Ruby, Carnelian, Hematite      |
| **Summoning**      | 👻       | Dark Purple  | Entities      | Sodalite, Tanzanite, Hematite  |
| **Temporal**       | ⏰       | Bronze       | Time          | Moonstone                      |
| **Transformation** | 🦋       | Violet       | Form          | Jade, Peridot, Tourmaline      |
| **Vortex**         | 🌀       | Storm Gray   | Force         | Apatite, Sodalite              |

### Gemstone Details

#### Epic Tier (Levels 51+)

**Ruby (🔴 Deep Red `#E63946`)**

- Schools: Fire, Blood
- Rituals: Elemental, Sacrifice
- Shape: Round
- Affinity: Fire
- Ore Rarity: 1 vein per 8 chunks, Y: 5-20

**Sapphire (🔵 Deep Blue `#2952A3`)**

- Schools: Ice
- Rituals: Elemental, Cosmic
- Shape: Round
- Affinity: Ice
- Ore Rarity: 1 vein per 8 chunks, Y: 5-20

**Tanzanite (🟣 Purple `#6B4B9E`)**

- Schools: Void, Arcane
- Rituals: Planar, Cosmic, Summoning
- Shape: Princess
- Affinity: Void
- Ore Rarity: 1 vein per 10 chunks, Y: 0-15

#### Rare Tier (Levels 31-50)

**Moonstone (⚪ Pearly White `#E8E5E0`)**

- Schools: Air
- Rituals: Circle, Cosmic, Temporal
- Shape: Oval
- Affinity: Lunar
- Ore Rarity: 1 vein per 6 chunks, Y: 40-80

**Topaz (🟠 Amber Orange `#D98736`)**

- Schools: Light, Thunder, Fire
- Rituals: Ascension
- Shape: Oval
- Affinity: Solar
- Ore Rarity: 1 vein per 6 chunks, Y: 30-70

_(See complete gemstone tables in FEATURES_FUNCTIONS.md)_

---

## ⚙️ Configuration

### Config File Location

```
.minecraft/config/mana.json
```

### Default Configuration

```json
{
  "overlay": {
    "enabled": true,
    "scale": 1.0,
    "xOffset": 0,
    "yOffset": 0,
    "transparency": 1.0
  },
  "magic": {
    "spell": {
      "manaCost": {
        "multiplier": 1.0
      }
    },
    "ritual": {
      "difficulty": {
        "multiplier": 1.0
      }
    }
  },
  "render": {
    "hud": {
      "manaBar": {
        "enabled": true
      },
      "cooldownOverlay": {
        "enabled": true
      },
      "debugInfo": {
        "enabled": false
      }
    }
  },
  "worldgen": {
    "gemstoneOres": {
      "enabled": true,
      "densityMultiplier": 1.0
    },
    "manaNodes": {
      "enabled": true,
      "spawnRate": 0.05
    },
    "leyLines": {
      "enabled": true,
      "maxDistance": 512
    }
  }
}
```

### Configuration Options

#### Overlay Settings

| Option                 | Type    | Range    | Default | Description                         |
| ---------------------- | ------- | -------- | ------- | ----------------------------------- |
| `overlay.enabled`      | boolean | -        | `true`  | Master HUD toggle                   |
| `overlay.scale`        | double  | 0.5-2.0  | `1.0`   | Visual scale (1.0 = 100%)           |
| `overlay.xOffset`      | integer | -∞ to +∞ | `0`     | Horizontal offset (+ right, - left) |
| `overlay.yOffset`      | integer | -∞ to +∞ | `0`     | Vertical offset (+ down, - up)      |
| `overlay.transparency` | double  | 0.0-1.0  | `1.0`   | Opacity (0.0 invisible, 1.0 opaque) |

#### Gameplay Balance

| Option                               | Type   | Range | Default | Description                          |
| ------------------------------------ | ------ | ----- | ------- | ------------------------------------ |
| `magic.spell.manaCost.multiplier`    | double | 0.0+  | `1.0`   | Global spell cost (2.0 = double)     |
| `magic.ritual.difficulty.multiplier` | double | 0.0+  | `1.0`   | Ritual difficulty (1.5 = 50% harder) |

#### Render Settings

| Option                               | Type    | Default | Description          |
| ------------------------------------ | ------- | ------- | -------------------- |
| `render.hud.manaBar.enabled`         | boolean | `true`  | Show mana bars       |
| `render.hud.cooldownOverlay.enabled` | boolean | `true`  | Show spell cooldowns |
| `render.hud.debugInfo.enabled`       | boolean | `false` | F3-style debug info  |

#### World Generation

| Option                                    | Type    | Range    | Default | Description                 |
| ----------------------------------------- | ------- | -------- | ------- | --------------------------- |
| `worldgen.gemstoneOres.enabled`           | boolean | -        | `true`  | Generate gemstone ores      |
| `worldgen.gemstoneOres.densityMultiplier` | double  | 0.0-5.0  | `1.0`   | Ore spawn density           |
| `worldgen.manaNodes.enabled`              | boolean | -        | `true`  | Generate mana nodes         |
| `worldgen.manaNodes.spawnRate`            | double  | 0.0-1.0  | `0.05`  | Node spawn chance per chunk |
| `worldgen.leyLines.enabled`               | boolean | -        | `true`  | Generate ley lines          |
| `worldgen.leyLines.maxDistance`           | integer | 128-2048 | `512`   | Max distance between nodes  |

### Example Configurations

**Performance Mode (Minimal HUD):**

```json
{
  "overlay": {
    "scale": 0.75,
    "transparency": 0.6
  },
  "render": {
    "hud": {
      "cooldownOverlay": { "enabled": false }
    }
  }
}
```

**Hardcore Balance:**

```json
{
  "magic": {
    "spell": {
      "manaCost": { "multiplier": 2.0 }
    },
    "ritual": {
      "difficulty": { "multiplier": 1.5 }
    }
  }
}
```

**Abundant Worldgen:**

```json
{
  "worldgen": {
    "gemstoneOres": {
      "densityMultiplier": 2.5
    },
    "manaNodes": {
      "spawnRate": 0.1
    }
  }
}
```

---

## 🛠️ API Usage

### Maven Dependency

```gradle
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    modImplementation "com.github.Mosberg:mam:1.0.0"
}
```

### Basic Operations

#### Accessing Mana

```java
import dk.mosberg.MAM;
import dk.mosberg.mana.ManaComponent;
import dk.mosberg.mana.ManaPool;
import net.minecraft.server.network.ServerPlayerEntity;

// Get player's mana component
ManaComponent component = MAM.getManaComponent(player);
if (component != null) {
    ManaPool pool = component.getManaPool();

    // Get values
    double totalMana = pool.getTotalMana();
    double primaryMana = pool.getPrimaryMana();
    double maxMana = pool.getTotalMaxMana();

    // Get percentages
    double primaryPercent = pool.getPrimaryPercent(); // 0.0 to 1.0
}
```

#### Consuming Mana

```java
import dk.mosberg.util.ManaPoolHelper;

// Method 1: Direct consumption
ManaPool pool = component.getManaPool();
if (pool.consumeMana(50.0)) {
    player.sendMessage(Text.literal("Spell cast!"));
} else {
    player.sendMessage(Text.literal("Not enough mana!"));
}

// Method 2: Using helper (recommended)
if (ManaPoolHelper.tryConsumeMana(player, 50.0)) {
    castFireball(player);
}
```

#### Restoring Mana

```java
// Restore 25 mana
pool.restoreMana(25.0);

// Restore specific pool to maximum
pool.restorePool(ManaPool.ManaPoolType.PRIMARY);

// Restore all pools
pool.restoreAll();

// Using helper
ManaPoolHelper.restoreMana(player, 25.0);
```

#### Checking Availability

```java
// Check if player has enough mana
if (ManaPoolHelper.hasEnoughMana(player, 100.0)) {
    // Can cast expensive spell
}

// Get formatted display
String display = ManaPoolHelper.formatMana(player, ManaPool.ManaPoolType.PRIMARY);
// Output: "120 / 250"
```

### Advanced Integration

#### Custom Spell System

```java
import dk.mosberg.spell.Spell;
import dk.mosberg.spell.SpellRegistry;
import dk.mosberg.config.MAMConfig;

public class SpellSystem {

    public static boolean castSpell(ServerPlayerEntity player, String spellId) {
        Spell spell = SpellRegistry.get(spellId);
        if (spell == null) return false;

        // Apply cost multiplier from config
        double cost = spell.getManaCost() * MAMConfig.getSpellManaCostMultiplier();

        // Check and consume mana
        if (ManaPoolHelper.tryConsumeMana(player, cost)) {
            // Execute spell
            spell.cast(player);

            // Apply cooldown
            SpellCooldownTracker.applyCooldown(player, spellId, spell.getCooldown());

            return true;
        }

        player.sendMessage(
            Text.literal("Not enough mana!").formatted(Formatting.RED)
        );
        return false;
    }
}
```

#### Custom Mana Item

```java
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class ManaPotion extends Item {
    private final double manaRestore;

    public ManaPotion(Settings settings, double manaRestore) {
        super(settings);
        this.manaRestore = manaRestore;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient && user instanceof ServerPlayerEntity serverPlayer) {
            ManaPoolHelper.restoreMana(serverPlayer, manaRestore);

            serverPlayer.sendMessage(
                Text.literal("Restored " + manaRestore + " mana!")
                    .formatted(Formatting.AQUA)
            );

            // Consume item
            ItemStack stack = user.getStackInHand(hand);
            if (!user.getAbilities().creativeMode) {
                stack.decrement(1);
            }

            return TypedActionResult.success(stack);
        }

        return TypedActionResult.pass(user.getStackInHand(hand));
    }
}
```

#### Custom Ritual

```java
import dk.mosberg.ritual.Ritual;
import dk.mosberg.ritual.RitualPattern;

public class CustomRitual implements Ritual {

    @Override
    public String getId() {
        return "mam:custom_ritual";
    }

    @Override
    public boolean canActivate(World world, BlockPos pos, ServerPlayerEntity player) {
        // Check pattern structure
        if (!RitualPattern.matches(world, pos, getPattern())) {
            return false;
        }

        // Check player has required items
        if (!hasRequiredItems(player)) {
            return false;
        }

        // Check environmental requirements
        if (!checkEnvironment(world, pos)) {
            return false;
        }

        return true;
    }

    @Override
    public void activate(World world, BlockPos pos, ServerPlayerEntity player) {
        // Consume mana
        ManaPoolHelper.tryConsumeMana(player, getManaCost());

        // Consume ritual items
        consumeItems(player);

        // Execute ritual effect
        spawnParticles(world, pos);
        playSound(world, pos);
        applyEffect(world, pos, player);
    }
}
```

### Event Hooks

```java
import dk.mosberg.event.ManaEvents;

// Listen for mana changes
ManaEvents.MANA_CHANGED.register((player, oldValue, newValue) -> {
    if (newValue < oldValue) {
        // Mana was consumed
        MAM.LOGGER.info("{} consumed {} mana", player.getName(), oldValue - newValue);
    }
});

// Listen for spell casts
SpellEvents.SPELL_CAST.register((player, spell, success) -> {
    if (success) {
        // Grant XP, trigger achievements, etc.
        ProgressionTracker.addSpellXP(player, spell.getSchool(), 10);
    }
});

// Listen for ritual completion
RitualEvents.RITUAL_COMPLETE.register((world, pos, player, ritual) -> {
    // Award advancement
    AdvancementTriggers.trigger(player, "complete_ritual_" + ritual.getId());
});
```

---

## 📁 File Structure

```
mam/
├── gradle/
│   └── wrapper/                         # Gradle wrapper
├── src/
│   ├── main/
│   │   ├── java/dk/mosberg/
│   │   │   ├── MAM.java                 # Main mod class
│   │   │   ├── config/
│   │   │   │   ├── MAMConfig.java       # JSON config manager
│   │   │   │   └── ConfigHelper.java    # Environment vars
│   │   │   ├── mana/
│   │   │   │   ├── ManaPool.java        # Three-pool system
│   │   │   │   ├── ManaComponent.java   # Player attachment
│   │   │   │   ├── ManaComponents.java  # Component registry
│   │   │   │   └── ManaComponentProvider.java
│   │   │   ├── spell/
│   │   │   │   ├── Spell.java           # Spell interface
│   │   │   │   ├── SpellRegistry.java   # Spell registration
│   │   │   │   ├── SpellCaster.java     # Cast logic
│   │   │   │   ├── school/
│   │   │   │   │   ├── FireSpells.java
│   │   │   │   │   ├── IceSpells.java
│   │   │   │   │   └── ... (13 schools)
│   │   │   │   └── type/
│   │   │   │       ├── ProjectileSpell.java
│   │   │   │       ├── AoESpell.java
│   │   │   │       └── UtilitySpell.java
│   │   │   ├── ritual/
│   │   │   │   ├── Ritual.java          # Ritual interface
│   │   │   │   ├── RitualRegistry.java
│   │   │   │   ├── RitualPattern.java   # Pattern validation
│   │   │   │   └── category/
│   │   │   │       └── ... (13 categories)
│   │   │   ├── gemstone/
│   │   │   │   ├── Gemstone.java
│   │   │   │   ├── GemstoneRegistry.java
│   │   │   │   └── GemstoneItem.java
│   │   │   ├── item/
│   │   │   │   ├── MagicWand.java
│   │   │   │   ├── MagicStaff.java
│   │   │   │   ├── CatalystItem.java
│   │   │   │   └── ManaItem.java
│   │   │   ├── block/
│   │   │   │   ├── ArcaneAltar.java
│   │   │   │   ├── ManaNode.java
│   │   │   │   ├── ManaSpring.java
│   │   │   │   └── GemstoneOre.java
│   │   │   ├── entity/
│   │   │   │   ├── projectile/
│   │   │   │   │   ├── FireballEntity.java
│   │   │   │   │   ├── IceSpearEntity.java
│   │   │   │   │   └── MagicBolt.java
│   │   │   │   └── summon/
│   │   │   │       ├── ArcaneGolem.java
│   │   │   │       ├── ElementalSpirit.java
│   │   │   │       └── ManaWisp.java
│   │   │   ├── enchantment/
│   │   │   │   ├── CapacityEnchantment.java
│   │   │   │   ├── EfficiencyEnchantment.java
│   │   │   │   └── PotencyEnchantment.java
│   │   │   ├── progression/
│   │   │   │   ├── MageLevel.java
│   │   │   │   ├── MageSpecialization.java
│   │   │   │   └── ProgressionTracker.java
│   │   │   ├── worldgen/
│   │   │   │   ├── OreFeatures.java
│   │   │   │   ├── ManaNodeFeature.java
│   │   │   │   ├── LeyLineFeature.java
│   │   │   │   └── AltarRuinsFeature.java
│   │   │   ├── network/
│   │   │   │   ├── packet/
│   │   │   │   │   ├── CastSpellPayload.java
│   │   │   │   │   └── SyncManaPayload.java
│   │   │   │   └── PacketHandler.java
│   │   │   ├── command/
│   │   │   │   ├── ManaCommand.java
│   │   │   │   └── DebugCommand.java
│   │   │   ├── event/
│   │   │   │   ├── ManaEvents.java
│   │   │   │   ├── SpellEvents.java
│   │   │   │   └── RitualEvents.java
│   │   │   └── util/
│   │   │       ├── ManaPoolHelper.java
│   │   │       ├── ParticleHelper.java
│   │   │       ├── MathUtil.java
│   │   │       └── NbtUtil.java
│   │   └── resources/
│   │       ├── assets/mam/
│   │       │   ├── blockstates/
│   │       │   │   ├── gemstone_ore.json
│   │       │   │   ├── arcane_altar.json
│   │       │   │   └── mana_node.json
│   │       │   ├── models/
│   │       │   │   ├── block/
│   │       │   │   │   ├── gemstone_ore.json
│   │       │   │   │   └── ... (structures)
│   │       │   │   └── item/
│   │       │   │       ├── gemstones/
│   │       │   │       │   └── *.json (15 files)
│   │       │   │       └── wands/
│   │       │   │           └── *.json
│   │       │   ├── textures/
│   │       │   │   ├── block/
│   │       │   │   │   ├── gemstone_ore.png
│   │       │   │   │   └── ... (blocks)
│   │       │   │   ├── item/
│   │       │   │   │   ├── gemstones/
│   │       │   │   │   │   └── *.png (15 files)
│   │       │   │   │   └── wands/
│   │       │   │   │       └── *.png
│   │       │   │   ├── gui/
│   │       │   │   │   ├── mana_bar.png
│   │       │   │   │   ├── spell_menu.png
│   │       │   │   │   └── sprites/container/slot/
│   │       │   │   │       └── gemstone_slots/ (15 files)
│   │       │   │   └── entity/
│   │       │   │       └── ... (entity textures)
│   │       │   ├── lang/
│   │       │   │   └── en_us.json
│   │       │   └── icon.png
│   │       ├── data/mam/
│   │       │   ├── loot_table/
│   │       │   │   └── blocks/
│   │       │   │       └── gemstone_ore.json
│   │       │   ├── recipe/
│   │       │   │   └── gemstone_*.json
│   │       │   ├── rituals/
│   │       │   │   ├── ascension/
│   │       │   │   │   └── apotheosis_ritual.json
│   │       │   │   ├── cosmic/
│   │       │   │   │   └── cosmic_alignment.json
│   │       │   │   └── ... (13 categories)
│   │       │   ├── spells/
│   │       │   │   ├── fire/
│   │       │   │   │   ├── fire_strike.json
│   │       │   │   │   ├── fireball.json
│   │       │   │   │   └── ... (4+ spells)
│   │       │   │   ├── ice/
│   │       │   │   │   └── ... (3 spells)
│   │       │   │   └── ... (13 schools)
│   │       │   ├── tags/
│   │       │   │   ├── block/
│   │       │   │   │   └── gemstone_ores.json
│   │       │   │   └── item/
│   │       │   │       ├── gemstones.json
│   │       │   │       └── catalysts.json
│   │       │   ├── worldgen/
│   │       │   │   ├── configured_feature/
│   │       │   │   │   └── ore_gemstone.json
│   │       │   │   └── placed_feature/
│   │       │   │       └── ore_gemstone.json
│   │       │   └── advancement/
│   │       │       └── magic/
│   │       │           └── root.json
│   │       └── fabric.mod.json
│   └── client/
│       └── java/dk/mosberg/client/
│           ├── ManaClient.java           # Client entry point
│           ├── overlay/
│           │   ├── ManaHudOverlay.java   # Main HUD
│           │   ├── HealthBarHelper.java
│           │   ├── StatusIconHelper.java
│           │   ├── CooldownOverlay.java
│           │   └── DebugHudOverlay.java
│           ├── renderer/
│           │   ├── OverlayRenderer.java  # Core rendering
│           │   ├── RenderHelper.java
│           │   ├── ColorHelper.java
│           │   ├── TextHelper.java
│           │   ├── DrawHelper.java
│           │   └── ScreenHelper.java
│           ├── screen/
│           │   ├── MageMenuScreen.java
│           │   └── ProgressionMenuScreen.java
│           ├── particle/
│           │   ├── ManaParticle.java
│           │   └── ElementalParticle.java
│           └── keybind/
│               └── KeybindHandler.java
├── docs/
│   ├── csv/                              # Canonical data tables
│   │   ├── gemstones.csv
│   │   ├── spells.csv
│   │   └── rituals.csv
│   ├── json/                             # Reference schemas
│   │   ├── spell_schema.json
│   │   └── ritual_schema.json
│   └── md/
│       └── MAGIC_SYSTEM_DOCUMENTATION.md
├── .github/
│   ├── workflows/
│   │   └── build.yml                     # CI/CD
│   └── copilot-instructions.md           # AI guidelines
├── gradle.properties                     # Build configuration
├── build.gradle                          # Gradle build script
├── settings.gradle
├── README.md                             # This file
├── FEATURES_FUNCTIONS.md                 # Detailed features
├── RESOURCES.md                          # Dependencies & tools
├── JAVA_JSON.md                          # Technical reference
└── LICENSE                               # MIT License
```

---

## 💻 Development

### Prerequisites

- **Java 21+:** [Adoptium OpenJDK](https://adoptium.net/)
- **Git:** Version control
- **IDE:** IntelliJ IDEA (recommended), VS Code, or Eclipse

### Setup Instructions

```bash
# 1. Clone repository
git clone https://github.com/mosberg/mam.git
cd mam

# 2. Generate IDE files
./gradlew genSources

# 3. Import into IDE
# IntelliJ: File > Open > select build.gradle
# VS Code: Open folder, install Java extension pack
```

### Build Commands

```bash
# Clean build artifacts
./gradlew clean

# Compile code
./gradlew classes

# Build JAR
./gradlew build
# Output: build/libs/mam-1.0.0.jar

# Run tests
./gradlew test

# Check code style
./gradlew checkstyleMain

# Generate JavaDoc
./gradlew javadoc
# Output: build/docs/javadoc/
```

### Testing

```bash
# Run all tests
./gradlew test

# Run specific test
./gradlew test --tests ManaPoolTest

# Run with coverage
./gradlew test jacocoTestReport
# Output: build/reports/jacoco/test/html/index.html

# Integration tests (requires Minecraft)
./gradlew runGametest
```

### Debugging

```bash
# Launch client with remote debugging
./gradlew runClient --debug-jvm

# Launch server with remote debugging
./gradlew runServer --debug-jvm

# Connect IDE debugger to localhost:5005
```

### Performance Profiling

```bash
# Enable JFR profiling
./gradlew runClient -Dfabric.development=true \
  -XX:StartFlightRecording=filename=recording.jfr

# Analyze with JDK Mission Control
jmc recording.jfr
```

### Code Style

```java
// Naming Conventions
public class ManaPool {}                  // PascalCase for classes
public void consumeMana() {}              // camelCase for methods
public static final int MAX_MANA = 1000;  // UPPER_SNAKE_CASE for constants
private double primaryMana;               // camelCase for variables

// JSON/Assets
mana:fire_strike                          // snake_case for IDs
assets/mam/textures/item/ruby.png         // snake_case for paths
```

### Contributing Workflow

```bash
# 1. Create feature branch
git checkout -b feature/amazing-feature

# 2. Make changes
# Edit files...

# 3. Test changes
./gradlew test
./gradlew runClient

# 4. Commit with conventional commits
git commit -m "feat: add amazing feature"

# 5. Push branch
git push origin feature/amazing-feature

# 6. Open Pull Request on GitHub
```

---

## 🤝 Contributing

We welcome contributions! Please follow these guidelines:

### How to Contribute

1. **Fork the Repository**

   - Click "Fork" on GitHub
   - Clone your fork locally

2. **Create a Feature Branch**

   ```bash
   git checkout -b feature/your-feature-name
   ```

3. **Make Changes**

   - Follow code style guidelines
   - Add tests for new features
   - Update documentation

4. **Test Thoroughly**

   ```bash
   ./gradlew test
   ./gradlew runClient
   ```

5. **Commit Changes**

   ```bash
   git commit -m "feat: description of changes"
   ```

6. **Push and Create PR**
   ```bash
   git push origin feature/your-feature-name
   ```

### Contribution Types

- **Bug Fixes:** Fix existing issues
- **New Features:** Add spells, rituals, gemstones
- **Documentation:** Improve docs, add examples
- **Translations:** Add language files
- **Performance:** Optimize code
- **Tests:** Add unit/integration tests

### Code Review Process

1. **Automated Checks:** CI must pass
2. **Code Review:** Maintainer reviews code
3. **Testing:** Feature tested in-game
4. **Merge:** PR merged to main branch

### Reporting Bugs

Use [GitHub Issues](https://github.com/mosberg/mam/issues) with:

- Minecraft version
- Mod version
- Fabric Loader version
- Full crash log (if applicable)
- Steps to reproduce

### Suggesting Features

Use [GitHub Discussions](https://github.com/mosberg/mam/discussions) with:

- Clear use case
- Expected behavior
- Alternative solutions considered

---

## 📄 License

This project is licensed under the **MIT License**.

```
MIT License

Copyright (c) 2026 Mosberg

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

### What This Means

✅ **Commercial Use:** Use in modpacks, even monetized
✅ **Modification:** Create derivative works
✅ **Distribution:** Share and redistribute freely
✅ **Private Use:** Use internally without sharing
⚠️ **Attribution:** Credit the original author
⚠️ **Liability:** Provided "as-is" without warranty

---

## 🙏 Acknowledgments

- **[Fabric Team](https://fabricmc.net/)** - Modding framework and documentation
- **[Yarn Mappings](https://github.com/FabricMC/yarn)** - Deobfuscation
- **Minecraft Modding Community** - Inspiration and support
- **Contributors** - Everyone who reports issues and suggests improvements

---

## 📞 Support

### Need Help?

- 🐛 **Bug Reports:** [GitHub Issues](https://github.com/mosberg/mam/issues)
- 💬 **Questions:** [GitHub Discussions](https://github.com/mosberg/mam/discussions)
- 📖 **Documentation:** [Project Wiki](https://mosberg.github.io/mam)
- 📧 **Contact:** [Mosberg on GitHub](https://github.com/Mosberg)

### Community

- 💬 **Discord:** Coming Soon
- 🐦 **Twitter:** Coming Soon
- 📺 **YouTube:** Coming Soon

---

## 📊 Project Status

| Component          | Status         | Version |
| ------------------ | -------------- | ------- |
| Core Mana System   | ✅ Stable      | 1.0.0   |
| Spell System       | ✅ Stable      | 1.0.0   |
| Ritual System      | ✅ Stable      | 1.0.0   |
| Gemstone System    | ✅ Stable      | 1.0.0   |
| World Generation   | ✅ Stable      | 1.0.0   |
| HUD/UI             | ✅ Stable      | 1.0.0   |
| Progression System | 🚧 In Progress | 0.9.0   |
| Multiplayer        | ✅ Stable      | 1.0.0   |
| Documentation      | ✅ Complete    | 1.0.0   |

---

## 🗺️ Roadmap

### Version 1.1 (Q1 2026)

- [ ] Spell combinations system
- [ ] Advanced ritual effects
- [ ] More gemstone types
- [ ] Mage armor and tools

### Version 1.2 (Q2 2026)

- [ ] Spell book item
- [ ] Ritual automation
- [ ] Mana transfer blocks
- [ ] Multiplayer optimizations

### Version 2.0 (Q3 2026)

- [ ] Complete progression overhaul
- [ ] School specializations
- [ ] PvP balance pass
- [ ] Addon API finalization

---

<div align="center">

**Made with ❤️ by [Mosberg](https://github.com/Mosberg)**

_For the Minecraft modding community_

[⬆ Back to Top](#-mana--magic-mam---complete-magic-system-for-minecraft)

</div>
