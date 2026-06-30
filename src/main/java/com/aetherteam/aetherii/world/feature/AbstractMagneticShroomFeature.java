package com.aetherteam.aetherii.world.feature;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.world.feature.configuration.BigMagneticShroomConfiguration;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
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

    public void generateSmallShroom(WorldGenLevel level, RandomSource random, BlockPos pos, BigMagneticShroomConfiguration config) {
        BlockPos.MutableBlockPos mutablePos = pos.mutable();
        this.generateStem(level, random, mutablePos, config, Direction.UP, UniformInt.of(1, 2));
        this.generateSmallCap(level, random, mutablePos, config);
    }

    public void generateLargeShroom(WorldGenLevel level, RandomSource random, BlockPos pos, BigMagneticShroomConfiguration config) {
        BlockPos.MutableBlockPos mutablePos = pos.mutable();
        this.generateStem(level, random, mutablePos, config, Direction.UP, UniformInt.of(3, 5));
        this.generateLargeCap(level, random, mutablePos, config);
    }

    public void generateMediumShroom(WorldGenLevel level, RandomSource random, BlockPos pos, BigMagneticShroomConfiguration config) {
        BlockPos.MutableBlockPos mutablePos = pos.mutable();
        this.generateStem(level, random, mutablePos, config, Direction.UP, UniformInt.of(2, 3));
        this.generateMediumCap(level, random, mutablePos, config);
    }

    public void generateStem(WorldGenLevel level, RandomSource random, BlockPos.MutableBlockPos pos, BigMagneticShroomConfiguration config, Direction direction, IntProvider length) {
        int max = length.sample(random);
        for (int i = 1; i <= max; i++) {
            this.setBlock(level, pos, config.stemProvider().getState(random, pos));
            pos.setWithOffset(pos, direction);
        }
    }

    public void generateSmallCap(WorldGenLevel level, RandomSource random, BlockPos.MutableBlockPos pos, BigMagneticShroomConfiguration config) {
        this.placeSquare(level, random, pos, config.bottomCapProvider(), 1, false);
        pos.setWithOffset(pos, Direction.UP);
        this.setBlock(level, pos, config.capProvider().getState(random, pos));
    }

    public void generateMediumCap(WorldGenLevel level, RandomSource random, BlockPos.MutableBlockPos pos, BigMagneticShroomConfiguration config) {
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

    public void placeGround(WorldGenLevel level, RandomSource random, BlockPos pos, BlockStateProvider provider) {
        this.placeGroundCircle(level, random, pos.west().north(), provider);
        this.placeGroundCircle(level, random, pos.east(2).north(), provider);
        this.placeGroundCircle(level, random, pos.west().south(2), provider);
        this.placeGroundCircle(level, random, pos.east(2).south(2), provider);
        for (int j = 0; j < 5; ++j) {
            int k = random.nextInt(64);
            int l = k % 8;
            int i1 = k / 8;
            if (l == 0 || l == 7 || i1 == 0 || i1 == 7) {
                this.placeGroundCircle(level, random, pos.offset(-3 + l, 0, -3 + i1), provider);
            }
        }
    }

    public void placeGroundCircle(WorldGenLevel level, RandomSource random, BlockPos pos, BlockStateProvider provider) {
        for (int i = -2; i <= 2; ++i) {
            for (int j = -2; j <= 2; ++j) {
                if (Math.abs(i) != 2 || Math.abs(j) != 2) {
                    this.placeGroundBlockAt(level, random, pos.offset(i, 0, j), provider);
                }
            }
        }
    }

    public void placeGroundBlockAt(WorldGenLevel level, RandomSource random, BlockPos pos,  BlockStateProvider provider) {
        for (int i = 2; i >= -3; --i) {
            BlockPos blockpos = pos.above(i);
            if (level.isStateAtPosition(blockpos, (state) -> state.is(AetherIITags.Blocks.AETHER_GROUND_BLOCKS))) {
                this.setBlock(level, blockpos, provider.getState(random, pos));
                break;
            }
            if (!level.getBlockState(blockpos).isAir() && i < 0) {
                break;
            }
        }
    }
}
