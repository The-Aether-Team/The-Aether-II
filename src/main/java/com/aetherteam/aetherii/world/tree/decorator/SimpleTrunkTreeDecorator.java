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
                    Codec.FLOAT.fieldOf("placement_chance").forGetter(decorator -> decorator.placementChance)
            ).apply(instance, SimpleTrunkTreeDecorator::new));

    private final BlockStateProvider trunkState;
    private final float placementChance;

    public SimpleTrunkTreeDecorator(BlockStateProvider trunkState, float placementChance) {
        this.trunkState = trunkState;
        this.placementChance = placementChance;
    }

    public void place(Context context) {
        BlockPos pos = context.logs().get(1);
        RandomSource random = context.random();

        placeBlockAt(context, pos.north(), trunkState.getState(random, pos.north()).setValue(TrunkBlock.SOUTH_CONNECTION, WallSide.LOW), random);
        placeBlockAt(context, pos.east(), trunkState.getState(random, pos.east()).setValue(TrunkBlock.WEST_CONNECTION, WallSide.LOW), random);
        placeBlockAt(context, pos.south(), trunkState.getState(random, pos.south()).setValue(TrunkBlock.NORTH_CONNECTION, WallSide.LOW), random);
        placeBlockAt(context, pos.west(), trunkState.getState(random, pos.west()).setValue(TrunkBlock.EAST_CONNECTION, WallSide.LOW), random);
    }

    private void placeBlockAt(Context context, BlockPos pos, BlockState state, RandomSource random) {
        if (TreeFeature.validTreePos(context.level(), pos) && !TreeFeature.validTreePos(context.level(), pos.below()) && placementChance > 0.0F && random.nextFloat() < placementChance) {
            context.setBlock(pos, state);
        }
    }

    protected @NotNull TreeDecoratorType<?> type() {
        return AetherIITreeDecoratorTypes.SIMPLE_TRUNK.get();
    }
}