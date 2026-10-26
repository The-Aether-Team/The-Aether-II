package com.aetherteam.aetherii.world.tree.decorator;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.block.natural.AetherLeavesBlock;
import com.aetherteam.aetherii.data.resources.registries.AetherIIDensityFunctions;
import com.aetherteam.aetherii.world.density.PerlinNoiseFunction;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Util;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

public class SnowDecorator extends TreeDecorator {
    public static final MapCodec<SnowDecorator> CODEC = MapCodec.unit(SnowDecorator::new);

    public SnowDecorator() { }

    @Override
    public void place(TreeDecorator.Context context) {
        WorldGenLevel level = context.level();
        RandomSource random = context.random();

        HolderGetter<DensityFunction> function = context.level().holderLookup(Registries.DENSITY_FUNCTION);
        DensityFunction noise =  AetherIIDensityFunctions.getFunction(function, AetherIIDensityFunctions.ENVIRONMENTAL_SNOW);
        DensityFunction.Visitor visitor = PerlinNoiseFunction.createOrGetVisitor(level.getSeed());
        noise.mapAll(visitor);

        for (BlockPos leafPos : Util.shuffledCopy(context.leaves(), random)) {
            BlockPos heightmapPos = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, leafPos);
            BlockPos relativePos = leafPos.above();

            double snowCalc = noise.compute(new DensityFunction.SinglePointContext(heightmapPos.getX(), heightmapPos.getY(), heightmapPos.getZ()));
            if (snowCalc < 0.5) {
                if (heightmapPos.getY() == relativePos.getY()) {
                    BlockState blockState = level.getBlockState(leafPos);
                    if (blockState.getBlock() instanceof AetherLeavesBlock) {
                        context.level().setBlock(leafPos, blockState.setValue(AetherLeavesBlock.SNOWY, true), 0);
                        context.level().setBlock(relativePos, BlockStateProvider.simple(AetherIIBlocks.ARCTIC_SNOW.get()).getState(level, random, relativePos), 0);
                    }
                }
            }
        }
    }

    @Override
    protected TreeDecoratorType<?> type() {
        return AetherIITreeDecoratorTypes.SNOW.get();
    }
}
