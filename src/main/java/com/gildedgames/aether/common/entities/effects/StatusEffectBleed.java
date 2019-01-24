package com.gildedgames.aether.common.entities.effects;

import com.gildedgames.aether.api.effects_system.EEffectIntensity;
import com.gildedgames.aether.common.capabilities.entity.effects.EffectsDamageSource;
import net.minecraft.entity.EntityLivingBase;

public class StatusEffectBleed extends StatusEffect
{
	public StatusEffectBleed(EntityLivingBase livingBase)
	{
		super(effectTypes.BLEED, null, livingBase);
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

	@Override
	public int getBuildupFromIntensity(EEffectIntensity intensity)
	{
		switch (intensity)
		{
			case MINOR:
				return 3;
			case ORDINARY:
				return 10;
			case MAJOR:
				return 35;
		}

		return 0;
	}

}
