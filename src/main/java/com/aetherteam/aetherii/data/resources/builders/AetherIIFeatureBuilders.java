package com.aetherteam.aetherii.data.resources.builders;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.data.resources.registries.AetherIIDensityFunctions;
import com.aetherteam.aetherii.world.feature.configuration.AercloudConfiguration;
import com.aetherteam.aetherii.world.feature.configuration.CoastConfiguration;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class AetherIIFeatureBuilders {

    @SuppressWarnings("deprecation")
    public static CoastConfiguration createCoast(BlockState coastState, HolderGetter<DensityFunction> function) {
        return new CoastConfiguration(
                BlockStateProvider.simple(coastState),
                16.35F,
                AetherIIDensityFunctions.getFunction(function, AetherIIDensityFunctions.COASTS_HIGHFIELDS),
                UniformInt.of(112, 156),
                HolderSet.direct(Block::builtInRegistryHolder, AetherIIBlocks.AETHER_GRASS_BLOCK.get())
        );
    }

    public static AercloudConfiguration aercloud(int bounds, BlockState blockState) {
        return new AercloudConfiguration(bounds, BlockStateProvider.simple(blockState));
    }
}