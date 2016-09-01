package com.gildedgames.aether.common.capabilities.entity.effects.processors;

import com.gildedgames.aether.api.capabilites.entity.effects.EntityEffectInstance;
import com.gildedgames.aether.api.capabilites.entity.effects.EntityEffectRule;
import com.gildedgames.aether.api.capabilites.entity.properties.ElementalDamageSource;
import com.gildedgames.aether.api.capabilites.entity.properties.ElementalState;
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

public class ChangeAttackElementEffect extends AbstractEffectProcessor<ChangeAttackElementEffect.Instance>
{

	public static class Instance extends EntityEffectInstance
	{

		private ElementalState elementalState;

		public Instance(ElementalState elementalState, EntityEffectRule... rules)
		{
			super(rules);

			this.elementalState = elementalState;
		}

		public ElementalState getElementalState()
		{
			return this.elementalState;
		}

		@Override
		public EntityEffectInstance cloneInstance()
		{
			return new Instance(this.elementalState, this.getRules());
		}

	}

	public ChangeAttackElementEffect()
	{
		super("ability.changeAttackElement.name");
	}

	@Override
	public String[] getUnlocalizedDesc(Entity source, Instance instance)
	{
		return new String[] { I18n.format("ability.changeAttackElement.desc"), TextFormatting.RESET + "" + instance.getElementalState().getNameFormatting() + "\u2022 " + I18n.format(instance.getElementalState().getUnlocalizedName()) };
	}

	@Override
	public void apply(Entity source, Instance instance, List<Instance> all)
	{
		if (!(source instanceof EntityLivingBase))
		{
			return;
		}

		IEntityPropertiesCapability properties = EntityProperties.get(source);

		properties.setElementalStateOverride(instance.getElementalState());
	}

	@Override
	public void cancel(Entity source, Instance instance, List<Instance> all)
	{
		if (!(source instanceof EntityLivingBase))
		{
			return;
		}

		IEntityPropertiesCapability properties = EntityProperties.get(source);

		properties.setElementalStateOverride(null);
	}

}
