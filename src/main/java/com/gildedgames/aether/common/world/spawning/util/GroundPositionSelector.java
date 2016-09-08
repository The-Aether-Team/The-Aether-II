package com.gildedgames.aether.common.world.spawning.util;

import com.gildedgames.aether.common.world.spawning.PositionSelector;
import com.gildedgames.util.core.util.GGHelper;
import net.minecraft.world.World;

public class GroundPositionSelector implements PositionSelector
{
	@Override
	public int getPosY(World world, int posX, int posZ)
	{
		return GGHelper.getTopBlockHeight(world, posX, posZ);
	}

	@Override
	public int getScatter(World world)
	{
		return 4;
	}
}
