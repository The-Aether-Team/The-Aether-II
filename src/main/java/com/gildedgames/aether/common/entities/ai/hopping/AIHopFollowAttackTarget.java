package com.gildedgames.aether.common.entities.ai.hopping;

import com.gildedgames.aether.common.entities.ai.EntityAI;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;

public class AIHopFollowAttackTarget extends EntityAI<MobEntity>
{

	private final HoppingMoveHelper hoppingMoveHelper;

	private final double speed;

	private int growTieredTimer;

	public AIHopFollowAttackTarget(final MobEntity entity, final HoppingMoveHelper hoppingMoveHelper, final double speed)
	{
		super(entity);

		this.hoppingMoveHelper = hoppingMoveHelper;
		this.speed = speed;

		this.setMutexBits(2);
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	@Override
	public boolean shouldExecute()
	{
		final LivingEntity entitylivingbase = this.entity().getAttackTarget();
		return entitylivingbase != null && (entitylivingbase.isAlive() && (!(entitylivingbase instanceof PlayerEntity)
				|| !((PlayerEntity) entitylivingbase).capabilities.disableDamage));
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	@Override
	public void startExecuting()
	{
		this.growTieredTimer = 300;
		super.startExecuting();
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	@Override
	public boolean shouldContinueExecuting()
	{
		final LivingEntity entitylivingbase = this.entity().getAttackTarget();
		return entitylivingbase != null && (entitylivingbase.isAlive() && (
				!(entitylivingbase instanceof PlayerEntity && ((PlayerEntity) entitylivingbase).capabilities.disableDamage)
						&& --this.growTieredTimer > 0));
	}

	/**
	 * Updates the task
	 */
	@Override
	public void updateTask()
	{
		this.entity().faceEntity(this.entity().getAttackTarget(), 10.0F, 10.0F);
		this.hoppingMoveHelper.setSpeed(this.speed);
		this.hoppingMoveHelper.setDirection(this.entity().rotationYaw);
	}

}
