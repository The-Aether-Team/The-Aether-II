package com.aetherteam.aetherii.world.feature.stateprovider;

import com.aetherteam.aetherii.world.density.DensityTest;
import com.aetherteam.aetherii.world.density.PerlinNoiseFunction;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProviderType;

import java.util.List;

public class DensityFunctionProvider extends BlockStateProvider {
    public static final MapCodec<DensityFunctionProvider> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            DensityTest.CODEC.listOf().fieldOf("options").forGetter((provider) -> provider.options),
            DensityFunction.HOLDER_HELPER_CODEC.fieldOf("function").forGetter((provider) -> provider.function)
    ).apply(inst, DensityFunctionProvider::new));

    private final List<DensityTest> options;
    private final DensityFunction function;

    public DensityFunctionProvider(List<DensityTest> options, DensityFunction function) {
        this.options = options;
        this.function = function;
    }

    @Override
    public BlockState getState(WorldGenLevel worldGenLevel, RandomSource randomSource, BlockPos blockPos) {
        DensityFunction.Visitor visitor = PerlinNoiseFunction.createOrGetVisitor(worldGenLevel.getSeed());
        this.function.mapAll(visitor);
        double noiseValue = this.function.compute(new DensityFunction.SinglePointContext(blockPos.getX(), blockPos.getY(), blockPos.getZ()));
        for (DensityTest option : this.options) {
            BlockState block = option.test(noiseValue);
            if (block != null) {
                return block;
            }
        }
        return Blocks.AIR.defaultBlockState();
    }

    @Override
    protected BlockStateProviderType<?> type() {
        return AetherIIBlockStateProviderTypes.DENSITY_FUNCTION_PROVIDER.get();
    }
}
