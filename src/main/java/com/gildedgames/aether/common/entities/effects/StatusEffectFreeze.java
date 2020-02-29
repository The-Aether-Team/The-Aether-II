package com.gildedgames.aether.common.entities.effects;

import com.gildedgames.aether.api.entity.effects.EEffectIntensity;
import com.gildedgames.aether.api.entity.effects.IAetherStatusEffectPool;
import com.gildedgames.aether.api.entity.effects.IAetherStatusEffects;
import com.gildedgames.aether.api.registrar.CapabilitiesAether;
import com.gildedgames.aether.common.capabilities.entity.effects.EffectsDamageSource;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.FoodStats;
import net.minecraft.util.text.TextFormatting;

import java.util.Collection;

public class StatusEffectFreeze extends StatusEffect
{
    private EntityLivingBase affectedEntity;

    public StatusEffectFreeze(EntityLivingBase livingBase)
    {
        super(effectTypes.FREEZE, new AttributeModifier("aether.statusEffectFreeze", -1.0, 1).setSaved(false), livingBase);

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

            if (timer % (TICKS_PER_SECOND * 4) == 0)
            {
                if (livingBase instanceof EntityPlayer)
                {
                    EntityPlayer player = (EntityPlayer) livingBase;
                    FoodStats foodStats = player.getFoodStats();

                    foodStats.setFoodLevel(foodStats.getFoodLevel() - 1);
                }
            }

            if (PlayerAether.getPlayer((EntityPlayer) livingBase).getJumping())
            {
                this.decreaseTimer += 5;
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
                return 1;
            case ORDINARY:
                return 15;
            case MAJOR:
                return 30;
        }

        return 0;
    }

    @Override
    public void addInformation(Collection<String> label)
    {
        label.add(TextFormatting.AQUA.toString() + I18n.format("effect.aether.freeze"));
    }
}
