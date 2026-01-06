package dk.mosberg.client.screen;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import dk.mosberg.MAM;
import dk.mosberg.spell.Spell;
import dk.mosberg.spell.SpellLoader;
import dk.mosberg.spell.SpellSchool;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;


/**
 * Screen for selecting and viewing spells organized by school. Allows players to cast spells by
 * selecting them from a UI with texture-based rendering.
 */
@Environment(EnvType.CLIENT)
public class SpellSelectionScreen extends Screen {
    // Texture identifiers
    private static final Identifier BACKGROUND_TEXTURE =
            Identifier.of(MAM.MOD_ID, "textures/gui/spell_selection_bg.png");
    private static final Identifier INFO_PANEL_TEXTURE =
            Identifier.of(MAM.MOD_ID, "textures/gui/spell_info_panel.png");
    private static final Identifier SPELL_SLOT_EMPTY =
            Identifier.of(MAM.MOD_ID, "textures/gui/sprites/spell_slot_empty.png");
    private static final Identifier SPELL_SLOT_SELECTED =
            Identifier.of(MAM.MOD_ID, "textures/gui/sprites/spell_slot_selected.png");
    private static final Identifier SCHOOL_BUTTON_NORMAL =
            Identifier.of(MAM.MOD_ID, "textures/gui/sprites/school_button_normal.png");
    private static final Identifier SCHOOL_BUTTON_HOVER =
            Identifier.of(MAM.MOD_ID, "textures/gui/sprites/school_button_hover.png");
    private static final Identifier SCHOOL_BUTTON_SELECTED =
            Identifier.of(MAM.MOD_ID, "textures/gui/sprites/school_button_selected.png");

    private static final int BUTTON_WIDTH = 120;
    private static final int BUTTON_HEIGHT = 20;
    private static final int MARGIN = 10;
    private static final int SCHOOL_BUTTON_WIDTH = 80;

    private final Screen parentScreen;
    private SpellSchool selectedSchool = SpellSchool.FIRE;
    private Spell selectedSpell = null;
    private int scrollOffset = 0;
    private final int spellsPerPage = 8;
    private List<Spell> currentSpells = new ArrayList<>();

    // UI state
    private int schoolButtonStartX = 0;
    private int schoolButtonStartY = 0;
    private int spellListStartX = 0;
    private int spellListStartY = 0;
    private int infoBoxStartX = 0;
    private int infoBoxStartY = 0;

    public SpellSelectionScreen(Screen parentScreen) {
        super(Text.literal("Select Spell"));
        this.parentScreen = parentScreen;
        updateSpellList();
    }

    @Override
    protected void init() {
        super.init();

        // Layout: Schools on left, spells in middle, info on right
        schoolButtonStartX = MARGIN;
        schoolButtonStartY = MARGIN + 30;
        spellListStartX = schoolButtonStartX + SCHOOL_BUTTON_WIDTH + MARGIN;
        spellListStartY = schoolButtonStartY;
        infoBoxStartX = spellListStartX + SCHOOL_BUTTON_WIDTH + MARGIN + 50;
        infoBoxStartY = spellListStartY;

        // Create school selection buttons
        int yPos = schoolButtonStartY;
        for (SpellSchool school : SpellSchool.values()) {
            final SpellSchool schoolFinal = school;
            this.addDrawableChild(ButtonWidget
                    .builder(Text.literal(school.getId().toUpperCase(Locale.ROOT)),
                            btn -> selectSchool(schoolFinal))
                    .dimensions(schoolButtonStartX, yPos, SCHOOL_BUTTON_WIDTH, BUTTON_HEIGHT)
                    .build());
            yPos += BUTTON_HEIGHT + 5;
        }

        // Create scroll buttons for spell list
        this.addDrawableChild(ButtonWidget.builder(Text.literal("^"), btn -> scrollSpells(-1))
                .dimensions(spellListStartX, spellListStartY - 25, 20, 20).build());

        this.addDrawableChild(ButtonWidget.builder(Text.literal("v"), btn -> scrollSpells(1))
                .dimensions(spellListStartX + 25, spellListStartY - 25, 20, 20).build());

        // Create cast button
        this.addDrawableChild(ButtonWidget
                .builder(Text.literal("Cast Spell"), btn -> castSelectedSpell())
                .dimensions(infoBoxStartX, this.height - BUTTON_HEIGHT - MARGIN, 100, BUTTON_HEIGHT)
                .build());

        // Create close button
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Close"), btn -> this.close())
                .dimensions(infoBoxStartX + 110, this.height - BUTTON_HEIGHT - MARGIN, 80,
                        BUTTON_HEIGHT)
                .build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        // Render darkened game background
        this.renderBackground(context, mouseX, mouseY, delta);

        // Render main panel background texture (centered)
        int panelWidth = 256;
        int panelHeight = 256;
        int panelX = (this.width - panelWidth) / 2;
        int panelY = (this.height - panelHeight) / 2;
        context.drawTexture(RenderPipelines.GUI_TEXTURED, BACKGROUND_TEXTURE, panelX, panelY, 0.0f,
                0.0f, panelWidth, panelHeight, 256, 256);

        // Render title
        TextRenderer textRenderer = this.client.textRenderer;
        String title = "Spell Selection - " + selectedSchool.getId().toUpperCase();
        int titleX = (this.width - textRenderer.getWidth(title)) / 2;
        context.drawText(textRenderer, title, titleX, panelY + 10, 0xFFD700, false);

        // Render schools section header
        context.drawText(textRenderer, "Schools:", schoolButtonStartX, schoolButtonStartY - 20,
                0xCCCCCC, false);

        // Render spells section header
        context.drawText(textRenderer, "Spells:", spellListStartX, spellListStartY - 20, 0xCCCCCC,
                false);

        // Render spell list with textures
        renderSpellList(context, mouseX, mouseY);

        // Render info box
        if (selectedSpell != null) {
            renderSpellInfo(context);
        }

        // Render super (buttons)
        super.render(context, mouseX, mouseY, delta);
    }

    private void renderSpellList(DrawContext context, int mouseX, int mouseY) {
        TextRenderer textRenderer = this.client.textRenderer;

        int yPos = spellListStartY;
        int displayCount = 0;

        for (int i = scrollOffset; i < currentSpells.size() && displayCount < spellsPerPage; i++) {
            Spell spell = currentSpells.get(i);
            boolean isHovered = mouseX >= spellListStartX && mouseX <= spellListStartX + 140
                    && mouseY >= yPos && mouseY <= yPos + 20;
            boolean isSelected = spell == selectedSpell;

            // Render spell slot background
            Identifier slotTexture = isSelected ? SPELL_SLOT_SELECTED : SPELL_SLOT_EMPTY;
            context.drawTexture(RenderPipelines.GUI_TEXTURED, slotTexture, spellListStartX, yPos,
                    0.0f, 0.0f, 32, 32, 32, 32);

            // Render spell name
            String spellLabel = spell.getName();
            int textColor = isSelected ? 0xFFFF00 : (isHovered ? 0xFFAA00 : 0xFFFFFF);
            context.drawText(textRenderer, spellLabel, spellListStartX + 36, yPos + 3, textColor,
                    false);

            // Render mana cost
            String manaCost = (int) spell.getManaCost() + "m";
            context.drawText(textRenderer, manaCost, spellListStartX + 36, yPos + 13, 0x33FFFF,
                    false);

            yPos += 36;
            displayCount++;
        }

        // Render spell count
        String countText = "Page " + (scrollOffset / spellsPerPage + 1) + " of "
                + Math.max(1, (currentSpells.size() + spellsPerPage - 1) / spellsPerPage);
        context.drawText(textRenderer, countText, spellListStartX,
                spellListStartY + (spellsPerPage * 36) + 5, 0xAAAA, false);
    }

    private void renderSpellInfo(DrawContext context) {
        TextRenderer textRenderer = this.client.textRenderer;
        int panelWidth = 180;
        int panelHeight = 200;

        // Render info panel background texture
        context.drawTexture(RenderPipelines.GUI_TEXTURED, INFO_PANEL_TEXTURE, infoBoxStartX,
                infoBoxStartY, 0.0f, 0.0f, panelWidth, panelHeight, 180, 200);

        int yPos = infoBoxStartY + 25;
        int xPos = infoBoxStartX + 10;

        // Spell name
        context.drawText(textRenderer, "§6" + selectedSpell.getName(), xPos, yPos, 0xFFFF00, false);
        yPos += 15;

        // Spell school
        context.drawText(textRenderer, "School: " + selectedSchool.getId(), xPos, yPos, 0xFFFFFF,
                false);
        yPos += 12;

        // Mana cost
        context.drawText(textRenderer,
                "Mana Cost: " + String.format("%.1f", selectedSpell.getManaCost()), xPos, yPos,
                0xFF33FFFF, false);
        yPos += 12;

        // Cast type
        context.drawText(textRenderer, "Type: " + selectedSpell.getCastType(), xPos, yPos, 0xFFFFFF,
                false);
        yPos += 12;

        // Damage
        if (selectedSpell.getDamage() > 0) {
            context.drawText(textRenderer, "Damage: " + (int) selectedSpell.getDamage(), xPos, yPos,
                    0xFFFF00, false);
            yPos += 12;
        }

        // Cooldown
        if (selectedSpell.getCooldown() > 0) {
            context.drawText(textRenderer,
                    "Cooldown: " + String.format("%.1f", selectedSpell.getCooldown()) + "s", xPos,
                    yPos, 0xFFFFFF, false);
            yPos += 12;
        }

        // Level requirement
        context.drawText(textRenderer, "Level Req: " + selectedSpell.getRequiredLevel(), xPos, yPos,
                0xFFFFFF, false);
        yPos += 12;

        // Tier
        context.drawText(textRenderer, "Tier: " + selectedSpell.getTier(), xPos, yPos, 0xFFFF99,
                false);
        yPos += 15;

        // Description if available
        if (selectedSpell.getDescription() != null && !selectedSpell.getDescription().isEmpty()) {
            String desc = selectedSpell.getDescription();
            // Wrap text
            List<String> lines = wrapText(desc, 20);
            for (String line : lines) {
                if (yPos < infoBoxStartY + panelHeight - 20) {
                    context.drawText(textRenderer, line, xPos, yPos, 0xCCCCCC, false);
                    yPos += 10;
                }
            }
        }
    }

    private List<String> wrapText(String text, int maxChars) {
        List<String> lines = new ArrayList<>();
        String[] words = text.split(" ");
        StringBuilder line = new StringBuilder();

        for (String word : words) {
            if ((line.length() + word.length() + 1) > maxChars) {
                if (line.length() > 0) {
                    lines.add(line.toString());
                    line = new StringBuilder();
                }
            }
            if (line.length() > 0) {
                line.append(" ");
            }
            line.append(word);
        }

        if (line.length() > 0) {
            lines.add(line.toString());
        }

        return lines;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount,
            double verticalAmount) {
        // Scroll spell list with mouse wheel
        if (mouseX >= spellListStartX && mouseX <= spellListStartX + 140) {
            scrollSpells((int) -verticalAmount);
            return true;
        }
        return super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
    }

    private void selectSchool(SpellSchool school) {
        selectedSchool = school;
        scrollOffset = 0;
        selectedSpell = null;
        updateSpellList();
    }

    private void updateSpellList() {
        Map<Identifier, Spell> spellMap = SpellLoader.getSpellsBySchool(selectedSchool);
        currentSpells = new ArrayList<>(spellMap.values());
        if (!currentSpells.isEmpty() && selectedSpell == null) {
            selectedSpell = currentSpells.get(0);
        }
    }

    private void scrollSpells(int direction) {
        scrollOffset += direction;
        int maxOffset = Math.max(0, currentSpells.size() - spellsPerPage);
        scrollOffset = Math.min(Math.max(0, scrollOffset), maxOffset);
    }

    private void castSelectedSpell() {
        if (selectedSpell == null) {
            return;
        }

        // Send packet to server to cast spell
        dk.mosberg.client.network.ClientSpellCastPacket.send(selectedSpell.getId());
        this.close();
    }

    @Override
    public void close() {
        this.client.setScreen(parentScreen);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }
}
