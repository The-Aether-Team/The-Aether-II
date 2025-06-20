package com.aetherteam.aetherii.world.feature;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.world.feature.configuration.HugeMagneticShroomConfiguration;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.featuresize.FeatureSize;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HugeMagneticShroomFeature extends Feature<HugeMagneticShroomConfiguration> {
    public HugeMagneticShroomFeature(Codec<HugeMagneticShroomConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<HugeMagneticShroomConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos pos = context.origin().above();
        RandomSource random = context.random();
        HugeMagneticShroomConfiguration config = context.config();

        if (this.canPlace(level, random, pos, config)) {
            if (!config.large()) {
                this.generateSmallShroom(level, random, pos, config);
            } else {
                BlockPos.MutableBlockPos mutableBlockPos = pos.mutable();
//            if (random.nextBoolean()) {
//                this.generateStem(level, random, mutableBlockPos, config, Direction.UP, UniformInt.of(1, 2));
//                List<Direction> directions = new ArrayList<>(Direction.Plane.HORIZONTAL.stream().toList());
//                Collections.shuffle(directions);
//                for (Direction direction : Direction.Plane.HORIZONTAL) {
//                    if (random.nextBoolean()) {
//                        this.generateStem(level, random, mutableBlockPos, config, Direction.UP, UniformInt.of(1, 3));
//                        this.generateBranch(level, random, new BlockPos(mutableBlockPos).mutable(), direction, config);
//                    }
//                }
//                this.generateStem(level, random, mutableBlockPos, config, Direction.UP, UniformInt.of(1, 2));
//            }
                this.generateLargeShroom(level, random, mutableBlockPos, config);
            }
            return true;
        }
        return false;
    }

    public boolean canPlace(WorldGenLevel level, RandomSource random, BlockPos pos, HugeMagneticShroomConfiguration config) {
        int height = 7; //todo ?

        for (int y = 0; y <= height; ++y) {
            int i = config.minimumSize().getSizeAtHeight(height, y);

            for (int x = -i; x <= i; ++x) {
                for (int z = -i; z <= i; ++z) {
                    BlockPos checkPos = pos.offset(x, y, z);
                    if (!level.isStateAtPosition(checkPos, (blockstate) -> {
                        AetherII.LOGGER.info(String.valueOf(blockstate));
                        return blockstate.isAir();
                    })) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void generateBranch(WorldGenLevel level, RandomSource random, BlockPos.MutableBlockPos pos, Direction direction, HugeMagneticShroomConfiguration config) {
        this.generateStem(level, random, pos, config, direction, UniformInt.of(3, 4));
        this.generateSmallShroom(level, random, pos, config);
    }

    public void generateLargeShroom(WorldGenLevel level, RandomSource random, BlockPos pos, HugeMagneticShroomConfiguration config) {
        BlockPos.MutableBlockPos mutablePos = pos.mutable();
        this.generateStem(level, random, mutablePos, config, Direction.UP, UniformInt.of(3, 5));
        this.generateLargeCap(level, random, mutablePos, config);
    }

    public void generateSmallShroom(WorldGenLevel level, RandomSource random, BlockPos pos, HugeMagneticShroomConfiguration config) {
        BlockPos.MutableBlockPos mutablePos = pos.mutable();
        this.generateStem(level, random, mutablePos, config, Direction.UP, UniformInt.of(2, 3));
        this.generateSmallCap(level, random, mutablePos, config);
    }

    public void generateStem(WorldGenLevel level, RandomSource random, BlockPos.MutableBlockPos pos, HugeMagneticShroomConfiguration config, Direction direction, IntProvider length) {
        int max = length.sample(random);
        for (int i = 1; i <= max; i++) {
            pos.setWithOffset(pos, direction);
            this.setBlock(level, pos, config.stemProvider().getState(random, pos));
        }
    }

    public void generateSmallCap(WorldGenLevel level, RandomSource random, BlockPos.MutableBlockPos pos, HugeMagneticShroomConfiguration config) {
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            BlockPos offsetPos = pos.relative(direction);
            this.setBlock(level, offsetPos, config.stemProvider().getState(random, offsetPos));
        }
        pos.setWithOffset(pos, Direction.UP);
        this.placeSquare(level, random, pos, config.capProvider(), 2, true);
        this.placeSquare(level, random, pos, config.bottomCapProvider(), 1, false);
        pos.setWithOffset(pos, Direction.UP);
        this.placeSquare(level, random, pos, config.capProvider(), 1, false);
        pos.setWithOffset(pos, Direction.UP);
        this.placeSquare(level, random, pos, config.capProvider(), 1, true);
    }

    public void generateLargeCap(WorldGenLevel level, RandomSource random, BlockPos.MutableBlockPos pos, HugeMagneticShroomConfiguration config) {
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            for (int j = 1; j <= 2; j++) {
                BlockPos offsetPos = pos.relative(direction, j);
                this.setBlock(level, offsetPos, config.stemProvider().getState(random, offsetPos));
            }
        }
        pos.setWithOffset(pos, Direction.UP);
        this.placeSquare(level, random, pos, config.capProvider(), 3, true);
        this.placeSquare(level, random, pos, config.bottomCapProvider(), 2, true);
        pos.setWithOffset(pos, Direction.UP);
        this.placeSquare(level, random, pos, config.capProvider(), 2, true);
        pos.setWithOffset(pos, Direction.UP);
        this.placeSquare(level, random, pos, config.capProvider(), 1, false);
        pos.setWithOffset(pos, Direction.UP);
        this.placeSquare(level, random, pos, config.capProvider(), 1, true);
    }

    public void placeSquare(WorldGenLevel level, RandomSource random, BlockPos pos, BlockStateProvider state, int radius, boolean rounded) {
        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                BlockPos offsetPos = pos.offset(x, 0, z);
                if (!rounded || (Math.abs(x) != radius || Math.abs(z) != radius)) {
                    this.setBlock(level, offsetPos, state.getState(random, offsetPos));
                }
            }
        }
    }
}
