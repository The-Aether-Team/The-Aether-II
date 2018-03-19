package com.gildedgames.aether.common.world.aether.island.gen;

import com.gildedgames.aether.api.world.islands.IIslandData;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;

public interface IIslandGenerator
{

	void genIslandForChunk(World world, final ChunkPrimer primer, final IIslandData island, final int chunkX, final int chunkZ);

}
