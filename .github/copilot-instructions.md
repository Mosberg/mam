# Mana And Magic (MAM) - Fabric Mod Development Guide

## Project Overview

**Mana And Magic** is a data-driven, extensible magic mod for Minecraft 1.21.11 using Fabric Loader. Built with Java 21, Yarn mappings, and split source sets for strict client/server separation.

**Tech Stack:**

- Minecraft 1.21.11 + Fabric Loader 0.18.4
- Yarn mappings `1.21.11+build.3` (intermediary → named)
- Fabric Loom 1.14.10 with split source sets
- Java 21 toolchain with G1GC optimization

## Architecture & Source Organization

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
- Modding Helper API dependency from CurseMaven: `modding-helper-api`
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

## Dependencies & Integration

### Key Dependencies

- **Fabric API** (`fabric-api`) - Required; provides hooks into Minecraft events
- **Modding Helper API** (`modding-helper-api`) - Custom utility library from CurseMaven
- **GSON** (bundled) - JSON serialization for spell configs
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

### 4. Modding Helper API Not Found

**Symptom:** `Could not resolve curse.maven:modding-helper-api`
**Cause:** CurseMaven repository issue or version mismatch
**Fix:** Check CurseForge for latest file ID and update in `build.gradle`

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
