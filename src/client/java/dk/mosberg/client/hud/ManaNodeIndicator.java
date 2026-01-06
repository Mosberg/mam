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
 * Displays a compass-like indicator showing direction and distance to nearby mana nodes.
 */
@Environment(EnvType.CLIENT)
public class ManaNodeIndicator {
    // Texture identifiers
    private static final Identifier NODE_INDICATOR =
            Identifier.of(MAM.MOD_ID, "textures/gui/hud/mana_node_indicator.png");

    private static boolean enabled = true;
    private static final int ICON_SIZE = 16;

    /**
     * Render mana node indicators when nodes are nearby.
     */
    public static void render(DrawContext context, float tickDelta) {
        if (!enabled) {
            return;
        }

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || client.getDebugHud().shouldShowDebugHud()) {
            return;
        }

        // TODO: Get actual mana node locations from world
        // For now, render example indicator
        renderExampleIndicator(context, client);
    }

    /**
     * Render example node indicator (placeholder).
     */
    private static void renderExampleIndicator(DrawContext context, MinecraftClient client) {
        int screenWidth = client.getWindow().getScaledWidth();
        int screenHeight = client.getWindow().getScaledHeight();

        // Position at top center
        int x = screenWidth / 2 - ICON_SIZE / 2;
        int y = 5;

        // Render node indicator (pulsing animation removed for 2D rendering)
        context.drawTexture(RenderPipelines.GUI_TEXTURED, NODE_INDICATOR, x, y, 0.0f, 0.0f,
                ICON_SIZE, ICON_SIZE, 16, 16);

        // Render distance text
        TextRenderer textRenderer = client.textRenderer;
        String distanceText = "~45m"; // Example distance
        int textX = x + ICON_SIZE / 2 - textRenderer.getWidth(distanceText) / 2;
        int textY = y + ICON_SIZE + 2;
        context.drawText(textRenderer, distanceText, textX, textY, 0x66FFFF, true);
    }

    public static void setEnabled(boolean enabled) {
        ManaNodeIndicator.enabled = enabled;
    }

    public static boolean isEnabled() {
        return enabled;
    }
}
