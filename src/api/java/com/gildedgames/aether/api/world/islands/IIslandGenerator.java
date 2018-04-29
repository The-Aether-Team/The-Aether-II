package com.gildedgames.aether.api.world.islands;

import com.gildedgames.aether.api.util.OpenSimplexNoise;
import com.gildedgames.orbis_api.processing.IBlockAccessExtended;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;

public interface IIslandGenerator
{

	void genIslandForChunk(Biome[] biomes, OpenSimplexNoise noise, IBlockAccessExtended access, final ChunkPrimer primer, final IIslandData island,
			final int chunkX,
			final int chunkZ);

}
