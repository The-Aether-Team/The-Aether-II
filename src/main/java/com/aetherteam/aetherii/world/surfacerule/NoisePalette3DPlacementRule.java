package com.aetherteam.aetherii.world.surfacerule;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.XoroshiroRandomSource;
import net.minecraft.world.level.levelgen.synth.ImprovedNoise;
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
			List<BlockState> blockStates = List.of(Blocks.EMERALD_BLOCK.defaultBlockState(), AetherIIBlocks.HOLYSTONE.get().defaultBlockState());
//			AetherII.LOGGER.info(blockStates.toString());
			ImprovedNoise noise = new ImprovedNoise(new XoroshiroRandomSource(0)) ;
//			double noiseValue = noise.noise(x * this.noiseFreq, y * this.noiseFreq, z * this.noiseFreq);
			double noiseValue = noise.noise(x * 0.05, y * 0.05, z * 0.05);
			float normalizedNoise = (float) noiseValue * 0.5f + 0.5f; // Limits noise within 0.0 - 1.0 range.
			double clamped = Mth.clampedLerp(0, blockStates.size(), normalizedNoise);
			double finalClamp = Mth.clamp(clamped, 0, blockStates.size() - 1);
			return blockStates.get((int) finalClamp);
		};
	}

	@Override
	public KeyDispatchDataCodec<? extends SurfaceRules.RuleSource> codec() {
		return KEY_CODEC;
	}
}
