package com.gildedgames.aether.common.entities.effects;

import com.gildedgames.aether.common.capabilities.entity.effects.EffectsDamageSource;
import net.minecraft.entity.EntityLivingBase;

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
			livingBase.attackEntityFrom(EffectsDamageSource.BLEED, 10);
		}
	}

	@Override
	public void onEffectEnd()
	{

	}

}
