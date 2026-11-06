package com.aetherteam.aetherii.world.tree.decorator;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.IntProviders;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MossyWisprootDecorator extends TreeDecorator {
    public static final MapCodec<MossyWisprootDecorator> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    BlockStateProvider.CODEC.fieldOf("mossy_full_block_provider").forGetter(decorator -> decorator.mossyFullState),
                    BlockStateProvider.CODEC.fieldOf("mossy_transition_block_provider").forGetter(decorator -> decorator.mossyTransitionState),
                    IntProviders.CODEC.fieldOf("extra_height").forGetter(decorator -> decorator.extraHeight)
            ).apply(instance, MossyWisprootDecorator::new));

    private final BlockStateProvider mossyFullState;
    private final BlockStateProvider mossyTransitionState;
    private final IntProvider extraHeight;

    public MossyWisprootDecorator(BlockStateProvider mossyFullState, BlockStateProvider mossyTransitionState, IntProvider extraHeight) {
        this.mossyFullState = mossyFullState;
        this.mossyTransitionState = mossyTransitionState;
        this.extraHeight = extraHeight;
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
                context.setBlock(pos, this.mossyFullState.getState(context.level(), context.random(), pos));
                if (context.random().nextBoolean()) {
                    up++;
                }
            } else {
                int limit = up + this.extraHeight.sample(context.random());
                for (int i = 0; i < limit; i++) {
                    context.setBlock(pos.above(i), this.mossyFullState.getState(context.level(), context.random(), pos.above(i)));
                }
                context.setBlock(pos.above(limit), this.mossyTransitionState.getState(context.level(), context.random(), pos.above(limit)));
                break;
            }
        }

    }

    protected @NotNull TreeDecoratorType<?> type() {
        return AetherIITreeDecoratorTypes.MOSSY_WISPROOT.get();
    }
}