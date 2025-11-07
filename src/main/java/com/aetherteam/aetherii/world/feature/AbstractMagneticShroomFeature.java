package com.aetherteam.aetherii.world.feature;

import com.aetherteam.aetherii.world.feature.configuration.BigMagneticShroomConfiguration;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public abstract class AbstractMagneticShroomFeature extends Feature<BigMagneticShroomConfiguration> {
    public AbstractMagneticShroomFeature(Codec<BigMagneticShroomConfiguration> codec) {
        super(codec);
    }

    public void generateBranch(WorldGenLevel level, RandomSource random, BlockPos.MutableBlockPos pos, Direction direction, BigMagneticShroomConfiguration config) {
        this.generateStem(level, random, pos, config, direction, UniformInt.of(3, 4));
        this.generateSmallShroom(level, random, pos, config);
    }

    public void generateLargeShroom(WorldGenLevel level, RandomSource random, BlockPos pos, BigMagneticShroomConfiguration config) {
        BlockPos.MutableBlockPos mutablePos = pos.mutable();
        this.generateStem(level, random, mutablePos, config, Direction.UP, UniformInt.of(3, 5));
        this.generateLargeCap(level, random, mutablePos, config);
    }

    public void generateSmallShroom(WorldGenLevel level, RandomSource random, BlockPos pos, BigMagneticShroomConfiguration config) {
        BlockPos.MutableBlockPos mutablePos = pos.mutable();
        this.generateStem(level, random, mutablePos, config, Direction.UP, UniformInt.of(2, 3));
        this.generateSmallCap(level, random, mutablePos, config);
    }

    public void generateStem(WorldGenLevel level, RandomSource random, BlockPos.MutableBlockPos pos, BigMagneticShroomConfiguration config, Direction direction, IntProvider length) {
        int max = length.sample(random);
        for (int i = 1; i <= max; i++) {
            pos.setWithOffset(pos, direction);
            this.setBlock(level, pos, config.stemProvider().getState(random, pos));
        }
    }

    public void generateSmallCap(WorldGenLevel level, RandomSource random, BlockPos.MutableBlockPos pos, BigMagneticShroomConfiguration config) {
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

    public void generateLargeCap(WorldGenLevel level, RandomSource random, BlockPos.MutableBlockPos pos, BigMagneticShroomConfiguration config) {
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
