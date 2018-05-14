package com.gildedgames.aether.api.world;

import java.util.Collection;

public interface ISpawnSystem
{
	Collection<ISpawnHandler> getSpawnHandlers();

	void tick();
}
