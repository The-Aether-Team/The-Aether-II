package com.aetherteam.aetherii.world.feature;

import com.aetherteam.aetherii.world.feature.configuration.FallenLogConfiguration;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public class FallenLogFeature extends Feature<FallenLogConfiguration> {
    public FallenLogFeature(Codec<FallenLogConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<FallenLogConfiguration> context) {
        WorldGenLevel level = context.level();
        RandomSource random = context.random();
        BlockPos pos = context.origin();
        FallenLogConfiguration config = context.config();

        int length = config.length().sample(random);
        Direction direction = Direction.from2DDataValue(random.nextInt(4));

        for (int i = 0; i < length; i++) {
            BlockPos placementPos = pos.relative(direction, i);
            BlockState blockState = config.block().getState(random, placementPos);
            if ((level.getBlockState(placementPos).canBeReplaced() || level.getBlockState(placementPos).liquid()) && level.getBlockState(placementPos.below()).is(config.validBlocks())) {
                if (blockState.getOptionalValue(BlockStateProperties.AXIS).isPresent()) {
                    blockState = blockState.setValue(BlockStateProperties.AXIS, direction.getAxis());
                    level.setBlock(placementPos, blockState, 2);
                    if (config.vegetationChance() > 0.0F && random.nextFloat() < config.vegetationChance()) {
                        config.vegetationFeature().ifPresent(placedFeatureHolder -> placedFeatureHolder.value().place(level, context.chunkGenerator(), random, placementPos));
                    }
                }
            }
        }

        return false;
    }
}
