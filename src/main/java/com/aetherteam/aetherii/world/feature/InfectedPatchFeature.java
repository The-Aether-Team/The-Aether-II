package com.aetherteam.aetherii.world.feature;

import com.aetherteam.aetherii.data.resources.maps.BlockInfection;
import com.aetherteam.aetherii.data.resources.registries.AetherIIDataMaps;
import com.aetherteam.aetherii.world.feature.configuration.InfectedPatchConfiguration;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

public class InfectedPatchFeature extends Feature<InfectedPatchConfiguration> { //TODO: clean-up, remove unused parameters
    public InfectedPatchFeature(Codec<InfectedPatchConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<InfectedPatchConfiguration> context) {
        WorldGenLevel level = context.level();
        InfectedPatchConfiguration config = context.config();
        RandomSource random = context.random();
        BlockPos pos = context.origin();
        Predicate<BlockState> predicate = state -> state.is(config.replaceable());
        int i = config.xzRadius().sample(random) + 1;
        int j = config.xzRadius().sample(random) + 1;
        Set<BlockPos> set = this.placeGroundPatch(level, config, random, pos, predicate, i, j);
        this.distributeVegetation(context, level, config, random, set, i, j);
        return !set.isEmpty();
    }

    protected Set<BlockPos> placeGroundPatch(WorldGenLevel level, InfectedPatchConfiguration config, RandomSource random, BlockPos pos, Predicate<BlockState> state, int xRadius, int zRadius) {
        BlockPos.MutableBlockPos mutablePos = pos.mutable();
        BlockPos.MutableBlockPos mutable = mutablePos.mutable();
        Direction direction = config.surface().getDirection();
        Direction directionOpposite = direction.getOpposite();
        Set<BlockPos> set = new HashSet<>();

        for (int i = -xRadius; i <= xRadius; i++) {
            boolean flag = i == -xRadius || i == xRadius;

            for (int j = -zRadius; j <= zRadius; j++) {
                boolean flag1 = j == -zRadius || j == zRadius;
                boolean flag2 = flag || flag1;
                boolean flag3 = flag && flag1;
                boolean flag4 = flag2 && !flag3;
                if (!flag3 && (!flag4 || config.extraEdgeColumnChance() != 0.0F && !(random.nextFloat() > config.extraEdgeColumnChance()))) {
                    mutablePos.setWithOffset(pos, i, 0, j);

                    for (int k = 0;
                        level.isStateAtPosition(mutablePos, BlockBehaviour.BlockStateBase::isAir) && k < config.verticalRange();
                        k++
                    ) {
                        mutablePos.move(direction);
                    }

                    for (int i1 = 0;
                        level.isStateAtPosition(mutablePos, p_360244_ -> !p_360244_.isAir()) && i1 < config.verticalRange();
                        i1++
                    ) {
                        mutablePos.move(directionOpposite);
                    }

                    mutable.setWithOffset(mutablePos, config.surface().getDirection());
                    BlockState blockstate = level.getBlockState(mutable);
                    if (level.isEmptyBlock(mutablePos)
                        && blockstate.isFaceSturdy(level, mutable, config.surface().getDirection().getOpposite())) {
                        int l = config.depth().sample(random)
                            + (config.extraBottomBlockChance() > 0.0F && random.nextFloat() < config.extraBottomBlockChance() ? 1 : 0);
                        BlockPos blockpos = mutable.immutable();
                        boolean flag5 = this.placeGround(level, config, state, random, mutable, l);
                        if (flag5) {
                            set.add(blockpos);
                        }
                    }
                }
            }
        }

        return set;
    }

    protected void distributeVegetation(FeaturePlaceContext<InfectedPatchConfiguration> context, WorldGenLevel level, InfectedPatchConfiguration config, RandomSource random, Set<BlockPos> possiblePositions, int xRadius, int zRadius) {
        for (BlockPos blockpos : possiblePositions) {
            if (config.vegetationChance() > 0.0F && random.nextFloat() < config.vegetationChance()) {
                this.placeVegetation(level, config, context.chunkGenerator(), random, blockpos);
            }
        }
    }

    protected void placeVegetation(WorldGenLevel level, InfectedPatchConfiguration config, ChunkGenerator chunkGenerator, RandomSource random, BlockPos pos) {
        config.vegetationFeature().value().place(level, chunkGenerator, random, pos.relative(config.surface().getDirection().getOpposite()));
    }

    protected boolean placeGround(WorldGenLevel level, InfectedPatchConfiguration config, Predicate<BlockState> replaceable, RandomSource random, BlockPos.MutableBlockPos mutablePos, int maxDistance) {
        for (int i = 0; i < maxDistance; i++) {
            BlockState groundState = infectBlocks(level, mutablePos);
            BlockState state = level.getBlockState(mutablePos);
            if (!groundState.is(state.getBlock())) {
                if (!replaceable.test(state)) {
                    return i != 0;
                }

                level.setBlock(mutablePos, groundState, 2);
                mutablePos.move(config.surface().getDirection());
            }
        }
        return true;
    }

    public static BlockState infectBlocks(WorldGenLevel level, BlockPos.MutableBlockPos mutablePos) {
        BlockInfection infection = BuiltInRegistries.BLOCK.wrapAsHolder(level.getBlockState(mutablePos).getBlock()).getData(AetherIIDataMaps.INFECTED_BLOCKS);
        if (infection != null) {
            Block block = BuiltInRegistries.BLOCK.getValue(infection.block());
            if (block != null) {
                return block.defaultBlockState();
            }
        }
        return level.getBlockState(mutablePos);
    }
}
