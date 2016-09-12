package com.gildedgames.aether.common.capabilities.entity.effects.processors;

import com.gildedgames.aether.api.capabilites.entity.effects.EntityEffectInstance;
import com.gildedgames.aether.api.capabilites.entity.effects.EntityEffectRule;
import com.gildedgames.aether.api.capabilites.entity.properties.ElementalDamageSource;
import com.gildedgames.aether.api.capabilites.entity.properties.ElementalState;
import com.gildedgames.aether.api.capabilites.entity.properties.IEntityProperties;
import com.gildedgames.aether.api.capabilites.entity.properties.IEntityPropertiesCapability;
import com.gildedgames.aether.common.capabilities.entity.effects.AbstractEffectProcessor;
import com.gildedgames.aether.common.capabilities.entity.effects.RangedAttributeModifier;
import com.gildedgames.aether.common.capabilities.entity.properties.EntityProperties;
import com.google.common.base.Supplier;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.util.text.TextFormatting;

import java.util.List;
import java.util.Random;
import java.util.UUID;

public class ModifyDamageEffect extends AbstractEffectProcessor<ModifyDamageEffect.Instance>
{

	public static class Instance extends EntityEffectInstance
	{

		private final RangedAttributeModifier modifier;

		private ElementalDamageSource elementalDamageSource;

		public Instance(ElementalState elementalState, double damage, EntityEffectRule... rules)
		{
			this(elementalState, damage, damage, false, rules);
		}

		public Instance(ElementalState elementalState, int min, int max, EntityEffectRule... rules)
		{
			this(elementalState, min, max, false, rules);
		}

		public Instance(ElementalState elementalState, double min, double max, boolean floatRanges, EntityEffectRule... rules)
		{
			super(rules);

			this.getAttributes().setInteger("elementalState", elementalState.ordinal());

			this.getAttributes().setDouble("min", min);
			this.getAttributes().setDouble("max", max);

			this.getAttributes().setBoolean("floatRanges", floatRanges);

			this.modifier = (RangedAttributeModifier) new RangedAttributeModifier("Extra Attack Damage", min, max, floatRanges, new Random(), 0).setSaved(false);
			this.elementalDamageSource = new ElementalDamageSource(elementalState, new Supplier<Double>()
			{
				@Override
				public Double get()
				{
					return Instance.this.modifier.getLastAmount();
				}
			});
		}

		public AttributeModifier getModifier()
		{
			return this.modifier;
		}

		public ElementalDamageSource getElementalDamageSource()
		{
			 return this.elementalDamageSource;
		}

		@Override
		public EntityEffectInstance cloneInstance()
		{
			ElementalState elementalState = ElementalState.fromOrdinal(this.getAttributes().getInteger("elementalState"));

			return new Instance(elementalState, this.getAttributes().getFloat("min"), this.getAttributes().getFloat("max"), this.getAttributes().getBoolean("floatRanges"), this.getRules());
		}

	}

	public ModifyDamageEffect()
	{
		super("ability.extraDamage.localizedName", "ability.extraDamage.desc");
	}

	private String displayValue(float value)
	{
		return value == (int) Math.floor(value) ? String.valueOf((int) Math.floor(value)) : String.valueOf(value);
	}

	@Override
	public String[] getFormatParameters(Entity source, Instance instance)
	{
		float min = instance.getAttributes().getFloat("min");
		float max = instance.getAttributes().getFloat("max");

		String prefix = min >= 0 ? (instance.getElementalDamageSource().getElementalState().getNameFormatting() + "+") : (TextFormatting.RED + "");

		String par;

		if (min == max)
		{
			par = prefix + this.displayValue(min);
		}
		else
		{
			par = prefix + this.displayValue(min) + "-" + this.displayValue(max);
		}

		String elementName = I18n.format(instance.getElementalDamageSource().getElementalState().getUnlocalizedName());

		if (instance.getElementalDamageSource().getElementalState() == ElementalState.BIOLOGICAL)
		{
			elementName = I18n.format("ability.extraDamage.attack");
		}

		return new String[] { par, elementName };
	}

	@Override
	public void apply(Entity source, Instance instance, List<Instance> all)
	{
		if (!(source instanceof EntityLivingBase) || source.getEntityWorld().isRemote)
		{
			return;
		}

		EntityLivingBase living = (EntityLivingBase) source;

		IAttributeInstance attribute = living.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);

		if (!attribute.hasModifier(instance.getModifier()))
		{
			attribute.applyModifier(instance.getModifier());
		}

		IEntityPropertiesCapability properties = EntityProperties.get(living);

		if (!properties.hasElementalDamageSource(instance.getElementalDamageSource()))
		{
			properties.addElementalDamageSource(instance.getElementalDamageSource());
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

		IAttributeInstance attribute = living.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);

		if (attribute.hasModifier(instance.getModifier()))
		{
			attribute.removeModifier(instance.getModifier());
		}

		IEntityPropertiesCapability properties = EntityProperties.get(living);

		if (properties.hasElementalDamageSource(instance.getElementalDamageSource()))
		{
			properties.removeElementalDamageSource(instance.getElementalDamageSource());
		}
	}

}
