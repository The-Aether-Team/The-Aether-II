package com.gildedgames.aether.common.capabilities.entity.effects;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;

public class EffectFracture extends AetherEffects
{
	public EffectFracture()
	{
		super(effectTypes.FRACTURE,
				new AttributeModifier("aether.effect.status.fracture_cripple", -0.25, 1).setSaved(false));
	}

	@Override
	public void applyEffect(EntityLivingBase entity)
	{
		IAttributeInstance iAttributeInstance;
		if (this.isEffectApplied())
		{
			iAttributeInstance = entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);
			if (!iAttributeInstance.hasModifier(this.getAttributeModifier()))
			{
				iAttributeInstance.applyModifier(this.getAttributeModifier());
			}

			iAttributeInstance = entity.getEntityAttribute(SharedMonsterAttributes.ARMOR);
			if (!iAttributeInstance.hasModifier(this.getAttributeModifier()))
			{
				iAttributeInstance.applyModifier(this.getAttributeModifier());
			}

			iAttributeInstance = entity.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED);
			if (!iAttributeInstance.hasModifier(this.getAttributeModifier()))
			{
				iAttributeInstance.applyModifier(this.getAttributeModifier());
			}
		}
		else
		{
			iAttributeInstance = entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);
			if (iAttributeInstance.getModifier(this.getAttributeModifier().getID()) != null)
			{
				iAttributeInstance.removeModifier(this.getAttributeModifier());
			}

			iAttributeInstance = entity.getEntityAttribute(SharedMonsterAttributes.ARMOR);
			if (iAttributeInstance.getModifier(this.getAttributeModifier().getID()) != null)
			{
				iAttributeInstance.removeModifier(this.getAttributeModifier());
			}

			iAttributeInstance = entity.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED);
			if (iAttributeInstance.getModifier(this.getAttributeModifier().getID()) != null)
			{
				iAttributeInstance.removeModifier(this.getAttributeModifier());
			}
		}
	}
}
