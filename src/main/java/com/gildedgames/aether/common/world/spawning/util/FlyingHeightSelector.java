package com.gildedgames.aether.common.world.spawning.util;

import com.gildedgames.aether.common.world.spawning.HeightSelector;
import com.gildedgames.util.core.util.GGHelper;
import net.minecraft.world.World;

public class FlyingHeightSelector implements HeightSelector
{
	@Override
	public int getPosY(World world, int posX, int posZ)
	{
		return world.rand.nextInt(world.getActualHeight());
	}
}
