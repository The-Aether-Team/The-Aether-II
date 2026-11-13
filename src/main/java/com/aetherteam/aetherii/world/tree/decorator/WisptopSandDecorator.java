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
import java.util.function.UnaryOperator;

public class WisptopSandDecorator extends TreeDecorator {
    public static final MapCodec<WisptopSandDecorator> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    BlockStateProvider.CODEC.fieldOf("sand_provider").forGetter(decorator -> decorator.sandProvider),
                    Codec.FLOAT.fieldOf("chance").forGetter(decorator -> decorator.chance)
            ).apply(instance, WisptopSandDecorator::new));

    private final BlockStateProvider sandProvider;
    private final float chance;

    public WisptopSandDecorator(BlockStateProvider sandProvider, float chance) {
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
                    BlockPos placePos = log.above();
                    if (leaves.contains(placePos.relative(direction))) {
                        placePos = placePos.relative(direction);
                    }
                    if (!leaves.contains(placePos.relative(direction)) && random.nextFloat() < this.chance) {
                        this.placeLayer(context, level, random, placePos, direction);
                    }
                    checkMiddle = false;
                }
            }
            if (checkMiddle) {
                boolean placeMiddle = true;
                for (Direction direction : Direction.Plane.HORIZONTAL) {
                    BlockPos relative = log.relative(direction);
                    if (!leaves.contains(relative)) {
                        placeMiddle = false;
                        break;
                    }
                }
                if (placeMiddle) {
                    if (random.nextFloat() < this.chance) {
                        this.placeLayer(context, level, random, log.above());
                    }
                }
            }
        }
    }

    public void placeLayer(Context context, WorldGenLevel level, RandomSource random, BlockPos origin, Direction direction) {
        this.placeBlock(context, level, random, origin.relative(direction));

        boolean side = random.nextBoolean();

        if (this.placeBlock(context, level, random, origin.relative(direction.getClockWise()))) {
            if (side) {
                this.placeBlock(context, level, random, origin.relative(direction).relative(direction.getClockWise(), 2));
            }
        }
        if (this.placeBlock(context, level, random, origin.relative(direction.getCounterClockWise()))) {
            if (!side) {
                this.placeBlock(context, level, random, origin.relative(direction).relative(direction.getCounterClockWise(), 2));
            }
        }
        if (random.nextBoolean()) {
            if (random.nextBoolean()) {
                this.placeBlock(context, level, random, origin.relative(direction, 2).relative(direction.getClockWise(), 2));
                this.placeBlock(context, level, random, origin.relative(direction, 3).relative(direction.getClockWise()));
            } else {
                this.placeBlock(context, level, random, origin.relative(direction, 2).relative(direction.getCounterClockWise(), 2));
                this.placeBlock(context, level, random, origin.relative(direction, 3).relative(direction.getCounterClockWise()));
            }
        }

    }

    public void placeLayer(Context context, WorldGenLevel level, RandomSource random, BlockPos origin) {
        boolean tendencyCheck = random.nextBoolean();
        UnaryOperator<Direction> tendency = (direction) -> tendencyCheck ? direction.getClockWise() : direction.getCounterClockWise();
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            BlockPos above = origin.relative(direction);
            if (this.placeBlock(context, level, random, above) && random.nextInt(4) == 0) {
                this.placeBlock(context, level, random, above.relative(direction).relative(tendency.apply(direction)));
            }
        }
    }

    public boolean placeBlock(Context context, WorldGenLevel level, RandomSource random, BlockPos pos) {
        if (context.checkBlock(pos, BlockBehaviour.BlockStateBase::canBeReplaced)) {
            context.setBlock(pos, this.sandProvider.getState(level, random, pos));
            return true;
        }
        return false;
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
