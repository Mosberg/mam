"""
GUI texture generator for Mana And Magic.
Builds mana bars, spell slots, and container backgrounds using shared palettes.
"""

from __future__ import annotations

import argparse
import os

from PIL import Image, ImageDraw

from texture_palettes import OUTPUT_DIR, SCHOOL_COLORS

GUI_DIR = os.path.join(OUTPUT_DIR, "gui")
SPRITES_DIR = os.path.join(GUI_DIR, "sprites")


def ensure_dir(path: str) -> None:
    os.makedirs(path, exist_ok=True)


def save_texture(img: Image.Image, relative_path: str, overwrite: bool = False) -> None:
    full_path = os.path.join(GUI_DIR, relative_path)
    ensure_dir(os.path.dirname(full_path))
    if not overwrite and os.path.exists(full_path):
        print(f"[SKIP] Existing {relative_path}")
        return
    img.save(full_path)
    print(f"[OK] Created {relative_path}")


def create_mana_bar() -> Image.Image:
    img = Image.new("RGBA", (182, 5), (0, 0, 0, 0))
    draw = ImageDraw.Draw(img)
    draw.rectangle([0, 0, 181, 4], fill=(0, 0, 0, 128), outline=(255, 255, 255, 180))
    return img


def create_mana_fill(color: tuple[int, int, int]) -> Image.Image:
    img = Image.new("RGBA", (182, 5), (0, 0, 0, 0))
    draw = ImageDraw.Draw(img)
    for x in range(0, 182):
        brightness = 1.0 - (x / 182.0) * 0.3
        adjusted = tuple(int(c * brightness) for c in color)
        draw.line([(x, 1), (x, 3)], fill=adjusted)
    return img


def create_spell_slot() -> Image.Image:
    img = Image.new("RGBA", (18, 18), (0, 0, 0, 0))
    draw = ImageDraw.Draw(img)
    draw.rectangle([0, 0, 17, 17], fill=(139, 69, 19, 180), outline=(0, 0, 0, 255))
    draw.rectangle([1, 1, 16, 16], fill=(101, 67, 33, 200))
    draw.line([(1, 1), (16, 1)], fill=(180, 140, 80, 100))
    draw.line([(1, 1), (1, 16)], fill=(180, 140, 80, 100))
    return img


def create_altar_gui() -> Image.Image:
    img = Image.new("RGBA", (176, 166), (0, 0, 0, 0))
    draw = ImageDraw.Draw(img)
    draw.rectangle([0, 0, 175, 165], fill=(40, 30, 50, 240))
    border = SCHOOL_COLORS["arcane"]["base"]
    draw.rectangle([0, 0, 175, 165], outline=border, width=2)
    grid_x, grid_y = 30, 17
    slot_size = 18
    for row in range(3):
        for col in range(3):
            x = grid_x + col * slot_size
            y = grid_y + row * slot_size
            draw.rectangle([x, y, x + 17, y + 17], fill=(70, 50, 90), outline=border)
    output_x, output_y = 124, 35
    draw.rectangle([output_x, output_y, output_x + 25, output_y + 25], fill=(90, 60, 120), outline=(255, 215, 0), width=2)
    draw.rectangle([0, 0, 175, 16], fill=(60, 40, 80))
    return img


def create_spell_book_gui() -> Image.Image:
    img = Image.new("RGBA", (252, 180), (0, 0, 0, 0))
    draw = ImageDraw.Draw(img)
    parchment = (245, 222, 179)
    draw.rectangle([5, 5, 120, 175], fill=parchment, outline=(139, 69, 19), width=2)
    draw.rectangle([131, 5, 246, 175], fill=parchment, outline=(139, 69, 19), width=2)
    draw.rectangle([121, 5, 130, 175], fill=(101, 67, 33))
    corner = (139, 69, 19)
    for page_x in [5, 131]:
        for y_offset in [5, 170]:
            draw.line([(page_x, y_offset), (page_x + 5, y_offset)], fill=corner, width=2)
            draw.line([(page_x, y_offset), (page_x, y_offset + 5)], fill=corner, width=2)
    return img


def generate_gui_textures(overwrite: bool = False) -> None:
    print("\n[GUI] GUI Textures")
    ensure_dir(GUI_DIR)
    ensure_dir(SPRITES_DIR)

    save_texture(create_mana_bar(), os.path.join("sprites", "mana_bar.png"), overwrite)

    mana_fills = {
        "personal": SCHOOL_COLORS.get("water", {"accent": (0, 191, 255)})["accent"],
        "aura": SCHOOL_COLORS.get("arcane", {"accent": (186, 85, 211)})["accent"],
        "reserve": SCHOOL_COLORS.get("fire", {"accent": (255, 140, 0)})["accent"],
    }
    for pool, color in mana_fills.items():
        save_texture(create_mana_fill(color), os.path.join("sprites", f"mana_bar_{pool}.png"), overwrite)

    save_texture(create_spell_slot(), os.path.join("sprites", "spell_slot.png"), overwrite)
    save_texture(create_altar_gui(), "arcane_altar.png", overwrite)
    save_texture(create_spell_book_gui(), "spell_book.png", overwrite)


def parse_args() -> argparse.Namespace:
    parser = argparse.ArgumentParser(description="Generate Mana And Magic GUI textures")
    parser.add_argument("--overwrite", action="store_true", help="Overwrite existing textures")
    return parser.parse_args()


def main() -> None:
    args = parse_args()
    generate_gui_textures(overwrite=args.overwrite)
    print("\n[OK] GUI texture generation complete")
    print(f"[OUT] Output: {GUI_DIR}")


if __name__ == "__main__":
    main()
