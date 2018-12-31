package com.gildedgames.aether.api.world.islands;

import com.gildedgames.aether.api.util.OpenSimplexNoise;
import com.gildedgames.aether.api.world.IAetherChunkColumnInfo;
import com.gildedgames.orbis_api.preparation.impl.ChunkMask;
import com.gildedgames.orbis_api.processing.IBlockAccessExtended;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;

import javax.annotation.Nonnull;

public interface IIslandGenerator
{

	void genMask(IAetherChunkColumnInfo info, ChunkMask mask, IIslandData island, int chunkX, int chunkY, int chunkZ);

	void genChunk(Biome[] biomes, OpenSimplexNoise noise, IBlockAccessExtended access, ChunkMask mask, ChunkPrimer primer, IIslandData island, int chunkX, int chunkY, int chunkZ);

	@Nonnull
	IIslandChunkColumnInfo genInfo(Biome[] biomes, OpenSimplexNoise noise, IIslandData island, int chunkX, int chunkZ);
}
