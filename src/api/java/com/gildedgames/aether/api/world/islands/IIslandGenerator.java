package com.gildedgames.aether.api.world.islands;

import com.gildedgames.aether.api.util.OpenSimplexNoise;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;

public interface IIslandGenerator
{

	void genIslandForChunk(OpenSimplexNoise noise, World access, final ChunkPrimer primer, final IIslandData island, final int chunkX, final int chunkZ);

}
