package com.gildedgames.aether.common.capabilities.entity.effects;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;

public class EffectCockatriceVenom extends AetherEffects
{
	public EffectCockatriceVenom()
	{
		super(effectTypes.COCKATRICE_VENOM, null);
	}

	@Override
	public void applyEffect(EntityLivingBase entity)
	{
		if (this.isEffectApplied() && entity.getHealth() > 1 && this.effectTimer % (TICKS_PER_SECOND) == 0)
		{
			entity.attackEntityFrom(DamageSource.MAGIC, 1f);
		}
	}
}
