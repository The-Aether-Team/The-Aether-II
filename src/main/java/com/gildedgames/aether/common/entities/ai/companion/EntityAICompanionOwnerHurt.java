package com.gildedgames.aether.common.entities.ai.companion;

import com.gildedgames.aether.common.entities.living.companions.EntityCompanion;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITarget;

public class EntityAICompanionOwnerHurt extends EntityAITarget
{

	private EntityLivingBase target;

	private final EntityCompanion entity;

	private int timestamp;

	public EntityAICompanionOwnerHurt(EntityCompanion entity)
	{
		super(entity, false);

		this.entity = entity;

		this.setMutexBits(1);
	}

	@Override
	public boolean shouldExecute()
	{
		EntityLivingBase owner = this.entity.getOwner();

		if (owner == null)
		{
			return false;
		}
		else
		{
			this.target = owner.getAITarget();
			int i = owner.getRevengeTimer();
			return i != this.timestamp && this.isSuitableTarget(this.target, false) && this.target != owner;
		}
	}

	@Override
	public void startExecuting()
	{
		if (this.target == this.entity.getOwner())
		{
			return;
		}

		this.taskOwner.setAttackTarget(this.target);
		EntityLivingBase owner = this.entity.getOwner();

		if (owner != null)
		{
			this.timestamp = owner.getRevengeTimer();
		}

		super.startExecuting();
	}

}
