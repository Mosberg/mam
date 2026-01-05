package dk.mosberg.spell;

import com.google.gson.JsonObject;

/**
 * Represents a status effect that can be applied by a spell.
 */
public class StatusEffectData {
    private final String effect;
    private final int duration;
    private final int amplifier;

    public StatusEffectData(String effect, int duration, int amplifier) {
        this.effect = effect;
        this.duration = duration;
        this.amplifier = amplifier;
    }

    public String getEffect() {
        return effect;
    }

    public int getDuration() {
        return duration;
    }

    public int getAmplifier() {
        return amplifier;
    }

    public static StatusEffectData fromJson(JsonObject json) {
        String effect = json.get("effect").getAsString();
        int duration = json.has("duration") ? json.get("duration").getAsInt() : 60;
        int amplifier = json.has("amplifier") ? json.get("amplifier").getAsInt() : 0;
        return new StatusEffectData(effect, duration, amplifier);
    }
}
