package com.gildedgames.aether.common.capabilities.entity.effects;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;

public class EffectStun extends AetherEffects
{
	public EffectStun()
	{
		super(effectTypes.STUN,
				new AttributeModifier("aether.effect.status.stun", -1D, 1).setSaved(false));
	}

	@Override
	public void applyEffect(EntityLivingBase entity)
	{
		IAttributeInstance iAttributeInstance = entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);

		if (this.isEffectApplied())
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
}
