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
 * Displays active buff and debuff effects with icons in the HUD.
 */
@Environment(EnvType.CLIENT)
public class BuffDisplayOverlay {
    // Texture identifiers
    private static final Identifier BUFF_FRAME =
            Identifier.of(MAM.MOD_ID, "textures/gui/sprites/buff_frame.png");
    private static final Identifier BUFF_POSITIVE =
            Identifier.of(MAM.MOD_ID, "textures/gui/sprites/buff_positive.png");
    private static final Identifier BUFF_NEGATIVE =
            Identifier.of(MAM.MOD_ID, "textures/gui/sprites/buff_negative.png");

    private static boolean enabled = true;
    private static int xOffset = -5;
    private static int yOffset = 5;
    private static final int ICON_SIZE = 18;
    private static final int SPACING = 2;

    /**
     * Render active buff/debuff effects.
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
        int x = screenWidth + xOffset - ICON_SIZE;
        int y = yOffset;

        // Placeholder: replace with player component buff data when available
        // For now, render example buffs
        renderExampleBuffs(context, client, x, y);
    }

    /**
     * Render example buff icons (placeholder until buff system is connected).
     */
    private static void renderExampleBuffs(DrawContext context, MinecraftClient client, int x,
            int y) {
        // Example buffs: {isPositive, duration}
        boolean[][] exampleBuffs = {{true, true}, // Positive buff
                {true, true}, // Positive buff
                {false, true}, // Negative buff
        };

        String[] buffNames = {"Fire Shield", "Mana Regen", "Curse"};
        int[] durations = {120, 300, 60}; // In ticks

        for (int i = 0; i < exampleBuffs.length; i++) {
            boolean isPositive = exampleBuffs[i][0];
            int currentY = y + i * (ICON_SIZE + SPACING);

            renderBuffIcon(context, client, x, currentY, isPositive, buffNames[i], durations[i]);
        }
    }

    /**
     * Render a single buff/debuff icon with border and duration.
     */
    private static void renderBuffIcon(DrawContext context, MinecraftClient client, int x, int y,
            boolean isPositive, String name, int durationTicks) {

        // Render frame background
        context.drawTexture(RenderPipelines.GUI_TEXTURED, BUFF_FRAME, x, y, 0.0f, 0.0f, ICON_SIZE,
                ICON_SIZE, 18, 18);

        // Render colored border
        Identifier borderTexture = isPositive ? BUFF_POSITIVE : BUFF_NEGATIVE;
        context.drawTexture(RenderPipelines.GUI_TEXTURED, borderTexture, x, y, 0.0f, 0.0f,
                ICON_SIZE, ICON_SIZE, 18, 18);

        // Render duration text (seconds)
        int durationSeconds = durationTicks / 20;
        String durationText = String.valueOf(durationSeconds);
        TextRenderer textRenderer = client.textRenderer;
        int textX = x + ICON_SIZE / 2 - textRenderer.getWidth(durationText) / 2;
        int textY = y + ICON_SIZE / 2 - 4;
        context.drawText(textRenderer, durationText, textX, textY, 0xFFFFFF, true);

        // Render buff name on hover (hover detection stub)
    }

    /**
     * Render tooltip for buff on hover.
     */
    @SuppressWarnings("unused")
    private static void renderBuffTooltip(DrawContext context, MinecraftClient client, int mouseX,
            int mouseY, String name, String description, int duration) {

        TextRenderer textRenderer = client.textRenderer;
        int tooltipWidth = 120;
        int tooltipHeight = 40;

        // Position tooltip near mouse
        int x = mouseX + 10;
        int y = mouseY - 10;

        // Render background
        context.fill(x, y, x + tooltipWidth, y + tooltipHeight, 0xDD000000);
        // Draw border using 4 lines (renderOutline doesn't exist)
        context.fill(x, y, x + tooltipWidth, y + 1, 0xFFFFFFFF); // Top
        context.fill(x, y + tooltipHeight - 1, x + tooltipWidth, y + tooltipHeight, 0xFFFFFFFF); // Bottom
        context.fill(x, y, x + 1, y + tooltipHeight, 0xFFFFFFFF); // Left
        context.fill(x + tooltipWidth - 1, y, x + tooltipWidth, y + tooltipHeight, 0xFFFFFFFF); // Right

        // Render text
        int textX = x + 5;
        int textY = y + 5;
        context.drawText(textRenderer, name, textX, textY, 0xFFFF00, false);
        textY += 12;

        // Wrap description text
        String[] words = description.split(" ");
        StringBuilder line = new StringBuilder();
        for (String word : words) {
            if (textRenderer.getWidth(line + word) > tooltipWidth - 10) {
                context.drawText(textRenderer, line.toString(), textX, textY, 0xFFFFFF, false);
                textY += 10;
                line = new StringBuilder(word + " ");
            } else {
                line.append(word).append(" ");
            }
        }
        if (line.length() > 0) {
            context.drawText(textRenderer, line.toString(), textX, textY, 0xFFFFFF, false);
        }
    }

    public static void setEnabled(boolean enabled) {
        BuffDisplayOverlay.enabled = enabled;
    }

    public static boolean isEnabled() {
        return enabled;
    }

    public static void setPosition(int x, int y) {
        xOffset = x;
        yOffset = y;
    }
}
