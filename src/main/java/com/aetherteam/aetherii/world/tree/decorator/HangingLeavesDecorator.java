package com.aetherteam.aetherii.world.tree.decorator;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Util;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

import java.util.List;

public class HangingLeavesDecorator extends TreeDecorator {
    public static final MapCodec<HangingLeavesDecorator> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    BlockStateProvider.CODEC.fieldOf("body_provider").forGetter(decorator -> decorator.bodyProvider),
                    BlockStateProvider.CODEC.fieldOf("head_provider").forGetter(decorator -> decorator.headProvider)
            ).apply(instance, HangingLeavesDecorator::new));

    protected final BlockStateProvider bodyProvider;
    protected final BlockStateProvider headProvider;

    public HangingLeavesDecorator(BlockStateProvider bodyProvider, BlockStateProvider headProvider) {
        this.bodyProvider = bodyProvider;
        this.headProvider = headProvider;
    }

    @Override
    public void place(TreeDecorator.Context context) {
        WorldGenLevel level = context.level();
        RandomSource random = context.random();
        List<BlockPos> leaves = context.leaves();
        List<BlockPos> logs = context.logs();

        //todo find surface from heightmap to determine if hanging leaf should be full length possibly?

        int reachedGroundCount = 0;
        int reachedGroundLimit = random.nextInt(3) + 1;

        for (BlockPos leafPos : Util.shuffledCopy(context.leaves(), random)) {
            if (level.getBlockState(leafPos.below()).canBeReplaced()) {
                for (int i = 1; i < random.nextInt(20); i++) {
                    BlockPos pos = leafPos.below(i);
                    if (level.getBlockState(pos.below()).canBeReplaced()) {
                        level.setBlock(pos, this.bodyProvider.getState(level, random, pos), 1 | 2);
                    } else {
                        level.setBlock(pos, this.headProvider.getState(level, random, pos), 1 | 2);
                        break;
                    }
                }
            }
        }
    }

    @Override
    protected TreeDecoratorType<?> type() {
        return AetherIITreeDecoratorTypes.HANGING_LEAVES.get();
    }
}
