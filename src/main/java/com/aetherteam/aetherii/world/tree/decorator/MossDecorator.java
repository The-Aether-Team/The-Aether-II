package com.aetherteam.aetherii.world.tree.decorator;

import com.aetherteam.aetherii.block.natural.BottomedVineBlock;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

import java.util.Optional;

public class MossDecorator extends TreeDecorator {
    public static final MapCodec<MossDecorator> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    BlockStateProvider.CODEC.fieldOf("block_provider").forGetter(decorator -> decorator.blockProvider),
                    BlockStateProvider.CODEC.fieldOf("carpet_provider").forGetter(decorator -> decorator.carpetProvider),
                    BlockStateProvider.CODEC.fieldOf("vines_provider").forGetter(decorator -> decorator.vinesProvider),
                    BlockStateProvider.CODEC.optionalFieldOf("flower_provider").forGetter(decorator -> decorator.flowerProvider)
            ).apply(instance, MossDecorator::new));

    protected final BlockStateProvider blockProvider;
    protected final BlockStateProvider carpetProvider;
    protected final BlockStateProvider vinesProvider;
    protected final Optional<BlockStateProvider> flowerProvider;

    public MossDecorator(BlockStateProvider blockProvider, BlockStateProvider carpetProvider, BlockStateProvider vinesProvider) {
        this(blockProvider, carpetProvider, vinesProvider, Optional.empty());
    }

    public MossDecorator(BlockStateProvider blockProvider, BlockStateProvider carpetProvider, BlockStateProvider vinesProvider, Optional<BlockStateProvider> flowerProvider) {
        this.blockProvider = blockProvider;
        this.carpetProvider = carpetProvider;
        this.vinesProvider = vinesProvider;
        this.flowerProvider = flowerProvider;
    }

    @Override
    public void place(TreeDecorator.Context context) {
        RandomSource random = context.random();
        for (BlockPos leafPos : Util.shuffledCopy(context.leaves(), random)) {
            BlockPos heightmapPos = context.level().getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, leafPos);
            BlockPos relativePos = leafPos.above();
            if (heightmapPos.getY() == relativePos.getY()) {
                if (random.nextInt(10) == 0) {
                    context.setBlock(leafPos, this.blockProvider.getState(random, leafPos));
                    if (context.isAir(relativePos)) {
                        if (this.flowerProvider.isEmpty() || random.nextBoolean()) {
                            context.setBlock(relativePos, this.carpetProvider.getState(random, relativePos));
                        } else {
                            context.setBlock(relativePos, this.flowerProvider.get().getState(random, relativePos));
                        }
                    }
                }
                if (random.nextInt(5) == 0) {
                    context.setBlock(relativePos, this.carpetProvider.getState(random, relativePos));
                }
                for (Direction offsetDirection : Direction.Plane.HORIZONTAL.stream().toList()) {
                    BlockPos newPos = leafPos.relative(offsetDirection);
                    if (context.isAir(newPos)) {
                        Direction direction = Direction.Plane.HORIZONTAL.getRandomDirection(random);
                        if (context.level().isStateAtPosition(newPos.relative(direction), BlockBehaviour.BlockStateBase::isSolid)) {
                            BlockState blockState = this.vinesProvider.getState(random, newPos.relative(direction));
                            blockState = blockState.setValue(VineBlock.getPropertyForFace(direction), true);
                            if ((context.level().isStateAtPosition(newPos.relative(direction), (state) -> state.is(this.blockProvider.getState(random, newPos.relative(direction)).getBlock()))
                                    || context.level().isStateAtPosition(newPos.above().relative(direction), (state) -> state.is(this.blockProvider.getState(random, newPos.above().relative(direction)).getBlock()))
                                    || context.level().isStateAtPosition(newPos.above().relative(direction), (state) -> state.is(this.carpetProvider.getState(random, newPos.above().relative(direction)).getBlock())))
                                    && random.nextInt(4) == 0) {
                                blockState = blockState.setValue(BottomedVineBlock.AGE, 25);
                            } else {
                                blockState = blockState.setValue(BottomedVineBlock.AGE, 20 + random.nextInt(5));
                            }
                            BlockState finalBlockState = blockState;
                            if (context.level().isStateAtPosition(newPos.above(), (state) -> !state.is(finalBlockState.getBlock()) || (state.hasProperty(BottomedVineBlock.AGE) && state.getValue(BottomedVineBlock.AGE) < 25))) {
                                addHangingVine(context, newPos, blockState);
                            }
                        }
                    }
                }
            }
        }
    }

    private static void addHangingVine(TreeDecorator.Context context, BlockPos pos, BlockState blockState) {
        context.setBlock(pos, blockState);
        int i = 10;

        for (BlockPos blockpos = pos.below(); context.isAir(blockpos) && i > 0; i--) {
            if (blockState.getValue(BottomedVineBlock.AGE) + 1 <= 25) {
                blockState = blockState.setValue(BottomedVineBlock.AGE, blockState.getValue(BottomedVineBlock.AGE) + 1);
                context.setBlock(blockpos, blockState);
                blockpos = blockpos.below();
            } else {
                break;
            }
        }
    }

    @Override
    protected TreeDecoratorType<?> type() {
        return AetherIITreeDecoratorTypes.MOSS.get();
    }
}