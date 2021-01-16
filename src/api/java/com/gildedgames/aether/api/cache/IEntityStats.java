package com.gildedgames.aether.api.cache;

import com.gildedgames.aether.api.entity.effects.IAetherStatusEffects;

import java.util.Map;

public interface IEntityStats
{
	float getMaxHealth();

	double getSlashDefenseLevel();

	double getPierceDefenseLevel();

	double getImpactDefenseLevel();

	Map<IAetherStatusEffects.effectTypes, Double> getResistances();
}
