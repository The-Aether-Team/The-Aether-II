package com.gildedgames.aether.common.entities.effects;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;

public class StatusEffectFracture extends StatusEffect
{
	public StatusEffectFracture()
	{
		super(effectTypes.FRACTURE, new AttributeModifier("aether.statusEffectFractureCripple", -0.25, 1).setSaved(false));
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

	}

}
