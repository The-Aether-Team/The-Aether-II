package com.gildedgames.aether.common.entities.ai.dungeon.labyrinth;

import com.gildedgames.aether.common.entities.ai.EntityAI;
import com.gildedgames.aether.common.entities.living.dungeon.labyrinth.EntitySentryGuardian;

public class AISentryGuardianMelee extends EntityAI<EntitySentryGuardian>
{

	private double chargeSpeed;

	private int secsAttacking;

	public AISentryGuardianMelee(EntitySentryGuardian entity, double chargeSpeed, int secsAttacking)
	{
		super(entity);

		this.chargeSpeed = chargeSpeed;

		this.secsAttacking = secsAttacking;

		this.setMutexBits(1);
	}

	@Override
	public void startExecuting()
	{
		this.entity().getAttackTimer().reset();
		this.entity().getNavigator().tryMoveToEntityLiving(this.entity().getAttackTarget(), this.chargeSpeed);
	}

	@Override
	public boolean shouldExecute()
	{
		return this.entity().getAttackTarget() != null && this.entity().canEntityBeSeen(this.entity().getAttackTarget()) && !this.entity().isOverheating();
	}

	@Override
	public boolean continueExecuting()
	{
		return this.shouldExecute();
	}

	@Override
	public boolean isInterruptible()
	{
		return true;
	}

	@Override
	public void resetTask()
	{

	}

	@Override
	public void updateTask()
	{
		this.entity().getAttackTimer().tick();

		if (this.entity().getAttackTimer().getSecondsPassed() <= this.secsAttacking)
		{
			this.entity().setOverheating(false);

			if (this.entity().getNavigator().noPath())
			{
				this.entity().getNavigator().tryMoveToEntityLiving(this.entity().getAttackTarget(), this.chargeSpeed);
			}

			if (this.entity().getAttackTimer().isMultipleOfTicks(10))
			{
				if (this.entity().getDistanceSqToEntity(this.entity().getAttackTarget()) <= 4.5D * 4.5D)
				{
					this.entity().attackEntityAsMob(this.entity().getAttackTarget());
				}
			}
		}
		else
		{
			this.entity().setOverheating(true);

			this.entity().getNavigator().clearPathEntity();
		}
	}

}
