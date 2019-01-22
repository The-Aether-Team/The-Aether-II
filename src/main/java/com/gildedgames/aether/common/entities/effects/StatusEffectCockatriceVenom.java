package com.gildedgames.aether.common.entities.effects;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;

public class StatusEffectCockatriceVenom extends StatusEffect
{
	public StatusEffectCockatriceVenom()
	{
		super(effectTypes.COCKATRICE_VENOM, null);
	}

	@Override
	public void applyEffect(EntityLivingBase livingBase, int timer)
	{
		if (this.isEffectApplied && livingBase.getHealth() > 1 && this.effectTimer % (TICKS_PER_SECOND) == 0)
		{
			livingBase.attackEntityFrom(DamageSource.MAGIC, 1f);
		}
	}

	@Override
	public void onEffectEnd()
	{

	}
}
