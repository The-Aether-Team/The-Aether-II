package com.gildedgames.aether.common.capabilities.entity.effects.processors;

import com.gildedgames.aether.api.capabilites.entity.effects.EntityEffectInstance;
import com.gildedgames.aether.api.capabilites.entity.effects.EntityEffectRule;
import com.gildedgames.aether.common.capabilities.entity.effects.AbstractEffectProcessor;
import com.gildedgames.aether.common.capabilities.entity.effects.processors.ModifySpeedEffect.Instance;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.util.text.TextFormatting;

import java.util.List;

public class ModifySpeedEffect extends AbstractEffectProcessor<Instance>
{

	public static class Instance extends EntityEffectInstance
	{

		private AttributeModifier modifier;

		public Instance(float movementSpeedMod, EntityEffectRule... rules)
		{
			super(rules);

			this.getAttributes().setFloat("movementSpeedMod", movementSpeedMod);
			this.modifier = new AttributeModifier("Extra Movement Speed", movementSpeedMod, 2).setSaved(false);
		}

		public AttributeModifier getModifier()
		{
			return this.modifier;
		}

		@Override
		public EntityEffectInstance cloneInstance()
		{
			return new Instance(this.getAttributes().getFloat("movementSpeedMod"), this.getRules());
		}
		
	}

	public ModifySpeedEffect()
	{
		super("ability.movementSpeedMod.localizedName", "ability.movementSpeedMod.desc");
	}

	@Override
	public String[] getFormatParameters(Entity source, Instance instance)
	{
		float movementSpeedMod = instance.getAttributes().getFloat("movementSpeedMod");

		String prefix = movementSpeedMod > 0 ? (TextFormatting.BLUE + "+") : (TextFormatting.RED + "");

		float value = (float) (movementSpeedMod / SharedMonsterAttributes.MOVEMENT_SPEED.getDefaultValue()) * 100;

		String par = prefix + (int) (value) + "%";

		return new String[] { par };
	}

	@Override
	public void apply(Entity source, Instance instance, List<Instance> all)
	{
		if (!(source instanceof EntityLivingBase))
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
		if (!(source instanceof EntityLivingBase))
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