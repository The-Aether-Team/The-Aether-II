# The Aether II - NeoForge - 1.21.11-alpha.3

Ported the mod to 26.1.2
Made various improvements to the code-base during porting

Additions

- Added Golden Wyndberry, which can be used to stop baby mobs from aging and is obtained by feeding Golden Amber to a Carrion Sprout
- Added an advancement for feeding Golden Amber to a Carrion Sprout
- Added Arkenium Chip item
- Added a server config option `"Spawns the player in the Aether"` to allow spawning in the Aether at the start of the world

Changes

- Adjusted recipes of Arkenium Chains and Lanterns to be closer to the vanilla recipes
- Increased crafting output for crafting Arkenium Chains from 2 to 3
- Increased crafting output for crafting Arkenium Bars from 16 to 32
- Arkenium Chips can be used to craft Nametags
- Holystone Quartz Ore will now drop a renamed item "Aether Quartz" instead of Nether Quartz; the item can be converted back into Nether Quartz in the crafting grid
- Renamed Ice Pendant to Icestone Pendant
- Icestone Pendants will now place regular Frosted Ice instead of Arctic Frosted Ice when used in the Overworld
- Icestone Pendants can now be reinforced
- Aerbunnies now dither sooner when looking upwards with them equipped

Fixes

- Fixed in-world freezing recipes not respecting biome checks properly
- Fixed summoning Mannequin entities crashing the game
- Fixed replacing the leaves model causing a crash
- Fixed Moa Eggs having a brighter breaking texture than other blocks
- Fixed item renders above the Altar having breaking textures
- Fixed glowing parts on deployed Hammer of Demolition head piece model not rendering as emissive
- Fixed pendants having the wrong third person model
- Fixed inconsistencies with portals being able to be built
- Fixed Aether Portal particles being incorrect
- Fixed Sentry Brick double slabs not rendering
- Fixed Holystone Smoker sounds and subtitles
- Fixed incorrect glint for irradiated items
- Fixed Music Player not stopping when out of inventory
- Fixed transparency rendering of Tangled Branches and Guardian Leaves
- Fixed locked and dungeon doorway blocks not being applying a darker tint to the block they copy
- Fixed locked and dungeon doorway blocks not taking all the properties of the block they copy

Experimental Dungeon Content

*[Note: Infected Guardian Tree structure must be spawned in using commands; following blocks and items need to be enabled in the mod's config]*

- The central core of the Infected Guardian Tree structure is now covered by terrain
- Implemented a noise based infected block processor to the structure
- Added decorational features to the entrance of the guardian tree structure
- Made various miscellaneous improvements to the guardian tree structure generation
- Rotshrooms are now placeable on locked guardian blocks
