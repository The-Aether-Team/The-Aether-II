package com.gildedgames.aether.api.world;

import com.gildedgames.aether.api.world.islands.IIslandChunkColumnInfo;
import com.gildedgames.aether.api.world.preparation.IChunkColumnInfo;

public interface IAetherChunkColumnInfo extends IChunkColumnInfo
{
	void setIslandData(int index, IIslandChunkColumnInfo data);

	@SuppressWarnings("unchecked")
	<T extends IIslandChunkColumnInfo> T getIslandData(int index, Class<T> clazz);
}
