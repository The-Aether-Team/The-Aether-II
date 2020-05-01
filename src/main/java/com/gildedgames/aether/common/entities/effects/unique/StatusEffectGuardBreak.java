package com.gildedgames.aether.common.entities.effects.unique;

import com.gildedgames.aether.api.entity.effects.EEffectIntensity;
import com.gildedgames.aether.common.entities.effects.StatusEffect;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.text.TextFormatting;

import java.util.Collection;

public class StatusEffectGuardBreak extends StatusEffect
{

    public StatusEffectGuardBreak(EntityLivingBase livingBase)
    {
        super(effectTypes.GUARD_BREAK, null, livingBase);
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
        label.add(TextFormatting.YELLOW.toString() + I18n.format("effect.aether.guard_break"));
    }
}
