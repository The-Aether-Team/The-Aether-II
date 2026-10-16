package com.aetherteam.aetherii.world.feature;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.world.density.PerlinNoiseFunction;
import com.aetherteam.aetherii.world.feature.configuration.AlkahestNoiseLakeConfiguration;
import com.aetherteam.aetherii.world.feature.configuration.StructureCoverConfiguration;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class AlkahestNoiseLakeFeature extends Feature<AlkahestNoiseLakeConfiguration> {

    public AlkahestNoiseLakeFeature(Codec<AlkahestNoiseLakeConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<AlkahestNoiseLakeConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos pos = context.origin().below(2);
        AlkahestNoiseLakeConfiguration config = context.config();
        Set<BlockPos> positions = new HashSet<>();

        DensityFunction noise = config.noise();
        DensityFunction.Visitor visitor = PerlinNoiseFunction.createOrGetVisitor(level.getSeed());
        noise.mapAll(visitor);

        for (int i = 1; i < config.height(); ++i) {
            placeAirLayer(i, level, pos, context, config, noise, positions);
        }
        for (int i = 6; i >= 0; --i) {
            placeLakeEdgeLayer(i, level, pos, context, config, noise, positions);
        }
        for (int i = 5; i >= 0; --i) {
            placeAlkahestLayer(i, level, pos, context, config, noise, positions);
        }

        return true;
    }

    public void placeLakeEdgeLayer(int i, WorldGenLevel level, BlockPos pos, FeaturePlaceContext<AlkahestNoiseLakeConfiguration> context, AlkahestNoiseLakeConfiguration config, DensityFunction noise, Set<BlockPos> positions) {
        this.placeDisk(level, new BlockPos(pos.getX(), pos.getY() - i, pos.getZ()), config.radius(), positions);

        for (BlockPos position : positions) {
            double density = noise.compute(new DensityFunction.SinglePointContext(position.getX(), position.getY(), position.getZ()));
            if (position.getY() == pos.getY() - i) {
                double densitySmoothed = density - Mth.clamp(position.distToCenterSqr(pos.getX(), pos.getY(), pos.getZ()) * config.inclineFactor() - config.radius() * config.scatterFactor(), 0, 10);
                if (densitySmoothed > 0) {
                    if (!level.getBlockState(position).isAir()) {
                        level.setBlock(position, AetherIIBlocks.ICHORITE.get().defaultBlockState(), 2);
                    }
                }
            }
        }
    }

    public void placeAlkahestLayer(int i, WorldGenLevel level, BlockPos pos, FeaturePlaceContext<AlkahestNoiseLakeConfiguration> context, AlkahestNoiseLakeConfiguration config, DensityFunction noise, Set<BlockPos> positions) {
        this.placeDisk(level, new BlockPos(pos.getX(), pos.getY() - i, pos.getZ()), config.radius() - 5, positions);

        for (BlockPos position : positions) {
            double density = noise.compute(new DensityFunction.SinglePointContext(position.getX(), position.getY(), position.getZ()));
            if (position.getY() == pos.getY() - i) {
                double densitySmoothed = density - Mth.clamp(position.distToCenterSqr(pos.getX(), pos.getY(), pos.getZ()) * config.inclineFactor() - (config.radius() - 5) * config.scatterFactor(), 0, 10);
                if (densitySmoothed > 0) {
                    if (!level.getBlockState(position).isAir()) {
                        level.setBlock(position, AetherIIBlocks.ALKAHEST.get().defaultBlockState(), 2);
                        if (level.getBlockState(position.north()).isAir() || level.getBlockState(position.east()).isAir() || level.getBlockState(position.south()).isAir() || level.getBlockState(position.west()).isAir() || level.getBlockState(position.below()).isAir()) {
                            level.setBlock(position, AetherIIBlocks.ICHORITE.get().defaultBlockState(), 2);
                        }
                    }
                }
            }
        }
    }

    public void placeAirLayer(int i, WorldGenLevel level, BlockPos pos, FeaturePlaceContext<AlkahestNoiseLakeConfiguration> context, AlkahestNoiseLakeConfiguration config, DensityFunction noise, Set<BlockPos> positions) {
        this.placeDisk(level, new BlockPos(pos.getX(), pos.getY() + i, pos.getZ()), config.radius(), positions);

        for (BlockPos position : positions) {
            double density = noise.compute(new DensityFunction.SinglePointContext(position.getX(), position.getY(), position.getZ()));
            if (position.getY() == pos.getY() + i) {
                double densitySmoothed = density - Mth.clamp(position.distToCenterSqr(pos.getX(), pos.getY() + i, pos.getZ()) * config.inclineFactor() - config.radius() * config.scatterFactor(), 0, 10);
                if (densitySmoothed > 0) {
                    level.setBlock(position, Blocks.AIR.defaultBlockState(), 2);
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
        positions.add(pos);
    }
}