"""
Generate missing item textures for Mana And Magic mod
"""

from PIL import Image, ImageDraw
import os

texture_dir = "src/main/resources/assets/mam/textures/item"

def create_bottle_texture(filename, liquid_color, glow_color):
    """Create a mana bottle texture"""
    img = Image.new('RGBA', (16, 16), (0, 0, 0, 0))
    draw = ImageDraw.Draw(img)

    # Bottle outline (glass)
    draw.rectangle([5, 3, 10, 14], outline=(200, 200, 220, 255), width=1)

    # Bottle top/cork
    draw.rectangle([6, 1, 9, 3], fill=(139, 69, 19, 255))

    # Liquid inside
    draw.rectangle([6, 7, 9, 13], fill=liquid_color)

    # Glow effect on liquid
    draw.point((7, 8), fill=glow_color)
    draw.point((8, 9), fill=glow_color)

    img.save(os.path.join(texture_dir, filename))
    print(f"  ✓ Created {filename}")

def create_shard_texture(filename, color1, color2):
    """Create a crystal shard texture"""
    img = Image.new('RGBA', (16, 16), (0, 0, 0, 0))
    draw = ImageDraw.Draw(img)

    # Crystal shard shape (diagonal)
    points = [(8, 2), (12, 8), (10, 14), (6, 14), (4, 8)]
    draw.polygon(points, fill=color1, outline=color2)

    # Inner highlight
    draw.line([(7, 4), (10, 10)], fill=color2, width=1)

    img.save(os.path.join(texture_dir, filename))
    print(f"  ✓ Created {filename}")

def create_vial_texture(filename, essence_color):
    """Create an essence vial texture"""
    img = Image.new('RGBA', (16, 16), (0, 0, 0, 0))
    draw = ImageDraw.Draw(img)

    # Vial body
    draw.ellipse([5, 5, 10, 13], outline=(180, 180, 200, 255), width=1)

    # Vial top
    draw.rectangle([6, 3, 9, 5], fill=(100, 100, 120, 255))
    draw.rectangle([7, 2, 8, 3], fill=(80, 80, 100, 255))

    # Essence inside (swirly)
    for i in range(6, 10):
        alpha = 255 - (i - 6) * 30
        color = essence_color[:3] + (alpha,)
        draw.point((i, 7 + (i % 2)), fill=color)
        draw.point((14 - i, 9 + (i % 2)), fill=color)

    img.save(os.path.join(texture_dir, filename))
    print(f"  ✓ Created {filename}")

def create_focus_texture(filename):
    """Create a ritual focus texture - ornate crystal"""
    img = Image.new('RGBA', (16, 16), (0, 0, 0, 0))
    draw = ImageDraw.Draw(img)

    # Central crystal
    draw.ellipse([6, 6, 10, 10], fill=(255, 215, 0, 255), outline=(218, 165, 32, 255))

    # Four prongs
    draw.line([(8, 4), (8, 6)], fill=(160, 160, 160, 255), width=1)
    draw.line([(8, 10), (8, 12)], fill=(160, 160, 160, 255), width=1)
    draw.line([(4, 8), (6, 8)], fill=(160, 160, 160, 255), width=1)
    draw.line([(10, 8), (12, 8)], fill=(160, 160, 160, 255), width=1)

    # Prong tips
    for x, y in [(8, 3), (8, 13), (3, 8), (13, 8)]:
        draw.point((x, y), fill=(192, 192, 192, 255))

    img.save(os.path.join(texture_dir, filename))
    print(f"  ✓ Created {filename}")

def create_component_texture(filename):
    """Create a spell component texture - magical dust"""
    img = Image.new('RGBA', (16, 16), (0, 0, 0, 0))
    draw = ImageDraw.Draw(img)

    # Pile of magical dust
    colors = [(255, 255, 0), (255, 215, 0), (255, 192, 128)]

    # Bottom layer
    draw.ellipse([4, 10, 12, 14], fill=colors[2])
    # Middle layer
    draw.ellipse([5, 7, 11, 11], fill=colors[1])
    # Top sparkle
    draw.ellipse([6, 5, 10, 8], fill=colors[0])

    # Sparkle points
    for x, y in [(4, 6), (12, 8), (8, 4), (10, 12)]:
        draw.point((x, y), fill=(255, 255, 255, 255))

    img.save(os.path.join(texture_dir, filename))
    print(f"  ✓ Created {filename}")

def create_scroll_texture(filename):
    """Create a spell scroll texture"""
    img = Image.new('RGBA', (16, 16), (0, 0, 0, 0))
    draw = ImageDraw.Draw(img)

    # Rolled parchment
    draw.rectangle([5, 2, 11, 14], fill=(245, 222, 179, 255), outline=(210, 180, 140, 255))

    # Roll ends
    draw.rectangle([4, 2, 5, 14], fill=(139, 90, 43, 255))
    draw.rectangle([11, 2, 12, 14], fill=(139, 90, 43, 255))

    # Magical runes on parchment
    draw.line([(6, 5), (10, 5)], fill=(138, 43, 226, 255), width=1)
    draw.line([(7, 7), (10, 7)], fill=(138, 43, 226, 255), width=1)
    draw.line([(6, 9), (9, 9)], fill=(138, 43, 226, 255), width=1)
    draw.line([(7, 11), (10, 11)], fill=(138, 43, 226, 255), width=1)

    img.save(os.path.join(texture_dir, filename))
    print(f"  ✓ Created {filename}")

def create_book_texture(filename, cover_color, accent_color):
    """Create a book texture"""
    img = Image.new('RGBA', (16, 16), (0, 0, 0, 0))
    draw = ImageDraw.Draw(img)

    # Book cover
    draw.rectangle([4, 2, 12, 14], fill=cover_color, outline=(0, 0, 0, 255))

    # Book spine
    draw.line([(4, 2), (4, 14)], fill=(0, 0, 0, 255), width=1)

    # Pages on right
    draw.rectangle([12, 3, 13, 13], fill=(255, 255, 255, 255))

    # Magical symbol on cover
    draw.ellipse([7, 6, 11, 10], outline=accent_color, width=1)
    draw.line([(9, 6), (9, 10)], fill=accent_color)
    draw.line([(7, 8), (11, 8)], fill=accent_color)

    img.save(os.path.join(texture_dir, filename))
    print(f"  ✓ Created {filename}")

def create_artifact_texture(filename):
    """Create a catalyst artifact texture - powerful magical item"""
    img = Image.new('RGBA', (16, 16), (0, 0, 0, 0))
    draw = ImageDraw.Draw(img)

    # Central gem
    draw.rectangle([6, 6, 10, 10], fill=(255, 0, 255, 255), outline=(200, 0, 200, 255))

    # Golden frame
    draw.rectangle([5, 5, 11, 11], outline=(255, 215, 0, 255), width=1)

    # Four energy beams
    draw.line([(8, 2), (8, 5)], fill=(255, 215, 0, 255), width=1)
    draw.line([(8, 11), (8, 14)], fill=(255, 215, 0, 255), width=1)
    draw.line([(2, 8), (5, 8)], fill=(255, 215, 0, 255), width=1)
    draw.line([(11, 8), (14, 8)], fill=(255, 215, 0, 255), width=1)

    # Glow points
    for x, y in [(8, 1), (8, 15), (1, 8), (15, 8)]:
        draw.point((x, y), fill=(255, 255, 0, 255))

    img.save(os.path.join(texture_dir, filename))
    print(f"  ✓ Created {filename}")

def create_enchanted_gem_texture(filename):
    """Create an enchanted gem texture"""
    img = Image.new('RGBA', (16, 16), (0, 0, 0, 0))
    draw = ImageDraw.Draw(img)

    # Gem shape (diamond)
    points = [(8, 3), (12, 8), (8, 13), (4, 8)]
    draw.polygon(points, fill=(0, 255, 255, 255), outline=(0, 200, 200, 255))

    # Inner facets
    draw.line([(8, 3), (8, 13)], fill=(200, 255, 255, 255))
    draw.line([(4, 8), (12, 8)], fill=(200, 255, 255, 255))
    draw.line([(6, 6), (10, 10)], fill=(200, 255, 255, 255))
    draw.line([(10, 6), (6, 10)], fill=(200, 255, 255, 255))

    # Magical sparkles
    for x, y in [(5, 5), (11, 5), (5, 11), (11, 11)]:
        draw.point((x, y), fill=(255, 255, 255, 255))

    img.save(os.path.join(texture_dir, filename))
    print(f"  ✓ Created {filename}")

def main():
    print("Creating missing item textures...")

    os.makedirs(texture_dir, exist_ok=True)

    print("\n🍾 Mana Bottle:")
    create_bottle_texture("mana_bottle.png", (0, 191, 255, 255), (100, 220, 255, 255))

    print("\n💎 Mana Shard:")
    create_shard_texture("mana_shard.png", (0, 191, 255, 200), (100, 220, 255, 255))

    print("\n🧪 Essence Vial:")
    create_vial_texture("essence_vial.png", (186, 85, 211, 255))

    print("\n🔮 Ritual Focus:")
    create_focus_texture("ritual_focus.png")

    print("\n✨ Spell Component:")
    create_component_texture("spell_component.png")

    print("\n📜 Spell Scroll:")
    create_scroll_texture("spell_scroll.png")

    print("\n📖 Grimoire:")
    create_book_texture("grimoire.png", (75, 0, 130, 255), (255, 215, 0, 255))

    print("\n📕 Spell Tome:")
    create_book_texture("spell_tome.png", (178, 34, 34, 255), (255, 215, 0, 255))

    print("\n⚡ Catalyst Artifact:")
    create_artifact_texture("catalyst_artifact.png")

    print("\n💠 Enchanted Gem:")
    create_enchanted_gem_texture("enchanted_gem.png")

    print("\n✅ All missing textures created!")
    print(f"📁 Location: {texture_dir}")

if __name__ == "__main__":
    main()
