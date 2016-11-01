package com.gildedgames.aether.common.world.spawning.util;

import com.gildedgames.aether.common.util.helpers.BlockUtil;
import com.gildedgames.aether.common.world.spawning.PositionSelector;
import net.minecraft.world.World;

public class GroundPositionSelector implements PositionSelector
{
	@Override
	public int getPosY(World world, int posX, int posZ)
	{
		return BlockUtil.getTopBlockHeight(world, posX, posZ);
	}

	@Override
	public int getScatter(World world)
	{
		return 4;
	}
}
