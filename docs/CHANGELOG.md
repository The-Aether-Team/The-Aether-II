# The Aether II - NeoForge - 1.21.11-alpha.2

Ported the mod to 1.21.11

Additions
- Added Veradexian Ruins structure; an uncommon structure made of a cluster of ruins, generating all across The Aether
- Added Veradexian Library structure; a rare variant of the Veradexian Ruins with a hidden library in the basement. This structure is exclusive to dense forest biomes
  (Both Veradexian Ruins and Library come in a temperate and an arctic variant to adjust the structure's block palette to the arctic environments)
- Added Irradiated Bunker Remnants structure; a structure exclusive to the irradiated biomes consisting out of 1-2 bunkers ruins
- Added Irradiated Settlement Remnants structure; another irradiated theme exclusive structure
- Added Animal Den structure, a small den structure generating inside forests. The den is currently uninhabited but this will change in the future

- Added Skyroot Barrel
- Added Holystone Smoker
- Added Aether Shelf variants
- Added Veradexian Vase; a vanilla like decorated pot block with unique visuals
- Added Animal Stash block; a chest like container with a different model that will retain its open state when opened once

- Added Ice Pendant; an accessory that temporarily freezes liquid blocks when stepped above
- Added Charm of Reach I; a new tool charm that will increase a player's block interaction range

- Added a config for enabling experimental dungeon content
- Implemented a working world gen continentalness value that will later be used by the expanse biome


Changes
- Made magnetic biomes more common
- Gave magnetic biomes a unique terrain generation
- Microbiomes will occur less often now
- Slightly tweaked generation rates of existing structures
- Made Amberoot trees a bit more common in magnetic and arctic biomes
- Improved snow generation in arctic biomes to have less chunk border cut-offs

- Rebalanced build-up effect durations; previously most effects triggered so rarely due to low build-up making them seem bugged. Because of this a lot of mobs were way easier to defeat than they were meant to be. We do appreciate feedback on these changes.
- Cockatrice will now deal venom build-up with melee attacks
- Aechor Plants and Carrion Sprouts no longer spawn if there is a block above them
- Reduced flying monster spawn rates
- Adjusted Zephyr spawn costs
- Reduced Zephyr follow range from 35 to 30
- Reduced Zephyr flying speed from 0.1 to 0.07

- Nerfed Zanite Armor attack speed bonus from +0.3 to +0.15
- Nerfed Charm of Dexterity I attack speed bonus from +0.25 to +0.15
- Nerfed Charm of Resistance I knockback resistance bonus from 25% to 10%
- Renamed Spears to Pikes
- Pendants are now repairable
- Added pendants to the Irradiated Chunk loot table
- The Aether II Spawn Eggs creative inventory tab is now sorted by category instead of alphabetically for vanilla parity

- Fixed Aether Portals created in The Aether spawning in mid-air in the overworld
- Removed Quicksoil from the portal whitelist

- Raised cloud height from y256 to y320
- Irradiated biome fog is less intense now


Fixes
- Fixed Irradiated Holystone and Marbled Ichorite Stairs, Slabs and Walls not having an assigned tool and not dropping anything
- Fixed tree nest Moas sometimes generating inside the tree's log
- Fixed a rare crash with the shrouded forest mossy tree feature
- Fixed Zanite Pendant lacking its ability tooltip
- Fixed Wisproot and Greatroot Planks map colors being inaccurate


# The Aether II - NeoForge - 1.21.8-alpha.1.1

Changes

- Moa Feather and Moa Egg textures have darker outlines.
- Stripped Greatroot Log textures no longer have noticeable tiling.

Fixes

- Fix entity crashes in certain language locales due to incorrectly handling lowercase conversions for some data IDs.
- Fix Neptune Armor and Sentry Boots breaking when right-clicking to equip.
- Fix desynced block motion when levitating a block with a Gravitite Tool.
- Fix Aerbunnies sometimes choosing to go in one direction and not stopping.
- Fix some recipes replacing vanilla recipes in the Minecraft namespace.
- Fix Aether Grass Blocks having tinted particles.
- Fix leaves textures that use snowy or mossy overlays sometimes becoming visually corrupted after reloading resource packs.
- Fix Brettl Grass Bundle blocks not boosting Campfire smoke signals.
- Fix the Artisan's Bench not showing up as a stonecutting recipe block in JEI.

# The Aether II - NeoForge - 1.21.8-alpha.1

Initial Alpha Release
