package com.gildedgames.aether.common.entities.ai.hopping;

import com.gildedgames.aether.common.entities.ai.EntityAI;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.pathfinding.GroundPathNavigator;

import java.util.EnumSet;

public class AIHopFloat extends EntityAI<MobEntity>
{

	private final HoppingMoveHelper hoppingMoveHelper;

	public AIHopFloat(MobEntity entity, HoppingMoveHelper hoppingMoveHelper)
	{
		super(entity);

		this.hoppingMoveHelper = hoppingMoveHelper;

		this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.JUMP));
		this.entity().getNavigator().setCanSwim(true);
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
	public void tick()
	{
		if (this.entity().getRNG().nextFloat() < 0.8F)
		{
			this.entity().getJumpController().setJumping();
		}

		this.hoppingMoveHelper.setSpeed(1.2D);
	}
}
