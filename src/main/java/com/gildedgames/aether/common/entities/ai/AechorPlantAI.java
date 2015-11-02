package com.gildedgames.aether.common.entities.ai;

import com.gildedgames.aether.common.entities.projectiles.EntityDart;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITarget;

public class AechorPlantAI extends EntityAITarget
{
	private int ticksUntilAttack;

	public AechorPlantAI(EntityCreature creature, boolean checkSight)
	{
		super(creature, checkSight);
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
		return this.hasTarget();
	}

	@Override
	public void updateTask()
	{
		if (this.ticksUntilAttack <= 0)
		{
			this.ticksUntilAttack = 30;

			double d1, d2;

			EntityLivingBase target = this.taskOwner.getAttackTarget();
			EntityCreature predator = this.taskOwner;

			d1 = target.posX - predator.posX;
			d2 = target.posZ - predator.posZ;

			double d4 = 0.1D + (Math.sqrt((d1 * d1) + (d2 * d2) + 0.1D) * 0.5D) + ((predator.posY - target.posY) * 0.25D);

			d1 = d1 * d4;
			d2 = d2 * d4;

			if (!predator.worldObj.isRemote)
			{
				EntityDart dart = new EntityDart(predator.worldObj, predator, 0f);
				dart.posY = predator.posY + 0.6D;

				//predator.worldObj.playSoundAtEntity(predator, "aether:aemisc.shootDart", 2.0F, 1.0F / (predator.worldObj.rand.nextFloat() * 0.4F + 0.8F));

				dart.setThrowableHeading(d1, d4, d2, 0.285F + ((float) d4 * 0.55F), 1.0F);
				dart.worldObj.spawnEntityInWorld(dart);
			}
		}

		this.ticksUntilAttack--;
	}

	public boolean hasTarget()
	{
		EntityCreature predator = this.taskOwner;

		return predator.getAttackTarget() != null && predator.isEntityAlive() && predator.getDistanceSqToEntity(predator.getAttackTarget()) < this.getTargetDistance();
	}
}
