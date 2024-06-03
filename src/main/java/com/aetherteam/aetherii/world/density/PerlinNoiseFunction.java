package com.aetherteam.aetherii.world.density;

import com.aetherteam.aetherii.mixin.mixins.common.accessor.PerlinNoiseAccessor;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.XoroshiroRandomSource;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import net.minecraft.world.level.levelgen.synth.PerlinNoise;

public class PerlinNoiseFunction implements DensityFunction {

    public static final KeyDispatchDataCodec<PerlinNoiseFunction> CODEC = KeyDispatchDataCodec.of(RecordCodecBuilder.mapCodec(
            p_208798_ -> p_208798_.group(
                            NormalNoise.NoiseParameters.DIRECT_CODEC.fieldOf("noise").forGetter((func) -> func.params),
                            Codec.DOUBLE.fieldOf("xz_scale").forGetter((func) -> func.xzScale),
                            Codec.DOUBLE.fieldOf("y_scale").forGetter((func) -> func.yScale),
                            Codec.LONG.fieldOf("seed").forGetter((func) -> func.seed)
                    )
                    .apply(p_208798_, PerlinNoiseFunction::new)));

    public final PerlinNoise noise;
    public final NormalNoise.NoiseParameters params;
    private final long seed;
    private final double xzScale;
    private final double yScale;

    public PerlinNoiseFunction(NormalNoise.NoiseParameters params, double xzScale, double yScale, long seed) {
        this(PerlinNoise.create(new XoroshiroRandomSource(seed), params.firstOctave(), params.amplitudes()), params, xzScale, yScale, seed);
    }

    private PerlinNoiseFunction(PerlinNoise noise, NormalNoise.NoiseParameters params, double xzScale, double yScale, long seed) {
        this.seed = seed;
        this.params = params;
        this.noise = noise;
        this.xzScale = xzScale;
        this.yScale = yScale;
    }

    public double compute(DensityFunction.FunctionContext pContext) {
        return this.noise
                .getValue((double)pContext.blockX() * this.xzScale, (double)pContext.blockY() * this.yScale, (double)pContext.blockZ() * this.xzScale);
    }

    @Override
    public void fillArray(double[] pArray, ContextProvider pContextProvider) {
        pContextProvider.fillAllDirectly(pArray, this);
    }

    @Override
    public DensityFunction mapAll(Visitor pVisitor) {
        return pVisitor.apply(new PerlinNoiseFunction(this.noise, this.params, this.xzScale, this.yScale, this.seed));
    }

    @Override
    public double minValue() {
        return -this.maxValue();
    }

    @Override
    public double maxValue() {
        return ((PerlinNoiseAccessor)this.noise).callMaxValue();
    }


    @Override
    public KeyDispatchDataCodec<? extends DensityFunction> codec() {
        return CODEC;
    }
}
