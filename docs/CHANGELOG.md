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
