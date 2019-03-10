package com.gildedgames.aether.api.world.islands;

import com.gildedgames.aether.api.util.OpenSimplexNoise;
import com.gildedgames.aether.api.world.IAetherChunkColumnInfo;
import com.gildedgames.orbis.lib.preparation.IChunkMaskTransformer;
import com.gildedgames.orbis.lib.preparation.impl.ChunkMask;

import javax.annotation.Nonnull;

public interface IIslandGenerator
{

	void generateChunkSegment(IAetherChunkColumnInfo info, ChunkMask mask, IIslandData island, int chunkX, int chunkZ);

	@Nonnull
	IIslandChunkColumnInfo generateColumnInfo(OpenSimplexNoise noise, IIslandData island, int chunkX, int chunkZ);

	IChunkMaskTransformer createMaskTransformer(IIslandData island, int chunkX, int chunkZ);
}
