package com.aetherteam.aetherii.world.tree.decorator;

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
            BlockPos terrainHeightmapPos = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, leafPos);
            BlockPos leafHeightmapPos = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, leafPos);
            BlockPos relativePos = leafPos.above();

            int fullSnowLimit = 196;
            double snowMagnitude = (fullSnowLimit - Math.min(fullSnowLimit, terrainHeightmapPos.getY())) * 0.006F;
            double snowCalc = noise.compute(new DensityFunction.SinglePointContext(terrainHeightmapPos.getX(), terrainHeightmapPos.getY(), terrainHeightmapPos.getZ()));
            if (snowCalc >= snowMagnitude) {
                if (leafHeightmapPos.getY() == relativePos.getY()) {
                    BlockPos belowPos = relativePos.below();
                    context.level().isStateAtPosition(belowPos, (blockState) -> {
                        if (blockState.getBlock() instanceof AetherLeavesBlock) {
                            context.setBlock(relativePos, BlockStateProvider.simple(AetherIIBlocks.ARCTIC_SNOW.get()).getState(context.level(), random, relativePos));
                            context.setBlock(belowPos, blockState.setValue(AetherLeavesBlock.SNOWY, true));
                            return true;
                        }
                        return false;
                    });
                }
            }
        }
    }

    @Override
    protected TreeDecoratorType<?> type() {
        return AetherIITreeDecoratorTypes.SNOW.get();
    }
}
