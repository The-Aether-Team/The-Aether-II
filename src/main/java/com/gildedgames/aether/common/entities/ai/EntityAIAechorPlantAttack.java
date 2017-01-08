package com.gildedgames.aether.common.entities.ai;

import com.gildedgames.aether.common.entities.projectiles.EntityDart;
import com.gildedgames.aether.common.items.weapons.ItemDartType;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.util.math.MathHelper;

public class EntityAIAechorPlantAttack extends EntityAITarget
{
	private int ticksUntilAttack = 3;

	public EntityAIAechorPlantAttack(EntityCreature creature)
	{
		super(creature, true);
	}

	@Override
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
			this.ticksUntilAttack = 20;

			return false;
		}

		return true;
	}

	@Override
	public void updateTask()
	{
		if (this.ticksUntilAttack <= 0)
		{
			this.ticksUntilAttack = 45;

			EntityLivingBase prey = this.taskOwner.getAttackTarget();

			if (prey == null)
			{
				return;
			}

			EntityCreature predator = this.taskOwner;

			if (!predator.worldObj.isRemote)
			{
				EntityDart dart = new EntityDart(predator.worldObj, predator);
				dart.setThrowableHeading(prey.posX, prey.posY, prey.posZ, 0.6F, 1.0F);

				double motionX = prey.posX - predator.posX;
				double motionY = prey.getEntityBoundingBox().minY + (double) (prey.height / 3.0F) - dart.posY;
				double motionZ = prey.posZ - predator.posZ;

				double accel = (double) MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);

				dart.setThrowableHeading(motionX, motionY + accel * 0.2D, motionZ, 1.6F, 0.5f);
				dart.setDamage(0.5f);

				dart.setDartType(ItemDartType.POISON);

				dart.worldObj.spawnEntityInWorld(dart);
			}
		}

		this.ticksUntilAttack--;
	}

	public boolean hasTarget()
	{
		EntityCreature predator = this.taskOwner;

		double maxDistance = this.getTargetDistance();

		return predator.getAttackTarget() != null && predator.isEntityAlive()
				&& predator.getDistanceSqToEntity(predator.getAttackTarget()) < (maxDistance * maxDistance);
	}
}
