package com.aetherteam.aetherii.world.surfacerule;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

import java.util.List;

public record NoisePalette3DPlacementRule(List<BlockState> blockStates, ResourceKey<NormalNoise.NoiseParameters> noise, double noiseFreq) implements SurfaceRules.RuleSource {
	public static final KeyDispatchDataCodec<NoisePalette3DPlacementRule> KEY_CODEC = KeyDispatchDataCodec.of(RecordCodecBuilder.mapCodec(inst -> inst.group(
			BlockState.CODEC.listOf().fieldOf("blockstates").forGetter(NoisePalette3DPlacementRule::blockStates),
			ResourceKey.codec(Registries.NOISE).fieldOf("noise").forGetter(NoisePalette3DPlacementRule::noise),
			Codec.DOUBLE.fieldOf("noise_frequency").forGetter(NoisePalette3DPlacementRule::noiseFreq)
	).apply(inst, NoisePalette3DPlacementRule::new)));

	@Override
	public SurfaceRules.SurfaceRule apply(SurfaceRules.Context context) {
		return (x, y, z) -> {
			NormalNoise noise = context.randomState.getOrCreateNoise(this.noise);
			float normalizedNoise = (float) Math.clamp(noise.getValue(x * this.noiseFreq, y * this.noiseFreq, z * this.noiseFreq) * 0.5f + 0.5f, 0f, 1f); // Limits noise within 0.0 - 1.0 range.
			return this.blockStates.get(Mth.lerpDiscrete(normalizedNoise, 0, this.blockStates.size() - 1));
		};
	}

	@Override
	public KeyDispatchDataCodec<? extends SurfaceRules.RuleSource> codec() {
		return KEY_CODEC;
	}
}
