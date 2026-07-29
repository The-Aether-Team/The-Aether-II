package com.aetherteam.aetherii.world.surfacerule;

import com.aetherteam.aetherii.world.density.PerlinNoiseFunction;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.SurfaceRules;

public record DensityFunctionPlacementRule(BlockState block, DensityFunction function, double threshold) implements SurfaceRules.RuleSource {
	public static final KeyDispatchDataCodec<DensityFunctionPlacementRule> KEY_CODEC = KeyDispatchDataCodec.of(RecordCodecBuilder.mapCodec(inst -> inst.group(
			BlockState.CODEC.fieldOf("block").forGetter(DensityFunctionPlacementRule::block),
			DensityFunction.HOLDER_HELPER_CODEC.fieldOf("function").forGetter(DensityFunctionPlacementRule::function),
			Codec.DOUBLE.fieldOf("threshold").forGetter(DensityFunctionPlacementRule::threshold)
	).apply(inst, DensityFunctionPlacementRule::new)));

	@Override
	public SurfaceRules.SurfaceRule apply(SurfaceRules.Context context) {
		DensityFunction.Visitor visitor = PerlinNoiseFunction.createOrGetVisitor(1234L);
		this.function().mapAll(visitor);
		return (x, y, z) -> {
			double noiseValue = this.function().compute(new DensityFunction.SinglePointContext(x, y, z));
			if (noiseValue < this.threshold()) {
				return this.block();
			} else {
				return null;
			}
		};
	}

	@Override
	public KeyDispatchDataCodec<? extends SurfaceRules.RuleSource> codec() {
		return KEY_CODEC;
	}
}
