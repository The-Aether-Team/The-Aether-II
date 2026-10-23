package com.aetherteam.aetherii.world.feature;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.world.density.PerlinNoiseFunction;
import com.aetherteam.aetherii.world.feature.configuration.AlkahestNoiseLakeConfiguration;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PointedDripstoneBlock;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

import java.util.HashSet;
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
        int radius = config.radius().sample(context.random());

        for (int i = 1; i < config.height(); ++i) {
            placeAirLayer(i, level, pos, radius, config, noise, positions, context.random(), context.chunkGenerator());
        }
        for (int i = 6; i >= 0; --i) {
            placeLakeEdgeLayer(i, level, pos, radius, config, noise, positions);
        }
        for (int i = 5; i >= 0; --i) {
            placeAlkahestLayer(i, level, pos, radius, config, noise, positions, context.random());
        }

        return true;
    }

    public void placeLakeEdgeLayer(int i, WorldGenLevel level, BlockPos pos, int radius, AlkahestNoiseLakeConfiguration config, DensityFunction noise, Set<BlockPos> positions) {
        this.placeDisk(new BlockPos(pos.getX(), pos.getY() - i, pos.getZ()), radius, positions);

        for (BlockPos position : positions) {
            double density = noise.compute(new DensityFunction.SinglePointContext(position.getX(), position.getY(), position.getZ()));
            if (position.getY() == pos.getY() - i) {
                double densitySmoothed = density - Mth.clamp(position.distToCenterSqr(pos.getX(), pos.getY(), pos.getZ()) * config.inclineFactor() - radius * config.scatterFactor(), 0, 10);
                if (densitySmoothed > 0) {
                    if (!level.getBlockState(position).isAir()) {
                        level.setBlock(position, AetherIIBlocks.ICHORITE.get().defaultBlockState(), 2);
                    }
                }
            }
        }
    }

    public void placeAlkahestLayer(int i, WorldGenLevel level, BlockPos pos, int radius, AlkahestNoiseLakeConfiguration config, DensityFunction noise, Set<BlockPos> positions, RandomSource random) {
        this.placeDisk(new BlockPos(pos.getX(), pos.getY() - i, pos.getZ()), radius - 5, positions);

        for (BlockPos position : positions) {
            double density = noise.compute(new DensityFunction.SinglePointContext(position.getX(), position.getY(), position.getZ()));
            if (position.getY() == pos.getY() - i) {
                double densitySmoothed = density - Mth.clamp(position.distToCenterSqr(pos.getX(), pos.getY(), pos.getZ()) * config.inclineFactor() - (radius + 2) * config.scatterFactor(), 0, 10);
                if (densitySmoothed > 0) {
                    if (!level.getBlockState(position).isAir()) {
                        level.setBlock(position, AetherIIBlocks.ALKAHEST.get().defaultBlockState(), 2);
                        if (level.getBlockState(position.north()).isAir() || level.getBlockState(position.east()).isAir() || level.getBlockState(position.south()).isAir() || level.getBlockState(position.west()).isAir() || level.getBlockState(position.below()).isAir()) {
                            level.setBlock(position, AetherIIBlocks.ICHORITE.get().defaultBlockState(), 2);
                        }
                        if (random.nextFloat() < 0.01 && level.getBlockState(position.above()).isAir()) {
                            placeLakePlatform(3, 6, level, position, random);
                        }
                    }
                }
            }
        }
    }

    public void placeAirLayer(int i, WorldGenLevel level, BlockPos pos, int radius, AlkahestNoiseLakeConfiguration config, DensityFunction noise, Set<BlockPos> positions, RandomSource random, ChunkGenerator chunk) {
        this.placeDisk(new BlockPos(pos.getX(), pos.getY() + i, pos.getZ()), radius - 5 * i + 1, positions);

        for (BlockPos position : positions) {
            double density = noise.compute(new DensityFunction.SinglePointContext(position.getX(), position.getY(), position.getZ()));
            if (position.getY() == pos.getY() + i) {
                double densitySmoothed = density - Mth.clamp(position.distToCenterSqr(pos.getX(), pos.getY() + i, pos.getZ()) * config.inclineFactor() - (radius - 5 * i + 1) * config.scatterFactor(), 0, 10);
                if (densitySmoothed > 0) {
                    level.setBlock(position, Blocks.AIR.defaultBlockState(), 2);
                    if (random.nextFloat() < 0.1) {
                        if (!position.above().equals(positions)) {
                            AetherII.LOGGER.info("{}{}", level.getBlockState(position.above()), position.above());
                            growPointedIchorite(level, position, Direction.DOWN, 1 + random.nextInt(2), false);
                        }
                    }
                }
            }
        }
    }

    public void placeDisk(BlockPos center, float radius, Set<BlockPos> positions) {
        float radiusSq = radius * radius;
        this.placeProvidedBlock(center, positions);
        for (int z = 0; z < radius; z++) {
            for (int x = 0; x < radius; x++) {
                if (x * x + z * z > radiusSq) continue;
                this.placeProvidedBlock(center.offset(x, 0, z), positions);
                this.placeProvidedBlock(center.offset(-x, 0, -z), positions);
                this.placeProvidedBlock(center.offset(-z, 0, x), positions);
                this.placeProvidedBlock(center.offset(z, 0, -x), positions);
            }
        }
    }

    public void placeProvidedBlock(BlockPos pos, Set<BlockPos> positions) {
        positions.add(pos);
    }

    public void placeLakePlatform(int depth, int depthMiddle, WorldGenLevel level, BlockPos pos, RandomSource random) {
            for (int i = 0; i < depth; i++) {
                    BlockPos newPos = new BlockPos(pos.getX(), pos.getY() - i, pos.getZ());

                    level.setBlock(newPos.north(), AetherIIBlocks.ICHORITE.get().defaultBlockState(), 2);
                    level.setBlock(newPos.east(), AetherIIBlocks.ICHORITE.get().defaultBlockState(), 2);
                    level.setBlock(newPos.south(), AetherIIBlocks.ICHORITE.get().defaultBlockState(), 2);
                    level.setBlock(newPos.west(), AetherIIBlocks.ICHORITE.get().defaultBlockState(), 2);
                    level.setBlock(newPos.north().below(), AetherIIBlocks.ICHORITE.get().defaultBlockState(), 2);
                    level.setBlock(newPos.east().below(), AetherIIBlocks.ICHORITE.get().defaultBlockState(), 2);
                    level.setBlock(newPos.south().below(), AetherIIBlocks.ICHORITE.get().defaultBlockState(), 2);
                    level.setBlock(newPos.west().below(), AetherIIBlocks.ICHORITE.get().defaultBlockState(), 2);
                    if (random.nextBoolean()) {
                        level.setBlock(newPos.north().east(), AetherIIBlocks.ICHORITE.get().defaultBlockState(), 2);
                    }
                    if (random.nextBoolean()) {
                        level.setBlock(newPos.north().west(), AetherIIBlocks.ICHORITE.get().defaultBlockState(), 2);
                    }
                    if (random.nextBoolean()) {
                        level.setBlock(newPos.south().east(), AetherIIBlocks.ICHORITE.get().defaultBlockState(), 2);
                    }
                    if (random.nextBoolean()) {
                        level.setBlock(newPos.south().west(), AetherIIBlocks.ICHORITE.get().defaultBlockState(), 2);
                    }
                }
            for (int j = 0; j < depthMiddle; j++) {
                level.setBlock(new BlockPos(pos.getX(), pos.getY() - j, pos.getZ()), AetherIIBlocks.ICHORITE.get().defaultBlockState(), 2);
            }
    }

    public static void growPointedIchorite(LevelAccessor level, BlockPos pos, Direction direction, int height, boolean mergeTip) {
        if (AlkahestPoolFeature.isDripstoneBase(level.getBlockState(pos.relative(direction.getOpposite())))) {
            BlockPos.MutableBlockPos mutablePos = pos.mutable();
            AlkahestPoolFeature.buildBaseToTipColumn(direction, height, mergeTip, state -> {
                if (state.is(AetherIIBlocks.POINTED_ICHORITE.get())) {
                    state = state.setValue(PointedDripstoneBlock.WATERLOGGED, level.isWaterAt(mutablePos));
                }
                if (state.canSurvive(level, pos)) {
                    level.setBlock(mutablePos, state, 2);
                    mutablePos.move(direction);
                }
            });
        }
    }
}