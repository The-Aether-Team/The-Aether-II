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
        SimpleBlockConfiguration config = context.config();
        WorldGenLevel level = context.level();
        BlockPos pos = context.origin();
        BlockState state = config.toPlace().getState(context.random(), pos);

        if (state.canSurvive(level, pos) && level.isEmptyBlock(pos.above())) {
            OrangeTreeBlock.placeAt(level, state, pos, 2);
            return true;
        } else {
            return false;
        }
    }
}