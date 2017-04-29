package com.gildedgames.aether.common.entities.living.companions;

import net.minecraft.world.World;

public class EntitySoaringWisp extends EntityCompanion
{

	public EntitySoaringWisp(World worldIn)
	{
		super(worldIn);

		this.setSize(0.75f, 2.0f);

		this.stepHeight = 1.0F;
		this.isFlying = true;
	}
}
