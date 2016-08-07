package com.gildedgames.aether.common.entities.companions;

import net.minecraft.init.MobEffects;
import net.minecraft.world.World;

public class EntityFrostpineTotem extends EntityBasicCompanion
{
	public EntityFrostpineTotem(World worldIn)
	{
		super(worldIn);

		this.setSize(0.9f, 2.1f);
		this.setPotion(MobEffects.NIGHT_VISION, 0);
	}
}
