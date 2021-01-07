package com.gildedgames.aether.common.entities.effects;

import com.gildedgames.aether.common.items.weapons.swords.ItemAetherSword;

import java.util.Map;

public interface IEffectDamageHolder
{
    <T extends ItemAetherSword> T addStatusEffect(StatusEffect effect, int amount);

    Map<StatusEffect, Integer> getStatusEffects();
}
