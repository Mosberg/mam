package dk.mosberg.ritual;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the effect a ritual produces when completed.
 */
public class RitualEffect {
    private final String type;
    private final List<String> buffs;
    private final int duration;

    public RitualEffect(String type, List<String> buffs, int duration) {
        this.type = type;
        this.buffs = buffs;
        this.duration = duration;
    }

    public String getType() {
        return type;
    }

    public List<String> getBuffs() {
        return buffs;
    }

    public int getDuration() {
        return duration;
    }

    public static RitualEffect fromJson(JsonObject json) {
        String type = json.has("type") ? json.get("type").getAsString() : "buff";
        int duration = json.has("duration") ? json.get("duration").getAsInt() : 60;

        List<String> buffs = new ArrayList<>();
        if (json.has("buffs") && json.get("buffs").isJsonArray()) {
            JsonArray buffsArray = json.getAsJsonArray("buffs");
            for (JsonElement buff : buffsArray) {
                buffs.add(buff.getAsString());
            }
        }

        return new RitualEffect(type, buffs, duration);
    }
}
