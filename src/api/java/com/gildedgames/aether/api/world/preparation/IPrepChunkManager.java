package com.gildedgames.aether.api.world.preparation;

import net.minecraft.world.World;

/**
 * Actually manages the chunks within a world.
 */
public interface IPrepChunkManager<T extends IChunkInfo>
{
	World getWorld();

	IChunkMask getChunk(IPrepSectorData sectorData, final int chunkX, final int chunkZ);

	IChunkMaskTransformer createMaskTransformer();
}
