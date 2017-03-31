package com.gildedgames.aether.api.capabilites.instances;

import com.gildedgames.aether.api.util.WorldPos;

public interface IPlayerInstances
{

	Instance getInstance();

	void setInstance(Instance instance);

	WorldPos outside();

	void setOutside(WorldPos pos);

}
