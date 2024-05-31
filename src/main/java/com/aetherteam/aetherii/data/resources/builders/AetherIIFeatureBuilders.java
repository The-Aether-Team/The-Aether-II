package com.aetherteam.aetherii.data.resources.builders;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.world.feature.CloudLayerFeature;
import com.aetherteam.aetherii.world.feature.configuration.AercloudConfiguration;
import com.aetherteam.aetherii.world.feature.configuration.CoastConfiguration;
import net.minecraft.core.HolderSet;
import net.minecraft.util.valueproviders.ConstantFloat;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class AetherIIFeatureBuilders {

    @SuppressWarnings("deprecation")
    public static CoastConfiguration createCoast(BlockState coastState, int minHeight, int maxHeight) {
        return new CoastConfiguration(
                BlockStateProvider.simple(coastState),
                ConstantFloat.of(6.63F),
                ConstantFloat.of(4.46F),
                UniformInt.of(minHeight, maxHeight),
                HolderSet.direct(Block::builtInRegistryHolder, AetherIIBlocks.AETHER_GRASS_BLOCK.get())
        );
    }

    public static AercloudConfiguration aercloud(int bounds, BlockState blockState) {
        return new AercloudConfiguration(bounds, BlockStateProvider.simple(blockState));
    }

    public static CloudLayerFeature.Config cloudLayer(BlockState blockState, BlockPredicate predicate, int yLevel, double xzScale, double yScale, double noiseOffset) {
        return new CloudLayerFeature.Config(BlockStateProvider.simple(blockState), predicate, yLevel, xzScale, yScale, noiseOffset);
    }
}