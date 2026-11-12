package com.aetherteam.aetherii.world.tree.decorator;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.MultifaceSpreadeableBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

import java.util.List;

public class LogMossCoverDecorator extends TreeDecorator {
    public static final MapCodec<LogMossCoverDecorator> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    BlockStateProvider.CODEC.fieldOf("provider").forGetter(decorator -> decorator.provider)
            ).apply(instance, LogMossCoverDecorator::new));

    protected final BlockStateProvider provider;

    public LogMossCoverDecorator(BlockStateProvider provider) {
        this.provider = provider;
    }

    @Override
    public void place(TreeDecorator.Context context) {
        WorldGenLevel level = context.level();
        RandomSource random = context.random();
        List<BlockPos> logs = context.logs();

        for (int i = 0; i < logs.size(); i++) {
            BlockPos logPos = logs.get(i);
            BlockState state = level.getBlockState(logPos);
            Direction.Axis axis = null;
            if (state.hasProperty(DirectionalBlock.FACING)) {
                axis = state.getValue(DirectionalBlock.FACING).getAxis();
            }
            if (state.hasProperty(RotatedPillarBlock.AXIS)) {
                axis = state.getValue(RotatedPillarBlock.AXIS);
            }
            if (axis != null) {
                for (Direction direction : Direction.Plane.HORIZONTAL) {
                    if (axis == Direction.Axis.Y) {
                        BlockPos relative = logPos.relative(direction);

                        if (context.isAir(relative) && random.nextInt(logs.size()) > i) {
                            context.setBlock(relative, this.provider.getState(level, random, relative).setValue(MultifaceSpreadeableBlock.getFaceProperty(direction.getOpposite()), true));
                        }
                    }
                }
            }
        }
    }

    @Override
    protected TreeDecoratorType<?> type() {
        return AetherIITreeDecoratorTypes.LOG_MOSS_COVER.get();
    }
}
