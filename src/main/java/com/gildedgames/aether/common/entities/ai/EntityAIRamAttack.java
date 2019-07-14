package com.gildedgames.aether.common.entities.ai;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.Goal;

import java.util.List;
import java.util.Random;

public class EntityAIRamAttack extends Goal
{
	private MobEntity entity;

	private LivingEntity target;

	private double maxDistance, chargeSpeed;

	private final float minCharge, maxCharge;

	private float currentCharge;

	private boolean ramming;

	private Random rand;

	public EntityAIRamAttack(MobEntity entity, double chargeSpeed, float minChargeTime, float maxChargeTime, double maxDistance)
	{
		this.rand = new Random();
		this.entity = entity;
		this.chargeSpeed = chargeSpeed;
		this.minCharge = minChargeTime;
		this.maxCharge = maxChargeTime;
		this.maxDistance = maxDistance;
	}

	@Override
	public void tick()
	{
		if (this.shouldExecute())
		{
			if (this.ramming)
			{
				this.entity.getLookController().setLookPositionWithEntity(this.target, (float)this.entity.getHorizontalFaceSpeed(), (float)this.entity.getVerticalFaceSpeed());
				this.entity.faceEntity(this.target, 180f, 180f);

				List<LivingEntity> entities = this.entity.world.getEntitiesWithinAABB(LivingEntity.class, this.entity.getBoundingBox().expand(1, 1, 1));

				if (entities.contains(this.target))
				{
					this.ramming = false;
					this.entity.attackEntityAsMob(this.target);
					this.target = null;
				}
			}
		}
		else
		{
			this.target = null;
		}
	}

	@Override
	public boolean shouldExecute()
	{
		return this.target != null && this.entity.getDistance(this.target) < this.maxDistance;
	}

	public void setTarget(LivingEntity target)
	{
		if (this.target == null || this.currentCharge <= 0)
		{
			this.target = target;
			this.currentCharge = (this.minCharge + (this.maxCharge - this.minCharge) * this.rand.nextFloat()) * 20;
		}
		else if (this.target.equals(target))
		{
			this.currentCharge /= 1.2f;
		}
	}

	public void update()
	{
		if (this.target == null)
		{
			return;
		}

		if (this.ramming)
		{
			LivingEntity target = this.target;

			double ang = Math.atan2(target.posZ - this.entity.posZ, target.posX - this.entity.posX);

			this.entity.setMotion((float) Math.cos(ang) * this.chargeSpeed, this.entity.getMotion().getY(), (float) Math.sin(ang) * this.chargeSpeed);
			this.entity.getLookController().setLookPositionWithEntity(this.target, (float)this.entity.getHorizontalFaceSpeed(), (float)this.entity.getVerticalFaceSpeed());
			this.entity.faceEntity(this.target, 360f, 360f);
		}
		else if (this.currentCharge > 0)
		{
			this.currentCharge--;

			if (this.currentCharge <= 0)
			{
				this.ramming = true;
			}
		}
	}

	public void setMaxDistance(double maxDistance)
	{
		this.maxDistance = maxDistance;
	}

}
