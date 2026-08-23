package com.aetherteam.aetherii.world.feature;

import com.aetherteam.aetherii.data.resources.registries.holyisles.HolyIslesConfiguredFeatures;
import com.aetherteam.aetherii.world.density.PerlinNoiseFunction;
import com.aetherteam.aetherii.world.feature.configuration.CrystalIslandConfiguration;
import com.aetherteam.aetherii.world.feature.configuration.StructureCoverConfiguration;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.Mth;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

import java.util.HashSet;
import java.util.Locale;
import java.util.Objects;
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
            placeCrystalIsland(i, level, pos, context, config, noise, positions);
        }

        ConfiguredFeature<?, ?> turf = Objects.requireNonNull(level.registryAccess().lookupOrThrow(Registries.CONFIGURED_FEATURE).get(HolyIslesConfiguredFeatures.CRYSTAL_ISLAND_TURF).orElse(null)).value();
        turf.place(level, context.chunkGenerator(), context.random(), pos);

        ConfiguredFeature<?, ?> crystalroot = Objects.requireNonNull(level.registryAccess().lookupOrThrow(Registries.CONFIGURED_FEATURE).get(HolyIslesConfiguredFeatures.CRYSTALROOT).orElse(null)).value();
        crystalroot.place(level, context.chunkGenerator(), context.random(), pos);

        return true;
    }

    public void placeCrystalIsland(int i, WorldGenLevel level, BlockPos pos, FeaturePlaceContext<CrystalIslandConfiguration> context, CrystalIslandConfiguration config, DensityFunction noise, Set<BlockPos> positions) {
        this.placeDisk(level, new BlockPos(pos.getX(), pos.getY() - i, pos.getZ()), config.radius() - (float) (i / 2), positions);

        for (BlockPos position : positions) {
            double density = noise.compute(new DensityFunction.SinglePointContext(position.getX(), position.getY(), position.getZ()));
            if (position.getY() == pos.getY() - i) {
                double densitySmoothed = density - Mth.clamp(position.distToCenterSqr(pos.getX(), pos.getY() - i, pos.getZ()) * config.inclineFactor() - config.radius() * config.scatterFactor(), 0, 10);
                if (densitySmoothed > 0) {
                    level.setBlock(position, config.block().getState(level, context.random(), position), 2);
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