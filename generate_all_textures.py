"""
Master texture generator for Mana And Magic.
Delegates to item, block, entity, and GUI generators.
"""

from __future__ import annotations

import argparse

from generate_block_textures import generate_block_textures
from generate_entity_textures import generate_entity_textures
from generate_gui_textures import generate_gui_textures
from generate_item_textures import generate_item_textures


def parse_args() -> argparse.Namespace:
    parser = argparse.ArgumentParser(description="Generate all Mana And Magic textures")
    parser.add_argument("--items", action="store_true", help="Generate item textures only")
    parser.add_argument("--blocks", action="store_true", help="Generate block textures only")
    parser.add_argument("--entities", action="store_true", help="Generate entity textures only")
    parser.add_argument("--gui", action="store_true", help="Generate GUI textures only")
    parser.add_argument("--overwrite", action="store_true", help="Overwrite existing textures")
    return parser.parse_args()


def main() -> None:
    args = parse_args()
    targets = {
        "items": args.items,
        "blocks": args.blocks,
        "entities": args.entities,
        "gui": args.gui,
    }

    # If no scope flags provided, run everything.
    if not any(targets.values()):
        targets = {key: True for key in targets}

    print("=" * 60)
    print("Generating Mana And Magic textures")
    print("=" * 60)

    if targets["items"]:
        generate_item_textures(overwrite=args.overwrite)
    if targets["blocks"]:
        generate_block_textures(overwrite=args.overwrite)
    if targets["entities"]:
        generate_entity_textures(overwrite=args.overwrite)
    if targets["gui"]:
        generate_gui_textures(overwrite=args.overwrite)

    print("\n" + "=" * 60)
    print("✅ Texture generation complete")
    print("=" * 60)


if __name__ == "__main__":
    main()
