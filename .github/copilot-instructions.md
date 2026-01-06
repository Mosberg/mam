# Mana And Magic (MAM) - Fabric Mod Development Guide

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

## Dependencies & Integration

### Key Dependencies

- **Fabric API** (`fabric-api`) - Required; provides hooks into Minecraft events
- **Modding Helper API** (`dk.mosberg:moddinghelperapi:1.0.0`) - Custom utility library (local Maven dependency)
- **GSON** (bundled) - JSON serialization for spell/ritual configs
- **JetBrains Annotations** (compile-only) - `@NotNull`, `@Nullable` for better IDE hints

### Modding Helper API Setup

**Local Maven dependency** - not from CurseMaven:

```gradle
modImplementation "dk.mosberg:moddinghelperapi:1.0.0"
```

If missing, check `mavenLocal()` repository or install locally:

```bash
cd /path/to/moddinghelperapi
./gradlew publishToMavenLocal
```

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

### 4. Modding Helper API Not Found

**Symptom:** `Could not resolve dk.mosberg:moddinghelperapi:1.0.0`
**Cause:** Dependency not installed in local Maven repository
**Fix:**

1. Ensure moddinghelperapi is published to local Maven:

```bash
cd /path/to/moddinghelperapi
./gradlew publishToMavenLocal
```

2. Verify `mavenLocal()` is in repositories section of build.gradle

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
