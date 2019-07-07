package com.gildedgames.aether.common.entities.effects;

import com.gildedgames.aether.api.effects_system.EEffectIntensity;
import com.gildedgames.aether.common.events.listeners.player.PlayerAetherListener;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.living.LivingHealEvent;

import java.util.Collection;

public class StatusEffectAmbrosiumPoisoning extends StatusEffect
{
	public StatusEffectAmbrosiumPoisoning(EntityLivingBase livingBase)
	{
		super(effectTypes.AMBROSIUM_POISONING, null, livingBase);
	}

	@Override
	public void applyEffect(EntityLivingBase livingBase, int timer)
	{
		/**
		 * Ambrosium Poisoning has a unique effect, and is handled elsewhere.
		 * reduce all resistances by 50%.
		 * @see com.gildedgames.aether.common.capabilities.entity.effects.StatusEffectPool#applyStatusEffect(effectTypes, int)
		 *
		 * Negate weak player HP regeneration.
		 * @see PlayerAetherListener#onLivingHeal(LivingHealEvent)
		 */
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
				return 25;
			case MAJOR:
				return 50;
		}

		return 0;
	}

	@Override
	public void addInformation(Collection<String> label)
	{
		label.add(TextFormatting.YELLOW.toString() + I18n.format("statusEffect.aether.ambroPoison"));
	}
}
