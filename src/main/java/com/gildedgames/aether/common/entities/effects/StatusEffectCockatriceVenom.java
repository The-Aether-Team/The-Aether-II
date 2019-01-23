package com.gildedgames.aether.common.entities.effects;

import com.gildedgames.aether.common.capabilities.entity.effects.EffectsDamageSource;
import net.minecraft.entity.EntityLivingBase;

public class StatusEffectCockatriceVenom extends StatusEffect
{
	public StatusEffectCockatriceVenom(EntityLivingBase livingBase)
	{
		super(effectTypes.COCKATRICE_VENOM, null, livingBase);
	}

	@Override
	public void applyEffect(EntityLivingBase livingBase, int timer)
	{
		if (this.isEffectApplied && livingBase.getHealth() > 1 && this.effectTimer % (TICKS_PER_SECOND * 2) == 0)
		{
			livingBase.attackEntityFrom(EffectsDamageSource.COCKATRICE_VENOM, 1f);
		}
	}

	@Override
	public void onEffectEnd()
	{

	}
}
