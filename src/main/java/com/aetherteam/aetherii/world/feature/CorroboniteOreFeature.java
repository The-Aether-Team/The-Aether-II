package com.aetherteam.aetherii.world.feature;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.block.natural.CorroboniteClusterBlock;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.SectionPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.BulkSectionAccess;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;

import java.util.BitSet;
import java.util.function.Function;

public class CorroboniteOreFeature extends Feature<OreConfiguration> { //todo config
    public CorroboniteOreFeature(Codec<OreConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<OreConfiguration> context) {
        RandomSource randomsource = context.random();
        BlockPos blockpos = context.origin();
        WorldGenLevel worldgenlevel = context.level();
        OreConfiguration oreconfiguration = context.config();
        float f = randomsource.nextFloat() * (float) Math.PI;
        float f1 = (float)oreconfiguration.size / 8.0F;
        int i = Mth.ceil(((float)oreconfiguration.size / 16.0F * 2.0F + 1.0F) / 2.0F);
        double d0 = (double)blockpos.getX() + Math.sin(f) * (double)f1;
        double d1 = (double)blockpos.getX() - Math.sin(f) * (double)f1;
        double d2 = (double)blockpos.getZ() + Math.cos(f) * (double)f1;
        double d3 = (double)blockpos.getZ() - Math.cos(f) * (double)f1;
        int j = 2;
        double d4 = blockpos.getY() + randomsource.nextInt(3) - 2;
        double d5 = blockpos.getY() + randomsource.nextInt(3) - 2;
        int k = blockpos.getX() - Mth.ceil(f1) - i;
        int l = blockpos.getY() - 2 - i;
        int i1 = blockpos.getZ() - Mth.ceil(f1) - i;
        int j1 = 2 * (Mth.ceil(f1) + i);
        int k1 = 2 * (2 + i);

        for (int l1 = k; l1 <= k + j1; l1++) {
            for (int i2 = i1; i2 <= i1 + j1; i2++) {
                if (l <= worldgenlevel.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, l1, i2)) {
                    return this.doPlace(worldgenlevel, randomsource, oreconfiguration, d0, d1, d2, d3, d4, d5, k, l, i1, j1, k1);
                }
            }
        }

        return false;
    }

    protected boolean doPlace(
            WorldGenLevel level,
            RandomSource random,
            OreConfiguration config,
            double minX,
            double maxX,
            double minZ,
            double maxZ,
            double minY,
            double maxY,
            int x,
            int y,
            int z,
            int width,
            int height
    ) {
        int i = 0;
        BitSet bitset = new BitSet(width * height * width);
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
        int j = config.size;
        double[] adouble = new double[j * 4];

        for (int k = 0; k < j; k++) {
            float f = (float)k / (float)j;
            double d0 = Mth.lerp(f, minX, maxX);
            double d1 = Mth.lerp(f, minY, maxY);
            double d2 = Mth.lerp(f, minZ, maxZ);
            double d3 = random.nextDouble() * (double)j / 16.0;
            double d4 = ((double)(Mth.sin((float) Math.PI * f) + 1.0F) * d3 + 1.0) / 2.0;
            adouble[k * 4 + 0] = d0;
            adouble[k * 4 + 1] = d1;
            adouble[k * 4 + 2] = d2;
            adouble[k * 4 + 3] = d4;
        }

        for (int l3 = 0; l3 < j - 1; l3++) {
            if (!(adouble[l3 * 4 + 3] <= 0.0)) {
                for (int i4 = l3 + 1; i4 < j; i4++) {
                    if (!(adouble[i4 * 4 + 3] <= 0.0)) {
                        double d8 = adouble[l3 * 4 + 0] - adouble[i4 * 4 + 0];
                        double d10 = adouble[l3 * 4 + 1] - adouble[i4 * 4 + 1];
                        double d12 = adouble[l3 * 4 + 2] - adouble[i4 * 4 + 2];
                        double d14 = adouble[l3 * 4 + 3] - adouble[i4 * 4 + 3];
                        if (d14 * d14 > d8 * d8 + d10 * d10 + d12 * d12) {
                            if (d14 > 0.0) {
                                adouble[i4 * 4 + 3] = -1.0;
                            } else {
                                adouble[l3 * 4 + 3] = -1.0;
                            }
                        }
                    }
                }
            }
        }

        try (BulkSectionAccess bulksectionaccess = new BulkSectionAccess(level)) {
            for (int j4 = 0; j4 < j; j4++) {
                double d9 = adouble[j4 * 4 + 3];
                if (!(d9 < 0.0)) {
                    double d11 = adouble[j4 * 4 + 0];
                    double d13 = adouble[j4 * 4 + 1];
                    double d15 = adouble[j4 * 4 + 2];
                    int k4 = Math.max(Mth.floor(d11 - d9), x);
                    int l = Math.max(Mth.floor(d13 - d9), y);
                    int i1 = Math.max(Mth.floor(d15 - d9), z);
                    int j1 = Math.max(Mth.floor(d11 + d9), k4);
                    int k1 = Math.max(Mth.floor(d13 + d9), l);
                    int l1 = Math.max(Mth.floor(d15 + d9), i1);

                    for (int i2 = k4; i2 <= j1; i2++) {
                        double d5 = ((double)i2 + 0.5 - d11) / d9;
                        if (d5 * d5 < 1.0) {
                            for (int j2 = l; j2 <= k1; j2++) {
                                double d6 = ((double)j2 + 0.5 - d13) / d9;
                                if (d5 * d5 + d6 * d6 < 1.0) {
                                    for (int k2 = i1; k2 <= l1; k2++) {
                                        double d7 = ((double)k2 + 0.5 - d15) / d9;
                                        if (d5 * d5 + d6 * d6 + d7 * d7 < 1.0 && !level.isOutsideBuildHeight(j2)) {
                                            int l2 = i2 - x + (j2 - y) * width + (k2 - z) * width * height;
                                            if (!bitset.get(l2)) {
                                                bitset.set(l2);
                                                blockpos$mutableblockpos.set(i2, j2, k2);
                                                if (level.ensureCanWrite(blockpos$mutableblockpos)) {
                                                    LevelChunkSection levelchunksection = bulksectionaccess.getSection(blockpos$mutableblockpos);
                                                    if (levelchunksection != null) {
                                                        int i3 = SectionPos.sectionRelative(i2);
                                                        int j3 = SectionPos.sectionRelative(j2);
                                                        int k3 = SectionPos.sectionRelative(k2);
                                                        BlockState blockstate = levelchunksection.getBlockState(i3, j3, k3);

                                                        for (OreConfiguration.TargetBlockState oreconfiguration$targetblockstate : config.targetStates) {
                                                            if (canPlaceOre(
                                                                    blockstate,
                                                                    bulksectionaccess::getBlockState,
                                                                    random,
                                                                    config,
                                                                    oreconfiguration$targetblockstate,
                                                                    blockpos$mutableblockpos
                                                            )) {
                                                                this.placeOreBlock(bulksectionaccess, levelchunksection, bulksectionaccess::getBlockState, blockpos$mutableblockpos, i3, j3, k3, oreconfiguration$targetblockstate);
                                                                i++;
                                                                break;
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return i > 0;
    }

    public void placeOreBlock(BulkSectionAccess bulksectionaccess, LevelChunkSection levelchunksection, Function<BlockPos, BlockState> adjacentStateAccessor, BlockPos.MutableBlockPos mutablePos, int x, int y, int z, OreConfiguration.TargetBlockState oreconfiguration$targetblockstate) {
        BlockPos blockPos = new BlockPos(x, y, z);
        levelchunksection.setBlockState(blockPos.getX(), blockPos.getY(), blockPos.getZ(), oreconfiguration$targetblockstate.state, false);


        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

        for (Direction direction : Direction.values()) {
            blockpos$mutableblockpos.setWithOffset(mutablePos, direction);
            if (adjacentStateAccessor.apply(blockpos$mutableblockpos).isAir()) {
                LevelChunkSection offsetsection = bulksectionaccess.getSection(blockpos$mutableblockpos);
                if (offsetsection != null) {
                    int i3 = SectionPos.sectionRelative(blockpos$mutableblockpos.getX());
                    int j3 = SectionPos.sectionRelative(blockpos$mutableblockpos.getY());
                    int k3 = SectionPos.sectionRelative(blockpos$mutableblockpos.getZ());
                    offsetsection.setBlockState(i3, j3, k3, AetherIIBlocks.CORROBONITE_CLUSTER.get().defaultBlockState().setValue(CorroboniteClusterBlock.FACING, direction), false);
                }
            }
        }
    }

    public static boolean canPlaceOre(
            BlockState state,
            Function<BlockPos, BlockState> adjacentStateAccessor,
            RandomSource random,
            OreConfiguration config,
            OreConfiguration.TargetBlockState targetState,
            BlockPos.MutableBlockPos mutablePos
    ) {
        if (!targetState.target.test(state, random)) {
            return false;
        } else {
            return shouldSkipAirCheck(random, config.discardChanceOnAirExposure) || !isAdjacentToAir(adjacentStateAccessor, mutablePos);
        }
    }

    protected static boolean shouldSkipAirCheck(RandomSource random, float chance) {
        if (chance <= 0.0F) {
            return true;
        } else {
            return !(chance >= 1.0F) && random.nextFloat() >= chance;
        }
    }
}