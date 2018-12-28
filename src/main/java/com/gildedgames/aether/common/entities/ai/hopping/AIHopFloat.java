package com.gildedgames.aether.common.entities.ai.hopping;

import com.gildedgames.aether.common.entities.ai.EntityAI;
import net.minecraft.entity.EntityLiving;
import net.minecraft.pathfinding.PathNavigateGround;

public class AIHopFloat extends EntityAI<EntityLiving>
{

	private final HoppingMoveHelper hoppingMoveHelper;

	public AIHopFloat(EntityLiving entity, HoppingMoveHelper hoppingMoveHelper)
	{
		super(entity);

		this.hoppingMoveHelper = hoppingMoveHelper;

		this.setMutexBits(5);
		((PathNavigateGround) this.entity().getNavigator()).setCanSwim(true);
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	@Override
	public boolean shouldExecute()
	{
		return this.entity().isInWater() || this.entity().isInLava();
	}

	/**
	 * Updates the task
	 */
	@Override
	public void updateTask()
	{
		if (this.entity().getRNG().nextFloat() < 0.8F)
		{
			this.entity().getJumpHelper().setJumping();
		}

		this.hoppingMoveHelper.setSpeed(1.2D);
	}
}
