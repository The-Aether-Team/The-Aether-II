package com.gildedgames.aether.common.entities.effects.teas;

import com.gildedgames.aether.api.entity.effects.EEffectIntensity;
import com.gildedgames.aether.common.entities.effects.StatusEffect;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.text.TextFormatting;

import java.util.Collection;

public class StatusEffectSaturationBoost extends StatusEffect
{
    public StatusEffectSaturationBoost(EntityLivingBase livingBase)
    {
        super(effectTypes.SATURATION_BOOST, null, livingBase);
    }

    @Override
    public void applyEffect(EntityLivingBase livingBase, int timer)
    {

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
                return 1;
            case ORDINARY:
                return 50;
            case MAJOR:
                return 100;
        }

        return 0;
    }

    @Override
    public void addInformation(Collection<String> label)
    {
        label.add(TextFormatting.GREEN.toString() + I18n.format("effect.aether.saturation_boost"));
    }
}