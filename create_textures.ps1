# Texture Creation Script for Mana And Magic
# This script generates missing block textures using ImageMagick
# Install ImageMagick: winget install ImageMagick.ImageMagick

$textureDir = "src\main\resources\assets\mam\textures"

# Check if ImageMagick is installed
try {
    magick -version | Out-Null
} catch {
    Write-Host "ERROR: ImageMagick not found. Install it first:" -ForegroundColor Red
    Write-Host "winget install ImageMagick.ImageMagick" -ForegroundColor Yellow
    exit 1
}

Write-Host "Creating missing block textures..." -ForegroundColor Cyan

# Create block texture directory if it doesn't exist
New-Item -ItemType Directory -Force -Path "$textureDir\block" | Out-Null

# === MANA CRYSTALS ===
Write-Host "  Creating mana crystal textures..." -ForegroundColor Green

# Personal Mana Crystal (Cyan/Blue with glow)
magick -size 16x16 xc:"#00BFFF" `
    -sparse-color barycentric "8,8 #00FFFF  0,0 #0080FF  15,15 #0080FF" `
    -blur 0x0.5 `
    "$textureDir\block\personal_mana_crystal.png"

# Aura Mana Crystal (Purple with glow)
magick -size 16x16 xc:"#9370DB" `
    -sparse-color barycentric "8,8 #BA55D3  0,0 #663399  15,15 #663399" `
    -blur 0x0.5 `
    "$textureDir\block\aura_mana_crystal.png"

# Reserve Mana Crystal (Orange with glow)
magick -size 16x16 xc:"#FF8C00" `
    -sparse-color barycentric "8,8 #FFA500  0,0 #FF6347  15,15 #FF6347" `
    -blur 0x0.5 `
    "$textureDir\block\reserve_mana_crystal.png"

# === RITUAL BLOCKS ===
Write-Host "  Creating ritual block textures..." -ForegroundColor Green

# Ritual Pedestal Top (Dark stone with arcane rune)
magick -size 16x16 xc:"#2F2F2F" `
    -fill "#5555AA" -draw "circle 8,8 8,4" `
    -fill "#7777CC" -draw "line 8,5 8,11" `
    -fill "#7777CC" -draw "line 5,8 11,8" `
    "$textureDir\block\ritual_pedestal_top.png"

# Ritual Pedestal Side (Dark stone pillar)
magick -size 16x16 xc:"#2F2F2F" `
    -fill "#1F1F1F" -draw "line 0,0 0,15" `
    -fill "#1F1F1F" -draw "line 15,0 15,15" `
    -fill "#3F3F3F" -draw "line 4,0 4,15" `
    -fill "#3F3F3F" -draw "line 11,0 11,15" `
    "$textureDir\block\ritual_pedestal_side.png"

# Ritual Candle (White candle texture)
magick -size 16x16 xc:"#FFFFFF" `
    -fill "#F5F5DC" -draw "rectangle 6,4 9,15" `
    -fill "#FFD700" -draw "rectangle 6,2 9,4" `
    -fill "#FF4500" -draw "point 7,1" `
    -fill "#FF4500" -draw "point 8,1" `
    "$textureDir\block\ritual_candle.png"

# === BUILDING BLOCKS ===
Write-Host "  Creating building block textures..." -ForegroundColor Green

# Mana Infused Stone (Stone with blue veins)
magick -size 16x16 xc:"#808080" `
    -fill "#A0A0A0" -draw "point 3,2" -draw "point 7,5" -draw "point 12,8" `
    -fill "#606060" -draw "point 4,11" -draw "point 9,13" -draw "point 14,3" `
    -fill "#00BFFF" -draw "line 2,3 5,6" `
    -fill "#00BFFF" -draw "line 8,4 11,7" `
    -fill "#00BFFF" -draw "line 3,10 7,13" `
    -fill "#4169E1" -draw "line 10,9 13,12" `
    "$textureDir\block\mana_infused_stone.png"

# Mana Infused Stone Bricks (Brick pattern with blue veins)
magick -size 16x16 xc:"#707070" `
    -fill "#505050" -draw "line 0,4 15,4" `
    -fill "#505050" -draw "line 0,12 15,12" `
    -fill "#505050" -draw "line 8,0 8,3" `
    -fill "#505050" -draw "line 8,5 8,11" `
    -fill "#505050" -draw "line 8,13 8,15" `
    -fill "#00BFFF" -draw "line 2,2 4,2" `
    -fill "#00BFFF" -draw "line 10,6 12,6" `
    -fill "#00BFFF" -draw "line 4,10 6,10" `
    -fill "#00BFFF" -draw "line 12,14 14,14" `
    "$textureDir\block\mana_infused_stone_bricks.png"

# === ARCANE ALTAR ===
Write-Host "  Creating arcane altar textures..." -ForegroundColor Green

# Arcane Altar Top (Purple magical surface with runes)
magick -size 16x16 xc:"#4B0082" `
    -fill "#8B00FF" -draw "circle 8,8 8,5" `
    -fill "#9370DB" -draw "circle 4,4 4,2" `
    -fill "#9370DB" -draw "circle 12,4 12,2" `
    -fill "#9370DB" -draw "circle 4,12 4,10" `
    -fill "#9370DB" -draw "circle 12,12 12,10" `
    -fill "#BA55D3" -draw "line 8,6 8,10" `
    -fill "#BA55D3" -draw "line 6,8 10,8" `
    "$textureDir\block\arcane_altar_top.png"

# Arcane Altar Side (Purple stone sides)
magick -size 16x16 xc:"#4B0082" `
    -fill "#663399" -draw "rectangle 0,0 15,2" `
    -fill "#663399" -draw "rectangle 0,13 15,15" `
    -fill "#8B00FF" -draw "point 4,6" `
    -fill "#8B00FF" -draw "point 11,9" `
    "$textureDir\block\arcane_altar_side.png"

# Arcane Altar Bottom (Dark stone base)
magick -size 16x16 xc:"#2F2F2F" `
    -fill "#1F1F1F" -draw "line 0,0 15,15" `
    -fill "#1F1F1F" -draw "line 15,0 0,15" `
    "$textureDir\block\arcane_altar_bottom.png"

# === ENTITY TEXTURE ===
Write-Host "  Creating entity texture..." -ForegroundColor Green

# Spell Projectile (Glowing orb - will be colored by code)
magick -size 16x16 xc:transparent `
    -fill "#FFFFFF" -draw "circle 8,8 8,3" `
    -fill "#EEEEEE" -draw "circle 8,8 8,4" `
    -fill "#DDDDDD" -draw "circle 8,8 8,5" `
    -fill "#CCCCCC" -draw "circle 8,8 8,6" `
    -fill "#BBBBBB" -draw "circle 8,8 8,7" `
    -blur 0x1 `
    "$textureDir\entity\spell_projectile.png"

Write-Host "`nTexture creation complete!" -ForegroundColor Green
Write-Host "Created textures in: $textureDir" -ForegroundColor Cyan

Write-Host "`n=== NEXT STEPS ===" -ForegroundColor Yellow
Write-Host "1. Run: ./gradlew build" -ForegroundColor White
Write-Host "2. Run: ./gradlew runClient" -ForegroundColor White
Write-Host "3. Check in-game appearance" -ForegroundColor White
Write-Host "4. Refine textures manually using GIMP/Aseprite if needed" -ForegroundColor White
