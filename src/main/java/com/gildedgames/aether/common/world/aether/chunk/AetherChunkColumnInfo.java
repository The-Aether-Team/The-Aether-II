package com.gildedgames.aether.common.world.aether.chunk;

import com.gildedgames.aether.api.world.IAetherChunkColumnInfo;
import com.gildedgames.aether.api.world.islands.IIslandChunkColumnInfo;

public class AetherChunkColumnInfo implements IAetherChunkColumnInfo
{
	private final IIslandChunkColumnInfo[] islands;

	public AetherChunkColumnInfo(int count)
	{
		this.islands = new IIslandChunkColumnInfo[count];
	}

	@Override
	public void setIslandData(int index, IIslandChunkColumnInfo data)
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
	public <T extends IIslandChunkColumnInfo> T getIslandData(int index, Class<T> clazz)
	{
		return (T) this.islands[index];
	}
}
