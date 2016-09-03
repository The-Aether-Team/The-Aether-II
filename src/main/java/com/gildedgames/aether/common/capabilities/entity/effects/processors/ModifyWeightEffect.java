package com.gildedgames.aether.common.capabilities.entity.effects.processors;

import com.gildedgames.aether.api.capabilites.entity.effects.EntityEffectInstance;
import com.gildedgames.aether.api.capabilites.entity.effects.EntityEffectRule;
import com.gildedgames.aether.api.capabilites.entity.properties.ElementalDamageSource;
import com.gildedgames.aether.api.capabilites.entity.properties.ElementalState;
import com.gildedgames.aether.api.capabilites.entity.properties.IEntityPropertiesCapability;
import com.gildedgames.aether.common.capabilities.entity.effects.AbstractEffectProcessor;
import com.gildedgames.aether.common.capabilities.entity.properties.EntityProperties;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import java.util.List;

public class ModifyWeightEffect extends AbstractEffectProcessor<ModifyWeightEffect.Instance>
{

	public static class Instance extends EntityEffectInstance
	{

		private AttributeModifier modifier;

		public Instance(double weightKg, EntityEffectRule... rules)
		{
			super(rules);

			this.getAttributes().setDouble("weightKg", weightKg);

			double percent = (-weightKg / 3.0D) * 0.01D;

			this.modifier = new AttributeModifier("Negative Weight Speed", percent * SharedMonsterAttributes.MOVEMENT_SPEED.getDefaultValue(), 2).setSaved(false);
		}

		public double getWeightInKg()
		{
			return this.getAttributes().getDouble("weightKg");
		}

		public AttributeModifier getModifier()
		{
			return this.modifier;
		}

		@Override
		public EntityEffectInstance cloneInstance()
		{
			return new Instance(this.getWeightInKg(), this.getRules());
		}

	}

	public ModifyWeightEffect()
	{
		super("ability.weight.name");
	}

	@Override
	public String[] getUnlocalizedDesc(Entity source, Instance instance)
	{
		return new String[] { instance.getWeightInKg() > 0 ? "ability.weight.desc1" : "ability.weight.desc2"};
	}

	@Override
	public String[] getFormatParameters(Entity source, Instance instance)
	{
		double weightKg = instance.getWeightInKg();

		String prefix = weightKg > 0 ? (TextFormatting.RED + "+") : (TextFormatting.BLUE + "+");

		String par = prefix + (weightKg == (int) Math.floor(weightKg) ? String.valueOf((int) Math.floor(Math.abs(weightKg))) : String.valueOf(Math.abs(weightKg)));

		return new String[] { par };
	}

	@Override
	public void apply(Entity source, Instance instance, List<Instance> all)
	{
		if (!(source instanceof EntityLivingBase) || source.getEntityWorld().isRemote)
		{
			return;
		}

		double overall = 0.0D;

		for (Instance inst : all)
		{
			overall += inst.getWeightInKg();
		}

		if (overall <= 0)
		{
			return;
		}

		EntityLivingBase living = (EntityLivingBase) source;

		IAttributeInstance attribute = living.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);

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

		IAttributeInstance attribute = living.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);

		if (attribute.hasModifier(instance.getModifier()))
		{
			attribute.removeModifier(instance.getModifier());
		}
	}

}
