package com.gildedgames.aether.common.world.spawning.conditions;

import com.gildedgames.aether.api.world.PosCondition;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CheckLightLevel implements PosCondition
{

	protected int lightLevel;

	public CheckLightLevel(int lightLevel)
	{
		this.lightLevel = lightLevel;
	}

	@Override
	public boolean isMet(World world, BlockPos spawnAt, BlockPos underneath)
	{
		return world.getLightFromNeighbors(spawnAt) <= lightLevel;
	}
}
