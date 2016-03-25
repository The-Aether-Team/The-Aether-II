package com.gildedgames.aether.common.entities.effects.processors;

import com.gildedgames.aether.common.entities.effects.EffectInstance;
import com.gildedgames.aether.common.entities.effects.EffectProcessor;
import com.gildedgames.aether.common.entities.effects.EffectRule;
import com.gildedgames.aether.common.entities.effects.processors.ModifySpeedEffect.Instance;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import java.util.List;
import java.util.UUID;

public class ModifySpeedEffect implements EffectProcessor<Instance>
{

	public static class Instance extends EffectInstance
	{

		private AttributeModifier modifier;

		public Instance(float movementSpeedMod, EffectRule... rules)
		{
			super(rules);

			this.getAttributes().setFloat("movementSpeedMod", movementSpeedMod);
			this.modifier = new AttributeModifier(UUID.randomUUID(), "Extra Movement Speed", movementSpeedMod, 2).setSaved(false);
		}

		public AttributeModifier getModifier()
		{
			return this.modifier;
		}

		@Override
		public EffectInstance cloneInstance()
		{
			return new Instance(this.getAttributes().getFloat("movementSpeedMod"), this.getRules());
		}
		
	}

	public ModifySpeedEffect()
	{

	}

	@Override
	public String getUnlocalizedName(Entity source, Instance instance)
	{
		return "ability.movementSpeedMod.name";
	}

	@Override
	public String[] getUnlocalizedDesc(Entity source, Instance instance)
	{
		return new String[] { "ability.movementSpeedMod.desc" };
	}

	@Override
	public String[] getFormatParameters(Entity source, Instance instance)
	{
		float movementSpeedMod = instance.getAttributes().getFloat("movementSpeedMod");

		String prefix = movementSpeedMod > 0 ? (EnumChatFormatting.BLUE + "+") : (EnumChatFormatting.RED + "");

		float value = (float) (movementSpeedMod / SharedMonsterAttributes.movementSpeed.getDefaultValue()) * 100;

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

		living.getEntityAttribute(SharedMonsterAttributes.movementSpeed).applyModifier(instance.getModifier());
	}

	@Override
	public void tick(Entity source, List<Instance> all)
	{

	}

	@Override
	public void cancel(Entity source, Instance instance, List<Instance> all)
	{
		if (!(source instanceof EntityLivingBase))
		{
			return;
		}

		EntityLivingBase living = (EntityLivingBase) source;

		living.getEntityAttribute(SharedMonsterAttributes.movementSpeed).removeModifier(instance.getModifier());
	}

	@Override
	public void onKill(LivingDropsEvent event, Entity source, List<Instance> all)
	{

	}

	@Override
	public void onAttack(LivingHurtEvent event, Entity source, List<Instance> all)
	{

	}

}
