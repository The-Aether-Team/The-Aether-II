package com.gildedgames.aether.common.world.aether;

import com.gildedgames.aether.api.world.IChunkInfoAether;
import com.gildedgames.aether.api.world.islands.IIslandChunkInfo;

public class ChunkInfoAether implements IChunkInfoAether
{
	private final IIslandChunkInfo[] islands;

	public ChunkInfoAether(int count)
	{
		this.islands = new IIslandChunkInfo[count];
	}

	@Override
	public void setIslandData(int index, IIslandChunkInfo data)
	{
		if (data == null)
		{
			throw new IllegalArgumentException("Data must not be null");
		}

		if (this.islands[index] != null)
		{
			throw new IllegalArgumentException("Data already set for index " + index);
		}

		this.islands[index] = data;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends IIslandChunkInfo> T getIslandData(int index, Class<T> clazz)
	{
		return (T) this.islands[index];
	}
}
