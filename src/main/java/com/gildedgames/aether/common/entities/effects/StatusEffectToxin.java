package com.gildedgames.aether.common.entities.effects;

import com.gildedgames.aether.api.entity.effects.EEffectIntensity;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.entity.effects.EffectsDamageSource;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.text.TextFormatting;

import java.util.Collection;

public class StatusEffectToxin extends StatusEffect
{
	private int NumOfHeartsEffected = 0;
	private final int MAX_HEARTS = 6;

	public StatusEffectToxin(EntityLivingBase livingBase)
	{
		super(effectTypes.TOXIN, null, livingBase);
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

	@Override
	public int getBuildupFromIntensity(EEffectIntensity intensity)
	{
		switch (intensity)
		{
			case MINOR:
				return 15;
			case ORDINARY:
				return 50;
			case MAJOR:
				return 80;
		}

		return 0;
	}

	@Override
	public void addInformation(Collection<String> label)
	{
		label.add(TextFormatting.GRAY.toString() + I18n.format("statusEffect.aether.toxin"));
	}
}
