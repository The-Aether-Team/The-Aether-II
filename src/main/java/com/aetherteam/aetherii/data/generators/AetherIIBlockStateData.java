package com.aetherteam.aetherii.data.generators;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.data.providers.AetherIIBlockStateProvider;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class AetherIIBlockStateData extends AetherIIBlockStateProvider {
    public AetherIIBlockStateData(PackOutput output, ExistingFileHelper helper) {
        super(output, AetherII.MODID, helper);
    }

    @Override
    public void registerStatesAndModels() {
        // Portal
        this.portal(AetherIIBlocks.AETHER_PORTAL.get());

        // Aether Dirt
        this.grass(AetherIIBlocks.AETHER_GRASS_BLOCK.get(), AetherIIBlocks.AETHER_DIRT.get());
        this.enchantedGrass(AetherIIBlocks.ENCHANTED_AETHER_GRASS_BLOCK.get(), AetherIIBlocks.AETHER_GRASS_BLOCK.get(), AetherIIBlocks.AETHER_DIRT.get());
        this.dirtPath(AetherIIBlocks.AETHER_DIRT_PATH.get(), AetherIIBlocks.AETHER_DIRT.get());
        this.block(AetherIIBlocks.AETHER_DIRT.get(), "natural/");
        this.block(AetherIIBlocks.COARSE_AETHER_DIRT.get(), "natural/");
        this.farmland(AetherIIBlocks.AETHER_FARMLAND.get(), AetherIIBlocks.AETHER_DIRT.get());

        // Underground
        this.block(AetherIIBlocks.HOLYSTONE.get(), "natural/");
        this.block(AetherIIBlocks.UNDERSHALE.get(), "natural/");
        this.block(AetherIIBlocks.AGIOSITE.get(), "natural/");
        this.translucentBlock(AetherIIBlocks.CRUDE_SCATTERGLASS.get(), "natural/");

        // Highfields
        this.block(AetherIIBlocks.QUICKSOIL.get(), "natural/");
        this.block(AetherIIBlocks.MOSSY_HOLYSTONE.get(), "natural/");

        // Magnetic
        this.block(AetherIIBlocks.FERROSITE_SAND.get(), "natural/");
        this.block(AetherIIBlocks.FERROSITE.get(), "natural/");
        this.block(AetherIIBlocks.RUSTED_FERROSITE.get(), "natural/");

        // Arctic
        this.block(AetherIIBlocks.ARCTIC_SNOW_BLOCK.get(), "natural/");
        this.snowLayer(AetherIIBlocks.ARCTIC_SNOW.get(), AetherIIBlocks.ARCTIC_SNOW_BLOCK.get());
        this.translucentBlock(AetherIIBlocks.ARCTIC_ICE.get(), "natural/");
        this.block(AetherIIBlocks.ARCTIC_PACKED_ICE.get(), "natural/");
        this.block(AetherIIBlocks.ICESTONE.get(), "natural/");

        // Irradiated
        this.block(AetherIIBlocks.IRRADIATED_HOLYSTONE.get(), "natural/");

        // Ores
        this.block(AetherIIBlocks.AMBROSIUM_ORE.get(), "natural/");
        this.block(AetherIIBlocks.ZANITE_ORE.get(), "natural/");
        this.block(AetherIIBlocks.ARKENIUM_ORE.get(), "natural/");
        this.block(AetherIIBlocks.GRAVITITE_ORE.get(), "natural/");

        // Aerclouds
        this.aercloudAll(AetherIIBlocks.COLD_AERCLOUD.get(), "natural/");
        this.aercloudAll(AetherIIBlocks.BLUE_AERCLOUD.get(), "natural/");
        this.aercloudAll(AetherIIBlocks.GOLDEN_AERCLOUD.get(), "natural/");
        this.aercloudAll(AetherIIBlocks.GREEN_AERCLOUD.get(), "natural/");
        this.purpleAercloud(AetherIIBlocks.PURPLE_AERCLOUD.get());
        this.aercloudAll(AetherIIBlocks.STORM_AERCLOUD.get(), "natural/");

        // Moa Nest
        this.block(AetherIIBlocks.WOVEN_SKYROOT_STICKS.get(), "natural/");

        // Logs
        this.log(AetherIIBlocks.SKYROOT_LOG.get());
        this.log(AetherIIBlocks.STRIPPED_SKYROOT_LOG.get());
        this.log(AetherIIBlocks.GREATROOT_LOG.get());
        this.log(AetherIIBlocks.WISPROOT_LOG.get());
        this.mossyWisproot(AetherIIBlocks.MOSSY_WISPROOT_LOG.get(), AetherIIBlocks.WISPROOT_LOG.get());
        this.logDifferentTop(AetherIIBlocks.AMBEROOT_LOG.get(), AetherIIBlocks.SKYROOT_LOG.get());
        this.wood(AetherIIBlocks.SKYROOT_WOOD.get(), AetherIIBlocks.SKYROOT_LOG.get());
        this.wood(AetherIIBlocks.STRIPPED_SKYROOT_WOOD.get(), AetherIIBlocks.STRIPPED_SKYROOT_LOG.get());
        this.wood(AetherIIBlocks.GREATROOT_WOOD.get(), AetherIIBlocks.GREATROOT_LOG.get());
        this.wood(AetherIIBlocks.WISPROOT_WOOD.get(), AetherIIBlocks.WISPROOT_LOG.get());
        this.wood(AetherIIBlocks.AMBEROOT_WOOD.get(), AetherIIBlocks.AMBEROOT_LOG.get());

        // Leaf Pile
        this.leafPile(AetherIIBlocks.SKYROOT_LEAF_PILE.get(), AetherIIBlocks.SKYROOT_LEAVES.get());
        this.leafPile(AetherIIBlocks.SKYPLANE_LEAF_PILE.get(), AetherIIBlocks.SKYPLANE_LEAVES.get());
        this.leafPile(AetherIIBlocks.SKYBIRCH_LEAF_PILE.get(), AetherIIBlocks.SKYBIRCH_LEAVES.get());
        this.leafPile(AetherIIBlocks.SKYPINE_LEAF_PILE.get(), AetherIIBlocks.SKYPINE_LEAVES.get());
        this.leafPile(AetherIIBlocks.WISPROOT_LEAF_PILE.get(), AetherIIBlocks.WISPROOT_LEAVES.get());
        this.leafPile(AetherIIBlocks.WISPTOP_LEAF_PILE.get(), AetherIIBlocks.WISPTOP_LEAVES.get());
        this.leafPile(AetherIIBlocks.GREATROOT_LEAF_PILE.get(), AetherIIBlocks.GREATROOT_LEAVES.get());
        this.leafPile(AetherIIBlocks.GREATOAK_LEAF_PILE.get(), AetherIIBlocks.GREATOAK_LEAVES.get());
        this.leafPile(AetherIIBlocks.GREATBOA_LEAF_PILE.get(), AetherIIBlocks.GREATBOA_LEAVES.get());
        this.leafPile(AetherIIBlocks.AMBEROOT_LEAF_PILE.get(), AetherIIBlocks.AMBEROOT_LEAVES.get());

        // Leaves
        this.leaves(AetherIIBlocks.SKYROOT_LEAVES.get());
        this.leaves(AetherIIBlocks.SKYPLANE_LEAVES.get());
        this.leaves(AetherIIBlocks.SKYBIRCH_LEAVES.get());
        this.leaves(AetherIIBlocks.SKYPINE_LEAVES.get());
        this.leaves(AetherIIBlocks.WISPROOT_LEAVES.get());
        this.leaves(AetherIIBlocks.WISPTOP_LEAVES.get());
        this.leaves(AetherIIBlocks.GREATROOT_LEAVES.get());
        this.leaves(AetherIIBlocks.GREATOAK_LEAVES.get());
        this.leaves(AetherIIBlocks.GREATBOA_LEAVES.get());
        this.leaves(AetherIIBlocks.AMBEROOT_LEAVES.get());

        // Saplings
        this.saplingBlock(AetherIIBlocks.SKYROOT_SAPLING.get(), "natural/");
        this.saplingBlock(AetherIIBlocks.SKYBIRCH_SAPLING.get(), "natural/");
        this.saplingBlock(AetherIIBlocks.WISPROOT_SAPLING.get(), "natural/");
        this.saplingBlock(AetherIIBlocks.WISPTOP_SAPLING.get(), "natural/");
        this.saplingBlock(AetherIIBlocks.GREATROOT_SAPLING.get(), "natural/");
        this.saplingBlock(AetherIIBlocks.GREATOAK_SAPLING.get(), "natural/");
        this.saplingBlock(AetherIIBlocks.GREATBOA_SAPLING.get(), "natural/");
        this.saplingBlock(AetherIIBlocks.AMBEROOT_SAPLING.get(), "natural/");

        // Potted Saplings
        this.pottedPlant(AetherIIBlocks.POTTED_SKYROOT_SAPLING.get(), AetherIIBlocks.SKYROOT_SAPLING.get(), "natural/");
        this.pottedPlant(AetherIIBlocks.POTTED_SKYBIRCH_SAPLING.get(), AetherIIBlocks.SKYBIRCH_SAPLING.get(), "natural/");
        this.pottedPlant(AetherIIBlocks.POTTED_WISPROOT_SAPLING.get(), AetherIIBlocks.WISPTOP_SAPLING.get(), "natural/");
        this.pottedPlant(AetherIIBlocks.POTTED_WISPTOP_SAPLING.get(), AetherIIBlocks.WISPTOP_SAPLING.get(), "natural/");
        this.pottedPlant(AetherIIBlocks.POTTED_GREATROOT_SAPLING.get(), AetherIIBlocks.GREATROOT_LEAVES.get(), "natural/");
        this.pottedPlant(AetherIIBlocks.POTTED_GREATOAK_SAPLING.get(), AetherIIBlocks.GREATOAK_SAPLING.get(), "natural/");
        this.pottedPlant(AetherIIBlocks.POTTED_GREATBOA_SAPLING.get(), AetherIIBlocks.GREATBOA_SAPLING.get(), "natural/");
        this.pottedPlant(AetherIIBlocks.POTTED_AMBEROOT_SAPLING.get(), AetherIIBlocks.AMBEROOT_SAPLING.get(), "natural/");

        // Grasses
        this.shortGrass(AetherIIBlocks.AETHER_SHORT_GRASS.get());
        this.shortGrass(AetherIIBlocks.AETHER_MEDIUM_GRASS.get());
        this.shortGrass(AetherIIBlocks.AETHER_LONG_GRASS.get());

        // Flowers
        this.crossBlock(AetherIIBlocks.HESPEROSE.get(), "natural/");
        this.crossBlock(AetherIIBlocks.TARABLOOM.get(), "natural/");

        // Potted Flowers
        this.pottedPlant(AetherIIBlocks.POTTED_HESPEROSE.get(), AetherIIBlocks.HESPEROSE.get(), "natural/");
        this.pottedPlant(AetherIIBlocks.POTTED_TARABLOOM.get(), AetherIIBlocks.TARABLOOM.get(), "natural/");

        // Bushes
        this.bush(AetherIIBlocks.HIGHLANDS_BUSH.get());
        this.crossBlock(AetherIIBlocks.BLUEBERRY_BUSH_STEM.get(), "natural/");
        this.berryBush(AetherIIBlocks.BLUEBERRY_BUSH.get(), AetherIIBlocks.BLUEBERRY_BUSH_STEM.get());

        // Potted Bushes
        this.pottedBush(AetherIIBlocks.POTTED_HIGHLANDS_BUSH.get(), "natural/");
        this.pottedBush(AetherIIBlocks.POTTED_BLUEBERRY_BUSH.get(), AetherIIBlocks.POTTED_BLUEBERRY_BUSH_STEM.get(), "natural/");
        this.pottedStem(AetherIIBlocks.POTTED_BLUEBERRY_BUSH_STEM.get(), "natural/");

        // Orange Tree
        this.orangeTree(AetherIIBlocks.ORANGE_TREE.get());

        // Potted Orange Tree
        this.pottedOrangeTree(AetherIIBlocks.POTTED_ORANGE_TREE.get(), AetherIIBlocks.ORANGE_TREE.get());

        // Ground Decoration
        this.twig(AetherIIBlocks.SKYROOT_TWIG.get(), AetherIIBlocks.SKYROOT_LOG.get());
        this.rock(AetherIIBlocks.HOLYSTONE_ROCK.get(), AetherIIBlocks.HOLYSTONE.get());

        // Skyroot Planks
        this.block(AetherIIBlocks.SKYROOT_PLANKS.get(), "construction/");
        this.stairs(AetherIIBlocks.SKYROOT_STAIRS.get(), AetherIIBlocks.SKYROOT_PLANKS.get(), "construction/");
        this.slab(AetherIIBlocks.SKYROOT_SLAB.get(), AetherIIBlocks.SKYROOT_PLANKS.get(), "construction/");
        this.fence(AetherIIBlocks.SKYROOT_FENCE.get(), AetherIIBlocks.SKYROOT_PLANKS.get(), "construction/");
        this.fenceGateBlock(AetherIIBlocks.SKYROOT_FENCE_GATE.get(), AetherIIBlocks.SKYROOT_PLANKS.get(), "construction/");
        this.doorBlock(AetherIIBlocks.SKYROOT_DOOR.get(), texture(name(AetherIIBlocks.SKYROOT_DOOR.get()), "construction/", "_bottom"), texture(name(AetherIIBlocks.SKYROOT_DOOR.get()), "construction/", "_top"));
        this.trapdoorBlock(AetherIIBlocks.SKYROOT_TRAPDOOR.get(), texture(name(AetherIIBlocks.SKYROOT_TRAPDOOR.get()), "construction/"), true);
        this.buttonBlock(AetherIIBlocks.SKYROOT_BUTTON.get(), this.texture(this.name(AetherIIBlocks.SKYROOT_PLANKS.get()), "construction/"));
        this.pressurePlateBlock(AetherIIBlocks.SKYROOT_PRESSURE_PLATE.get(), this.texture(this.name(AetherIIBlocks.SKYROOT_PLANKS.get()), "construction/"));

        // Skyroot Decorative Blocks
        this.block(AetherIIBlocks.SKYROOT_FLOORBOARDS.get(), "decorative/");
        this.block(AetherIIBlocks.SKYROOT_HIGHLIGHT.get(), "decorative/");
        this.block(AetherIIBlocks.SKYROOT_SHINGLES.get(), "decorative/");
        this.block(AetherIIBlocks.SKYROOT_SMALL_SHINGLES.get(), "decorative/");
        this.decorativeBlock(AetherIIBlocks.SKYROOT_BASE_PLANKS.get(), AetherIIBlocks.SKYROOT_HIGHLIGHT.get());
        this.decorativeBlock(AetherIIBlocks.SKYROOT_TOP_PLANKS.get(), AetherIIBlocks.SKYROOT_HIGHLIGHT.get());
        this.decorativeBlock(AetherIIBlocks.SKYROOT_BASE_BEAM.get(), AetherIIBlocks.SKYROOT_HIGHLIGHT.get());
        this.decorativeBlock(AetherIIBlocks.SKYROOT_TOP_BEAM.get(), AetherIIBlocks.SKYROOT_HIGHLIGHT.get());
        this.decorativePillar(AetherIIBlocks.SKYROOT_BEAM.get(), AetherIIBlocks.SKYROOT_HIGHLIGHT.get());

        // Greatroot Planks
        this.block(AetherIIBlocks.GREATROOT_PLANKS.get(), "construction/");
        this.stairs(AetherIIBlocks.GREATROOT_STAIRS.get(), AetherIIBlocks.GREATROOT_PLANKS.get(), "construction/");
        this.slab(AetherIIBlocks.GREATROOT_SLAB.get(), AetherIIBlocks.GREATROOT_PLANKS.get(), "construction/");
        this.fence(AetherIIBlocks.GREATROOT_FENCE.get(), AetherIIBlocks.GREATROOT_PLANKS.get(), "construction/");
        this.fenceGateBlock(AetherIIBlocks.GREATROOT_FENCE_GATE.get(), AetherIIBlocks.GREATROOT_PLANKS.get(), "construction/");
        this.doorBlock(AetherIIBlocks.GREATROOT_DOOR.get(), texture(name(AetherIIBlocks.GREATROOT_DOOR.get()), "construction/", "_bottom"), texture(name(AetherIIBlocks.GREATROOT_DOOR.get()), "construction/", "_top"));
        this.trapdoorBlock(AetherIIBlocks.GREATROOT_TRAPDOOR.get(), texture(name(AetherIIBlocks.GREATROOT_TRAPDOOR.get()), "construction/"), true);
        this.buttonBlock(AetherIIBlocks.GREATROOT_BUTTON.get(), this.texture(this.name(AetherIIBlocks.GREATROOT_PLANKS.get()), "construction/"));
        this.pressurePlateBlock(AetherIIBlocks.GREATROOT_PRESSURE_PLATE.get(), this.texture(this.name(AetherIIBlocks.GREATROOT_PLANKS.get()), "construction/"));

        // Greatroot Decorative Blocks
        this.block(AetherIIBlocks.GREATROOT_FLOORBOARDS.get(), "decorative/");
        this.block(AetherIIBlocks.GREATROOT_HIGHLIGHT.get(), "decorative/");
        this.block(AetherIIBlocks.GREATROOT_SHINGLES.get(), "decorative/");
        this.block(AetherIIBlocks.GREATROOT_SMALL_SHINGLES.get(), "decorative/");
        this.decorativeBlock(AetherIIBlocks.GREATROOT_BASE_PLANKS.get(), AetherIIBlocks.GREATROOT_HIGHLIGHT.get());
        this.decorativeBlock(AetherIIBlocks.GREATROOT_TOP_PLANKS.get(), AetherIIBlocks.GREATROOT_HIGHLIGHT.get());
        this.decorativeBlock(AetherIIBlocks.GREATROOT_BASE_BEAM.get(), AetherIIBlocks.GREATROOT_HIGHLIGHT.get());
        this.decorativeBlock(AetherIIBlocks.GREATROOT_TOP_BEAM.get(), AetherIIBlocks.GREATROOT_HIGHLIGHT.get());
        this.decorativePillar(AetherIIBlocks.GREATROOT_BEAM.get(), AetherIIBlocks.GREATROOT_HIGHLIGHT.get());

        // Wisproot Planks
        this.block(AetherIIBlocks.WISPROOT_PLANKS.get(), "construction/");
        this.stairs(AetherIIBlocks.WISPROOT_STAIRS.get(), AetherIIBlocks.WISPROOT_PLANKS.get(), "construction/");
        this.slab(AetherIIBlocks.WISPROOT_SLAB.get(), AetherIIBlocks.WISPROOT_PLANKS.get(), "construction/");
        this.fence(AetherIIBlocks.WISPROOT_FENCE.get(), AetherIIBlocks.WISPROOT_PLANKS.get(), "construction/");
        this.fenceGateBlock(AetherIIBlocks.WISPROOT_FENCE_GATE.get(), AetherIIBlocks.WISPROOT_PLANKS.get(), "construction/");
        this.doorBlock(AetherIIBlocks.WISPROOT_DOOR.get(), texture(name(AetherIIBlocks.WISPROOT_DOOR.get()), "construction/", "_bottom"), texture(name(AetherIIBlocks.WISPROOT_DOOR.get()), "construction/", "_top"));
        this.trapdoorBlock(AetherIIBlocks.WISPROOT_TRAPDOOR.get(), texture(name(AetherIIBlocks.WISPROOT_TRAPDOOR.get()), "construction/"), true);
        this.buttonBlock(AetherIIBlocks.WISPROOT_BUTTON.get(), this.texture(this.name(AetherIIBlocks.WISPROOT_PLANKS.get()), "construction/"));
        this.pressurePlateBlock(AetherIIBlocks.WISPROOT_PRESSURE_PLATE.get(), this.texture(this.name(AetherIIBlocks.WISPROOT_PLANKS.get()), "construction/"));

        // Wisproot Decorative Blocks
        this.block(AetherIIBlocks.WISPROOT_FLOORBOARDS.get(), "decorative/");
        this.block(AetherIIBlocks.WISPROOT_HIGHLIGHT.get(), "decorative/");
        this.block(AetherIIBlocks.WISPROOT_SHINGLES.get(), "decorative/");
        this.block(AetherIIBlocks.WISPROOT_SMALL_SHINGLES.get(), "decorative/");
        this.decorativeBlock(AetherIIBlocks.WISPROOT_BASE_PLANKS.get(), AetherIIBlocks.WISPROOT_HIGHLIGHT.get());
        this.decorativeBlock(AetherIIBlocks.WISPROOT_TOP_PLANKS.get(),  AetherIIBlocks.WISPROOT_HIGHLIGHT.get());
        this.decorativeBlock(AetherIIBlocks.WISPROOT_BASE_BEAM.get(),  AetherIIBlocks.WISPROOT_HIGHLIGHT.get());
        this.decorativeBlock(AetherIIBlocks.WISPROOT_TOP_BEAM.get(),  AetherIIBlocks.WISPROOT_HIGHLIGHT.get());
        this.decorativePillar(AetherIIBlocks.WISPROOT_BEAM.get(),  AetherIIBlocks.WISPROOT_HIGHLIGHT.get());

        // Holystone
        this.stairs(AetherIIBlocks.HOLYSTONE_STAIRS.get(), AetherIIBlocks.HOLYSTONE.get(), "natural/");
        this.slab(AetherIIBlocks.HOLYSTONE_SLAB.get(), AetherIIBlocks.HOLYSTONE.get(), "natural/");
        this.wallBlock(AetherIIBlocks.HOLYSTONE_WALL.get(), AetherIIBlocks.HOLYSTONE.get(), "natural/");
        this.buttonBlock(AetherIIBlocks.HOLYSTONE_BUTTON.get(), this.texture(this.name(AetherIIBlocks.HOLYSTONE.get()), "natural/"));
        this.pressurePlateBlock(AetherIIBlocks.HOLYSTONE_PRESSURE_PLATE.get(), this.texture(this.name(AetherIIBlocks.HOLYSTONE.get()), "natural/"));

        // Mossy Holystone
        this.stairs(AetherIIBlocks.MOSSY_HOLYSTONE_STAIRS.get(), AetherIIBlocks.MOSSY_HOLYSTONE.get(), "natural/");
        this.slab(AetherIIBlocks.MOSSY_HOLYSTONE_SLAB.get(), AetherIIBlocks.MOSSY_HOLYSTONE.get(), "natural/");
        this.wallBlock(AetherIIBlocks.MOSSY_HOLYSTONE_WALL.get(), AetherIIBlocks.MOSSY_HOLYSTONE.get(), "natural/");

        // Irradiated Holystone
        this.stairs(AetherIIBlocks.IRRADIATED_HOLYSTONE_STAIRS.get(), AetherIIBlocks.IRRADIATED_HOLYSTONE.get(), "natural/");
        this.slab(AetherIIBlocks.IRRADIATED_HOLYSTONE_SLAB.get(), AetherIIBlocks.IRRADIATED_HOLYSTONE.get(), "natural/");
        this.wallBlock(AetherIIBlocks.IRRADIATED_HOLYSTONE_WALL.get(), AetherIIBlocks.IRRADIATED_HOLYSTONE.get(), "natural/");

        // Holystone Bricks
        this.block(AetherIIBlocks.HOLYSTONE_BRICKS.get(), "construction/");
        this.stairs(AetherIIBlocks.HOLYSTONE_BRICK_STAIRS.get(), AetherIIBlocks.HOLYSTONE_BRICKS.get(), "construction/");
        this.slab(AetherIIBlocks.HOLYSTONE_BRICK_SLAB.get(), AetherIIBlocks.HOLYSTONE_BRICKS.get(), "construction/");
        this.wallBlock(AetherIIBlocks.HOLYSTONE_BRICK_WALL.get(), AetherIIBlocks.HOLYSTONE_BRICKS.get(), "construction/");

        // Holystone Decorative Blocks
        this.block(AetherIIBlocks.HOLYSTONE_FLAGSTONES.get(), "decorative/");
        this.block(AetherIIBlocks.HOLYSTONE_HEADSTONE.get(), "decorative/");
        this.block(AetherIIBlocks.HOLYSTONE_KEYSTONE.get(), "decorative/");
        this.decorativeBlock(AetherIIBlocks.HOLYSTONE_BASE_BRICKS.get(), AetherIIBlocks.HOLYSTONE_KEYSTONE.get());
        this.decorativeBlock(AetherIIBlocks.HOLYSTONE_CAPSTONE_BRICKS.get(), AetherIIBlocks.HOLYSTONE_KEYSTONE.get());
        this.decorativeBlock(AetherIIBlocks.HOLYSTONE_BASE_PILLAR.get(), AetherIIBlocks.HOLYSTONE_KEYSTONE.get());
        this.decorativeBlock(AetherIIBlocks.HOLYSTONE_CAPSTONE_PILLAR.get(), AetherIIBlocks.HOLYSTONE_KEYSTONE.get());
        this.decorativePillar(AetherIIBlocks.HOLYSTONE_PILLAR.get(), AetherIIBlocks.HOLYSTONE_KEYSTONE.get());

        // Faded Holystone Decorative Blocks
        this.block(AetherIIBlocks.FADED_HOLYSTONE_FLAGSTONES.get(), "decorative/");
        this.block(AetherIIBlocks.FADED_HOLYSTONE_HEADSTONE.get(), "decorative/");
        this.block(AetherIIBlocks.FADED_HOLYSTONE_KEYSTONE.get(), "decorative/");
        this.decorativeBlock(AetherIIBlocks.FADED_HOLYSTONE_BASE_BRICKS.get(), AetherIIBlocks.FADED_HOLYSTONE_KEYSTONE.get());
        this.decorativeBlock(AetherIIBlocks.FADED_HOLYSTONE_CAPSTONE_BRICKS.get(), AetherIIBlocks.FADED_HOLYSTONE_KEYSTONE.get());
        this.decorativeBlock(AetherIIBlocks.FADED_HOLYSTONE_BASE_PILLAR.get(), AetherIIBlocks.FADED_HOLYSTONE_KEYSTONE.get());
        this.decorativeBlock(AetherIIBlocks.FADED_HOLYSTONE_CAPSTONE_PILLAR.get(), AetherIIBlocks.FADED_HOLYSTONE_KEYSTONE.get());
        this.decorativePillar(AetherIIBlocks.FADED_HOLYSTONE_PILLAR.get(), AetherIIBlocks.FADED_HOLYSTONE_KEYSTONE.get());

        // Faded Holystone Bricks
        this.block(AetherIIBlocks.FADED_HOLYSTONE_BRICKS.get(), "construction/");
        this.stairs(AetherIIBlocks.FADED_HOLYSTONE_BRICK_STAIRS.get(), AetherIIBlocks.FADED_HOLYSTONE_BRICKS.get(), "construction/");
        this.slab(AetherIIBlocks.FADED_HOLYSTONE_BRICK_SLAB.get(), AetherIIBlocks.FADED_HOLYSTONE_BRICKS.get(), "construction/");
        this.wallBlock(AetherIIBlocks.FADED_HOLYSTONE_BRICK_WALL.get(), AetherIIBlocks.FADED_HOLYSTONE_BRICKS.get(), "construction/");

        // Undershale
        this.stairs(AetherIIBlocks.UNDERSHALE_STAIRS.get(), AetherIIBlocks.UNDERSHALE.get(), "natural/");
        this.slab(AetherIIBlocks.UNDERSHALE_SLAB.get(), AetherIIBlocks.UNDERSHALE.get(), "natural/");
        this.wallBlock(AetherIIBlocks.UNDERSHALE_WALL.get(), AetherIIBlocks.UNDERSHALE.get(), "natural/");

        // Agiosite
        this.stairs(AetherIIBlocks.AGIOSITE_STAIRS.get(), AetherIIBlocks.AGIOSITE.get(), "natural/");
        this.slab(AetherIIBlocks.AGIOSITE_SLAB.get(), AetherIIBlocks.AGIOSITE.get(), "natural/");
        this.wallBlock(AetherIIBlocks.AGIOSITE_WALL.get(), AetherIIBlocks.AGIOSITE.get(), "natural/");

        // Agiosite Bricks
        this.block(AetherIIBlocks.AGIOSITE_BRICKS.get(), "construction/");
        this.stairs(AetherIIBlocks.AGIOSITE_BRICK_STAIRS.get(), AetherIIBlocks.AGIOSITE_BRICKS.get(), "construction/");
        this.slab(AetherIIBlocks.AGIOSITE_BRICK_SLAB.get(), AetherIIBlocks.AGIOSITE_BRICKS.get(), "construction/");
        this.wallBlock(AetherIIBlocks.AGIOSITE_BRICK_WALL.get(), AetherIIBlocks.AGIOSITE_BRICKS.get(), "construction/");

        // Agiosite Decorative Blocks
        this.block(AetherIIBlocks.AGIOSITE_FLAGSTONES.get(), "decorative/");
        this.block(AetherIIBlocks.AGIOSITE_KEYSTONE.get(), "decorative/");
        this.decorativeBlock(AetherIIBlocks.AGIOSITE_BASE_BRICKS.get(), AetherIIBlocks.AGIOSITE_KEYSTONE.get());
        this.decorativeBlock(AetherIIBlocks.AGIOSITE_CAPSTONE_BRICKS.get(), AetherIIBlocks.AGIOSITE_KEYSTONE.get());
        this.decorativeBlock(AetherIIBlocks.AGIOSITE_BASE_PILLAR.get(), AetherIIBlocks.AGIOSITE_KEYSTONE.get());
        this.decorativeBlock(AetherIIBlocks.AGIOSITE_CAPSTONE_PILLAR.get(), AetherIIBlocks.AGIOSITE_KEYSTONE.get());
        this.decorativePillar(AetherIIBlocks.AGIOSITE_PILLAR.get(), AetherIIBlocks.AGIOSITE_KEYSTONE.get());

        // Icestone
        this.stairs(AetherIIBlocks.ICESTONE_STAIRS.get(), AetherIIBlocks.ICESTONE.get(), "natural/");
        this.slab(AetherIIBlocks.ICESTONE_SLAB.get(), AetherIIBlocks.ICESTONE.get(), "natural/");
        this.wallBlock(AetherIIBlocks.ICESTONE_WALL.get(), AetherIIBlocks.ICESTONE.get(), "natural/");

        // Glass
        this.translucentBlock(AetherIIBlocks.QUICKSOIL_GLASS.get(), "construction/");
        this.pane(AetherIIBlocks.QUICKSOIL_GLASS_PANE.get(), AetherIIBlocks.QUICKSOIL_GLASS.get(), "construction/");
        this.crudeScatterglassPane(AetherIIBlocks.CRUDE_SCATTERGLASS_PANE.get(), AetherIIBlocks.CRUDE_SCATTERGLASS.get(), "natural/");
        this.translucentBlock(AetherIIBlocks.SCATTERGLASS.get(), "natural/");
        this.pane(AetherIIBlocks.SCATTERGLASS_PANE.get(), AetherIIBlocks.SCATTERGLASS.get(), "natural/");

        // Wool
        this.block(AetherIIBlocks.CLOUDWOOL.get(), "construction/");
        this.block(AetherIIBlocks.WHITE_CLOUDWOOL.get(), "construction/");

        // Carpet
        this.carpet(AetherIIBlocks.CLOUDWOOL_CARPET.get(), AetherIIBlocks.CLOUDWOOL.get(),"construction/");
        this.carpet(AetherIIBlocks.WHITE_CLOUDWOOL_CARPET.get(), AetherIIBlocks.WHITE_CLOUDWOOL.get(),"construction/");

        // Mineral Blocks
        this.block(AetherIIBlocks.AMBROSIUM_BLOCK.get(), "construction/");
        this.block(AetherIIBlocks.ZANITE_BLOCK.get(), "construction/");
        this.block(AetherIIBlocks.ARKENIUM_BLOCK.get(), "construction/");
        this.block(AetherIIBlocks.GRAVITITE_BLOCK.get(), "construction/");

        // Utility
        this.torchBlock(AetherIIBlocks.AMBROSIUM_TORCH.get(), AetherIIBlocks.AMBROSIUM_WALL_TORCH.get());
        this.skyrootCraftingTable(AetherIIBlocks.SKYROOT_CRAFTING_TABLE.get(), AetherIIBlocks.SKYROOT_PLANKS.get(), "construction/");
        this.holystoneFurnace(AetherIIBlocks.HOLYSTONE_FURNACE.get());
        this.artisansBench(AetherIIBlocks.ARTISANS_BENCH.get());
        this.altar(AetherIIBlocks.ALTAR.get());
        this.skyrootChest(AetherIIBlocks.SKYROOT_CHEST.get());
        this.skyrootLadder(AetherIIBlocks.SKYROOT_LADDER.get());

        // Bookshelves
        this.bookshelf(AetherIIBlocks.SKYROOT_BOOKSHELF.get(), AetherIIBlocks.SKYROOT_PLANKS.get());
        this.bookshelf(AetherIIBlocks.HOLYSTONE_BOOKSHELF.get(), AetherIIBlocks.HOLYSTONE_BRICKS.get());
    }
}