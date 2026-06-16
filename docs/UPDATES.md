# The Aether II - NeoForge - 26.1.2-alpha.4

Additions

- Add Rustic Arkenium Bars, a darker decorative counterpart to Arkenium Bars
- Add Gel Blocks, a sticky storage block for Swet Gel that is also created from fluid collisions between Water and Alkahest
- Add Holystone and Brexallen Vases, lootable blocks that can be found in new structures
- Add Shifting Glass, a tool item found in loot that can be used for directional dashing
- Add advancement for using Shifting Glass
- Add Aerbunny Bell, a craftable companion item that can be used to keep a tamed Aerbunny in to retrieve and deploy
- Add Resonant Stone, a material item found in loot used for crafting the Aerbunny Bell
- Add advancement for using an Aerbunny Bell
- Add the Prismallard, a duck-like creature that spawns at bodies of water in Highfields biomes. When scared, it'll intimidate predators by displaying it's plumage
- Add Prismallard Feather
- Add Prismallard Egg and Fried Prismallard Egg
- Add Prismallard Leg and Prismallard Roast
- Add Ancient Henges, a rare structure found in Magnetic biomes
- Add Veradexian Aqueducts, an uncommon structure found in Shimmering Basin biomes
- Add Brexallen Ruins, a common underground structure
- Add Undercloud Mineshafts, another underground structure
- Add a new data-driven `item_reinforcement` registry, which is used for determining the attributes that are changed for a given item per reinforcing tier
- Add an internal sound instance that merges given stereo audio channels into a single mono audio channel; this will be used in the future for playing Engraved Discs from a block location
- Add lots of new tags and Aether II content to existing Vanilla, NeoForge, and common `c` tags
- Add a config screen
- Update Cumulus to 2.0.15; disallows world modification when world is previewed, allows double clicking menu entries for selection, fixes button render issues, fixes config entries missing translations, fixes mixin refmap warning
- Update Nitrogen to 1.3.5; fixes mixin refmap warning

Changes

- Reinforced items' durability bar now darkens with damage, as opposed to staying the same color regardless of durability left
- Aerbunnies now follow the player when tamed
- Aerbunnies can now receive external damage from hazards and enemies, excluding player-sourced damage and block suffocation damage
- An Aerbunny on the player's head will now display it's health above the hotbar
- Updated Swet assets to be brighter and shinier
- Irradiated Chunks can now purify into a wider array of output item groups including: log blocks, raw stone blocks, processed ore items, and some additional crafting materials
- Irradiated Armor purification output now includes pendants
- Irradiated Weapon purification output now includes tiered crossbows
- Irradiated Tool purification output now includes Zanite Shears and tiered shields
- Irradiated Dust now works as a fuel for the Holystone Furnace
- Golden Amber Blocks now work as a fuel for the Amber Hourglass
- Isolate Hesperose and Tarabloom flower generation to Highfields biomes
- Immolation effect buildup can now be reduced by drinking Water Vials and getting in water
- Isolate Lilichime, Poasprout, and Pluracian flower generation to Magnetic biomes
- Isolate Satival Shoot generation to Arctic biomes
- Rework Trunk block generation on trees to look more dynamic
- Rework Aether Bush and Blueberry Bush generation to look more natural
- Rework flower generation in the Flourishing field to occur in noise-based patches and "trails"
- Rework tree generation in the Flourishing Field to be in groups as opposed to individual trees, and generate Aether Ferns around these groups
- Use emissive rendering for spots and gills of Magnetic Shrooms and Magnetic Shroom Blocks
- Brighten the shadow rendering on Skyplane Leaves, Bryalinn Moss Flowers, Holpupeas, and Tarahesp Flowers
- Adjust lightmap color of the Holy Isles dimension
- Disallow putting Engraved Discs in Jukeboxes; a special equivalent will be implemented in the future
- Change the `spawn_in_aether` and `experimental_dungeon_content` config options to be common configurations as opposed to server configurations

Fixes

- Fixed Gravitite tools and weapons not having the correct stat upgrades at Reinforcement III
- Fixed Gravitite tools' ability being able to pick up blocks it shouldn't and not updating placed blocks properly
- Fixed enchantment prevention for Aether II's equipment not working
- Fixed 3D noise blobs of Mossy Holystone, Packed Ice, Ferrosite, Irradiated Holystone, and Ichorite not generating correctly in their respective biomes
- Fixed Aether II's saplings creating vanilla Dirt when grown and not Aether Dirt
- Fixed Vase blocks in Small Veradexian Ruins not having the `cracked` block state
- Fixed leaf particles not being spawned when walking through Aether Bushes
- Fixed Bone Meal and Swet Gel being usable on Tall Aether Grass, since there is no additional growth state
- Fixed Irradiated Dust not having a usage animation
- Fixed landing on Aerclouds playing the fall damage sound even though fall damage is prevented
- Fixed the Guidebook descriptions screen not having a properly darkened background
- Fixed the alpha information screen not having a properly darkened background
- Fixed Aerwhales and Zephyrs getting stuck on aerclouds
- Fixed animals having trouble pathfinding when tempted by food and when breeding
- Fixed Aerbunnies being able to jump even when sitting down
- Fixed visual sync issues with mounts and vehicles on multiplayer servers
- Fixed visual sync issues with accessories, latched Swets, stuck projectiles, and Aerbunnies on servers
- Fixed the unfold animation not playing when Cloud Skiffs are placed
- Fixed insect fold and unfold animations not playing correctly at the right times
- Fixed hovering block entities created by Gravitite tools' ability being rendered in complete darkness
- Fixed Aether II's armor sets not rendering properly on baby humanoid mobs
- Fixed Aether II's title screen music not looping properly
- Fixed music playing at the same time as Aether Portal sounds
- Fixed the Vertigo advancement being granted even when the player has not hit the ground
- Fixed aether II's key mapping category missing a translation key
- Fixed Corrobonite Crystals and Glint Gemstones being incorrectly included in the `gems/zanite` tag
- Fixed some recipes accidentally being placed in the `minecraft` namespace
- Fixed a mixin refmap warning error occurring on game startup
- Fixed some bugs with Sodium, Iris, and Complementary Shaders Reimagined compatibility; this includes snowy plant rendering, waterfall void fade rendering and tinting (only with Sodium), and Moa and Aerbunny rendering

Experimental Dungeon Content

*[Note: Infected Guardian Tree structure must be spawned in using commands; following blocks and items need to be enabled in the mod's config]*

- Added Prayer Candle
- Added Guardian Pew
- Added Guardian Donation Box
- Added Abandoned Bag
- Added Fungal Cache
- Added Sage Chest
- Dead Ends of the Infected Guardian Tree structures have been given more variation to give a dungeon a more natural appearance on the exterior


# The Aether II - NeoForge - 26.1.2-alpha.3.1

Additions

- Add a backend Healing Overflow effect to save the Healing Stone's extra hearts after logout
- Add biome color and structure icon support for maps like https://map.jacobsjo.eu/

Changes

- Rename Carrion Pull backend-effect to Carrion Trap
- Rename `mob_effect/typed` tag to `mob_effect/milk_doesnt_clear` to represent the proper purpose of the tag
- Put more of Aether II's effects into the `milk_doesnt_clear` tag

Fixes

- Fixed a crash from throwing a glider item out of the player's inventory
- Fixed a crash when running the mod with SpongeNeo
- Fixed a crash when trying to break blocks in the world using Integrated Tunnels
- Fixed non-dyeable gloves from rendering the base texture as completely black
- Fixed the top face of mossy leaves not rendering properly
- Fixed advancement names not having unique colors on dedicated servers
- Fixed /clear command and creative mode inventory screen deletion slot not clearing accessory items
- Fixed Aerbunnies not staying on the player's head when falling out of the Aether in a vehicle
- Fixed Copper Torches not igniting Hestveil blocks


# The Aether II - NeoForge - 26.1.2-alpha.3

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


# The Aether II - NeoForge - 1.21.11-alpha.2.1
Additions
- Added Arkenium Bars; these come in one regular and 3 Artisan's Bench variants

Changes
- Charms can now be applied to pendants
- Buffed Neptune Armor set-bonus
- The Sentry Boots zephyr protection ability is now mentioned in the item's tooltip
- Tamed animals will no longer die when falling out of The Aether

Fixes
- Fixed Bedrolls exploding when used
- Fixed head rotation of bestiary entity renders being wrong
- Fixed guidebook health display sometimes being inaccurate
- Fixed Skybirch Saplings not growing

Experimental Dungeon Content

*[Note: Infected Guardian Tree structure must be spawned in using commands]*
- Fixed some Infected Guardian Tree room connection issues
- Reduced average amount of Guardian Tree rooms
- The Guardian Tree is no longer made-up of placeholder blocks


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
