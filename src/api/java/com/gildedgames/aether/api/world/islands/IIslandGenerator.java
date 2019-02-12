package com.gildedgames.aether.api.world.islands;

import com.gildedgames.aether.api.util.OpenSimplexNoise;
import com.gildedgames.aether.api.world.IAetherChunkColumnInfo;
import com.gildedgames.orbis_api.preparation.IChunkMaskTransformer;
import com.gildedgames.orbis_api.preparation.impl.ChunkSegmentMask;

import javax.annotation.Nonnull;

public interface IIslandGenerator
{

	void generateChunkSegment(IAetherChunkColumnInfo info, ChunkSegmentMask mask, IIslandData island, int chunkX, int chunkY, int chunkZ);

	@Nonnull
	IIslandChunkColumnInfo generateColumnInfo(OpenSimplexNoise noise, IIslandData island, int chunkX, int chunkZ);

	IChunkMaskTransformer createMaskTransformer(IIslandData island, int chunkX, int chunkZ);
}
