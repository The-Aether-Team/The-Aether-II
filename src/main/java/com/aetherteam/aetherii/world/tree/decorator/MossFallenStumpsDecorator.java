package com.aetherteam.aetherii.world.tree.decorator;

import com.aetherteam.aetherii.block.natural.BottomedVineBlock;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

import java.util.List;

public class MossFallenStumpsDecorator extends TreeDecorator {
    public static final MapCodec<MossFallenStumpsDecorator> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    BlockStateProvider.CODEC.fieldOf("carpet_provider").forGetter(decorator -> decorator.carpetProvider),
                    BlockStateProvider.CODEC.fieldOf("vines_provider").forGetter(decorator -> decorator.vinesProvider),
                    Codec.FLOAT.fieldOf("chance").forGetter(decorator -> decorator.chance)
            ).apply(instance, MossFallenStumpsDecorator::new));

    protected final BlockStateProvider carpetProvider;
    protected final BlockStateProvider vinesProvider;
    protected final float chance;

    public MossFallenStumpsDecorator(BlockStateProvider carpetProvider, BlockStateProvider vinesProvider, float chance) {
        this.carpetProvider = carpetProvider;
        this.vinesProvider = vinesProvider;
        this.chance = chance;
    }

    @Override
    public void place(TreeDecorator.Context context) {
        WorldGenLevel level = context.level();
        RandomSource random = context.random();
        List<BlockPos> logs = context.logs();
        logs.removeIf((pos) -> !level.getBlockState(pos).is(BlockTags.LOGS));
        if (!logs.isEmpty()) {
            BlockPos logPos = logs.getLast();
            if (random.nextFloat() <= this.chance) {
                for (Direction direction : Direction.Plane.HORIZONTAL) {
                    if (random.nextBoolean()) {
                        BlockPos relative = logPos.relative(direction);
                        BlockState state = this.vinesProvider.getState(level, random, relative).setValue(VineBlock.getPropertyForFace(direction.getOpposite()), true);
                        if (random.nextInt(4) > 0) {
                            state = state.setValue(BottomedVineBlock.AGE, 25);
                        } else {
                            state = state.setValue(BottomedVineBlock.AGE, 24);
                        }
                        addHangingVine(relative, state, level);
                    }
                }
                BlockPos above = logPos.above();
                if (context.isAir(above)) {
                    context.setBlock(above, this.carpetProvider.getState(level, random, above));
                }
            }
        }
    }

    private static void addHangingVine(BlockPos pos, BlockState blockState, WorldGenLevel worldgenlevel) {
        worldgenlevel.setBlock(pos, blockState, 2);
        int i = 10;

        for (BlockPos blockpos = pos.below(); worldgenlevel.isEmptyBlock(blockpos) && i > 0; i--) {
            if (blockState.getValue(BottomedVineBlock.AGE) + 1 <= 25) {
                blockState = blockState.setValue(BottomedVineBlock.AGE, blockState.getValue(BottomedVineBlock.AGE) + 1);
                worldgenlevel.setBlock(blockpos, blockState, 2);
                blockpos = blockpos.below();
            } else {
                break;
            }
        }
    }

    @Override
    protected TreeDecoratorType<?> type() {
        return AetherIITreeDecoratorTypes.MOSS_FALLEN_STUMPS.get();
    }
}
