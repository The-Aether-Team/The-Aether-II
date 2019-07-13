package com.gildedgames.aether.common.world.spawning.conditions;

import com.gildedgames.aether.api.world.spawn.conditions.IConditionPosition;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CheckLightLevel implements IConditionPosition
{

	protected final int lightLevel;

	public CheckLightLevel(int lightLevel)
	{
		this.lightLevel = lightLevel;
	}

	@Override
	public boolean isMet(World world, BlockPos spawnAt, BlockPos underneath)
	{
		return world.getBrightness(spawnAt) <= this.lightLevel;
	}
}
