package com.aetherteam.aetherii.world;

import com.aetherteam.aetherii.world.feature.configuration.HedgeConfiguration;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class HedgeFeature extends Feature<HedgeConfiguration> {
    public HedgeFeature(Codec<HedgeConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<HedgeConfiguration> context) {
        HedgeConfiguration config = context.config();
        WorldGenLevel level = context.level();
        RandomSource random = context.random();
        BlockPos originPos = context.origin();
        int length = config.size().sample(random);

        int rotation = random.nextBoolean() ? random.nextInt(60) + 15 : random.nextInt(60) + 105;
        if (random.nextBoolean()) {
            rotation *= -1;
        }

        int xOffset = Math.round(length * Mth.sin(rotation * Mth.DEG_TO_RAD));
        int zOffset = Math.round(length * Mth.cos(rotation * Mth.DEG_TO_RAD));
        int distance = originPos.distChessboard(originPos.offset(xOffset, 0, zOffset));

        List<BlockPos> basePositions = new ArrayList<>();
        Direction xDirection = Direction.getApproximateNearest(new Vec3(xOffset, 0, 0));
        Direction zDirection = Direction.getApproximateNearest(new Vec3(0, 0, zOffset));
        Direction[] offsetDirections = { xDirection, zDirection };

        for (int i = 0; i < distance; i++) {
            int x = Math.round(((float) i / distance) * xOffset);
            int z = Math.round(((float) i / distance) * zOffset);

            BlockPos newPos = originPos.offset(x, 0, z);

            if (!level.getBlockState(newPos.below()).isSolid()) {
                if (level.getBlockState(newPos.below(2)).isSolid()) {
                    originPos = originPos.below();
                    newPos = newPos.below();
                } else {
                    break;
                }
            } else  if (level.getBlockState(newPos).isSolid()) {
                 if (!level.getBlockState(newPos.above()).isSolid()) {
                     originPos = originPos.above();
                     newPos = newPos.above();
                } else {
                    break;
                }
            }
            basePositions.add(newPos);
        }

        if (basePositions.size() > 2) {
            int distanceShift = 0;
            for (int i = 0; i < basePositions.size(); i++) {
                BlockPos basePos = basePositions.get(i);
                int max = basePositions.size() - 1;
                float half = max / 2.0F;

                int mainDistToCenter = Mth.ceil(Math.abs(i - half)) + distanceShift;
                if (random.nextBoolean()) {
                    distanceShift = random.nextBoolean() ? -1 : 1;
                }
                int height = Mth.floor((basePositions.size() / 2.0F) - mainDistToCenter);

                for (int above = 0; above <= height; above++) {
                    BlockPos abovePos = basePos.above(above);
                    this.setBlock(level, abovePos, config.block().getState(level, random, abovePos));
                }

                for (Direction offsetDirection : offsetDirections) {
                    if (i < max) {
                        BlockPos offsetPos = basePos.relative(offsetDirection);
                        height = height - 1;

                        for (int above = 0; above <= height; above++) {
                            if (level.getBlockState(offsetPos.below(2)).isSolid()) {
                                BlockPos abovePos = offsetPos.above(above);
                                this.setBlock(level, abovePos, config.block().getState(level, random, abovePos));
                                if (above == 0 && !level.getBlockState(offsetPos.below()).isSolid()) {
                                    BlockPos belowPos = offsetPos.below();
                                    this.setBlock(level, belowPos, config.block().getState(level, random, belowPos));
                                }
                            }
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
}
