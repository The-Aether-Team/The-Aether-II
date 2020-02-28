package com.gildedgames.aether.common.entities.effects;

import com.gildedgames.aether.api.entity.effects.EEffectIntensity;
import com.gildedgames.aether.common.capabilities.entity.effects.EffectsDamageSource;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.text.TextFormatting;

import java.util.Collection;

public class StatusEffectCockatriceVenom extends StatusEffect
{
	public StatusEffectCockatriceVenom(EntityLivingBase livingBase)
	{
		super(effectTypes.COCKATRICE_VENOM, null, livingBase);
	}

	@Override
	public void applyEffect(EntityLivingBase livingBase, int timer)
	{
		if (this.isEffectApplied)
		{
			if (this.effectTimer % (TICKS_PER_SECOND * 2) == 0 && livingBase.getHealth() >= 2)
			{
				livingBase.attackEntityFrom(EffectsDamageSource.COCKATRICE_VENOM, 1f);
			}
		}
	}

	@Override
	public void onEffectEnd()
	{

	}

	@Override
	public int getBuildupFromIntensity(EEffectIntensity intensity)
	{
		return 55;
	}

	@Override
	public void addInformation(Collection<String> label)
	{
		label.add(TextFormatting.DARK_PURPLE.toString() + I18n.format("effect.aether.cockatrice_venom"));
	}
}
