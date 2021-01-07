package com.gildedgames.aether.common.items.accessories;

import com.gildedgames.aether.common.entities.effects.IEffectResistanceHolder;
import com.gildedgames.aether.common.entities.effects.StatusEffect;
import net.minecraft.item.Item;

import java.util.HashMap;
import java.util.Map;

public class ItemAccessory extends Item implements IEffectResistanceHolder
{
    private Map<StatusEffect, Double> statusEffects = new HashMap<>();

    public ItemAccessory()
    {
        this.setMaxStackSize(1);
    }

    public ItemAccessory addStatusEffectResistance(StatusEffect effect, double resistance)
    {
        this.statusEffects.put(effect, resistance);

        return this;
    }

    public Map<StatusEffect, Double> getStatusEffects()
    {
        return this.statusEffects;
    }
}
