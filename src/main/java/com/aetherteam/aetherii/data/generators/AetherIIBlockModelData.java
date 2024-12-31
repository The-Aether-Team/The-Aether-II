package com.aetherteam.aetherii.data.generators;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.data.providers.AetherIIBlockModelProvider;
import com.aetherteam.aetherii.data.resources.builders.models.AetherIIBlockFamilies;
import com.aetherteam.aetherii.data.resources.builders.models.AetherIITexturedModels;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TexturedModel;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;

public class AetherIIBlockModelData extends AetherIIBlockModelProvider {
    public AetherIIBlockModelData(PackOutput packOutput) {
        super(packOutput, AetherII.MODID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        AetherIIBlockFamilies.getAllFamilies().filter(BlockFamily::shouldGenerateModel).forEach((family) -> blockModels.family(family.getBaseBlock()).generateFor(family));

        // Portal
        this.createAetherPortalBlock(blockModels);

        // Surface
        this.createAetherGrassBlocks(blockModels);
        blockModels.createTrivialCube(AetherIIBlocks.AETHER_DIRT.get());
        blockModels.createTrivialCube(AetherIIBlocks.COARSE_AETHER_DIRT.get());
        this.createAetherFarmland(blockModels);
        blockModels.createTrivialCube(AetherIIBlocks.SHIMMERING_SILT.get());

        // Underground
        blockModels.createTrivialCube(AetherIIBlocks.UNSTABLE_HOLYSTONE.get());
        blockModels.createTrivialCube(AetherIIBlocks.UNSTABLE_UNDERSHALE.get());
        blockModels.createTrivialCube(AetherIIBlocks.ICHORITE.get());
        this.createSnowyCross(blockModels, AetherIIBlocks.SKY_ROOTS.get());
        this.createTranslucentCube(blockModels, AetherIIBlocks.GAS.get()); //todo translcucent interior
        this.createTranslucentCube(blockModels, AetherIIBlocks.ACID.get()); //todo ???? blockstate definition
        this.createPointedStone(blockModels, AetherIIBlocks.POINTED_HOLYSTONE.get());
        this.createPointedStone(blockModels, AetherIIBlocks.POINTED_ICHORITE.get());

        // Highfields
        blockModels.createTrivialCube(AetherIIBlocks.QUICKSOIL.get());
        blockModels.createFullAndCarpetBlocks(AetherIIBlocks.BRYALINN_MOSS_BLOCK.get(), AetherIIBlocks.BRYALINN_MOSS_CARPET.get());
        this.createVine(blockModels, AetherIIBlocks.BRYALINN_MOSS_VINES.get()); //todo
        this.createCustomFlowerBed(blockModels, AetherIIBlocks.BRYALINN_MOSS_FLOWERS.get(),
                AetherIITexturedModels.BRYALINN_MOSS_FLOWERS_1.create(AetherIIBlocks.BRYALINN_MOSS_FLOWERS.get(), blockModels.modelOutput),
                AetherIITexturedModels.BRYALINN_MOSS_FLOWERS_2.create(AetherIIBlocks.BRYALINN_MOSS_FLOWERS.get(), blockModels.modelOutput),
                AetherIITexturedModels.BRYALINN_MOSS_FLOWERS_3.create(AetherIIBlocks.BRYALINN_MOSS_FLOWERS.get(), blockModels.modelOutput),
                AetherIITexturedModels.BRYALINN_MOSS_FLOWERS_4.create(AetherIIBlocks.BRYALINN_MOSS_FLOWERS.get(), blockModels.modelOutput));
        this.createCutoutMippedCube(blockModels, AetherIIBlocks.TANGLED_BRANCHES.get());

        // Magnetic
        blockModels.createTrivialCube(AetherIIBlocks.FERROSITE_SAND.get());
        blockModels.createTrivialCube(AetherIIBlocks.FERROSITE_MUD.get());
        blockModels.createTrivialCube(AetherIIBlocks.FERROSITE.get());
        blockModels.createTrivialCube(AetherIIBlocks.RUSTED_FERROSITE.get());
        this.createCutoutCross(blockModels, AetherIIBlocks.MAGNETIC_SHROOM.get()); //todo flower pot

        // Arctic
        this.createArcticSnowBlocks(blockModels);
        this.createTranslucentCube(blockModels, AetherIIBlocks.ARCTIC_ICE.get());
        blockModels.createTrivialCube(AetherIIBlocks.ARCTIC_PACKED_ICE.get());
        this.createCrystal(blockModels, AetherIIBlocks.LARGE_ARCTIC_ICE_CRYSTAL.get());
        this.createCrystal(blockModels, AetherIIBlocks.MEDIUM_ARCTIC_ICE_CRYSTAL.get());
        this.createCrystal(blockModels, AetherIIBlocks.SMALL_ARCTIC_ICE_CRYSTAL.get());
        blockModels.createFullAndCarpetBlocks(AetherIIBlocks.SHAYELINN_MOSS_BLOCK.get(), AetherIIBlocks.SHAYELINN_MOSS_CARPET.get());
        this.createVine(blockModels, AetherIIBlocks.SHAYELINN_MOSS_VINES.get()); //todo
        this.createCustomFlowerBed(blockModels, AetherIIBlocks.HOLPUPEA.get(),
                AetherIITexturedModels.HOLPUPEA_1.create(AetherIIBlocks.HOLPUPEA.get(), blockModels.modelOutput),
                AetherIITexturedModels.HOLPUPEA_2.create(AetherIIBlocks.HOLPUPEA.get(), blockModels.modelOutput),
                AetherIITexturedModels.HOLPUPEA_3.create(AetherIIBlocks.HOLPUPEA.get(), blockModels.modelOutput),
                AetherIITexturedModels.HOLPUPEA_4.create(AetherIIBlocks.HOLPUPEA.get(), blockModels.modelOutput));

        // Irradiated
        blockModels.createTrivialCube(AetherIIBlocks.IRRADIATED_DUST_BLOCK.get());
        blockModels.createFullAndCarpetBlocks(AetherIIBlocks.AMBRELINN_MOSS_BLOCK.get(), AetherIIBlocks.AMBRELINN_MOSS_CARPET.get());
        this.createVine(blockModels, AetherIIBlocks.AMBRELINN_MOSS_VINES.get()); //todo
        this.createCustomFlowerBed(blockModels, AetherIIBlocks.TARAHESP_FLOWERS.get(),
                AetherIITexturedModels.TARAHESP_FLOWERS_1.create(AetherIIBlocks.TARAHESP_FLOWERS.get(), blockModels.modelOutput),
                AetherIITexturedModels.TARAHESP_FLOWERS_2.create(AetherIIBlocks.TARAHESP_FLOWERS.get(), blockModels.modelOutput),
                AetherIITexturedModels.TARAHESP_FLOWERS_3.create(AetherIIBlocks.TARAHESP_FLOWERS.get(), blockModels.modelOutput),
                AetherIITexturedModels.TARAHESP_FLOWERS_4.create(AetherIIBlocks.TARAHESP_FLOWERS.get(), blockModels.modelOutput));

        // Ores
        blockModels.createTrivialCube(AetherIIBlocks.HOLYSTONE_QUARTZ_ORE.get());
        blockModels.createTrivialCube(AetherIIBlocks.AMBROSIUM_ORE.get());
        blockModels.createTrivialCube(AetherIIBlocks.ZANITE_ORE.get());
        blockModels.createTrivialCube(AetherIIBlocks.GLINT_ORE.get());
        blockModels.createTrivialCube(AetherIIBlocks.ARKENIUM_ORE.get());
        blockModels.createTrivialCube(AetherIIBlocks.GRAVITITE_ORE.get());
        blockModels.createTrivialCube(AetherIIBlocks.UNDERSHALE_AMBROSIUM_ORE.get());
        blockModels.createTrivialCube(AetherIIBlocks.UNDERSHALE_ZANITE_ORE.get());
        blockModels.createTrivialCube(AetherIIBlocks.UNDERSHALE_GLINT_ORE.get());
        blockModels.createTrivialCube(AetherIIBlocks.UNDERSHALE_ARKENIUM_ORE.get());
        blockModels.createTrivialCube(AetherIIBlocks.UNDERSHALE_GRAVITITE_ORE.get());
        blockModels.createTrivialCube(AetherIIBlocks.CORROBONITE_ORE.get());
        this.createCrystal(blockModels, AetherIIBlocks.CORROBONITE_CLUSTER.get());

        // Aerclouds
        this.createTranslucentCube(blockModels, AetherIIBlocks.COLD_AERCLOUD.get()); //todo translucent interior
        this.createTranslucentCube(blockModels, AetherIIBlocks.BLUE_AERCLOUD.get());
        this.createTranslucentCube(blockModels, AetherIIBlocks.GOLDEN_AERCLOUD.get());
        this.createTranslucentCube(blockModels, AetherIIBlocks.GREEN_AERCLOUD.get());
        this.createTranslucentCube(blockModels, AetherIIBlocks.PURPLE_AERCLOUD.get()); //todo faces
        this.createTranslucentCube(blockModels, AetherIIBlocks.STORM_AERCLOUD.get());

        // Moa Nest
        blockModels.createTrivialCube(AetherIIBlocks.WOVEN_SKYROOT_STICKS.get());

        // Logs
        blockModels.woodProvider(AetherIIBlocks.SKYROOT_LOG.get()).logWithHorizontal(AetherIIBlocks.SKYROOT_LOG.get()).wood(AetherIIBlocks.SKYROOT_WOOD.get());
        blockModels.woodProvider(AetherIIBlocks.STRIPPED_SKYROOT_LOG.get()).logWithHorizontal(AetherIIBlocks.STRIPPED_SKYROOT_LOG.get()).wood(AetherIIBlocks.STRIPPED_SKYROOT_WOOD.get());
        blockModels.woodProvider(AetherIIBlocks.GREATROOT_LOG.get()).logWithHorizontal(AetherIIBlocks.GREATROOT_LOG.get()).wood(AetherIIBlocks.GREATROOT_WOOD.get());
        blockModels.woodProvider(AetherIIBlocks.STRIPPED_GREATROOT_LOG.get()).logWithHorizontal(AetherIIBlocks.STRIPPED_GREATROOT_LOG.get()).wood(AetherIIBlocks.STRIPPED_GREATROOT_WOOD.get());
        blockModels.woodProvider(AetherIIBlocks.WISPROOT_LOG.get()).logWithHorizontal(AetherIIBlocks.WISPROOT_LOG.get()).wood(AetherIIBlocks.WISPROOT_WOOD.get());
        blockModels.woodProvider(AetherIIBlocks.STRIPPED_WISPROOT_LOG.get()).logWithHorizontal(AetherIIBlocks.STRIPPED_WISPROOT_LOG.get()).wood(AetherIIBlocks.STRIPPED_WISPROOT_WOOD.get());
        this.createCustomColumn(blockModels, AetherIIBlocks.MOSSY_WISPROOT_LOG.get(), AetherIIBlocks.WISPROOT_LOG.get()); //todo
        this.createCustomColumn(blockModels, AetherIIBlocks.AMBEROOT_LOG.get(), AetherIIBlocks.SKYROOT_LOG.get()); //todo
        blockModels.createTrivialCube(AetherIIBlocks.AMBEROOT_WOOD.get()); //todo

        // Leaves
        this.createLeavesWithPiles(blockModels, AetherIIBlocks.SKYROOT_LEAVES.get(), AetherIIBlocks.SKYROOT_LEAF_PILE.get());
        this.createLeavesWithPiles(blockModels, AetherIIBlocks.SKYPLANE_LEAVES.get(), AetherIIBlocks.SKYPLANE_LEAF_PILE.get());
        this.createLeavesWithPiles(blockModels, AetherIIBlocks.SKYBIRCH_LEAVES.get(), AetherIIBlocks.SKYBIRCH_LEAF_PILE.get());
        this.createLeavesWithPiles(blockModels, AetherIIBlocks.SKYPINE_LEAVES.get(), AetherIIBlocks.SKYPINE_LEAF_PILE.get());
        this.createLeavesWithPiles(blockModels, AetherIIBlocks.WISPROOT_LEAVES.get(), AetherIIBlocks.WISPROOT_LEAF_PILE.get());
        this.createLeavesWithPiles(blockModels, AetherIIBlocks.WISPTOP_LEAVES.get(), AetherIIBlocks.WISPTOP_LEAF_PILE.get());
        this.createLeavesWithPiles(blockModels, AetherIIBlocks.GREATROOT_LEAVES.get(), AetherIIBlocks.GREATROOT_LEAF_PILE.get());
        this.createLeavesWithPiles(blockModels, AetherIIBlocks.GREATOAK_LEAVES.get(), AetherIIBlocks.GREATOAK_LEAF_PILE.get());
        this.createLeavesWithPiles(blockModels, AetherIIBlocks.GREATBOA_LEAVES.get(), AetherIIBlocks.GREATBOA_LEAF_PILE.get());
        this.createLeavesWithPiles(blockModels, AetherIIBlocks.AMBEROOT_LEAVES.get(), AetherIIBlocks.AMBEROOT_LEAF_PILE.get());
        this.createTintedLeavesWithPiles(blockModels, AetherIIBlocks.IRRADIATED_SKYROOT_LEAVES.get(), AetherIIBlocks.IRRADIATED_SKYROOT_LEAF_PILE.get());
        this.createTintedLeavesWithPiles(blockModels, AetherIIBlocks.IRRADIATED_SKYPLANE_LEAVES.get(), AetherIIBlocks.IRRADIATED_SKYPLANE_LEAF_PILE.get());
        this.createTintedLeavesWithPiles(blockModels, AetherIIBlocks.IRRADIATED_SKYBIRCH_LEAVES.get(), AetherIIBlocks.IRRADIATED_SKYBIRCH_LEAF_PILE.get());
        this.createTintedLeavesWithPiles(blockModels, AetherIIBlocks.IRRADIATED_SKYPINE_LEAVES.get(), AetherIIBlocks.IRRADIATED_SKYPINE_LEAF_PILE.get());
        this.createTintedLeavesWithPiles(blockModels, AetherIIBlocks.IRRADIATED_WISPROOT_LEAVES.get(), AetherIIBlocks.IRRADIATED_WISPROOT_LEAF_PILE.get());
        this.createTintedLeavesWithPiles(blockModels, AetherIIBlocks.IRRADIATED_WISPTOP_LEAVES.get(), AetherIIBlocks.IRRADIATED_WISPTOP_LEAF_PILE.get());
        this.createTintedLeavesWithPiles(blockModels, AetherIIBlocks.IRRADIATED_GREATROOT_LEAVES.get(), AetherIIBlocks.IRRADIATED_GREATROOT_LEAF_PILE.get());
        this.createTintedLeavesWithPiles(blockModels, AetherIIBlocks.IRRADIATED_GREATOAK_LEAVES.get(), AetherIIBlocks.IRRADIATED_GREATOAK_LEAF_PILE.get());
        this.createTintedLeavesWithPiles(blockModels, AetherIIBlocks.IRRADIATED_GREATBOA_LEAVES.get(), AetherIIBlocks.IRRADIATED_GREATBOA_LEAF_PILE.get());

        // Saplings
        blockModels.createPlantWithDefaultItem(AetherIIBlocks.SKYROOT_SAPLING.get(), AetherIIBlocks.POTTED_SKYROOT_SAPLING.get(), BlockModelGenerators.PlantType.NOT_TINTED);
        blockModels.createPlantWithDefaultItem(AetherIIBlocks.SKYPLANE_SAPLING.get(), AetherIIBlocks.POTTED_SKYPLANE_SAPLING.get(), BlockModelGenerators.PlantType.NOT_TINTED);
        blockModels.createPlantWithDefaultItem(AetherIIBlocks.SKYBIRCH_SAPLING.get(), AetherIIBlocks.POTTED_SKYBIRCH_SAPLING.get(), BlockModelGenerators.PlantType.NOT_TINTED);
        blockModels.createPlantWithDefaultItem(AetherIIBlocks.SKYPINE_SAPLING.get(), AetherIIBlocks.POTTED_SKYPINE_SAPLING.get(), BlockModelGenerators.PlantType.NOT_TINTED);
        blockModels.createPlantWithDefaultItem(AetherIIBlocks.WISPROOT_SAPLING.get(), AetherIIBlocks.POTTED_WISPROOT_SAPLING.get(), BlockModelGenerators.PlantType.NOT_TINTED);
        blockModels.createPlantWithDefaultItem(AetherIIBlocks.WISPTOP_SAPLING.get(), AetherIIBlocks.POTTED_WISPTOP_SAPLING.get(), BlockModelGenerators.PlantType.NOT_TINTED);
        blockModels.createPlantWithDefaultItem(AetherIIBlocks.GREATROOT_SAPLING.get(), AetherIIBlocks.POTTED_GREATROOT_SAPLING.get(), BlockModelGenerators.PlantType.NOT_TINTED);
        blockModels.createPlantWithDefaultItem(AetherIIBlocks.GREATOAK_SAPLING.get(), AetherIIBlocks.POTTED_GREATOAK_SAPLING.get(), BlockModelGenerators.PlantType.NOT_TINTED);
        blockModels.createPlantWithDefaultItem(AetherIIBlocks.GREATBOA_SAPLING.get(), AetherIIBlocks.POTTED_GREATBOA_SAPLING.get(), BlockModelGenerators.PlantType.NOT_TINTED);
        blockModels.createPlantWithDefaultItem(AetherIIBlocks.AMBEROOT_SAPLING.get(), AetherIIBlocks.POTTED_AMBEROOT_SAPLING.get(), BlockModelGenerators.PlantType.NOT_TINTED);

        // Grasses
        blockModels.createCrossBlock(AetherIIBlocks.AETHER_SHORT_GRASS.get(), BlockModelGenerators.PlantType.NOT_TINTED); //todo tinting and frosting
        blockModels.createCrossBlock(AetherIIBlocks.AETHER_MEDIUM_GRASS.get(), BlockModelGenerators.PlantType.NOT_TINTED);
        blockModels.createCrossBlock(AetherIIBlocks.AETHER_LONG_GRASS.get(), BlockModelGenerators.PlantType.NOT_TINTED);
//        blockModels.createCrossBlock(AetherIIBlocks.HIGHLAND_FERN.get(), BlockModelGenerators.PlantType.NOT_TINTED);
//        blockModels.createCrossBlock(AetherIIBlocks.SHIELD_FERN.get(), BlockModelGenerators.PlantType.NOT_TINTED);

        // Flowers //todo
        blockModels.createPlantWithDefaultItem(AetherIIBlocks.HIGHLAND_FERN.get(), AetherIIBlocks.POTTED_HIGHLAND_FERN.get(), BlockModelGenerators.PlantType.NOT_TINTED);
        blockModels.createPlantWithDefaultItem(AetherIIBlocks.SHIELD_FERN.get(), AetherIIBlocks.POTTED_SHIELD_FERN.get(), BlockModelGenerators.PlantType.NOT_TINTED);
        this.createSnowyPlantWithDefaultItem(blockModels, AetherIIBlocks.HESPEROSE.get(), AetherIIBlocks.POTTED_HESPEROSE.get());
        this.createSnowyPlantWithDefaultItem(blockModels, AetherIIBlocks.TARABLOOM.get(), AetherIIBlocks.POTTED_TARABLOOM.get());
        this.createSnowyPlantWithDefaultItem(blockModels, AetherIIBlocks.POASPROUT.get(), AetherIIBlocks.POTTED_POASPROUT.get());
        blockModels.createPlantWithDefaultItem(AetherIIBlocks.SATIVAL_SHOOT.get(), AetherIIBlocks.POTTED_SATIVAL_SHOOT.get(), BlockModelGenerators.PlantType.NOT_TINTED);
        blockModels.createPlantWithDefaultItem(AetherIIBlocks.LILICHIME.get(), AetherIIBlocks.POTTED_LILICHIME.get(), BlockModelGenerators.PlantType.NOT_TINTED);
        blockModels.createPlantWithDefaultItem(AetherIIBlocks.PLURACIAN.get(), AetherIIBlocks.POTTED_PLURACIAN.get(), BlockModelGenerators.PlantType.NOT_TINTED);
        blockModels.createPlantWithDefaultItem(AetherIIBlocks.BLADE_POA.get(), AetherIIBlocks.POTTED_BLADE_POA.get(), BlockModelGenerators.PlantType.NOT_TINTED);
        blockModels.createPlantWithDefaultItem(AetherIIBlocks.AECHOR_CUTTING.get(), AetherIIBlocks.POTTED_AECHOR_CUTTING.get(), BlockModelGenerators.PlantType.NOT_TINTED);

        // Bushes
//        blockModels.createTrivialCube(AetherIIBlocks.HIGHLANDS_BUSH.get()); //todo custom model
//        blockModels.createCrossBlock(AetherIIBlocks.BLUEBERRY_BUSH_STEM.get(), BlockModelGenerators.PlantType.NOT_TINTED);
//        blockModels.createTrivialCube(AetherIIBlocks.BLUEBERRY_BUSH.get());  //todo custom model //, AetherIIBlocks.BLUEBERRY_BUSH_STEM.get()

        // Potted Bushes
        blockModels.createPlantWithDefaultItem(AetherIIBlocks.HIGHLANDS_BUSH.get(), AetherIIBlocks.POTTED_HIGHLANDS_BUSH.get(), BlockModelGenerators.PlantType.NOT_TINTED);
        blockModels.createPlantWithDefaultItem(AetherIIBlocks.BLUEBERRY_BUSH_STEM.get(), AetherIIBlocks.POTTED_BLUEBERRY_BUSH_STEM.get(), BlockModelGenerators.PlantType.NOT_TINTED);
        blockModels.createPlantWithDefaultItem(AetherIIBlocks.BLUEBERRY_BUSH.get(), AetherIIBlocks.POTTED_BLUEBERRY_BUSH.get(), BlockModelGenerators.PlantType.NOT_TINTED);

        // Orange Tree
//        blockModels.createCrossBlock(AetherIIBlocks.ORANGE_TREE.get(), BlockModelGenerators.PlantType.NOT_TINTED); //todo custom model

        // Potted Orange Tree
        blockModels.createPlantWithDefaultItem(AetherIIBlocks.ORANGE_TREE.get(), AetherIIBlocks.POTTED_ORANGE_TREE.get(), BlockModelGenerators.PlantType.NOT_TINTED); //todo

        // Surface Vegetation
        this.createValkyrieSprout(blockModels);
        blockModels.createGrowingPlant(AetherIIBlocks.BRETTL_PLANT_TIP.get(), AetherIIBlocks.BRETTL_PLANT.get(), BlockModelGenerators.PlantType.NOT_TINTED);

        // Lake
        this.createCutoutCross(blockModels, AetherIIBlocks.ARILUM_SHOOT.get());
        this.createCutoutCross(blockModels, AetherIIBlocks.ARILUM.get());
        this.createCutoutCross(blockModels, AetherIIBlocks.ARILUM_PLANT.get());
        this.createCutoutCross(blockModels, AetherIIBlocks.BLOOMING_ARILUM.get());
        this.createCutoutCross(blockModels, AetherIIBlocks.BLOOMING_ARILUM_PLANT.get());

        // Ground Decoration
        blockModels.createTrivialCube(AetherIIBlocks.SKYROOT_TWIG.get());
        blockModels.createTrivialCube(AetherIIBlocks.HOLYSTONE_ROCK.get());
        //todo
        //        this.twig(AetherIIBlocks.SKYROOT_TWIG.get(), AetherIIBlocks.SKYROOT_LOG.get());
        //        this.rock(AetherIIBlocks.HOLYSTONE_ROCK.get(), AetherIIBlocks.HOLYSTONE.get());

        // Skyroot Decorative Blocks
        blockModels.createTrivialCube(AetherIIBlocks.SKYROOT_FLOORBOARDS.get());
        blockModels.createTrivialCube(AetherIIBlocks.SKYROOT_HIGHLIGHT.get());
        blockModels.createTrivialCube(AetherIIBlocks.SKYROOT_SHINGLES.get());
        blockModels.createTrivialCube(AetherIIBlocks.SKYROOT_SMALL_SHINGLES.get());
        this.createCustomColumn(blockModels, AetherIIBlocks.SKYROOT_BASE_PLANKS.get(), AetherIIBlocks.SKYROOT_HIGHLIGHT.get());
        this.createCustomColumn(blockModels, AetherIIBlocks.SKYROOT_TOP_PLANKS.get(), AetherIIBlocks.SKYROOT_HIGHLIGHT.get());
        this.createCustomColumn(blockModels, AetherIIBlocks.SKYROOT_BASE_BEAM.get(), AetherIIBlocks.SKYROOT_HIGHLIGHT.get()); //todo
        this.createCustomColumn(blockModels, AetherIIBlocks.SKYROOT_TOP_BEAM.get(), AetherIIBlocks.SKYROOT_HIGHLIGHT.get());
        this.createCustomColumn(blockModels, AetherIIBlocks.SKYROOT_BEAM.get(), AetherIIBlocks.SKYROOT_HIGHLIGHT.get());
        blockModels.createDoor(AetherIIBlocks.SECRET_SKYROOT_DOOR.get()); //todo
        blockModels.createTrapdoor(AetherIIBlocks.SECRET_SKYROOT_TRAPDOOR.get());

        // Greatroot Decorative Blocks
        blockModels.createTrivialCube(AetherIIBlocks.GREATROOT_FLOORBOARDS.get());
        blockModels.createTrivialCube(AetherIIBlocks.GREATROOT_HIGHLIGHT.get());
        blockModels.createTrivialCube(AetherIIBlocks.GREATROOT_SHINGLES.get());
        blockModels.createTrivialCube(AetherIIBlocks.GREATROOT_SMALL_SHINGLES.get());
        this.createCustomColumn(blockModels, AetherIIBlocks.GREATROOT_BASE_PLANKS.get(), AetherIIBlocks.GREATROOT_HIGHLIGHT.get());
        this.createCustomColumn(blockModels, AetherIIBlocks.GREATROOT_TOP_PLANKS.get(), AetherIIBlocks.GREATROOT_HIGHLIGHT.get());
        this.createCustomColumn(blockModels, AetherIIBlocks.GREATROOT_BASE_BEAM.get(), AetherIIBlocks.GREATROOT_HIGHLIGHT.get()); //todo
        this.createCustomColumn(blockModels, AetherIIBlocks.GREATROOT_TOP_BEAM.get(), AetherIIBlocks.GREATROOT_HIGHLIGHT.get());
        this.createCustomColumn(blockModels, AetherIIBlocks.GREATROOT_BEAM.get(), AetherIIBlocks.GREATROOT_HIGHLIGHT.get());
        blockModels.createDoor(AetherIIBlocks.SECRET_GREATROOT_DOOR.get()); //todo
        blockModels.createTrapdoor(AetherIIBlocks.SECRET_GREATROOT_TRAPDOOR.get());

        // Wisproot Decorative Blocks
        blockModels.createTrivialCube(AetherIIBlocks.WISPROOT_FLOORBOARDS.get());
        blockModels.createTrivialCube(AetherIIBlocks.WISPROOT_HIGHLIGHT.get());
        blockModels.createTrivialCube(AetherIIBlocks.WISPROOT_SHINGLES.get());
        blockModels.createTrivialCube(AetherIIBlocks.WISPROOT_SMALL_SHINGLES.get());
        this.createCustomColumn(blockModels, AetherIIBlocks.WISPROOT_BASE_PLANKS.get(), AetherIIBlocks.WISPROOT_HIGHLIGHT.get());
        this.createCustomColumn(blockModels, AetherIIBlocks.WISPROOT_TOP_PLANKS.get(), AetherIIBlocks.WISPROOT_HIGHLIGHT.get());
        this.createCustomColumn(blockModels, AetherIIBlocks.WISPROOT_BASE_BEAM.get(), AetherIIBlocks.WISPROOT_HIGHLIGHT.get()); //todo
        this.createCustomColumn(blockModels, AetherIIBlocks.WISPROOT_TOP_BEAM.get(), AetherIIBlocks.WISPROOT_HIGHLIGHT.get());
        this.createCustomColumn(blockModels, AetherIIBlocks.WISPROOT_BEAM.get(), AetherIIBlocks.WISPROOT_HIGHLIGHT.get());
        blockModels.createDoor(AetherIIBlocks.SECRET_WISPROOT_DOOR.get()); //todo
        blockModels.createTrapdoor(AetherIIBlocks.SECRET_WISPROOT_TRAPDOOR.get());

        // Holystone Decorative Blocks
        blockModels.createTrivialCube(AetherIIBlocks.HOLYSTONE_FLAGSTONES.get());
        blockModels.createTrivialCube(AetherIIBlocks.HOLYSTONE_HEADSTONE.get());
        blockModels.createTrivialCube(AetherIIBlocks.HOLYSTONE_KEYSTONE.get());
        this.createCustomColumn(blockModels, AetherIIBlocks.HOLYSTONE_BASE_BRICKS.get(), AetherIIBlocks.HOLYSTONE_KEYSTONE.get());
        this.createCustomColumn(blockModels, AetherIIBlocks.HOLYSTONE_CAPSTONE_BRICKS.get(), AetherIIBlocks.HOLYSTONE_KEYSTONE.get());
        this.createCustomColumn(blockModels, AetherIIBlocks.HOLYSTONE_BASE_PILLAR.get(), AetherIIBlocks.HOLYSTONE_KEYSTONE.get()); //todo
        this.createCustomColumn(blockModels, AetherIIBlocks.HOLYSTONE_CAPSTONE_PILLAR.get(), AetherIIBlocks.HOLYSTONE_KEYSTONE.get());
        this.createCustomColumn(blockModels, AetherIIBlocks.HOLYSTONE_PILLAR.get(), AetherIIBlocks.HOLYSTONE_KEYSTONE.get());

        // Faded Holystone Decorative Blocks
        blockModels.createTrivialCube(AetherIIBlocks.FADED_HOLYSTONE_FLAGSTONES.get());
        blockModels.createTrivialCube(AetherIIBlocks.FADED_HOLYSTONE_HEADSTONE.get());
        blockModels.createTrivialCube(AetherIIBlocks.FADED_HOLYSTONE_KEYSTONE.get());
        this.createCustomColumn(blockModels, AetherIIBlocks.FADED_HOLYSTONE_BASE_BRICKS.get(), AetherIIBlocks.FADED_HOLYSTONE_KEYSTONE.get());
        this.createCustomColumn(blockModels, AetherIIBlocks.FADED_HOLYSTONE_CAPSTONE_BRICKS.get(), AetherIIBlocks.FADED_HOLYSTONE_KEYSTONE.get());
        this.createCustomColumn(blockModels, AetherIIBlocks.FADED_HOLYSTONE_BASE_PILLAR.get(), AetherIIBlocks.FADED_HOLYSTONE_KEYSTONE.get()); //todo
        this.createCustomColumn(blockModels, AetherIIBlocks.FADED_HOLYSTONE_CAPSTONE_PILLAR.get(), AetherIIBlocks.FADED_HOLYSTONE_KEYSTONE.get());
        this.createCustomColumn(blockModels, AetherIIBlocks.FADED_HOLYSTONE_PILLAR.get(), AetherIIBlocks.FADED_HOLYSTONE_KEYSTONE.get());

        // Agiosite Decorative Blocks
        blockModels.createTrivialCube(AetherIIBlocks.AGIOSITE_FLAGSTONES.get());
        blockModels.createTrivialCube(AetherIIBlocks.AGIOSITE_KEYSTONE.get());
        this.createCustomColumn(blockModels, AetherIIBlocks.AGIOSITE_BASE_BRICKS.get(), AetherIIBlocks.AGIOSITE_KEYSTONE.get());
        this.createCustomColumn(blockModels, AetherIIBlocks.AGIOSITE_CAPSTONE_BRICKS.get(), AetherIIBlocks.AGIOSITE_KEYSTONE.get());
        this.createCustomColumn(blockModels, AetherIIBlocks.AGIOSITE_BASE_PILLAR.get(), AetherIIBlocks.AGIOSITE_KEYSTONE.get());
        this.createCustomColumn(blockModels, AetherIIBlocks.AGIOSITE_CAPSTONE_PILLAR.get(), AetherIIBlocks.AGIOSITE_KEYSTONE.get());
        this.createCustomColumn(blockModels, AetherIIBlocks.AGIOSITE_PILLAR.get(), AetherIIBlocks.AGIOSITE_KEYSTONE.get());

        // Icestone Decorative Blocks
        blockModels.createTrivialCube(AetherIIBlocks.ICESTONE_FLAGSTONES.get());
        blockModels.createTrivialCube(AetherIIBlocks.ICESTONE_KEYSTONE.get());
        this.createCustomColumn(blockModels, AetherIIBlocks.ICESTONE_BASE_BRICKS.get(), AetherIIBlocks.ICESTONE_KEYSTONE.get());
        this.createCustomColumn(blockModels, AetherIIBlocks.ICESTONE_CAPSTONE_BRICKS.get(), AetherIIBlocks.ICESTONE_KEYSTONE.get());
        this.createCustomColumn(blockModels, AetherIIBlocks.ICESTONE_BASE_PILLAR.get(), AetherIIBlocks.ICESTONE_KEYSTONE.get());
        this.createCustomColumn(blockModels, AetherIIBlocks.ICESTONE_CAPSTONE_PILLAR.get(), AetherIIBlocks.ICESTONE_KEYSTONE.get());
        this.createCustomColumn(blockModels, AetherIIBlocks.ICESTONE_PILLAR.get(), AetherIIBlocks.ICESTONE_KEYSTONE.get());

        // Glass
        this.createGlassBlocks(blockModels, AetherIIBlocks.QUICKSOIL_GLASS.get(), AetherIIBlocks.QUICKSOIL_GLASS_PANE.get());
        this.createGlassBlocks(blockModels, AetherIIBlocks.SKYROOT_FRAMED_QUICKSOIL_GLASS.get(), AetherIIBlocks.SKYROOT_FRAMED_QUICKSOIL_GLASS_PANE.get());
        this.createGlassBlocks(blockModels, AetherIIBlocks.ARKENIUM_FRAMED_QUICKSOIL_GLASS.get(), AetherIIBlocks.ARKENIUM_FRAMED_QUICKSOIL_GLASS_PANE.get());
        this.createGlassBlocks(blockModels, AetherIIBlocks.CRUDE_SCATTERGLASS.get(), AetherIIBlocks.CRUDE_SCATTERGLASS_PANE.get());
        this.createGlassBlocks(blockModels, AetherIIBlocks.SKYROOT_FRAMED_CRUDE_SCATTERGLASS.get(), AetherIIBlocks.SKYROOT_FRAMED_CRUDE_SCATTERGLASS_PANE.get());
        this.createGlassBlocks(blockModels, AetherIIBlocks.ARKENIUM_FRAMED_CRUDE_SCATTERGLASS.get(), AetherIIBlocks.ARKENIUM_FRAMED_CRUDE_SCATTERGLASS_PANE.get());
        this.createGlassBlocks(blockModels, AetherIIBlocks.SCATTERGLASS.get(), AetherIIBlocks.SCATTERGLASS_PANE.get());
        this.createGlassBlocks(blockModels, AetherIIBlocks.SKYROOT_FRAMED_SCATTERGLASS.get(), AetherIIBlocks.SKYROOT_FRAMED_SCATTERGLASS_PANE.get());
        this.createGlassBlocks(blockModels, AetherIIBlocks.ARKENIUM_FRAMED_SCATTERGLASS.get(), AetherIIBlocks.ARKENIUM_FRAMED_SCATTERGLASS_PANE.get());

        // Wool
        blockModels.createFullAndCarpetBlocks(AetherIIBlocks.CLOUDWOOL.get(), AetherIIBlocks.CLOUDWOOL_CARPET.get());
        blockModels.createFullAndCarpetBlocks(AetherIIBlocks.WHITE_CLOUDWOOL.get(), AetherIIBlocks.WHITE_CLOUDWOOL_CARPET.get());
        blockModels.createFullAndCarpetBlocks(AetherIIBlocks.ORANGE_CLOUDWOOL.get(), AetherIIBlocks.ORANGE_CLOUDWOOL_CARPET.get());
        blockModels.createFullAndCarpetBlocks(AetherIIBlocks.MAGENTA_CLOUDWOOL.get(), AetherIIBlocks.MAGENTA_CLOUDWOOL_CARPET.get());
        blockModels.createFullAndCarpetBlocks(AetherIIBlocks.LIGHT_BLUE_CLOUDWOOL.get(), AetherIIBlocks.LIGHT_BLUE_CLOUDWOOL_CARPET.get());
        blockModels.createFullAndCarpetBlocks(AetherIIBlocks.YELLOW_CLOUDWOOL.get(), AetherIIBlocks.YELLOW_CLOUDWOOL_CARPET.get());
        blockModels.createFullAndCarpetBlocks(AetherIIBlocks.LIME_CLOUDWOOL.get(), AetherIIBlocks.LIME_CLOUDWOOL_CARPET.get());
        blockModels.createFullAndCarpetBlocks(AetherIIBlocks.PINK_CLOUDWOOL.get(), AetherIIBlocks.PINK_CLOUDWOOL_CARPET.get());
        blockModels.createFullAndCarpetBlocks(AetherIIBlocks.GRAY_CLOUDWOOL.get(), AetherIIBlocks.GRAY_CLOUDWOOL_CARPET.get());
        blockModels.createFullAndCarpetBlocks(AetherIIBlocks.LIGHT_GRAY_CLOUDWOOL.get(), AetherIIBlocks.LIGHT_GRAY_CLOUDWOOL_CARPET.get());
        blockModels.createFullAndCarpetBlocks(AetherIIBlocks.CYAN_CLOUDWOOL.get(), AetherIIBlocks.CYAN_CLOUDWOOL_CARPET.get());
        blockModels.createFullAndCarpetBlocks(AetherIIBlocks.PURPLE_CLOUDWOOL.get(), AetherIIBlocks.PURPLE_CLOUDWOOL_CARPET.get());
        blockModels.createFullAndCarpetBlocks(AetherIIBlocks.BLUE_CLOUDWOOL.get(), AetherIIBlocks.BLUE_CLOUDWOOL_CARPET.get());
        blockModels.createFullAndCarpetBlocks(AetherIIBlocks.BROWN_CLOUDWOOL.get(), AetherIIBlocks.BROWN_CLOUDWOOL_CARPET.get());
        blockModels.createFullAndCarpetBlocks(AetherIIBlocks.GREEN_CLOUDWOOL.get(), AetherIIBlocks.GREEN_CLOUDWOOL_CARPET.get());
        blockModels.createFullAndCarpetBlocks(AetherIIBlocks.RED_CLOUDWOOL.get(), AetherIIBlocks.RED_CLOUDWOOL_CARPET.get());
        blockModels.createFullAndCarpetBlocks(AetherIIBlocks.BLACK_CLOUDWOOL.get(), AetherIIBlocks.BLACK_CLOUDWOOL_CARPET.get());

        // Arkenium Blocks
        blockModels.createDoor(AetherIIBlocks.ARKENIUM_DOOR.get());
        blockModels.createOrientableTrapdoor(AetherIIBlocks.ARKENIUM_TRAPDOOR.get());

        // Mineral Blocks
        blockModels.createTrivialCube(AetherIIBlocks.AMBROSIUM_BLOCK.get());
        blockModels.createTrivialCube(AetherIIBlocks.ZANITE_BLOCK.get());
        blockModels.createTrivialCube(AetherIIBlocks.ARKENIUM_BLOCK.get());
        blockModels.createTrivialCube(AetherIIBlocks.GRAVITITE_BLOCK.get());

        // Arilum Lantern
        blockModels.createTrivialCube(AetherIIBlocks.GREEN_ARILUM_LANTERN.get());
        blockModels.createTrivialCube(AetherIIBlocks.BLUE_ARILUM_LANTERN.get());
        blockModels.createTrivialCube(AetherIIBlocks.PURPLE_ARILUM_LANTERN.get());
        blockModels.createTrivialCube(AetherIIBlocks.GOLDEN_ARILUM_LANTERN.get());
        blockModels.createTrivialCube(AetherIIBlocks.WHITE_ARILUM_LANTERN.get());

        // Utility
        this.createAmbrosiumTorch(blockModels);
        blockModels.createCraftingTableLike(AetherIIBlocks.SKYROOT_CRAFTING_TABLE.get(), AetherIIBlocks.SKYROOT_PLANKS.get(), TextureMapping::craftingTable);
        blockModels.createFurnace(AetherIIBlocks.HOLYSTONE_FURNACE.get(), TexturedModel.ORIENTABLE_ONLY_TOP);
        this.createAltar(blockModels); //todo
        this.createArtisansBench(blockModels);
        this.createArkeniumForge(blockModels);
        blockModels.createChest(AetherIIBlocks.SKYROOT_CHEST.get(), AetherIIBlocks.SKYROOT_PLANKS.get(), ResourceLocation.withDefaultNamespace("skyroot"), true);
        blockModels.createNonTemplateHorizontalBlock(AetherIIBlocks.SKYROOT_LADDER.get());
        blockModels.createTrivialCube(AetherIIBlocks.SKYROOT_BED.get()); //todo

        blockModels.createHangingSign(AetherIIBlocks.STRIPPED_SKYROOT_LOG.get(), AetherIIBlocks.SKYROOT_HANGING_SIGN.get(), AetherIIBlocks.SKYROOT_WALL_HANGING_SIGN.get());
        blockModels.createHangingSign(AetherIIBlocks.STRIPPED_GREATROOT_LOG.get(), AetherIIBlocks.GREATROOT_HANGING_SIGN.get(), AetherIIBlocks.GREATROOT_WALL_HANGING_SIGN.get());
        blockModels.createHangingSign(AetherIIBlocks.STRIPPED_WISPROOT_LOG.get(), AetherIIBlocks.WISPROOT_HANGING_SIGN.get(), AetherIIBlocks.WISPROOT_WALL_HANGING_SIGN.get());

        // Moa Egg
        blockModels.createTrivialCube(AetherIIBlocks.MOA_EGG.get()); //todo

        // Bookshelves
        this.createCustomColumn(blockModels, AetherIIBlocks.SKYROOT_BOOKSHELF.get(), AetherIIBlocks.SKYROOT_PLANKS.get());
        this.createCustomColumn(blockModels, AetherIIBlocks.HOLYSTONE_BOOKSHELF.get(), AetherIIBlocks.HOLYSTONE_BRICKS.get());

        // Furniture
        blockModels.createTrivialCube(AetherIIBlocks.OUTPOST_CAMPFIRE.get()); //todo
    }
}