package com.gildedgames.aether.common.entities.ai.moa;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.player.PlayerEntity;

public class AIAvoidEntityAsChild extends AvoidEntityGoal<PlayerEntity>
{
	private final CreatureEntity entity;

	public AIAvoidEntityAsChild(CreatureEntity creature, Class<PlayerEntity> entityToAvoid, float par3, double par4, double par6)
	{
		super(creature, entityToAvoid, par3, par4, par6);

		this.entity = creature;
	}

	@Override
	public boolean shouldExecute()
	{
		return this.entity.isChild() && super.shouldExecute();
	}

}
