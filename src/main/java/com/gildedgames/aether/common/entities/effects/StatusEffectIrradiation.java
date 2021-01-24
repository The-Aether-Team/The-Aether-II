package com.gildedgames.aether.common.entities.effects;

import com.gildedgames.aether.api.entity.effects.EEffectIntensity;
import com.gildedgames.aether.common.capabilities.entity.effects.EffectsDamageSource;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.text.TextFormatting;

import java.util.Collection;

public class StatusEffectIrradiation extends StatusEffect
{
    public StatusEffectIrradiation(EntityLivingBase livingBase)
    {
        super(effectTypes.IRRADIATION, null, livingBase);
    }

    @Override
    public void applyEffect(EntityLivingBase livingBase, int timer)
    {
        if (this.isEffectApplied)
        {
            if (this.effectTimer % (TICKS_PER_SECOND * 10) == 0)
            {
                livingBase.attackEntityFrom(EffectsDamageSource.IRRADIATION, 1f);
            }
        }
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
                return 3;
            case ORDINARY:
                return 25;
            case MAJOR:
                return 50;
        }

        return 0;
    }

    @Override
    public void addInformation(Collection<String> label)
    {
        label.add(TextFormatting.YELLOW.toString() + TextFormatting.ITALIC.toString() + I18n.format("effect.aether.irradiation"));
    }
}
