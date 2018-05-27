package com.gildedgames.aether.api.world.islands;

import com.gildedgames.aether.api.util.OpenSimplexNoise;
import com.gildedgames.orbis_api.preparation.impl.ChunkMask;
import com.gildedgames.orbis_api.processing.IBlockAccessExtended;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;

public interface IIslandGenerator
{

	void genMask(Biome[] biomes, OpenSimplexNoise noise, IBlockAccessExtended access, final ChunkMask mask, final IIslandData island,
			final int chunkX,
			final int chunkZ);

	void genChunk(Biome[] biomes, OpenSimplexNoise noise, IBlockAccessExtended access, ChunkMask mask, ChunkPrimer primer, IIslandData island, int chunkX, int chunkZ);
}
