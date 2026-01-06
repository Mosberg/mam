# 🛣️ Mana & Magic - Development Roadmap

**Version:** 1.0.0
**Last Updated:** January 6, 2026 (Session 6 - Resource & Lang Cleanup)
**Target Release:** Q2 2026
**Current Progress:** 75% Framework ✅, 80% Content 🚧

## 🎉 Recent Completions (January 6, 2026 - Session 6)

### Assets & Localization ✅

- ✅ Fixed blockstates/models to match non-directional blocks (arcane tree log, arcane workbench, mana infuser, spell altar, spell amplifier, gift box) and corrected texture references.
- ✅ Added placeholder texture for `void_essence_block` and expanded EN/DA translations for mana node, gift boxes, candy items, and remaining block keys.

## 🎉 Recent Completions (January 6, 2026 - Session 4)

### Data Fixes ✅

- ✅ Corrected all 13 spell book crafting recipes so they now output their respective spell book items instead of repeating ingredient keys.

## 🎉 Recent Completions (January 6, 2026 - Session 2)

### Core Systems Completed ✅

- ✅ **Mana System:** Three pools with regeneration, persistence, and networking
- ✅ **Spell Casting:** All 5 cast types implemented with 65+ spell JSONs
- ✅ **Ritual System:** 34+ rituals across 13 categories
- ✅ **Client HUD:** Mana/health overlay with full customization
- ✅ **Networking:** Server-client mana sync with custom packets
- ✅ **Configuration:** Server properties + client JSON config

### Content Completed ✅

- ✅ **Items:** 26 wands, 13 spell books, 15 gemstones, 19 ore block items
- ✅ **Blocks:** 19 gemstone ores with deepslate variants
- ✅ **Loot Tables:** 19 ore loot tables with Fortune/Silk Touch support
- ✅ **Worldgen:** 15 configured features + 15 placed features for ore generation
- ✅ **Data Files:** 151 total JSON files (spells, rituals, worldgen, loot tables)
- ✅ **Item Models:** 47 JSON models for wands, spell books, gemstones
- ✅ **Block Models:** 19 ore block models with cube_all parent
- ✅ **Blockstates:** 19 ore blockstate JSONs
- ✅ **Translations:** English (US) with 100+ entries for all items/blocks
- ✅ **Item Tags:** 5 gemstone tags by rarity (common, uncommon, rare, epic, all)
- ✅ **Crafting Recipes:** 39 recipes (26 wands, 13 spell books) using gemstone tags
- ✅ **Total JSON Files:** 220+ (spells, rituals, worldgen, loot, recipes, tags, models)

### Tooling Completed ✅

- ✅ **Texture Generation Suite:** Python generators for items, blocks, entities, and GUI with shared palettes and overwrite-safe runs (see `generate_all_textures.py`).

---

## 📊 Project Timeline

```
Current (Jan 2026)    MVP Release      Full Release
    ↓                     ↓                  ↓
[████░░░░░░░░░░░░░░] [████████░░░░░░░░░░] [██████████████░░]
0%                    40%                 60%                100%
2 months              4 months            6 months
```

---

## 🎯 Phase 1: MVP Foundation (6-8 weeks)

**Goal:** Playable magic system with core features
**Target Date:** End of February 2026
**Effort:** ~120-160 hours

### Week 1-2: Spell Content Creation

**Status:** ❌ NOT STARTED
**Effort:** 40 hours
**Deliverables:** 50+ spell JSON files

#### Tasks

- [ ] **Fire School** (6 spells)

  - [ ] fire_strike (basic projectile)
  - [ ] flame_burst (AoE)
  - [ ] fireball (projectile with AoE)
  - [ ] inferno_nova (large AoE)
  - [ ] meteor_strike (delayed AoE)
  - [ ] phoenix_rise (utility/resurrection)
  - **Effort:** 8 hours
  - **JSON Structure:** Review `docs/FEATURES_FUNCTIONS.md` Fire School section

- [ ] **Ice School** (5 spells)

  - [ ] frost_bolt (projectile with slowness)
  - [ ] ice_shard (faster projectile)
  - [ ] ice_comet (heavy impact)
  - [ ] blizzard_storm (area storm)
  - [ ] glacial_prison (freeze trap)
  - **Effort:** 7 hours

- [ ] **Arcane School** (6 spells)

  - [ ] arcane_missile (homing)
  - [ ] mana_shield (utility)
  - [ ] spell_weave (combo enhancer)
  - [ ] arcane_implosion (gravity well)
  - [ ] dimension_shift (teleport)
  - [ ] time_dilation (slow zone)
  - **Effort:** 8 hours

- [ ] **Light School** (5 spells)

  - [ ] heal (self-heal)
  - [ ] smite (ray attack)
  - [ ] holy_blast (AoE)
  - [ ] celestial_beam (piercing ray)
  - [ ] radiant_judgment (area smite)
  - **Effort:** 7 hours

- [ ] **Remaining Schools** (32 spells)
  - [ ] Dark, Thunder, Nature, Water, Earth, Air, Blood, Chaos, Void
  - **Effort:** 32 hours (4 spells × 8 hours)

**Definition of Done:**

- All 50+ spell JSON files created
- Validated against schema
- Spells load without errors
- Spell stats balance-reviewed

---

### Week 2-3: Basic Spell Effects Implementation

**Status:** ❌ NOT STARTED
**Effort:** 30 hours
**Dependencies:** Week 1-2 (spell files created)

#### Tasks

- [ ] **Projectile Cast Type**

  - [ ] Implement projectile spawning
  - [ ] Add velocity/direction
  - [ ] Projectile collision detection
  - [ ] Damage on hit
  - [ ] Status effect application
  - **Effort:** 12 hours
  - **File to Modify:** `spell/SpellCaster.java`

- [ ] **AoE Cast Type**

  - [ ] AoE detection (entity box)
  - [ ] Damage to all entities
  - [ ] Knockback/force effects
  - [ ] Particle spawning
  - **Effort:** 10 hours

- [ ] **Utility Cast Type**
  - [ ] Self-buffs
  - [ ] Healing
  - [ ] Teleportation
  - [ ] Mana restoration
  - **Effort:** 8 hours

**Definition of Done:**

- Can cast basic projectile spells
- Projectiles deal damage
- AoE detection works
- Status effects apply

---

### Week 3-4: Ritual Content & Basic Execution

**Status:** ❌ NOT STARTED
**Effort:** 35 hours

#### Tasks

- [ ] **Create Ritual JSON Files** (20 hours)

  - [ ] 2-3 rituals per category
  - [ ] Pattern definitions
  - [ ] Mana costs
  - [ ] Duration settings
  - [ ] Effect data

- [ ] **Ritual Pattern Detection** (10 hours)

  - [ ] Scan multi-block structures
  - [ ] Validate material requirements
  - [ ] Check radius/orientation
  - [ ] Return validation result
  - **File:** `ritual/RitualPattern.java`

- [ ] **Basic Ritual Execution** (5 hours)
  - [ ] Start ritual on detection
  - [ ] Drain mana over time
  - [ ] Apply basic effects
  - [ ] Complete or fail ritual

**Definition of Done:**

- 13+ ritual JSON files created
- Can detect ritual pattern
- Can start basic ritual
- Mana drains properly

---

### Week 4-5: Client-Side Basics

**Status:** ❌ NOT STARTED
**Effort:** 40 hours
**Priority:** HIGH (blocks all in-game testing)

#### Create New Files

- [ ] `src/client/java/dk/mosberg/client/MAMClient.java`

  - Client entry point (ClientModInitializer)
  - Register keybinds
  - Register screens

- [ ] `src/client/java/dk/mosberg/client/screen/SpellSelectionScreen.java`

  - 50-spell menu
  - School filtering
  - Keybind to open (default: K)

- [ ] `src/client/java/dk/mosberg/client/hud/ManaBarRenderer.java`

  - Render 3-tier mana bars
  - Position (top-left by default)
  - Update on mana sync

- [ ] `src/client/java/dk/mosberg/client/hud/CooldownOverlay.java`
  - Circular cooldown indicators
  - Show remaining cooldown time

#### Tasks

- [ ] **Keybind System** (8 hours)

  - [ ] Register K key for spell menu
  - [ ] Register R key for cast selected spell
  - [ ] Register C key for cooldowns toggle

- [ ] **Spell Selection Screen** (20 hours)

  - [ ] List all 50+ spells
  - [ ] Filter by school (tabs)
  - [ ] Show spell details (cost, cooldown, description)
  - [ ] Preview selected spell

- [ ] **Mana Bar HUD** (8 hours)

  - [ ] Render 3 bars (personal, aura, reserve)
  - [ ] Color coding (cyan, purple, orange)
  - [ ] Percentage text

- [ ] **Network Synchronization** (4 hours)
  - [ ] C2S: Send spell cast request
  - [ ] S2C: Mana update
  - [ ] S2C: Cooldown update

**Definition of Done:**

- Can open spell menu (K key)
- Can select and cast spell (R key)
- Mana bars display correctly
- Cooldowns show on overlay

---

### Week 5-6: Testing & Polish

**Status:** ❌ NOT STARTED
**Effort:** 20 hours

#### Tasks

- [ ] **Unit Tests**

  - [ ] ManaPool consumption/regeneration
  - [ ] Spell loading validation
  - [ ] Ritual pattern detection
  - **Effort:** 10 hours

- [ ] **In-Game Testing**

  - [ ] Test 20+ spells
  - [ ] Test 5+ rituals
  - [ ] Verify mana persistence
  - [ ] Check balance issues
  - **Effort:** 8 hours

- [ ] **Bug Fixes**
  - [ ] Address crashes
  - [ ] Fix balance issues
  - [ ] Polish UI/UX
  - **Effort:** 2 hours

**Definition of Done:**

- 0 known critical bugs
- All core systems tested
- Balance reviewed

---

### Week 6-8: Documentation & Release

**Status:** ❌ NOT STARTED
**Effort:** 15 hours

#### Tasks

- [ ] **Update Docs** (5 hours)

  - [ ] Update FEATURES_FUNCTIONS.md with implemented systems
  - [ ] Create CHANGELOG.md
  - [ ] Update README with v1.0 features

- [ ] **Create User Guide** (5 hours)

  - [ ] How to cast spells
  - [ ] Mana pool mechanics
  - [ ] Basic rituals

- [ ] **Release Preparation** (5 hours)
  - [ ] Version 1.0.0 JAR build
  - [ ] CurseForge upload
  - [ ] Modrinth upload

**Definition of Done:**

- Release-quality documentation
- JAR available for download

---

## 🎯 Phase 2: Core Features (8-10 weeks)

**Goal:** Advanced spell effects, complete ritual system, world generation
**Target Date:** End of April 2026
**Effort:** ~160-200 hours

### Week 1-2: Channel & Ray Cast Types

**Status:** ❌ NOT STARTED
**Effort:** 40 hours

#### Tasks

- [ ] **Channel Cast Type** (20 hours)

  - [ ] Implement held-key mechanics
  - [ ] Damage/effect per tick
  - [ ] Visual beam/channel effect
  - [ ] Cancel on key release
  - [ ] Create 5+ channel spells

- [ ] **Ray Cast Type** (20 hours)
  - [ ] Raycast from player eye
  - [ ] Hit detection
  - [ ] Penetrating rays
  - [ ] Create 5+ ray spells

**Examples:**

- Channel: Flame channel, frost beam, healing aura
- Ray: Lightning bolt, celestial beam, shadow bolt

---

### Week 2-4: Ritual Execution System

**Status:** ❌ NOT STARTED
**Effort:** 50 hours

#### Tasks

- [ ] **Active Ritual Tracking** (15 hours)

  - [ ] RitualState class
  - [ ] Per-player ritual state
  - [ ] Duration countdown
  - [ ] Mana drain over time

- [ ] **Ritual Effects** (20 hours)

  - [ ] Apply buff effects
  - [ ] Summon entities
  - [ ] Teleport player
  - [ ] Modify world
  - [ ] Complete/fail logic

- [ ] **Ritual Visualization** (15 hours)
  - [ ] Particle rings
  - [ ] Glowing blocks
  - [ ] Sound effects
  - [ ] Completion animation

**Rituals to Implement:**

- Circle: Protective Circle
- Elemental: Elemental Convergence
- Summoning: Summon Familiar
- Ascension: Apotheosis
- And 8+ more...

---

### Week 4-6: World Generation

**Status:** ❌ NOT STARTED
**Effort:** 60 hours

#### Tasks

- [ ] **Ore Generation** (15 hours)

  - [ ] 15 gemstone ores
  - [ ] Rarity-based distribution
  - [ ] Deepslate variants
  - [ ] Ore drops configuration

- [ ] **Mana Nodes** (15 hours)

  - [ ] Float in air
  - [ ] Mana regeneration aura
  - [ ] Breaking gives mana shards
  - [ ] Rare generation

- [ ] **Mana Springs** (10 hours)

  - [ ] Liquid mana blocks
  - [ ] Walking gives mana restoration
  - [ ] Water-like behavior

- [ ] **Altar Ruins** (20 hours)
  - [ ] Ancient structures
  - [ ] Loot tables
  - [ ] Ritual pedestal inside
  - [ ] Atmosphere/lore

---

### Week 6-8: Advancement System

**Status:** ❌ NOT STARTED
**Effort:** 40 hours

#### Tasks

- [ ] **Spell Progression** (20 hours)

  - [ ] Cast spell = gain XP
  - [ ] Unlock higher-tier spells
  - [ ] Specialization system
  - [ ] Bonus perks

- [ ] **Advancement Tree** (10 hours)

  - [ ] Create advancement JSON files
  - [ ] Progression milestones
  - [ ] Unlock new spell schools

- [ ] **Ritual Mastery** (10 hours)
  - [ ] Complete rituals = progress
  - [ ] Unlock advanced rituals
  - [ ] Category mastery bonuses

**Example Progressions:**

- Level 1-10: Basic spells
- Level 11-25: Intermediate spells
- Level 26-50: Advanced spells
- Level 51-100: Master spells

---

### Week 8-10: Enchantment System

**Status:** ❌ NOT STARTED
**Effort:** 40 hours

#### Tasks

- [ ] **Capacity Enchantment** (15 hours)

  - [ ] Increases mana pool size
  - [ ] Works on wands/artifacts
  - [ ] Levels I-V

- [ ] **Efficiency Enchantment** (15 hours)

  - [ ] Reduces mana costs
  - [ ] Levels I-V

- [ ] **Potency Enchantment** (10 hours)
  - [ ] Increases spell damage
  - [ ] Levels I-III

---

## 🎯 Phase 3: Polish & Expansion (6-8 weeks)

**Goal:** Advanced features, complete UI, performance optimization
**Target Date:** End of June 2026
**Effort:** ~120-160 hours

### Features

- [ ] **Advanced Entities** (40 hours)

  - [ ] Summoned elementals
  - [ ] Arcane golems
  - [ ] Phoenix behavior
  - [ ] Mana guardians

- [ ] **Spell Synergies** (30 hours)

  - [ ] Dual-school combos
  - [ ] Enhanced effects
  - [ ] Visual feedback

- [ ] **Advanced UI** (30 hours)

  - [ ] Spell tree UI
  - [ ] Mage level display
  - [ ] Specialization menu
  - [ ] Ritual preview

- [ ] **Performance** (20 hours)
  - [ ] Optimize spell casting
  - [ ] Parallel ritual detection
  - [ ] Render optimization

---

## 📋 Detailed Task Breakdown by Week

### MVP Phase (Weeks 1-8)

| Week      | Focus            | Hours   | Status | Risk |
| --------- | ---------------- | ------- | ------ | ---- |
| 1-2       | Spell JSON files | 40      | ❌     | 🟡   |
| 2-3       | Spell effects    | 30      | ❌     | 🔴   |
| 3-4       | Rituals          | 35      | ❌     | 🟠   |
| 4-5       | Client basics    | 40      | ❌     | 🔴   |
| 5-6       | Testing          | 20      | ❌     | 🟡   |
| 6-8       | Polish           | 15      | ❌     | 🟢   |
| **Total** |                  | **180** |        |      |

---

## 🎯 Success Criteria

### MVP (End of Phase 1)

- [ ] ✅ 50+ spells implemented and castable
- [ ] ✅ 13+ rituals implemented
- [ ] ✅ Mana system working and persisting
- [ ] ✅ Player can cast spells in-game
- [ ] ✅ HUD displays mana and cooldowns
- [ ] ✅ Commands functional
- [ ] [ ] Build is stable (0 crash-on-startup)

### v1.5 (End of Phase 2)

- [ ] ✅ All 50+ documented spells working
- [ ] ✅ World generation operational
- [ ] ✅ Ritual system fully functional
- [ ] ✅ Advanced entities spawning
- [ ] ✅ Progression system tracking

### v2.0 (End of Phase 3)

- [ ] ✅ All planned features complete
- [ ] ✅ Balanced and polished
- [ ] ✅ Multiplayer tested
- [ ] ✅ Performance optimized

---

## ⚠️ Risk Assessment

### High Risk 🔴

1. **Client-side rendering** (Weeks 4-5)

   - Impact: Blocks all in-game testing
   - Mitigation: Start early, use templates

2. **Spell balance** (Weeks 2-6)

   - Impact: Gameplay feels broken
   - Mitigation: Create balance spreadsheet, test frequently

3. **Network synchronization** (Week 4)
   - Impact: Multiplayer broken
   - Mitigation: Use Fabric API, test with 2+ players

### Medium Risk 🟠

1. **Ritual pattern detection** (Weeks 3-4)

   - Complex pattern matching
   - Mitigation: Test with simple patterns first

2. **Data loading** (Weeks 1-4)
   - JSON parsing edge cases
   - Mitigation: Schema validation, error logging

### Low Risk 🟢

1. **Mana system changes** (Already implemented)
2. **Command system** (Already implemented)
3. **Item/block registration** (Already implemented)

---

## 📊 Resource Requirements

### Development Team

- **1 Lead Dev** (Full-time) - Core systems, architecture
- **1 Content Creator** (Part-time) - Spell/ritual JSON files, balance
- **1 Tester** (Part-time) - QA, bug reporting

### Tools

- IntelliJ IDEA (development)
- Git (version control)
- Gradle (build automation)
- GSON (JSON handling - already integrated)
- JUnit 5 (testing - needs setup)

---

## 📈 Milestones

### Milestone 1: Core Systems (Feb 15, 2026)

- All spell JSON files created
- Spell effects 50% implemented
- Client keybinds working

### Milestone 2: Playable MVP (Feb 28, 2026)

- Can cast 50+ spells
- Basic rituals working
- Mana bar displays
- First release candidate

### Milestone 3: Full Features (Apr 30, 2026)

- World generation complete
- Progression system done
- All documented features working

### Milestone 4: Polish & Release (June 30, 2026)

- Balanced and optimized
- v1.0.0 release
- Full documentation

---

## 🔄 Iteration Strategy

### Sprint Structure (2-week sprints)

- **Sprint Planning** (Monday, 2 hours)
- **Daily Standups** (15 min, 5 days)
- **Sprint Review** (Friday, 2 hours)
- **Sprint Retrospective** (Friday, 1 hour)

### Definition of Ready

- [ ] Task has clear acceptance criteria
- [ ] Effort estimated
- [ ] Dependencies identified
- [ ] Resources assigned

### Definition of Done

- [ ] Code written and reviewed
- [ ] Tests passing (when applicable)
- [ ] Documentation updated
- [ ] Merged to main branch

---

## 📝 Dependencies

### Blocking Items

1. **Client-side framework** → Spell menu, HUD rendering
2. **Spell JSON files** → Testing spell system
3. **Network packets** → Multiplayer support

### Non-blocking Items

1. Advanced entities (after spell system)
2. World generation (parallel track)
3. Progression system (parallel track)

---

## 🎯 Alternative Paths

### Fast Track (12 weeks instead of 20)

- Skip advanced entities
- Minimal world generation
- Simple progression
- Focus on core 20 spells

### Extended Plan (26 weeks)

- Add more spell schools
- Advanced rituals (30+)
- Custom dimensions
- Full progression tree
- Multiplayer guilds

---

## 📞 Communication Plan

### Status Updates

- **Weekly:** Sprint review + demo
- **Monthly:** Progress report + roadmap updates
- **Quarterly:** Architecture review + planning

### Issue Tracking

- GitHub Issues for bugs
- GitHub Projects for planning
- Wiki for documentation

---

## ✅ Pre-MVP Checklist

Before Phase 1 starts:

- [ ] Build system verified (builds clean)
- [ ] Git repository set up
- [ ] Development environment configured
- [ ] Team roles assigned
- [ ] Communication channels established
- [ ] Testing framework set up
- [ ] CI/CD pipeline (if needed)

---

## 🚀 Go/No-Go Decision Points

### Week 2 Go/No-Go

- **GO if:** Spell JSON structure working, 20+ spells created
- **NO-GO if:** Major parsing issues, schema problems unsolved

### Week 4 Go/No-Go

- **GO if:** Spells castable, basic effects working, 50% client done
- **NO-GO if:** Core casting broken, network issues persist

### Week 6 Go/No-Go

- **GO if:** MVP playable, tests passing, <5 critical bugs
- **NO-GO if:** Major features broken, stability issues

---

**Document Version:** 1.0
**Last Updated:** 2026-01-06
**Next Review:** 2026-01-13
