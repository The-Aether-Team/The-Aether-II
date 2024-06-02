package com.aetherteam.aetherii.data.generators;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.data.providers.AetherIILanguageProvider;
import com.aetherteam.aetherii.effect.AetherIIEffects;
import com.aetherteam.aetherii.entity.AetherIIAttributes;
import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import com.aetherteam.aetherii.inventory.menu.AetherIIMenuTypes;
import com.aetherteam.aetherii.item.AetherIICreativeTabs;
import com.aetherteam.aetherii.item.AetherIIItems;
import net.minecraft.data.PackOutput;

public class AetherIILanguageData extends AetherIILanguageProvider {
    public AetherIILanguageData(PackOutput output) {
        super(output, AetherII.MODID);
    }

    @Override
    protected void addTranslations() {
        this.addBlock(AetherIIBlocks.AETHER_PORTAL, "Aether Portal");

        this.addBlock(AetherIIBlocks.AETHER_GRASS_BLOCK, "Aether Grass Block");
        this.addBlock(AetherIIBlocks.ENCHANTED_AETHER_GRASS_BLOCK, "Enchanted Aether Grass Block");
        this.addBlock(AetherIIBlocks.AETHER_DIRT, "Aether Dirt");
        this.addBlock(AetherIIBlocks.COARSE_AETHER_DIRT, "Coarse Aether Dirt");
        this.addBlock(AetherIIBlocks.AETHER_FARMLAND, "Aether Farmland");
        this.addBlock(AetherIIBlocks.AETHER_DIRT_PATH, "Aether Dirt Path");

        this.addBlock(AetherIIBlocks.QUICKSOIL, "Quicksoil");
        this.addBlock(AetherIIBlocks.FERROSITE_SAND, "Ferrosite Sand");
        this.addBlock(AetherIIBlocks.HOLYSTONE, "Holystone");
        this.addBlock(AetherIIBlocks.MOSSY_HOLYSTONE, "Mossy Holystone");
        this.addBlock(AetherIIBlocks.IRRADIATED_HOLYSTONE, "Irradiated Holystone");
        this.addBlock(AetherIIBlocks.UNDERSHALE, "Undershale");
        this.addBlock(AetherIIBlocks.AGIOSITE, "Agiosite");
        this.addBlock(AetherIIBlocks.FERROSITE, "Ferrosite");
        this.addBlock(AetherIIBlocks.RUSTED_FERROSITE, "Rusted Ferrosite");
        this.addBlock(AetherIIBlocks.CRUDE_SCATTERGLASS, "Crude Scatterglass");
        this.addBlock(AetherIIBlocks.SCATTERGLASS, "Scatterglass");
        this.addBlock(AetherIIBlocks.ARCTIC_SNOW_BLOCK, "Arctic Snow Block");
        this.addBlock(AetherIIBlocks.ARCTIC_SNOW, "Arctic Snow");
        this.addBlock(AetherIIBlocks.ARCTIC_ICE, "Arctic Ice");
        this.addBlock(AetherIIBlocks.ARCTIC_PACKED_ICE, "Arctic Packed Ice");

        this.addBlock(AetherIIBlocks.AMBROSIUM_ORE, "Ambrosium Ore");
        this.addBlock(AetherIIBlocks.ZANITE_ORE, "Zanite Ore");
        this.addBlock(AetherIIBlocks.ARKENIUM_ORE, "Arkenium Ore");
        this.addBlock(AetherIIBlocks.GRAVITITE_ORE, "Gravitite Ore");

        this.addBlock(AetherIIBlocks.COLD_AERCLOUD, "Cold Aercloud");
        this.addBlock(AetherIIBlocks.BLUE_AERCLOUD, "Blue Aercloud");
        this.addBlock(AetherIIBlocks.GOLDEN_AERCLOUD, "Golden Aercloud");
        this.addBlock(AetherIIBlocks.GREEN_AERCLOUD, "Green Aercloud");
        this.addBlock(AetherIIBlocks.PURPLE_AERCLOUD, "Purple Aercloud");
        this.addBlock(AetherIIBlocks.STORM_AERCLOUD, "Storm Aercloud");

        this.addBlock(AetherIIBlocks.SKYROOT_LOG, "Skyroot Log");
        this.addBlock(AetherIIBlocks.STRIPPED_SKYROOT_LOG, "Stripped Skyroot Log");
        this.addBlock(AetherIIBlocks.GREATROOT_LOG, "Greatroot Log");
        this.addBlock(AetherIIBlocks.WISPROOT_LOG, "Wisproot Log");
        this.addBlock(AetherIIBlocks.AMBEROOT_LOG, "Amberoot Log");
        this.addBlock(AetherIIBlocks.SKYROOT_WOOD, "Skyroot Wood");
        this.addBlock(AetherIIBlocks.STRIPPED_SKYROOT_WOOD, "Stripped Skyroot Wood");
        this.addBlock(AetherIIBlocks.GREATROOT_WOOD, "Greatroot Wood");
        this.addBlock(AetherIIBlocks.WISPROOT_WOOD, "Wisproot Wood");
        this.addBlock(AetherIIBlocks.AMBEROOT_WOOD, "Amberoot Wood");

        this.addBlock(AetherIIBlocks.SKYROOT_LEAVES, "Skyroot Leaves");
        this.addBlock(AetherIIBlocks.SKYPLANE_LEAVES, "Skyplane Leaves");
        this.addBlock(AetherIIBlocks.SKYBIRCH_LEAVES, "Skybirch Leaves");
        this.addBlock(AetherIIBlocks.SKYPINE_LEAVES, "Skypine Leaves");
        this.addBlock(AetherIIBlocks.WISPROOT_LEAVES, "Wisproot Leaves");
        this.addBlock(AetherIIBlocks.WISPTOP_LEAVES, "Wisptop Leaves");
        this.addBlock(AetherIIBlocks.GREATROOT_LEAVES, "Greatroot Leaves");
        this.addBlock(AetherIIBlocks.GREATOAK_LEAVES, "Greatoak Leaves");
        this.addBlock(AetherIIBlocks.GREATBOA_LEAVES, "Greatboa Leaves");
        this.addBlock(AetherIIBlocks.AMBEROOT_LEAVES, "Amberoot Leaves");

        this.addBlock(AetherIIBlocks.SKYROOT_SAPLING, "Skyroot Sapling");
        this.addBlock(AetherIIBlocks.WISPROOT_SAPLING, "Wisproot Sapling");
        this.addBlock(AetherIIBlocks.WISPTOP_SAPLING, "Wisptop Sapling");
        this.addBlock(AetherIIBlocks.GREATROOT_SAPLING, "Greatroot Sapling");
        this.addBlock(AetherIIBlocks.GREATOAK_SAPLING, "Greatoak Sapling");
        this.addBlock(AetherIIBlocks.GREATBOA_SAPLING, "Greatboa Sapling");
        this.addBlock(AetherIIBlocks.AMBEROOT_SAPLING, "Amberoot Sapling");

        this.addBlock(AetherIIBlocks.POTTED_SKYROOT_SAPLING, "Potted Skyroot Sapling");
        this.addBlock(AetherIIBlocks.POTTED_WISPROOT_SAPLING, "Potted Wisproot Sapling");
        this.addBlock(AetherIIBlocks.POTTED_WISPTOP_SAPLING, "Potted Wisptop Sapling");
        this.addBlock(AetherIIBlocks.POTTED_AMBEROOT_SAPLING, "Potted Amberoot Sapling");

        this.addBlock(AetherIIBlocks.BLUEBERRY_BUSH, "Blueberry Bush");
        this.addBlock(AetherIIBlocks.BLUEBERRY_BUSH_STEM, "Blueberry Bush Stem");
        this.addBlock(AetherIIBlocks.HIGHLANDS_BUSH, "Highlands Bush");

        this.addBlock(AetherIIBlocks.ORANGE_TREE, "Orange Tree");

        this.addBlock(AetherIIBlocks.AETHER_SHORT_GRASS, "Aether Short Grass");
        this.addBlock(AetherIIBlocks.AETHER_MEDIUM_GRASS, "Aether Medium Grass");
        this.addBlock(AetherIIBlocks.AETHER_LONG_GRASS, "Aether Long Grass");

        this.addBlock(AetherIIBlocks.SKYROOT_TWIG, "Skyroot Twig");
        this.addBlock(AetherIIBlocks.HOLYSTONE_ROCK, "Holystone Rock");
        this.addBlock(AetherIIBlocks.SKYROOT_LEAF_PILE, "Skyroot Leaf Pile");
        this.addBlock(AetherIIBlocks.SKYPLANE_LEAF_PILE, "Skyplane Leaf Pile");
        this.addBlock(AetherIIBlocks.SKYBIRCH_LEAF_PILE, "Skybirch Leaf Pile");
        this.addBlock(AetherIIBlocks.SKYPINE_LEAF_PILE, "Skypine Leaf Pile");
        this.addBlock(AetherIIBlocks.WISPROOT_LEAF_PILE, "Wisproot Leaf Pile");
        this.addBlock(AetherIIBlocks.WISPTOP_LEAF_PILE, "Wisptop Leaf Pile");
        this.addBlock(AetherIIBlocks.GREATROOT_LEAF_PILE, "Greatroot Leaf Pile");
        this.addBlock(AetherIIBlocks.GREATOAK_LEAF_PILE, "Greatoak Leaf Pile");
        this.addBlock(AetherIIBlocks.GREATBOA_LEAF_PILE, "Greatboa Leaf Pile");
        this.addBlock(AetherIIBlocks.AMBEROOT_LEAF_PILE, "Amberoot Leaf Pile");

        this.addBlock(AetherIIBlocks.WOVEN_SKYROOT_STICKS, "Woven Skyroot Sticks");

        this.addBlock(AetherIIBlocks.SKYROOT_PLANKS, "Skyroot Planks");
        this.addBlock(AetherIIBlocks.SKYROOT_STAIRS, "Skyroot Stairs");
        this.addBlock(AetherIIBlocks.SKYROOT_SLAB, "Skyroot Slab");
        this.addBlock(AetherIIBlocks.SKYROOT_FENCE, "Skyroot Fence");
        this.addBlock(AetherIIBlocks.SKYROOT_FENCE_GATE, "Skyroot Fence Gate");
        this.addBlock(AetherIIBlocks.SKYROOT_DOOR, "Skyroot Door");
        this.addBlock(AetherIIBlocks.SKYROOT_TRAPDOOR, "Skyroot Trapdoor");
        this.addBlock(AetherIIBlocks.SKYROOT_BUTTON, "Skyroot Button");
        this.addBlock(AetherIIBlocks.SKYROOT_PRESSURE_PLATE, "Skyroot Pressure Plate");

        this.addBlock(AetherIIBlocks.GREATROOT_PLANKS, "Greatroot Planks");
        this.addBlock(AetherIIBlocks.GREATROOT_STAIRS, "Greatroot Stairs");
        this.addBlock(AetherIIBlocks.GREATROOT_SLAB, "Greatroot Slab");
        this.addBlock(AetherIIBlocks.GREATROOT_FENCE, "Greatroot Fence");
        this.addBlock(AetherIIBlocks.GREATROOT_FENCE_GATE, "Greatroot Fence Gate");
        this.addBlock(AetherIIBlocks.GREATROOT_DOOR, "Greatroot Door");
        this.addBlock(AetherIIBlocks.GREATROOT_TRAPDOOR, "Greatroot Trapdoor");
        this.addBlock(AetherIIBlocks.GREATROOT_BUTTON, "Greatroot Button");
        this.addBlock(AetherIIBlocks.GREATROOT_PRESSURE_PLATE, "Greatroot Pressure Plate");

        this.addBlock(AetherIIBlocks.WISPROOT_PLANKS, "Wisproot Planks");
        this.addBlock(AetherIIBlocks.WISPROOT_STAIRS, "Wisproot Stairs");
        this.addBlock(AetherIIBlocks.WISPROOT_SLAB, "Wisproot Slab");
        this.addBlock(AetherIIBlocks.WISPROOT_FENCE, "Wisproot Fence");
        this.addBlock(AetherIIBlocks.WISPROOT_FENCE_GATE, "Wisproot Fence Gate");
        this.addBlock(AetherIIBlocks.WISPROOT_DOOR, "Wisproot Door");
        this.addBlock(AetherIIBlocks.WISPROOT_TRAPDOOR, "Wisproot Trapdoor");
        this.addBlock(AetherIIBlocks.WISPROOT_BUTTON, "Wisproot Button");
        this.addBlock(AetherIIBlocks.WISPROOT_PRESSURE_PLATE, "Wisproot Pressure Plate");

        this.addBlock(AetherIIBlocks.HOLYSTONE_STAIRS, "Holystone Stairs");
        this.addBlock(AetherIIBlocks.HOLYSTONE_SLAB, "Holystone Slab");
        this.addBlock(AetherIIBlocks.HOLYSTONE_WALL, "Holystone Wall");
        this.addBlock(AetherIIBlocks.HOLYSTONE_BUTTON, "Holystone Button");
        this.addBlock(AetherIIBlocks.HOLYSTONE_PRESSURE_PLATE, "Holystone Pressure Plate");

        this.addBlock(AetherIIBlocks.MOSSY_HOLYSTONE_STAIRS, "Mossy Holystone Stairs");
        this.addBlock(AetherIIBlocks.MOSSY_HOLYSTONE_SLAB, "Mossy Holystone Slab");
        this.addBlock(AetherIIBlocks.MOSSY_HOLYSTONE_WALL, "Mossy Holystone Wall");

        this.addBlock(AetherIIBlocks.IRRADIATED_HOLYSTONE_STAIRS, "Irradiated Holystone Stairs");
        this.addBlock(AetherIIBlocks.IRRADIATED_HOLYSTONE_SLAB, "Irradiated Holystone Slab");
        this.addBlock(AetherIIBlocks.IRRADIATED_HOLYSTONE_WALL, "Irradiated Holystone Wall");

        this.addBlock(AetherIIBlocks.HOLYSTONE_BRICKS, "Holystone Bricks");
        this.addBlock(AetherIIBlocks.HOLYSTONE_BRICK_STAIRS, "Holystone Brick Stairs");
        this.addBlock(AetherIIBlocks.HOLYSTONE_BRICK_SLAB, "Holystone Brick Slab");
        this.addBlock(AetherIIBlocks.HOLYSTONE_BRICK_WALL, "Holystone Brick Wall");

        this.addBlock(AetherIIBlocks.FADED_HOLYSTONE_BRICKS, "Faded Holystone Bricks");
        this.addBlock(AetherIIBlocks.FADED_HOLYSTONE_BRICK_STAIRS, "Faded Holystone Brick Stairs");
        this.addBlock(AetherIIBlocks.FADED_HOLYSTONE_BRICK_SLAB, "Faded Holystone Brick Slab");
        this.addBlock(AetherIIBlocks.FADED_HOLYSTONE_BRICK_WALL, "Faded Holystone Brick Wall");

        this.addBlock(AetherIIBlocks.UNDERSHALE_STAIRS, "Undershale Stairs");
        this.addBlock(AetherIIBlocks.UNDERSHALE_SLAB, "Undershale Slab");
        this.addBlock(AetherIIBlocks.UNDERSHALE_WALL, "Undershale Wall");

        this.addBlock(AetherIIBlocks.AGIOSITE_STAIRS, "Agiosite Stairs");
        this.addBlock(AetherIIBlocks.AGIOSITE_SLAB, "Agiosite Slab");
        this.addBlock(AetherIIBlocks.AGIOSITE_WALL, "Agiosite Wall");

        this.addBlock(AetherIIBlocks.AGIOSITE_BRICKS, "Agiosite Bricks");
        this.addBlock(AetherIIBlocks.AGIOSITE_BRICK_STAIRS, "Agiosite Brick Stairs");
        this.addBlock(AetherIIBlocks.AGIOSITE_BRICK_SLAB, "Agiosite Brick Slab");
        this.addBlock(AetherIIBlocks.AGIOSITE_BRICK_WALL, "Agiosite Brick Wall");

        this.addBlock(AetherIIBlocks.QUICKSOIL_GLASS, "Quicksoil Glass");
        this.addBlock(AetherIIBlocks.QUICKSOIL_GLASS_PANE, "Quicksoil Glass Pane");

        this.addBlock(AetherIIBlocks.CLOUDWOOL, "Cloudwool");
        this.addBlock(AetherIIBlocks.CLOUDWOOL_CARPET, "Cloudwool Carpet");

        this.addBlock(AetherIIBlocks.AMBROSIUM_BLOCK, "Block of Ambrosium");
        this.addBlock(AetherIIBlocks.ZANITE_BLOCK, "Block of Zanite");
        this.addBlock(AetherIIBlocks.GRAVITITE_BLOCK, "Block of Gravitite");

        this.addBlock(AetherIIBlocks.AMBROSIUM_TORCH, "Ambrosium Torch");
        this.addBlock(AetherIIBlocks.SKYROOT_CRAFTING_TABLE, "Skyroot Crafting Table");
        this.addBlock(AetherIIBlocks.HOLYSTONE_FURNACE, "Holystone Furnace");
        this.addBlock(AetherIIBlocks.MASONRY_BENCH, "Masonry Bench");
        this.addBlock(AetherIIBlocks.SKYROOT_CHEST, "Skyroot Chest");
        this.addBlock(AetherIIBlocks.SKYROOT_LADDER, "Skyroot Ladder");

        this.addBlock(AetherIIBlocks.SKYROOT_BOOKSHELF, "Skyroot Bookshelf");
        this.addBlock(AetherIIBlocks.HOLYSTONE_BOOKSHELF, "Holystone Bookshelf");

        this.addBlock(AetherIIBlocks.SKYROOT_FLOORBOARDS, "Skyroot Floorboards");
        this.addBlock(AetherIIBlocks.SKYROOT_HIGHLIGHT, "Skyroot Highlight");
        this.addBlock(AetherIIBlocks.SKYROOT_SHINGLES, "Skyroot Shingles");
        this.addBlock(AetherIIBlocks.SKYROOT_SMALL_SHINGLES, "Skyroot Small Shingles");
        this.addBlock(AetherIIBlocks.SKYROOT_BASE_PLANKS, "Skyroot Base Planks");
        this.addBlock(AetherIIBlocks.SKYROOT_TOP_PLANKS, "Skyroot Top Planks");
        this.addBlock(AetherIIBlocks.SKYROOT_BASE_BEAM, "Skyroot Base Beam");
        this.addBlock(AetherIIBlocks.SKYROOT_TOP_BEAM, "Skyroot Top Beam");
        this.addBlock(AetherIIBlocks.SKYROOT_BEAM, "Skyroot Beam");

        this.addBlock(AetherIIBlocks.GREATROOT_FLOORBOARDS, "Greatroot Floorboards");
        this.addBlock(AetherIIBlocks.GREATROOT_HIGHLIGHT, "Greatroot Highlight");
        this.addBlock(AetherIIBlocks.GREATROOT_SHINGLES, "Greatroot Shingles");
        this.addBlock(AetherIIBlocks.GREATROOT_SMALL_SHINGLES, "Greatroot Small Shingles");
        this.addBlock(AetherIIBlocks.GREATROOT_BASE_PLANKS, "Greatroot Base Planks");
        this.addBlock(AetherIIBlocks.GREATROOT_TOP_PLANKS, "Greatroot Top Planks");
        this.addBlock(AetherIIBlocks.GREATROOT_BASE_BEAM, "Greatroot Base Beam");
        this.addBlock(AetherIIBlocks.GREATROOT_TOP_BEAM, "Greatroot Top Beam");
        this.addBlock(AetherIIBlocks.GREATROOT_BEAM, "Greatroot Beam");

        this.addBlock(AetherIIBlocks.WISPROOT_FLOORBOARDS, "Wisproot Floorboards");
        this.addBlock(AetherIIBlocks.WISPROOT_HIGHLIGHT, "Wisproot Highlight");
        this.addBlock(AetherIIBlocks.WISPROOT_SHINGLES, "Wisproot Shingles");
        this.addBlock(AetherIIBlocks.WISPROOT_SMALL_SHINGLES, "Wisproot Small Shingles");
        this.addBlock(AetherIIBlocks.WISPROOT_BASE_PLANKS, "Wisproot Base Planks");
        this.addBlock(AetherIIBlocks.WISPROOT_TOP_PLANKS, "Wisproot Top Planks");
        this.addBlock(AetherIIBlocks.WISPROOT_BASE_BEAM, "Wisproot Base Beam");
        this.addBlock(AetherIIBlocks.WISPROOT_TOP_BEAM, "Wisproot Top Beam");
        this.addBlock(AetherIIBlocks.WISPROOT_BEAM, "Wisproot Beam");


        this.addItem(AetherIIItems.SKYROOT_SHORTSWORD, "Skyroot Shortsword");
        this.addItem(AetherIIItems.SKYROOT_HAMMER, "Skyroot Hammer");
        this.addItem(AetherIIItems.SKYROOT_SPEAR, "Skyroot Spear");
        this.addItem(AetherIIItems.SKYROOT_PICKAXE, "Skyroot Pickaxe");
        this.addItem(AetherIIItems.SKYROOT_AXE, "Skyroot Axe");
        this.addItem(AetherIIItems.SKYROOT_SHOVEL, "Skyroot Shovel");
        this.addItem(AetherIIItems.SKYROOT_TROWEL, "Skyroot Trowel");

        this.addItem(AetherIIItems.HOLYSTONE_SHORTSWORD, "Holystone Shortsword");
        this.addItem(AetherIIItems.HOLYSTONE_HAMMER, "Holystone Hammer");
        this.addItem(AetherIIItems.HOLYSTONE_SPEAR, "Holystone Spear");
        this.addItem(AetherIIItems.HOLYSTONE_PICKAXE, "Holystone Pickaxe");
        this.addItem(AetherIIItems.HOLYSTONE_AXE, "Holystone Axe");
        this.addItem(AetherIIItems.HOLYSTONE_SHOVEL, "Holystone Shovel");
        this.addItem(AetherIIItems.HOLYSTONE_TROWEL, "Holystone Trowel");

        this.addItem(AetherIIItems.ZANITE_SHORTSWORD, "Zanite Shortsword");
        this.addItem(AetherIIItems.ZANITE_HAMMER, "Zanite Hammer");
        this.addItem(AetherIIItems.ZANITE_SPEAR, "Zanite Spear");
        this.addItem(AetherIIItems.ZANITE_PICKAXE, "Zanite Pickaxe");
        this.addItem(AetherIIItems.ZANITE_AXE, "Zanite Axe");
        this.addItem(AetherIIItems.ZANITE_SHOVEL, "Zanite Shovel");
        this.addItem(AetherIIItems.ZANITE_TROWEL, "Zanite Trowel");

        this.addItem(AetherIIItems.ARKENIUM_SHORTSWORD, "Arkenium Shortsword");
        this.addItem(AetherIIItems.ARKENIUM_HAMMER, "Arkenium Hammer");
        this.addItem(AetherIIItems.ARKENIUM_SPEAR, "Arkenium Spear");
        this.addItem(AetherIIItems.ARKENIUM_PICKAXE, "Arkenium Pickaxe");
        this.addItem(AetherIIItems.ARKENIUM_AXE, "Arkenium Axe");
        this.addItem(AetherIIItems.ARKENIUM_SHOVEL, "Arkenium Shovel");
        this.addItem(AetherIIItems.ARKENIUM_TROWEL, "Arkenium Trowel");

        this.addItem(AetherIIItems.GRAVITITE_SHORTSWORD, "Gravitite Shortsword");
        this.addItem(AetherIIItems.GRAVITITE_HAMMER, "Gravitite Hammer");
        this.addItem(AetherIIItems.GRAVITITE_SPEAR, "Gravitite Spear");
        this.addItem(AetherIIItems.GRAVITITE_PICKAXE, "Gravitite Pickaxe");
        this.addItem(AetherIIItems.GRAVITITE_AXE, "Gravitite Axe");
        this.addItem(AetherIIItems.GRAVITITE_SHOVEL, "Gravitite Shovel");
        this.addItem(AetherIIItems.GRAVITITE_TROWEL, "Gravitite Trowel");

        this.addItem(AetherIIItems.SKYROOT_STICK, "Skyroot Stick");
        this.addItem(AetherIIItems.AMBROSIUM_SHARD, "Ambrosium Shard");
        this.addItem(AetherIIItems.ZANITE_GEMSTONE, "Zanite Gemstone");
        this.addItem(AetherIIItems.RAW_ARKENIUM, "Raw Arkenium");
        this.addItem(AetherIIItems.ARKENIUM_PLATE, "Arkenium Plate");
        this.addItem(AetherIIItems.RAW_GRAVITITE, "Raw Gravitite");
        this.addItem(AetherIIItems.GRAVITITE_PLATE, "Gravitite Plate");
        this.addItem(AetherIIItems.GOLDEN_AMBER, "Golden Amber");
        this.addItem(AetherIIItems.TAEGORE_HIDE, "Taegore Hide");
        this.addItem(AetherIIItems.BURRUKAI_PELT, "Burrukai Pelt");
        this.addItem(AetherIIItems.AECHOR_PETAL, "Aechor Petal");

        this.addItem(AetherIIItems.BLUEBERRY, "Blueberry");
        this.addItem(AetherIIItems.ENCHANTED_BERRY, "Enchanted Berry");
        this.addItem(AetherIIItems.ORANGE, "Orange");
        this.addItem(AetherIIItems.WYNDBERRY, "Wyndberry");
        this.addItem(AetherIIItems.BLUE_SWET_JELLY, "Blue Swet Jelly");
        this.addItem(AetherIIItems.GOLDEN_SWET_JELLY, "Golden Swet Jelly");


        this.addItem(AetherIIItems.MUSIC_DISC_AETHER_TUNE, "Blue Music Disc");
        this.addDiscDesc(AetherIIItems.MUSIC_DISC_AETHER_TUNE, "Noisestorm - Aether Tune");
        this.addItem(AetherIIItems.MUSIC_DISC_ASCENDING_DAWN, "Valkyrie Music Disc");
        this.addDiscDesc(AetherIIItems.MUSIC_DISC_ASCENDING_DAWN, "Emile van Krieken - Ascending Dawn");
        this.addItem(AetherIIItems.MUSIC_DISC_AERWHALE, "Aerwhale Music Disc");
        this.addDiscDesc(AetherIIItems.MUSIC_DISC_AERWHALE, "AetherAudio - Aerwhale");
        this.addItem(AetherIIItems.MUSIC_DISC_APPROACHES, "Moa Music Disc");
        this.addDiscDesc(AetherIIItems.MUSIC_DISC_APPROACHES, "Emile van Krieken - Approaches");
        this.addItem(AetherIIItems.MUSIC_DISC_DEMISE, "Labyrinth Music Disc");
        this.addDiscDesc(AetherIIItems.MUSIC_DISC_DEMISE, "Moorziey - Demise");
        this.addItem(AetherIIItems.RECORDING_892, "Recording #892");
        this.addDiscDesc(AetherIIItems.RECORDING_892, "Emile van Krieken - ???");
      
        this.addItem(AetherIIItems.AERBUNNY_SPAWN_EGG, "Aerbunny Spawn Egg");
        this.addItem(AetherIIItems.FLYING_COW_SPAWN_EGG, "Flying Cow Spawn Egg");
        this.addItem(AetherIIItems.PHYG_SPAWN_EGG, "Phyg Spawn Egg");
        this.addItem(AetherIIItems.KIRRID_SPAWN_EGG, "Kirrid Spawn Egg");
        this.addItem(AetherIIItems.ZEPHYR_SPAWN_EGG, "Zephyr Spawn Egg");

        this.addItem(AetherIIItems.AETHER_PORTAL_FRAME, "Aether Portal Frame");

        this.addEffect(AetherIIEffects.TOXIN, "Toxin");

        this.addDamageTypeTooltip("slash", "\u00A79Slash\u00A7r Damage");
        this.addDamageTypeTooltip("impact", "\u00A7eImpact\u00A7r Damage");
        this.addDamageTypeTooltip("pierce", "\u00A7cPierce\u00A7r Damage");

        this.addPerItemAbilityTooltip(AetherIIItems.ZANITE_PICKAXE.get(), 1, "\u00A79Ability:\u00A7r Grows Stronger");
        this.addPerItemAbilityTooltip(AetherIIItems.ZANITE_AXE.get(), 1, "\u00A79Ability:\u00A7r Grows Stronger");
        this.addPerItemAbilityTooltip(AetherIIItems.ZANITE_SHOVEL.get(), 1, "\u00A79Ability:\u00A7r Grows Stronger");
        this.addPerItemAbilityTooltip(AetherIIItems.ZANITE_TROWEL.get(), 1, "\u00A79Ability:\u00A7r Grows Stronger");
        this.addPerItemAbilityTooltip(AetherIIItems.ZANITE_SHORTSWORD.get(), 1, "\u00A79Ability:\u00A7r Grows Stronger");
        this.addPerItemAbilityTooltip(AetherIIItems.ZANITE_HAMMER.get(), 1, "\u00A79Ability:\u00A7r Grows Stronger");
        this.addPerItemAbilityTooltip(AetherIIItems.ZANITE_SPEAR.get(), 1, "\u00A79Ability:\u00A7r Grows Stronger");

        this.addAttribute(AetherIIAttributes.SWEEP_RANGE.get(), "Sweep Range");
        this.addAttribute(AetherIIAttributes.SWEEP_KNOCKBACK.get(), "Sweep Knockback");
        this.addAttribute(AetherIIAttributes.SWEEP_DAMAGE.get(), "Sweep Damage");
        this.addAttribute(AetherIIAttributes.SHOCK_RANGE.get(), "Shock Range");
        this.addAttribute(AetherIIAttributes.SHOCK_KNOCKBACK.get(), "Shock Knockback");
        this.addAttribute(AetherIIAttributes.SHOCK_DAMAGE.get(), "Shock Damage");
        this.addAttribute(AetherIIAttributes.STAB_RADIUS.get(), "Stab Radius");
        this.addAttribute(AetherIIAttributes.STAB_DISTANCE.get(), "Stab Distance");
        this.addAttribute(AetherIIAttributes.STAB_KNOCKBACK.get(), "Stab Knockback");
        this.addAttribute(AetherIIAttributes.STAB_DAMAGE.get(), "Stab Damage");

        this.addEntityType(AetherIIEntityTypes.AERBUNNY, "Aerbunny");
        this.addEntityType(AetherIIEntityTypes.PHYG, "Phyg");
        this.addEntityType(AetherIIEntityTypes.FLYING_COW, "Flying Cow");
        this.addEntityType(AetherIIEntityTypes.KIRRID, "Kirrid");
        this.addEntityType(AetherIIEntityTypes.ZEPHYR, "Zephyr");

        this.addCreativeTab(AetherIICreativeTabs.AETHER_II_BUILDING_BLOCKS.get(), "Aether II Building Blocks");
        this.addCreativeTab(AetherIICreativeTabs.AETHER_II_DUNGEON_BLOCKS.get(), "Aether II Dungeon Blocks");
        this.addCreativeTab(AetherIICreativeTabs.AETHER_II_MASONRY_BLOCKS.get(), "Aether II Masonry Blocks");
        this.addCreativeTab(AetherIICreativeTabs.AETHER_II_NATURAL_BLOCKS.get(), "Aether II Natural Blocks");
        this.addCreativeTab(AetherIICreativeTabs.AETHER_II_FUNCTIONAL_BLOCKS.get(), "Aether II Functional Blocks");
        this.addCreativeTab(AetherIICreativeTabs.AETHER_II_EQUIPMENT_AND_UTILITIES.get(), "Aether II Equipment & Utilities");
        this.addCreativeTab(AetherIICreativeTabs.AETHER_II_ARMOR_AND_ACCESSORIES.get(), "Aether II Armor & Accessories");
        this.addCreativeTab(AetherIICreativeTabs.AETHER_II_FOOD_AND_DRINKS.get(), "Aether II Food & Drinks");
        this.addCreativeTab(AetherIICreativeTabs.AETHER_II_INGREDIENTS.get(), "Aether II Ingredients");
        this.addCreativeTab(AetherIICreativeTabs.AETHER_II_SPAWN_EGGS.get(), "Aether II Spawn Eggs");

        this.addSubtitle("block", "aether_portal.ambient", "Aether Portal whooshes");
        this.addSubtitle("block", "aether_portal.trigger", "Aether Portal noise intensifies");
        this.addSubtitle("block", "aercloud.blue_aercloud_bounce", "Blue Aercloud bounces");

        this.addSubtitle("entity", "phyg.ambient", "Phyg oinks");
        this.addSubtitle("entity", "phyg.death", "Phyg dies");
        this.addSubtitle("entity", "phyg.hurt", "Phyg hurts");
        this.addSubtitle("entity", "phyg.saddle", "Saddle equips");
        this.addSubtitle("entity", "phyg.step", "Footsteps");

        this.addSubtitle("entity", "flying_cow.ambient", "Flying Cow moos");
        this.addSubtitle("entity", "flying_cow.death", "Flying Cow dies");
        this.addSubtitle("entity", "flying_cow.hurt", "Flying Cow hurts");
        this.addSubtitle("entity", "flying_cow.saddle", "Saddle equips");
        this.addSubtitle("entity", "flying_cow.milk", "Flying Cow gets milked");
        this.addSubtitle("entity", "flying_cow.step", "Footsteps");


        this.addSubtitle("entity", "aerbunny.death", "Aerbunny dies");
        this.addSubtitle("entity", "aerbunny.hurt", "Aerbunny squeals");
        this.addSubtitle("entity", "aerbunny.lift", "Aerbunny squeaks");

        this.addSubtitle("entity", "zephyr.shoot", "Zephyr spits");
        this.addSubtitle("entity", "zephyr.ambient", "Zephyr blows");
        this.addSubtitle("entity", "zephyr.death", "Zephyr dies");
        this.addSubtitle("entity", "zephyr.hurt", "Zephyr hurts");

        this.addContainerType(AetherIIMenuTypes.HOLYSTONE_FURNACE, "Holystone Furnace");

        this.addPackDescription("mod", "Aether II Resources");
    }
}