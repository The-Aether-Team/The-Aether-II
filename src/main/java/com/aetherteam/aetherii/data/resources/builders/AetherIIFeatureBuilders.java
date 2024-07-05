package com.aetherteam.aetherii.data.resources.builders;

import com.aetherteam.aetherii.world.feature.configuration.AercloudConfiguration;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class AetherIIFeatureBuilders {

    public static AercloudConfiguration aercloud(int bounds, BlockState blockState) {
        return new AercloudConfiguration(bounds, BlockStateProvider.simple(blockState));
    }
}