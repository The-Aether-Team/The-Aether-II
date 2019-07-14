package com.gildedgames.aether.common.entities.companions;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;

public class EntityFleetingWisp extends EntityCompanion
{

	public EntityFleetingWisp(EntityType<? extends CreatureEntity> type, World worldIn)
	{
		super(type, worldIn);

		this.stepHeight = 1.0F;
		this.isFlying = true;
	}
}
