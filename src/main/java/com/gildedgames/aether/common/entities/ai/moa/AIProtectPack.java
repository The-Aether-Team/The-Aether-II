package com.gildedgames.aether.common.entities.ai.moa;

import com.gildedgames.aether.common.entities.util.groups.EntityGroup;
import com.gildedgames.aether.common.entities.util.groups.EntityGroupMember;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.EntityAITarget;

public class AIProtectPack extends EntityAITarget
{

	EntityGroupMember animal;

	LivingEntity agressor;

	public AIProtectPack(CreatureEntity par1EntityCreature)
	{
		super(par1EntityCreature, false, true);
		if (par1EntityCreature instanceof EntityGroupMember)
		{
			this.animal = (EntityGroupMember) par1EntityCreature;
		}
		this.setMutexBits(1);
	}

	@Override
	public boolean shouldExecute()
	{
		EntityGroup pack = this.animal.getGroup();

		if (pack == null || !this.animal.isProtective())
		{
			return false;
		}

		this.agressor = pack.findNearestAggressor(this.taskOwner);

		if (this.agressor == null)
		{
			return false;
		}

		return this.isSuitableTarget(this.agressor, false);
	}

	@Override
	public void startExecuting()
	{
		this.taskOwner.setAttackTarget(this.agressor);
		super.startExecuting();
	}

}
