package com.aetherteam.aetherii.world.tree.decorator;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.IntProviders;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

import java.util.List;

public class VerticalMossyWisprootLogsDecorator extends TreeDecorator {
    public static final MapCodec<VerticalMossyWisprootLogsDecorator> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    BlockStateProvider.CODEC.fieldOf("mossy_full_block_provider").forGetter(decorator -> decorator.mossyFullState),
                    BlockStateProvider.CODEC.fieldOf("mossy_transition_block_provider").forGetter(decorator -> decorator.mossyTransitionState),
                    IntProviders.CODEC.fieldOf("extra_height").forGetter(decorator -> decorator.extraHeight)
            ).apply(instance, VerticalMossyWisprootLogsDecorator::new));

    private final BlockStateProvider mossyFullState;
    private final BlockStateProvider mossyTransitionState;
    private final IntProvider extraHeight;

    public VerticalMossyWisprootLogsDecorator(BlockStateProvider mossyFullState, BlockStateProvider mossyTransitionState, IntProvider extraHeight) {
        this.mossyFullState = mossyFullState;
        this.mossyTransitionState = mossyTransitionState;
        this.extraHeight = extraHeight;
    }

    @Override
    public void place(Context context) {
        WorldGenLevel level = context.level();
        RandomSource random = context.random();
        List<BlockPos> logs = context.logs();
        logs.removeIf((pos) -> !level.getBlockState(pos).is(BlockTags.LOGS));
        int start = 0;
        int limit = start + this.extraHeight.sample(random);
        for (int i = 0; i < logs.size(); i++) {
            BlockPos pos = logs.get(i);
            boolean underwater = false;
            for (Direction adjacent : Direction.Plane.HORIZONTAL) {
                if (level.isFluidAtPosition(pos.relative(adjacent), (fluid) -> fluid.is(FluidTags.WATER))) {
                    underwater = true;
                    break;
                }
            }
            if (underwater) {
                context.setBlock(pos, this.mossyFullState.getState(level, random, pos));
                start++;
                limit++;
            } else {
                if (i >= start) {
                    if (i < limit) {
                        context.setBlock(pos, this.mossyFullState.getState(level, random, pos));
                    } else {
                        context.setBlock(pos, this.mossyTransitionState.getState(level, random, pos));
                        break;
                    }
                }
            }
        }
    }

    @Override
    protected TreeDecoratorType<?> type() {
        return AetherIITreeDecoratorTypes.VERTICAL_MOSSY_WISPROOT_LOGS.get();
    }
}