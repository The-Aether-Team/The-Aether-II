package com.gildedgames.aether.api.effects_system;

public interface IAetherStatusEffectIntensity
{
	static int getBuildupFromEffect(IAetherStatusEffects effect, EEffectIntensity intensity)
	{
		return effect.getBuildupFromIntensity(intensity);
	}
}
