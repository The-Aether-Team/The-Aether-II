package com.aetherteam.aetherii.world.feature;

import com.aetherteam.aetherii.world.feature.configuration.ArilumConfiguration;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.KelpBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public class ArilumFeature extends Feature<ArilumConfiguration> {
    public ArilumFeature(Codec<ArilumConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<ArilumConfiguration> context) {
        int i = 0;
        WorldGenLevel level = context.level();
        BlockPos pos = context.origin();
        RandomSource random = context.random();
        int height = context.config().height().sample(random);
        int depth = context.config().depth().sample(random);
        if (level.getBlockState(pos).is(Blocks.WATER) && level.getBlockState(pos.above(depth)).is(Blocks.WATER)) {
            BlockState endState = context.config().grassProvider().getState(random, pos);
            BlockState bodyState = context.config().plantProvider().getState(random, pos);
            for (int l = 0; l <= height; l++) {
                if (level.getBlockState(pos).is(Blocks.WATER) && bodyState.canSurvive(level, pos)) {
                    if (l == height) {
                        level.setBlock(pos, endState.setValue(KelpBlock.AGE, random.nextInt(2) + 23), 2);
                        i++;
                    } else {
                        level.setBlock(pos, bodyState, 2);
                    }
                } else if (l > 0) {
                    BlockPos belowPos = pos.below();
                    if (endState.canSurvive(level, belowPos) && !level.getBlockState(belowPos.below()).is(endState.getBlock())) {
                        level.setBlock(belowPos, endState.setValue(KelpBlock.AGE, random.nextInt(2) + 23), 2);
                        i++;
                    }
                    break;
                }

                pos = pos.above();
            }
        }

        return i > 0;
    }
}
