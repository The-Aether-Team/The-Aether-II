package com.gildedgames.aether.common.entities.effects;

import com.gildedgames.aether.api.entity.effects.EEffectIntensity;
import com.gildedgames.aether.common.capabilities.entity.effects.EffectsDamageSource;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.util.text.TextFormatting;

import java.util.Collection;

public class StatusEffectFungalRot extends StatusEffect
{
	public StatusEffectFungalRot(EntityLivingBase livingBase)
	{
		super(effectTypes.FUNGAL_ROT, new AttributeModifier("aether.statusEffectFungalRot", -0.5D, 1).setSaved(false), livingBase);
	}

	@Override
	public void applyEffect(EntityLivingBase livingBase, int timer)
	{
		IAttributeInstance iAttributeInstance = livingBase.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);

		if (this.isEffectApplied && this.effectTimer % (TICKS_PER_SECOND * 2) == 0)
		{
			livingBase.attackEntityFrom(EffectsDamageSource.FUNGAL_ROT, 1f);

			if (!iAttributeInstance.hasModifier(this.getAttributeModifier()))
			{
				iAttributeInstance.applyModifier(this.getAttributeModifier());
			}
		}
		else if (!this.isEffectApplied)
		{
			if (iAttributeInstance.getModifier(this.getAttributeModifier().getID()) != null)
			{
				iAttributeInstance.removeModifier(this.getAttributeModifier());
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
				return 5;
			case ORDINARY:
				return 15;
			case MAJOR:
				return 40;
		}

		return 0;
	}

	@Override
	public void addInformation(Collection<String> label)
	{
		label.add(TextFormatting.DARK_GREEN.toString() + I18n.format("statusEffect.aether.fungalRot"));
	}
}
