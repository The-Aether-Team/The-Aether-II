package com.aetherteam.aetherii.world.tree.decorator;

import com.aetherteam.aetherii.block.natural.HangingLeavesBlock;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Util;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

import java.util.*;

public class HangingLeavesDecorator extends TreeDecorator {
    public static final MapCodec<HangingLeavesDecorator> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    BlockStateProvider.CODEC.fieldOf("provider").forGetter(decorator -> decorator.provider),
                    Codec.floatRange(0.0F, 1.0F).fieldOf("leaves_probability").forGetter(decorator -> decorator.leavesProbability)
            ).apply(instance, HangingLeavesDecorator::new));

    protected final BlockStateProvider provider;
    private final float leavesProbability;

    public HangingLeavesDecorator(BlockStateProvider provider, float leavesProbability) {
        this.provider = provider;
        this.leavesProbability = leavesProbability;
    }

    @Override
    public void place(TreeDecorator.Context context) {
        RandomSource random = context.random();
        WorldGenLevel level = context.level();
        List<BlockPos> leaves = Util.shuffledCopy(context.leaves(), random);
        List<BlockPos> logs = context.logs();
        logs.removeIf((pos) -> !level.getBlockState(pos).is(BlockTags.LOGS));

        if (!logs.isEmpty()) {
            BlockPos origin = logs.getFirst().below();

            int limitToFloor = 1 + random.nextInt(3);
            int amountToFloor = 0;

            for (BlockPos pos : leaves) {
                List<BlockPos> column = new ArrayList<>();

                if (level.getBlockState(pos.below()).isAir()) {
                    int maxDistance = pos.getY() - origin.getY();
                    int normalDistance = random.nextInt(4);
                    if (amountToFloor == limitToFloor) {
                        maxDistance = random.nextFloat() < this.leavesProbability ? normalDistance : 0;
                    }

                    for (int i = 1; i <= maxDistance; i++) {
                        BlockPos down = pos.below(i);
                        if (down.getY() > origin.getY()) {
                            if (level.getBlockState(down).canBeReplaced()) {
                                column.add(down);
                            } else {
                                column = column.subList(0, Math.min(normalDistance, column.size()));
                                break;
                            }
                        } else {
                            amountToFloor++;
                            break;
                        }
                    }
                }
                for (int i = 0; i < column.size(); i++) {
                    BlockPos position = column.get(i);
                    if (i < column.size() - 1) {
                        context.setBlock(position, this.provider.getState(level, random, position).setValue(HangingLeavesBlock.TIP, false));
                    } else {
                        context.setBlock(position, this.provider.getState(level, random, position).setValue(HangingLeavesBlock.TIP, true));
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
