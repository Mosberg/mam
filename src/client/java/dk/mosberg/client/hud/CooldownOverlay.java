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
 * Cooldown overlay that displays active spell cooldowns around the crosshair.
 */
@Environment(EnvType.CLIENT)
public class CooldownOverlay {
    // Texture identifiers
    private static final Identifier COOLDOWN_25 =
            Identifier.of(MAM.MOD_ID, "textures/gui/sprites/cooldown_25.png");
    private static final Identifier COOLDOWN_50 =
            Identifier.of(MAM.MOD_ID, "textures/gui/sprites/cooldown_50.png");
    private static final Identifier COOLDOWN_75 =
            Identifier.of(MAM.MOD_ID, "textures/gui/sprites/cooldown_75.png");
    private static final Identifier COOLDOWN_100 =
            Identifier.of(MAM.MOD_ID, "textures/gui/sprites/cooldown_100.png");
    private static final Identifier SPELL_CROSSHAIR =
            Identifier.of(MAM.MOD_ID, "textures/gui/hud/spell_crosshair.png");

    private static boolean enabled = true;
    private static final int ICON_SIZE = 32;
    private static final int RADIUS = 48; // Distance from center

    /**
     * Render cooldown overlays around crosshair.
     */
    public static void render(DrawContext context, float tickDelta) {
        if (!enabled) {
            return;
        }

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || client.getDebugHud().shouldShowDebugHud()) {
            return;
        }

        int screenWidth = client.getWindow().getScaledWidth();
        int screenHeight = client.getWindow().getScaledHeight();
        int centerX = screenWidth / 2;
        int centerY = screenHeight / 2;

        // Render custom spell crosshair
        renderSpellCrosshair(context, centerX, centerY);

        // Placeholder: swap in player spell cooldown data once hooked up
        // For now, render example cooldowns
        renderExampleCooldowns(context, centerX, centerY);
    }

    /**
     * Render custom crosshair overlay when spell is ready.
     */
    private static void renderSpellCrosshair(DrawContext context, int centerX, int centerY) {
        int size = 16;
        context.drawTexture(RenderPipelines.GUI_TEXTURED, SPELL_CROSSHAIR, centerX - size / 2,
                centerY - size / 2, 0.0f, 0.0f, size, size, 16, 16);
    }

    /**
     * Render example cooldown indicators (placeholder until spell system is connected).
     */
    private static void renderExampleCooldowns(DrawContext context, int centerX, int centerY) {
        // Example: 4 spell slots in cardinal directions
        float[] cooldownPercents = {0.75f, 0.25f, 0.5f, 1.0f}; // Example cooldowns
        int[][] positions = {{centerX, centerY - RADIUS}, // Top
                {centerX + RADIUS, centerY}, // Right
                {centerX, centerY + RADIUS}, // Bottom
                {centerX - RADIUS, centerY} // Left
        };

        for (int i = 0; i < cooldownPercents.length; i++) {
            float percent = cooldownPercents[i];
            int x = positions[i][0] - ICON_SIZE / 2;
            int y = positions[i][1] - ICON_SIZE / 2;

            renderCooldownIcon(context, x, y, percent);
        }
    }

    /**
     * Render a single cooldown icon with appropriate overlay.
     */
    private static void renderCooldownIcon(DrawContext context, int x, int y, float percent) {
        // Select appropriate cooldown texture based on percentage
        Identifier cooldownTexture;
        if (percent >= 0.75f) {
            cooldownTexture = COOLDOWN_100;
        } else if (percent >= 0.5f) {
            cooldownTexture = COOLDOWN_75;
        } else if (percent >= 0.25f) {
            cooldownTexture = COOLDOWN_50;
        } else {
            cooldownTexture = COOLDOWN_25;
        }

        // Render cooldown overlay
        context.drawTexture(RenderPipelines.GUI_TEXTURED, cooldownTexture, x, y, 0.0f, 0.0f,
                ICON_SIZE, ICON_SIZE, 32, 32);

        // Render remaining time text if on cooldown
        if (percent > 0 && percent < 1.0f) {
            MinecraftClient client = MinecraftClient.getInstance();
            TextRenderer textRenderer = client.textRenderer;
            String timeText = String.format("%.1fs", percent * 10); // Example calculation
            int textX = x + ICON_SIZE / 2 - textRenderer.getWidth(timeText) / 2;
            int textY = y + ICON_SIZE + 2;
            context.drawText(textRenderer, timeText, textX, textY, 0xFFFFFF, true);
        }
    }

    public static void setEnabled(boolean enabled) {
        CooldownOverlay.enabled = enabled;
    }

    public static boolean isEnabled() {
        return enabled;
    }
}
