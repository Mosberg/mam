# Mana And Magic (MAM) - Fabric Mod Development Guide

## 📋 Documentation Management Protocol

**CRITICAL:** Before completing ANY task or ending a work session, you MUST update the following documentation files to reflect the current state of the project:

### Required Documentation Updates

1. **README.md** (Root) - User-facing project overview

   - Update feature status when new features are implemented
   - Reflect current version numbers and dependencies
   - Keep installation/usage instructions current

2. **docs/ROADMAP.md** - Development timeline and progress tracking

   - Mark completed tasks with ✅
   - Update progress percentages
   - Move completed items from "NOT STARTED" to "COMPLETED" sections
   - Update "Last Updated" date to current date

3. **docs/IMPLEMENTATION_SUMMARY.md** - Technical implementation details

   - Add newly implemented features with full technical descriptions
   - Document API changes, new classes, and system architectures
   - Include code snippets showing how new features work

4. **docs/FEATURES_FUNCTIONS.md** - Feature catalog with usage examples

   - Document new spells, rituals, items, blocks with full specifications
   - Include JSON schemas and example configurations
   - Add usage instructions for new features

5. **docs/COMPLETION_REPORT.md** - Work session summary

   - Append new completion entries with timestamp
   - Summarize what was implemented/fixed
   - Note any breaking changes or migration steps

6. **docs/ASSETS_COMPLETE.md** or **docs/TEXTURES_COMPLETE.md** - Asset tracking

   - Update when assets (models, textures, sounds) are added
   - Mark completion status of asset categories
   - List newly added files

7. **docs/PLANNED_FEATURES.md** - Future feature planning
   - Remove features that are now implemented
   - Add new ideas that emerge during development
   - Prioritize based on current project state

### Documentation Update Workflow

When completing ANY development task:

```
1. Implement code/content changes
2. Run ./gradlew build to verify compilation
3. Update ALL relevant documentation files listed above
4. Verify documentation is accurate and complete
5. Commit changes with descriptive message
```

### Documentation Style Guidelines

- Use clear, concise language
- Include practical code examples
- Mark status with emojis: ✅ (done), ❌ (not started), 🚧 (in progress)
- Always update "Last Updated" timestamps
- Cross-reference related documents
- Keep technical accuracy paramount

### Why This Matters

- Ensures project state is always documented
- Prevents information loss between sessions
- Helps new contributors understand the project
- Tracks progress toward milestones
- Makes troubleshooting easier with accurate historical records

### Cross-Document Synchronization

When updating documentation, ensure consistency across all files:

#### Feature Implementation Flow

1. **Task starts** → Update docs/ROADMAP.md (mark 🚧 in progress)
2. **Code written** → Document in docs/IMPLEMENTATION_SUMMARY.md
3. **Feature complete** → Update docs/FEATURES_FUNCTIONS.md with usage
4. **Task ends** → Update docs/ROADMAP.md (mark ✅ complete), docs/COMPLETION_REPORT.md
5. **If user-facing** → Update README.md feature list

#### Document Relationships

- **README.md** references → docs/ROADMAP.md, docs/FEATURES_FUNCTIONS.md
- **ROADMAP.md** tasks align with → PLANNED_FEATURES.md goals
- **IMPLEMENTATION_SUMMARY.md** technical details match → FEATURES_FUNCTIONS.md schemas
- **COMPLETION_REPORT.md** logs reference → ROADMAP.md task IDs
- **ASSETS_COMPLETE.md** file lists match → actual files in src/main/resources/

#### Auto-Update Triggers

Update documentation when:

- ✅ New Java class/method added → IMPLEMENTATION_SUMMARY.md
- ✅ New JSON file created (spell/ritual) → FEATURES_FUNCTIONS.md, ROADMAP.md
- ✅ New block/item registered → README.md feature count, IMPLEMENTATION_SUMMARY.md
- ✅ Asset file created → ASSETS_COMPLETE.md, TEXTURES_COMPLETE.md
- ✅ Build completes successfully → COMPLETION_REPORT.md session log
- ✅ gradle.properties version changed → README.md, ROADMAP.md

#### README.md Special Rules

The README.md serves as the project's public face. Update it when:

1. **Major features complete** - Add to Features section with ✅
2. **Version changes** - Update version badge/number
3. **Dependencies change** - Update Tech Stack section
4. **Installation changes** - Update setup instructions
5. **New content types added** - Update spell/ritual/gemstone counts

Example README.md update triggers:

```markdown
# When you implement 5 new spells:

- Update "X spell schools" count if schools changed
- Don't update README for individual spells (use FEATURES_FUNCTIONS.md)

# When you add worldgen system:

- Add "✅ Ore Generation" to Features section
- Update feature description

# When you complete a major milestone:

- Update "Current Progress: X%" if it exists
- Add to changelog/release notes section
```

---

## Project Overview

**Mana And Magic** is a data-driven, extensible magic mod for Minecraft 1.21.11 using Fabric Loader. Built with Java 21, Yarn mappings, and split source sets for strict client/server separation.

**Tech Stack:**

- Minecraft 1.21.11 + Fabric Loader 0.18.4
- Yarn mappings `1.21.11+build.3` (intermediary → named)
- Fabric Loom 1.14.10 with split source sets
- Java 21 toolchain with G1GC optimization

## Remote Indexing & API References

**GitHub Copilot Remote Indexing:** This project uses remote repository indexing for enhanced context awareness. The following repositories are indexed for API guidance:

### Core Dependencies (Remote Indexed)

- **Fabric API** - `https://github.com/FabricMC/fabric` - Fabric API hooks, networking, rendering
- **Minecraft Yarn Mappings** - `https://github.com/FabricMC/yarn` - Readable class/method names for 1.21.11
- **Fabric Loader** - `https://github.com/FabricMC/fabric-loader` - Mod loading system
- **Fabric Loader 0.18.4 Docs** - `https://maven.fabricmc.net/docs/fabric-loader-0.18.4/` - Packages for Fabric Loader 0.18.4
- **Fabric API 0.140.2+1.21.11 Docs** - `https://maven.fabricmc.net/docs/fabric-api-0.140.2+1.21.11/` - Packages for Fabric API 0.140.2+1.21.11
- **Yarn 1.21.11+build.3 Docs** - `https://maven.fabricmc.net/docs/yarn-1.21.11+build.3/` - Packages for Yarn mappings 1.21.11+build.3

### Key Documentation Sources

- **Fabric Wiki** - https://fabricmc.net/wiki/ - Comprehensive mod development guide
- **Fabric API Docs** - https://maven.fabricmc.net/ - Maven repository with Javadocs
- **Yarn Mappings Browser** - https://fabricmc.net/develop - Search intermediary mappings
- **Docs Fabricmc Develop** - https://docs.fabricmc.net/develop/ - Developer Guides for Fabric projects
- **Docs Fabricmc Develop Items Creating Your First Item** - https://docs.fabricmc.net/develop/items/first-item - Step-by-step item creation guide
- **Docs Fabricmc Develop Items Food Items** - https://docs.fabricmc.net/develop/items/food - Guide on creating food items
- **Docs Fabricmc Develop Items Potions** - https://docs.fabricmc.net/develop/items/potions - Guide on creating potion items
- **Docs Fabricmc Develop Items Spawn Eggs** - https://docs.fabricmc.net/develop/items/spawn-eggs - Guide on creating spawn egg items
- **Docs Fabricmc Develop Items Tools and Weapons** - https://docs.fabricmc.net/develop/items/custom-tools - Guide on creating custom tools and weapons
- **Docs Fabricmc Develop Items Custom Armor** - https://docs.fabricmc.net/develop/items/custom-armor - Guide on creating custom armor items
- **Docs Fabricmc Develop Items Item Models** - https://docs.fabricmc.net/develop/items/item-models - Guide on defining item models
- **Docs Fabricmc Develop Items Item Appearance** - https://docs.fabricmc.net/develop/items/item-appearance - Guide on customizing item appearance
- **Docs Fabricmc Develop Items Custom Creative Tabs** - https://docs.fabricmc.net/develop/items/custom-item-groups - Guide on creating custom creative tabs
- **Docs Fabricmc Develop Items Custom Item Interactions** - https://docs.fabricmc.net/develop/items/custom-item-interactions - Guide on implementing custom item interactions
- **Docs Fabricmc Develop Items Custom Enchantment Effects** - https://docs.fabricmc.net/develop/items/custom-enchantment-effects - Guide on creating custom enchantment effects
- **Docs Fabricmc Develop Items Custom Data Components** - https://docs.fabricmc.net/develop/items/custom-data-components - Guide on adding custom data components to items
- **Docs Fabricmc Develop Blocks Creating Your First Block** - https://docs.fabricmc.net/develop/blocks/first-block - Step-by-step block creation guide
- **Docs Fabricmc Develop Blocks Block Models** - https://docs.fabricmc.net/develop/blocks/block-models - Guide on defining block models
- **Docs Fabricmc Develop Blocks Block States** - https://docs.fabricmc.net/develop/blocks/blockstates - Guide on defining block states
- **Docs Fabricmc Develop Blocks Block Entities** - https://docs.fabricmc.net/develop/blocks/block-entities - Guide on creating block entities
- **Docs Fabricmc Develop Blocks Block Entity Renderers** - https://docs.fabricmc.net/develop/blocks/block-entity-renderers - Guide on rendering block entities
- **Docs Fabricmc Develop Blocks Transparency and Tinting** - https://docs.fabricmc.net/develop/blocks/transparency-and-tinting - Guide on block transparency and tinting
- **Docs Fabricmc Develop Entities Entity Attributes** - https://docs.fabricmc.net/develop/entities/attributes - Guide on defining entity attributes
- **Docs Fabricmc Develop Entities Mob Effects** - https://docs.fabricmc.net/develop/entities/effects - Guide on creating custom mob effects
- **Docs Fabricmc Develop Entities Damage Types** - https://docs.fabricmc.net/develop/entities/damage-types - Guide on defining custom damage types
- **Docs Fabricmc Develop Sounds Playing Sounds** - https://docs.fabricmc.net/develop/sounds/using-sounds - Guide on playing sounds in-game
- **Docs Fabricmc Develop Sounds Creating Custom Sounds** - https://docs.fabricmc.net/develop/sounds/custom-sounds - Guide on creating and adding custom sounds
- **Docs Fabricmc Develop Sounds Dynamic and Interactive Sounds** - https://docs.fabricmc.net/develop/sounds/dynamic-sounds - Guide on implementing dynamic and interactive sounds
- **Docs Fabricmc Develop Commands Creating Commands** - https://docs.fabricmc.net/develop/commands/basics - Guide on creating commands
- **Docs Fabricmc Develop Commands Command Arguments** - https://docs.fabricmc.net/develop/commands/arguments - Guide on using command arguments
- **Docs Fabricmc Develop Commands Command Suggestions** - https://docs.fabricmc.net/develop/commands/suggestions - Guide on adding suggestions to commands
- **Docs Fabricmc Develop Rendering Basic Rendering Concepts** - https://docs.fabricmc.net/develop/rendering/basic-concepts - Overview of rendering concepts in Fabric
- **Docs Fabricmc Develop Rendering Drawing to the GUI** - https://docs.fabricmc.net/develop/rendering/draw-context - Guide on drawing to the GUI
- **Docs Fabricmc Develop Rendering Rendering in the HUD** - https://docs.fabricmc.net/develop/rendering/hud - Guide on rendering elements in the HUD
- **Docs Fabricmc Develop Rendering Rendering in the World** - https://docs.fabricmc.net/develop/rendering/world - Guide on rendering custom elements in the world
- **Docs Fabricmc Develop Rendering GUI Custom Screens** - https://docs.fabricmc.net/develop/rendering/gui/custom-screens - Guide on creating custom GUI screens
- **Docs Fabricmc Develop Rendering GUI Custom Widgets** - https://docs.fabricmc.net/develop/rendering/gui/custom-widgets - Guide on creating custom GUI widgets
- **Docs Fabricmc Develop Rendering Particles Creating Custom Particles** - https://docs.fabricmc.net/develop/rendering/particles/creating-particles - Guide on creating custom particles
- **Docs Fabricmc Develop Codecs** - https://docs.fabricmc.net/develop/codecs/ - Guide on using codecs for data serialization
- **Docs Fabricmc Develop Data Attachments** - https://docs.fabricmc.net/develop/data-attachments/ - Guide on attaching custom data to game objects
- **Docs Fabricmc Develop Saved Data** - https://docs.fabricmc.net/develop/saved-data/ - Guide on saving and loading custom data
- **Docs Fabricmc Develop Events** - https://docs.fabricmc.net/develop/events/ - Overview of event handling in Fabric
- **Docs Fabricmc Develop Text and Translations** - https://docs.fabricmc.net/develop/text-and-translations/ - Guide on handling text and translations
- **Docs Fabricmc Develop Networking** - https://docs.fabricmc.net/develop/networking/ - Guide on implementing networking in Fabric mods
- **Docs Fabricmc Develop Key Mappings** - https://docs.fabricmc.net/develop/key-mappings/ - Guide on creating and handling key mappings
- **Docs Fabricmc Develop Debugging Mods** - https://docs.fabricmc.net/develop/debugging/ - Guide on debugging Fabric mods
- **Docs Fabricmc Develop Automated Testing** - https://docs.fabricmc.net/develop/automatic-testing/ - Guide on setting up automated tests for Fabric mods
- **Docs Fabricmc Develop Data Generation Data Generation Setup** - https://docs.fabricmc.net/develop/data-generation/setup/ - Guide on setting up data generation
- **Docs Fabricmc Develop Data Generation Translation Generation** - https://docs.fabricmc.net/develop/data-generation/translations/ - Guide on generating translations
- **Docs Fabricmc Develop Data Generation Block Model Generation** - https://docs.fabricmc.net/develop/data-generation/block-models/ - Guide on generating block models
- **Docs Fabricmc Develop Data Generation Item Model Generation** - https://docs.fabricmc.net/develop/data-generation/item-models/ - Guide on generating item models
- **Docs Fabricmc Develop Data Generation Tag Generation** - https://docs.fabricmc.net/develop/data-generation/tags/ - Guide on generating tags
- **Docs Fabricmc Develop Data Generation Advancement Generation** - https://docs.fabricmc.net/develop/data-generation/advancements/ - Guide on generating advancements
- **Docs Fabricmc Develop Data Generation Recipe Generation** - https://docs.fabricmc.net/develop/data-generation/recipes/ - Guide on generating recipes
- **Docs Fabricmc Develop Data Generation Loot Table Generation** - https://docs.fabricmc.net/develop/data-generation/loot-tables/ - Guide on generating loot tables

**Usage in Development:**

- Copilot can reference Fabric API patterns from indexed repositories
- Minecraft 1.21.11-specific APIs are resolved via Yarn mappings
- Client/server split patterns follow Fabric Loader conventions
- Network packet handling uses Fabric Networking API v1

## Architecture & Source Organization

### Core Content Design

**13 Spell Schools:** Air, Arcane, Blood, Chaos, Dark, Earth, Fire, Ice, Light, Nature, Thunder, Void, Water
**13 Ritual Categories:** Ascension, Circle, Cosmic, Elemental, Fountain, Planar, Reality, Resurrection, Sacrifice, Summoning, Temporal, Transformation, Vortex
**15 Gemstone Types:** Ruby, Sapphire, Tanzanite (Epic); Apatite, Aquamarine, Moonstone, Rhodonite, Topaz, Tourmaline (Rare); Carnelian, Citrine, Jade, Peridot, Sodalite (Uncommon); Hematite (Common)

**Design principle:** Each gemstone binds to specific spell schools and ritual categories. See README.md for complete gemstone-to-school/ritual mappings.

### Split Source Sets (Loom Feature)

**Critical:** This project uses `splitEnvironmentSourceSets()` which physically separates client and server code at compile time.

```
src/
├── main/               # Server-side & common logic (runs on both sides)
│   ├── java/dk/mosberg/
│   │   └── MAM.java   # Main entrypoint (ModInitializer)
│   └── resources/
│       ├── fabric.mod.json  # Mod manifest (template - auto-expanded)
│       └── assets/mam/      # Shared resources
│
└── client/            # Client-only code (NEVER imported by server)
    ├── java/dk/mosberg/client/
    │   └── MAMClient.java    # Client entrypoint (ClientModInitializer)
    └── resources/
        └── assets/mam/       # Client-specific assets
```

### Environment Rules

1. **Server code cannot import client packages** - Causes `ClassNotFoundException` on dedicated servers
2. **Client code can import server code** - Safe, clients have all classes
3. **Annotate client-only classes with `@Environment(EnvType.CLIENT)`** (from `net.fabricmc.api.EnvType`)
4. **Use Yarn mappings** - All Minecraft classes use intermediary names that remap to readable names

### Entry Points

- `dk.mosberg.MAM` (main) - Runs on both client and server, register items/blocks here
- `dk.mosberg.client.MAMClient` (client) - Runs only on client, register renderers/keybinds here

## Build System & Development Workflow

### Essential Commands

```bash
./gradlew build              # Full build + tests (creates JAR in build/libs/)
./gradlew runClient          # Launch Minecraft with mod loaded (run/ directory)
./gradlew runServer          # Launch dedicated server (run-server/ directory)
./gradlew build -x test      # Fast build without tests
./gradlew projectInfo        # Show version info and configuration
./gradlew clean              # Remove build artifacts
```

### Gradle Configuration

**gradle.properties** - Single source of truth for versions:

- All metadata (`mod_id`, `mod_version`, `mod_name`, etc.) auto-populates `fabric.mod.json`
- Update Minecraft/Fabric versions here, then run `./gradlew build`
- **Never edit `fabric.mod.json` directly** - it's a template that gets expanded during `processResources`

**build.gradle** key features:

- Yarn mappings for readable Minecraft names: `mappings "net.fabricmc:yarn:${yarn_mappings}:v2"`
- Split source sets configured via `loom.splitEnvironmentSourceSets()`
- Modding Helper API dependency: `modImplementation "dk.mosberg:moddinghelperapi:1.0.0"` (local Maven)
- GSON bundled in JAR via `include implementation()`

### IDE Setup (IntelliJ IDEA / Eclipse)

- Fabric Loom auto-generates run configurations (`ideConfigGenerated = true`)
- **F5 / Run "Minecraft Client"** - Full client with hot code swap support
- **F5 / Run "Minecraft Server"** - Dedicated server for multiplayer testing
- Set breakpoints in both `src/main` and `src/client` code

### Yarn Mappings Workflow

Yarn provides human-readable names for Minecraft's obfuscated code:

- `net.minecraft.item.Item` instead of `class_1792`
- `player.getInventory()` instead of `method_31548()`
- **When Minecraft updates:** Check https://fabricmc.net/develop for new yarn mappings version
- **IDE autocomplete** works natively - no manual decompilation needed

## Coding Conventions

### Mod Constants

```java
// In MAM.java
public static final String MOD_ID = "mam";  // Used everywhere
public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

// Usage in other classes
Identifier id = Identifier.of(MAM.MOD_ID, "magic_wand");  // Creates "mam:magic_wand"
Registry.register(Registries.ITEM, id, MAGIC_WAND);
```

### Logging

```java
// Use the centralized logger from MAM.java
MAM.LOGGER.info("Initializing magic system");
MAM.LOGGER.warn("Deprecated spell format detected");
MAM.LOGGER.error("Failed to load spell: {}", spellId, exception);
```

### Client/Server Code Examples

```java
// ✅ CORRECT: Server-side registration (src/main)
public class MAM implements ModInitializer {
    public static final Item MAGIC_WAND = new MagicWandItem(new Item.Settings());

    @Override
    public void onInitialize() {
        Registry.register(Registries.ITEM, Identifier.of(MOD_ID, "magic_wand"), MAGIC_WAND);
    }
}

// ✅ CORRECT: Client-side rendering (src/client)
@Environment(EnvType.CLIENT)
public class MAMClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // Register item renderers, key bindings, etc.
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> 0x00FF00, MAM.MAGIC_WAND);
    }
}

// ❌ WRONG: Importing client code in server package
// src/main/java/dk/mosberg/MAM.java
import dk.mosberg.client.MAMClient;  // CRASHES on dedicated server!
```

## Data-Driven Content System

**Critical:** This mod is **data-driven** - most content is defined in JSON, not Java code.

### Content Structure

```
src/main/resources/data/mam/
├── spells/              # 13 spell schools (air, arcane, blood, chaos, dark, earth, fire, ice, light, nature, thunder, void, water)
│   ├── fire/
│   │   └── fireball.json    # Example: projectile spell with damage, AoE, status effects
│   └── [school]/...         # Each school has multiple spells
├── rituals/             # 13 ritual categories (ascension, circle, cosmic, elemental, fountain, planar, reality, resurrection, sacrifice, summoning, temporal, transformation, vortex)
│   ├── ascension/
│   │   └── apotheosis_ritual.json  # Example: multi-ring pattern, buffs, requirements
│   └── [category]/...
├── worldgen/            # Ore generation, structures
│   ├── configured_feature/
│   └── placed_feature/
├── tags/                # Item/block tags for gemstones, etc.
└── recipe/              # Crafting recipes
```

### Spell JSON Schema

```json
{
  "id": "mam:fireball",
  "school": "fire",
  "castType": "projectile|aoe|utility|ritual|synergy",
  "manaCost": 15.0,
  "tier": 1,
  "damage": 8.0,
  "statusEffects": [{ "effect": "fire", "duration": 60 }],
  "vfx": { "particleType": "flame", "color": "FF4500" }
}
```

### Ritual JSON Schema

```json
{
  "id": "mam:apotheosis_ritual",
  "category": "ascension",
  "ritual_items": ["mam:ascension_gem", "minecraft:nether_star"],
  "pattern": {
    "type": "ascension_circle",
    "ring1": { "material": "mam:ascension_crystal_block", "radius": 4 }
  },
  "effect": { "type": "ascend", "buffs": ["strength"] }
}
```

### Configuration System

**`mam.properties`** - Runtime configuration (hot-reloadable):

```properties
mana.personal.max_pool=1000
mana.personal.regen_rate=0.5
```

### Adding New Content

**To add a spell/ritual:**

1. Create JSON file in appropriate `data/mam/spells/[school]/` or `data/mam/rituals/[category]/` directory
2. Follow existing schema - no Java code needed
3. Run `./gradlew runClient` to test
4. Assets (textures/models) go in `assets/mam/textures/` or `assets/mam/models/`

**Java registration** happens automatically via data loaders - only write Java for **custom behavior** (e.g., special projectile mechanics).

## Documentation Update Checklist

Before ending ANY task, verify these documentation files are current:

### 1. README.md Update Check

```bash
# Read README.md and check:
- [ ] Version number matches gradle.properties
- [ ] Feature list reflects newly implemented features
- [ ] Installation instructions are current
- [ ] Links to docs/ files are valid
```

### 2. docs/ROADMAP.md Update Check

```bash
# For each completed task:
- [ ] Change status from ❌ NOT STARTED to ✅ COMPLETED
- [ ] Update progress percentage (calculate: completed_tasks / total_tasks * 100)
- [ ] Update "Last Updated" date to current date (format: Month Day, Year)
- [ ] Move completed sections to appropriate phase completion area
```

### 3. docs/IMPLEMENTATION_SUMMARY.md Update Check

```bash
# For each new feature/system:
- [ ] Add section with feature name and description
- [ ] Include technical details (classes, methods, APIs used)
- [ ] Add code examples showing usage
- [ ] Document configuration options
- [ ] Note any limitations or known issues
```

### 4. docs/FEATURES_FUNCTIONS.md Update Check

```bash
# For each new spell/ritual/item/block:
- [ ] Add entry with full specification
- [ ] Include JSON schema example
- [ ] Document all parameters and effects
- [ ] Add usage instructions for players
- [ ] Include gemstone/school bindings if applicable
```

### 5. docs/COMPLETION_REPORT.md Update Check

```bash
# After each work session:
- [ ] Add timestamp entry (## Session: YYYY-MM-DD HH:MM)
- [ ] List completed tasks with ✅ markers
- [ ] Summarize changes (files modified, features added)
- [ ] Note breaking changes or migration requirements
- [ ] Add "Files Changed" section listing all modified files
```

### 6. docs/ASSETS_COMPLETE.md Update Check (if asset work done)

```bash
# For models, textures, sounds added:
- [ ] Update completion status (percentage or checkmarks)
- [ ] List newly created asset files with paths
- [ ] Update category totals (e.g., "Block Models: 15/20")
- [ ] Mark entire categories as complete when done
```

### 7. docs/PLANNED_FEATURES.md Update Check

```bash
# Maintain future feature list:
- [ ] Remove features that are now implemented
- [ ] Add new feature ideas discovered during development
- [ ] Re-prioritize based on dependencies
- [ ] Update effort estimates as understanding improves
```

### Automated Documentation Verification

Use these commands to check documentation currency:

```bash
# Check if ROADMAP.md has today's date
grep "Last Updated:" docs/ROADMAP.md

# Count completed tasks in ROADMAP.md
grep -c "✅ COMPLETED" docs/ROADMAP.md

# List recent changes in COMPLETION_REPORT.md
tail -n 50 docs/COMPLETION_REPORT.md

# Verify README version matches gradle.properties
grep "mod_version" gradle.properties
grep "Version:" README.md
```

### Pre-Task-Completion Documentation Checklist

**MANDATORY:** Before marking any task as complete or ending a work session, verify:

#### Phase 1: Verify Code Changes

```bash
1. Run ./gradlew build -x test (must succeed)
2. Check git status for modified files
3. List all new/modified Java classes
4. List all new/modified JSON data files
5. List all new/modified asset files
```

#### Phase 2: Update Technical Documentation

```markdown
For EACH modified Java file:

- [ ] Add/update entry in docs/IMPLEMENTATION_SUMMARY.md
- [ ] Include class purpose, key methods, dependencies
- [ ] Add code example if public API

For EACH new JSON data file (spell/ritual/worldgen):

- [ ] Add entry to docs/FEATURES_FUNCTIONS.md
- [ ] Include complete JSON schema
- [ ] Document all parameters and their effects
- [ ] Add to appropriate school/category section

For EACH new asset file (model/texture/sound):

- [ ] Update docs/ASSETS_COMPLETE.md or docs/TEXTURES_COMPLETE.md
- [ ] Increment category completion counts
- [ ] List file path and purpose
```

#### Phase 3: Update Project Tracking

```markdown
- [ ] Update docs/ROADMAP.md:

  - Mark completed tasks as ✅
  - Update phase progress percentages
  - Update "Last Updated" to current date (MMMM D, YYYY format)

- [ ] Update docs/COMPLETION_REPORT.md:

  - Add session entry with current timestamp
  - List all completed tasks
  - List all files modified/created
  - Note any breaking changes or migration needs

- [ ] Update docs/PLANNED_FEATURES.md:
  - Remove now-implemented features
  - Add any new ideas discovered during work
```

#### Phase 4: Update User-Facing Documentation

```markdown
- [ ] Check if README.md needs updates:

  - Version number changed? → Update README
  - Major feature added? → Add to Features section
  - New spell school/ritual category? → Update counts/tables
  - Installation steps changed? → Update instructions

- [ ] Verify cross-references:
  - All links to docs/ files work
  - Feature counts match actual implementation
  - Tech stack versions match gradle.properties
```

#### Phase 5: Final Validation

```bash
# Run these checks before ending session:
1. grep "Last Updated:" docs/ROADMAP.md | grep "$(date +%B)"  # Should match current month
2. wc -l docs/COMPLETION_REPORT.md  # Should be longer than before
3. git diff README.md  # Review changes if major features added
4. find docs/ -name "*.md" -mtime +7  # Flag stale docs (>7 days old)
```

### Documentation Update Priority Levels

**CRITICAL (Always required):**

- docs/ROADMAP.md status updates
- docs/COMPLETION_REPORT.md session log
- docs/IMPLEMENTATION_SUMMARY.md for new Java code

**HIGH (Required for user-facing changes):**

- README.md for major features
- docs/FEATURES_FUNCTIONS.md for new spells/rituals/items

**MEDIUM (Required for assets/data):**

- docs/ASSETS_COMPLETE.md for models/textures
- docs/PLANNED_FEATURES.md updates

**LOW (Update when relevant):**

- docs/CODEBASE_ANALYSIS.md
- docs/RESOURCES.md

### Documentation Update Examples

#### Example 1: After Adding New Spell

```markdown
1. Create spell JSON in data/mam/spells/fire/nova_blast.json
2. Test with ./gradlew runClient
3. Update docs/FEATURES_FUNCTIONS.md:
   - Add "Nova Blast" under Fire School spells
   - Include JSON schema, damage values, mana cost
4. Update docs/ROADMAP.md:
   - Mark "Create fire spells" as ✅ if last fire spell
5. Update docs/COMPLETION_REPORT.md:
   - Add session entry with nova_blast.json in Files Changed
```

#### Example 2: After Implementing New System

```markdown
1. Implement ManaRegenerationSystem.java
2. Run ./gradlew build
3. Update docs/IMPLEMENTATION_SUMMARY.md:
   - Add "Mana Regeneration System" section
   - Document tick-based regen, config options
   - Show code example of usage
4. Update README.md:
   - Add "Automatic Mana Regeneration" to Features list
5. Update docs/ROADMAP.md:
   - Mark "Implement mana regeneration" as ✅
   - Update Phase progress percentage
6. Update docs/COMPLETION_REPORT.md:
   - Document the implementation with timestamp
```

## Dependencies & Integration

### Key Dependencies

- **Fabric API** (`fabric-api`) - Required; provides hooks into Minecraft events
- **GSON** (bundled) - JSON serialization for spell/ritual configs
- **JetBrains Annotations** (compile-only) - `@NotNull`, `@Nullable` for better IDE hints

### Working with Yarn Mappings

Common Minecraft class names in Yarn:

- `PlayerEntity` - Player instance
- `ItemStack` - Stack of items
- `BlockState` - Block with properties
- `World` / `ServerWorld` / `ClientWorld` - World instances
- `Identifier` - Namespaced resource location (`mam:magic_wand`)

**Finding mappings:** Use IDE's "Go to Declaration" or check https://fabricmc.net/wiki/

## Testing

**Framework:** JUnit 5 with parameterized tests

```bash
./gradlew test                          # Run all tests
./gradlew test --tests SpellTest       # Run specific test class
./gradlew test --tests "SpellTest.testFireball"  # Run specific method
```

**Test locations:**

- Unit tests: `src/test/java/`
- Test resources: `src/test/resources/`

## Common Pitfalls & Solutions

### 1. ClassNotFoundException on Server

**Symptom:** Mod works in client, crashes on dedicated server
**Cause:** Server code importing client classes
**Fix:**

- Move client-only code to `src/client/`
- Add `@Environment(EnvType.CLIENT)` to client-only classes
- Never import `dk.mosberg.client.*` in `src/main/`

### 2. Yarn Mapping Confusion

**Symptom:** IDE shows `class_1234` instead of readable names
**Cause:** IDE not using Yarn mappings
**Fix:** Run `./gradlew genSources` and refresh Gradle project in IDE

### 3. fabric.mod.json Changes Not Applied

**Symptom:** Mod metadata unchanged after editing `fabric.mod.json`
**Cause:** Template expansion happens during build
**Fix:**

- Edit properties in `gradle.properties` instead
- Run `./gradlew clean build` to regenerate

## Quick Reference

| Task                  | Command                 |
| --------------------- | ----------------------- |
| Build mod JAR         | `./gradlew build`       |
| Test in-game          | `./gradlew runClient`   |
| Test dedicated server | `./gradlew runServer`   |
| Run unit tests        | `./gradlew test`        |
| Generate IDE sources  | `./gradlew genSources`  |
| View build info       | `./gradlew projectInfo` |
| Clean build cache     | `./gradlew clean`       |
| Generate Javadocs     | `./gradlew javadoc`     |

**Output locations:**

- Mod JAR: `build/libs/mam-1.0.0.jar`
- Test reports: `build/reports/tests/test/index.html`
- Javadoc: `build/docs/javadoc/index.html`
- Client logs: `run/logs/latest.log`
- Server logs: `run-server/logs/latest.log`

---

## 📝 Documentation File Reference

| File                               | Purpose                                        | When to Update                                        |
| ---------------------------------- | ---------------------------------------------- | ----------------------------------------------------- |
| **README.md**                      | User-facing project overview                   | Major features, version changes, installation updates |
| **docs/ROADMAP.md**                | Development timeline and task tracking         | Every task completion, progress updates               |
| **docs/IMPLEMENTATION_SUMMARY.md** | Technical documentation of implemented systems | New classes, systems, or major code changes           |
| **docs/FEATURES_FUNCTIONS.md**     | Spell/ritual/item catalog with examples        | New spells, rituals, items, blocks added              |
| **docs/COMPLETION_REPORT.md**      | Session logs with timestamps                   | End of every work session                             |
| **docs/ASSETS_COMPLETE.md**        | Asset inventory and status                     | Asset files created (models, textures, sounds)        |
| **docs/TEXTURES_COMPLETE.md**      | Texture-specific tracking                      | Texture files added or modified                       |
| **docs/PLANNED_FEATURES.md**       | Future feature backlog                         | Features implemented (remove) or new ideas (add)      |
| **docs/CODEBASE_ANALYSIS.md**      | Architecture and code structure analysis       | Major refactors or architecture changes               |
| **docs/RESOURCES.md**              | External resources and references              | New resources discovered or added                     |

### Documentation Update Automation Script Template

```bash
#!/bin/bash
# docs-update.sh - Run before completing any task

echo "📋 Documentation Update Assistant"
echo "================================="

# Check gradle.properties version
VERSION=$(grep "mod_version" gradle.properties | cut -d'=' -f2)
echo "✓ Current version: $VERSION"

# Check ROADMAP.md last updated
ROADMAP_DATE=$(grep "Last Updated:" docs/ROADMAP.md | head -1)
echo "ℹ️ $ROADMAP_DATE"

# Count completed tasks
COMPLETED=$(grep -c "✅" docs/ROADMAP.md || echo "0")
echo "✓ Completed tasks: $COMPLETED"

# Check for stale documentation
echo ""
echo "⚠️  Potentially stale documentation files:"
find docs/ -name "*.md" -mtime +7 -exec echo "  - {}" \;

echo ""
echo "📝 Required Updates Checklist:"
echo "  [ ] docs/ROADMAP.md - Mark task as ✅, update date"
echo "  [ ] docs/IMPLEMENTATION_SUMMARY.md - Document new code"
echo "  [ ] docs/COMPLETION_REPORT.md - Add session entry"
echo "  [ ] README.md - Update if user-facing changes"
```

---
