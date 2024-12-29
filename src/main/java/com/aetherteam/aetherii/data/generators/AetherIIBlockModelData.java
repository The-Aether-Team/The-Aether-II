package com.aetherteam.aetherii.data.generators;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.data.providers.AetherIIBlockModelProvider;
import com.aetherteam.aetherii.data.resources.builders.AetherIIBlockFamilies;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.blockstates.Variant;
import net.minecraft.client.data.models.blockstates.VariantProperties;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.PackOutput;

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
        TextureMapping grassMapping = new TextureMapping().put(TextureSlot.BOTTOM, TextureMapping.getBlockTexture(AetherIIBlocks.AETHER_DIRT.get())).copyForced(TextureSlot.BOTTOM, TextureSlot.PARTICLE)
                .put(TextureSlot.TOP, TextureMapping.getBlockTexture(AetherIIBlocks.AETHER_GRASS_BLOCK.get(), "_top"))
                .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(AetherIIBlocks.AETHER_GRASS_BLOCK.get(), "_snow"));
        Variant snowVariant = Variant.variant().with(VariantProperties.MODEL, ModelTemplates.CUBE_BOTTOM_TOP.createWithSuffix(AetherIIBlocks.AETHER_GRASS_BLOCK.get(), "_snow", grassMapping, blockModels.modelOutput));

        blockModels.createGrassLikeBlock(AetherIIBlocks.AETHER_GRASS_BLOCK.get(), ModelLocationUtils.getModelLocation(AetherIIBlocks.AETHER_GRASS_BLOCK.get()), snowVariant);
        blockModels.createGrassLikeBlock(AetherIIBlocks.ENCHANTED_AETHER_GRASS_BLOCK.get(), ModelLocationUtils.getModelLocation(AetherIIBlocks.ENCHANTED_AETHER_GRASS_BLOCK.get()), snowVariant); //todo
        blockModels.blockStateOutput.accept(BlockModelGenerators.createRotatedVariant(AetherIIBlocks.AETHER_DIRT_PATH.get(), ModelLocationUtils.getModelLocation(AetherIIBlocks.AETHER_DIRT_PATH.get())));
        blockModels.createTrivialCube(AetherIIBlocks.AETHER_DIRT.get());
        blockModels.createTrivialCube(AetherIIBlocks.COARSE_AETHER_DIRT.get());
        this.createAetherFarmland(blockModels);
        blockModels.createTrivialCube(AetherIIBlocks.SHIMMERING_SILT.get());

        // Underground
        blockModels.createTrivialCube(AetherIIBlocks.UNSTABLE_HOLYSTONE.get());
        blockModels.createTrivialCube(AetherIIBlocks.UNSTABLE_UNDERSHALE.get());
        blockModels.createTrivialCube(AetherIIBlocks.ICHORITE.get());
//        this.roots(AetherIIBlocks.SKY_ROOTS.get());
//        this.gas(AetherIIBlocks.GAS.get());
//        this.pointedStone(AetherIIBlocks.POINTED_HOLYSTONE.get());
//        this.pointedStone(AetherIIBlocks.POINTED_ICHORITE.get());

        // Highfields
        blockModels.createTrivialCube(AetherIIBlocks.QUICKSOIL.get());
        blockModels.createFullAndCarpetBlocks(AetherIIBlocks.BRYALINN_MOSS_BLOCK.get(), AetherIIBlocks.BRYALINN_MOSS_CARPET.get());
//        this.mossVines(AetherIIBlocks.BRYALINN_MOSS_VINES.get());
//        this.bryalinnFlowers(AetherIIBlocks.BRYALINN_MOSS_FLOWERS.get());
        this.createCutoutCube(blockModels, AetherIIBlocks.TANGLED_BRANCHES.get());

        // Magnetic
        blockModels.createTrivialCube(AetherIIBlocks.FERROSITE_SAND.get());
        blockModels.createTrivialCube(AetherIIBlocks.FERROSITE_MUD.get());
        blockModels.createTrivialCube(AetherIIBlocks.FERROSITE.get());
        blockModels.createTrivialCube(AetherIIBlocks.RUSTED_FERROSITE.get());
        blockModels.createCrossBlock(AetherIIBlocks.MAGNETIC_SHROOM.get(), BlockModelGenerators.PlantType.NOT_TINTED);

        // Arctic
        blockModels.createTrivialCube(AetherIIBlocks.ARCTIC_SNOW_BLOCK.get());
//        this.snowLayer(AetherIIBlocks.ARCTIC_SNOW.get(), AetherIIBlocks.ARCTIC_SNOW_BLOCK.get());
        this.createTranslucentCube(blockModels, AetherIIBlocks.ARCTIC_ICE.get());
        blockModels.createTrivialCube(AetherIIBlocks.ARCTIC_PACKED_ICE.get());
//        this.iceCrystal(AetherIIBlocks.LARGE_ARCTIC_ICE_CRYSTAL.get());
//        this.iceCrystal(AetherIIBlocks.MEDIUM_ARCTIC_ICE_CRYSTAL.get());
//        this.iceCrystal(AetherIIBlocks.SMALL_ARCTIC_ICE_CRYSTAL.get());
        blockModels.createFullAndCarpetBlocks(AetherIIBlocks.SHAYELINN_MOSS_BLOCK.get(), AetherIIBlocks.SHAYELINN_MOSS_CARPET.get());
//        this.mossVines(AetherIIBlocks.SHAYELINN_MOSS_VINES.get());

        // Irradiated
        blockModels.createTrivialCube(AetherIIBlocks.IRRADIATED_DUST_BLOCK.get());
        blockModels.createFullAndCarpetBlocks(AetherIIBlocks.AMBRELINN_MOSS_BLOCK.get(), AetherIIBlocks.AMBRELINN_MOSS_CARPET.get());
//        this.ambrelinnMossVines(AetherIIBlocks.AMBRELINN_MOSS_VINES.get());
//        this.tarahespFlowers(AetherIIBlocks.TARAHESP_FLOWERS.get());

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
//        this.corroboniteCluster(AetherIIBlocks.CORROBONITE_CLUSTER.get());
//
//        // Aerclouds
//        this.translucentInterior(AetherIIBlocks.COLD_AERCLOUD.get());
//        this.translucentInterior(AetherIIBlocks.BLUE_AERCLOUD.get());
//        this.translucentInterior(AetherIIBlocks.GOLDEN_AERCLOUD.get());
//        this.translucentInterior(AetherIIBlocks.GREEN_AERCLOUD.get());
//        this.purpleAercloud(AetherIIBlocks.PURPLE_AERCLOUD.get());
//        this.translucentInterior(AetherIIBlocks.STORM_AERCLOUD.get());
//
        // Moa Nest
        blockModels.createTrivialCube(AetherIIBlocks.WOVEN_SKYROOT_STICKS.get());
//
//        // Logs
//        this.log(AetherIIBlocks.SKYROOT_LOG.get());
//        this.log(AetherIIBlocks.STRIPPED_SKYROOT_LOG.get());
//        this.log(AetherIIBlocks.GREATROOT_LOG.get());
//        this.log(AetherIIBlocks.STRIPPED_GREATROOT_LOG.get());
//        this.log(AetherIIBlocks.WISPROOT_LOG.get());
//        this.log(AetherIIBlocks.STRIPPED_WISPROOT_LOG.get());
//        this.mossyWisprootLog(AetherIIBlocks.MOSSY_WISPROOT_LOG.get(), AetherIIBlocks.WISPROOT_LOG.get());
//        this.logDifferentTop(AetherIIBlocks.AMBEROOT_LOG.get(), AetherIIBlocks.SKYROOT_LOG.get());
//        this.wood(AetherIIBlocks.SKYROOT_WOOD.get(), AetherIIBlocks.SKYROOT_LOG.get());
//        this.wood(AetherIIBlocks.STRIPPED_SKYROOT_WOOD.get(), AetherIIBlocks.STRIPPED_SKYROOT_LOG.get());
//        this.wood(AetherIIBlocks.GREATROOT_WOOD.get(), AetherIIBlocks.GREATROOT_LOG.get());
//        this.wood(AetherIIBlocks.STRIPPED_GREATROOT_WOOD.get(), AetherIIBlocks.STRIPPED_GREATROOT_LOG.get());
//        this.wood(AetherIIBlocks.WISPROOT_WOOD.get(), AetherIIBlocks.WISPROOT_LOG.get());
//        this.wood(AetherIIBlocks.STRIPPED_WISPROOT_WOOD.get(), AetherIIBlocks.STRIPPED_WISPROOT_LOG.get());
//        this.wood(AetherIIBlocks.AMBEROOT_WOOD.get(), AetherIIBlocks.AMBEROOT_LOG.get());
//
//        // Leaf Pile
//        this.leafPile(AetherIIBlocks.SKYROOT_LEAF_PILE.get(), AetherIIBlocks.SKYROOT_LEAVES.get());
//        this.leafPile(AetherIIBlocks.SKYPLANE_LEAF_PILE.get(), AetherIIBlocks.SKYPLANE_LEAVES.get());
//        this.leafPile(AetherIIBlocks.SKYBIRCH_LEAF_PILE.get(), AetherIIBlocks.SKYBIRCH_LEAVES.get());
//        this.leafPile(AetherIIBlocks.SKYPINE_LEAF_PILE.get(), AetherIIBlocks.SKYPINE_LEAVES.get());
//        this.leafPile(AetherIIBlocks.WISPROOT_LEAF_PILE.get(), AetherIIBlocks.WISPROOT_LEAVES.get());
//        this.leafPile(AetherIIBlocks.WISPTOP_LEAF_PILE.get(), AetherIIBlocks.WISPTOP_LEAVES.get());
//        this.leafPile(AetherIIBlocks.GREATROOT_LEAF_PILE.get(), AetherIIBlocks.GREATROOT_LEAVES.get());
//        this.leafPile(AetherIIBlocks.GREATOAK_LEAF_PILE.get(), AetherIIBlocks.GREATOAK_LEAVES.get());
//        this.leafPile(AetherIIBlocks.GREATBOA_LEAF_PILE.get(), AetherIIBlocks.GREATBOA_LEAVES.get());
//        this.leafPile(AetherIIBlocks.AMBEROOT_LEAF_PILE.get(), AetherIIBlocks.AMBEROOT_LEAVES.get());
//        this.leafPile(AetherIIBlocks.IRRADIATED_SKYROOT_LEAF_PILE.get(), AetherIIBlocks.IRRADIATED_SKYROOT_LEAVES.get());
//        this.leafPile(AetherIIBlocks.IRRADIATED_SKYPLANE_LEAF_PILE.get(), AetherIIBlocks.IRRADIATED_SKYPLANE_LEAVES.get());
//        this.leafPile(AetherIIBlocks.IRRADIATED_SKYBIRCH_LEAF_PILE.get(), AetherIIBlocks.IRRADIATED_SKYBIRCH_LEAVES.get());
//        this.leafPile(AetherIIBlocks.IRRADIATED_SKYPINE_LEAF_PILE.get(), AetherIIBlocks.IRRADIATED_SKYPINE_LEAVES.get());
//        this.leafPile(AetherIIBlocks.IRRADIATED_WISPROOT_LEAF_PILE.get(), AetherIIBlocks.IRRADIATED_WISPROOT_LEAVES.get());
//        this.leafPile(AetherIIBlocks.IRRADIATED_WISPTOP_LEAF_PILE.get(), AetherIIBlocks.IRRADIATED_WISPTOP_LEAVES.get());
//        this.leafPile(AetherIIBlocks.IRRADIATED_GREATROOT_LEAF_PILE.get(), AetherIIBlocks.IRRADIATED_GREATROOT_LEAVES.get());
//        this.leafPile(AetherIIBlocks.IRRADIATED_GREATOAK_LEAF_PILE.get(), AetherIIBlocks.IRRADIATED_GREATOAK_LEAVES.get());
//        this.leafPile(AetherIIBlocks.IRRADIATED_GREATBOA_LEAF_PILE.get(), AetherIIBlocks.IRRADIATED_GREATBOA_LEAVES.get());
//
//        // Leaves
//        this.leaves(AetherIIBlocks.SKYROOT_LEAVES.get());
//        this.leaves(AetherIIBlocks.SKYPLANE_LEAVES.get());
//        this.leaves(AetherIIBlocks.SKYBIRCH_LEAVES.get());
//        this.leaves(AetherIIBlocks.SKYPINE_LEAVES.get());
//        this.leaves(AetherIIBlocks.WISPROOT_LEAVES.get());
//        this.leaves(AetherIIBlocks.WISPTOP_LEAVES.get());
//        this.leaves(AetherIIBlocks.GREATROOT_LEAVES.get());
//        this.leaves(AetherIIBlocks.GREATOAK_LEAVES.get());
//        this.leaves(AetherIIBlocks.GREATBOA_LEAVES.get());
//        this.leaves(AetherIIBlocks.AMBEROOT_LEAVES.get());
//        this.tintedLeaves(AetherIIBlocks.IRRADIATED_SKYROOT_LEAVES.get());
//        this.tintedLeaves(AetherIIBlocks.IRRADIATED_SKYPLANE_LEAVES.get());
//        this.tintedLeaves(AetherIIBlocks.IRRADIATED_SKYBIRCH_LEAVES.get());
//        this.tintedLeaves(AetherIIBlocks.IRRADIATED_SKYPINE_LEAVES.get());
//        this.tintedLeaves(AetherIIBlocks.IRRADIATED_WISPROOT_LEAVES.get());
//        this.tintedLeaves(AetherIIBlocks.IRRADIATED_WISPTOP_LEAVES.get());
//        this.tintedLeaves(AetherIIBlocks.IRRADIATED_GREATROOT_LEAVES.get());
//        this.tintedLeaves(AetherIIBlocks.IRRADIATED_GREATOAK_LEAVES.get());
//        this.tintedLeaves(AetherIIBlocks.IRRADIATED_GREATBOA_LEAVES.get());
//
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
//
//        // Grasses
//        this.shortGrass(AetherIIBlocks.AETHER_SHORT_GRASS.get());
//        this.shortGrass(AetherIIBlocks.AETHER_MEDIUM_GRASS.get());
//        this.shortGrass(AetherIIBlocks.AETHER_LONG_GRASS.get());
//        this.tintedFern(AetherIIBlocks.HIGHLAND_FERN.get());
        blockModels.createCrossBlock(AetherIIBlocks.SHIELD_FERN.get(), BlockModelGenerators.PlantType.NOT_TINTED);
//
//        // Flowers
//        this.frostedCross(AetherIIBlocks.HESPEROSE.get());
//        this.frostedCross(AetherIIBlocks.TARABLOOM.get());
//        this.frostedCross(AetherIIBlocks.POASPROUT.get());
//        this.lilichime(AetherIIBlocks.LILICHIME.get());
//        this.pluracian(AetherIIBlocks.PLURACIAN.get());
//        this.asymmetricalCrossEven(AetherIIBlocks.SATIVAL_SHOOT.get());
//        this.holpupea(AetherIIBlocks.HOLPUPEA.get());
//        this.asymmetricalCrossOdd(AetherIIBlocks.BLADE_POA.get());
        blockModels.createCrossBlock(AetherIIBlocks.AECHOR_CUTTING.get(), BlockModelGenerators.PlantType.NOT_TINTED);
//
//        // Potted Flowers
//        this.pottedTintedPlant(AetherIIBlocks.POTTED_HIGHLAND_FERN.get(), AetherIIBlocks.HIGHLAND_FERN.get());
//        this.pottedPlant(AetherIIBlocks.POTTED_SHIELD_FERN.get(), AetherIIBlocks.SHIELD_FERN.get());
//        this.pottedPlant(AetherIIBlocks.POTTED_HESPEROSE.get(), AetherIIBlocks.HESPEROSE.get());
//        this.pottedPlant(AetherIIBlocks.POTTED_TARABLOOM.get(), AetherIIBlocks.TARABLOOM.get());
//        this.pottedPlant(AetherIIBlocks.POTTED_POASPROUT.get(), AetherIIBlocks.POASPROUT.get());
//        this.pottedAsymmetricalEvenPlant(AetherIIBlocks.POTTED_SATIVAL_SHOOT.get(), AetherIIBlocks.SATIVAL_SHOOT.get());
//        this.pottedLilichime(AetherIIBlocks.POTTED_LILICHIME.get(), AetherIIBlocks.LILICHIME.get());
//        this.pottedPluracian(AetherIIBlocks.POTTED_PLURACIAN.get(), AetherIIBlocks.PLURACIAN.get());
//        this.pottedAsymmetricalOddPlant(AetherIIBlocks.POTTED_BLADE_POA.get(), AetherIIBlocks.BLADE_POA.get());
//        this.pottedPlant(AetherIIBlocks.POTTED_AECHOR_CUTTING.get(), AetherIIBlocks.AECHOR_CUTTING.get());
//
//        // Bushes
//        this.bush(AetherIIBlocks.HIGHLANDS_BUSH.get());
        blockModels.createCrossBlock(AetherIIBlocks.BLUEBERRY_BUSH_STEM.get(), BlockModelGenerators.PlantType.NOT_TINTED);
//        this.berryBush(AetherIIBlocks.BLUEBERRY_BUSH.get(), AetherIIBlocks.BLUEBERRY_BUSH_STEM.get());
//
//        // Potted Bushes
//        this.pottedBush(AetherIIBlocks.POTTED_HIGHLANDS_BUSH.get());
//        this.pottedBush(AetherIIBlocks.POTTED_BLUEBERRY_BUSH.get(), AetherIIBlocks.POTTED_BLUEBERRY_BUSH_STEM.get());
//        this.pottedStem(AetherIIBlocks.POTTED_BLUEBERRY_BUSH_STEM.get());
//
//        // Orange Tree
//        this.orangeTree(AetherIIBlocks.ORANGE_TREE.get());
//
//        // Potted Orange Tree
//        this.pottedOrangeTree(AetherIIBlocks.POTTED_ORANGE_TREE.get(), AetherIIBlocks.ORANGE_TREE.get());
//
//        // Valkyrie Sprout
//        this.valkyrieSprout(AetherIIBlocks.VALKYRIE_SPROUT.get());
//
//        // Brettl
//        this.brettlPlant(AetherIIBlocks.BRETTL_PLANT.get());
//        this.brettlPlant(AetherIIBlocks.BRETTL_PLANT_TIP.get());
//
        // Lake
        blockModels.createCrossBlock(AetherIIBlocks.ARILUM_SHOOT.get(), BlockModelGenerators.PlantType.NOT_TINTED);
        blockModels.createCrossBlock(AetherIIBlocks.ARILUM.get(), BlockModelGenerators.PlantType.NOT_TINTED);
        blockModels.createCrossBlock(AetherIIBlocks.ARILUM_PLANT.get(), BlockModelGenerators.PlantType.NOT_TINTED);
        blockModels.createCrossBlock(AetherIIBlocks.BLOOMING_ARILUM.get(), BlockModelGenerators.PlantType.NOT_TINTED);
        blockModels.createCrossBlock(AetherIIBlocks.BLOOMING_ARILUM_PLANT.get(), BlockModelGenerators.PlantType.NOT_TINTED);
//
//        // Ground Decoration
//        this.twig(AetherIIBlocks.SKYROOT_TWIG.get(), AetherIIBlocks.SKYROOT_LOG.get());
//        this.rock(AetherIIBlocks.HOLYSTONE_ROCK.get(), AetherIIBlocks.HOLYSTONE.get());
//
//        // Skyroot Decorative Blocks
        blockModels.createTrivialCube(AetherIIBlocks.SKYROOT_FLOORBOARDS.get());
        blockModels.createTrivialCube(AetherIIBlocks.SKYROOT_HIGHLIGHT.get());
        blockModels.createTrivialCube(AetherIIBlocks.SKYROOT_SHINGLES.get());
        blockModels.createTrivialCube(AetherIIBlocks.SKYROOT_SMALL_SHINGLES.get());
//        this.decorativeBlock(AetherIIBlocks.SKYROOT_BASE_PLANKS.get(), AetherIIBlocks.SKYROOT_HIGHLIGHT.get());
//        this.decorativeBlock(AetherIIBlocks.SKYROOT_TOP_PLANKS.get(), AetherIIBlocks.SKYROOT_HIGHLIGHT.get());
//        this.decorativeFacingPillar(AetherIIBlocks.SKYROOT_BASE_BEAM.get(), AetherIIBlocks.SKYROOT_HIGHLIGHT.get());
//        this.decorativeFacingPillar(AetherIIBlocks.SKYROOT_TOP_BEAM.get(), AetherIIBlocks.SKYROOT_HIGHLIGHT.get());
//        this.decorativeFacingPillar(AetherIIBlocks.SKYROOT_BEAM.get(), AetherIIBlocks.SKYROOT_HIGHLIGHT.get());
//        this.doorBlock(AetherIIBlocks.SECRET_SKYROOT_DOOR.get(), this.texture(this.name(AetherIIBlocks.SKYROOT_PLANKS.get())), this.texture(this.name(AetherIIBlocks.SKYROOT_PLANKS.get())));
//        this.secretTrapdoorBlock(AetherIIBlocks.SECRET_SKYROOT_TRAPDOOR.get(), this.texture(this.name(AetherIIBlocks.SKYROOT_PLANKS.get())));
//
//        // Greatroot Decorative Blocks
        blockModels.createTrivialCube(AetherIIBlocks.GREATROOT_FLOORBOARDS.get());
        blockModels.createTrivialCube(AetherIIBlocks.GREATROOT_HIGHLIGHT.get());
        blockModels.createTrivialCube(AetherIIBlocks.GREATROOT_SHINGLES.get());
        blockModels.createTrivialCube(AetherIIBlocks.GREATROOT_SMALL_SHINGLES.get());
//        this.decorativeBlock(AetherIIBlocks.GREATROOT_BASE_PLANKS.get(), AetherIIBlocks.GREATROOT_HIGHLIGHT.get());
//        this.decorativeBlock(AetherIIBlocks.GREATROOT_TOP_PLANKS.get(), AetherIIBlocks.GREATROOT_HIGHLIGHT.get());
//        this.decorativeFacingPillar(AetherIIBlocks.GREATROOT_BASE_BEAM.get(), AetherIIBlocks.GREATROOT_HIGHLIGHT.get());
//        this.decorativeFacingPillar(AetherIIBlocks.GREATROOT_TOP_BEAM.get(), AetherIIBlocks.GREATROOT_HIGHLIGHT.get());
//        this.decorativeFacingPillar(AetherIIBlocks.GREATROOT_BEAM.get(), AetherIIBlocks.GREATROOT_HIGHLIGHT.get());
//        this.doorBlock(AetherIIBlocks.SECRET_GREATROOT_DOOR.get(), this.texture(this.name(AetherIIBlocks.GREATROOT_PLANKS.get())), this.texture(this.name(AetherIIBlocks.GREATROOT_PLANKS.get())));
//        this.secretTrapdoorBlock(AetherIIBlocks.SECRET_GREATROOT_TRAPDOOR.get(), this.texture(this.name(AetherIIBlocks.GREATROOT_PLANKS.get())));
//
//        // Wisproot Decorative Blocks
        blockModels.createTrivialCube(AetherIIBlocks.WISPROOT_FLOORBOARDS.get());
        blockModels.createTrivialCube(AetherIIBlocks.WISPROOT_HIGHLIGHT.get());
        blockModels.createTrivialCube(AetherIIBlocks.WISPROOT_SHINGLES.get());
        blockModels.createTrivialCube(AetherIIBlocks.WISPROOT_SMALL_SHINGLES.get());
//        this.decorativeBlock(AetherIIBlocks.WISPROOT_BASE_PLANKS.get(), AetherIIBlocks.WISPROOT_HIGHLIGHT.get());
//        this.decorativeBlock(AetherIIBlocks.WISPROOT_TOP_PLANKS.get(),  AetherIIBlocks.WISPROOT_HIGHLIGHT.get());
//        this.decorativeFacingPillar(AetherIIBlocks.WISPROOT_BASE_BEAM.get(),  AetherIIBlocks.WISPROOT_HIGHLIGHT.get());
//        this.decorativeFacingPillar(AetherIIBlocks.WISPROOT_TOP_BEAM.get(),  AetherIIBlocks.WISPROOT_HIGHLIGHT.get());
//        this.decorativeFacingPillar(AetherIIBlocks.WISPROOT_BEAM.get(),  AetherIIBlocks.WISPROOT_HIGHLIGHT.get());
//        this.doorBlock(AetherIIBlocks.SECRET_WISPROOT_DOOR.get(), this.texture(this.name(AetherIIBlocks.WISPROOT_PLANKS.get())), this.texture(this.name(AetherIIBlocks.WISPROOT_PLANKS.get())));
//        this.secretTrapdoorBlock(AetherIIBlocks.SECRET_WISPROOT_TRAPDOOR.get(), this.texture(this.name(AetherIIBlocks.WISPROOT_PLANKS.get())));
//
//        // Holystone Decorative Blocks
        blockModels.createTrivialCube(AetherIIBlocks.HOLYSTONE_FLAGSTONES.get());
        blockModels.createTrivialCube(AetherIIBlocks.HOLYSTONE_HEADSTONE.get());
        blockModels.createTrivialCube(AetherIIBlocks.HOLYSTONE_KEYSTONE.get());
//        this.decorativeBlock(AetherIIBlocks.HOLYSTONE_BASE_BRICKS.get(), AetherIIBlocks.HOLYSTONE_KEYSTONE.get());
//        this.decorativeBlock(AetherIIBlocks.HOLYSTONE_CAPSTONE_BRICKS.get(), AetherIIBlocks.HOLYSTONE_KEYSTONE.get());
//        this.decorativeFacingPillar(AetherIIBlocks.HOLYSTONE_BASE_PILLAR.get(), AetherIIBlocks.HOLYSTONE_KEYSTONE.get());
//        this.decorativeFacingPillar(AetherIIBlocks.HOLYSTONE_CAPSTONE_PILLAR.get(), AetherIIBlocks.HOLYSTONE_KEYSTONE.get());
//        this.decorativeFacingPillar(AetherIIBlocks.HOLYSTONE_PILLAR.get(), AetherIIBlocks.HOLYSTONE_KEYSTONE.get());
//
//        // Faded Holystone Decorative Blocks
        blockModels.createTrivialCube(AetherIIBlocks.FADED_HOLYSTONE_FLAGSTONES.get());
        blockModels.createTrivialCube(AetherIIBlocks.FADED_HOLYSTONE_HEADSTONE.get());
        blockModels.createTrivialCube(AetherIIBlocks.FADED_HOLYSTONE_KEYSTONE.get());
//        this.decorativeBlock(AetherIIBlocks.FADED_HOLYSTONE_BASE_BRICKS.get(), AetherIIBlocks.FADED_HOLYSTONE_KEYSTONE.get());
//        this.decorativeBlock(AetherIIBlocks.FADED_HOLYSTONE_CAPSTONE_BRICKS.get(), AetherIIBlocks.FADED_HOLYSTONE_KEYSTONE.get());
//        this.decorativeFacingPillar(AetherIIBlocks.FADED_HOLYSTONE_BASE_PILLAR.get(), AetherIIBlocks.FADED_HOLYSTONE_KEYSTONE.get());
//        this.decorativeFacingPillar(AetherIIBlocks.FADED_HOLYSTONE_CAPSTONE_PILLAR.get(), AetherIIBlocks.FADED_HOLYSTONE_KEYSTONE.get());
//        this.decorativeFacingPillar(AetherIIBlocks.FADED_HOLYSTONE_PILLAR.get(), AetherIIBlocks.FADED_HOLYSTONE_KEYSTONE.get());
//
//        // Undershale Bricks
        blockModels.createTrivialCube(AetherIIBlocks.UNDERSHALE_BRICKS.get());
//        this.stairs(AetherIIBlocks.UNDERSHALE_BRICK_STAIRS.get(), AetherIIBlocks.UNDERSHALE_BRICKS.get());
//        this.slab(AetherIIBlocks.UNDERSHALE_BRICK_SLAB.get(), AetherIIBlocks.UNDERSHALE_BRICKS.get());
//        this.wallBlock(AetherIIBlocks.UNDERSHALE_BRICK_WALL.get(), AetherIIBlocks.UNDERSHALE_BRICKS.get());
//
//        // Agiosite Decorative Blocks
        blockModels.createTrivialCube(AetherIIBlocks.AGIOSITE_FLAGSTONES.get());
        blockModels.createTrivialCube(AetherIIBlocks.AGIOSITE_KEYSTONE.get());
//        this.decorativeBlock(AetherIIBlocks.AGIOSITE_BASE_BRICKS.get(), AetherIIBlocks.AGIOSITE_KEYSTONE.get());
//        this.decorativeBlock(AetherIIBlocks.AGIOSITE_CAPSTONE_BRICKS.get(), AetherIIBlocks.AGIOSITE_KEYSTONE.get());
//        this.decorativeFacingPillar(AetherIIBlocks.AGIOSITE_BASE_PILLAR.get(), AetherIIBlocks.AGIOSITE_KEYSTONE.get());
//        this.decorativeFacingPillar(AetherIIBlocks.AGIOSITE_CAPSTONE_PILLAR.get(), AetherIIBlocks.AGIOSITE_KEYSTONE.get());
//        this.decorativeFacingPillar(AetherIIBlocks.AGIOSITE_PILLAR.get(), AetherIIBlocks.AGIOSITE_KEYSTONE.get());
//
//        // Icestone Decorative Blocks
        blockModels.createTrivialCube(AetherIIBlocks.ICESTONE_FLAGSTONES.get());
        blockModels.createTrivialCube(AetherIIBlocks.ICESTONE_KEYSTONE.get());
//        this.decorativeBlock(AetherIIBlocks.ICESTONE_BASE_BRICKS.get(), AetherIIBlocks.ICESTONE_KEYSTONE.get());
//        this.decorativeBlock(AetherIIBlocks.ICESTONE_CAPSTONE_BRICKS.get(), AetherIIBlocks.ICESTONE_KEYSTONE.get());
//        this.decorativeFacingPillar(AetherIIBlocks.ICESTONE_BASE_PILLAR.get(), AetherIIBlocks.ICESTONE_KEYSTONE.get());
//        this.decorativeFacingPillar(AetherIIBlocks.ICESTONE_CAPSTONE_PILLAR.get(), AetherIIBlocks.ICESTONE_KEYSTONE.get());
//        this.decorativeFacingPillar(AetherIIBlocks.ICESTONE_PILLAR.get(), AetherIIBlocks.ICESTONE_KEYSTONE.get());
//
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
//
//        // Arkenium Blocks
//        this.doorBlock(AetherIIBlocks.ARKENIUM_DOOR.get());
//        this.trapdoorBlock(AetherIIBlocks.ARKENIUM_TRAPDOOR.get(), this.texture(this.name(AetherIIBlocks.ARKENIUM_TRAPDOOR.get())), true);
//
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
//
//        // Utility
//        this.torchBlock(AetherIIBlocks.AMBROSIUM_TORCH.get(), AetherIIBlocks.AMBROSIUM_WALL_TORCH.get());
//        this.skyrootCraftingTable(AetherIIBlocks.SKYROOT_CRAFTING_TABLE.get(), AetherIIBlocks.SKYROOT_PLANKS.get());
//        this.holystoneFurnace(AetherIIBlocks.HOLYSTONE_FURNACE.get());
//        this.artisansBench(AetherIIBlocks.ARTISANS_BENCH.get());
//        this.altar(AetherIIBlocks.ALTAR.get());
//        this.arkeniumForge(AetherIIBlocks.ARKENIUM_FORGE.get());
//        this.skyrootChest(AetherIIBlocks.SKYROOT_CHEST.get());
//        this.skyrootLadder(AetherIIBlocks.SKYROOT_LADDER.get());
//        this.bed(AetherIIBlocks.SKYROOT_BED.get(), AetherIIBlocks.SKYROOT_PLANKS.get());
//
//        this.hangingSignBlock(AetherIIBlocks.SKYROOT_HANGING_SIGN.get(), AetherIIBlocks.SKYROOT_WALL_HANGING_SIGN.get(), this.texture(this.name(AetherIIBlocks.STRIPPED_SKYROOT_LOG.get())));
//        this.hangingSignBlock(AetherIIBlocks.GREATROOT_HANGING_SIGN.get(), AetherIIBlocks.GREATROOT_WALL_HANGING_SIGN.get(), this.texture(this.name(AetherIIBlocks.STRIPPED_SKYROOT_LOG.get())));
//        this.hangingSignBlock(AetherIIBlocks.WISPROOT_HANGING_SIGN.get(), AetherIIBlocks.WISPROOT_WALL_HANGING_SIGN.get(), this.texture(this.name(AetherIIBlocks.STRIPPED_SKYROOT_LOG.get())));
//
//        // Moa Egg
//        this.moaEgg(AetherIIBlocks.MOA_EGG.get());
//
//        // Bookshelves
//        this.bookshelf(AetherIIBlocks.SKYROOT_BOOKSHELF.get(), AetherIIBlocks.SKYROOT_PLANKS.get());
//        this.bookshelf(AetherIIBlocks.HOLYSTONE_BOOKSHELF.get(), AetherIIBlocks.HOLYSTONE_BRICKS.get());
//
//        // Furniture
//        this.campfire(AetherIIBlocks.OUTPOST_CAMPFIRE.get());
    }
}
