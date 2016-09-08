package com.gildedgames.aether.common.entities.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIWander;

public class EntityAIForcedWander extends EntityAIWander
{

	private EntityCreature entity;

	private int chance;

	public EntityAIForcedWander(EntityCreature entity, double speedIn, int chance)
	{
		super(entity, speedIn, chance);

		this.entity = entity;
		this.chance = chance;
	}

	@Override
	public boolean shouldExecute()
	{
		this.makeUpdate();

		if (this.entity.getRNG().nextInt(chance) != 0)
		{
			return false;
		}

		return super.shouldExecute();
	}

}
