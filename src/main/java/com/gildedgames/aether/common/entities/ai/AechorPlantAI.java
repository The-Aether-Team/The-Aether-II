package com.gildedgames.aether.common.entities.ai;

import com.gildedgames.aether.common.entities.projectiles.EntityDart;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITarget;

public class AechorPlantAI extends EntityAITarget
{
	private int ticksUntilAttack = 3;

	public AechorPlantAI(EntityCreature creature)
	{
		super(creature, true);
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean shouldExecute()
	{
		return this.hasTarget();
	}

	@Override
	public boolean continueExecuting()
	{
		EntityLivingBase target = this.taskOwner.getAttackTarget();

		if (target == null || !target.isEntityAlive())
		{
			this.ticksUntilAttack = 3;

			return false;
		}

		return true;
	}

	@Override
	public void updateTask()
	{
		if (this.ticksUntilAttack <= 0)
		{
			this.ticksUntilAttack = 30;

			EntityLivingBase target = this.taskOwner.getAttackTarget();
			EntityCreature predator = this.taskOwner;

			if (!predator.worldObj.isRemote)
			{
				EntityDart dart = new EntityDart(predator.worldObj, predator, target, 0.6F, 3.0F);

				dart.worldObj.spawnEntityInWorld(dart);
			}
		}

		this.ticksUntilAttack--;
	}

	public boolean hasTarget()
	{
		EntityCreature predator = this.taskOwner;

		double maxDistance = this.getTargetDistance();

		return predator.getAttackTarget() != null && predator.isEntityAlive() && predator.getDistanceSqToEntity(predator.getAttackTarget()) < (maxDistance * maxDistance);
	}
}
