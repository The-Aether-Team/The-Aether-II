package com.gildedgames.aether.common.entities.living.companions;

import net.minecraft.world.World;

public class EntityFrostpineTotem extends EntityCompanion
{
	public EntityFrostpineTotem(World worldIn)
	{
		super(worldIn);

		this.setSize(0.9f, 2.0f);

		this.stepHeight = 1.0F;
		this.isFlying = true;
	}
}
