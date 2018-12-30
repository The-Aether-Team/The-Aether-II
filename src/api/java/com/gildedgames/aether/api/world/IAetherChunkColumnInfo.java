package com.gildedgames.aether.api.world;

public interface IAetherChunkColumnInfo
{
	void setIslandData(int index, Object data);

	@SuppressWarnings("unchecked")
	<T> T getIslandData(int index, Class<T> clazz);
}
