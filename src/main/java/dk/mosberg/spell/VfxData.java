package dk.mosberg.spell;

import com.google.gson.JsonObject;

/**
 * Represents visual effects (VFX) data for a spell.
 */
public class VfxData {
    private final String particleType;
    private final int particleCount;
    private final String color;

    public VfxData(String particleType, int particleCount, String color) {
        this.particleType = particleType;
        this.particleCount = particleCount;
        this.color = color;
    }

    public String getParticleType() {
        return particleType;
    }

    public int getParticleCount() {
        return particleCount;
    }

    public String getColor() {
        return color;
    }

    public int getColorAsInt() {
        try {
            return Integer.parseInt(color, 16);
        } catch (NumberFormatException e) {
            return 0xFFFFFF;
        }
    }

    public static VfxData fromJson(JsonObject json) {
        String particleType =
                json.has("particleType") ? json.get("particleType").getAsString() : "flame";
        int particleCount = json.has("particleCount") ? json.get("particleCount").getAsInt() : 10;
        String color = json.has("color") ? json.get("color").getAsString() : "FFFFFF";
        return new VfxData(particleType, particleCount, color);
    }
}
