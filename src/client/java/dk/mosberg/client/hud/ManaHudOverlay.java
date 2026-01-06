package dk.mosberg.client.hud;

import dk.mosberg.MAM;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;

/**
 * Client-side HUD overlay for displaying mana pools, health, and status effects. Provides
 * customizable three-tier mana bar display with smooth animations and texture-based rendering.
 */
@Environment(EnvType.CLIENT)
public class ManaHudOverlay {
    // Texture identifiers
    private static final Identifier MANA_BAR_BG =
            Identifier.of(MAM.MOD_ID, "textures/gui/sprites/mana_bar_background.png");
    private static final Identifier MANA_BAR_PERSONAL =
            Identifier.of(MAM.MOD_ID, "textures/gui/sprites/mana_bar_personal.png");
    private static final Identifier MANA_BAR_AURA =
            Identifier.of(MAM.MOD_ID, "textures/gui/sprites/mana_bar_aura.png");
    private static final Identifier MANA_BAR_RESERVE =
            Identifier.of(MAM.MOD_ID, "textures/gui/sprites/mana_bar_reserve.png");
    private static final Identifier HEALTH_BAR_BG =
            Identifier.of(MAM.MOD_ID, "textures/gui/sprites/health_bar_background.png");
    private static final Identifier HEALTH_BAR_FILL =
            Identifier.of(MAM.MOD_ID, "textures/gui/sprites/health_bar_fill.png");
    private static final Identifier HEART_ICON =
            Identifier.of(MAM.MOD_ID, "textures/gui/sprites/heart.png");
    private static final Identifier MANA_ICON_PERSONAL =
            Identifier.of(MAM.MOD_ID, "textures/gui/hud/mana_pool_icon_personal.png");
    private static final Identifier MANA_ICON_AURA =
            Identifier.of(MAM.MOD_ID, "textures/gui/hud/mana_pool_icon_aura.png");
    private static final Identifier MANA_ICON_RESERVE =
            Identifier.of(MAM.MOD_ID, "textures/gui/hud/mana_pool_icon_reserve.png");

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

    // Colors for mana pools (fallback when textures unavailable)
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
     * Render the three-tier mana bar display with textures.
     */
    private static void renderManaDisplay(DrawContext context, MinecraftClient client,
            float tickDelta) {
        // Calculate position (default: top-left)
        int scaledX = xOffset;
        int scaledY = yOffset;

        // Render health bar
        renderHealthBar(context, client, scaledX, scaledY);
        scaledY += 15;

        // Render mana pools with icons and textures
        scaledY = renderManaPoolWithTexture(context, client, 0, "Personal", MANA_BAR_PERSONAL,
                MANA_ICON_PERSONAL, maxMana[0], currentMana[0], scaledX, scaledY);
        scaledY = renderManaPoolWithTexture(context, client, 1, "Aura", MANA_BAR_AURA,
                MANA_ICON_AURA, maxMana[1], currentMana[1], scaledX, scaledY);
        scaledY = renderManaPoolWithTexture(context, client, 2, "Reserve", MANA_BAR_RESERVE,
                MANA_ICON_RESERVE, maxMana[2], currentMana[2], scaledX, scaledY);
    }

    /**
     * Render health bar with textures and heart icons.
     */
    private static void renderHealthBar(DrawContext context, MinecraftClient client, int x, int y) {
        if (client.player == null) {
            return;
        }

        float health = client.player.getHealth();
        float maxHealth = client.player.getMaxHealth();
        int barWidth = 102;
        int barHeight = 9;

        // Render heart icon
        context.drawTexture(RenderPipelines.GUI_TEXTURED, HEART_ICON, x - 11, y, 0.0f, 0.0f, 9, 9,
                9, 9);

        // Render health bar background texture
        context.drawTexture(RenderPipelines.GUI_TEXTURED, HEALTH_BAR_BG, x, y, 0.0f, 0.0f, barWidth,
                barHeight, 182, 9);

        // Render health bar fill (scaled by health percentage)
        int healthWidth = (int) ((health / maxHealth) * barWidth);
        if (healthWidth > 0) {
            context.drawTexture(RenderPipelines.GUI_TEXTURED, HEALTH_BAR_FILL, x, y, 0.0f, 0.0f,
                    healthWidth, barHeight, 182, 9);
        }

        // Text overlay
        TextRenderer textRenderer = client.textRenderer;
        String healthText = String.format("%.1f/%.1f", health, maxHealth);
        context.drawText(textRenderer, healthText, x + 4, y + 1, 0xFFFFFFFF, true);
    }

    /**
     * Render a single mana pool bar with texture.
     *
     * @return The y position for the next element
     */
    private static int renderManaPoolWithTexture(DrawContext context, MinecraftClient client,
            int poolIndex, String name, Identifier barTexture, Identifier iconTexture,
            double maxMana, double currentMana, int x, int y) {

        int barWidth = 102;
        int barHeight = 5;

        // Update smooth animation
        float targetMana = (float) currentMana;
        smoothMana[poolIndex] += (targetMana - smoothMana[poolIndex]) * ANIMATION_SPEED;

        // Render mana pool icon
        context.drawTexture(RenderPipelines.GUI_TEXTURED, iconTexture, x - 18, y - 1, 0.0f, 0.0f,
                16, 16, 16, 16);

        // Render mana bar background
        context.drawTexture(RenderPipelines.GUI_TEXTURED, MANA_BAR_BG, x, y, 0.0f, 0.0f, barWidth,
                barHeight, 182, 5);

        // Render mana bar fill (scaled by mana percentage)
        int manaWidth = (int) ((smoothMana[poolIndex] / maxMana) * barWidth);
        if (manaWidth > 0) {
            context.drawTexture(RenderPipelines.GUI_TEXTURED, barTexture, x, y, 0.0f, 0.0f,
                    manaWidth, barHeight, 182, 5);
        }

        // Text overlay
        TextRenderer textRenderer = client.textRenderer;
        String manaText = String.format("%s: %.0f/%.0f (%.0f%%)", name, smoothMana[poolIndex],
                maxMana, (smoothMana[poolIndex] / maxMana) * 100);
        context.drawText(textRenderer, manaText, x + 4, y - 8, 0xFFFFFFFF, true);

        return y + barHeight + 12; // Return next Y position with spacing
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
