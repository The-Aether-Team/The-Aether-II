package com.gildedgames.aether.common.world.chunk.gen;

import net.minecraft.world.chunk.ChunkPrimer;

public interface ChunkGen
{

	void prepare(int chunkX, int chunkZ, double posX, double posZ);

	void build(ChunkPrimer primer, int chunkX, int chunkZ, double posX, double posZ);

}
