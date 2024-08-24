package com.aetherteam.aetherii.world.tree.decorator;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.block.natural.AetherLeavesBlock;
import com.mojang.serialization.MapCodec;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

public class SnowDecorator extends TreeDecorator {
    public static final MapCodec<SnowDecorator> CODEC = MapCodec.unit(SnowDecorator::new);

    public SnowDecorator() { }

    @Override
    public void place(TreeDecorator.Context context) {
        RandomSource random = context.random();
        for (BlockPos leafPos : Util.shuffledCopy(context.leaves(), random)) {
            BlockPos heightmapPos = context.level().getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, leafPos);
            BlockPos relativePos = leafPos.above();
            if (heightmapPos.getY() == relativePos.getY()) {
                context.setBlock(relativePos, BlockStateProvider.simple(AetherIIBlocks.ARCTIC_SNOW.get()).getState(random, relativePos));
                BlockPos belowPos = relativePos.below();
                context.level().isStateAtPosition(belowPos, (blockState) -> {
                    if (blockState.getBlock() instanceof AetherLeavesBlock) {
                        context.setBlock(belowPos, blockState.setValue(AetherLeavesBlock.SNOWY, true));
                        return true;
                    }
                    return false;
                });
            }
        }
    }

    @Override
    protected TreeDecoratorType<?> type() {
        return AetherIITreeDecoratorTypes.SNOW.get();
    }
}
