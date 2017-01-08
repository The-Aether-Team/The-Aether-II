package com.gildedgames.aether.common.entities.ai.hopping;

import com.gildedgames.aether.common.entities.ai.EntityAI;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class AIHopFollowAttackTarget extends EntityAI<EntityLiving>
{

	private HoppingMoveHelper hoppingMoveHelper;

	private int growTieredTimer;

	private double speed;

	public AIHopFollowAttackTarget(EntityLiving entity, HoppingMoveHelper hoppingMoveHelper, double speed)
	{
		super(entity);

		this.hoppingMoveHelper = hoppingMoveHelper;
		this.speed = speed;

		this.setMutexBits(2);
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	public boolean shouldExecute()
	{
		EntityLivingBase entitylivingbase = this.entity().getAttackTarget();
		return entitylivingbase != null && (entitylivingbase.isEntityAlive() && (!(entitylivingbase instanceof EntityPlayer)
				|| !((EntityPlayer) entitylivingbase).capabilities.disableDamage));
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	public void startExecuting()
	{
		this.growTieredTimer = 300;
		super.startExecuting();
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	public boolean continueExecuting()
	{
		EntityLivingBase entitylivingbase = this.entity().getAttackTarget();
		return entitylivingbase != null && (entitylivingbase.isEntityAlive() && (
				!(entitylivingbase instanceof EntityPlayer && ((EntityPlayer) entitylivingbase).capabilities.disableDamage)
						&& --this.growTieredTimer > 0));
	}

	/**
	 * Updates the task
	 */
	public void updateTask()
	{
		this.entity().faceEntity(this.entity().getAttackTarget(), 10.0F, 10.0F);
		this.hoppingMoveHelper.setSpeed(this.speed);
		this.hoppingMoveHelper.setDirection(this.entity().rotationYaw);
	}

}
