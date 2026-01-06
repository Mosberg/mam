"""
Entity texture generator for Mana And Magic.
Creates glowing spell projectile orbs per school palette.
"""

from __future__ import annotations

import argparse
import os

from PIL import Image, ImageDraw

from texture_palettes import OUTPUT_DIR, SCHOOL_COLORS, TEXTURE_SIZE

ENTITY_DIR = os.path.join(OUTPUT_DIR, "entity")


def ensure_dir(path: str) -> None:
    os.makedirs(path, exist_ok=True)


def save_texture(img: Image.Image, relative_path: str, overwrite: bool = False) -> None:
    full_path = os.path.join(ENTITY_DIR, relative_path)
    ensure_dir(os.path.dirname(full_path))
    if not overwrite and os.path.exists(full_path):
        print(f"[SKIP] Existing {relative_path}")
        return
    img.save(full_path)
    print(f"[OK] Created {relative_path}")


def create_projectile(color: tuple[int, int, int]) -> Image.Image:
    img = Image.new("RGBA", (TEXTURE_SIZE, TEXTURE_SIZE), (0, 0, 0, 0))
    draw = ImageDraw.Draw(img)
    for radius in range(8, 0, -1):
        alpha = int(255 * (radius / 8))
        scale = 1.0 - (8 - radius) * 0.05
        fill = (int(color[0] * scale), int(color[1] * scale), int(color[2] * scale), alpha)
        draw.ellipse([8 - radius, 8 - radius, 8 + radius, 8 + radius], fill=fill)
    return img


def generate_entity_textures(overwrite: bool = False) -> None:
    print("\n[ENTITIES] Entity Textures")
    ensure_dir(ENTITY_DIR)
    # Default arcane projectile
    save_texture(create_projectile(SCHOOL_COLORS["arcane"]["accent"]), "spell_projectile.png", overwrite)
    for school, colors in SCHOOL_COLORS.items():
        save_texture(create_projectile(colors["accent"]), f"spell_projectile_{school}.png", overwrite)


def parse_args() -> argparse.Namespace:
    parser = argparse.ArgumentParser(description="Generate Mana And Magic entity textures")
    parser.add_argument("--overwrite", action="store_true", help="Overwrite existing textures")
    return parser.parse_args()


def main() -> None:
    args = parse_args()
    generate_entity_textures(overwrite=args.overwrite)
    print("\n[OK] Entity texture generation complete")
    print(f"[OUT] Output: {ENTITY_DIR}")


if __name__ == "__main__":
    main()
