package com.gildedgames.aether.common.entities.ai;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.pathfinding.GroundPathNavigator;

public class EntityAIRestrictRain extends Goal
{
	private final CreatureEntity entity;

	public EntityAIRestrictRain(CreatureEntity creature)
	{
		this.entity = creature;
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	@Override
	public boolean shouldExecute()
	{
		return this.entity.world.isRaining();
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	@Override
	public void startExecuting()
	{
		((GroundPathNavigator) this.entity.getNavigator()).setAvoidSun(true);
	}

	/**
	 * Reset the task's internal state. Called when this task is interrupted by another one
	 */
	@Override
	public void resetTask()
	{
		((GroundPathNavigator) this.entity.getNavigator()).setAvoidSun(false);
	}
}