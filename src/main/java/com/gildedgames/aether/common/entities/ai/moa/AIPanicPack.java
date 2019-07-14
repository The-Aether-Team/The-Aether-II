package com.gildedgames.aether.common.entities.ai.moa;

import com.gildedgames.aether.common.entities.util.groups.EntityGroupMember;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.goal.PanicGoal;

public class AIPanicPack extends PanicGoal
{

	private EntityGroupMember animal;

	private final CreatureEntity entity;

	public AIPanicPack(CreatureEntity entity, double par2)
	{
		super(entity, par2);

		if (entity instanceof EntityGroupMember)
		{
			this.animal = (EntityGroupMember) entity;
		}

		this.entity = entity;
	}

	@Override
	public boolean shouldExecute()
	{
		if (this.animal.isProtective() && !this.entity.isBurning())
		{
			return false;
		}

		this.entity.setAttackTarget(this.animal.getGroup().findNearestAggressor(this.entity));

		return super.shouldExecute();

	}
}
