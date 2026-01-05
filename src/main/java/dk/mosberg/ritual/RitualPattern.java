package dk.mosberg.ritual;

import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the physical pattern a ritual requires.
 */
public class RitualPattern {
    private final String type;
    private final String centerBlock;
    private final List<RitualRing> rings;

    public RitualPattern(String type, String centerBlock, List<RitualRing> rings) {
        this.type = type;
        this.centerBlock = centerBlock;
        this.rings = rings;
    }

    public String getType() {
        return type;
    }

    public String getCenterBlock() {
        return centerBlock;
    }

    public List<RitualRing> getRings() {
        return rings;
    }

    public static RitualPattern fromJson(JsonObject json) {
        String type = json.has("type") ? json.get("type").getAsString() : "circle";
        String centerBlock = json.has("center_block") ? json.get("center_block").getAsString()
                : "minecraft:gold_block";

        List<RitualRing> rings = new ArrayList<>();
        // Parse ring1, ring2, ring3, etc.
        for (int i = 1; i <= 5; i++) {
            String ringKey = "ring" + i;
            if (json.has(ringKey) && json.get(ringKey).isJsonObject()) {
                rings.add(RitualRing.fromJson(json.getAsJsonObject(ringKey)));
            }
        }

        return new RitualPattern(type, centerBlock, rings);
    }
}
