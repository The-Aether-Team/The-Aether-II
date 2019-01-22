package com.gildedgames.aether.common.entities.effects;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;

public class StatusEffectStun extends StatusEffect
{
	public StatusEffectStun()
	{
		super(effectTypes.STUN, new AttributeModifier("aether.statusEffectStun", -1D, 1).setSaved(false));
	}

	@Override
	public void applyEffect(EntityLivingBase livingBase, int timer)
	{
		IAttributeInstance iAttributeInstance = livingBase.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);

		if (this.isEffectApplied)
		{
			if (!iAttributeInstance.hasModifier(this.getAttributeModifier()))
			{
				iAttributeInstance.applyModifier(this.getAttributeModifier());
			}
		}
		else
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
}
