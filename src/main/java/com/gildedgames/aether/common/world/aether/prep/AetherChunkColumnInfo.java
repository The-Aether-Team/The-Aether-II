package com.gildedgames.aether.common.world.aether.prep;

import com.gildedgames.aether.api.world.IAetherChunkColumnInfo;

public class AetherChunkColumnInfo implements IAetherChunkColumnInfo
{
	private final Object[] islands;

	public AetherChunkColumnInfo(int count)
	{
		this.islands = new Object[count];
	}

	@Override
	public void setIslandData(int index, Object data)
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
	public <T> T getIslandData(int index, Class<T> clazz)
	{
		Object obj = this.islands[index];

		if (obj.getClass() != clazz)
		{
			throw new IllegalArgumentException("Expected " + clazz + ", got " + obj.getClass());
		}

		return (T) obj;
	}
}
