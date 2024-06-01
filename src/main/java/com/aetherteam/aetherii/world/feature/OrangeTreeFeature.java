package com.aetherteam.aetherii.world.feature;

import com.aetherteam.aetherii.block.natural.OrangeTreeBlock;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;

public class OrangeTreeFeature extends Feature<SimpleBlockConfiguration> {
    public OrangeTreeFeature(Codec<SimpleBlockConfiguration> codec) {
        super(codec);
    }

    public boolean place(FeaturePlaceContext<SimpleBlockConfiguration> context) {
        SimpleBlockConfiguration simpleBlockConfiguration = context.config();
        WorldGenLevel worldGenLevel = context.level();
        BlockPos blockPos = context.origin();
        BlockState blockState = simpleBlockConfiguration.toPlace().getState(context.random(), blockPos);
        if (blockState.canSurvive(worldGenLevel, blockPos) && worldGenLevel.isEmptyBlock(blockPos.above())) {
            OrangeTreeBlock.placeAt(worldGenLevel, blockState, blockPos, 2);
            return true;
        } else {
            return false;
        }
    }
}