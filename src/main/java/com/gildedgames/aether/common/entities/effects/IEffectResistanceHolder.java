package com.gildedgames.aether.common.entities.effects;

import com.gildedgames.aether.common.items.armor.ItemAetherArmor;

import java.util.Map;

public interface IEffectResistanceHolder
{
    <T extends ItemAetherArmor> T addStatusEffectResistance(StatusEffect effect, double resistance);

    Map<StatusEffect, Double> getStatusEffects();
}
