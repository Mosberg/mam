#!/usr/bin/env python3
from PIL import Image
import os

# Create textures directory if it doesn't exist
textures_path = r"c:\Users\Rasmu\Documents\Projects\Minecraft\Mods\mam\src\main\resources\assets\mam\textures"

# List of block textures to create (16x16 purple)
block_textures = [
    "block/arcane_workbench_top.png",
    "block/arcane_workbench_side.png",
    "block/arcane_workbench_bottom.png",
    "block/mana_crystal_block.png",
    "block/mana_infuser.png",
    "block/mana_lamp.png",
    "block/mana_ley_line.png",
    "block/mana_node_block.png",
    "block/ritual_circle_block.png",
    "block/spell_altar.png",
    "block/spell_amplifier.png",
    "block/arcane_tree_log.png",
    "block/arcane_tree_leaves.png",
    "block/void_essence.png",
    "item/mana_node.png",
]

# Create a 16x16 purple image
for texture in block_textures:
    filepath = os.path.join(textures_path, texture)
    # Create parent directory if needed
    os.makedirs(os.path.dirname(filepath), exist_ok=True)
    # Create 16x16 purple image (RGB: 128, 0, 255)
    img = Image.new('RGB', (16, 16), color=(128, 0, 255))
    img.save(filepath)
    print(f"Created: {texture}")

print("All textures created successfully!")
