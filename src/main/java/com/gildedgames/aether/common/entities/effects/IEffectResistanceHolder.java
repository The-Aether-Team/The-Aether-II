package com.gildedgames.aether.common.entities.effects;

import net.minecraft.item.Item;

import java.util.Map;

public interface IEffectResistanceHolder
{
    <T extends Item> T addStatusEffectResistance(StatusEffect effect, double resistance);

    Map<StatusEffect, Double> getStatusEffects();
}
