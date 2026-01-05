"""
Generate all missing textures for Mana And Magic mod.
Creates textures for blocks, items, entities, and GUI elements.
"""

from PIL import Image, ImageDraw
import os

TEXTURE_SIZE = 16
OUTPUT_DIR = "src/main/resources/assets/mam/textures"

def ensure_dir(path):
    """Create directory if it doesn't exist."""
    os.makedirs(path, exist_ok=True)

# ============================================================================
# WAND TEXTURES (school-specific colors)
# ============================================================================

def create_school_wand(output_path, base_color, accent_color, tier_name):
    """Create a wand texture with school-specific colors."""
    img = Image.new('RGBA', (TEXTURE_SIZE, TEXTURE_SIZE), (0, 0, 0, 0))
    draw = ImageDraw.Draw(img)

    # Handle - vertical stick
    handle_color = (101, 67, 33)  # Brown wood
    for y in range(1, 12):
        x = 7
        draw.point((x, y), fill=handle_color)
        draw.point((x+1, y), fill=handle_color)

    # Core gem - different sizes based on tier
    if tier_name == "novice":
        # Small gem at top
        draw.rectangle([7, 0, 8, 1], fill=base_color)
    elif tier_name == "master":
        # Large gem with accent
        draw.rectangle([6, 0, 9, 2], fill=base_color)
        draw.point((7, 1), fill=accent_color)
        draw.point((8, 1), fill=accent_color)
        # Energy particles
        draw.point((5, 3), fill=accent_color + (180,))
        draw.point((10, 3), fill=accent_color + (180,))

    # Magical glow at top
    draw.point((7, 0), fill=accent_color)

    img.save(output_path)
    print(f"🪄 Created wand: {os.path.basename(output_path)}")

def generate_wand_textures():
    """Generate all missing wand textures."""
    wand_dir = os.path.join(OUTPUT_DIR, "item/wand")
    ensure_dir(wand_dir)

    wands = [
        ("fire", (255, 69, 0), (255, 140, 0)),      # Orange-red fire
        ("ice", (173, 216, 230), (135, 206, 250)),   # Light blue ice
        ("arcane", (138, 43, 226), (186, 85, 211)),  # Purple arcane
    ]

    for school, base_color, accent_color in wands:
        for tier in ["novice", "master"]:
            output_path = os.path.join(wand_dir, f"{school}_wand_{tier}.png")
            if not os.path.exists(output_path):
                create_school_wand(output_path, base_color, accent_color, tier)

# ============================================================================
# SPELL BOOK TEXTURES (school-specific)
# ============================================================================

def create_school_spell_book(output_path, cover_color, accent_color):
    """Create a spell book texture with school-specific colors."""
    img = Image.new('RGBA', (TEXTURE_SIZE, TEXTURE_SIZE), (0, 0, 0, 0))
    draw = ImageDraw.Draw(img)

    # Book outline
    outline = (0, 0, 0)
    draw.rectangle([3, 2, 12, 13], fill=cover_color, outline=outline)

    # Pages (white edge)
    draw.line([(12, 3), (12, 12)], fill=(255, 255, 255))

    # Magical symbol on cover
    draw.rectangle([6, 5, 9, 8], fill=accent_color)
    draw.point((7, 6), fill=cover_color)
    draw.point((8, 6), fill=cover_color)
    draw.point((7, 7), fill=cover_color)

    # Spine detail
    draw.line([(3, 2), (3, 13)], fill=(0, 0, 0))

    img.save(output_path)
    print(f"📖 Created spell book: {os.path.basename(output_path)}")

def generate_spell_book_textures():
    """Generate all missing spell book textures."""
    book_dir = os.path.join(OUTPUT_DIR, "item/spellbook")
    ensure_dir(book_dir)

    books = [
        ("fire", (178, 34, 34), (255, 215, 0)),      # Red with gold
        ("ice", (70, 130, 180), (173, 216, 230)),    # Steel blue with light blue
        ("arcane", (75, 0, 130), (186, 85, 211)),    # Indigo with medium orchid
        ("nature", (34, 139, 34), (144, 238, 144)),  # Forest green with light green
        ("dark", (25, 25, 25), (128, 0, 128)),       # Almost black with purple
        ("light", (255, 255, 224), (255, 215, 0)),   # Light yellow with gold
    ]

    for school, cover_color, accent_color in books:
        output_path = os.path.join(book_dir, f"{school}_spell_book.png")
        if not os.path.exists(output_path):
            create_school_spell_book(output_path, cover_color, accent_color)

# ============================================================================
# BLOCK TEXTURES
# ============================================================================

def create_gift_box_texture(output_path):
    """Create a gift box block texture."""
    img = Image.new('RGBA', (TEXTURE_SIZE, TEXTURE_SIZE), (0, 0, 0, 0))
    draw = ImageDraw.Draw(img)

    # Box base (festive red)
    box_color = (200, 0, 0)
    draw.rectangle([0, 0, 15, 15], fill=box_color)

    # Ribbon (gold)
    ribbon = (255, 215, 0)
    # Horizontal ribbon
    draw.rectangle([0, 7, 15, 8], fill=ribbon)
    # Vertical ribbon
    draw.rectangle([7, 0, 8, 15], fill=ribbon)

    # Bow at center
    draw.rectangle([6, 6, 9, 9], fill=ribbon)
    # Bow loops
    draw.point((5, 7), fill=ribbon)
    draw.point((10, 7), fill=ribbon)

    img.save(output_path)
    print(f"🎁 Created gift box: {os.path.basename(output_path)}")

# ============================================================================
# GUI TEXTURES
# ============================================================================

def create_mana_bar_texture(output_path):
    """Create mana bar overlay for HUD."""
    img = Image.new('RGBA', (182, 5), (0, 0, 0, 0))
    draw = ImageDraw.Draw(img)

    # Empty bar background (dark gray with transparency)
    draw.rectangle([0, 0, 181, 4], fill=(0, 0, 0, 128), outline=(255, 255, 255, 180))

    img.save(output_path)
    print(f"📊 Created mana bar: {os.path.basename(output_path)}")

def create_mana_bar_fill_texture(output_path, color, bar_type):
    """Create filled mana bar portion."""
    img = Image.new('RGBA', (182, 5), (0, 0, 0, 0))
    draw = ImageDraw.Draw(img)

    # Filled bar with gradient effect
    for x in range(0, 182):
        brightness = 1.0 - (x / 182.0) * 0.3
        adjusted_color = tuple(int(c * brightness) for c in color)
        draw.line([(x, 1), (x, 3)], fill=adjusted_color)

    img.save(output_path)
    print(f"📊 Created {bar_type} mana fill: {os.path.basename(output_path)}")

def create_spell_slot_texture(output_path):
    """Create spell slot background for GUI."""
    img = Image.new('RGBA', (18, 18), (0, 0, 0, 0))
    draw = ImageDraw.Draw(img)

    # Slot outline
    draw.rectangle([0, 0, 17, 17], fill=(139, 69, 19, 180), outline=(0, 0, 0, 255))

    # Inner area
    draw.rectangle([1, 1, 16, 16], fill=(101, 67, 33, 200))

    # Highlight
    draw.line([(1, 1), (16, 1)], fill=(180, 140, 80, 100))
    draw.line([(1, 1), (1, 16)], fill=(180, 140, 80, 100))

    img.save(output_path)
    print(f"🎰 Created spell slot: {os.path.basename(output_path)}")

def create_altar_gui_texture(output_path):
    """Create arcane altar GUI background."""
    img = Image.new('RGBA', (176, 166), (0, 0, 0, 0))
    draw = ImageDraw.Draw(img)

    # Main background (dark with purple tint)
    draw.rectangle([0, 0, 175, 165], fill=(40, 30, 50, 240))

    # Border
    border_color = (138, 43, 226)
    draw.rectangle([0, 0, 175, 165], outline=border_color, width=2)

    # Central crafting area (3x3 grid)
    grid_x, grid_y = 30, 17
    slot_size = 18
    for row in range(3):
        for col in range(3):
            x = grid_x + col * slot_size
            y = grid_y + row * slot_size
            draw.rectangle([x, y, x+17, y+17], fill=(70, 50, 90), outline=border_color)

    # Output slot
    output_x, output_y = 124, 35
    draw.rectangle([output_x, output_y, output_x+25, output_y+25],
                   fill=(90, 60, 120), outline=(255, 215, 0), width=2)

    # Title area
    draw.rectangle([0, 0, 175, 16], fill=(60, 40, 80))

    img.save(output_path)
    print(f"🏺 Created altar GUI: {os.path.basename(output_path)}")

def create_spell_book_gui_texture(output_path):
    """Create spell book GUI background."""
    img = Image.new('RGBA', (252, 180), (0, 0, 0, 0))
    draw = ImageDraw.Draw(img)

    # Book pages (parchment color)
    parchment = (245, 222, 179)

    # Left page
    draw.rectangle([5, 5, 120, 175], fill=parchment, outline=(139, 69, 19), width=2)

    # Right page
    draw.rectangle([131, 5, 246, 175], fill=parchment, outline=(139, 69, 19), width=2)

    # Center binding shadow
    draw.rectangle([121, 5, 130, 175], fill=(101, 67, 33))

    # Decorative corners
    corner_color = (139, 69, 19)
    for page_x in [5, 131]:
        for y_offset in [5, 170]:
            draw.line([(page_x, y_offset), (page_x+5, y_offset)], fill=corner_color, width=2)
            draw.line([(page_x, y_offset), (page_x, y_offset+5)], fill=corner_color, width=2)

    img.save(output_path)
    print(f"📚 Created spell book GUI: {os.path.basename(output_path)}")

def generate_gui_textures():
    """Generate all GUI textures."""
    gui_dir = os.path.join(OUTPUT_DIR, "gui")
    sprites_dir = os.path.join(gui_dir, "sprites")
    ensure_dir(gui_dir)
    ensure_dir(sprites_dir)

    # HUD elements
    mana_bar_path = os.path.join(sprites_dir, "mana_bar.png")
    if not os.path.exists(mana_bar_path):
        create_mana_bar_texture(mana_bar_path)

    # Filled mana bars (different colors for each pool)
    mana_fills = [
        ("personal", (0, 191, 255)),    # Cyan
        ("aura", (186, 85, 211)),        # Medium orchid
        ("reserve", (255, 140, 0)),      # Dark orange
    ]

    for pool_type, color in mana_fills:
        fill_path = os.path.join(sprites_dir, f"mana_bar_{pool_type}.png")
        if not os.path.exists(fill_path):
            create_mana_bar_fill_texture(fill_path, color, pool_type)

    # Spell slot
    spell_slot_path = os.path.join(sprites_dir, "spell_slot.png")
    if not os.path.exists(spell_slot_path):
        create_spell_slot_texture(spell_slot_path)

    # Container GUIs
    altar_gui_path = os.path.join(gui_dir, "arcane_altar.png")
    if not os.path.exists(altar_gui_path):
        create_altar_gui_texture(altar_gui_path)

    spell_book_gui_path = os.path.join(gui_dir, "spell_book.png")
    if not os.path.exists(spell_book_gui_path):
        create_spell_book_gui_texture(spell_book_gui_path)

# ============================================================================
# MAIN GENERATION
# ============================================================================

def main():
    """Generate all missing textures."""
    print("=" * 60)
    print("Generating ALL missing textures for Mana And Magic")
    print("=" * 60)

    print("\n📦 ITEM TEXTURES")
    print("-" * 60)
    generate_wand_textures()
    generate_spell_book_textures()

    print("\n🧱 BLOCK TEXTURES")
    print("-" * 60)
    gift_box_path = os.path.join(OUTPUT_DIR, "block/gift_box.png")
    if not os.path.exists(gift_box_path):
        ensure_dir(os.path.join(OUTPUT_DIR, "block"))
        create_gift_box_texture(gift_box_path)
    else:
        print(f"✓ Gift box already exists")

    print("\n🎨 GUI TEXTURES")
    print("-" * 60)
    generate_gui_textures()

    print("\n" + "=" * 60)
    print("✅ TEXTURE GENERATION COMPLETE!")
    print("=" * 60)

if __name__ == "__main__":
    main()
