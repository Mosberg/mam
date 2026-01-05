package dk.mosberg.client.hud;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;

/**
 * Client-side HUD overlay for displaying mana pools, health, and status effects. Provides
 * customizable three-tier mana bar display with smooth animations.
 */
@Environment(EnvType.CLIENT)
public class ManaHudOverlay {
    // Configuration
    private static boolean enabled = true;
    private static float scale = 1.0f;
    private static int xOffset = 10;
    private static int yOffset = 10;
    private static float transparency = 1.0f;

    // Animation state
    private static double[] currentMana = new double[] {250.0, 500.0, 1000.0}; // Current values
                                                                               // from server
    private static double[] maxMana = new double[] {250.0, 500.0, 1000.0}; // Max values from server
    private static float[] smoothMana = new float[3]; // Smooth values for animation
    private static final float ANIMATION_SPEED = 0.1f;

    // Colors for mana pools
    private static final int PRIMARY_COLOR = 0xFF3399FF; // Cyan (Personal)
    private static final int SECONDARY_COLOR = 0xFF9933FF; // Purple (Aura)
    private static final int TERTIARY_COLOR = 0xFFFF9933; // Orange (Reserve)

    /**
     * Render the mana HUD overlay. Called every frame from client rendering hook.
     *
     * @param context The draw context
     * @param tickDelta The tick delta for smooth interpolation
     */
    public static void render(DrawContext context, float tickDelta) {
        if (!enabled) {
            return;
        }

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || client.getDebugHud().shouldShowDebugHud()) {
            return;
        }

        // Get mana component (client doesn't have direct access, would need packet sync)
        // For now, render placeholder values
        renderManaDisplay(context, client, tickDelta);
    }

    /**
     * Render the three-tier mana bar display.
     */
    private static void renderManaDisplay(DrawContext context, MinecraftClient client,
            float tickDelta) {
        // Calculate position (default: top-left)
        int x = xOffset;
        int y = yOffset;

        // Render health bar
        renderHealthBar(context, client, x, y);
        y += 15;

        // Render mana pools
        y = renderManaPool(context, client, 0, "Primary", PRIMARY_COLOR, maxMana[0], currentMana[0],
                x, y);
        y = renderManaPool(context, client, 1, "Secondary", SECONDARY_COLOR, maxMana[1],
                currentMana[1], x, y);
        y = renderManaPool(context, client, 2, "Tertiary", TERTIARY_COLOR, maxMana[2],
                currentMana[2], x, y);
    }

    /**
     * Render health bar with heart icons.
     */
    private static void renderHealthBar(DrawContext context, MinecraftClient client, int x, int y) {
        if (client.player == null) {
            return;
        }

        float health = client.player.getHealth();
        float maxHealth = client.player.getMaxHealth();
        int barWidth = 100;
        int barHeight = 10;

        // Background
        context.fill(x, y, x + barWidth + 2, y + barHeight + 2, 0x88000000);

        // Health bar (red)
        int healthWidth = (int) ((health / maxHealth) * barWidth);
        context.fill(x + 1, y + 1, x + 1 + healthWidth, y + 1 + barHeight, 0xFFFF0000);

        // Text
        TextRenderer textRenderer = client.textRenderer;
        String healthText = String.format("❤ %.1f/%.1f", health, maxHealth);
        context.drawText(textRenderer, healthText, x + 2, y + 2, 0xFFFFFFFF, true);
    }

    /**
     * Render a single mana pool bar.
     *
     * @return The y position for the next element
     */
    private static int renderManaPool(DrawContext context, MinecraftClient client, int poolIndex,
            String name, int color, double maxMana, double currentMana, int x, int y) {

        int barWidth = 100;
        int barHeight = 8;

        // Update smooth animation
        float targetMana = (float) currentMana;
        smoothMana[poolIndex] += (targetMana - smoothMana[poolIndex]) * ANIMATION_SPEED;

        // Background
        context.fill(x, y, x + barWidth + 2, y + barHeight + 2, 0x88000000);

        // Mana bar
        int manaWidth = (int) ((smoothMana[poolIndex] / maxMana) * barWidth);
        context.fill(x + 1, y + 1, x + 1 + manaWidth, y + 1 + barHeight,
                applyAlpha(color, transparency));

        // Text
        TextRenderer textRenderer = client.textRenderer;
        String manaText = String.format("%s: %.0f/%.0f", name, smoothMana[poolIndex], maxMana);
        context.drawText(textRenderer, manaText, x + 2, y + 2, 0xFFFFFFFF, true);

        return y + barHeight + 4; // Return next Y position
    }

    /**
     * Apply alpha transparency to a color.
     */
    private static int applyAlpha(int color, float alpha) {
        int a = (int) (alpha * 255) << 24;
        return (color & 0x00FFFFFF) | a;
    }

    // Configuration methods

    public static void setEnabled(boolean enabled) {
        ManaHudOverlay.enabled = enabled;
    }

    public static boolean isEnabled() {
        return enabled;
    }

    public static void setScale(float scale) {
        ManaHudOverlay.scale = Math.max(0.5f, Math.min(2.0f, scale));
    }

    public static float getScale() {
        return scale;
    }

    public static void setPosition(int x, int y) {
        xOffset = x;
        yOffset = y;
    }

    public static int getXOffset() {
        return xOffset;
    }

    public static int getYOffset() {
        return yOffset;
    }

    public static void setTransparency(float transparency) {
        ManaHudOverlay.transparency = Math.max(0.1f, Math.min(1.0f, transparency));
    }

    public static float getTransparency() {
        return transparency;
    }

    /**
     * Update mana values for smooth animation. Called when server sends mana sync packets.
     *
     * @param primary Current personal/primary mana
     * @param secondary Current aura/secondary mana
     * @param tertiary Current reserve/tertiary mana
     */
    public static void updateManaValues(double primary, double secondary, double tertiary) {
        currentMana[0] = primary;
        currentMana[1] = secondary;
        currentMana[2] = tertiary;
    }

    /**
     * Update max mana values. Called when server sends mana sync packets.
     *
     * @param primaryMax Max personal/primary mana
     * @param secondaryMax Max aura/secondary mana
     * @param tertiaryMax Max reserve/tertiary mana
     */
    public static void updateMaxManaValues(double primaryMax, double secondaryMax,
            double tertiaryMax) {
        maxMana[0] = primaryMax;
        maxMana[1] = secondaryMax;
        maxMana[2] = tertiaryMax;
    }
}
