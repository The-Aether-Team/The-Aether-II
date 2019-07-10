package com.gildedgames.aether.common.world.spawning.conditions;

import com.gildedgames.aether.api.world.spawn.conditions.IConditionPosition;
import com.gildedgames.aether.api.world.spawn.conditions.IConditionWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CheckTime implements IConditionPosition, IConditionWorld
{

	private final Time time;

	public CheckTime(Time time)
	{
		this.time = time;
	}

	@Override
	public boolean isMet(World world, BlockPos spawnAt, BlockPos underneath)
	{
		return this.isMet(world);
	}

	@Override
	public boolean isMet(World world)
	{
		return (this.time == Time.DAY) == world.isDaytime();
	}

	public enum Time
	{
		DAY, NIGHT
	}

}
