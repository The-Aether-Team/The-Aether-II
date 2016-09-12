package com.gildedgames.aether.common.capabilities.entity.effects.processors;

import com.gildedgames.aether.api.capabilites.entity.effects.EntityEffectInstance;
import com.gildedgames.aether.api.capabilites.entity.effects.EntityEffectRule;
import com.gildedgames.aether.common.capabilities.entity.effects.AbstractEffectProcessor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.util.text.TextFormatting;

import java.util.List;
import java.util.UUID;

public class ModifyAttackSpeedEffect extends AbstractEffectProcessor<ModifyAttackSpeedEffect.Instance>
{

	public static class Instance extends EntityEffectInstance
	{

		private AttributeModifier modifier;

		public Instance(double attackSpeedMod, EntityEffectRule... rules)
		{
			super(rules);

			this.getAttributes().setDouble("attackSpeedMod", attackSpeedMod);
			this.modifier = new AttributeModifier(UUID.randomUUID(), "Extra Attack Speed", attackSpeedMod, 2).setSaved(false);
		}

		public AttributeModifier getModifier()
		{
			return this.modifier;
		}

		@Override
		public EntityEffectInstance cloneInstance()
		{
			return new Instance(this.getAttributes().getDouble("attackSpeedMod"), this.getRules());
		}

	}

	public ModifyAttackSpeedEffect()
	{
		super("ability.attackSpeedMod.name", "ability.attackSpeedMod.desc");
	}

	@Override
	public String[] getFormatParameters(Entity source, Instance instance)
	{
		double attackSpeedMod = instance.getAttributes().getDouble("attackSpeedMod");

		String prefix = attackSpeedMod > 0 ? (TextFormatting.BLUE + "+") : (TextFormatting.RED + "");

		double value = (float) (attackSpeedMod / SharedMonsterAttributes.ATTACK_SPEED.getDefaultValue()) * 100.0D;

		String valueString = value == (int) Math.floor(value) ? String.valueOf((int) Math.floor(value)) : String.valueOf(value);

		if (value != (int) Math.floor(value))
		{
			double floor = Math.floor(value);
			double dif = value - floor;

			if (dif < 0.1D)
			{
				valueString = String.valueOf((int) Math.floor(value));
			}
			else
			{
				valueString = String.format("%.1f", Double.valueOf(valueString));
			}
		}

		String par = prefix + (valueString) + "%";

		return new String[] { par };
	}

	@Override
	public void apply(Entity source, Instance instance, List<Instance> all)
	{
		if (!(source instanceof EntityLivingBase) || source.getEntityWorld().isRemote)
		{
			return;
		}

		EntityLivingBase living = (EntityLivingBase) source;

		IAttributeInstance attribute = living.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED);

		if (!attribute.hasModifier(instance.getModifier()))
		{
			attribute.applyModifier(instance.getModifier());
		}
	}

	@Override
	public void cancel(Entity source, Instance instance, List<Instance> all)
	{
		if (!(source instanceof EntityLivingBase) || source.getEntityWorld().isRemote)
		{
			return;
		}

		EntityLivingBase living = (EntityLivingBase) source;

		IAttributeInstance attribute = living.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED);

		if (attribute.hasModifier(instance.getModifier()))
		{
			attribute.removeModifier(instance.getModifier());
		}
	}

}
