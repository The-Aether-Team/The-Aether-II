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

public class StatusEffectFracture extends StatusEffect
{
	private EntityLivingBase affectedEntity;

	public StatusEffectFracture(EntityLivingBase livingBase)
	{
		super(effectTypes.FRACTURE, new AttributeModifier("aether.statusEffectFractureCripple", -0.25, 1).setSaved(false), livingBase);

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

			iAttributeInstance = livingBase.getEntityAttribute(SharedMonsterAttributes.ARMOR);
			if (iAttributeInstance != null && !iAttributeInstance.hasModifier(this.getAttributeModifier()))
			{
				iAttributeInstance.applyModifier(this.getAttributeModifier());
			}

			iAttributeInstance = livingBase.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED);
			if (iAttributeInstance != null && !iAttributeInstance.hasModifier(this.getAttributeModifier()))
			{
				iAttributeInstance.applyModifier(this.getAttributeModifier());
			}

			if (livingBase.isSprinting())
			{
				if (timer % TICKS_PER_SECOND == 0)
				{
					livingBase.attackEntityFrom(EffectsDamageSource.FRACTURE, 1f);
				}
			}
		}
		else
		{
			iAttributeInstance = livingBase.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);
			if (iAttributeInstance != null && iAttributeInstance.getModifier(this.getAttributeModifier().getID()) != null)
			{
				iAttributeInstance.removeModifier(this.getAttributeModifier());
			}

			iAttributeInstance = livingBase.getEntityAttribute(SharedMonsterAttributes.ARMOR);
			if (iAttributeInstance != null && iAttributeInstance.getModifier(this.getAttributeModifier().getID()) != null)
			{
				iAttributeInstance.removeModifier(this.getAttributeModifier());
			}

			iAttributeInstance = livingBase.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED);
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

		iAttributeInstance = livingBase.getEntityAttribute(SharedMonsterAttributes.ARMOR);
		if (iAttributeInstance != null && iAttributeInstance.getModifier(this.getAttributeModifier().getID()) != null)
		{
			iAttributeInstance.removeModifier(this.getAttributeModifier());
		}

		iAttributeInstance = livingBase.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED);
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
				return 15;
			case ORDINARY:
				return 35;
			case MAJOR:
				return 75;
		}

		return 0;
	}

	@Override
	public void addInformation(Collection<String> label)
	{
		label.add(TextFormatting.WHITE.toString() + TextFormatting.ITALIC.toString() + I18n.format("effect.aether.fracture"));
	}

}
