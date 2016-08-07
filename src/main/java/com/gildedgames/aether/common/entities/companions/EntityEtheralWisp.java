package com.gildedgames.aether.common.entities.companions;

import net.minecraft.init.MobEffects;
import net.minecraft.world.World;

public class EntityEtheralWisp extends EntityBasicCompanion
{
	public EntityEtheralWisp(World worldIn)
	{
		super(worldIn);

		this.setSize(0.75f, 2.0f);
		this.setPotion(MobEffects.INVISIBILITY, 0);
	}
}
