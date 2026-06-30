package com.aetherteam.aetherii.world.tree.decorator;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class WisprootTreeDecorator extends TreeDecorator {
    public static final MapCodec<WisprootTreeDecorator> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    BlockStateProvider.CODEC.fieldOf("mossy_full_block_provider").forGetter(decorator -> decorator.mossyFullState),
                    BlockStateProvider.CODEC.fieldOf("mossy_transition_block_provider").forGetter(decorator -> decorator.mossyTransitionState)
            ).apply(instance, WisprootTreeDecorator::new));

    private final BlockStateProvider mossyFullState;
    private final BlockStateProvider mossyTransitionState;

    public  WisprootTreeDecorator(BlockStateProvider mossyFullState, BlockStateProvider mossyTransitionState) {
        this.mossyFullState = mossyFullState;
        this.mossyTransitionState = mossyTransitionState;
    }

    public void place(Context context) {
        List<BlockPos> trunk = context.logs();
        List<BlockPos> sorted = trunk.stream().filter((blockPos) -> context.level().isStateAtPosition(blockPos, (blockState) -> blockState.is(BlockTags.LOGS))).toList();
        int up = 0;
        for (BlockPos pos : sorted) {
            boolean underwater = false;
            for (Direction adjacent : Direction.Plane.HORIZONTAL) {
                if (context.level().isFluidAtPosition(pos.relative(adjacent), (fluid) -> fluid.is(FluidTags.WATER))) {
                    underwater = true;
                    break;
                }
            }
            if (underwater) {
                context.setBlock(pos, this.mossyFullState.getState(context.random(), pos));
                if (context.random().nextBoolean()) {
                    up++;
                }
            } else {
                for (int i = 0; i < up; i++) {
                    context.setBlock(pos.above(i), this.mossyFullState.getState(context.random(), pos.above(i)));
                }
                context.setBlock(pos.above(up), this.mossyTransitionState.getState(context.random(), pos.above(up)));
                break;
            }
        }

    }

    protected @NotNull TreeDecoratorType<?> type() {
        return AetherIITreeDecoratorTypes.WISPROOT.get();
    }
}