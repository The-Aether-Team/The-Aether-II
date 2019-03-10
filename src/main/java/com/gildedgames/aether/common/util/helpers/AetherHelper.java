package com.gildedgames.aether.common.util.helpers;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.registry.content.DimensionsAether;
import com.gildedgames.aether.common.registry.content.InstancesAether;
import com.gildedgames.orbis.lib.world.instances.IInstance;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

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

	public static boolean isEnabled(int dimension)
	{
		World world = DimensionManager.getWorld(dimension);

		if (world != null)
		{
			return isEnabled(world);
		}

		return isNecromancerTower(dimension) || isAether(dimension);
	}

	public static boolean isNecromancerTower(int dimension)
	{
		IInstance instance = InstancesAether.NECROMANCER_TOWER_HANDLER.getFromDimId(dimension);

		return instance != null || dimension == AetherCore.CONFIG.getNecromancerDimID();
	}

	public static boolean isAether(int dimension)
	{
		return dimension == AetherCore.CONFIG.getAetherDimID();
	}

	public static boolean isAether(World world)
	{
		if (world == null)
		{
			return false;
		}

		return world.provider.getDimensionType() == DimensionsAether.AETHER;
	}

	public static boolean isNecromancerTower(World world)
	{
		if (world == null)
		{
			return false;
		}

		return world.provider.getDimensionType() == DimensionsAether.NECROMANCER_TOWER;
	}

}
