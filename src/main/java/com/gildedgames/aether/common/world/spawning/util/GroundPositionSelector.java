package com.gildedgames.aether.common.world.spawning.util;

import com.gildedgames.aether.api.world.PositionSelector;
import com.gildedgames.orbis.lib.util.mc.BlockUtil;
import net.minecraft.world.World;

public class GroundPositionSelector implements PositionSelector
{
	@Override
	public int getPosY(final World world, final int posX, final int posZ)
	{
		return BlockUtil.getTopBlockHeight(world, posX, posZ);
	}

	@Override
	public int getScatter(final World world)
	{
		return 4;
	}
}
