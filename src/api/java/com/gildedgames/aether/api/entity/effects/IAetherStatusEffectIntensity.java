package com.gildedgames.aether.api.entity.effects;

public interface IAetherStatusEffectIntensity
{
	static int getBuildupFromEffect(IAetherStatusEffects effect, EEffectIntensity intensity)
	{
		return effect.getBuildupFromIntensity(intensity);
	}
}
