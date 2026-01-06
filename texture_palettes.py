"""
Shared color palettes and texture constants for Mana And Magic texture generators.
"""

from __future__ import annotations

TEXTURE_SIZE = 16
OUTPUT_DIR = "src/main/resources/assets/mam/textures"

# School color palette: base fills and brighter accents for highlights/glow.
SCHOOL_COLORS = {
    "air": {"base": (200, 200, 210), "accent": (235, 235, 245)},
    "arcane": {"base": (138, 43, 226), "accent": (186, 85, 211)},
    "blood": {"base": (178, 34, 34), "accent": (220, 20, 60)},
    "chaos": {"base": (199, 21, 133), "accent": (255, 69, 0)},
    "dark": {"base": (25, 25, 35), "accent": (128, 0, 128)},
    "earth": {"base": (109, 92, 64), "accent": (46, 139, 87)},
    "fire": {"base": (220, 20, 60), "accent": (255, 140, 0)},
    "ice": {"base": (135, 206, 235), "accent": (173, 216, 230)},
    "light": {"base": (255, 250, 205), "accent": (255, 215, 0)},
    "nature": {"base": (34, 139, 34), "accent": (144, 238, 144)},
    "thunder": {"base": (255, 215, 0), "accent": (135, 206, 250)},
    "void": {"base": (72, 61, 139), "accent": (123, 104, 238)},
    "water": {"base": (30, 144, 255), "accent": (0, 191, 255)},
}

# Gemstone palette used for items and ore overlays.
GEM_COLORS = {
    "ruby": (220, 20, 60),
    "sapphire": (15, 82, 186),
    "tanzanite": (90, 70, 170),
    "apatite": (64, 224, 208),
    "aquamarine": (69, 190, 232),
    "moonstone": (180, 200, 255),
    "rhodonite": (216, 98, 125),
    "topaz": (255, 185, 60),
    "tourmaline": (46, 139, 87),
    "carnelian": (205, 92, 60),
    "citrine": (238, 201, 0),
    "jade": (0, 168, 107),
    "peridot": (173, 205, 50),
    "sodalite": (70, 80, 160),
    "hematite": (112, 128, 144),
}

STONE_BASE = (128, 128, 128, 255)
DEEPSLATE_BASE = (72, 72, 80, 255)
ORE_VEIN_ALPHA = 235
ORE_VEIN_VARIATION = 22

# Simple helper to create RGBA tuples.
def rgba(rgb: tuple[int, int, int], alpha: int = 255) -> tuple[int, int, int, int]:
    return (rgb[0], rgb[1], rgb[2], alpha)
