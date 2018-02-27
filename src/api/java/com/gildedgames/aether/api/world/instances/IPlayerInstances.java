package com.gildedgames.aether.api.world.instances;

import com.gildedgames.aether.api.util.BlockPosDimension;

public interface IPlayerInstances
{

	IInstance getInstance();

	void setInstance(IInstance instance);

	BlockPosDimension outside();

	void setOutside(BlockPosDimension pos);

}
