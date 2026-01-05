# 🔧 Mana & Magic - Resources & Dependencies

**Version:** 1.0.0
**Last Updated:** January 5, 2026
**Mod ID:** `mam`

---

## 📖 Table of Contents

- [Core Dependencies](#core-dependencies)
- [Development Tools](#development-tools)
- [Build Configuration](#build-configuration)
- [External Libraries](#external-libraries)
- [Testing Frameworks](#testing-frameworks)
- [Documentation Resources](#documentation-resources)
- [Community Resources](#community-resources)
- [Asset Creation Tools](#asset-creation-tools)
- [Performance Profiling](#performance-profiling)
- [Useful Links](#useful-links)

---

## 🎯 Core Dependencies

### Runtime Dependencies

#### Minecraft & Fabric

| Dependency        | Version         | Type    | Purpose                |
| ----------------- | --------------- | ------- | ---------------------- |
| **Minecraft**     | 1.21.11         | Runtime | Base game              |
| **Fabric Loader** | 0.18.4+         | Runtime | Mod loader             |
| **Fabric API**    | 0.140.2+1.21.11 | Runtime | Core modding framework |
| **Java**          | 21              | Runtime | Programming language   |

**Installation:**

```gradle
dependencies {
    minecraft "com.mojang:minecraft:${minecraft_version}"
    mappings "net.fabricmc:yarn:${yarn_mappings}:v2"
    modImplementation "net.fabricmc:fabric-loader:${loader_version}"
    modImplementation "net.fabricmc.fabric-api:fabric-api:${fabric_version}"
}
```

#### Fabric API Modules Used

| Module                       | Purpose            | Version |
| ---------------------------- | ------------------ | ------- |
| `fabric-api-base`            | Core API utilities | 0.140.2 |
| `fabric-lifecycle-events-v1` | Lifecycle hooks    | 0.140.2 |
| `fabric-networking-api-v1`   | Client/Server sync | 0.140.2 |
| `fabric-rendering-v1`        | Custom rendering   | 0.140.2 |
| `fabric-resource-loader-v0`  | Data pack loading  | 0.140.2 |
| `fabric-command-api-v2`      | Custom commands    | 0.140.2 |
| `fabric-key-binding-api-v1`  | Keybinds           | 0.140.2 |
| `fabric-entity-events-v1`    | Entity hooks       | 0.140.2 |
| `fabric-item-api-v1`         | Item registration  | 0.140.2 |
| `fabric-block-api-v1`        | Block registration | 0.140.2 |

### Build Dependencies

#### Gradle & Plugins

| Dependency        | Version | Purpose                      |
| ----------------- | ------- | ---------------------------- |
| **Gradle**        | 8.5+    | Build automation             |
| **Fabric Loom**   | 1.14.10 | Fabric-specific build plugin |
| **Shadow Plugin** | 8.1.1   | JAR shading (if needed)      |

**Gradle Wrapper:**

```bash
./gradlew wrapper --gradle-version=8.5
```

---

## 🛠️ Development Tools

### Required Tools

#### 1. Java Development Kit (JDK)

**Recommended:** [Eclipse Adoptium OpenJDK 21](https://adoptium.net/)

**Installation:**

```bash
# macOS (Homebrew)
brew install openjdk@21

# Linux (apt)
sudo apt install openjdk-21-jdk

# Windows
# Download installer from https://adoptium.net/
```

**Verify Installation:**

```bash
java -version
# Output: openjdk version "21.0.1" 2023-10-17 LTS

javac -version
# Output: javac 21.0.1
```

#### 2. Integrated Development Environment (IDE)

**Option A: IntelliJ IDEA (Recommended)**

- **Edition:** Community (Free) or Ultimate
- **Version:** 2024.3+
- **Download:** [https://www.jetbrains.com/idea/](https://www.jetbrains.com/idea/)

**Required Plugins:**

- Minecraft Development
- Fabric Development
- Rainbow Brackets (optional)
- GitToolBox (optional)

**Setup:**

```bash
./gradlew idea
./gradlew genSources

# Open IntelliJ → File → Open → Select build.gradle
```

**Option B: Visual Studio Code**

- **Version:** 1.85+
- **Download:** [https://code.visualstudio.com/](https://code.visualstudio.com/)

**Required Extensions:**

- Extension Pack for Java
- Gradle for Java
- Language Support for Java(TM)
- Debugger for Java

**Setup:**

```bash
./gradlew vscode
./gradlew genSources

# Open VS Code → File → Open Folder → Select project root
```

**Option C: Eclipse**

- **Version:** 2024-03+
- **Download:** [https://www.eclipse.org/downloads/](https://www.eclipse.org/downloads/)

**Setup:**

```bash
./gradlew eclipse
./gradlew genSources

# Open Eclipse → File → Import → Existing Gradle Project
```

#### 3. Version Control

**Git**

- **Version:** 2.40+
- **Download:** [https://git-scm.com/](https://git-scm.com/)

**Configuration:**

```bash
git config --global user.name "Your Name"
git config --global user.email "your.email@example.com"
git config --global core.autocrlf input  # Linux/Mac
git config --global core.autocrlf true   # Windows
```

**GitHub CLI (Optional):**

```bash
# Installation
brew install gh  # macOS
winget install GitHub.cli  # Windows

# Authentication
gh auth login
```

### Development Utilities

#### 4. Blockbench

**Purpose:** 3D model creation for entities, items, blocks

- **Version:** 4.9+
- **Download:** [https://www.blockbench.net/](https://www.blockbench.net/)
- **Format:** Bedrock/Java Block/Item models, Entity models

**Plugins:**

- Fabric Model Exporter
- Texture Packer
- Animation Tools

#### 5. NBT Studio

**Purpose:** NBT data structure editing and viewing

- **Download:** [https://github.com/tryashtar/nbt-studio](https://github.com/tryashtar/nbt-studio)
- **Use Cases:**
  - Debug player data
  - View mana pool NBT
  - Inspect ritual progress

#### 6. MCreator (Optional - for rapid prototyping)

**Purpose:** Visual mod development tool

- **Version:** 2024.1+
- **Download:** [https://mcreator.net/](https://mcreator.net/)
- **Note:** Export to Fabric, then refine in IDE

---

## 📦 External Libraries

### Standard Libraries

#### 1. Google Gson

**Purpose:** JSON serialization/deserialization

- **Version:** 2.13.2
- **Maven:** `com.google.code.gson:gson:2.13.2`
- **License:** Apache 2.0

**Usage:**

```java
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

Gson gson = new GsonBuilder()
    .setPrettyPrinting()
    .create();

// Serialize
String json = gson.toJson(manaPool);

// Deserialize
ManaPool pool = gson.fromJson(json, ManaPool.class);
```

#### 2. SLF4J (Simple Logging Facade)

**Purpose:** Logging framework

- **Version:** 2.0.17
- **Maven:** `org.slf4j:slf4j-api:2.0.17`
- **License:** MIT

**Usage:**

```java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MAM {
    public static final Logger LOGGER = LoggerFactory.getLogger("mam");

    public void init() {
        LOGGER.info("Initializing Mana & Magic");
        LOGGER.debug("Debug info");
        LOGGER.warn("Warning message");
        LOGGER.error("Error occurred", exception);
    }
}
```

#### 3. JetBrains Annotations

**Purpose:** Code annotations for nullability, contracts

- **Version:** 26.0.2
- **Maven:** `org.jetbrains:annotations:26.0.2`
- **License:** Apache 2.0

**Usage:**

```java
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ManaPool {
    public void consumeMana(@NotNull ServerPlayerEntity player, double amount) {
        // player cannot be null
    }

    @Nullable
    public ManaComponent getComponent(ServerPlayerEntity player) {
        // may return null
    }
}
```

### Optional Libraries

#### 4. Apache Commons Math (if needed for advanced calculations)

**Purpose:** Advanced mathematical functions

- **Version:** 3.6.1
- **Maven:** `org.apache.commons:commons-math3:3.6.1`
- **License:** Apache 2.0

---

## 🧪 Testing Frameworks

### JUnit 5

**Purpose:** Unit testing framework

- **Version:** 5.11.0
- **Maven:** `org.junit.jupiter:junit-jupiter:5.11.0`
- **License:** EPL 2.0

**Configuration:**

```gradle
dependencies {
    testImplementation 'org.junit.jupiter:junit-jupiter:5.11.0'
    testRuntimeOnly 'org.junit.platform:junit-platform-launcher'
}

test {
    useJUnitPlatform()
    testLogging {
        events "passed", "skipped", "failed"
    }
}
```

**Example Test:**

```java
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ManaPoolTest {

    @Test
    void testManaConsumption() {
        ManaPool pool = new ManaPool(250, 500, 1000);
        pool.setPrimaryMana(100);

        assertTrue(pool.consumeMana(50));
        assertEquals(50, pool.getPrimaryMana());
    }

    @Test
    void testCascadingConsumption() {
        ManaPool pool = new ManaPool(250, 500, 1000);
        pool.setPrimaryMana(100);
        pool.setSecondaryMana(200);

        assertTrue(pool.consumeMana(250));
        assertEquals(0, pool.getPrimaryMana());
        assertEquals(50, pool.getSecondaryMana());
    }
}
```

### Mockito (Optional)

**Purpose:** Mocking framework for tests

- **Version:** 5.8.0
- **Maven:** `org.mockito:mockito-core:5.8.0`

---

## 📚 Documentation Resources

### Official Documentation

#### Fabric Documentation

| Resource            | URL                                                                  | Description                   |
| ------------------- | -------------------------------------------------------------------- | ----------------------------- |
| **Fabric Wiki**     | [https://fabricmc.net/wiki/](https://fabricmc.net/wiki/)             | Official Fabric documentation |
| **Fabric API Docs** | [https://maven.fabricmc.net/docs/](https://maven.fabricmc.net/docs/) | API JavaDocs                  |
| **Yarn Mappings**   | [https://github.com/FabricMC/yarn](https://github.com/FabricMC/yarn) | Deobfuscation mappings        |

#### Minecraft Documentation

| Resource             | URL                                                                      | Description              |
| -------------------- | ------------------------------------------------------------------------ | ------------------------ |
| **Minecraft Wiki**   | [https://minecraft.wiki/](https://minecraft.wiki/)                       | Game mechanics reference |
| **Data Pack Format** | [https://minecraft.wiki/w/Data_pack](https://minecraft.wiki/w/Data_pack) | JSON format specs        |
| **Commands**         | [https://minecraft.wiki/w/Commands](https://minecraft.wiki/w/Commands)   | Command syntax           |

#### Java Documentation

| Resource               | URL                                                                                      | Description            |
| ---------------------- | ---------------------------------------------------------------------------------------- | ---------------------- |
| **Java 21 Docs**       | [https://docs.oracle.com/en/java/javase/21/](https://docs.oracle.com/en/java/javase/21/) | Official Java API      |
| **Java Language Spec** | [https://docs.oracle.com/javase/specs/](https://docs.oracle.com/javase/specs/)           | Language specification |

### Community Resources

#### Tutorial Sites

| Resource                   | URL                                                                        | Focus             |
| -------------------------- | -------------------------------------------------------------------------- | ----------------- |
| **Fabric Mod Development** | [https://fabricmc.net/develop/](https://fabricmc.net/develop/)             | Getting started   |
| **Modding by Kaupenjoe**   | [https://www.youtube.com/c/Kaupenjoe](https://www.youtube.com/c/Kaupenjoe) | Video tutorials   |
| **Fabric Discord**         | [https://discord.gg/v6v4pMv](https://discord.gg/v6v4pMv)                   | Community support |

#### Example Mods (Reference)

| Mod                    | GitHub                                                                        | Focus Area      |
| ---------------------- | ----------------------------------------------------------------------------- | --------------- |
| **Fabric Example Mod** | [FabricMC/fabric-example-mod](https://github.com/FabricMC/fabric-example-mod) | Basic structure |
| **Botania**            | [VazkiiMods/Botania](https://github.com/VazkiiMods/Botania)                   | Magic system    |
| **Psi**                | [VazkiiMods/Psi](https://github.com/VazkiiMods/Psi)                           | Spell system    |
| **Astromine**          | [Astromine-Team/Astromine](https://github.com/Astromine-Team/Astromine)       | UI/HUD          |

---

## 🎨 Asset Creation Tools

### Texture Creation

#### 1. Aseprite

**Purpose:** Pixel art and animation

- **Cost:** $19.99 (or compile from source)
- **Download:** [https://www.aseprite.org/](https://www.aseprite.org/)
- **Use Cases:**
  - Item textures
  - Block textures
  - GUI elements
  - Particle sprites

**Free Alternatives:**

- **LibreSprite** (fork): [https://libresprite.github.io/](https://libresprite.github.io/)
- **Piskel** (online): [https://www.piskelapp.com/](https://www.piskelapp.com/)

#### 2. GIMP

**Purpose:** Advanced image editing

- **Cost:** Free (GPL)
- **Download:** [https://www.gimp.org/](https://www.gimp.org/)
- **Use Cases:**
  - Complex textures
  - Texture atlases
  - Post-processing

#### 3. Paint.NET

**Purpose:** Simple image editing (Windows)

- **Cost:** Free
- **Download:** [https://www.getpaint.net/](https://www.getpaint.net/)

### Sound Creation

#### 1. Audacity

**Purpose:** Audio editing

- **Cost:** Free (GPL)
- **Download:** [https://www.audacityteam.org/](https://www.audacityteam.org/)
- **Use Cases:**
  - Sound effects
  - Audio trimming
  - Format conversion

#### 2. Freesound

**Purpose:** Sound effects library

- **URL:** [https://freesound.org/](https://freesound.org/)
- **License:** Various (CC, Public Domain)
- **Use Cases:**
  - Spell sounds
  - Ambient effects
  - UI sounds

### Model Creation

#### 1. Blockbench (mentioned above)

**Best for:**

- Block/item models
- Simple entity models
- Minecraft-style assets

#### 2. Blender

**Purpose:** Advanced 3D modeling

- **Cost:** Free (GPL)
- **Download:** [https://www.blender.org/](https://www.blender.org/)
- **Use Cases:**
  - Complex entity models
  - Promotional renders
  - Animation

---

## 🔍 Performance Profiling

### Profiling Tools

#### 1. VisualVM

**Purpose:** Java application profiling

- **Cost:** Free
- **Download:** [https://visualvm.github.io/](https://visualvm.github.io/)
- **Features:**
  - CPU profiling
  - Memory profiling
  - Thread analysis

**Usage:**

```bash
# Launch Minecraft with profiling
./gradlew runClient -Dvisualvm.display.name=MAM

# Attach VisualVM to process
visualvm
```

#### 2. JProfiler

**Purpose:** Advanced Java profiler

- **Cost:** Commercial (trial available)
- **Download:** [https://www.ej-technologies.com/products/jprofiler/overview.html](https://www.ej-technologies.com/products/jprofiler/overview.html)

#### 3. Spark

**Purpose:** In-game Minecraft profiler

- **Download:** [https://spark.lucko.me/](https://spark.lucko.me/)
- **Installation:** Mod for client/server
- **Features:**
  - Tick profiling
  - Memory analysis
  - Heap dumps

### Performance Testing

#### MineBench (Custom)

**Purpose:** Automated performance benchmarks

**Test Suite:**

```bash
# Run performance tests
./gradlew runPerformanceTests

# Tests:
# - Mana regeneration (10,000 ticks)
# - Spell casting (1,000 casts)
# - Ritual validation (100 patterns)
# - NBT serialization (10,000 saves)
# - Particle rendering (1,000 particles)
```

---

## 🌐 Useful Links

### Project Links

| Resource              | URL                                                                                      |
| --------------------- | ---------------------------------------------------------------------------------------- |
| **Homepage**          | [https://mosberg.github.io/mam](https://mosberg.github.io/mam)                           |
| **GitHub Repository** | [https://github.com/mosberg/mam](https://github.com/mosberg/mam)                         |
| **Issue Tracker**     | [https://github.com/mosberg/mam/issues](https://github.com/mosberg/mam/issues)           |
| **Discussions**       | [https://github.com/mosberg/mam/discussions](https://github.com/mosberg/mam/discussions) |
| **Wiki**              | [https://github.com/mosberg/mam/wiki](https://github.com/mosberg/mam/wiki)               |

### Distribution Platforms

| Platform            | URL                                                                                                  | Description          |
| ------------------- | ---------------------------------------------------------------------------------------------------- | -------------------- |
| **Modrinth**        | [https://modrinth.com/mod/mam](https://modrinth.com/mod/mam)                                         | Primary distribution |
| **CurseForge**      | [https://www.curseforge.com/minecraft/mc-mods/mam](https://www.curseforge.com/minecraft/mc-mods/mam) | Alternative platform |
| **GitHub Releases** | [https://github.com/mosberg/mam/releases](https://github.com/mosberg/mam/releases)                   | Direct downloads     |

### Development Resources

| Resource               | URL                                                                  | Purpose               |
| ---------------------- | -------------------------------------------------------------------- | --------------------- |
| **Fabric Versions**    | [https://fabricmc.net/develop/](https://fabricmc.net/develop/)       | Check latest versions |
| **Minecraft Wiki**     | [https://minecraft.wiki/](https://minecraft.wiki/)                   | Game mechanics        |
| **Mojang Bug Tracker** | [https://bugs.mojang.com/](https://bugs.mojang.com/)                 | Report vanilla bugs   |
| **Fabric API Javadoc** | [https://maven.fabricmc.net/docs/](https://maven.fabricmc.net/docs/) | API documentation     |

### Asset Resources

| Resource        | URL                                                                        | License                |
| --------------- | -------------------------------------------------------------------------- | ---------------------- |
| **OpenGameArt** | [https://opengameart.org/](https://opengameart.org/)                       | CC, GPL, Public Domain |
| **Freesound**   | [https://freesound.org/](https://freesound.org/)                           | CC, Public Domain      |
| **Pixabay**     | [https://pixabay.com/](https://pixabay.com/)                               | Pixabay License        |
| **CC Search**   | [https://search.creativecommons.org/](https://search.creativecommons.org/) | Various CC             |

---

## 📊 Build Configuration Reference

### gradle.properties Explained

```properties
# ═══════════════════════════════════════════════════════════════════════════════
# JVM Configuration
# ═══════════════════════════════════════════════════════════════════════════════

# Allocate 4GB heap memory for Gradle daemon
# G1GC: Garbage collector optimized for large heaps (>2GB)
# ParallelRefProcEnabled: Parallel reference processing during GC
# MaxGCPauseMillis: Target maximum GC pause time (200ms)
org.gradle.jvmargs=-Xmx4G -XX:+UseG1GC -XX:+ParallelRefProcEnabled -XX:MaxGCPauseMillis=200

# ═══════════════════════════════════════════════════════════════════════════════
# Build Optimizations
# ═══════════════════════════════════════════════════════════════════════════════

# Execute independent tasks in parallel (faster builds)
org.gradle.parallel=true

# Cache build outputs locally and remotely (speeds up clean builds)
org.gradle.caching=true

# Cache task configuration (experimental, ~20% faster subsequent builds)
org.gradle.configuration-cache=true

# ═══════════════════════════════════════════════════════════════════════════════
# Mod Metadata
# ═══════════════════════════════════════════════════════════════════════════════

# Maven group ID (reverse domain notation)
maven_group=dk.mosberg

# Base name for generated artifacts (JAR files)
archives_base_name=mam

# Mod identifier (lowercase, no spaces or underscores)
# Used in code: MAM.MOD_ID
mod_id=mam

# Mod version (Semantic Versioning: MAJOR.MINOR.PATCH)
mod_version=1.0.0

# Display name
mod_name=Mana And Magic

# Short description (1-2 sentences)
mod_description=Mana and Magic is a data-driven, extensible Minecraft magic mod for Fabric.

# Author name
mod_author=Mosberg

# Project URLs
mod_homepage=https://mosberg.github.io/mam
mod_sources=https://github.com/mosberg/mam
mod_issues=https://github.com/mosberg/mam/issues

# License identifier (SPDX format)
mod_license=MIT

# ═══════════════════════════════════════════════════════════════════════════════
# Minecraft & Fabric Versions
# ═══════════════════════════════════════════════════════════════════════════════

# Minecraft version (check https://fabricmc.net/develop/)
minecraft_version=1.21.11

# Fabric Loader version (minimum required)
loader_version=0.18.4

# Yarn mappings version (deobfuscation)
# Format: <minecraft_version>+build.<build_number>
yarn_mappings=1.21.11+build.3

# Fabric Loom version (Gradle plugin)
loom_version=1.14.10

# Fabric API version (core mod API)
# Format: <api_version>+<minecraft_version>
fabric_version=0.140.2+1.21.11

# Java version (language level)
java_version=21

# ═══════════════════════════════════════════════════════════════════════════════
# Library Dependencies
# ═══════════════════════════════════════════════════════════════════════════════

# Gson (JSON serialization)
gson_version=2.13.2

# SLF4J (logging)
slf4j_version=2.0.17

# JetBrains Annotations (nullability)
annotations_version=26.0.2

# ═══════════════════════════════════════════════════════════════════════════════
# Testing Framework
# ═══════════════════════════════════════════════════════════════════════════════

# JUnit 5 (unit testing)
junit_version=5.11.0
```

### build.gradle Key Sections

```gradle
plugins {
    id 'fabric-loom' version "${loom_version}"
    id 'maven-publish'
}

// Version info
version = project.mod_version
group = project.maven_group

repositories {
    maven { url 'https://maven.fabricmc.net/' }
    maven { url 'https://maven.shedaniel.me/' }
    mavenCentral()
}

dependencies {
    // Minecraft
    minecraft "com.mojang:minecraft:${minecraft_version}"
    mappings "net.fabricmc:yarn:${yarn_mappings}:v2"

    // Fabric
    modImplementation "net.fabricmc:fabric-loader:${loader_version}"
    modImplementation "net.fabricmc.fabric-api:fabric-api:${fabric_version}"

    // Libraries
    implementation "com.google.code.gson:gson:${gson_version}"
    implementation "org.slf4j:slf4j-api:${slf4j_version}"
    compileOnly "org.jetbrains:annotations:${annotations_version}"

    // Testing
    testImplementation "org.junit.jupiter:junit-jupiter:${junit_version}"
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

tasks.withType(JavaCompile).configureEach {
    options.encoding = 'UTF-8'
    options.release = 21
}

jar {
    from("LICENSE") {
        rename { "${it}_${project.archivesBaseName}"}
    }
}
```

---

## 🔧 Environment Setup Checklist

### Development Environment

- [ ] **Java 21 JDK installed**
  - Verify: `java -version` shows 21.x.x
- [ ] **Git installed and configured**
  - Verify: `git --version`
- [ ] **IDE installed** (IntelliJ/VSCode/Eclipse)
- [ ] **Gradle wrapper present**
  - Verify: `./gradlew --version`
- [ ] **Project dependencies downloaded**
  - Run: `./gradlew build`
- [ ] **IDE sources generated**
  - Run: `./gradlew genSources`
- [ ] **Development client launches**
  - Run: `./gradlew runClient`

### Asset Creation Environment

- [ ] **Blockbench installed** (for models)
- [ ] **Image editor installed** (Aseprite/GIMP)
- [ ] **Audio editor installed** (Audacity)
- [ ] **NBT editor installed** (NBT Studio)

### Version Control

- [ ] **Repository cloned**
- [ ] **`.gitignore` configured**
- [ ] **Remote origin set**
- [ ] **Git LFS installed** (if using large files)

---
