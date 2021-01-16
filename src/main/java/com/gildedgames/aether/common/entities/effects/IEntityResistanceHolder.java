package com.gildedgames.aether.common.entities.effects;

import com.gildedgames.aether.api.entity.effects.IAetherStatusEffects;

import java.util.Map;

public interface IEntityResistanceHolder
{
    Map<IAetherStatusEffects.effectTypes, Double> getResistances();
}
