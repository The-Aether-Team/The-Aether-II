package com.gildedgames.aether.common.world.spawning.util;

import com.gildedgames.aether.api.world.PositionSelector;
import net.minecraft.world.World;

public class FlyingPositionSelector implements PositionSelector
{
	@Override
	public int getPosY(World world, int posX, int posZ)
	{
		return world.rand.nextInt(world.getActualHeight());
	}

	@Override
	public int getScatter(World world)
	{
		return 4;
	}
}
