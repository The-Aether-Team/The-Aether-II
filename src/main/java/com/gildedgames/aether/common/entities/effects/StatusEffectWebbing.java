package com.gildedgames.aether.common.entities.effects;

import com.gildedgames.aether.api.entity.effects.EEffectIntensity;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.util.text.TextFormatting;

import java.util.Collection;

public class StatusEffectWebbing extends StatusEffect
{
    private EntityLivingBase affectedEntity;

    public StatusEffectWebbing(EntityLivingBase livingBase)
    {
        super(effectTypes.WEBBING, new AttributeModifier("aether.statusEffectWebbingBound", -0.20, 1).setSaved(false), livingBase);

        this.affectedEntity = livingBase;
    }

    @Override
    public void applyEffect(EntityLivingBase livingBase, int timer)
    {
        IAttributeInstance iAttributeInstance;

        if (this.isEffectApplied)
        {
            iAttributeInstance = livingBase.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);
            if (iAttributeInstance != null && !iAttributeInstance.hasModifier(this.getAttributeModifier()))
            {
                iAttributeInstance.applyModifier(this.getAttributeModifier());
            }
        }
        else
        {
            iAttributeInstance = livingBase.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);
            if (iAttributeInstance != null && iAttributeInstance.getModifier(this.getAttributeModifier().getID()) != null)
            {
                iAttributeInstance.removeModifier(this.getAttributeModifier());
            }
        }
    }

    @Override
    public void onEffectEnd()
    {
        EntityLivingBase livingBase = this.affectedEntity;

        IAttributeInstance iAttributeInstance;

        iAttributeInstance = livingBase.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);
        if (iAttributeInstance != null && iAttributeInstance.getModifier(this.getAttributeModifier().getID()) != null)
        {
            iAttributeInstance.removeModifier(this.getAttributeModifier());
        }
    }

    @Override
    public int getBuildupFromIntensity(EEffectIntensity intensity)
    {
        switch (intensity)
        {
            case MINOR:
                return 5;
            case ORDINARY:
                return 20;
            case MAJOR:
                return 40;
        }

        return 0;
    }

    @Override
    public void addInformation(Collection<String> label)
    {
        label.add(TextFormatting.GRAY.toString() + TextFormatting.ITALIC.toString() + I18n.format("effect.aether.webbing"));
    }
}
