package com.gildedgames.aether.common.capabilities.entity.effects;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.util.DamageSource;

public class EffectFungalRot extends AetherEffects
{
	public EffectFungalRot()
	{
		super(effectTypes.FUNGAL_ROT,
				new AttributeModifier("aether.effect.status.fungal_rot_slow", -0.5D, 1).setSaved(false));
	}

	@Override
	public void applyEffect(EntityLivingBase entity)
	{
		IAttributeInstance iAttributeInstance = entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);

		if (this.isEffectApplied() && this.effectTimer % (TICKS_PER_SECOND * 2) == 0)
		{
			entity.attackEntityFrom(DamageSource.MAGIC, 1f);
			if (!iAttributeInstance.hasModifier(this.getAttributeModifier()))
			{
				iAttributeInstance.applyModifier(this.getAttributeModifier());
			}
		}
		else if (!this.isEffectApplied())
		{
			if (iAttributeInstance.getModifier(this.getAttributeModifier().getID()) != null)
			{
				iAttributeInstance.removeModifier(this.getAttributeModifier());
			}
		}
	}
}
