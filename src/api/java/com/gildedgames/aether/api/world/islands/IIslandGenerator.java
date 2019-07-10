package com.gildedgames.aether.api.world.islands;

import com.gildedgames.aether.api.util.OpenSimplexNoise;
import com.gildedgames.aether.api.world.IChunkInfoAether;
import com.gildedgames.aether.api.world.preparation.IChunkMask;
import com.gildedgames.aether.api.world.preparation.IChunkMaskTransformer;

import javax.annotation.Nonnull;

public interface IIslandGenerator
{
	void generateChunkSegment(IChunkInfoAether info, IChunkMask mask, IIslandData island, int chunkX, int chunkZ);

	@Nonnull
	IIslandChunkInfo generateColumnInfo(OpenSimplexNoise noise, IIslandData island, int chunkX, int chunkZ);

	IChunkMaskTransformer createMaskTransformer(IIslandData island, int chunkX, int chunkZ);
}
