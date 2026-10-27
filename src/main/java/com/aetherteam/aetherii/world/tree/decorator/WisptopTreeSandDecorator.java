package com.aetherteam.aetherii.world.tree.decorator;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

import java.util.*;

public class WisptopTreeSandDecorator extends TreeDecorator {
    public static final MapCodec<WisptopTreeSandDecorator> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    BlockStateProvider.CODEC.fieldOf("sand_provider").forGetter(decorator -> decorator.sandProvider),
                    Codec.FLOAT.fieldOf("chance").forGetter(decorator -> decorator.chance)
            ).apply(instance, WisptopTreeSandDecorator::new));

    private final BlockStateProvider sandProvider;
    private final float chance;

    public WisptopTreeSandDecorator(BlockStateProvider sandProvider, float chance) {
        this.sandProvider = sandProvider;
        this.chance = chance;
    }

    public void place(Context context) {
        WorldGenLevel level = context.level();
        RandomSource random = context.random();
        List<BlockPos> logs = context.logs();
        List<BlockPos> leaves = context.leaves();

        for (int i = 0; i < logs.size() - 3; i++) {
            BlockPos log = logs.get(i);

            boolean checkMiddle = true;
            for (Direction direction : Direction.Plane.HORIZONTAL) {
                BlockPos relative = log.relative(direction, 2);
                if (leaves.contains(relative)) {
                    BlockPos above = relative.above();
                    if (!leaves.contains(log.relative(direction).above()) && random.nextFloat() < this.chance) {
                        this.placeSideLayer(context, level, random, above, direction);
                    }
                    checkMiddle = false;
                }
            }
            if (checkMiddle) {
                boolean placeMiddle = true;
                for (Direction direction : Direction.Plane.HORIZONTAL) {
                    BlockPos relative = log.relative(direction);
                    if (!leaves.contains(relative) || leaves.contains(relative.above())) {
                        placeMiddle = false;
                        break;
                    }
                }
                if (placeMiddle) {
                    if (random.nextFloat() < this.chance) {
                        this.placeMiddleLayer(context, level, random, log.above());
                    }
                }
            }
        }
    }

    public void placeSideLayer(Context context, WorldGenLevel level, RandomSource random, BlockPos origin, Direction direction) {
        this.placeBlock(context, level, random, origin);
        this.placeBlock(context, level, random, origin.relative(direction.getOpposite()));
        this.placeBlock(context, level, random, origin.relative(direction.getOpposite()).relative(direction.getClockWise()));
        this.placeBlock(context, level, random, origin.relative(direction.getOpposite(), 2).relative(direction.getClockWise()));
        this.placeBlock(context, level, random, origin.relative(direction.getOpposite()).relative(direction.getCounterClockWise()));
        this.placeBlock(context, level, random, origin.relative(direction.getOpposite(), 2).relative(direction.getCounterClockWise()));
        this.placeBlock(context, level, random, origin.relative(direction.getOpposite(), 3));
        Direction offset = random.nextBoolean() ? direction.getClockWise() : direction.getCounterClockWise();
        this.placeBlock(context, level, random, origin.relative(offset).relative(direction));
        this.placeBlock(context, level, random, origin.relative(offset, 2).relative(direction));
        this.placeBlock(context, level, random, origin.relative(offset, 2));
    }

    public void placeMiddleLayer(Context context, WorldGenLevel level, RandomSource random, BlockPos origin) { //todo
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            BlockPos above = origin.relative(direction);
            this.placeBlock(context, level, random, above);
        }
        int xOffset = random.nextBoolean() ? -1 : 1;
        int zOffset = random.nextBoolean() ? -1 : 1;
        this.placeBlock(context, level, random, origin.offset(xOffset, 0, zOffset));
        this.placeBlock(context, level, random, origin.offset(-xOffset, 0, -zOffset));
        this.placeBlock(context, level, random, origin.offset(xOffset * 2, 0, -zOffset * 2));
        this.placeBlock(context, level, random, origin.offset(xOffset, 0, -zOffset * 2));
        this.placeBlock(context, level, random, origin.offset(xOffset * 2, 0, -zOffset));
        this.placeBlock(context, level, random, origin.offset(-xOffset * 2, 0, zOffset * 2));
        this.placeBlock(context, level, random, origin.offset(-xOffset, 0, zOffset * 2));
        this.placeBlock(context, level, random, origin.offset(-xOffset * 2, 0, zOffset));
    }

    public void placeBlock(Context context, WorldGenLevel level, RandomSource random, BlockPos pos) {
        if (context.checkBlock(pos, BlockBehaviour.BlockStateBase::canBeReplaced)) { // && context.checkBlock(pos.above(), BlockBehaviour.BlockStateBase::canBeReplaced)
            context.setBlock(pos, this.sandProvider.getState(level, random, pos));
        }
    }

    protected TreeDecoratorType<?> type() {
        return AetherIITreeDecoratorTypes.WISPTOP_SAND.get();
    }

    public record TypedPosition(BlockPos pos, Type type) {
        public enum Type {
            LOG,
            LEAF;
        }
    }
}
