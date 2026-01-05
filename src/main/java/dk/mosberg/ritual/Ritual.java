package dk.mosberg.ritual;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a ritual with all its properties loaded from JSON.
 */
public class Ritual {
    private final Identifier id;
    private final String name;
    private final RitualCategory category;
    private final String description;
    private final List<String> ritualItems;
    private final double manaCost;
    private final int durationSeconds;
    private final int cooldownSeconds;
    private final int levelRequirement;
    private final RitualPattern pattern;
    private final RitualEffect effect;

    private Ritual(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.category = builder.category;
        this.description = builder.description;
        this.ritualItems = builder.ritualItems;
        this.manaCost = builder.manaCost;
        this.durationSeconds = builder.durationSeconds;
        this.cooldownSeconds = builder.cooldownSeconds;
        this.levelRequirement = builder.levelRequirement;
        this.pattern = builder.pattern;
        this.effect = builder.effect;
    }

    // Getters
    public Identifier getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public RitualCategory getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getRitualItems() {
        return ritualItems;
    }

    public double getManaCost() {
        return manaCost;
    }

    public int getDurationSeconds() {
        return durationSeconds;
    }

    public int getCooldownSeconds() {
        return cooldownSeconds;
    }

    public int getLevelRequirement() {
        return levelRequirement;
    }

    public RitualPattern getPattern() {
        return pattern;
    }

    public RitualEffect getEffect() {
        return effect;
    }

    public static Ritual fromJson(JsonObject json) {
        Builder builder = new Builder();

        // Required fields
        builder.id = Identifier.of(json.get("id").getAsString());
        builder.name = json.has("name") ? json.get("name").getAsString() : builder.id.getPath();
        builder.category = RitualCategory.fromId(json.get("category").getAsString());

        // Optional fields
        builder.description = json.has("description") ? json.get("description").getAsString() : "";
        builder.manaCost = json.has("mana_cost") ? json.get("mana_cost").getAsDouble() : 100.0;
        builder.durationSeconds =
                json.has("duration_seconds") ? json.get("duration_seconds").getAsInt() : 60;
        builder.cooldownSeconds =
                json.has("cooldown_seconds") ? json.get("cooldown_seconds").getAsInt() : 300;
        builder.levelRequirement =
                json.has("level_requirement") ? json.get("level_requirement").getAsInt() : 1;

        // Ritual items
        if (json.has("ritual_items") && json.get("ritual_items").isJsonArray()) {
            JsonArray items = json.getAsJsonArray("ritual_items");
            for (JsonElement item : items) {
                builder.ritualItems.add(item.getAsString());
            }
        }

        // Pattern
        if (json.has("pattern") && json.get("pattern").isJsonObject()) {
            builder.pattern = RitualPattern.fromJson(json.getAsJsonObject("pattern"));
        }

        // Effect
        if (json.has("effect") && json.get("effect").isJsonObject()) {
            builder.effect = RitualEffect.fromJson(json.getAsJsonObject("effect"));
        }

        return builder.build();
    }

    private static class Builder {
        private Identifier id;
        private String name;
        private RitualCategory category;
        private String description = "";
        private List<String> ritualItems = new ArrayList<>();
        private double manaCost = 100.0;
        private int durationSeconds = 60;
        private int cooldownSeconds = 300;
        private int levelRequirement = 1;
        private RitualPattern pattern;
        private RitualEffect effect;

        private Ritual build() {
            return new Ritual(this);
        }
    }
}
