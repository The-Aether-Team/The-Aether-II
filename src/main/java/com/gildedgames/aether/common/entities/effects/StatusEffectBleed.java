package com.gildedgames.aether.common.entities.effects;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;

public class StatusEffectBleed extends StatusEffect
{
	public StatusEffectBleed()
	{
		super(effectTypes.BLEED, null);
	}

	@Override
	public void applyEffect(EntityLivingBase livingBase, int timer)
	{
		if (this.isEffectApplied)
		{
			livingBase.attackEntityFrom(DamageSource.MAGIC, 10);
		}
	}

	@Override
	public void onEffectEnd()
	{

	}

}
