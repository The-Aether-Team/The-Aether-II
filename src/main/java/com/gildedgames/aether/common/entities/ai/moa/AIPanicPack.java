package com.gildedgames.aether.common.entities.ai.moa;

import com.gildedgames.aether.common.entities.util.groups.EntityGroupMember;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.EntityAIPanic;

public class AIPanicPack extends EntityAIPanic
{

	EntityGroupMember animal;

	final CreatureEntity theEntityCreature;

	public AIPanicPack(CreatureEntity par1EntityCreature, double par2)
	{
		super(par1EntityCreature, par2);

		if (par1EntityCreature instanceof EntityGroupMember)
		{
			this.animal = (EntityGroupMember) par1EntityCreature;
		}

		this.theEntityCreature = par1EntityCreature;
	}

	@Override
	public boolean shouldExecute()
	{
		if (this.animal.isProtective() && !this.theEntityCreature.isBurning())
		{
			return false;
		}

		this.theEntityCreature.setAttackTarget(this.animal.getGroup().findNearestAggressor(this.theEntityCreature));

		return super.shouldExecute();

	}
}
