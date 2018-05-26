package com.gildedgames.aether.common.entities.ai;

import com.gildedgames.aether.api.world.islands.IIslandData;
import com.gildedgames.aether.api.world.islands.precipitation.PrecipitationType;
import com.gildedgames.aether.common.util.helpers.IslandHelper;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.pathfinding.PathNavigateGround;

public class EntityAIRestrictRain extends EntityAIBase
{
	private final EntityCreature entity;

	public EntityAIRestrictRain(EntityCreature creature)
	{
		this.entity = creature;
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	@Override
	public boolean shouldExecute()
	{
		IIslandData island = IslandHelper.get(this.entity.world, this.entity.chunkCoordX, this.entity.chunkCoordZ);

		return island != null && island.getPrecipitation().getType() == PrecipitationType.RAIN;
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	@Override
	public void startExecuting()
	{
		((PathNavigateGround) this.entity.getNavigator()).setAvoidSun(true);
	}

	/**
	 * Reset the task's internal state. Called when this task is interrupted by another one
	 */
	@Override
	public void resetTask()
	{
		((PathNavigateGround) this.entity.getNavigator()).setAvoidSun(false);
	}
}