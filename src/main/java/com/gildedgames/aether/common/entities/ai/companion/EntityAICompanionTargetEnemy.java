package com.gildedgames.aether.common.entities.ai.companion;

import com.gildedgames.aether.common.entities.companions.EntityCompanion;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.EntityAITarget;

public class EntityAICompanionTargetEnemy extends EntityAITarget
{

	private final EntityCompanion entity;

	private LivingEntity target;

	private int timestamp;

	public EntityAICompanionTargetEnemy(final EntityCompanion entity)
	{
		super(entity, false);

		this.entity = entity;
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
			this.target = owner.getLastAttackedEntity();
			final int i = owner.getLastAttackedEntityTime();
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
			this.timestamp = owner.getLastAttackedEntityTime();
		}

		super.startExecuting();
	}

}
