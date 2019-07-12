package com.gildedgames.aether.common.entities.ai.tempest;

import com.gildedgames.aether.api.entity.effects.EEffectIntensity;
import com.gildedgames.aether.api.entity.effects.IAetherStatusEffectIntensity;
import com.gildedgames.aether.api.entity.effects.IAetherStatusEffects;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.ai.EntityAI;
import com.gildedgames.aether.common.entities.effects.StatusEffectStun;
import com.gildedgames.aether.common.entities.monsters.EntityTempest;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.util.SoundEvent;

public class AIElectricShock extends EntityAI<EntityTempest>
{

	private int attackTimer;

	private int cooldownTimer;

	public AIElectricShock(final EntityTempest entity)
	{
		super(entity);
	}

	@Override
	public void startExecuting()
	{
		final IAttributeInstance movementSpeed = this.entity().getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);

		this.entity().getNavigator().tryMoveToEntityLiving(this.entity().getAttackTarget(),
				movementSpeed != null ? movementSpeed.getAttributeValue() : 1.0D);
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
		this.attackTimer = 0;
		this.cooldownTimer = 0;
	}

	@Override
	public void updateTask()
	{
		final LivingEntity prey = this.entity().getAttackTarget();

		final double distanceToAttackTarget = this.entity().getDistance(prey);

		this.cooldownTimer++;

		if (distanceToAttackTarget < 7.0D)
		{
			if (this.cooldownTimer >= 60)
			{
				if (this.attackTimer == 0)
				{
					this.entity().playSound(new SoundEvent(AetherCore.getResource("mob.zephyr.puff")), 1.0F,
							(this.entity().getRNG().nextFloat() - this.entity().getRNG().nextFloat()) * 0.2F + 1.0F);
				}

				this.attackTimer++;

				this.entity().getNavigator().clearPath();

				if (this.attackTimer >= 40)
				{
					this.entity().playSound(new SoundEvent(AetherCore.getResource("mob.tempest.electric_shock")), 1.0F,
							(this.entity().getRNG().nextFloat() - this.entity().getRNG().nextFloat()) * 0.2F + 1.0F);

					if (this.entity().getRNG().nextInt(8) == 0)
					{
						this.entity().playSound(new SoundEvent(AetherCore.getResource("mob.tempest.angry")), 1.0F,
								(this.entity().getRNG().nextFloat() - this.entity().getRNG().nextFloat()) * 0.2F + 1.0F);
					}

					this.entity().setAttacked(false);
					this.entity().attackEntityAsMob(prey);

					this.applyStatusEffectOnAttack(prey);

					this.attackTimer = 0;
					this.cooldownTimer = 0;
				}
			}
		}
		else if (this.entity().getNavigator().noPath())
		{
			this.startExecuting();
		}
	}


	private void applyStatusEffectOnAttack(final Entity target)
	{
		if (target instanceof LivingEntity)
		{
			final LivingEntity living = (LivingEntity) target;

			if (!living.isActiveItemStackBlocking())
			{
				int buildup = IAetherStatusEffectIntensity.getBuildupFromEffect(new StatusEffectStun(living), EEffectIntensity.MAJOR);
				IAetherStatusEffects.applyStatusEffect(living, IAetherStatusEffects.effectTypes.STUN, buildup);
			}
		}
	}
}
