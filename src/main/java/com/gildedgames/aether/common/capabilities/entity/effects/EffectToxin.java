package com.gildedgames.aether.common.capabilities.entity.effects;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;

public class EffectToxin extends AetherEffects
{
	public EffectToxin()
	{
		super(effectTypes.TOXIN, null);
	}

	@Override
	public void applyEffect(EntityLivingBase entity)
	{
		if (this.isEffectApplied() && entity.getHealth() > 6 && this.effectTimer % (TICKS_PER_SECOND/2) == 0)
		{
			entity.attackEntityFrom(DamageSource.MAGIC, 1f);
		}
	}
}
