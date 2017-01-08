package com.gildedgames.aether.common.entities.ai.dungeon.labyrinth;

import com.gildedgames.aether.common.entities.ai.EntityAI;
import com.gildedgames.aether.common.entities.living.dungeon.labyrinth.EntityChestMimic;

public class AISavageAttack extends EntityAI<EntityChestMimic>
{

	private double chargeSpeed;

	private int secsOverheating, secsAttacking;

	public AISavageAttack(EntityChestMimic entity, double chargeSpeed, int secsOverheating, int secsAttacking)
	{
		super(entity);

		this.chargeSpeed = chargeSpeed;

		this.secsOverheating = secsOverheating;
		this.secsAttacking = secsAttacking;

		this.setMutexBits(1);
	}

	@Override
	public void startExecuting()
	{
		this.entity().getNavigator().tryMoveToEntityLiving(this.entity().getAttackTarget(), this.chargeSpeed);
	}

	@Override
	public boolean shouldExecute()
	{
		return this.entity().getAttackTarget() != null && this.entity().canEntityBeSeen(this.entity().getAttackTarget());
	}

	@Override
	public boolean continueExecuting()
	{
		return this.shouldExecute();
	}

	@Override
	public boolean isInterruptible()
	{
		return false;
	}

	@Override
	public void resetTask()
	{
		this.entity().getAttackTimer().reset();
		this.entity().setOverheating(false);
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
				if (this.entity().getDistanceSqToEntity(this.entity().getAttackTarget()) <= 1.5D * 1.5D)
				{
					this.entity().setAttacked(false);
					this.entity().attackEntityAsMob(this.entity().getAttackTarget());
				}
			}
		}
		else if (this.entity().getAttackTimer().getSecondsPassed() <= this.secsAttacking + this.secsOverheating)
		{
			this.entity().setOverheating(true);

			this.entity().getNavigator().clearPathEntity();
			this.entity().getNavigator().setSpeed(0.0D);
		}
		else
		{
			this.entity().getAttackTimer().reset();
		}
	}

}
