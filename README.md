# Mana and Magic

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

- **Data-Driven:** All spells, rituals, gemstones, and worldgen are defined in JSON. Java is only for registration and custom logic.
- **Extensible:** Add new content by editing JSON and assets—no code required for most features.
- **Modern Fabric:** Built for MC 1.21.11+, Fabric 0.18.4, Java 21. Uses best practices for NBT, Codecs, and persistent state.
- **Progression:** Tiered gemstone and spell system, with customizable advancement and worldgen.
- **Synergy:** Dual-hand/catalyst spell synergy, ritual synergies, and gemstone-school bindings.

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
