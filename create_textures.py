"""
Texture Creation Script for Mana And Magic
Generates missing block textures using PIL (Pillow)
Install: pip install pillow
"""

from PIL import Image, ImageDraw
import os

texture_dir = "src/main/resources/assets/mam/textures"

def create_gradient_crystal(filename, center_color, edge_color):
    """Create a gradient crystal texture"""
    img = Image.new('RGBA', (16, 16), edge_color)
    draw = ImageDraw.Draw(img)

    # Create circular gradient effect
    for radius in range(8, 0, -1):
        alpha = int(255 * (radius / 8))
        color = tuple(list(center_color[:3]) + [alpha])
        draw.ellipse([8-radius, 8-radius, 8+radius, 8+radius], fill=color)

    img.save(os.path.join(texture_dir, "block", filename))
    print(f"  ✓ Created {filename}")

def create_stone_texture(filename, base_color, vein_color):
    """Create a stone texture with magical veins"""
    img = Image.new('RGBA', (16, 16), base_color)
    draw = ImageDraw.Draw(img)

    # Add some texture variation
    for i in range(16):
        for j in range(16):
            if (i + j) % 5 == 0:
                darker = tuple(max(0, c - 20) for c in base_color[:3]) + (255,)
                draw.point((i, j), fill=darker)

    # Draw magical veins
    draw.line([(2, 3), (5, 6)], fill=vein_color, width=1)
    draw.line([(8, 4), (11, 7)], fill=vein_color, width=1)
    draw.line([(3, 10), (7, 13)], fill=vein_color, width=1)
    draw.line([(10, 9), (13, 12)], fill=vein_color, width=1)

    img.save(os.path.join(texture_dir, "block", filename))
    print(f"  ✓ Created {filename}")

def create_brick_texture(filename, base_color, vein_color):
    """Create a brick pattern with magical veins"""
    img = Image.new('RGBA', (16, 16), base_color)
    draw = ImageDraw.Draw(img)

    mortar = tuple(max(0, c - 30) for c in base_color[:3]) + (255,)

    # Draw brick pattern
    draw.line([(0, 4), (15, 4)], fill=mortar)
    draw.line([(0, 12), (15, 12)], fill=mortar)
    draw.line([(8, 0), (8, 3)], fill=mortar)
    draw.line([(8, 5), (8, 11)], fill=mortar)
    draw.line([(8, 13), (8, 15)], fill=mortar)

    # Add magical veins
    draw.line([(2, 2), (4, 2)], fill=vein_color)
    draw.line([(10, 6), (12, 6)], fill=vein_color)
    draw.line([(4, 10), (6, 10)], fill=vein_color)
    draw.line([(12, 14), (14, 14)], fill=vein_color)

    img.save(os.path.join(texture_dir, "block", filename))
    print(f"  ✓ Created {filename}")

def create_pedestal_top(filename):
    """Create ritual pedestal top with arcane rune"""
    img = Image.new('RGBA', (16, 16), (47, 47, 47, 255))
    draw = ImageDraw.Draw(img)

    # Draw arcane circle
    draw.ellipse([4, 4, 12, 12], outline=(85, 85, 170, 255), width=1)

    # Draw rune cross
    draw.line([(8, 5), (8, 11)], fill=(119, 119, 204, 255), width=1)
    draw.line([(5, 8), (11, 8)], fill=(119, 119, 204, 255), width=1)

    img.save(os.path.join(texture_dir, "block", filename))
    print(f"  ✓ Created {filename}")

def create_pedestal_side(filename):
    """Create ritual pedestal side texture"""
    img = Image.new('RGBA', (16, 16), (47, 47, 47, 255))
    draw = ImageDraw.Draw(img)

    # Add pillar lines
    draw.line([(0, 0), (0, 15)], fill=(31, 31, 31, 255))
    draw.line([(15, 0), (15, 15)], fill=(31, 31, 31, 255))
    draw.line([(4, 0), (4, 15)], fill=(63, 63, 63, 255))
    draw.line([(11, 0), (11, 15)], fill=(63, 63, 63, 255))

    img.save(os.path.join(texture_dir, "block", filename))
    print(f"  ✓ Created {filename}")

def create_candle(filename):
    """Create ritual candle texture"""
    img = Image.new('RGBA', (16, 16), (0, 0, 0, 0))
    draw = ImageDraw.Draw(img)

    # Candle body (white/beige)
    draw.rectangle([6, 4, 9, 15], fill=(245, 245, 220, 255))

    # Wick top (gold)
    draw.rectangle([6, 2, 9, 4], fill=(255, 215, 0, 255))

    # Flame (orange/red)
    draw.point((7, 1), fill=(255, 69, 0, 255))
    draw.point((8, 1), fill=(255, 69, 0, 255))

    img.save(os.path.join(texture_dir, "block", filename))
    print(f"  ✓ Created {filename}")

def create_altar_top(filename):
    """Create arcane altar top with magical runes"""
    img = Image.new('RGBA', (16, 16), (75, 0, 130, 255))
    draw = ImageDraw.Draw(img)

    # Central magical circle
    draw.ellipse([5, 5, 11, 11], outline=(139, 0, 255, 255), width=1)

    # Corner runes
    draw.ellipse([2, 2, 6, 6], fill=(147, 112, 219, 255))
    draw.ellipse([10, 2, 14, 6], fill=(147, 112, 219, 255))
    draw.ellipse([2, 10, 6, 14], fill=(147, 112, 219, 255))
    draw.ellipse([10, 10, 14, 14], fill=(147, 112, 219, 255))

    # Cross in center
    draw.line([(8, 6), (8, 10)], fill=(186, 85, 211, 255))
    draw.line([(6, 8), (10, 8)], fill=(186, 85, 211, 255))

    img.save(os.path.join(texture_dir, "block", filename))
    print(f"  ✓ Created {filename}")

def create_altar_side(filename):
    """Create arcane altar side texture"""
    img = Image.new('RGBA', (16, 16), (75, 0, 130, 255))
    draw = ImageDraw.Draw(img)

    # Top and bottom borders
    draw.rectangle([0, 0, 15, 2], fill=(102, 51, 153, 255))
    draw.rectangle([0, 13, 15, 15], fill=(102, 51, 153, 255))

    # Add some magical sparkles
    draw.point((4, 6), fill=(139, 0, 255, 255))
    draw.point((11, 9), fill=(139, 0, 255, 255))

    img.save(os.path.join(texture_dir, "block", filename))
    print(f"  ✓ Created {filename}")

def create_altar_bottom(filename):
    """Create arcane altar bottom texture"""
    img = Image.new('RGBA', (16, 16), (47, 47, 47, 255))
    draw = ImageDraw.Draw(img)

    # Add diagonal lines for texture
    for i in range(0, 16, 4):
        draw.line([(i, 0), (i+4, 4)], fill=(31, 31, 31, 255))
        draw.line([(0, i), (4, i+4)], fill=(31, 31, 31, 255))

    img.save(os.path.join(texture_dir, "block", filename))
    print(f"  ✓ Created {filename}")

def create_spell_projectile(filename):
    """Create spell projectile entity texture"""
    img = Image.new('RGBA', (16, 16), (0, 0, 0, 0))
    draw = ImageDraw.Draw(img)

    # Create glowing orb with gradient
    for radius in range(8, 0, -1):
        alpha = int(255 * (radius / 8))
        brightness = 255 - (8 - radius) * 20
        color = (brightness, brightness, brightness, alpha)
        draw.ellipse([8-radius, 8-radius, 8+radius, 8+radius], fill=color)

    img.save(os.path.join(texture_dir, "entity", filename))
    print(f"  ✓ Created {filename}")

def main():
    print("Creating missing block textures...")

    # Ensure directories exist
    os.makedirs(os.path.join(texture_dir, "block"), exist_ok=True)
    os.makedirs(os.path.join(texture_dir, "entity"), exist_ok=True)

    print("\n📦 Mana Crystals:")
    create_gradient_crystal("personal_mana_crystal.png", (0, 255, 255, 255), (0, 191, 255, 255))
    create_gradient_crystal("aura_mana_crystal.png", (186, 85, 211, 255), (147, 112, 219, 255))
    create_gradient_crystal("reserve_mana_crystal.png", (255, 165, 0, 255), (255, 140, 0, 255))

    print("\n🕯️  Ritual Blocks:")
    create_pedestal_top("ritual_pedestal_top.png")
    create_pedestal_side("ritual_pedestal_side.png")
    create_candle("ritual_candle.png")

    print("\n🧱 Building Blocks:")
    create_stone_texture("mana_infused_stone.png", (128, 128, 128, 255), (0, 191, 255, 255))
    create_brick_texture("mana_infused_stone_bricks.png", (112, 112, 112, 255), (0, 191, 255, 255))

    print("\n✨ Arcane Altar:")
    create_altar_top("arcane_altar_top.png")
    create_altar_side("arcane_altar_side.png")
    create_altar_bottom("arcane_altar_bottom.png")

    print("\n🔮 Entity Textures:")
    create_spell_projectile("spell_projectile.png")

    print("\n✅ Texture creation complete!")
    print(f"📁 Created textures in: {texture_dir}")
    print("\n=== NEXT STEPS ===")
    print("1. Run: ./gradlew build")
    print("2. Run: ./gradlew runClient")
    print("3. Check in-game appearance")
    print("4. Refine textures manually using GIMP/Aseprite if needed")

if __name__ == "__main__":
    try:
        main()
    except ImportError:
        print("❌ ERROR: Pillow (PIL) not installed.")
        print("📦 Install with: pip install pillow")
        print("\nOr use ImageMagick instead:")
        print("   winget install ImageMagick.ImageMagick")
        print("   Then run: .\\create_textures.ps1")
