package com.aetherteam.aetherii.data.generators;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.data.providers.AetherIIBlockModelProvider;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.blockstates.Variant;
import net.minecraft.client.data.models.blockstates.VariantProperties;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.data.PackOutput;

public class AetherIIBlockModelData extends AetherIIBlockModelProvider {
    public AetherIIBlockModelData(PackOutput packOutput) {
        super(packOutput, AetherII.MODID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
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
        blockModels.createTrivialCube(AetherIIBlocks.HOLYSTONE.get());
        blockModels.createTrivialCube(AetherIIBlocks.UNSTABLE_HOLYSTONE.get());
        blockModels.createTrivialCube(AetherIIBlocks.UNDERSHALE.get());
        blockModels.createTrivialCube(AetherIIBlocks.UNSTABLE_UNDERSHALE.get());
        blockModels.createTrivialCube(AetherIIBlocks.AGIOSITE.get());
        blockModels.createTrivialCube(AetherIIBlocks.ICHORITE.get());
    }
}
