package com.gildedgames.aether.common.entities.ai.companion;

import com.gildedgames.aether.common.entities.companions.EntityCompanion;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.EntityAITarget;

public class EntityAICompanionOwnerHurt extends EntityAITarget
{

	private final EntityCompanion entity;

	private LivingEntity target;

	private int timestamp;

	public EntityAICompanionOwnerHurt(final EntityCompanion entity)
	{
		super(entity, false);

		this.entity = entity;

		this.setMutexBits(1);
	}

	@Override
	public boolean shouldExecute()
	{
		final LivingEntity owner = this.entity.getOwner();

		if (owner == null)
		{
			return false;
		}
		else
		{
			this.target = owner.getRevengeTarget();
			final int i = owner.getRevengeTimer();
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
		final LivingEntity owner = this.entity.getOwner();

		if (owner != null)
		{
			this.timestamp = owner.getRevengeTimer();
		}

		super.startExecuting();
	}

}
