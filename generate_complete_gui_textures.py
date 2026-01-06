"""
Complete GUI/HUD/Overlay Texture Generator for Mana And Magic.
Generates all missing GUI elements with consistent styling.
"""

from __future__ import annotations

import os
from PIL import Image, ImageDraw, ImageFont

# Setup paths
OUTPUT_DIR = "src/main/resources/assets/mam/textures"
GUI_DIR = os.path.join(OUTPUT_DIR, "gui")
SPRITES_DIR = os.path.join(GUI_DIR, "sprites")
HUD_DIR = os.path.join(GUI_DIR, "hud")
OVERLAY_DIR = os.path.join(GUI_DIR, "overlay")

# Color schemes
MANA_COLORS = {
    "personal": (51, 153, 255),      # Cyan
    "aura": (153, 51, 255),          # Purple
    "reserve": (255, 153, 51),       # Orange
}

SCHOOL_COLORS = {
    "air": (192, 192, 192),
    "arcane": (153, 102, 204),
    "blood": (139, 0, 0),
    "chaos": (255, 0, 255),
    "dark": (45, 27, 78),
    "earth": (139, 69, 19),
    "fire": (255, 69, 0),
    "ice": (0, 255, 255),
    "light": (255, 248, 220),
    "nature": (34, 139, 34),
    "thunder": (255, 215, 0),
    "void": (0, 0, 0),
    "water": (30, 144, 255),
}

def ensure_dir(path: str) -> None:
    os.makedirs(path, exist_ok=True)

def save_texture(img: Image.Image, relative_path: str, description: str = "") -> None:
    full_path = os.path.join(GUI_DIR, relative_path)
    ensure_dir(os.path.dirname(full_path))
    img.save(full_path)
    print(f"[OK] {description or relative_path}")

# ===== MANA BAR TEXTURES =====

def create_mana_bar_background() -> Image.Image:
    """Create the background/empty mana bar."""
    img = Image.new("RGBA", (182, 5), (0, 0, 0, 0))
    draw = ImageDraw.Draw(img)
    # Dark background with border
    draw.rectangle([0, 0, 181, 4], fill=(0, 0, 0, 180), outline=(80, 80, 80, 255))
    return img

def create_mana_bar_fill(color: tuple[int, int, int], name: str) -> Image.Image:
    """Create filled mana bar for a specific pool."""
    img = Image.new("RGBA", (182, 5), (0, 0, 0, 0))
    draw = ImageDraw.Draw(img)
    # Gradient effect
    for i in range(5):
        brightness = 1.0 - (i * 0.1)
        adjusted_color = tuple(int(c * brightness) for c in color)
        draw.rectangle([1, i, 180, i+1], fill=adjusted_color + (255,))
    return img

# ===== SPELL SLOT TEXTURES =====

def create_spell_slot_empty() -> Image.Image:
    """Empty spell slot background."""
    img = Image.new("RGBA", (32, 32), (0, 0, 0, 0))
    draw = ImageDraw.Draw(img)
    draw.rectangle([0, 0, 31, 31], fill=(40, 40, 40, 200), outline=(100, 100, 100, 255), width=2)
    return img

def create_spell_slot_selected() -> Image.Image:
    """Selected spell slot highlight."""
    img = Image.new("RGBA", (32, 32), (0, 0, 0, 0))
    draw = ImageDraw.Draw(img)
    draw.rectangle([0, 0, 31, 31], fill=(60, 60, 60, 200), outline=(255, 215, 0, 255), width=2)
    # Glow effect
    draw.rectangle([2, 2, 29, 29], outline=(255, 215, 0, 150), width=1)
    return img

def create_spell_slot_cooldown() -> Image.Image:
    """Cooldown overlay for spell slots."""
    img = Image.new("RGBA", (32, 32), (0, 0, 0, 0))
    draw = ImageDraw.Draw(img)
    draw.rectangle([0, 0, 31, 31], fill=(0, 0, 0, 150))
    return img

# ===== HUD OVERLAYS =====

def create_health_bar_background() -> Image.Image:
    """Health bar background."""
    img = Image.new("RGBA", (182, 9), (0, 0, 0, 0))
    draw = ImageDraw.Draw(img)
    draw.rectangle([0, 0, 181, 8], fill=(0, 0, 0, 180), outline=(80, 80, 80, 255))
    return img

def create_health_bar_fill() -> Image.Image:
    """Health bar fill (red)."""
    img = Image.new("RGBA", (182, 9), (0, 0, 0, 0))
    draw = ImageDraw.Draw(img)
    # Red gradient
    for i in range(9):
        brightness = 1.0 - (i * 0.05)
        red = int(255 * brightness)
        draw.rectangle([1, i, 180, i+1], fill=(red, 0, 0, 255))
    return img

def create_hearts_texture() -> Image.Image:
    """Heart icons for health display."""
    img = Image.new("RGBA", (9, 9), (0, 0, 0, 0))
    draw = ImageDraw.Draw(img)
    # Simple heart shape
    heart_pixels = [
        (1,0), (2,0), (4,0), (5,0), (6,0),
        (0,1), (1,1), (2,1), (3,1), (4,1), (5,1), (6,1), (7,1),
        (0,2), (1,2), (2,2), (3,2), (4,2), (5,2), (6,2), (7,2),
        (0,3), (1,3), (2,3), (3,3), (4,3), (5,3), (6,3), (7,3),
        (1,4), (2,4), (3,4), (4,4), (5,4), (6,4),
        (2,5), (3,5), (4,5), (5,5),
        (3,6), (4,6),
    ]
    for x, y in heart_pixels:
        draw.point((x, y), fill=(255, 0, 0, 255))
    return img

# ===== SCREEN BACKGROUNDS =====

def create_spell_selection_background() -> Image.Image:
    """Background for spell selection screen."""
    img = Image.new("RGBA", (256, 256), (0, 0, 0, 0))
    draw = ImageDraw.Draw(img)
    # Main panel
    draw.rectangle([0, 0, 255, 255], fill=(20, 20, 30, 220), outline=(100, 100, 150, 255), width=2)
    # Inner border
    draw.rectangle([4, 4, 251, 251], outline=(60, 60, 90, 255), width=1)
    return img

def create_school_button_normal() -> Image.Image:
    """Normal state for school selection button."""
    img = Image.new("RGBA", (80, 20), (0, 0, 0, 0))
    draw = ImageDraw.Draw(img)
    draw.rectangle([0, 0, 79, 19], fill=(50, 50, 80, 200), outline=(100, 100, 150, 255), width=1)
    return img

def create_school_button_hover() -> Image.Image:
    """Hover state for school selection button."""
    img = Image.new("RGBA", (80, 20), (0, 0, 0, 0))
    draw = ImageDraw.Draw(img)
    draw.rectangle([0, 0, 79, 19], fill=(70, 70, 110, 220), outline=(150, 150, 200, 255), width=2)
    return img

def create_school_button_selected() -> Image.Image:
    """Selected state for school selection button."""
    img = Image.new("RGBA", (80, 20), (0, 0, 0, 0))
    draw = ImageDraw.Draw(img)
    draw.rectangle([0, 0, 79, 19], fill=(100, 100, 150, 230), outline=(200, 200, 255, 255), width=2)
    # Glow effect
    draw.rectangle([2, 2, 77, 17], outline=(150, 150, 255, 150), width=1)
    return img

# ===== SPELL INFO PANEL =====

def create_spell_info_panel() -> Image.Image:
    """Info panel for spell details."""
    img = Image.new("RGBA", (180, 200), (0, 0, 0, 0))
    draw = ImageDraw.Draw(img)
    draw.rectangle([0, 0, 179, 199], fill=(26, 26, 42, 230), outline=(80, 80, 120, 255), width=2)
    # Title bar
    draw.rectangle([2, 2, 177, 20], fill=(40, 40, 60, 255))
    return img

# ===== COOLDOWN CIRCLE OVERLAY =====

def create_cooldown_circle(percent: int) -> Image.Image:
    """Cooldown circle overlay (0-100%)."""
    img = Image.new("RGBA", (32, 32), (0, 0, 0, 0))
    draw = ImageDraw.Draw(img)
    # Semi-transparent overlay
    if percent > 0:
        angle = int(360 * (percent / 100.0))
        draw.pieslice([0, 0, 31, 31], start=0, end=angle, fill=(0, 0, 0, 150))
    return img

# ===== RITUAL OVERLAY =====

def create_ritual_circle_overlay() -> Image.Image:
    """Overlay for active rituals."""
    img = Image.new("RGBA", (64, 64), (0, 0, 0, 0))
    draw = ImageDraw.Draw(img)
    # Outer circle
    draw.ellipse([0, 0, 63, 63], outline=(200, 100, 255, 200), width=2)
    # Inner circle
    draw.ellipse([16, 16, 47, 47], outline=(200, 100, 255, 150), width=1)
    # Runes (decorative lines)
    for angle in [0, 60, 120, 180, 240, 300]:
        import math
        x1 = 32 + int(20 * math.cos(math.radians(angle)))
        y1 = 32 + int(20 * math.sin(math.radians(angle)))
        x2 = 32 + int(30 * math.cos(math.radians(angle)))
        y2 = 32 + int(30 * math.sin(math.radians(angle)))
        draw.line([(x1, y1), (x2, y2)], fill=(200, 100, 255, 200), width=2)
    return img

# ===== BUFF/DEBUFF ICONS =====

def create_buff_icon_frame() -> Image.Image:
    """Frame for buff/debuff icons."""
    img = Image.new("RGBA", (18, 18), (0, 0, 0, 0))
    draw = ImageDraw.Draw(img)
    draw.rectangle([0, 0, 17, 17], fill=(40, 40, 40, 200), outline=(120, 120, 120, 255), width=1)
    return img

def create_buff_icon_positive() -> Image.Image:
    """Positive buff icon border."""
    img = Image.new("RGBA", (18, 18), (0, 0, 0, 0))
    draw = ImageDraw.Draw(img)
    draw.rectangle([0, 0, 17, 17], outline=(0, 255, 0, 255), width=2)
    return img

def create_buff_icon_negative() -> Image.Image:
    """Negative buff/debuff icon border."""
    img = Image.new("RGBA", (18, 18), (0, 0, 0, 0))
    draw = ImageDraw.Draw(img)
    draw.rectangle([0, 0, 17, 17], outline=(255, 0, 0, 255), width=2)
    return img

# ===== MANA NODE HUD INDICATOR =====

def create_mana_node_indicator() -> Image.Image:
    """Indicator for nearby mana nodes."""
    img = Image.new("RGBA", (16, 16), (0, 0, 0, 0))
    draw = ImageDraw.Draw(img)
    # Diamond shape
    draw.polygon([(8, 0), (15, 8), (8, 15), (0, 8)], fill=(100, 200, 255, 200), outline=(200, 255, 255, 255))
    # Inner glow
    draw.polygon([(8, 4), (11, 8), (8, 11), (4, 8)], fill=(200, 255, 255, 150))
    return img

# ===== CROSSHAIR OVERLAYS =====

def create_spell_crosshair() -> Image.Image:
    """Custom crosshair when spell is ready."""
    img = Image.new("RGBA", (16, 16), (0, 0, 0, 0))
    draw = ImageDraw.Draw(img)
    # Cross shape with glow
    draw.line([(8, 2), (8, 14)], fill=(150, 255, 255, 200), width=2)
    draw.line([(2, 8), (14, 8)], fill=(150, 255, 255, 200), width=2)
    # Corners
    draw.point((7, 7), fill=(255, 255, 255, 255))
    draw.point((9, 7), fill=(255, 255, 255, 255))
    draw.point((7, 9), fill=(255, 255, 255, 255))
    draw.point((9, 9), fill=(255, 255, 255, 255))
    return img

# ===== PROGRESS BARS =====

def create_progress_bar_background() -> Image.Image:
    """Generic progress bar background."""
    img = Image.new("RGBA", (100, 8), (0, 0, 0, 0))
    draw = ImageDraw.Draw(img)
    draw.rectangle([0, 0, 99, 7], fill=(0, 0, 0, 180), outline=(80, 80, 80, 255))
    return img

def create_progress_bar_fill() -> Image.Image:
    """Generic progress bar fill (green)."""
    img = Image.new("RGBA", (100, 8), (0, 0, 0, 0))
    draw = ImageDraw.Draw(img)
    for i in range(8):
        brightness = 1.0 - (i * 0.08)
        green = int(200 * brightness)
        draw.rectangle([1, i, 98, i+1], fill=(0, green, 0, 255))
    return img

# ===== MANA POOL ICONS =====

def create_mana_pool_icons():
    """Create individual icons for each mana pool type."""
    icons = {}
    for pool, color in MANA_COLORS.items():
        img = Image.new("RGBA", (16, 16), (0, 0, 0, 0))
        draw = ImageDraw.Draw(img)
        # Circle with pool color
        draw.ellipse([0, 0, 15, 15], fill=color + (220,), outline=(255, 255, 255, 255))
        # Inner circle (lighter)
        lighter = tuple(min(255, int(c * 1.3)) for c in color)
        draw.ellipse([4, 4, 11, 11], fill=lighter + (180,))
        icons[pool] = img
    return icons

# ===== GENERATE ALL TEXTURES =====

def generate_all_gui_textures():
    """Generate all GUI textures."""
    print("\n=== Generating Complete GUI/HUD/Overlay Texture Set ===\n")

    ensure_dir(GUI_DIR)
    ensure_dir(SPRITES_DIR)
    ensure_dir(HUD_DIR)
    ensure_dir(OVERLAY_DIR)

    # Mana bars
    save_texture(create_mana_bar_background(), "sprites/mana_bar_background.png", "Mana bar background")
    for pool, color in MANA_COLORS.items():
        save_texture(create_mana_bar_fill(color, pool), f"sprites/mana_bar_{pool}.png", f"Mana bar - {pool}")

    # Spell slots
    save_texture(create_spell_slot_empty(), "sprites/spell_slot_empty.png", "Empty spell slot")
    save_texture(create_spell_slot_selected(), "sprites/spell_slot_selected.png", "Selected spell slot")
    save_texture(create_spell_slot_cooldown(), "sprites/spell_slot_cooldown.png", "Spell cooldown overlay")

    # Health
    save_texture(create_health_bar_background(), "sprites/health_bar_background.png", "Health bar background")
    save_texture(create_health_bar_fill(), "sprites/health_bar_fill.png", "Health bar fill")
    save_texture(create_hearts_texture(), "sprites/heart.png", "Heart icon")

    # Screens
    save_texture(create_spell_selection_background(), "spell_selection_bg.png", "Spell selection background")
    save_texture(create_school_button_normal(), "sprites/school_button_normal.png", "School button normal")
    save_texture(create_school_button_hover(), "sprites/school_button_hover.png", "School button hover")
    save_texture(create_school_button_selected(), "sprites/school_button_selected.png", "School button selected")
    save_texture(create_spell_info_panel(), "spell_info_panel.png", "Spell info panel")

    # Cooldowns
    for percent in [25, 50, 75, 100]:
        save_texture(create_cooldown_circle(percent), f"sprites/cooldown_{percent}.png", f"Cooldown {percent}%")

    # Overlays
    save_texture(create_ritual_circle_overlay(), "overlay/ritual_circle.png", "Ritual circle overlay")
    save_texture(create_mana_node_indicator(), "hud/mana_node_indicator.png", "Mana node indicator")
    save_texture(create_spell_crosshair(), "hud/spell_crosshair.png", "Spell crosshair")

    # Buffs
    save_texture(create_buff_icon_frame(), "sprites/buff_frame.png", "Buff icon frame")
    save_texture(create_buff_icon_positive(), "sprites/buff_positive.png", "Positive buff border")
    save_texture(create_buff_icon_negative(), "sprites/buff_negative.png", "Negative buff border")

    # Progress
    save_texture(create_progress_bar_background(), "sprites/progress_bar_bg.png", "Progress bar background")
    save_texture(create_progress_bar_fill(), "sprites/progress_bar_fill.png", "Progress bar fill")

    # Mana pool icons
    icons = create_mana_pool_icons()
    for pool, icon in icons.items():
        save_texture(icon, f"hud/mana_pool_icon_{pool}.png", f"Mana pool icon - {pool}")

    print(f"\n=== Texture Generation Complete ===")
    print(f"Total textures generated: 30+")
    print(f"Output directory: {GUI_DIR}")

if __name__ == "__main__":
    generate_all_gui_textures()
