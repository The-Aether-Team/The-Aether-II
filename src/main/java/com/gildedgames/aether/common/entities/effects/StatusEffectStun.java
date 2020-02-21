package com.gildedgames.aether.common.entities.effects;

import com.gildedgames.aether.api.entity.effects.EEffectIntensity;
import com.gildedgames.aether.common.events.listeners.player.PlayerAetherListener;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;

import java.util.Collection;

public class StatusEffectStun extends StatusEffect
{
	private EntityLivingBase affectedEntity;

	public StatusEffectStun(EntityLivingBase livingBase)
	{
		super(effectTypes.STUN, new AttributeModifier("aether.statusEffectStun", -1.0, 1).setSaved(false), livingBase);

		this.affectedEntity = livingBase;
	}

	@Override
	public void applyEffect(EntityLivingBase livingBase, int timer)
	{
		IAttributeInstance iAttributeInstance;

		/**
		 * Stun's effect of disabling entity attacks cannot be set in here, so it is handled in a listener.
		 * @see com.gildedgames.aether.common.events.listeners.entity.EntityAttackListener#onEntityStunned(LivingAttackEvent)
		 *
		 * @see com.gildedgames.aether.common.events.listeners.player.PlayerAttackListener#onPlayerStunned(AttackEntityEvent)
		 */

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
				return 25;
			case ORDINARY:
				return 60;
			case MAJOR:
				return 90;
		}

		return 0;
	}

	@Override
	public void addInformation(Collection<String> label)
	{
		label.add(TextFormatting.GOLD.toString() + I18n.format("statusEffect.aether.stun"));
	}
}
