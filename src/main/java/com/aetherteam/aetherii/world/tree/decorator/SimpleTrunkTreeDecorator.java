package com.aetherteam.aetherii.world.tree.decorator;

import com.aetherteam.aetherii.block.natural.TrunkBlock;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WallSide;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import org.jetbrains.annotations.NotNull;

public class SimpleTrunkTreeDecorator extends TreeDecorator {
    public static final MapCodec<SimpleTrunkTreeDecorator> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    BlockStateProvider.CODEC.fieldOf("trunk_block_provider").forGetter(decorator -> decorator.trunkState),
                    Codec.FLOAT.fieldOf("placement_chance").forGetter(decorator -> decorator.placementChance),
                    Codec.FLOAT.fieldOf("above_placement_chance").forGetter(decorator -> decorator.abovePlacementChance)
            ).apply(instance, SimpleTrunkTreeDecorator::new));

    private final BlockStateProvider trunkState;
    private final float placementChance;
    private final float abovePlacementChance;

    public SimpleTrunkTreeDecorator(BlockStateProvider trunkState, float placementChance, float abovePlacementChance) {
        this.trunkState = trunkState;
        this.placementChance = placementChance;
        this.abovePlacementChance = abovePlacementChance;
    }

    public void place(Context context) {
        BlockPos pos = context.logs().get(1);
        RandomSource random = context.random();

        if (abovePlacementChance > 0.0F && random.nextFloat() < abovePlacementChance) {
            placeBlockAbove(context, pos.north(), trunkState.getState(context.level(), random, pos.north()).setValue(TrunkBlock.SOUTH_CONNECTION, WallSide.TALL).setValue(TrunkBlock.TALL, true), trunkState.getState(context.level(), random, pos.east().above()).setValue(TrunkBlock.SOUTH_CONNECTION, WallSide.LOW), random);
        }
        else placeBlockAt(context, pos.north(), trunkState.getState(context.level(), random, pos.north()).setValue(TrunkBlock.SOUTH_CONNECTION, WallSide.LOW), random);

        if (abovePlacementChance > 0.0F && random.nextFloat() < abovePlacementChance) {
            placeBlockAbove(context, pos.east(), trunkState.getState(context.level(), random, pos.east()).setValue(TrunkBlock.WEST_CONNECTION, WallSide.TALL).setValue(TrunkBlock.TALL, true), trunkState.getState(context.level(), random, pos.east().above()).setValue(TrunkBlock.WEST_CONNECTION, WallSide.LOW), random);
        }
        else placeBlockAt(context, pos.east(), trunkState.getState(context.level(), random, pos.east()).setValue(TrunkBlock.WEST_CONNECTION, WallSide.LOW), random);

        if (abovePlacementChance > 0.0F && random.nextFloat() < abovePlacementChance) {
            placeBlockAbove(context, pos.south(), trunkState.getState(context.level(), random, pos.south()).setValue(TrunkBlock.NORTH_CONNECTION, WallSide.TALL).setValue(TrunkBlock.TALL, true), trunkState.getState(context.level(), random, pos.east().above()).setValue(TrunkBlock.NORTH_CONNECTION, WallSide.LOW), random);
        }
        else placeBlockAt(context, pos.south(), trunkState.getState(context.level(), random, pos.south()).setValue(TrunkBlock.NORTH_CONNECTION, WallSide.LOW), random);

        if (abovePlacementChance > 0.0F && random.nextFloat() < abovePlacementChance) {
            placeBlockAbove(context, pos.west(), trunkState.getState(context.level(), random, pos.west()).setValue(TrunkBlock.EAST_CONNECTION, WallSide.TALL).setValue(TrunkBlock.TALL, true), trunkState.getState(context.level(), random, pos.east().above()).setValue(TrunkBlock.EAST_CONNECTION, WallSide.LOW), random);
        }
        else placeBlockAt(context, pos.west(), trunkState.getState(context.level(), random, pos.west()).setValue(TrunkBlock.EAST_CONNECTION, WallSide.LOW), random);
    }

    private void placeBlockAt(Context context, BlockPos pos, BlockState state, RandomSource random) {
        if (TreeFeature.validTreePos(context.level(), pos) && !TreeFeature.validTreePos(context.level(), pos.below()) && placementChance > 0.0F && random.nextFloat() < placementChance) {
            context.setBlock(pos, state);
        }
    }

    private void placeBlockAbove(Context context, BlockPos pos, BlockState state, BlockState stateAbove, RandomSource random) {
        if (TreeFeature.validTreePos(context.level(), pos) && !TreeFeature.validTreePos(context.level(), pos.below()) && placementChance > 0.0F && random.nextFloat() < placementChance) {
            context.setBlock(pos, state);
            context.setBlock(pos.above(), stateAbove);
        }
    }

    protected @NotNull TreeDecoratorType<?> type() {
        return AetherIITreeDecoratorTypes.SIMPLE_TRUNK.get();
    }
}