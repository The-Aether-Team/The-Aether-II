package com.aetherteam.aetherii.data.generators.models;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.client.renderer.item.model.VaseSpecialRenderer;
import com.aetherteam.aetherii.data.providers.AetherIIBlockModelSubProvider;
import com.aetherteam.aetherii.data.resources.builders.models.AetherIIBlockFamilies;
import com.aetherteam.aetherii.data.resources.builders.models.AetherIIModelTemplates;
import com.aetherteam.aetherii.data.resources.builders.models.AetherIITextureMappings;
import com.aetherteam.aetherii.data.resources.builders.models.AetherIITexturedModels;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelOutput;
import net.minecraft.client.data.models.blockstates.BlockModelDefinitionGenerator;
import net.minecraft.client.data.models.model.*;
import net.minecraft.data.BlockFamily;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Blocks;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class AetherIIBlockModels extends AetherIIBlockModelSubProvider {
    public AetherIIBlockModels(Consumer<BlockModelDefinitionGenerator> blockStateOutput, ItemModelOutput itemModelOutput, BiConsumer<Identifier, ModelInstance> modelOutput) {
        super(blockStateOutput, itemModelOutput, modelOutput);
    }

    @Override
    public void run() {
        AetherIIBlockFamilies.getAllFamilies().filter(BlockFamily::shouldGenerateModel).forEach((family) -> this.family(family.getBaseBlock()).generateFor(family));

        // Portal
        this.createAetherPortalBlock();

        // Surface
        this.createAetherGrassBlocks();
        this.createTrivialCube(AetherIIBlocks.AETHER_DIRT.get());
        this.createTrivialCube(AetherIIBlocks.COARSE_AETHER_DIRT.get());
        this.createTrivialCube(AetherIIBlocks.MYCELIAL_AETHER_DIRT.get());
        this.createAetherFarmland();
        this.createTrivialCube(AetherIIBlocks.SHIMMERING_SILT.get());

        // Underground
        this.createTrivialCube(AetherIIBlocks.UNSTABLE_HOLYSTONE.get());
        this.createTrivialCube(AetherIIBlocks.UNSTABLE_UNDERSHALE.get());
        this.createSnowyCross(AetherIIBlocks.SKY_ROOTS.get());
        this.createTranslucentCubeInnerFaces(AetherIIBlocks.HESTVEIL.get());
        this.createPointedStone(AetherIIBlocks.POINTED_HOLYSTONE.get());
        this.createPointedStone(AetherIIBlocks.POINTED_ICHORITE.get());

        // Highfields
        this.createTrivialCube(AetherIIBlocks.QUICKSOIL.get());
        this.createFullAndCarpetBlocks(AetherIIBlocks.BRYALINN_MOSS_BLOCK.get(), AetherIIBlocks.BRYALINN_MOSS_CARPET.get());
        this.createVine(AetherIIBlocks.BRYALINN_MOSS_VINES.get(), AetherIIModelTemplates.MOSS_VINE);
        this.createCustomFlowerBed(AetherIIBlocks.BRYALINN_MOSS_FLOWERS.get(),
                AetherIITexturedModels.BRYALINN_MOSS_FLOWERS_1.create(AetherIIBlocks.BRYALINN_MOSS_FLOWERS.get(), this.modelOutput),
                AetherIITexturedModels.BRYALINN_MOSS_FLOWERS_2.create(AetherIIBlocks.BRYALINN_MOSS_FLOWERS.get(), this.modelOutput),
                AetherIITexturedModels.BRYALINN_MOSS_FLOWERS_3.create(AetherIIBlocks.BRYALINN_MOSS_FLOWERS.get(), this.modelOutput),
                AetherIITexturedModels.BRYALINN_MOSS_FLOWERS_4.create(AetherIIBlocks.BRYALINN_MOSS_FLOWERS.get(), this.modelOutput));
        this.createCutoutMippedCube(AetherIIBlocks.TANGLED_BRANCHES.get());

        // Magnetic
        this.createTrivialCube(AetherIIBlocks.FERROSITE_SAND.get());
        this.createTrivialCube(AetherIIBlocks.FERROSITE_MUD.get());
        this.createTrivialCube(AetherIIBlocks.FERROSITE.get());
        this.createTrivialCube(AetherIIBlocks.RUSTED_FERROSITE.get());
        this.createMagneticShroom(AetherIIBlocks.MAGNETIC_SHROOM.get(), AetherIIBlocks.POTTED_MAGNETIC_SHROOM.get());
        this.createMagneticShroomBlock(AetherIIBlocks.MAGNETIC_SHROOM_BLOCK.get());
        this.createMagneticShroomBlockEmissive(AetherIIBlocks.SPOTTED_MAGNETIC_SHROOM_BLOCK.get());
        this.createMagneticShroomBlock(AetherIIBlocks.MAGNETIC_SHROOM_STEM.get());
        this.createMagneticShroomBlocksInside();

        // Arctic
        this.createArcticSnowBlocks();
        this.createTranslucentCube(AetherIIBlocks.ARCTIC_ICE.get());
        this.createTranslucentCube(AetherIIBlocks.FRAGILE_ARCTIC_ICE.get());
        this.createTrivialCube(AetherIIBlocks.ARCTIC_PACKED_ICE.get());
        this.createCrystal(AetherIIBlocks.LARGE_ARCTIC_ICE_CRYSTAL.get(), AetherIIModelTemplates.FULL_CRYSTAL);
        this.createCrystal(AetherIIBlocks.MEDIUM_ARCTIC_ICE_CRYSTAL.get(), AetherIIModelTemplates.FULL_CRYSTAL);
        this.createCrystal(AetherIIBlocks.SMALL_ARCTIC_ICE_CRYSTAL.get(), AetherIIModelTemplates.LARGE_CRYSTAL);
        this.createFullAndCarpetBlocks(AetherIIBlocks.SHAYELINN_MOSS_BLOCK.get(), AetherIIBlocks.SHAYELINN_MOSS_CARPET.get());
        this.createVine(AetherIIBlocks.SHAYELINN_MOSS_VINES.get(), AetherIIModelTemplates.MOSS_VINE);
        this.createCustomFlowerBed(AetherIIBlocks.HOLPUPEA.get(),
                AetherIITexturedModels.HOLPUPEA_1.create(AetherIIBlocks.HOLPUPEA.get(), this.modelOutput),
                AetherIITexturedModels.HOLPUPEA_2.create(AetherIIBlocks.HOLPUPEA.get(), this.modelOutput),
                AetherIITexturedModels.HOLPUPEA_3.create(AetherIIBlocks.HOLPUPEA.get(), this.modelOutput),
                AetherIITexturedModels.HOLPUPEA_4.create(AetherIIBlocks.HOLPUPEA.get(), this.modelOutput));

        // Irradiated
        this.createTrivialCube(AetherIIBlocks.IRRADIATED_DUST_BLOCK.get());
        this.createFullAndCarpetBlocks(AetherIIBlocks.AMBRELINN_MOSS_BLOCK.get(), AetherIIBlocks.AMBRELINN_MOSS_CARPET.get());
        this.createVine(AetherIIBlocks.AMBRELINN_MOSS_VINES.get(), AetherIIModelTemplates.AMBRELINN_MOSS_VINE);
        this.createCustomFlowerBed(AetherIIBlocks.TARAHESP_FLOWERS.get(),
                AetherIITexturedModels.TARAHESP_FLOWERS_1.create(AetherIIBlocks.TARAHESP_FLOWERS.get(), this.modelOutput),
                AetherIITexturedModels.TARAHESP_FLOWERS_2.create(AetherIIBlocks.TARAHESP_FLOWERS.get(), this.modelOutput),
                AetherIITexturedModels.TARAHESP_FLOWERS_3.create(AetherIIBlocks.TARAHESP_FLOWERS.get(), this.modelOutput),
                AetherIITexturedModels.TARAHESP_FLOWERS_4.create(AetherIIBlocks.TARAHESP_FLOWERS.get(), this.modelOutput));

        // Ores
        this.createTrivialCube(AetherIIBlocks.HOLYSTONE_QUARTZ_ORE.get());
        this.createTrivialCube(AetherIIBlocks.AMBROSIUM_ORE.get());
        this.createTrivialCube(AetherIIBlocks.ZANITE_ORE.get());
        this.createTrivialCube(AetherIIBlocks.ARKENIUM_ORE.get());
        this.createTrivialCube(AetherIIBlocks.GRAVITITE_ORE.get());
        this.createTrivialCube(AetherIIBlocks.GLINT_ORE.get());
        this.createTrivialCube(AetherIIBlocks.UNDERSHALE_AMBROSIUM_ORE.get());
        this.createTrivialCube(AetherIIBlocks.UNDERSHALE_ZANITE_ORE.get());
        this.createTrivialCube(AetherIIBlocks.UNDERSHALE_ARKENIUM_ORE.get());
        this.createTrivialCube(AetherIIBlocks.UNDERSHALE_GRAVITITE_ORE.get());
        this.createTrivialCube(AetherIIBlocks.UNDERSHALE_GLINT_ORE.get());
        this.createTrivialCube(AetherIIBlocks.CORROBONITE_ORE.get());
        this.createCorroboniteCluster(AetherIIBlocks.CORROBONITE_CLUSTER.get(), AetherIIModelTemplates.MEDIUM_CRYSTAL);

        // Aerclouds
        this.createAercloud(AetherIIBlocks.COLD_AERCLOUD.get());
        this.createAercloud(AetherIIBlocks.BLUE_AERCLOUD.get());
        this.createAercloud(AetherIIBlocks.GOLDEN_AERCLOUD.get());
        this.createAercloud(AetherIIBlocks.GREEN_AERCLOUD.get());
        this.createPurpleAercloud(AetherIIBlocks.PURPLE_AERCLOUD.get());
        this.createAercloud(AetherIIBlocks.STORM_AERCLOUD.get());

        // Nest Blocks
        this.createWovenSticks(AetherIIBlocks.WOVEN_SKYROOT_STICKS.get());
        this.createAnimalStash(AetherIIBlocks.ANIMAL_STASH.get(), AetherIIBlocks.WOVEN_SKYROOT_STICKS.get());
        this.createMoaEgg(AetherIIBlocks.MOA_EGG.get());

        // Logs
        this.woodProvider(AetherIIBlocks.SKYROOT_LOG.get()).logWithHorizontal(AetherIIBlocks.SKYROOT_LOG.get()).wood(AetherIIBlocks.SKYROOT_WOOD.get());
        this.woodProvider(AetherIIBlocks.STRIPPED_SKYROOT_LOG.get()).logWithHorizontal(AetherIIBlocks.STRIPPED_SKYROOT_LOG.get()).wood(AetherIIBlocks.STRIPPED_SKYROOT_WOOD.get());
        this.woodProvider(AetherIIBlocks.GREATROOT_LOG.get()).logWithHorizontal(AetherIIBlocks.GREATROOT_LOG.get()).wood(AetherIIBlocks.GREATROOT_WOOD.get());
        this.woodProvider(AetherIIBlocks.STRIPPED_GREATROOT_LOG.get()).logWithHorizontal(AetherIIBlocks.STRIPPED_GREATROOT_LOG.get()).wood(AetherIIBlocks.STRIPPED_GREATROOT_WOOD.get());
        this.woodProvider(AetherIIBlocks.WISPROOT_LOG.get()).logWithHorizontal(AetherIIBlocks.WISPROOT_LOG.get()).wood(AetherIIBlocks.WISPROOT_WOOD.get());
        this.woodProvider(AetherIIBlocks.STRIPPED_WISPROOT_LOG.get()).logWithHorizontal(AetherIIBlocks.STRIPPED_WISPROOT_LOG.get()).wood(AetherIIBlocks.STRIPPED_WISPROOT_WOOD.get());
        this.woodProvider(AetherIIBlocks.MOSSY_WISPROOT_LOG.get()).logWithHorizontal(AetherIIBlocks.MOSSY_WISPROOT_LOG.get()).wood(AetherIIBlocks.MOSSY_WISPROOT_WOOD.get());
        this.createFacingTopBottomColumnWithHorizontalVariant(AetherIIBlocks.MOSSY_WISPROOT_LOG_BASE.get(), AetherIIBlocks.WISPROOT_LOG.get(), AetherIIBlocks.MOSSY_WISPROOT_LOG.get());
        this.woodProvider(AetherIIBlocks.AMBEROOT_LOG.get()).logWithHorizontal(AetherIIBlocks.AMBEROOT_LOG.get()).wood(AetherIIBlocks.AMBEROOT_WOOD.get());
        this.woodProvider(AetherIIBlocks.STRIPPED_AMBEROOT_LOG.get()).logWithHorizontal(AetherIIBlocks.STRIPPED_AMBEROOT_LOG.get()).wood(AetherIIBlocks.STRIPPED_AMBEROOT_WOOD.get());
        this.woodProviderColumn(AetherIIBlocks.AMBEROOT_DEPOSIT.get(), AetherIIBlocks.AMBEROOT_LOG.get()).logWithHorizontal(AetherIIBlocks.AMBEROOT_DEPOSIT.get());

        // Trunks
        this.createTrunk(AetherIIBlocks.SKYROOT_TRUNK.get(), AetherIIBlocks.SKYROOT_LOG.get());
        this.createTrunk(AetherIIBlocks.STRIPPED_SKYROOT_TRUNK.get(), AetherIIBlocks.STRIPPED_SKYROOT_LOG.get());
        this.createTrunk(AetherIIBlocks.GREATROOT_TRUNK.get(), AetherIIBlocks.GREATROOT_LOG.get());
        this.createTrunk(AetherIIBlocks.STRIPPED_GREATROOT_TRUNK.get(), AetherIIBlocks.STRIPPED_GREATROOT_LOG.get());
        this.createTrunk(AetherIIBlocks.WISPROOT_TRUNK.get(), AetherIIBlocks.WISPROOT_LOG.get());
        this.createTrunk(AetherIIBlocks.MOSSY_WISPROOT_TRUNK.get(), AetherIIBlocks.MOSSY_WISPROOT_LOG.get());
        this.createTrunk(AetherIIBlocks.STRIPPED_WISPROOT_TRUNK.get(), AetherIIBlocks.STRIPPED_WISPROOT_LOG.get());
        this.createTrunk(AetherIIBlocks.AMBEROOT_TRUNK.get(), AetherIIBlocks.AMBEROOT_LOG.get());
        this.createTrunk(AetherIIBlocks.STRIPPED_AMBEROOT_TRUNK.get(), AetherIIBlocks.STRIPPED_AMBEROOT_LOG.get());

        // Leaves
        this.createLeavesWithPiles(AetherIIBlocks.SKYROOT_LEAVES.get(), AetherIIBlocks.SKYROOT_LEAF_PILE.get(), AetherIITexturedModels.LEAVES, AetherIIModelTemplates.OVERLAID_LEAVES);
        this.createLeavesWithPiles(AetherIIBlocks.SKYPLANE_LEAVES.get(), AetherIIBlocks.SKYPLANE_LEAF_PILE.get(), AetherIITexturedModels.LEAVES, AetherIIModelTemplates.OVERLAID_LEAVES);
        this.createLeavesWithPiles(AetherIIBlocks.SKYBIRCH_LEAVES.get(), AetherIIBlocks.SKYBIRCH_LEAF_PILE.get(), AetherIITexturedModels.LEAVES, AetherIIModelTemplates.OVERLAID_LEAVES);
        this.createLeavesWithPiles(AetherIIBlocks.SKYPINE_LEAVES.get(), AetherIIBlocks.SKYPINE_LEAF_PILE.get(), AetherIITexturedModels.LEAVES, AetherIIModelTemplates.OVERLAID_LEAVES);
        this.createLeavesWithPiles(AetherIIBlocks.WISPROOT_LEAVES.get(), AetherIIBlocks.WISPROOT_LEAF_PILE.get(), AetherIITexturedModels.LEAVES, AetherIIModelTemplates.OVERLAID_LEAVES);
        this.createLeavesWithPiles(AetherIIBlocks.WISPTOP_LEAVES.get(), AetherIIBlocks.WISPTOP_LEAF_PILE.get(), AetherIITexturedModels.LEAVES, AetherIIModelTemplates.OVERLAID_LEAVES);
        this.createLeavesWithPiles(AetherIIBlocks.GREATROOT_LEAVES.get(), AetherIIBlocks.GREATROOT_LEAF_PILE.get(), AetherIITexturedModels.LEAVES, AetherIIModelTemplates.OVERLAID_LEAVES);
        this.createLeavesWithPiles(AetherIIBlocks.GREATOAK_LEAVES.get(), AetherIIBlocks.GREATOAK_LEAF_PILE.get(), AetherIITexturedModels.LEAVES, AetherIIModelTemplates.OVERLAID_LEAVES);
        this.createLeavesWithPiles(AetherIIBlocks.GREATBOA_LEAVES.get(), AetherIIBlocks.GREATBOA_LEAF_PILE.get(), AetherIITexturedModels.LEAVES, AetherIIModelTemplates.OVERLAID_LEAVES);
        this.createLeavesWithPiles(AetherIIBlocks.AMBEROOT_LEAVES.get(), AetherIIBlocks.AMBEROOT_LEAF_PILE.get(), AetherIITexturedModels.LEAVES, AetherIIModelTemplates.OVERLAID_LEAVES);
        this.createLeavesWithPiles(AetherIIBlocks.IRRADIATED_SKYROOT_LEAVES.get(), AetherIIBlocks.IRRADIATED_SKYROOT_LEAF_PILE.get(), AetherIITexturedModels.TINTED_LEAVES, AetherIIModelTemplates.TINTED_OVERLAID_LEAVES);
        this.createLeavesWithPiles(AetherIIBlocks.IRRADIATED_SKYPLANE_LEAVES.get(), AetherIIBlocks.IRRADIATED_SKYPLANE_LEAF_PILE.get(), AetherIITexturedModels.TINTED_LEAVES, AetherIIModelTemplates.TINTED_OVERLAID_LEAVES);
        this.createLeavesWithPiles(AetherIIBlocks.IRRADIATED_SKYBIRCH_LEAVES.get(), AetherIIBlocks.IRRADIATED_SKYBIRCH_LEAF_PILE.get(), AetherIITexturedModels.TINTED_LEAVES, AetherIIModelTemplates.TINTED_OVERLAID_LEAVES);
        this.createLeavesWithPiles(AetherIIBlocks.IRRADIATED_SKYPINE_LEAVES.get(), AetherIIBlocks.IRRADIATED_SKYPINE_LEAF_PILE.get(), AetherIITexturedModels.TINTED_LEAVES, AetherIIModelTemplates.TINTED_OVERLAID_LEAVES);
        this.createLeavesWithPiles(AetherIIBlocks.IRRADIATED_WISPROOT_LEAVES.get(), AetherIIBlocks.IRRADIATED_WISPROOT_LEAF_PILE.get(), AetherIITexturedModels.TINTED_LEAVES, AetherIIModelTemplates.TINTED_OVERLAID_LEAVES);
        this.createLeavesWithPiles(AetherIIBlocks.IRRADIATED_WISPTOP_LEAVES.get(), AetherIIBlocks.IRRADIATED_WISPTOP_LEAF_PILE.get(), AetherIITexturedModels.TINTED_LEAVES, AetherIIModelTemplates.TINTED_OVERLAID_LEAVES);
        this.createLeavesWithPiles(AetherIIBlocks.IRRADIATED_GREATROOT_LEAVES.get(), AetherIIBlocks.IRRADIATED_GREATROOT_LEAF_PILE.get(), AetherIITexturedModels.TINTED_LEAVES, AetherIIModelTemplates.TINTED_OVERLAID_LEAVES);
        this.createLeavesWithPiles(AetherIIBlocks.IRRADIATED_GREATOAK_LEAVES.get(), AetherIIBlocks.IRRADIATED_GREATOAK_LEAF_PILE.get(), AetherIITexturedModels.TINTED_LEAVES, AetherIIModelTemplates.TINTED_OVERLAID_LEAVES);
        this.createLeavesWithPiles(AetherIIBlocks.IRRADIATED_GREATBOA_LEAVES.get(), AetherIIBlocks.IRRADIATED_GREATBOA_LEAF_PILE.get(), AetherIITexturedModels.TINTED_LEAVES, AetherIIModelTemplates.TINTED_OVERLAID_LEAVES);

        // Saplings
        this.createPlantWithDefaultItem(AetherIIBlocks.SKYROOT_SAPLING.get(), AetherIIBlocks.POTTED_SKYROOT_SAPLING.get(), PlantType.NOT_TINTED);
        this.createPlantWithDefaultItem(AetherIIBlocks.SKYPLANE_SAPLING.get(), AetherIIBlocks.POTTED_SKYPLANE_SAPLING.get(), PlantType.NOT_TINTED);
        this.createPlantWithDefaultItem(AetherIIBlocks.SKYBIRCH_SAPLING.get(), AetherIIBlocks.POTTED_SKYBIRCH_SAPLING.get(), PlantType.NOT_TINTED);
        this.createPlantWithDefaultItem(AetherIIBlocks.SKYPINE_SAPLING.get(), AetherIIBlocks.POTTED_SKYPINE_SAPLING.get(), PlantType.NOT_TINTED);
        this.createPlantWithDefaultItem(AetherIIBlocks.WISPROOT_SAPLING.get(), AetherIIBlocks.POTTED_WISPROOT_SAPLING.get(), PlantType.NOT_TINTED);
        this.createPlantWithDefaultItem(AetherIIBlocks.WISPTOP_SAPLING.get(), AetherIIBlocks.POTTED_WISPTOP_SAPLING.get(), PlantType.NOT_TINTED);
        this.createPlantWithDefaultItem(AetherIIBlocks.GREATROOT_SAPLING.get(), AetherIIBlocks.POTTED_GREATROOT_SAPLING.get(), PlantType.NOT_TINTED);
        this.createPlantWithDefaultItem(AetherIIBlocks.GREATOAK_SAPLING.get(), AetherIIBlocks.POTTED_GREATOAK_SAPLING.get(), PlantType.NOT_TINTED);
        this.createPlantWithDefaultItem(AetherIIBlocks.GREATBOA_SAPLING.get(), AetherIIBlocks.POTTED_GREATBOA_SAPLING.get(), PlantType.NOT_TINTED);
        this.createPlantWithDefaultItem(AetherIIBlocks.AMBEROOT_SAPLING.get(), AetherIIBlocks.POTTED_AMBEROOT_SAPLING.get(), PlantType.NOT_TINTED);

        // Grasses
        this.createTintedTallGrass(AetherIIBlocks.SHORT_AETHER_GRASS.get());
        this.createTintedTallGrass(AetherIIBlocks.MEDIUM_AETHER_GRASS.get());
        this.createTintedTallGrass(AetherIIBlocks.TALL_AETHER_GRASS.get());

        // Flowers
        this.createAetherFern();
        this.createPlantWithDefaultItem(AetherIIBlocks.SHIELD_FERN.get(), AetherIIBlocks.POTTED_SHIELD_FERN.get(), PlantType.NOT_TINTED);
        this.createSnowyPlantWithDefaultItem(AetherIIBlocks.HESPEROSE.get(), AetherIIBlocks.POTTED_HESPEROSE.get());
        this.createSnowyPlantWithDefaultItem(AetherIIBlocks.TARABLOOM.get(), AetherIIBlocks.POTTED_TARABLOOM.get());
        this.createSnowyPlantWithDefaultItem(AetherIIBlocks.POASPROUT.get(), AetherIIBlocks.POTTED_POASPROUT.get());
        this.createAsymmetricalPlantWithDefaultItem(AetherIIBlocks.SATIVAL_SHOOT.get(), AetherIITexturedModels.ASYMMETRICAL_CROSS_EVEN, AetherIITexturedModels.ASYMMETRICAL_CROSS_EVEN_MIRRORED,
                AetherIIBlocks.POTTED_SATIVAL_SHOOT.get(), AetherIIModelTemplates.POTTED_ASYMMETRICAL_CROSS_EVEN);
        this.createUniquePlantWithDefaultItem(AetherIIBlocks.LILICHIME.get(), AetherIITexturedModels.LILICHIME, AetherIIBlocks.POTTED_LILICHIME.get(), AetherIIModelTemplates.POTTED_LILICHIME, AetherIITextureMappings::pottedLilichime);
        this.createFacingPlantWithDefaultItem(AetherIIBlocks.PLURACIAN.get(), AetherIITexturedModels.PLURACIAN, AetherIIBlocks.POTTED_PLURACIAN.get(), AetherIIModelTemplates.POTTED_PLURACIAN, AetherIITextureMappings::pluracian);
        this.createAsymmetricalPlantWithDefaultItem(AetherIIBlocks.BLADE_POA.get(), AetherIITexturedModels.ASYMMETRICAL_CROSS_ODD, AetherIITexturedModels.ASYMMETRICAL_CROSS_ODD_MIRRORED,
                AetherIIBlocks.POTTED_BLADE_POA.get(), AetherIIModelTemplates.POTTED_ASYMMETRICAL_CROSS_ODD);
        this.createPlantWithDefaultItem(AetherIIBlocks.AECHOR_CUTTING.get(), AetherIIBlocks.POTTED_AECHOR_CUTTING.get(), PlantType.NOT_TINTED);
        this.createPlantWithDefaultItem(AetherIIBlocks.CARRION_CUTTING.get(), AetherIIBlocks.POTTED_CARRION_CUTTING.get(), PlantType.NOT_TINTED);

        // Bushes
        this.createBush(AetherIIBlocks.AETHER_BUSH.get(), AetherIIBlocks.POTTED_AETHER_BUSH.get());
        this.createPlantWithDefaultItem(AetherIIBlocks.BLUEBERRY_BUSH_STEM.get(), AetherIIBlocks.POTTED_BLUEBERRY_BUSH_STEM.get(), PlantType.NOT_TINTED);
        this.createBush(AetherIIBlocks.BLUEBERRY_BUSH.get(), AetherIIBlocks.POTTED_BLUEBERRY_BUSH.get());

        // Orange Tree
        this.createOrangeTree(AetherIIBlocks.ORANGE_TREE.get(), AetherIIBlocks.POTTED_ORANGE_TREE.get());

        // Surface Vegetation
        this.createValkyrieSprout();
        this.createBrettlPlant(AetherIIBlocks.BRETTL_PLANT.get());
        this.createBrettlPlant(AetherIIBlocks.BRETTL_PLANT_TIP.get());
        this.createCrossWithDefaultItem(AetherIIBlocks.BRETTL_FLOWER.get(), PlantType.NOT_TINTED);

        // Lake
        this.createCrossBlock(AetherIIBlocks.ARILUM_SHOOT.get(), PlantType.NOT_TINTED);
        this.createCrossWithDefaultItem(AetherIIBlocks.ARILUM.get(), PlantType.NOT_TINTED);
        this.createCrossWithDefaultItem(AetherIIBlocks.ARILUM_PLANT.get(), PlantType.NOT_TINTED);
        this.createCrossWithDefaultItem(AetherIIBlocks.BLOOMING_ARILUM.get(), PlantType.NOT_TINTED);
        this.createCrossWithDefaultItem(AetherIIBlocks.BLOOMING_ARILUM_PLANT.get(), PlantType.NOT_TINTED);

        // Expanse
        this.createCrossWithDefaultItem(AetherIIBlocks.CUMULUS_BUSH.get(), PlantType.NOT_TINTED);
        this.createDoublePlant(AetherIIBlocks.CUMULONIMBUS_BUSH.get(), PlantType.NOT_TINTED);
        this.registerSimpleFlatItemModel(AetherIIBlocks.CUMULONIMBUS_BUSH.asItem());
        this.createRoyalStratusFern(AetherIIBlocks.ROYAL_STRATUS_FERN.get());

        // Ground Decoration
        this.createTwig(AetherIIBlocks.SKYROOT_TWIG.get(), AetherIIBlocks.SKYROOT_LOG.get());
        this.createRock(AetherIIBlocks.HOLYSTONE_ROCK.get(), AetherIIBlocks.HOLYSTONE.get());

        // Skyroot Decorative Blocks
        this.createTrivialCube(AetherIIBlocks.SKYROOT_FLOORBOARDS.get());
        this.createTrivialCube(AetherIIBlocks.SKYROOT_HIGHLIGHT.get());
        this.createHorizontallyRotatedBlock(AetherIIBlocks.SKYROOT_SHINGLES.get(), TexturedModel.CUBE);
        this.createHorizontallyRotatedBlock(AetherIIBlocks.SKYROOT_SMALL_SHINGLES.get(), TexturedModel.CUBE);
        this.createCubeColumn(AetherIIBlocks.SKYROOT_BASE_PLANKS.get(), AetherIIBlocks.SKYROOT_HIGHLIGHT.get());
        this.createCubeColumn(AetherIIBlocks.SKYROOT_TOP_PLANKS.get(), AetherIIBlocks.SKYROOT_HIGHLIGHT.get());
        this.createFacingColumnWithHorizontalVariant(AetherIIBlocks.SKYROOT_BASE_BEAM.get(), AetherIIBlocks.SKYROOT_HIGHLIGHT.get());
        this.createFacingColumnWithHorizontalVariant(AetherIIBlocks.SKYROOT_TOP_BEAM.get(), AetherIIBlocks.SKYROOT_HIGHLIGHT.get());
        this.createFacingColumnWithHorizontalVariant(AetherIIBlocks.SKYROOT_BEAM.get(), AetherIIBlocks.SKYROOT_HIGHLIGHT.get());
        this.createSecretDoor(AetherIIBlocks.SECRET_SKYROOT_DOOR.get(), AetherIIBlocks.SKYROOT_PLANKS.get());
        this.createOrientableSecretTrapdoor(AetherIIBlocks.SECRET_SKYROOT_TRAPDOOR.get(), AetherIIBlocks.SKYROOT_PLANKS.get());

        // Greatroot Decorative Blocks
        this.createTrivialCube(AetherIIBlocks.GREATROOT_FLOORBOARDS.get());
        this.createTrivialCube(AetherIIBlocks.GREATROOT_HIGHLIGHT.get());
        this.createHorizontallyRotatedBlock(AetherIIBlocks.GREATROOT_SHINGLES.get(), TexturedModel.CUBE);
        this.createHorizontallyRotatedBlock(AetherIIBlocks.GREATROOT_SMALL_SHINGLES.get(), TexturedModel.CUBE);
        this.createCubeColumn(AetherIIBlocks.GREATROOT_BASE_PLANKS.get(), AetherIIBlocks.GREATROOT_HIGHLIGHT.get());
        this.createCubeColumn(AetherIIBlocks.GREATROOT_TOP_PLANKS.get(), AetherIIBlocks.GREATROOT_HIGHLIGHT.get());
        this.createFacingColumnWithHorizontalVariant(AetherIIBlocks.GREATROOT_BASE_BEAM.get(), AetherIIBlocks.GREATROOT_HIGHLIGHT.get());
        this.createFacingColumnWithHorizontalVariant(AetherIIBlocks.GREATROOT_TOP_BEAM.get(), AetherIIBlocks.GREATROOT_HIGHLIGHT.get());
        this.createFacingColumnWithHorizontalVariant(AetherIIBlocks.GREATROOT_BEAM.get(), AetherIIBlocks.GREATROOT_HIGHLIGHT.get());
        this.createSecretDoor(AetherIIBlocks.SECRET_GREATROOT_DOOR.get(), AetherIIBlocks.GREATROOT_PLANKS.get());
        this.createOrientableSecretTrapdoor(AetherIIBlocks.SECRET_GREATROOT_TRAPDOOR.get(), AetherIIBlocks.GREATROOT_PLANKS.get());

        // Wisproot Decorative Blocks
        this.createTrivialCube(AetherIIBlocks.WISPROOT_FLOORBOARDS.get());
        this.createTrivialCube(AetherIIBlocks.WISPROOT_HIGHLIGHT.get());
        this.createHorizontallyRotatedBlock(AetherIIBlocks.WISPROOT_SHINGLES.get(), TexturedModel.CUBE);
        this.createHorizontallyRotatedBlock(AetherIIBlocks.WISPROOT_SMALL_SHINGLES.get(), TexturedModel.CUBE);
        this.createCubeColumn(AetherIIBlocks.WISPROOT_BASE_PLANKS.get(), AetherIIBlocks.WISPROOT_HIGHLIGHT.get());
        this.createCubeColumn(AetherIIBlocks.WISPROOT_TOP_PLANKS.get(), AetherIIBlocks.WISPROOT_HIGHLIGHT.get());
        this.createFacingColumnWithHorizontalVariant(AetherIIBlocks.WISPROOT_BASE_BEAM.get(), AetherIIBlocks.WISPROOT_HIGHLIGHT.get());
        this.createFacingColumnWithHorizontalVariant(AetherIIBlocks.WISPROOT_TOP_BEAM.get(), AetherIIBlocks.WISPROOT_HIGHLIGHT.get());
        this.createFacingColumnWithHorizontalVariant(AetherIIBlocks.WISPROOT_BEAM.get(), AetherIIBlocks.WISPROOT_HIGHLIGHT.get());
        this.createSecretDoor(AetherIIBlocks.SECRET_WISPROOT_DOOR.get(), AetherIIBlocks.WISPROOT_PLANKS.get());
        this.createOrientableSecretTrapdoor(AetherIIBlocks.SECRET_WISPROOT_TRAPDOOR.get(), AetherIIBlocks.WISPROOT_PLANKS.get());

        // Amberoot Decorative Blocks
        this.createTrivialCube(AetherIIBlocks.AMBEROOT_FLOORBOARDS.get());
        this.createTrivialCube(AetherIIBlocks.AMBEROOT_HIGHLIGHT.get());
        this.createHorizontallyRotatedBlock(AetherIIBlocks.AMBEROOT_SHINGLES.get(), TexturedModel.CUBE);
        this.createHorizontallyRotatedBlock(AetherIIBlocks.AMBEROOT_SMALL_SHINGLES.get(), TexturedModel.CUBE);
        this.createCubeColumn(AetherIIBlocks.AMBEROOT_BASE_PLANKS.get(), AetherIIBlocks.AMBEROOT_HIGHLIGHT.get());
        this.createCubeColumn(AetherIIBlocks.AMBEROOT_TOP_PLANKS.get(), AetherIIBlocks.AMBEROOT_HIGHLIGHT.get());
        this.createFacingColumnWithHorizontalVariant(AetherIIBlocks.AMBEROOT_BASE_BEAM.get(), AetherIIBlocks.AMBEROOT_HIGHLIGHT.get());
        this.createFacingColumnWithHorizontalVariant(AetherIIBlocks.AMBEROOT_TOP_BEAM.get(), AetherIIBlocks.AMBEROOT_HIGHLIGHT.get());
        this.createFacingColumnWithHorizontalVariant(AetherIIBlocks.AMBEROOT_BEAM.get(), AetherIIBlocks.AMBEROOT_HIGHLIGHT.get());
        this.createSecretDoor(AetherIIBlocks.SECRET_AMBEROOT_DOOR.get(), AetherIIBlocks.AMBEROOT_PLANKS.get());
        this.createOrientableSecretTrapdoor(AetherIIBlocks.SECRET_AMBEROOT_TRAPDOOR.get(), AetherIIBlocks.AMBEROOT_PLANKS.get());

        // Holystone Decorative Blocks
        this.createTrivialCube(AetherIIBlocks.HOLYSTONE_FLAGSTONES.get());
        this.createTrivialCube(AetherIIBlocks.HOLYSTONE_HEADSTONE.get());
        this.createTrivialCube(AetherIIBlocks.HOLYSTONE_KEYSTONE.get());
        this.createCubeColumn(AetherIIBlocks.HOLYSTONE_BASE_BRICKS.get(), AetherIIBlocks.HOLYSTONE_KEYSTONE.get());
        this.createCubeColumn(AetherIIBlocks.HOLYSTONE_CAPSTONE_BRICKS.get(), AetherIIBlocks.HOLYSTONE_KEYSTONE.get());
        this.createFacingColumnWithHorizontalVariant(AetherIIBlocks.HOLYSTONE_BASE_PILLAR.get(), AetherIIBlocks.HOLYSTONE_KEYSTONE.get());
        this.createFacingColumnWithHorizontalVariant(AetherIIBlocks.HOLYSTONE_CAPSTONE_PILLAR.get(), AetherIIBlocks.HOLYSTONE_KEYSTONE.get());
        this.createFacingColumnWithHorizontalVariant(AetherIIBlocks.HOLYSTONE_PILLAR.get(), AetherIIBlocks.HOLYSTONE_KEYSTONE.get());

        // Faded Holystone Decorative Blocks
        this.createTrivialCube(AetherIIBlocks.FADED_HOLYSTONE_FLAGSTONES.get());
        this.createTrivialCube(AetherIIBlocks.FADED_HOLYSTONE_HEADSTONE.get());
        this.createTrivialCube(AetherIIBlocks.FADED_HOLYSTONE_KEYSTONE.get());
        this.createCubeColumn(AetherIIBlocks.FADED_HOLYSTONE_BASE_BRICKS.get(), AetherIIBlocks.FADED_HOLYSTONE_KEYSTONE.get());
        this.createCubeColumn(AetherIIBlocks.FADED_HOLYSTONE_CAPSTONE_BRICKS.get(), AetherIIBlocks.FADED_HOLYSTONE_KEYSTONE.get());
        this.createFacingColumnWithHorizontalVariant(AetherIIBlocks.FADED_HOLYSTONE_BASE_PILLAR.get(), AetherIIBlocks.FADED_HOLYSTONE_KEYSTONE.get());
        this.createFacingColumnWithHorizontalVariant(AetherIIBlocks.FADED_HOLYSTONE_CAPSTONE_PILLAR.get(), AetherIIBlocks.FADED_HOLYSTONE_KEYSTONE.get());
        this.createFacingColumnWithHorizontalVariant(AetherIIBlocks.FADED_HOLYSTONE_PILLAR.get(), AetherIIBlocks.FADED_HOLYSTONE_KEYSTONE.get());

        // Undershale Bricks
        this.pressurePlate(AetherIIBlocks.UNDERSHALE_BRICK_PRESSURE_PLATE.get());
        this.button(AetherIIBlocks.UNDERSHALE_BRICK_BUTTON.get());

        // Undershale Decorative Blocks
        this.createTrivialCube(AetherIIBlocks.UNDERSHALE_FLAGSTONES.get());
        this.createTrivialCube(AetherIIBlocks.UNDERSHALE_TILE.get());
        this.createCubeColumn(AetherIIBlocks.UNDERSHALE_BASE_BRICKS.get(), AetherIIBlocks.UNDERSHALE_TILE.get());
        this.createCubeColumn(AetherIIBlocks.UNDERSHALE_CAPSTONE_BRICKS.get(), AetherIIBlocks.UNDERSHALE_TILE.get());
        this.createFacingColumnWithHorizontalVariant(AetherIIBlocks.UNDERSHALE_BASE_PILLAR.get(), AetherIIBlocks.UNDERSHALE_TILE.get());
        this.createFacingColumnWithHorizontalVariant(AetherIIBlocks.UNDERSHALE_CAPSTONE_PILLAR.get(), AetherIIBlocks.UNDERSHALE_TILE.get());
        this.createFacingColumnWithHorizontalVariant(AetherIIBlocks.UNDERSHALE_PILLAR.get(), AetherIIBlocks.UNDERSHALE_TILE.get());

        // Sentry Bricks
        this.createLitBlock(AetherIIBlocks.SENTRY_BRICKS.get());
        this.createLitStairs(AetherIIBlocks.SENTRY_BRICK_STAIRS.get(), AetherIIBlocks.SENTRY_BRICKS.get());
        this.createLitSlab(AetherIIBlocks.SENTRY_BRICK_SLAB.get(), AetherIIBlocks.SENTRY_BRICKS.get());
        this.createLitWall(AetherIIBlocks.SENTRY_BRICK_WALL.get(), AetherIIBlocks.SENTRY_BRICKS.get(), AetherIIBlocks.UNDERSHALE_BRICKS.get());
        this.litButton(AetherIIBlocks.SENTRY_BUTTON.get());

        // Sentry Decorative Blocks
        this.createLitBlock(AetherIIBlocks.SENTRY_LIGHTSTONE.get());
        this.createLitBlock(AetherIIBlocks.SENTRY_FLAGSTONES.get());
        this.createLitBlock(AetherIIBlocks.SENTRY_TILE.get());
        this.createLitCubeColumn(AetherIIBlocks.SENTRY_BASE_BRICKS.get(), AetherIIBlocks.UNDERSHALE_TILE.get());
        this.createLitCubeColumn(AetherIIBlocks.SENTRY_CAPSTONE_BRICKS.get(), AetherIIBlocks.UNDERSHALE_TILE.get());
        this.createLitFacingColumnWithHorizontalVariant(AetherIIBlocks.SENTRY_BASE_PILLAR.get(), AetherIIBlocks.UNDERSHALE_TILE.get());
        this.createLitFacingColumnWithHorizontalVariant(AetherIIBlocks.SENTRY_CAPSTONE_PILLAR.get(), AetherIIBlocks.UNDERSHALE_TILE.get());
        this.createLitFacingColumnWithHorizontalVariant(AetherIIBlocks.SENTRY_PILLAR.get(), AetherIIBlocks.UNDERSHALE_TILE.get());

        // Agiosite Decorative Blocks
        this.createTrivialCube(AetherIIBlocks.AGIOSITE_FLAGSTONES.get());
        this.createTrivialCube(AetherIIBlocks.AGIOSITE_KEYSTONE.get());
        this.createCubeColumn(AetherIIBlocks.AGIOSITE_BASE_BRICKS.get(), AetherIIBlocks.AGIOSITE_KEYSTONE.get());
        this.createCubeColumn(AetherIIBlocks.AGIOSITE_CAPSTONE_BRICKS.get(), AetherIIBlocks.AGIOSITE_KEYSTONE.get());
        this.createFacingColumnWithHorizontalVariant(AetherIIBlocks.AGIOSITE_BASE_PILLAR.get(), AetherIIBlocks.AGIOSITE_KEYSTONE.get());
        this.createFacingColumnWithHorizontalVariant(AetherIIBlocks.AGIOSITE_CAPSTONE_PILLAR.get(), AetherIIBlocks.AGIOSITE_KEYSTONE.get());
        this.createFacingColumnWithHorizontalVariant(AetherIIBlocks.AGIOSITE_PILLAR.get(), AetherIIBlocks.AGIOSITE_KEYSTONE.get());

        // Ichorite Decorative Blocks
        this.createTrivialCube(AetherIIBlocks.ICHORITE_FLAGSTONES.get());
        this.createTrivialCube(AetherIIBlocks.ICHORITE_RUNESTONE.get());
        this.createTrivialCube(AetherIIBlocks.ICHORITE_KEYSTONE.get());
        this.createCubeColumn(AetherIIBlocks.ICHORITE_BASE_BRICKS.get(), AetherIIBlocks.ICHORITE_KEYSTONE.get());
        this.createCubeColumn(AetherIIBlocks.ICHORITE_CAPSTONE_BRICKS.get(), AetherIIBlocks.ICHORITE_KEYSTONE.get());
        this.createFacingColumnWithHorizontalVariant(AetherIIBlocks.ICHORITE_BASE_PILLAR.get(), AetherIIBlocks.ICHORITE_KEYSTONE.get());
        this.createFacingColumnWithHorizontalVariant(AetherIIBlocks.ICHORITE_CAPSTONE_PILLAR.get(), AetherIIBlocks.ICHORITE_KEYSTONE.get());
        this.createFacingColumnWithHorizontalVariant(AetherIIBlocks.ICHORITE_PILLAR.get(), AetherIIBlocks.ICHORITE_KEYSTONE.get());

        // Marbled Ichorite Decorative Blocks
        this.createTrivialCube(AetherIIBlocks.MARBLED_FLAGSTONES.get());
        this.createTrivialCube(AetherIIBlocks.MARBLED_KEYSTONE.get());
        this.createCubeColumn(AetherIIBlocks.MARBLED_BASE_BRICKS.get(), AetherIIBlocks.MARBLED_KEYSTONE.get());
        this.createCubeColumn(AetherIIBlocks.MARBLED_CAPSTONE_BRICKS.get(), AetherIIBlocks.MARBLED_KEYSTONE.get());
        this.createFacingColumnWithHorizontalVariant(AetherIIBlocks.MARBLED_BASE_PILLAR.get(), AetherIIBlocks.MARBLED_KEYSTONE.get());
        this.createFacingColumnWithHorizontalVariant(AetherIIBlocks.MARBLED_CAPSTONE_PILLAR.get(), AetherIIBlocks.MARBLED_KEYSTONE.get());
        this.createFacingColumnWithHorizontalVariant(AetherIIBlocks.MARBLED_PILLAR.get(), AetherIIBlocks.MARBLED_KEYSTONE.get());

        // Icestone Decorative Blocks
        this.createTrivialCube(AetherIIBlocks.ICESTONE_FLAGSTONES.get());
        this.createTrivialCube(AetherIIBlocks.ICESTONE_KEYSTONE.get());
        this.createCubeColumn(AetherIIBlocks.ICESTONE_BASE_BRICKS.get(), AetherIIBlocks.ICESTONE_KEYSTONE.get());
        this.createCubeColumn(AetherIIBlocks.ICESTONE_CAPSTONE_BRICKS.get(), AetherIIBlocks.ICESTONE_KEYSTONE.get());
        this.createFacingColumnWithHorizontalVariant(AetherIIBlocks.ICESTONE_BASE_PILLAR.get(), AetherIIBlocks.ICESTONE_KEYSTONE.get());
        this.createFacingColumnWithHorizontalVariant(AetherIIBlocks.ICESTONE_CAPSTONE_PILLAR.get(), AetherIIBlocks.ICESTONE_KEYSTONE.get());
        this.createFacingColumnWithHorizontalVariant(AetherIIBlocks.ICESTONE_PILLAR.get(), AetherIIBlocks.ICESTONE_KEYSTONE.get());

        // Glass
        this.createGlassBlocks(AetherIIBlocks.QUICKSOIL_GLASS.get(), AetherIIBlocks.QUICKSOIL_GLASS_PANE.get());
        this.createGlassBlocks(AetherIIBlocks.TILED_QUICKSOIL_GLASS.get(), AetherIIBlocks.TILED_QUICKSOIL_GLASS_PANE.get());
        this.createGlassBlocks(AetherIIBlocks.GRIDDED_QUICKSOIL_GLASS.get(), AetherIIBlocks.GRIDDED_QUICKSOIL_GLASS_PANE.get());
        this.createGlassBlocks(AetherIIBlocks.CRUDE_SCATTERGLASS.get(), AetherIIBlocks.CRUDE_SCATTERGLASS_PANE.get());
        this.createGlassBlocks(AetherIIBlocks.SKYROOT_FRAMED_CRUDE_SCATTERGLASS.get(), AetherIIBlocks.SKYROOT_FRAMED_CRUDE_SCATTERGLASS_PANE.get());
        this.createGlassBlocks(AetherIIBlocks.ARKENIUM_FRAMED_CRUDE_SCATTERGLASS.get(), AetherIIBlocks.ARKENIUM_FRAMED_CRUDE_SCATTERGLASS_PANE.get());
        this.createGlassBlocks(AetherIIBlocks.SCATTERGLASS.get(), AetherIIBlocks.SCATTERGLASS_PANE.get());
        this.createGlassBlocks(AetherIIBlocks.SKYROOT_FRAMED_SCATTERGLASS.get(), AetherIIBlocks.SKYROOT_FRAMED_SCATTERGLASS_PANE.get());
        this.createGlassBlocks(AetherIIBlocks.ARKENIUM_FRAMED_SCATTERGLASS.get(), AetherIIBlocks.ARKENIUM_FRAMED_SCATTERGLASS_PANE.get());

        // Wool
        this.createFullAndCarpetBlocks(AetherIIBlocks.CLOUDWOOL.get(), AetherIIBlocks.CLOUDWOOL_CARPET.get());
        this.createFullAndCarpetBlocks(AetherIIBlocks.WHITE_CLOUDWOOL.get(), AetherIIBlocks.WHITE_CLOUDWOOL_CARPET.get());
        this.createFullAndCarpetBlocks(AetherIIBlocks.ORANGE_CLOUDWOOL.get(), AetherIIBlocks.ORANGE_CLOUDWOOL_CARPET.get());
        this.createFullAndCarpetBlocks(AetherIIBlocks.MAGENTA_CLOUDWOOL.get(), AetherIIBlocks.MAGENTA_CLOUDWOOL_CARPET.get());
        this.createFullAndCarpetBlocks(AetherIIBlocks.LIGHT_BLUE_CLOUDWOOL.get(), AetherIIBlocks.LIGHT_BLUE_CLOUDWOOL_CARPET.get());
        this.createFullAndCarpetBlocks(AetherIIBlocks.YELLOW_CLOUDWOOL.get(), AetherIIBlocks.YELLOW_CLOUDWOOL_CARPET.get());
        this.createFullAndCarpetBlocks(AetherIIBlocks.LIME_CLOUDWOOL.get(), AetherIIBlocks.LIME_CLOUDWOOL_CARPET.get());
        this.createFullAndCarpetBlocks(AetherIIBlocks.PINK_CLOUDWOOL.get(), AetherIIBlocks.PINK_CLOUDWOOL_CARPET.get());
        this.createFullAndCarpetBlocks(AetherIIBlocks.GRAY_CLOUDWOOL.get(), AetherIIBlocks.GRAY_CLOUDWOOL_CARPET.get());
        this.createFullAndCarpetBlocks(AetherIIBlocks.LIGHT_GRAY_CLOUDWOOL.get(), AetherIIBlocks.LIGHT_GRAY_CLOUDWOOL_CARPET.get());
        this.createFullAndCarpetBlocks(AetherIIBlocks.CYAN_CLOUDWOOL.get(), AetherIIBlocks.CYAN_CLOUDWOOL_CARPET.get());
        this.createFullAndCarpetBlocks(AetherIIBlocks.PURPLE_CLOUDWOOL.get(), AetherIIBlocks.PURPLE_CLOUDWOOL_CARPET.get());
        this.createFullAndCarpetBlocks(AetherIIBlocks.BLUE_CLOUDWOOL.get(), AetherIIBlocks.BLUE_CLOUDWOOL_CARPET.get());
        this.createFullAndCarpetBlocks(AetherIIBlocks.BROWN_CLOUDWOOL.get(), AetherIIBlocks.BROWN_CLOUDWOOL_CARPET.get());
        this.createFullAndCarpetBlocks(AetherIIBlocks.GREEN_CLOUDWOOL.get(), AetherIIBlocks.GREEN_CLOUDWOOL_CARPET.get());
        this.createFullAndCarpetBlocks(AetherIIBlocks.RED_CLOUDWOOL.get(), AetherIIBlocks.RED_CLOUDWOOL_CARPET.get());
        this.createFullAndCarpetBlocks(AetherIIBlocks.BLACK_CLOUDWOOL.get(), AetherIIBlocks.BLACK_CLOUDWOOL_CARPET.get());

        // Roofing
        this.createRoofing(AetherIIBlocks.CLOUDWOOL_ROOFING.get());

        // Arkenium Blocks
        this.createDoor(AetherIIBlocks.ARKENIUM_DOOR.get());
        this.createOrientableTrapdoor(AetherIIBlocks.ARKENIUM_TRAPDOOR.get());
        this.createBarsWithDifferentEdge(AetherIIBlocks.ARKENIUM_BARS.get(), AetherIIBlocks.ARKENIUM_BARS.get(), "");
        this.createBarsWithDifferentEdge(AetherIIBlocks.FLORAL_ARKENIUM_BARS.get(), AetherIIBlocks.ARKENIUM_BARS.get(), "_edge");
        this.createBarsWithDifferentEdge(AetherIIBlocks.PATTERNED_ARKENIUM_BARS.get(), AetherIIBlocks.ARKENIUM_BARS.get(), "_edge");
        this.createBarsWithDifferentEdge(AetherIIBlocks.CURVED_ARKENIUM_BARS.get(), AetherIIBlocks.ARKENIUM_BARS.get(), "_edge");

        // Rustic Arkenium Blocks
        this.createBarsWithDifferentEdge(AetherIIBlocks.RUSTIC_ARKENIUM_BARS.get(), AetherIIBlocks.RUSTIC_ARKENIUM_BARS.get(), "");
        this.createBarsWithDifferentEdge(AetherIIBlocks.RUSTIC_FLORAL_ARKENIUM_BARS.get(), AetherIIBlocks.RUSTIC_ARKENIUM_BARS.get(), "_edge");
        this.createBarsWithDifferentEdge(AetherIIBlocks.RUSTIC_PATTERNED_ARKENIUM_BARS.get(), AetherIIBlocks.RUSTIC_ARKENIUM_BARS.get(), "_edge");
        this.createBarsWithDifferentEdge(AetherIIBlocks.RUSTIC_CURVED_ARKENIUM_BARS.get(), AetherIIBlocks.RUSTIC_ARKENIUM_BARS.get(), "_edge");

        // Inert Mineral Blocks
        this.createTrivialCube(AetherIIBlocks.INERT_ARKENIUM_BLOCK.get());
        this.createTrivialCube(AetherIIBlocks.INERT_GRAVITITE_BLOCK.get());

        // Mineral Blocks
        this.createTrivialCube(AetherIIBlocks.AMBROSIUM_BLOCK.get());
        this.createTrivialCube(AetherIIBlocks.ZANITE_BLOCK.get());
        this.createTrivialCube(AetherIIBlocks.ARKENIUM_BLOCK.get());
        this.createTrivialCube(AetherIIBlocks.GRAVITITE_BLOCK.get());
        this.createTrivialCube(AetherIIBlocks.GLINT_BLOCK.get());
        this.createTrivialCube(AetherIIBlocks.CORROBONITE_BLOCK.get());
        this.createTrivialCube(AetherIIBlocks.GOLDEN_AMBER_BLOCK.get());

        // Storage Blocks
        this.createRotatedPillarWithHorizontalVariant(AetherIIBlocks.BRETTL_GRASS_BUNDLE.get(), TexturedModel.COLUMN, TexturedModel.COLUMN_HORIZONTAL);
        this.createTrivialCube(AetherIIBlocks.GEL_BLOCK.get());

        // Arilum Lantern
        this.createArilumLantern(AetherIIBlocks.WHITE_ARILUM_LANTERN.get());
        this.createArilumLantern(AetherIIBlocks.ORANGE_ARILUM_LANTERN.get());
        this.createArilumLantern(AetherIIBlocks.MAGENTA_ARILUM_LANTERN.get());
        this.createArilumLantern(AetherIIBlocks.LIGHT_BLUE_ARILUM_LANTERN.get());
        this.createArilumLantern(AetherIIBlocks.YELLOW_ARILUM_LANTERN.get());
        this.createArilumLantern(AetherIIBlocks.LIME_ARILUM_LANTERN.get());
        this.createArilumLantern(AetherIIBlocks.PINK_ARILUM_LANTERN.get());
        this.createArilumLantern(AetherIIBlocks.GRAY_ARILUM_LANTERN.get());
        this.createArilumLantern(AetherIIBlocks.LIGHT_GRAY_ARILUM_LANTERN.get());
        this.createArilumLantern(AetherIIBlocks.CYAN_ARILUM_LANTERN.get());
        this.createArilumLantern(AetherIIBlocks.PURPLE_ARILUM_LANTERN.get());
        this.createArilumLantern(AetherIIBlocks.BLUE_ARILUM_LANTERN.get());
        this.createArilumLantern(AetherIIBlocks.BROWN_ARILUM_LANTERN.get());
        this.createArilumLantern(AetherIIBlocks.GREEN_ARILUM_LANTERN.get());
        this.createArilumLantern(AetherIIBlocks.RED_ARILUM_LANTERN.get());
        this.createArilumLantern(AetherIIBlocks.BLACK_ARILUM_LANTERN.get());

        // Utility
        this.createAmbrosiumTorch();
        this.createArkeniumLantern();
        this.createRusticArkeniumLantern();
        this.createAxisAlignedPillarBlockCustomModel(AetherIIBlocks.ARKENIUM_CHAIN.get(), plainVariant(ModelLocationUtils.getModelLocation(AetherIIBlocks.ARKENIUM_CHAIN.get())));
        this.createCraftingTableLike(AetherIIBlocks.SKYROOT_CRAFTING_TABLE.get(), AetherIIBlocks.SKYROOT_PLANKS.get(), TextureMapping::craftingTable);
        this.createFurnace(AetherIIBlocks.HOLYSTONE_FURNACE.get(), TexturedModel.ORIENTABLE_ONLY_TOP);
        this.createFurnace(AetherIIBlocks.HOLYSTONE_SMOKER.get(), TexturedModel.ORIENTABLE);
        this.createAmberHourglass(AetherIIBlocks.AMBER_HOURGLASS.get());
        this.createAltar(AetherIIBlocks.ALTAR.get(), AetherIIBlocks.HOLYSTONE.get());
        this.createArtisansBench(AetherIIBlocks.ARTISANS_BENCH.get(), AetherIIBlocks.HOLYSTONE_BRICKS.get());
        this.createArkeniumForge(AetherIIBlocks.ARKENIUM_FORGE.get(), AetherIIBlocks.ARKENIUM_BLOCK.get());
        this.createAlkahestPurifier(AetherIIBlocks.ALKAHEST_PURIFIER.get(), AetherIIBlocks.ARKENIUM_BLOCK.get());
        this.createTrivialCube(AetherIIBlocks.MUSIC_BLOCK.get());
        this.createCampfire(AetherIIBlocks.AMBROSIUM_CAMPFIRE.get());
        this.createChest(AetherIIBlocks.SKYROOT_CHEST.get(), AetherIIBlocks.SKYROOT_PLANKS.get(), Identifier.fromNamespaceAndPath(AetherII.MODID, "skyroot_chest"), true);
        this.createBarrel(AetherIIBlocks.SKYROOT_BARREL.get());
        this.createLadder(AetherIIBlocks.SKYROOT_LADDER.get());
        this.createBedroll(AetherIIBlocks.CLOUDWOOL_BEDROLL.get());

        this.createBed(AetherIIBlocks.SKYROOT_BED.get(), AetherIIBlocks.SKYROOT_PLANKS.get(), "undyed");
        this.createBed(AetherIIBlocks.WHITE_SKYROOT_BED.get(), AetherIIBlocks.SKYROOT_PLANKS.get(), "white");
        this.createBed(AetherIIBlocks.ORANGE_SKYROOT_BED.get(), AetherIIBlocks.SKYROOT_PLANKS.get(), "orange");
        this.createBed(AetherIIBlocks.MAGENTA_SKYROOT_BED.get(), AetherIIBlocks.SKYROOT_PLANKS.get(), "magenta");
        this.createBed(AetherIIBlocks.LIGHT_BLUE_SKYROOT_BED.get(), AetherIIBlocks.SKYROOT_PLANKS.get(), "light_blue");
        this.createBed(AetherIIBlocks.YELLOW_SKYROOT_BED.get(), AetherIIBlocks.SKYROOT_PLANKS.get(), "yellow");
        this.createBed(AetherIIBlocks.LIME_SKYROOT_BED.get(), AetherIIBlocks.SKYROOT_PLANKS.get(), "lime");
        this.createBed(AetherIIBlocks.PINK_SKYROOT_BED.get(), AetherIIBlocks.SKYROOT_PLANKS.get(), "pink");
        this.createBed(AetherIIBlocks.GRAY_SKYROOT_BED.get(), AetherIIBlocks.SKYROOT_PLANKS.get(), "gray");
        this.createBed(AetherIIBlocks.LIGHT_GRAY_SKYROOT_BED.get(), AetherIIBlocks.SKYROOT_PLANKS.get(), "light_gray");
        this.createBed(AetherIIBlocks.CYAN_SKYROOT_BED.get(), AetherIIBlocks.SKYROOT_PLANKS.get(), "cyan");
        this.createBed(AetherIIBlocks.PURPLE_SKYROOT_BED.get(), AetherIIBlocks.SKYROOT_PLANKS.get(), "purple");
        this.createBed(AetherIIBlocks.BLUE_SKYROOT_BED.get(), AetherIIBlocks.SKYROOT_PLANKS.get(), "blue");
        this.createBed(AetherIIBlocks.BROWN_SKYROOT_BED.get(), AetherIIBlocks.SKYROOT_PLANKS.get(), "brown");
        this.createBed(AetherIIBlocks.GREEN_SKYROOT_BED.get(), AetherIIBlocks.SKYROOT_PLANKS.get(), "green");
        this.createBed(AetherIIBlocks.RED_SKYROOT_BED.get(), AetherIIBlocks.SKYROOT_PLANKS.get(), "red");
        this.createBed(AetherIIBlocks.BLACK_SKYROOT_BED.get(), AetherIIBlocks.SKYROOT_PLANKS.get(), "black");

        this.createVase(AetherIIBlocks.HOLYSTONE_VASE.get(), AetherIIBlocks.FADED_HOLYSTONE_BRICKS.get());
        this.createVase(AetherIIBlocks.VERADEXIAN_VASE.get(), AetherIIBlocks.ICHORITE_BRICKS.get());
        this.createVase(AetherIIBlocks.BREXALLEN_VASE.get(), AetherIIBlocks.AGIOSITE.get());

        this.createSentryCrate(AetherIIBlocks.SENTRY_CRATE.get(), AetherIIBlocks.UNDERSHALE_BRICKS.get());
        this.createSentrySpawner(AetherIIBlocks.SENTRY_SPAWNER.get(), AetherIIBlocks.UNDERSHALE_BRICKS.get());
        this.createSentryTrap(AetherIIBlocks.SENTRY_TRAP.get(), AetherIIBlocks.UNDERSHALE_TILE.get());

        this.createCopyBlock(AetherIIBlocks.LOCKED_BLOCK, "dungeon_lock");
        this.createCopyBlock(AetherIIBlocks.BOSS_DOORWAY_BLOCK, "dungeon_doorway");
        this.createCopyBlock(AetherIIBlocks.TREASURE_DOORWAY_BLOCK, "dungeon_treasure");

        this.createHangingSign(AetherIIBlocks.STRIPPED_SKYROOT_LOG.get(), AetherIIBlocks.SKYROOT_HANGING_SIGN.get(), AetherIIBlocks.SKYROOT_WALL_HANGING_SIGN.get());
        this.createHangingSign(AetherIIBlocks.STRIPPED_GREATROOT_LOG.get(), AetherIIBlocks.GREATROOT_HANGING_SIGN.get(), AetherIIBlocks.GREATROOT_WALL_HANGING_SIGN.get());
        this.createHangingSign(AetherIIBlocks.STRIPPED_WISPROOT_LOG.get(), AetherIIBlocks.WISPROOT_HANGING_SIGN.get(), AetherIIBlocks.WISPROOT_WALL_HANGING_SIGN.get());
        this.createHangingSign(AetherIIBlocks.STRIPPED_AMBEROOT_LOG.get(), AetherIIBlocks.AMBEROOT_HANGING_SIGN.get(), AetherIIBlocks.AMBEROOT_WALL_HANGING_SIGN.get());

        this.createShelf(AetherIIBlocks.SKYROOT_SHELF.get(), AetherIIBlocks.STRIPPED_SKYROOT_LOG.get());
        this.createShelf(AetherIIBlocks.GREATROOT_SHELF.get(), AetherIIBlocks.STRIPPED_GREATROOT_LOG.get());
        this.createShelf(AetherIIBlocks.WISPROOT_SHELF.get(), AetherIIBlocks.STRIPPED_WISPROOT_LOG.get());
        this.createShelf(AetherIIBlocks.AMBEROOT_SHELF.get(), AetherIIBlocks.STRIPPED_AMBEROOT_LOG.get());

        this.createLever(AetherIIBlocks.HOLYSTONE_LEVER.get());

        // Bookshelves
        this.createCubeColumn(AetherIIBlocks.SKYROOT_BOOKSHELF.get(), AetherIIBlocks.SKYROOT_PLANKS.get());
        this.createCubeColumn(AetherIIBlocks.GREATROOT_BOOKSHELF.get(), AetherIIBlocks.GREATROOT_PLANKS.get());
        this.createCubeColumn(AetherIIBlocks.WISPROOT_BOOKSHELF.get(), AetherIIBlocks.WISPROOT_PLANKS.get());
        this.createCubeColumn(AetherIIBlocks.AMBEROOT_BOOKSHELF.get(), AetherIIBlocks.AMBEROOT_PLANKS.get());
        this.createCubeColumn(AetherIIBlocks.HOLYSTONE_BOOKSHELF.get(), AetherIIBlocks.HOLYSTONE_BRICKS.get());

        // Furniture
        this.createOutpostCampfire();
        this.createMural();
        this.createTrivialCube(AetherIIBlocks.MURAL.get());

        this.createMeltingBlock(AetherIIBlocks.FROSTED_ICE.get(), Blocks.FROSTED_ICE, ModelTemplates.CUBE_ALL);
        this.createMeltingBlock(AetherIIBlocks.FROSTED_ARCTIC_ICE.get(), AetherIIBlocks.FROSTED_ARCTIC_ICE.get(), ModelTemplates.CUBE_ALL);
        this.createMeltingBlock(AetherIIBlocks.UNSTABLE_OBSIDIAN.get(), AetherIIBlocks.UNSTABLE_OBSIDIAN.get(), ModelTemplates.CUBE_ALL);

        // Infected Guardian Tree
        // Guardian Wood
        this.woodProvider(AetherIIBlocks.GUARDIAN_LOG.get()).logWithHorizontal(AetherIIBlocks.GUARDIAN_LOG.get()).wood(AetherIIBlocks.GUARDIAN_WOOD.get());
        this.woodProvider(AetherIIBlocks.STRIPPED_GUARDIAN_LOG.get()).logWithHorizontal(AetherIIBlocks.STRIPPED_GUARDIAN_LOG.get()).wood(AetherIIBlocks.STRIPPED_GUARDIAN_WOOD.get());

        // Infected Wood
        this.woodProvider(AetherIIBlocks.INFECTED_LOG.get()).logWithHorizontal(AetherIIBlocks.INFECTED_LOG.get()).wood(AetherIIBlocks.INFECTED_WOOD.get());
        this.woodProvider(AetherIIBlocks.STRIPPED_INFECTED_LOG.get()).logWithHorizontal(AetherIIBlocks.STRIPPED_INFECTED_LOG.get()).wood(AetherIIBlocks.STRIPPED_INFECTED_WOOD.get());

        // Guardian Slabs
        this.createLogSlab(AetherIIBlocks.GUARDIAN_LOG_SLAB.get(), AetherIIBlocks.GUARDIAN_LOG.get());
        this.createWoodSlab(AetherIIBlocks.GUARDIAN_WOOD_SLAB.get(), AetherIIBlocks.GUARDIAN_WOOD.get(), AetherIIBlocks.GUARDIAN_LOG.get());
        this.createLogSlab(AetherIIBlocks.STRIPPED_GUARDIAN_LOG_SLAB.get(), AetherIIBlocks.STRIPPED_GUARDIAN_LOG.get());
        this.createWoodSlab(AetherIIBlocks.STRIPPED_GUARDIAN_WOOD_SLAB.get(), AetherIIBlocks.STRIPPED_GUARDIAN_WOOD.get(), AetherIIBlocks.STRIPPED_GUARDIAN_LOG.get());
        this.createLogSlab(AetherIIBlocks.INFECTED_LOG_SLAB.get(), AetherIIBlocks.INFECTED_LOG.get());
        this.createWoodSlab(AetherIIBlocks.INFECTED_WOOD_SLAB.get(), AetherIIBlocks.INFECTED_WOOD.get(), AetherIIBlocks.INFECTED_LOG.get());
        this.createLogSlab(AetherIIBlocks.STRIPPED_INFECTED_LOG_SLAB.get(), AetherIIBlocks.STRIPPED_INFECTED_LOG.get());
        this.createWoodSlab(AetherIIBlocks.STRIPPED_INFECTED_WOOD_SLAB.get(), AetherIIBlocks.STRIPPED_INFECTED_WOOD.get(), AetherIIBlocks.STRIPPED_INFECTED_LOG.get());

        // Guardian Trunks
        this.createTrunk(AetherIIBlocks.GUARDIAN_TRUNK.get(), AetherIIBlocks.GUARDIAN_LOG.get());
        this.createTrunk(AetherIIBlocks.STRIPPED_GUARDIAN_TRUNK.get(), AetherIIBlocks.STRIPPED_GUARDIAN_LOG.get());
        this.createTrunk(AetherIIBlocks.INFECTED_TRUNK.get(), AetherIIBlocks.INFECTED_LOG.get());
        this.createTrunk(AetherIIBlocks.STRIPPED_INFECTED_TRUNK.get(), AetherIIBlocks.STRIPPED_INFECTED_LOG.get());

        // Guardian Root Blocks
        this.createTrivialCube(AetherIIBlocks.GUARDIAN_ROOTS.get());
        this.createTrivialCube(AetherIIBlocks.UNSTABLE_GUARDIAN_ROOTS.get());
        this.createTrivialCube(AetherIIBlocks.LUCENT_GUARDIAN_ROOTS.get());
        this.createTrivialCube(AetherIIBlocks.GUARDIAN_LAMP.get());

        // Undergrowth Blocks
        this.createCutoutMippedCube(AetherIIBlocks.UNDERGROWTH_LEAVES.get());
        this.createVine(AetherIIBlocks.UNDERGROWTH_VINES.get(), AetherIIModelTemplates.MOSS_VINE);
        this.createHangingUndergrowth(AetherIIBlocks.HANGING_UNDERGROWTH.get());
        this.createHangingUndergrowth(AetherIIBlocks.HANGING_UNDERGROWTH_PLANT.get());
        this.registerSimpleFlatItemModel(AetherIIBlocks.HANGING_UNDERGROWTH.get(), "_plant");

        // Rotshroom Blocks
        this.createCubeBottom(AetherIIBlocks.ROTSHROOM_BLOCK.get());
        this.createMushroomSlab(AetherIIBlocks.ROTSHROOM_SLAB.get(), AetherIIBlocks.ROTSHROOM_BLOCK.get());
        this.createRotatedPillarWithHorizontalVariant(AetherIIBlocks.ROTSHROOM_STEM.get(), TexturedModel.COLUMN, TexturedModel.COLUMN_HORIZONTAL);
        this.createShelfRotshroomSlab(AetherIIBlocks.SHELF_ROTSHROOM_SLAB.get());
        this.createPlantWithDefaultItem(AetherIIBlocks.ROTSHROOM.get(), AetherIIBlocks.POTTED_ROTSHROOM.get(), PlantType.NOT_TINTED);
        this.createRotshroomCluster(AetherIIBlocks.ROTSHROOM_CLUSTER.get());
        this.createRotshroomToadstool(AetherIIBlocks.ROTSHROOM_TOADSTOOL.get());
        this.createShelfRotshroom(AetherIIBlocks.SHELF_ROTSHROOM.get());
        this.createVine(AetherIIBlocks.ROTGROWTH_VINES.get(), AetherIIModelTemplates.MOSS_VINE);

        // Dungeon Furniture
        this.createPrayerCandle(AetherIIBlocks.PRAYER_CANDLE.get(), AetherIIBlocks.GUARDIAN_LOG.get());
        this.createGuardianPew(AetherIIBlocks.GUARDIAN_PEW.get(), AetherIIBlocks.GUARDIAN_LOG.get());
        this.createGuardianDonationBox(AetherIIBlocks.GUARDIAN_DONATION_BOX.get(), AetherIIBlocks.GUARDIAN_LOG.get());
        this.createAbandonedBag(AetherIIBlocks.ABANDONED_BAG.get(), AetherIIBlocks.LIGHT_BLUE_CLOUDWOOL.get());
        this.createFungalCache(AetherIIBlocks.FUNGAL_CACHE.get(), AetherIIBlocks.ROTSHROOM_BLOCK.get());
        this.createSageChest(AetherIIBlocks.SAGE_CHEST.get(), AetherIIBlocks.GUARDIAN_LOG.get());
    }
}