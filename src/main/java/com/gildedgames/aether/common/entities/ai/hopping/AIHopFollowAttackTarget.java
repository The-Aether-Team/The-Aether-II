package com.gildedgames.aether.common.entities.ai.hopping;

import com.gildedgames.aether.common.entities.ai.EntityAI;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class AIHopFollowAttackTarget extends EntityAI<EntityLiving>
{

	private final HoppingMoveHelper hoppingMoveHelper;

	private final double speed;

	private int growTieredTimer;

	public AIHopFollowAttackTarget(final EntityLiving entity, final HoppingMoveHelper hoppingMoveHelper, final double speed)
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
		final EntityLivingBase entitylivingbase = this.entity().getAttackTarget();
		return entitylivingbase != null && (entitylivingbase.isEntityAlive() && (!(entitylivingbase instanceof EntityPlayer)
				|| !((EntityPlayer) entitylivingbase).capabilities.disableDamage));
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
		final EntityLivingBase entitylivingbase = this.entity().getAttackTarget();
		return entitylivingbase != null && (entitylivingbase.isEntityAlive() && (
				!(entitylivingbase instanceof EntityPlayer && ((EntityPlayer) entitylivingbase).capabilities.disableDamage)
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
