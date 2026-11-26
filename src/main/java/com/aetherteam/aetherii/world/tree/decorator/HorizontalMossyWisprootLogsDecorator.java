package com.aetherteam.aetherii.world.tree.decorator;

import com.aetherteam.aetherii.block.miscellaneous.FacingPillarBlock;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.IntProviders;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

import java.util.List;

public class HorizontalMossyWisprootLogsDecorator extends TreeDecorator {
    public static final MapCodec<HorizontalMossyWisprootLogsDecorator> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    BlockStateProvider.CODEC.fieldOf("mossy_full_block_provider").forGetter(decorator -> decorator.mossyFullState),
                    BlockStateProvider.CODEC.fieldOf("mossy_transition_block_provider").forGetter(decorator -> decorator.mossyTransitionState),
                    IntProviders.CODEC.fieldOf("end_size").forGetter(decorator -> decorator.endSize)
            ).apply(instance, HorizontalMossyWisprootLogsDecorator::new));

    private final BlockStateProvider mossyFullState;
    private final BlockStateProvider mossyTransitionState;
    private final IntProvider endSize;

    public HorizontalMossyWisprootLogsDecorator(BlockStateProvider mossyFullState, BlockStateProvider mossyTransitionState, IntProvider endSize) {
        this.mossyFullState = mossyFullState;
        this.mossyTransitionState = mossyTransitionState;
        this.endSize = endSize;
    }

    @Override
    public void place(Context context) {
        WorldGenLevel level = context.level();
        RandomSource random = context.random();
        List<BlockPos> logs = context.logs();
        logs.removeIf((pos) -> !level.getBlockState(pos).is(BlockTags.LOGS));

        if (logs.size() >= 2) {
            int start = this.endSize.sample(random);
            int end = this.endSize.sample(random);
            if (start == logs.size() - 1 - end) {
                end -= 1;
            }

            Vec3i difference = logs.get(1).subtract(logs.getFirst());
            Direction direction = Direction.getApproximateNearest(difference.getX(), difference.getY(), difference.getZ());

            for (int i = 0; i < logs.size(); i++) {
                BlockPos pos = logs.get(i);
                if (i < start || i > logs.size() - 1 - end) {
                    BlockState state = this.mossyFullState.getState(level, random, pos);
                    if (level.getBlockState(pos).hasProperty(RotatedPillarBlock.AXIS)) {
                        state = state.trySetValue(RotatedPillarBlock.AXIS, level.getBlockState(pos).getValue(RotatedPillarBlock.AXIS));
                    }
                    context.setBlock(pos, state);
                } else if (i == start) {
                    context.setBlock(pos, this.mossyTransitionState.getState(level, random, pos).trySetValue(FacingPillarBlock.FACING, direction));
                } else if (i == logs.size() - 1 - end) {
                    context.setBlock(pos, this.mossyTransitionState.getState(level, random, pos).trySetValue(FacingPillarBlock.FACING, direction.getOpposite()));
                }
            }
        }
    }

    @Override
    protected TreeDecoratorType<?> type() {
        return AetherIITreeDecoratorTypes.HORIZONTAL_MOSSY_WISPROOT_LOGS.get();
    }
}
