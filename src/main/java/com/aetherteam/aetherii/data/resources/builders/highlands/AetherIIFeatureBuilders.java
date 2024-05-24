package com.aetherteam.aetherii.data.resources.builders.highlands;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.world.feature.configuration.CoastConfiguration;
import net.minecraft.core.HolderSet;
import net.minecraft.util.valueproviders.ConstantFloat;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class AetherIIFeatureBuilders {

    //This was made a separate method to share code with Ferrosite Sand Coasts once these are implemented
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
}