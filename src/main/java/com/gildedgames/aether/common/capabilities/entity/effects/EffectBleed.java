package com.gildedgames.aether.common.capabilities.entity.effects;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;

public class EffectBleed extends AetherEffects
{

	public EffectBleed()
	{
		super(effectTypes.BLEED, null);
	}

	@Override
	public void applyEffect(EntityLivingBase entity)
	{
		if (this.isEffectApplied())
		{
			entity.attackEntityFrom(DamageSource.MAGIC, 10);
		}
	}
}
