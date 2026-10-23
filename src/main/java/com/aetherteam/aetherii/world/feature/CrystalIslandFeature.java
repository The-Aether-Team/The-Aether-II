package com.aetherteam.aetherii.world.feature;

import com.aetherteam.aetherii.world.density.PerlinNoiseFunction;
import com.aetherteam.aetherii.world.feature.configuration.CrystalIslandConfiguration;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

import java.util.HashSet;
import java.util.Set;

public class CrystalIslandFeature extends Feature<CrystalIslandConfiguration> {
    public CrystalIslandFeature(Codec<CrystalIslandConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<CrystalIslandConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos pos = context.origin().below(2);
        CrystalIslandConfiguration config = context.config();
        Set<BlockPos> positions = new HashSet<>();

        DensityFunction noise = config.noise();
        DensityFunction.Visitor visitor = PerlinNoiseFunction.createOrGetVisitor(level.getSeed());
        noise.mapAll(visitor);

        for (int i = config.height(); i > 0; --i) {
            this.placeCrystalIsland(i, level, pos, context, config, noise, positions);
        }

        config.treeFeature().value().place(level, context.chunkGenerator(), context.random(), pos);
        config.turfFeature().value().place(level, context.chunkGenerator(), context.random(), pos);

        return true;
    }

    public void placeCrystalIsland(int i, WorldGenLevel level, BlockPos pos, FeaturePlaceContext<CrystalIslandConfiguration> context, CrystalIslandConfiguration config, DensityFunction noise, Set<BlockPos> positions) {
        this.placeDisk(level, new BlockPos(pos.getX(), pos.getY() - i, pos.getZ()), config.radius() - (float) (i / 2), positions);

        for (BlockPos position : positions) {
            double density = noise.compute(new DensityFunction.SinglePointContext(position.getX(), position.getY(), position.getZ()));
            if (position.getY() == pos.getY() - i) {
                double densitySmoothed = density - Mth.clamp(position.distToCenterSqr(pos.getX(), pos.getY() - i, pos.getZ()) * config.inclineFactor() - config.radius() * config.scatterFactor(), 0, 10);
                if (densitySmoothed > 0) {
                    level.setBlock(position, config.baseState().getState(level, context.random(), position), 2);
                    BlockState oreState = config.oreState().getState(level, context.random(), pos);
                    if (!oreState.isAir()) {
                        level.setBlock(position, oreState, 2);
                    }
                }
            }
        }
    }

    public void placeDisk(WorldGenLevel level, BlockPos center, float radius, Set<BlockPos> positions) {
        float radiusSq = radius * radius;
        this.placeProvidedBlock(level, center, positions);
        for (int z = 0; z < radius; z++) {
            for (int x = 0; x < radius; x++) {
                if (x * x + z * z > radiusSq) continue;
                this.placeProvidedBlock(level, center.offset(x, 0, z), positions);
                this.placeProvidedBlock(level, center.offset(-x, 0, -z), positions);
                this.placeProvidedBlock(level, center.offset(-z, 0, x), positions);
                this.placeProvidedBlock(level, center.offset(z, 0, -x), positions);
            }
        }
    }

    public void placeProvidedBlock(WorldGenLevel level, BlockPos pos, Set<BlockPos> positions) {
        if (level.getBlockState(pos).isAir()) {
            positions.add(pos);
        }
    }
}