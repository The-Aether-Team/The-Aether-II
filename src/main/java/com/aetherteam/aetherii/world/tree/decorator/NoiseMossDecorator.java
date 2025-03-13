package com.aetherteam.aetherii.world.tree.decorator;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.data.resources.registries.AetherIIDensityFunctions;
import com.aetherteam.aetherii.world.density.PerlinNoiseFunction;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

public class NoiseMossDecorator extends TreeDecorator {
    public static final MapCodec<NoiseMossDecorator> CODEC = MapCodec.unit(NoiseMossDecorator::new);

    public NoiseMossDecorator() { }

    @Override
    public void place(TreeDecorator.Context context) {
        LevelSimulatedReader level = context.level();
        if (level instanceof WorldGenLevel worldGenLevel) {
            HolderGetter<DensityFunction> function = worldGenLevel.holderLookup(Registries.DENSITY_FUNCTION);
            DensityFunction noise =  AetherIIDensityFunctions.getFunction(function, AetherIIDensityFunctions.SNOW_NOISE);
            DensityFunction.Visitor visitor = PerlinNoiseFunction.createOrGetVisitor(worldGenLevel.getSeed());
            noise.mapAll(visitor);

            for (BlockPos pos : context.leaves()) {
                BlockPos heightmapPos = context.level().getHeightmapPos(Heightmap.Types.OCEAN_FLOOR, pos);
                BlockPos relativePos = pos.above();
                if (heightmapPos.getY() == relativePos.getY()) {
                    double snowCalc = noise.compute(new DensityFunction.SinglePointContext(relativePos.getX(), relativePos.getY(), relativePos.getZ()));
                    if (snowCalc >= -0.1F && worldGenLevel.getBlockState(relativePos).isAir()) {
                        worldGenLevel.setBlock(relativePos, AetherIIBlocks.BRYALINN_MOSS_CARPET.get().defaultBlockState(), 2);
                    }
                }
            }
        }
    }

    @Override
    protected TreeDecoratorType<?> type() {
        return AetherIITreeDecoratorTypes.NOISE_MOSS.get();
    }
}
