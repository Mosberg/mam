package dk.mosberg.ritual;

import com.google.gson.JsonObject;

/**
 * Represents a ring in a ritual pattern.
 */
public class RitualRing {
    private final String material;
    private final int count;
    private final int radius;
    private final int height;

    public RitualRing(String material, int count, int radius, int height) {
        this.material = material;
        this.count = count;
        this.radius = radius;
        this.height = height;
    }

    public String getMaterial() {
        return material;
    }

    public int getCount() {
        return count;
    }

    public int getRadius() {
        return radius;
    }

    public int getHeight() {
        return height;
    }

    public static RitualRing fromJson(JsonObject json) {
        String material = json.get("material").getAsString();
        int count = json.has("count") ? json.get("count").getAsInt() : 8;
        int radius = json.has("radius") ? json.get("radius").getAsInt() : 3;
        int height = json.has("height") ? json.get("height").getAsInt() : 0;
        return new RitualRing(material, count, radius, height);
    }
}
