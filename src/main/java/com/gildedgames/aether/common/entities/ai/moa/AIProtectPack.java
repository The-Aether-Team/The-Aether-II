package com.gildedgames.aether.common.entities.ai.moa;

import com.gildedgames.aether.common.entities.util.groups.EntityGroup;
import com.gildedgames.aether.common.entities.util.groups.EntityGroupMember;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.TargetGoal;

import java.util.EnumSet;

public class AIProtectPack extends TargetGoal
{

	private EntityGroupMember animal;

	private LivingEntity agressor;

	public AIProtectPack(CreatureEntity entity)
	{
		super(entity, false, true);
		if (entity instanceof EntityGroupMember)
		{
			this.animal = (EntityGroupMember) entity;
		}
		this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
	}

	@Override
	public boolean shouldExecute()
	{
		EntityGroup pack = this.animal.getGroup();

		if (pack == null || !this.animal.isProtective())
		{
			return false;
		}

		this.agressor = pack.findNearestAggressor(this.field_75299_d);

		if (this.agressor == null)
		{
			return false;
		}

		return this.func_220777_a(this.agressor, EntityPredicate.DEFAULT);
	}

	@Override
	public void startExecuting()
	{
		this.field_75299_d.setAttackTarget(this.agressor);
		super.startExecuting();
	}

}
