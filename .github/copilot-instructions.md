# Mana And Magic (MAM) - Minecraft Mod Development Guide

## Project Overview

**MAM** is a Fabric mod for Minecraft 1.21.11. It's a magic mod built with Gradle, Fabric Loom, and Java 21. The codebase is split into **server logic** (`src/main`) and **client logic** (`src/client`), with strict separation enforced by Fabric.

## Architecture & Code Organization

### Environment Split

- **Server code** (`src/main/java/dk/mosberg/`): Core game logic, data structures, items, blocks
- **Client code** (`src/client/java/dk/mosberg/client/`): Rendering, GUIs, particle effects, keybinds

### Entry Points (defined in `fabric.mod.json`)

1. `MAM` - Main mod initializer, server/common logic setup
2. `MAMClient` - Client initializer for rendering/input

**Key pattern**: Never import client code in server packages. Use `@Environment(EnvType.CLIENT)` on client-only code.

## Build System & Workflows

### Build Commands

```bash
./gradlew build              # Full build + tests
./gradlew runClient          # Launch Minecraft client for testing
./gradlew runServer          # Launch dedicated server
./gradlew build -x test      # Build without tests (faster iteration)
./gradlew projectInfo        # Display build configuration
./gradlew clean              # Remove build artifacts and generated resources
```

### Configuration Files

- **gradle.properties**: Version management (Minecraft, Fabric, Java, libraries)
- **build.gradle**: Plugin config, dependencies
- **settings.gradle**: Plugin repository configuration
- **fabric.mod.json**: Mod manifest (template - don't edit directly; changes populate during `processResources`)

### Key Dependencies

- **Fabric API**: Hooks into Minecraft events, utilities
- **Mosberg API**: Custom mod API dependency (Curse Maven)
- **GSON**: JSON serialization (bundled in JAR)
- **SLF4J**: Logging framework

## Testing

- **Framework**: JUnit 5 with parameterized tests
- **Run tests**: `./gradlew test` or through IDE via JUnit integration

## Important Conventions

### Logging

```java
// Use the mod's logger (defined in MAM.java)
MAM.LOGGER.info("Event occurred");
MAM.LOGGER.warn("Deprecated usage");
```

### Mod ID & Constants

- Mod ID: `mam` (lowercase, no underscores) - matches `fabric.mod.json` and manifest metadata
- Use `MAM.MOD_ID` constant everywhere for consistency
- Resource location prefix: `mam:` (for textures, models, etc.)

### Version Management

- **Never edit** `fabric.mod.json` properties directly - they're generated from `gradle.properties`
- Update Minecraft/Fabric versions in `gradle.properties`, then run `./gradlew build`
- Java 21 is required; toolchain auto-configures via `build.gradle`

### Resource Paths

- Client assets: `src/client/resources/assets/mam/`
- Server data: `src/main/resources/`
- Fabric mod config: `src/main/resources/fabric.mod.json` (template only)

## Debugging & Development

### IDE Setup (IntelliJ/Eclipse)

- Fabric Loom generates run configurations automatically (`ideConfigGenerated = true`)
- **Client run**: Launches full Minecraft client with mod loaded
- **Server run**: Dedicated server environment for testing server-only logic
- Breakpoints work in both client and server code

### Typical Development Cycle

1. Make code changes
2. Run `./gradlew runClient` to test
3. Check logs in `run/logs/` if issues occur
4. Use `./gradlew build -x test` for quick validation before committing
5. Run full `./gradlew build` before pushing

## External Integration Points

- **Fabric Events API**: Use event listeners for hooks (entity spawn, block place, etc.)
- **Mosberg API**: Import from `curse.maven` (custom dependency)
- **Maven Publishing**: Configured but disabled; uncomment in `publishing.repositories` to publish

## Common Pitfalls

1. **Importing client code in server code**: Will crash at runtime. Split packages carefully.
2. **Editing `fabric.mod.json` directly**: Changes get overwritten on rebuild; use `gradle.properties`.
3. **Forgetting `@Environment(EnvType.CLIENT)` on client classes**: Can cause classloading errors on server.

## Quick Reference

| Task             | Command                                                     |
| ---------------- | ----------------------------------------------------------- |
| Build everything | `./gradlew build`                                           |
| Test locally     | `./gradlew runClient`                                       |
| Run tests only   | `./gradlew test`                                            |
| Javadoc          | `build/docs/javadoc/index.html` (after `./gradlew javadoc`) |
