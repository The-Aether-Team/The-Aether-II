package com.aetherteam.aetherii.world.tree.decorator;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.MultifaceSpreadeableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

import java.util.List;

public class MossCoverLogsDecorator extends TreeDecorator {
    public static final MapCodec<MossCoverLogsDecorator> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    BlockStateProvider.CODEC.fieldOf("provider").forGetter(decorator -> decorator.provider),
                    Codec.FLOAT.fieldOf("additional_chance").forGetter(decorator -> decorator.additionalChance)
            ).apply(instance, MossCoverLogsDecorator::new));

    protected final BlockStateProvider provider;
    protected final float additionalChance;

    public MossCoverLogsDecorator(BlockStateProvider provider, float additionalChance) {
        this.provider = provider;
        this.additionalChance = additionalChance;
    }

    @Override
    public void place(TreeDecorator.Context context) {
        WorldGenLevel level = context.level();
        RandomSource random = context.random();
        List<BlockPos> logs = context.logs();
        logs.removeIf((pos) -> !level.getBlockState(pos).is(BlockTags.LOGS));
        if (!logs.isEmpty()) {
            for (int i = 0; i < logs.size(); i++) {
                BlockPos logPos = logs.get(i);
                for (Direction direction : Direction.values()) {
                    BlockPos relative = logPos.relative(direction);
                    BlockState relativeState = level.getBlockState(relative);
                    BlockState state = this.provider.getState(level, random, relative);
                    boolean match = relativeState.getBlock() == state.getBlock();
                    if ((context.isAir(relative) || match) && (i == 0 || random.nextInt((int) Math.pow(i, 2.5)) <= i && random.nextFloat() <= this.additionalChance)) {
                        if (match) {
                            state = relativeState;
                        }
                        context.setBlock(relative, state.setValue(MultifaceSpreadeableBlock.getFaceProperty(direction.getOpposite()), true));
                    }
                }
            }
        }
    }

    @Override
    protected TreeDecoratorType<?> type() {
        return AetherIITreeDecoratorTypes.MOSS_COVER_LOGS.get();
    }
}
