package com.gildedgames.aether.common.entities.effects;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.entity.effects.EffectsDamageSource;
import net.minecraft.entity.EntityLivingBase;

public class StatusEffectToxin extends StatusEffect
{
	private int NumOfHeartsEffected = 0;
	private final int MAX_HEARTS = 6;

	public StatusEffectToxin()
	{
		super(effectTypes.TOXIN, null);
	}

	@Override
	public void applyEffect(EntityLivingBase livingBase, int timer)
	{
		if (this.isEffectApplied)
		{
			if (timer % TICKS_PER_SECOND == 0 && this.NumOfHeartsEffected < this.MAX_HEARTS)
			{
				livingBase.attackEntityFrom(EffectsDamageSource.TOXIN, 1f);
				++this.NumOfHeartsEffected;
			}
		}
	}

	@Override
	public void onEffectEnd()
	{
		this.NumOfHeartsEffected = 0;
		AetherCore.LOGGER.info(this.NAME + " : Ended");
	}

}
