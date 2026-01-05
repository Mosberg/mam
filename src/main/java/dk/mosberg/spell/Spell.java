package dk.mosberg.spell;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a spell with all its properties loaded from JSON.
 */
public class Spell {
    private final Identifier id;
    private final String name;
    private final SpellSchool school;
    private final String description;
    private final CastType castType;
    private final double manaCost;
    private final double castTime;
    private final double cooldown;
    private final int tier;
    private final int requiredLevel;
    private final double damage;
    private final double range;
    private final double projectileSpeed;
    private final double aoeRadius;
    private final List<StatusEffectData> statusEffects;
    private final Map<String, Object> customData;
    private final String sound;
    private final VfxData vfx;

    private Spell(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.school = builder.school;
        this.description = builder.description;
        this.castType = builder.castType;
        this.manaCost = builder.manaCost;
        this.castTime = builder.castTime;
        this.cooldown = builder.cooldown;
        this.tier = builder.tier;
        this.requiredLevel = builder.requiredLevel;
        this.damage = builder.damage;
        this.range = builder.range;
        this.projectileSpeed = builder.projectileSpeed;
        this.aoeRadius = builder.aoeRadius;
        this.statusEffects = builder.statusEffects;
        this.customData = builder.customData;
        this.sound = builder.sound;
        this.vfx = builder.vfx;
    }

    // Getters
    public Identifier getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public SpellSchool getSchool() {
        return school;
    }

    public String getDescription() {
        return description;
    }

    public CastType getCastType() {
        return castType;
    }

    public double getManaCost() {
        return manaCost;
    }

    public double getCastTime() {
        return castTime;
    }

    public double getCooldown() {
        return cooldown;
    }

    public int getTier() {
        return tier;
    }

    public int getRequiredLevel() {
        return requiredLevel;
    }

    public double getDamage() {
        return damage;
    }

    public double getRange() {
        return range;
    }

    public double getProjectileSpeed() {
        return projectileSpeed;
    }

    public double getAoeRadius() {
        return aoeRadius;
    }

    public List<StatusEffectData> getStatusEffects() {
        return statusEffects;
    }

    public Map<String, Object> getCustomData() {
        return customData;
    }

    public String getSound() {
        return sound;
    }

    public VfxData getVfx() {
        return vfx;
    }

    public static Spell fromJson(JsonObject json) {
        Builder builder = new Builder();

        // Required fields
        builder.id = Identifier.of(json.get("id").getAsString());
        builder.name = json.has("name") ? json.get("name").getAsString() : builder.id.getPath();
        builder.school = SpellSchool.fromId(json.get("school").getAsString());

        // Optional fields with defaults
        builder.description = json.has("description") ? json.get("description").getAsString() : "";
        builder.castType =
                json.has("castType") ? CastType.fromId(json.get("castType").getAsString())
                        : CastType.UTILITY;
        builder.manaCost = json.has("manaCost") ? json.get("manaCost").getAsDouble() : 10.0;
        builder.castTime = json.has("castTime") ? json.get("castTime").getAsDouble() : 0.0;
        builder.cooldown = json.has("cooldown") ? json.get("cooldown").getAsDouble() : 0.0;
        builder.tier = json.has("tier") ? json.get("tier").getAsInt() : 1;
        builder.requiredLevel =
                json.has("requiredLevel") ? json.get("requiredLevel").getAsInt() : 1;
        builder.damage = json.has("damage") ? json.get("damage").getAsDouble() : 0.0;
        builder.range = json.has("range") ? json.get("range").getAsDouble() : 10.0;
        builder.projectileSpeed =
                json.has("projectileSpeed") ? json.get("projectileSpeed").getAsDouble() : 1.0;
        builder.aoeRadius = json.has("aoeRadius") ? json.get("aoeRadius").getAsDouble() : 0.0;
        builder.sound = json.has("sound") ? json.get("sound").getAsString() : "";

        // Status effects
        if (json.has("statusEffects") && json.get("statusEffects").isJsonArray()) {
            JsonArray effects = json.getAsJsonArray("statusEffects");
            for (JsonElement effect : effects) {
                builder.statusEffects.add(StatusEffectData.fromJson(effect.getAsJsonObject()));
            }
        }

        // Custom data
        if (json.has("customData") && json.get("customData").isJsonObject()) {
            JsonObject customData = json.getAsJsonObject("customData");
            for (Map.Entry<String, JsonElement> entry : customData.entrySet()) {
                JsonElement value = entry.getValue();
                if (value.isJsonPrimitive()) {
                    if (value.getAsJsonPrimitive().isBoolean()) {
                        builder.customData.put(entry.getKey(), value.getAsBoolean());
                    } else if (value.getAsJsonPrimitive().isNumber()) {
                        builder.customData.put(entry.getKey(), value.getAsNumber());
                    } else {
                        builder.customData.put(entry.getKey(), value.getAsString());
                    }
                }
            }
        }

        // VFX
        if (json.has("vfx") && json.get("vfx").isJsonObject()) {
            builder.vfx = VfxData.fromJson(json.getAsJsonObject("vfx"));
        }

        return builder.build();
    }

    private static class Builder {
        private Identifier id;
        private String name;
        private SpellSchool school;
        private String description = "";
        private CastType castType = CastType.UTILITY;
        private double manaCost = 10.0;
        private double castTime = 0.0;
        private double cooldown = 0.0;
        private int tier = 1;
        private int requiredLevel = 1;
        private double damage = 0.0;
        private double range = 10.0;
        private double projectileSpeed = 1.0;
        private double aoeRadius = 0.0;
        private List<StatusEffectData> statusEffects = new ArrayList<>();
        private Map<String, Object> customData = new HashMap<>();
        private String sound = "";
        private VfxData vfx = new VfxData("flame", 10, "FFFFFF");

        private Spell build() {
            return new Spell(this);
        }
    }
}
