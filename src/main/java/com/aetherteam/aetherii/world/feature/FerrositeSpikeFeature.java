package com.aetherteam.aetherii.world.feature;

import com.aetherteam.aetherii.world.density.PerlinNoiseFunction;
import com.aetherteam.aetherii.world.feature.configuration.FerrositeSpikeConfiguration;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

import java.util.HashSet;
import java.util.Set;

public class FerrositeSpikeFeature extends Feature<FerrositeSpikeConfiguration> {
    public FerrositeSpikeFeature(Codec<FerrositeSpikeConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<FerrositeSpikeConfiguration> context) {
        WorldGenLevel level = context.level();
        RandomSource random = context.random();
        BlockPos pos = context.origin().below(2);
        FerrositeSpikeConfiguration config = context.config();

        Set<BlockPos> positions = new HashSet<>();

        DensityFunction noise = config.noise();
        DensityFunction.Visitor visitor = PerlinNoiseFunction.createOrGetVisitor(level.getSeed());
        noise.mapAll(visitor);
        double radius = config.radius().sample(random);

        for (int i = 0; i < radius * 3; ++i) {
            double slopedRadius = radius - i * 0.33;
            this.placeSpike(i, (float) slopedRadius, level, pos, context, config, noise, positions, false);
        }
        for (int i = (int) (radius * 0.5); i > 0; --i) {
            double slopedRadius = radius - i * 2;
            this.placeSpike(i, (float) slopedRadius, level, pos, context, config, noise, positions, true);
        }

        return true;
    }

    public void placeSpike(int i, float radius, WorldGenLevel level, BlockPos pos, FeaturePlaceContext<FerrositeSpikeConfiguration> context, FerrositeSpikeConfiguration config, DensityFunction noise, Set<BlockPos> positions, boolean underside) {
        int j = underside ? -1 : 1;
        this.placeDisk(config, level, new BlockPos(pos.getX(), pos.getY() + i * j, pos.getZ()), radius, positions);

        if (this.groundChecks(level, pos, positions)) {
            for (BlockPos position : positions) {
                double density = noise.compute(new DensityFunction.SinglePointContext(position.getX(), position.getY(), position.getZ()));
                if (position.getY() == pos.getY() + i * j) {
                    double densitySmoothed = density - Mth.clamp(position.distToCenterSqr(pos.getX(), pos.getY() + i * j, pos.getZ()) * config.inclineFactor() - radius * config.scatterFactor(), 0, 10);
                    if (densitySmoothed > 0) {
                        if (radius > 1) {
                            level.setBlock(position, config.state().getState(level, context.random(), position), 2);
                        }
                    }
                }
            }
        }
    }

    public void placeDisk(FerrositeSpikeConfiguration config, WorldGenLevel level, BlockPos center, float radius, Set<BlockPos> positions) {
        float radiusSq = radius * radius;
        this.placeProvidedBlock(config, level, center, positions);
        for (int z = 0; z < radius; z++) {
            for (int x = 0; x < radius; x++) {
                if (x * x + z * z > radiusSq) continue;
                this.placeProvidedBlock(config, level, center.offset(x, 0, z), positions);
                this.placeProvidedBlock(config, level, center.offset(-x, 0, -z), positions);
                this.placeProvidedBlock(config, level, center.offset(-z, 0, x), positions);
                this.placeProvidedBlock(config, level, center.offset(z, 0, -x), positions);
            }
        }
    }

    public void placeProvidedBlock(FerrositeSpikeConfiguration config, WorldGenLevel level, BlockPos pos, Set<BlockPos> positions) {
        BlockState state = level.getBlockState(pos);
        if (!state.isSolid() || state.is(config.validBlocks())) {
            positions.add(pos);
        }
    }

    public boolean groundChecks(WorldGenLevel level, BlockPos pos, Set<BlockPos> positions) {
        for (BlockPos position : positions) {
            if (position.getY() == pos.getY()) {
                if (!level.getBlockState(position.below(5)).isSolid()) {
                    return false;
                }
            }
        }
        return true;
    }
}