package com.gildedgames.aether.api.world.islands;

import com.gildedgames.aether.api.util.OpenSimplexNoise;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.chunk.ChunkPrimer;

public interface IIslandGenerator
{

	void genIslandForChunk(OpenSimplexNoise noise, IBlockAccess access, final ChunkPrimer primer, final IIslandData island, final int chunkX, final int chunkZ);

}
