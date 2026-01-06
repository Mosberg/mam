"""
Generate item textures for Mana And Magic.
Supports wands (13 schools, 2 tiers), spell books, gemstones, and mana/ritual items.
"""

from __future__ import annotations

import argparse
import os

from PIL import Image, ImageDraw

from texture_palettes import GEM_COLORS, OUTPUT_DIR, SCHOOL_COLORS, TEXTURE_SIZE

ITEM_DIR = os.path.join(OUTPUT_DIR, "item")


def ensure_dir(path: str) -> None:
    os.makedirs(path, exist_ok=True)


def save_texture(img: Image.Image, relative_path: str, overwrite: bool = False) -> None:
    full_path = os.path.join(ITEM_DIR, relative_path)
    ensure_dir(os.path.dirname(full_path))
    if not overwrite and os.path.exists(full_path):
        print(f"✓ Skipping existing {relative_path}")
        return
    img.save(full_path)
    print(f"  ✓ Created {relative_path}")


def create_wand_texture(school: str, tier: str) -> Image.Image:
    colors = SCHOOL_COLORS[school]
    base = colors["base"]
    accent = colors["accent"]

    img = Image.new("RGBA", (TEXTURE_SIZE, TEXTURE_SIZE), (0, 0, 0, 0))
    draw = ImageDraw.Draw(img)

    handle_color = (101, 67, 33)
    for y in range(2, 12):
        for x in (7, 8):
            draw.point((x, y), fill=handle_color)

    if tier == "novice":
        draw.rectangle([7, 0, 8, 1], fill=base)
    else:
        draw.rectangle([6, 0, 9, 2], fill=base)
        draw.point((7, 1), fill=accent)
        draw.point((8, 1), fill=accent)
        draw.point((5, 3), fill=accent + (180,))
        draw.point((10, 3), fill=accent + (180,))

    draw.point((7, 0), fill=accent)
    return img


def generate_wand_textures(overwrite: bool = False) -> None:
    for school in SCHOOL_COLORS.keys():
        for tier in ("novice", "master"):
            save_texture(create_wand_texture(school, tier), f"{school}_wand_{tier}.png", overwrite)

    if "arcane" not in SCHOOL_COLORS:
        SCHOOL_COLORS["arcane"] = {"base": (186, 85, 211), "accent": (255, 215, 0)}
    for name in ("wand_novice.png", "wand_master.png", "wand_apprentice.png", "wand_adept.png"):
        save_texture(create_wand_texture("arcane", "master"), os.path.join("wand", name), overwrite)


def create_spell_book_texture(cover_color: tuple[int, int, int], accent_color: tuple[int, int, int]) -> Image.Image:
    img = Image.new("RGBA", (TEXTURE_SIZE, TEXTURE_SIZE), (0, 0, 0, 0))
    draw = ImageDraw.Draw(img)

    outline = (0, 0, 0)
    draw.rectangle([3, 2, 12, 13], fill=cover_color, outline=outline)
    draw.line([(12, 3), (12, 12)], fill=(255, 255, 255))
    draw.rectangle([6, 5, 9, 8], fill=accent_color)
    draw.point((7, 6), fill=cover_color)
    draw.point((8, 6), fill=cover_color)
    draw.point((7, 7), fill=cover_color)
    draw.line([(3, 2), (3, 13)], fill=outline)
    return img


def generate_spell_book_textures(overwrite: bool = False) -> None:
    for school, colors in SCHOOL_COLORS.items():
        cover, accent = colors["base"], colors["accent"]
        save_texture(create_spell_book_texture(cover, accent), f"{school}_spell_book.png", overwrite)

    neutral_cover = (139, 69, 19)
    neutral_accent = (255, 215, 0)
    for tier in ("novice", "apprentice", "adept", "master"):
        save_texture(
            create_spell_book_texture(neutral_cover, neutral_accent),
            os.path.join("spellbook", f"spell_book_{tier}.png"),
            overwrite,
        )


def create_gemstone_item(color: tuple[int, int, int]) -> Image.Image:
    img = Image.new("RGBA", (TEXTURE_SIZE, TEXTURE_SIZE), (0, 0, 0, 0))
    draw = ImageDraw.Draw(img)
    points = [(8, 2), (12, 8), (10, 14), (6, 14), (4, 8)]
    outline = tuple(min(255, int(c * 0.7)) for c in color) + (255,)
    draw.polygon(points, fill=color + (255,), outline=outline)
    draw.line([(7, 4), (10, 10)], fill=outline, width=1)
    draw.line([(9, 6), (7, 12)], fill=outline, width=1)
    return img


def generate_gemstone_items(overwrite: bool = False) -> None:
    for gem, color in GEM_COLORS.items():
        save_texture(create_gemstone_item(color), f"{gem}.png", overwrite)


def create_bottle_texture(liquid_color: tuple[int, int, int], glow_color: tuple[int, int, int]) -> Image.Image:
    img = Image.new("RGBA", (TEXTURE_SIZE, TEXTURE_SIZE), (0, 0, 0, 0))
    draw = ImageDraw.Draw(img)
    draw.rectangle([5, 3, 10, 14], outline=(200, 200, 220, 255), width=1)
    draw.rectangle([6, 1, 9, 3], fill=(139, 69, 19, 255))
    draw.rectangle([6, 7, 9, 13], fill=liquid_color + (255,))
    draw.point((7, 8), fill=glow_color + (255,))
    draw.point((8, 9), fill=glow_color + (255,))
    return img


def create_vial_texture(essence_color: tuple[int, int, int]) -> Image.Image:
    img = Image.new("RGBA", (TEXTURE_SIZE, TEXTURE_SIZE), (0, 0, 0, 0))
    draw = ImageDraw.Draw(img)
    draw.ellipse([5, 5, 10, 13], outline=(180, 180, 200, 255), width=1)
    draw.rectangle([6, 3, 9, 5], fill=(100, 100, 120, 255))
    draw.rectangle([7, 2, 8, 3], fill=(80, 80, 100, 255))
    for i in range(6, 10):
        alpha = 255 - (i - 6) * 30
        color = essence_color + (alpha,)
        draw.point((i, 7 + (i % 2)), fill=color)
        draw.point((14 - i, 9 + (i % 2)), fill=color)
    return img


def create_focus_texture() -> Image.Image:
    img = Image.new("RGBA", (TEXTURE_SIZE, TEXTURE_SIZE), (0, 0, 0, 0))
    draw = ImageDraw.Draw(img)
    draw.ellipse([6, 6, 10, 10], fill=(255, 215, 0, 255), outline=(218, 165, 32, 255))
    prongs = [(8, 4, 8, 6), (8, 10, 8, 12), (4, 8, 6, 8), (10, 8, 12, 8)]
    for x1, y1, x2, y2 in prongs:
        draw.line([(x1, y1), (x2, y2)], fill=(160, 160, 160, 255), width=1)
    for x, y in [(8, 3), (8, 13), (3, 8), (13, 8)]:
        draw.point((x, y), fill=(192, 192, 192, 255))
    return img


def create_component_texture() -> Image.Image:
    img = Image.new("RGBA", (TEXTURE_SIZE, TEXTURE_SIZE), (0, 0, 0, 0))
    draw = ImageDraw.Draw(img)
    colors = [(255, 255, 0), (255, 215, 0), (255, 192, 128)]
    draw.ellipse([4, 10, 12, 14], fill=colors[2])
    draw.ellipse([5, 7, 11, 11], fill=colors[1])
    draw.ellipse([6, 5, 10, 8], fill=colors[0])
    for x, y in [(4, 6), (12, 8), (8, 4), (10, 12)]:
        draw.point((x, y), fill=(255, 255, 255, 255))
    return img


def create_scroll_texture() -> Image.Image:
    img = Image.new("RGBA", (TEXTURE_SIZE, TEXTURE_SIZE), (0, 0, 0, 0))
    draw = ImageDraw.Draw(img)
    draw.rectangle([5, 2, 11, 14], fill=(245, 222, 179, 255), outline=(210, 180, 140, 255))
    draw.rectangle([4, 2, 5, 14], fill=(139, 90, 43, 255))
    draw.rectangle([11, 2, 12, 14], fill=(139, 90, 43, 255))
    draw.line([(6, 5), (10, 5)], fill=(138, 43, 226, 255), width=1)
    draw.line([(7, 7), (10, 7)], fill=(138, 43, 226, 255), width=1)
    draw.line([(6, 9), (9, 9)], fill=(138, 43, 226, 255), width=1)
    draw.line([(7, 11), (10, 11)], fill=(138, 43, 226, 255), width=1)
    return img


def create_book_texture(cover_color: tuple[int, int, int], accent_color: tuple[int, int, int]) -> Image.Image:
    img = Image.new("RGBA", (TEXTURE_SIZE, TEXTURE_SIZE), (0, 0, 0, 0))
    draw = ImageDraw.Draw(img)
    draw.rectangle([4, 2, 12, 14], fill=cover_color, outline=(0, 0, 0, 255))
    draw.line([(4, 2), (4, 14)], fill=(0, 0, 0, 255), width=1)
    draw.rectangle([12, 3, 13, 13], fill=(255, 255, 255, 255))
    draw.ellipse([7, 6, 11, 10], outline=accent_color, width=1)
    draw.line([(9, 6), (9, 10)], fill=accent_color)
    draw.line([(7, 8), (11, 8)], fill=accent_color)
    return img


def create_artifact_texture() -> Image.Image:
    img = Image.new("RGBA", (TEXTURE_SIZE, TEXTURE_SIZE), (0, 0, 0, 0))
    draw = ImageDraw.Draw(img)
    draw.rectangle([6, 6, 10, 10], fill=(255, 0, 255, 255), outline=(200, 0, 200, 255))
    draw.rectangle([5, 5, 11, 11], outline=(255, 215, 0, 255), width=1)
    beams = [((8, 2), (8, 5)), ((8, 11), (8, 14)), ((2, 8), (5, 8)), ((11, 8), (14, 8))]
    for start, end in beams:
        draw.line([start, end], fill=(255, 215, 0, 255), width=1)
    for x, y in [(8, 1), (8, 15), (1, 8), (15, 8)]:
        draw.point((x, y), fill=(255, 255, 0, 255))
    return img


def create_enchanted_gem_texture() -> Image.Image:
    img = Image.new("RGBA", (TEXTURE_SIZE, TEXTURE_SIZE), (0, 0, 0, 0))
    draw = ImageDraw.Draw(img)
    points = [(8, 3), (12, 8), (8, 13), (4, 8)]
    draw.polygon(points, fill=(0, 255, 255, 255), outline=(0, 200, 200, 255))
    draw.line([(8, 3), (8, 13)], fill=(200, 255, 255, 255))
    draw.line([(4, 8), (12, 8)], fill=(200, 255, 255, 255))
    draw.line([(6, 6), (10, 10)], fill=(200, 255, 255, 255))
    draw.line([(10, 6), (6, 10)], fill=(200, 255, 255, 255))
    for x, y in [(5, 5), (11, 5), (5, 11), (11, 11)]:
        draw.point((x, y), fill=(255, 255, 255, 255))
    return img


def generate_misc_items(overwrite: bool = False) -> None:
    save_texture(create_bottle_texture((0, 191, 255), (100, 220, 255)), "mana_bottle.png", overwrite)
    save_texture(create_gemstone_item((0, 191, 255)), "mana_shard.png", overwrite)
    save_texture(create_vial_texture((186, 85, 211)), "essence_vial.png", overwrite)
    save_texture(create_focus_texture(), "ritual_focus.png", overwrite)
    save_texture(create_component_texture(), "spell_component.png", overwrite)
    save_texture(create_scroll_texture(), "spell_scroll.png", overwrite)
    save_texture(create_book_texture((75, 0, 130), (255, 215, 0)), "grimoire.png", overwrite)
    save_texture(create_book_texture((178, 34, 34), (255, 215, 0)), "spell_tome.png", overwrite)
    save_texture(create_artifact_texture(), "catalyst_artifact.png", overwrite)
    save_texture(create_enchanted_gem_texture(), "enchanted_gem.png", overwrite)


def generate_item_textures(overwrite: bool = False) -> None:
    print("\n📦 Item Textures")
    generate_wand_textures(overwrite)
    generate_spell_book_textures(overwrite)
    generate_gemstone_items(overwrite)
    generate_misc_items(overwrite)


def parse_args() -> argparse.Namespace:
    parser = argparse.ArgumentParser(description="Generate Mana And Magic item textures")
    parser.add_argument("--overwrite", action="store_true", help="Overwrite existing textures")
    return parser.parse_args()


def main() -> None:
    args = parse_args()
    ensure_dir(ITEM_DIR)
    generate_item_textures(overwrite=args.overwrite)
    print("\n✅ Item texture generation complete")
    print(f"📁 Output: {ITEM_DIR}")


if __name__ == "__main__":
    main()
