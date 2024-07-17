package com.aetherteam.aetherii.data.resources.builders;

import com.aetherteam.aetherii.world.feature.AetherIIFeatures;
import com.aetherteam.aetherii.world.feature.configuration.AercloudConfiguration;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class AetherIIFeatureBuilders {
    public static RandomPatchConfiguration aetherGrassPatch(BlockStateProvider block, int tries) {
        return FeatureUtils.simpleRandomPatchConfiguration(tries, PlacementUtils.onlyWhenEmpty(AetherIIFeatures.AETHER_GRASS.get(), new SimpleBlockConfiguration(block)));
    }

    public static RandomPatchConfiguration brettlPatch(int tries, Holder<PlacedFeature> feature) {
        return new RandomPatchConfiguration(tries, 16, 4, feature);
    }

    public static AercloudConfiguration aercloud(int bounds, BlockState blockState) {
        return new AercloudConfiguration(bounds, BlockStateProvider.simple(blockState));
    }
}