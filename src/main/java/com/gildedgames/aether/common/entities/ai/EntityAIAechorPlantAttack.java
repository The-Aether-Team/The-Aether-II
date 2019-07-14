package com.gildedgames.aether.common.entities.ai;

import com.gildedgames.aether.common.entities.projectiles.EntityDart;
import com.gildedgames.aether.common.items.weapons.ItemDartType;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.TargetGoal;
import net.minecraft.util.math.MathHelper;

public class EntityAIAechorPlantAttack extends TargetGoal
{
	private int ticksUntilAttack = 3;

	public EntityAIAechorPlantAttack(final CreatureEntity creature)
	{
		super(creature, true);
	}

	@Override
	public boolean shouldExecute()
	{
		return this.hasTarget();
	}

	@Override
	public boolean shouldContinueExecuting()
	{
		final LivingEntity target = this.field_75299_d.getAttackTarget();

		if (target == null || !target.isAlive())
		{
			this.ticksUntilAttack = 20;

			return false;
		}

		return true;
	}

	@Override
	public void tick()
	{
		if (this.ticksUntilAttack <= 0)
		{
			this.ticksUntilAttack = 45;

			final LivingEntity prey = this.field_75299_d.getAttackTarget();

			if (prey == null)
			{
				return;
			}

			final MobEntity predator = this.field_75299_d;

			if (!predator.world.isRemote())
			{
				final EntityDart dart = new EntityDart(predator.world, predator);
				dart.shoot(prey.posX, prey.posY, prey.posZ, 0.6F, 1.0F);

				final double motionX = prey.posX - predator.posX;
				final double motionY = prey.getBoundingBox().minY + (double) (prey.getHeight() / 3.0F) - dart.posY;
				final double motionZ = prey.posZ - predator.posZ;

				final double accel = (double) MathHelper.sqrt(motionX * motionX + motionZ * motionZ);

				dart.shoot(motionX, motionY + accel * 0.2D, motionZ, 1.6F, 0.5f);
				dart.setDamage(0.5f);

				dart.setDartType(ItemDartType.POISON);

				dart.world.addEntity(dart);
			}
		}

		this.ticksUntilAttack--;
	}

	public boolean hasTarget()
	{
		final MobEntity predator = this.field_75299_d;

		final double maxDistance = this.getTargetDistance();

		return predator.getAttackTarget() != null && predator.isAlive()
				&& predator.getDistanceSq(predator.getAttackTarget()) < (maxDistance * maxDistance);
	}
}
