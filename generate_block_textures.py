"""
Block texture generator for Mana And Magic.
Creates ore variants for all gemstones plus ritual/building blocks.
"""

from __future__ import annotations

import argparse
import os
from typing import Tuple

from PIL import Image, ImageDraw

from texture_palettes import (
    DEEPSLATE_BASE,
    GEM_COLORS,
    ORE_VEIN_ALPHA,
    ORE_VEIN_VARIATION,
    OUTPUT_DIR,
    SCHOOL_COLORS,
    STONE_BASE,
    TEXTURE_SIZE,
)

BLOCK_DIR = os.path.join(OUTPUT_DIR, "block")


def clamp(value: int) -> int:
    return max(0, min(255, value))


def ensure_dir(path: str) -> None:
    os.makedirs(path, exist_ok=True)


def save_texture(img: Image.Image, relative_path: str, overwrite: bool = False) -> None:
    full_path = os.path.join(BLOCK_DIR, relative_path)
    ensure_dir(os.path.dirname(full_path))
    if not overwrite and os.path.exists(full_path):
        print(f"[SKIP] Existing {relative_path}")
        return
    img.save(full_path)
    print(f"[OK] Created {relative_path}")


def make_variated_base(base: Tuple[int, int, int, int]) -> Image.Image:
    img = Image.new("RGBA", (TEXTURE_SIZE, TEXTURE_SIZE), base)
    draw = ImageDraw.Draw(img)
    for x in range(TEXTURE_SIZE):
        for y in range(TEXTURE_SIZE):
            if (x * 13 + y * 7) % 9 == 0:
                delta = ((x + y) % 5) - 2
                r, g, b, a = base
                draw.point((x, y), fill=(clamp(r + delta), clamp(g + delta), clamp(b + delta), a))
    return img


def add_veins(draw: ImageDraw.ImageDraw, gem_color: Tuple[int, int, int], alpha: int) -> None:
    for x in range(TEXTURE_SIZE):
        for y in range(TEXTURE_SIZE):
            if (x * y + y) % 11 == 0 or (x + y * 2) % 13 == 1:
                jitter = ((x * 3 + y * 5) % (ORE_VEIN_VARIATION * 2)) - ORE_VEIN_VARIATION
                color = (
                    clamp(gem_color[0] + jitter),
                    clamp(gem_color[1] + jitter),
                    clamp(gem_color[2] + jitter),
                    alpha,
                )
                draw.point((x, y), fill=color)


def create_ore_texture(base: Tuple[int, int, int, int], gem_color: Tuple[int, int, int]) -> Image.Image:
    img = make_variated_base(base)
    draw = ImageDraw.Draw(img)
    add_veins(draw, gem_color, ORE_VEIN_ALPHA)
    return img


def create_infused_stone(base: Tuple[int, int, int, int], vein_color: Tuple[int, int, int]) -> Image.Image:
    img = make_variated_base(base)
    draw = ImageDraw.Draw(img)
    add_veins(draw, vein_color, 200)
    return img


def create_bricks(base: Tuple[int, int, int, int], vein_color: Tuple[int, int, int]) -> Image.Image:
    img = Image.new("RGBA", (TEXTURE_SIZE, TEXTURE_SIZE), base)
    draw = ImageDraw.Draw(img)
    mortar = tuple(clamp(c - 30) for c in base[:3]) + (base[3],)
    # Horizontal courses
    draw.line([(0, 4), (15, 4)], fill=mortar)
    draw.line([(0, 12), (15, 12)], fill=mortar)
    # Vertical offsets
    draw.line([(8, 0), (8, 3)], fill=mortar)
    draw.line([(8, 5), (8, 11)], fill=mortar)
    draw.line([(8, 13), (8, 15)], fill=mortar)
    add_veins(draw, vein_color, 200)
    return img


def create_pedestal_top() -> Image.Image:
    img = Image.new("RGBA", (TEXTURE_SIZE, TEXTURE_SIZE), (47, 47, 47, 255))
    draw = ImageDraw.Draw(img)
    draw.ellipse([4, 4, 12, 12], outline=(85, 85, 170, 255), width=1)
    draw.line([(8, 5), (8, 11)], fill=(119, 119, 204, 255), width=1)
    draw.line([(5, 8), (11, 8)], fill=(119, 119, 204, 255), width=1)
    return img


def create_pedestal_side() -> Image.Image:
    img = Image.new("RGBA", (TEXTURE_SIZE, TEXTURE_SIZE), (47, 47, 47, 255))
    draw = ImageDraw.Draw(img)
    draw.line([(0, 0), (0, 15)], fill=(31, 31, 31, 255))
    draw.line([(15, 0), (15, 15)], fill=(31, 31, 31, 255))
    draw.line([(4, 0), (4, 15)], fill=(63, 63, 63, 255))
    draw.line([(11, 0), (11, 15)], fill=(63, 63, 63, 255))
    return img


def create_candle() -> Image.Image:
    img = Image.new("RGBA", (TEXTURE_SIZE, TEXTURE_SIZE), (0, 0, 0, 0))
    draw = ImageDraw.Draw(img)
    draw.rectangle([6, 4, 9, 15], fill=(245, 245, 220, 255))
    draw.rectangle([6, 2, 9, 4], fill=(255, 215, 0, 255))
    draw.point((7, 1), fill=(255, 69, 0, 255))
    draw.point((8, 1), fill=(255, 69, 0, 255))
    return img


def create_altar_top() -> Image.Image:
    img = Image.new("RGBA", (TEXTURE_SIZE, TEXTURE_SIZE), (75, 0, 130, 255))
    draw = ImageDraw.Draw(img)
    draw.ellipse([5, 5, 11, 11], outline=(139, 0, 255, 255), width=1)
    draw.ellipse([2, 2, 6, 6], fill=(147, 112, 219, 255))
    draw.ellipse([10, 2, 14, 6], fill=(147, 112, 219, 255))
    draw.ellipse([2, 10, 6, 14], fill=(147, 112, 219, 255))
    draw.ellipse([10, 10, 14, 14], fill=(147, 112, 219, 255))
    draw.line([(8, 6), (8, 10)], fill=(186, 85, 211, 255))
    draw.line([(6, 8), (10, 8)], fill=(186, 85, 211, 255))
    return img


def create_altar_side() -> Image.Image:
    img = Image.new("RGBA", (TEXTURE_SIZE, TEXTURE_SIZE), (75, 0, 130, 255))
    draw = ImageDraw.Draw(img)
    draw.rectangle([0, 0, 15, 2], fill=(102, 51, 153, 255))
    draw.rectangle([0, 13, 15, 15], fill=(102, 51, 153, 255))
    draw.point((4, 6), fill=(139, 0, 255, 255))
    draw.point((11, 9), fill=(139, 0, 255, 255))
    return img


def create_altar_bottom() -> Image.Image:
    img = Image.new("RGBA", (TEXTURE_SIZE, TEXTURE_SIZE), (47, 47, 47, 255))
    draw = ImageDraw.Draw(img)
    for i in range(0, 16, 4):
        draw.line([(i, 0), (i + 4, 4)], fill=(31, 31, 31, 255))
        draw.line([(0, i), (4, i + 4)], fill=(31, 31, 31, 255))
    return img


def create_gift_box() -> Image.Image:
    img = Image.new("RGBA", (TEXTURE_SIZE, TEXTURE_SIZE), (0, 0, 0, 0))
    draw = ImageDraw.Draw(img)
    box_color = (200, 0, 0, 255)
    draw.rectangle([0, 0, 15, 15], fill=box_color)
    ribbon = (255, 215, 0, 255)
    draw.rectangle([0, 7, 15, 8], fill=ribbon)
    draw.rectangle([7, 0, 8, 15], fill=ribbon)
    draw.rectangle([6, 6, 9, 9], fill=ribbon)
    draw.point((5, 7), fill=ribbon)
    draw.point((10, 7), fill=ribbon)
    return img


def generate_ores(overwrite: bool) -> None:
    for gem, color in GEM_COLORS.items():
        save_texture(create_ore_texture(STONE_BASE, color), f"{gem}_ore.png", overwrite)
        save_texture(create_ore_texture(DEEPSLATE_BASE, color), f"deepslate_{gem}_ore.png", overwrite)


def generate_infusion_blocks(overwrite: bool) -> None:
    cyan = SCHOOL_COLORS.get("water", {"accent": (0, 191, 255)})["accent"]
    save_texture(create_infused_stone(STONE_BASE, cyan), "mana_infused_stone.png", overwrite)
    save_texture(create_bricks(STONE_BASE, cyan), "mana_infused_stone_bricks.png", overwrite)


def generate_ritual_blocks(overwrite: bool) -> None:
    save_texture(create_pedestal_top(), "ritual_pedestal_top.png", overwrite)
    save_texture(create_pedestal_side(), "ritual_pedestal_side.png", overwrite)
    save_texture(create_candle(), "ritual_candle.png", overwrite)
    save_texture(create_altar_top(), "arcane_altar_top.png", overwrite)
    save_texture(create_altar_side(), "arcane_altar_side.png", overwrite)
    save_texture(create_altar_bottom(), "arcane_altar_bottom.png", overwrite)
    save_texture(create_gift_box(), "gift_box.png", overwrite)


def generate_block_textures(overwrite: bool = False) -> None:
    print("\n[BLOCKS] Block Textures")
    ensure_dir(BLOCK_DIR)
    generate_ores(overwrite)
    generate_infusion_blocks(overwrite)
    generate_ritual_blocks(overwrite)


def parse_args() -> argparse.Namespace:
    parser = argparse.ArgumentParser(description="Generate Mana And Magic block textures")
    parser.add_argument("--overwrite", action="store_true", help="Overwrite existing textures")
    return parser.parse_args()


def main() -> None:
    args = parse_args()
    generate_block_textures(overwrite=args.overwrite)
    print("\n[OK] Block texture generation complete")
    print(f"[OUT] Output: {BLOCK_DIR}")


if __name__ == "__main__":
    main()
