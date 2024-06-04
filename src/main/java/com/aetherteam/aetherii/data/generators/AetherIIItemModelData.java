package com.aetherteam.aetherii.data.generators;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.data.providers.AetherIIItemModelProvider;
import com.aetherteam.aetherii.item.AetherIIItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class AetherIIItemModelData extends AetherIIItemModelProvider {
    public AetherIIItemModelData(PackOutput output, ExistingFileHelper helper) {
        super(output, AetherII.MODID, helper);
    }

    @Override
    protected void registerModels() {
        // Tools
        this.handheldItem(AetherIIItems.SKYROOT_PICKAXE.get(), "tools/");
        this.handheldItem(AetherIIItems.SKYROOT_AXE.get(), "tools/");
        this.handheldItem(AetherIIItems.SKYROOT_SHOVEL.get(), "tools/");
        this.handheldItem(AetherIIItems.SKYROOT_TROWEL.get(), "tools/");

        this.handheldItem(AetherIIItems.HOLYSTONE_PICKAXE.get(), "tools/");
        this.handheldItem(AetherIIItems.HOLYSTONE_AXE.get(), "tools/");
        this.handheldItem(AetherIIItems.HOLYSTONE_SHOVEL.get(), "tools/");
        this.handheldItem(AetherIIItems.HOLYSTONE_TROWEL.get(), "tools/");

        this.handheldItem(AetherIIItems.ZANITE_PICKAXE.get(), "tools/");
        this.handheldItem(AetherIIItems.ZANITE_AXE.get(), "tools/");
        this.handheldItem(AetherIIItems.ZANITE_SHOVEL.get(), "tools/");
        this.handheldItem(AetherIIItems.ZANITE_TROWEL.get(), "tools/");

        this.handheldItem(AetherIIItems.ARKENIUM_PICKAXE.get(), "tools/");
        this.handheldItem(AetherIIItems.ARKENIUM_AXE.get(), "tools/");
        this.handheldItem(AetherIIItems.ARKENIUM_SHOVEL.get(), "tools/");
        this.handheldItem(AetherIIItems.ARKENIUM_TROWEL.get(), "tools/");

        this.handheldItem(AetherIIItems.GRAVITITE_PICKAXE.get(), "tools/");
        this.handheldItem(AetherIIItems.GRAVITITE_AXE.get(), "tools/");
        this.handheldItem(AetherIIItems.GRAVITITE_SHOVEL.get(), "tools/");
        this.handheldItem(AetherIIItems.GRAVITITE_TROWEL.get(), "tools/");

        // Weapons
        this.handheldItem(AetherIIItems.SKYROOT_SHORTSWORD.get(), "weapons/");
        this.handheldItem(AetherIIItems.SKYROOT_HAMMER.get(), "weapons/");
        this.handheldItem(AetherIIItems.SKYROOT_SPEAR.get(), "weapons/");

        this.handheldItem(AetherIIItems.HOLYSTONE_SHORTSWORD.get(), "weapons/");
        this.handheldItem(AetherIIItems.HOLYSTONE_HAMMER.get(), "weapons/");
        this.handheldItem(AetherIIItems.HOLYSTONE_SPEAR.get(), "weapons/");

        this.handheldItem(AetherIIItems.ZANITE_SHORTSWORD.get(), "weapons/");
        this.handheldItem(AetherIIItems.ZANITE_HAMMER.get(), "weapons/");
        this.handheldItem(AetherIIItems.ZANITE_SPEAR.get(), "weapons/");

        this.handheldItem(AetherIIItems.ARKENIUM_SHORTSWORD.get(), "weapons/");
        this.handheldItem(AetherIIItems.ARKENIUM_HAMMER.get(), "weapons/");
        this.handheldItem(AetherIIItems.ARKENIUM_SPEAR.get(), "weapons/");

        this.handheldItem(AetherIIItems.GRAVITITE_SHORTSWORD.get(), "weapons/");
        this.handheldItem(AetherIIItems.GRAVITITE_HAMMER.get(), "weapons/");
        this.handheldItem(AetherIIItems.GRAVITITE_SPEAR.get(), "weapons/");

        // Materials
        this.handheldItem(AetherIIItems.SKYROOT_STICK.get(), "materials/");
        this.item(AetherIIItems.AMBROSIUM_SHARD.get(), "materials/");
        this.item(AetherIIItems.ZANITE_GEMSTONE.get(), "materials/");
        this.item(AetherIIItems.RAW_ARKENIUM.get(), "materials/");
        this.item(AetherIIItems.ARKENIUM_PLATE.get(), "materials/");
        this.item(AetherIIItems.ARKENIUM_STRIP.get(), "materials/");
        this.item(AetherIIItems.RAW_GRAVITITE.get(), "materials/");
        this.item(AetherIIItems.GRAVITITE_PLATE.get(), "materials/");
        this.item(AetherIIItems.GOLDEN_AMBER.get(), "materials/");
        this.item(AetherIIItems.TAEGORE_HIDE.get(), "materials/");
        this.item(AetherIIItems.BURRUKAI_PELT.get(), "materials/");
        this.item(AetherIIItems.AECHOR_PETAL.get(), "materials/");
        this.item(AetherIIItems.ARCTIC_SNOWBALL.get(), "materials/");
        this.item(AetherIIItems.GREEN_SWET_GEL.get(), "materials/");
        this.item(AetherIIItems.BLUE_SWET_GEL.get(), "materials/");
        this.item(AetherIIItems.PURPLE_SWET_GEL.get(), "materials/");

        // Food
        this.item(AetherIIItems.BLUEBERRY.get(), "food/");
        this.item(AetherIIItems.ENCHANTED_BERRY.get(), "food/");
        this.item(AetherIIItems.ORANGE.get(), "food/");
        this.item(AetherIIItems.WYNDBERRY.get(), "food/");
        this.item(AetherIIItems.BLUE_SWET_JELLY.get(), "food/");
        this.item(AetherIIItems.GOLDEN_SWET_JELLY.get(), "food/");

        // Music Discs
        this.item(AetherIIItems.MUSIC_DISC_AETHER_TUNE.get(), "miscellaneous/");
        this.item(AetherIIItems.MUSIC_DISC_ASCENDING_DAWN.get(), "miscellaneous/");
        this.item(AetherIIItems.MUSIC_DISC_AERWHALE.get(), "miscellaneous/");
        this.item(AetherIIItems.MUSIC_DISC_APPROACHES.get(), "miscellaneous/");
        this.item(AetherIIItems.MUSIC_DISC_DEMISE.get(), "miscellaneous/");
        this.item(AetherIIItems.RECORDING_892.get(), "miscellaneous/");

        // Spawn Eggs
        this.eggItem(AetherIIItems.FLYING_COW_SPAWN_EGG.get());
        this.eggItem(AetherIIItems.SHEEPUFF_SPAWN_EGG.get());
        this.eggItem(AetherIIItems.PHYG_SPAWN_EGG.get());
        this.eggItem(AetherIIItems.AERBUNNY_SPAWN_EGG.get());
        this.eggItem(AetherIIItems.KIRRID_SPAWN_EGG.get());
        this.eggItem(AetherIIItems.ZEPHYR_SPAWN_EGG.get());

        // Misc
        this.portalItem(AetherIIItems.AETHER_PORTAL_FRAME.get(), "miscellaneous/");

        // Blocks
        // Dirt
        this.itemBlock(AetherIIBlocks.AETHER_GRASS_BLOCK.get());
        this.itemBlock(AetherIIBlocks.ENCHANTED_AETHER_GRASS_BLOCK.get());
        this.itemBlock(AetherIIBlocks.AETHER_DIRT_PATH.get());
        this.itemBlock(AetherIIBlocks.AETHER_DIRT.get());
        this.itemBlock(AetherIIBlocks.COARSE_AETHER_DIRT.get());
        this.itemBlock(AetherIIBlocks.AETHER_FARMLAND.get());

        // Underground
        this.itemBlock(AetherIIBlocks.HOLYSTONE.get());
        this.itemBlock(AetherIIBlocks.UNDERSHALE.get());
        this.itemBlock(AetherIIBlocks.AGIOSITE.get());
        this.itemBlock(AetherIIBlocks.CRUDE_SCATTERGLASS.get());

        // Highfields
        this.itemBlock(AetherIIBlocks.QUICKSOIL.get());
        this.itemBlock(AetherIIBlocks.MOSSY_HOLYSTONE.get());

        // Magnetic
        this.itemBlock(AetherIIBlocks.FERROSITE_SAND.get());
        this.itemBlock(AetherIIBlocks.FERROSITE.get());
        this.itemBlock(AetherIIBlocks.RUSTED_FERROSITE.get());

        // Arctic
        this.itemBlock(AetherIIBlocks.ARCTIC_SNOW_BLOCK.get());
        this.itemBlock(AetherIIBlocks.ARCTIC_SNOW.get());
        this.itemBlock(AetherIIBlocks.ARCTIC_ICE.get());
        this.itemBlock(AetherIIBlocks.ARCTIC_PACKED_ICE.get());
        this.itemBlock(AetherIIBlocks.ICESTONE.get());

        // Irradiated
        this.itemBlock(AetherIIBlocks.IRRADIATED_HOLYSTONE.get());

        // Ores
        this.itemBlock(AetherIIBlocks.AMBROSIUM_ORE.get());
        this.itemBlock(AetherIIBlocks.ZANITE_ORE.get());
        this.itemBlock(AetherIIBlocks.ARKENIUM_ORE.get());
        this.itemBlock(AetherIIBlocks.GRAVITITE_ORE.get());

        // Aerclouds
        this.aercloudItem(AetherIIBlocks.COLD_AERCLOUD.get());
        this.aercloudItem(AetherIIBlocks.BLUE_AERCLOUD.get());
        this.aercloudItem(AetherIIBlocks.GOLDEN_AERCLOUD.get());
        this.aercloudItem(AetherIIBlocks.GREEN_AERCLOUD.get());
        this.itemBlock(AetherIIBlocks.PURPLE_AERCLOUD.get());
        this.aercloudItem(AetherIIBlocks.STORM_AERCLOUD.get());

        // Moa Nest
        this.itemBlock(AetherIIBlocks.WOVEN_SKYROOT_STICKS.get());

        // Logs
        this.itemBlock(AetherIIBlocks.SKYROOT_LOG.get());
        this.itemBlock(AetherIIBlocks.STRIPPED_SKYROOT_LOG.get());
        this.itemBlock(AetherIIBlocks.GREATROOT_LOG.get());
        this.itemBlock(AetherIIBlocks.WISPROOT_LOG.get());
        this.itemBlock(AetherIIBlocks.MOSSY_WISPROOT_LOG.get());
        this.itemBlock(AetherIIBlocks.AMBEROOT_LOG.get());
        this.itemBlock(AetherIIBlocks.SKYROOT_WOOD.get());
        this.itemBlock(AetherIIBlocks.STRIPPED_SKYROOT_WOOD.get());
        this.itemBlock(AetherIIBlocks.GREATROOT_WOOD.get());
        this.itemBlock(AetherIIBlocks.WISPROOT_WOOD.get());
        this.itemBlock(AetherIIBlocks.AMBEROOT_WOOD.get());

        // Leaf Pile
        this.itemBlock(AetherIIBlocks.SKYROOT_LEAF_PILE.get());
        this.itemBlock(AetherIIBlocks.SKYPLANE_LEAF_PILE.get());
        this.itemBlock(AetherIIBlocks.SKYBIRCH_LEAF_PILE.get());
        this.itemBlock(AetherIIBlocks.SKYPINE_LEAF_PILE.get());
        this.itemBlock(AetherIIBlocks.WISPROOT_LEAF_PILE.get());
        this.itemBlock(AetherIIBlocks.WISPTOP_LEAF_PILE.get());
        this.itemBlock(AetherIIBlocks.GREATROOT_LEAF_PILE.get());
        this.itemBlock(AetherIIBlocks.GREATOAK_LEAF_PILE.get());
        this.itemBlock(AetherIIBlocks.GREATBOA_LEAF_PILE.get());
        this.itemBlock(AetherIIBlocks.AMBEROOT_LEAF_PILE.get());

        // Leaves
        this.itemBlock(AetherIIBlocks.SKYROOT_LEAVES.get());
        this.itemBlock(AetherIIBlocks.SKYPLANE_LEAVES.get());
        this.itemBlock(AetherIIBlocks.SKYBIRCH_LEAVES.get());
        this.itemBlock(AetherIIBlocks.SKYPINE_LEAVES.get());
        this.itemBlock(AetherIIBlocks.WISPROOT_LEAVES.get());
        this.itemBlock(AetherIIBlocks.WISPTOP_LEAVES.get());
        this.itemBlock(AetherIIBlocks.GREATROOT_LEAVES.get());
        this.itemBlock(AetherIIBlocks.GREATOAK_LEAVES.get());
        this.itemBlock(AetherIIBlocks.GREATBOA_LEAVES.get());
        this.itemBlock(AetherIIBlocks.AMBEROOT_LEAVES.get());

        // Saplings
        this.itemBlockFlat(AetherIIBlocks.SKYROOT_SAPLING.get(), "natural/");
        this.itemBlockFlat(AetherIIBlocks.WISPROOT_SAPLING.get(), "natural/");
        this.itemBlockFlat(AetherIIBlocks.WISPTOP_SAPLING.get(), "natural/");
        this.itemBlockFlat(AetherIIBlocks.GREATROOT_SAPLING.get(), "natural/");
        this.itemBlockFlat(AetherIIBlocks.GREATOAK_SAPLING.get(), "natural/");
        this.itemBlockFlat(AetherIIBlocks.GREATBOA_SAPLING.get(), "natural/");
        this.itemBlockFlat(AetherIIBlocks.AMBEROOT_SAPLING.get(), "natural/");

        // Grasses
        this.itemBlockGrass(AetherIIBlocks.AETHER_SHORT_GRASS.get(), "natural/");
        this.itemBlockGrass(AetherIIBlocks.AETHER_MEDIUM_GRASS.get(), "natural/");
        this.itemBlockGrass(AetherIIBlocks.AETHER_LONG_GRASS.get(), "natural/");

        // Bushes
        this.itemBlock(AetherIIBlocks.HIGHLANDS_BUSH.get());
        this.itemBlock(AetherIIBlocks.BLUEBERRY_BUSH.get());
        this.itemBlockFlat(AetherIIBlocks.BLUEBERRY_BUSH_STEM.get(), "natural/");

        // Orange Tree
        this.orangeTree(AetherIIBlocks.ORANGE_TREE.get());

        // Ground Decoration
        this.item(AetherIIBlocks.SKYROOT_TWIG.asItem(), "miscellaneous/");
        this.item(AetherIIBlocks.HOLYSTONE_ROCK.asItem(), "miscellaneous/");

        // Skyroot Planks
        this.itemBlock(AetherIIBlocks.SKYROOT_PLANKS.get());
        this.itemBlock(AetherIIBlocks.SKYROOT_STAIRS.get());
        this.itemBlock(AetherIIBlocks.SKYROOT_SLAB.get());
        this.itemFence(AetherIIBlocks.SKYROOT_FENCE.get(), AetherIIBlocks.SKYROOT_PLANKS.get(), "construction/");
        this.itemBlock(AetherIIBlocks.SKYROOT_FENCE_GATE.get());
        this.item(AetherIIBlocks.SKYROOT_DOOR.get().asItem(), "miscellaneous/");
        this.itemBlock(AetherIIBlocks.SKYROOT_TRAPDOOR.get(), "_bottom");
        this.itemButton(AetherIIBlocks.SKYROOT_BUTTON.get(), AetherIIBlocks.SKYROOT_PLANKS.get(), "construction/");
        this.itemBlock(AetherIIBlocks.SKYROOT_PRESSURE_PLATE.get());

        // Skyroot Masonry Blocks
        this.itemBlock(AetherIIBlocks.SKYROOT_FLOORBOARDS.get());
        this.itemBlock(AetherIIBlocks.SKYROOT_HIGHLIGHT.get());
        this.itemBlock(AetherIIBlocks.SKYROOT_SHINGLES.get());
        this.itemBlock(AetherIIBlocks.SKYROOT_SMALL_SHINGLES.get());
        this.itemBlock(AetherIIBlocks.SKYROOT_BASE_PLANKS.get());
        this.itemBlock(AetherIIBlocks.SKYROOT_TOP_PLANKS.get());
        this.itemBlock(AetherIIBlocks.SKYROOT_BASE_BEAM.get());
        this.itemBlock(AetherIIBlocks.SKYROOT_TOP_BEAM.get());
        this.itemBlock(AetherIIBlocks.SKYROOT_BEAM.get());

        // Greatroot Planks
        this.itemBlock(AetherIIBlocks.GREATROOT_PLANKS.get());
        this.itemBlock(AetherIIBlocks.GREATROOT_STAIRS.get());
        this.itemBlock(AetherIIBlocks.GREATROOT_SLAB.get());
        this.itemFence(AetherIIBlocks.GREATROOT_FENCE.get(), AetherIIBlocks.GREATROOT_PLANKS.get(), "construction/");
        this.itemBlock(AetherIIBlocks.GREATROOT_FENCE_GATE.get());
        this.item(AetherIIBlocks.GREATROOT_DOOR.get().asItem(), "miscellaneous/");
        this.itemBlock(AetherIIBlocks.GREATROOT_TRAPDOOR.get(), "_bottom");
        this.itemButton(AetherIIBlocks.GREATROOT_BUTTON.get(), AetherIIBlocks.GREATROOT_PLANKS.get(), "construction/");
        this.itemBlock(AetherIIBlocks.GREATROOT_PRESSURE_PLATE.get());

        // Greatroot Masonry Blocks
        this.itemBlock(AetherIIBlocks.GREATROOT_FLOORBOARDS.get());
        this.itemBlock(AetherIIBlocks.GREATROOT_HIGHLIGHT.get());
        this.itemBlock(AetherIIBlocks.GREATROOT_SHINGLES.get());
        this.itemBlock(AetherIIBlocks.GREATROOT_SMALL_SHINGLES.get());
        this.itemBlock(AetherIIBlocks.GREATROOT_BASE_PLANKS.get());
        this.itemBlock(AetherIIBlocks.GREATROOT_TOP_PLANKS.get());
        this.itemBlock(AetherIIBlocks.GREATROOT_BASE_BEAM.get());
        this.itemBlock(AetherIIBlocks.GREATROOT_TOP_BEAM.get());
        this.itemBlock(AetherIIBlocks.GREATROOT_BEAM.get());

        // Wisproot Planks
        this.itemBlock(AetherIIBlocks.WISPROOT_PLANKS.get());
        this.itemBlock(AetherIIBlocks.WISPROOT_STAIRS.get());
        this.itemBlock(AetherIIBlocks.WISPROOT_SLAB.get());
        this.itemFence(AetherIIBlocks.WISPROOT_FENCE.get(), AetherIIBlocks.WISPROOT_PLANKS.get(), "construction/");
        this.itemBlock(AetherIIBlocks.WISPROOT_FENCE_GATE.get());
        this.item(AetherIIBlocks.WISPROOT_DOOR.get().asItem(), "miscellaneous/");
        this.itemBlock(AetherIIBlocks.WISPROOT_TRAPDOOR.get(), "_bottom");
        this.itemButton(AetherIIBlocks.WISPROOT_BUTTON.get(), AetherIIBlocks.WISPROOT_PLANKS.get(), "construction/");
        this.itemBlock(AetherIIBlocks.WISPROOT_PRESSURE_PLATE.get());

        // Wisproot Masonry Blocks
        this.itemBlock(AetherIIBlocks.WISPROOT_FLOORBOARDS.get());
        this.itemBlock(AetherIIBlocks.WISPROOT_HIGHLIGHT.get());
        this.itemBlock(AetherIIBlocks.WISPROOT_SHINGLES.get());
        this.itemBlock(AetherIIBlocks.WISPROOT_SMALL_SHINGLES.get());
        this.itemBlock(AetherIIBlocks.WISPROOT_BASE_PLANKS.get());
        this.itemBlock(AetherIIBlocks.WISPROOT_TOP_PLANKS.get());
        this.itemBlock(AetherIIBlocks.WISPROOT_BASE_BEAM.get());
        this.itemBlock(AetherIIBlocks.WISPROOT_TOP_BEAM.get());
        this.itemBlock(AetherIIBlocks.WISPROOT_BEAM.get());

        // Holystone
        this.itemBlock(AetherIIBlocks.HOLYSTONE_STAIRS.get());
        this.itemBlock(AetherIIBlocks.HOLYSTONE_SLAB.get());
        this.itemWallBlock(AetherIIBlocks.HOLYSTONE_WALL.get(), AetherIIBlocks.HOLYSTONE.get(), "natural/");
        this.itemButton(AetherIIBlocks.HOLYSTONE_BUTTON.get(), AetherIIBlocks.HOLYSTONE.get(), "natural/");
        this.itemBlock(AetherIIBlocks.HOLYSTONE_PRESSURE_PLATE.get());

        // Mossy Holystone
        this.itemBlock(AetherIIBlocks.MOSSY_HOLYSTONE_STAIRS.get());
        this.itemBlock(AetherIIBlocks.MOSSY_HOLYSTONE_SLAB.get());
        this.itemWallBlock(AetherIIBlocks.MOSSY_HOLYSTONE_WALL.get(), AetherIIBlocks.MOSSY_HOLYSTONE.get(), "natural/");

        // Irradiated Holystone
        this.itemBlock(AetherIIBlocks.IRRADIATED_HOLYSTONE_STAIRS.get());
        this.itemBlock(AetherIIBlocks.IRRADIATED_HOLYSTONE_SLAB.get());
        this.itemWallBlock(AetherIIBlocks.IRRADIATED_HOLYSTONE_WALL.get(), AetherIIBlocks.IRRADIATED_HOLYSTONE.get(), "natural/");

        // Holystone Bricks
        this.itemBlock(AetherIIBlocks.HOLYSTONE_BRICKS.get());
        this.itemBlock(AetherIIBlocks.HOLYSTONE_BRICK_STAIRS.get());
        this.itemBlock(AetherIIBlocks.HOLYSTONE_BRICK_SLAB.get());
        this.itemWallBlock(AetherIIBlocks.HOLYSTONE_BRICK_WALL.get(), AetherIIBlocks.HOLYSTONE_BRICKS.get(), "construction/");

        // Holystone Masonry Blocks
        this.itemBlock(AetherIIBlocks.HOLYSTONE_FLAGSTONES.get());
        this.itemBlock(AetherIIBlocks.HOLYSTONE_HEADSTONE.get());
        this.itemBlock(AetherIIBlocks.HOLYSTONE_KEYSTONE.get());
        this.itemBlock(AetherIIBlocks.HOLYSTONE_BASE_BRICKS.get());
        this.itemBlock(AetherIIBlocks.HOLYSTONE_CAPSTONE_BRICKS.get());
        this.itemBlock(AetherIIBlocks.HOLYSTONE_BASE_PILLAR.get());
        this.itemBlock(AetherIIBlocks.HOLYSTONE_CAPSTONE_PILLAR.get());
        this.itemBlock(AetherIIBlocks.HOLYSTONE_PILLAR.get());

        // Faded Holystone Bricks
        this.itemBlock(AetherIIBlocks.FADED_HOLYSTONE_BRICKS.get());
        this.itemBlock(AetherIIBlocks.FADED_HOLYSTONE_BRICK_STAIRS.get());
        this.itemBlock(AetherIIBlocks.FADED_HOLYSTONE_BRICK_SLAB.get());
        this.itemWallBlock(AetherIIBlocks.FADED_HOLYSTONE_BRICK_WALL.get(), AetherIIBlocks.FADED_HOLYSTONE_BRICKS.get(), "construction/");

        // Undershale
        this.itemBlock(AetherIIBlocks.UNDERSHALE_STAIRS.get());
        this.itemBlock(AetherIIBlocks.UNDERSHALE_SLAB.get());
        this.itemWallBlock(AetherIIBlocks.UNDERSHALE_WALL.get(), AetherIIBlocks.UNDERSHALE.get(), "natural/");

        // Agiosite
        this.itemBlock(AetherIIBlocks.AGIOSITE_STAIRS.get());
        this.itemBlock(AetherIIBlocks.AGIOSITE_SLAB.get());
        this.itemWallBlock(AetherIIBlocks.AGIOSITE_WALL.get(), AetherIIBlocks.AGIOSITE.get(), "natural/");

        // Agiosite Bricks
        this.itemBlock(AetherIIBlocks.AGIOSITE_BRICKS.get());
        this.itemBlock(AetherIIBlocks.AGIOSITE_BRICK_STAIRS.get());
        this.itemBlock(AetherIIBlocks.AGIOSITE_BRICK_SLAB.get());
        this.itemWallBlock(AetherIIBlocks.AGIOSITE_BRICK_WALL.get(), AetherIIBlocks.AGIOSITE_BRICKS.get(), "construction/");

        // Agiosite Masonry Blocks
        this.itemBlock(AetherIIBlocks.AGIOSITE_FLAGSTONES.get());
        this.itemBlock(AetherIIBlocks.AGIOSITE_KEYSTONE.get());
        this.itemBlock(AetherIIBlocks.AGIOSITE_BASE_BRICKS.get());
        this.itemBlock(AetherIIBlocks.AGIOSITE_CAPSTONE_BRICKS.get());
        this.itemBlock(AetherIIBlocks.AGIOSITE_BASE_PILLAR.get());
        this.itemBlock(AetherIIBlocks.AGIOSITE_CAPSTONE_PILLAR.get());
        this.itemBlock(AetherIIBlocks.AGIOSITE_PILLAR.get());

        // Icestone
        this.itemBlock(AetherIIBlocks.ICESTONE_STAIRS.get());
        this.itemBlock(AetherIIBlocks.ICESTONE_SLAB.get());
        this.itemWallBlock(AetherIIBlocks.ICESTONE_WALL.get(), AetherIIBlocks.ICESTONE.get(), "natural/");

        // Glass
        this.itemBlock(AetherIIBlocks.QUICKSOIL_GLASS.get());
        this.pane(AetherIIBlocks.QUICKSOIL_GLASS_PANE.get(), AetherIIBlocks.QUICKSOIL_GLASS.get(), "construction/");
        this.pane(AetherIIBlocks.CRUDE_SCATTERGLASS_PANE.get(), AetherIIBlocks.CRUDE_SCATTERGLASS.get(), "natural/");
        this.itemBlock(AetherIIBlocks.SCATTERGLASS.get());
        this.pane(AetherIIBlocks.SCATTERGLASS_PANE.get(), AetherIIBlocks.SCATTERGLASS.get(), "natural/");

        // Wool
        this.itemBlock(AetherIIBlocks.CLOUDWOOL.get());
        this.itemBlock(AetherIIBlocks.CLOUDWOOL_CARPET.get());

        // Mineral Blocks
        this.itemBlock(AetherIIBlocks.AMBROSIUM_BLOCK.get());
        this.itemBlock(AetherIIBlocks.ZANITE_BLOCK.get());
        this.itemBlock(AetherIIBlocks.GRAVITITE_BLOCK.get());

        // Utility
        this.itemBlockFlat(AetherIIBlocks.AMBROSIUM_TORCH.get(), "utility/");
        this.itemBlock(AetherIIBlocks.SKYROOT_CRAFTING_TABLE.get());
        this.itemBlock(AetherIIBlocks.HOLYSTONE_FURNACE.get());
        this.itemBlock(AetherIIBlocks.MASONRY_BENCH.get());
        this.lookalikeBlock(AetherIIBlocks.SKYROOT_CHEST.get(), this.mcLoc("item/chest"));
        this.itemBlockFlat(AetherIIBlocks.SKYROOT_LADDER.get(), "construction/");

        // Bookshelves
        this.itemBlock(AetherIIBlocks.SKYROOT_BOOKSHELF.get());
        this.itemBlock(AetherIIBlocks.HOLYSTONE_BOOKSHELF.get());
    }
}