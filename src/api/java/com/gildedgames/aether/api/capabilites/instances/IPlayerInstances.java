package com.gildedgames.aether.api.capabilites.instances;

import com.gildedgames.aether.api.util.BlockPosDimension;

public interface IPlayerInstances
{

	Instance getInstance();

	void setInstance(Instance instance);

	BlockPosDimension outside();

	void setOutside(BlockPosDimension pos);

}
