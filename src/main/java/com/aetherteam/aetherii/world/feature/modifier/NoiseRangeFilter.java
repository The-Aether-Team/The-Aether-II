package com.aetherteam.aetherii.world.feature.modifier;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.world.density.PerlinNoiseFunction;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementFilter;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

public class NoiseRangeFilter extends PlacementFilter {
    public static final MapCodec<NoiseRangeFilter> CODEC = RecordCodecBuilder.mapCodec((codec) -> codec.group(
            DensityFunction.HOLDER_HELPER_CODEC.fieldOf("noise").forGetter((modifier) -> modifier.noise),
            Codec.FLOAT.fieldOf("min").forGetter((modifier) -> modifier.min),
            Codec.FLOAT.fieldOf("max").forGetter((modifier) -> modifier.max)
    ).apply(codec, NoiseRangeFilter::new));
    private final DensityFunction noise;
    private final float min;
    private final float max;

    public NoiseRangeFilter(DensityFunction noise, float min, float max) {
        this.noise = noise;
        this.min = min;
        this.max = max;
    }

    @Override
    protected boolean shouldPlace(PlacementContext placementContext, RandomSource randomSource, BlockPos blockPos) {
        DensityFunction.Visitor visitor = PerlinNoiseFunction.createOrGetVisitor(placementContext.getLevel().getSeed());
        this.noise.mapAll(visitor);
        double value = this.noise.compute(new DensityFunction.SinglePointContext(blockPos.getX(), 0, blockPos.getZ()));
        return value >= this.min && value <= this.max;
    }

    @Override
    public PlacementModifierType<?> type() {
        return AetherIIPlacementModifierTypes.NOISE_RANGE_FILTER.get();
    }
}
