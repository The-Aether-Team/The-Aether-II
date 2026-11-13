package com.aetherteam.aetherii.world.tree.decorator;

import com.aetherteam.aetherii.block.miscellaneous.FacingPillarBlock;
import com.aetherteam.aetherii.block.natural.BottomedVineBlock;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

import java.util.List;

public class MossFallenLogsDecorator extends TreeDecorator {
    public static final MapCodec<MossFallenLogsDecorator> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    BlockStateProvider.CODEC.fieldOf("carpet_provider").forGetter(decorator -> decorator.carpetProvider),
                    BlockStateProvider.CODEC.fieldOf("vines_provider").forGetter(decorator -> decorator.vinesProvider),
                    Codec.FLOAT.fieldOf("chance").forGetter(decorator -> decorator.chance)
            ).apply(instance, MossFallenLogsDecorator::new));

    protected final BlockStateProvider carpetProvider;
    protected final BlockStateProvider vinesProvider;
    protected final float chance;

    public MossFallenLogsDecorator(BlockStateProvider carpetProvider, BlockStateProvider vinesProvider, float chance) {
        this.carpetProvider = carpetProvider;
        this.vinesProvider = vinesProvider;
        this.chance = chance;
    }

    @Override
    public void place(TreeDecorator.Context context) {
        WorldGenLevel level = context.level();
        RandomSource random = context.random();
        List<BlockPos> logs = context.logs();
        logs.removeIf((pos) -> !level.getBlockState(pos).is(BlockTags.LOGS));
        if (!logs.isEmpty()) {
            for (BlockPos logPos : logs) {
                BlockState logState = level.getBlockState(logPos);
                if (random.nextFloat() <= this.chance) {
                    for (Direction direction : Direction.Plane.HORIZONTAL) {
                        BlockPos relative = logPos.relative(direction);

                        Direction.Axis axis = null;
                        if (logState.hasProperty(RotatedPillarBlock.AXIS)) {
                            axis = logState.getValue(RotatedPillarBlock.AXIS);
                        }
                        if (logState.hasProperty(FacingPillarBlock.FACING)) {
                            axis = logState.getValue(FacingPillarBlock.FACING).getAxis();
                        }
                        if (axis != null && axis != direction.getAxis()) {
                            if (context.isAir(relative) && random.nextBoolean()) {
                                context.setBlock(relative, this.vinesProvider.getState(level, random, relative).setValue(VineBlock.getPropertyForFace(direction.getOpposite()), true).setValue(BottomedVineBlock.AGE, 25));
                            }
                        }
                    }
                    BlockPos above = logPos.above();
                    if (context.isAir(above)) {
                        context.setBlock(above, this.carpetProvider.getState(level, random, above));
                    }
                }
            }
        }
    }

    @Override
    protected TreeDecoratorType<?> type() {
        return AetherIITreeDecoratorTypes.MOSS_FALLEN_LOGS.get();
    }
}