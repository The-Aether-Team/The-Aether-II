package com.gildedgames.aether.common.entities.companions;

import net.minecraft.potion.Potion;
import net.minecraft.world.World;

public class EntitySoaringWisp extends EntityBasicCompanion
{
	public EntitySoaringWisp(World worldIn)
	{
		super(worldIn);

		this.setSize(0.75f, 2.0f);
		this.setPotion(Potion.getPotionFromResourceLocation("speed"), 0);
	}
}
