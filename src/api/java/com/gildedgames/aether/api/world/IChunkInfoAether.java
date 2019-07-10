package com.gildedgames.aether.api.world;

import com.gildedgames.aether.api.world.islands.IIslandChunkInfo;
import com.gildedgames.aether.api.world.preparation.IChunkInfo;

public interface IChunkInfoAether extends IChunkInfo
{
	void setIslandData(int index, IIslandChunkInfo data);

	@SuppressWarnings("unchecked")
	<T extends IIslandChunkInfo> T getIslandData(int index, Class<T> clazz);
}
