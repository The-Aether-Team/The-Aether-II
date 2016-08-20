package com.gildedgames.aether.common.entities.ai.tempest;

import com.gildedgames.aether.common.SoundsAether;
import com.gildedgames.aether.common.entities.ai.EntityAI;
import com.gildedgames.aether.common.util.TickTimer;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;

public class AIElectricShock extends EntityAI<EntityLiving>
{

	private TickTimer attackTimer = new TickTimer();

	private TickTimer cooldownTimer = new TickTimer();

	public AIElectricShock(EntityLiving entity)
	{
		super(entity);
	}

	@Override
	public void startExecuting()
	{
		IAttributeInstance movementSpeed = this.entity().getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);

		this.entity().getNavigator().tryMoveToEntityLiving(this.entity().getAttackTarget(), movementSpeed != null ? movementSpeed.getAttributeValue() : 1.0D);
	}

	@Override
	public boolean shouldExecute()
	{
		return this.entity().getAttackTarget() != null && this.entity().canEntityBeSeen(this.entity().getAttackTarget());
	}

	@Override
	public boolean isInterruptible()
	{
		return false;
	}

	@Override
	public void resetTask()
	{
		this.attackTimer.reset();
		this.cooldownTimer.reset();
	}

	@Override
	public void updateTask()
	{
		double distanceToAttackTarget = this.entity().getDistanceToEntity(this.entity().getAttackTarget());

		this.cooldownTimer.tick();

		if (distanceToAttackTarget < 2.0D)
		{
			if (this.cooldownTimer.getSecondsPassed() >= 3)
			{
				if (this.attackTimer.getTicksPassed() == 0)
				{
					this.entity().playSound(SoundsAether.zephyr_puff, 1.0F, (this.entity().getRNG().nextFloat() - this.entity().getRNG().nextFloat()) * 0.2F + 1.0F);
				}

				this.attackTimer.tick();

				this.entity().getNavigator().clearPathEntity();

				if (this.attackTimer.getSecondsPassed() >= 2)
				{
					this.entity().playSound(SoundsAether.tempest_electric_shock, 1.0F, (this.entity().getRNG().nextFloat() - this.entity().getRNG().nextFloat()) * 0.2F + 1.0F);

					if (this.entity().getRNG().nextInt(8) == 0)
					{
						this.entity().playSound(SoundsAether.tempest_angry, 1.0F, (this.entity().getRNG().nextFloat() - this.entity().getRNG().nextFloat()) * 0.2F + 1.0F);
					}

					this.entity().attackEntityAsMob(this.entity().getAttackTarget());

					this.entity().getAttackTarget().addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 40, 3));

					this.attackTimer.reset();
					this.cooldownTimer.reset();
				}
			}
		}
		else if (this.entity().getNavigator().noPath())
		{
			this.startExecuting();
		}
	}

}
