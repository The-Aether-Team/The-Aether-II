package com.gildedgames.aether.common.util.helpers;

import com.gildedgames.aether.common.registry.content.DimensionsAether;
import net.minecraft.world.World;

public class AetherHelper
{

	public static boolean isEnabled(World world)
	{
		if (world == null)
		{
			return false;
		}

		return world.provider.getDimensionType() == DimensionsAether.AETHER || world.provider.getDimensionType() == DimensionsAether.NECROMANCER_TOWER;
	}

}
