package com.gildedgames.aether.common.entities.effects.processors;

import java.util.List;
import java.util.UUID;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import com.gildedgames.aether.common.entities.effects.EffectInstance;
import com.gildedgames.aether.common.entities.effects.EffectProcessor;
import com.gildedgames.aether.common.entities.effects.EffectRule;
import com.gildedgames.aether.common.entities.effects.processors.ModifyMaxHealthEffect.Instance;

public class ModifyMaxHealthEffect implements EffectProcessor<Instance>
{
	
	public static class Instance extends EffectInstance
	{
		
		private AttributeModifier modifier;

		public Instance(float maxHealthMod, EffectRule... rules)
		{
			super(rules);
		
			this.getAttributes().setFloat("maxHealthMod", maxHealthMod);
			this.modifier = new AttributeModifier(UUID.randomUUID(), "Extra Max Health", maxHealthMod, 2).setSaved(false);
		}
		
		public AttributeModifier getModifier()
		{
			return this.modifier;
		}
		
		@Override
		public EffectInstance cloneInstance()
		{
			return new Instance(this.getAttributes().getFloat("maxHealthMod"), this.getRules());
		}

	}

	public ModifyMaxHealthEffect()
	{
		
	}
	
	@Override
	public String getUnlocalizedName(Entity source, Instance instance)
	{
		return "ability.maxHealthMod.name";
	}

	@Override
	public String[] getUnlocalizedDesc(Entity source, Instance instance)
	{
		return new String[] { "ability.maxHealthMod.desc" };
	}
	
	@Override
	public String[] getFormatParameters(Entity source, Instance instance)
	{
		float maxHealthMod = instance.getAttributes().getFloat("maxHealthMod");
		
		String prefix = maxHealthMod > 0 ? "§9+" : "§c";
		
		float value = maxHealthMod * 10.0F;
		
		String par = prefix + (value == (int)Math.floor(value) ? String.valueOf((int)Math.floor(value)) : String.valueOf(value));

		return new String[] { par };
	}

	@Override
	public void apply(Entity source, Instance instance, List<Instance> all)
	{
		if (!(source instanceof EntityLivingBase))
		{
			return;
		}
		
		EntityLivingBase living = (EntityLivingBase)source;
		
		living.getEntityAttribute(SharedMonsterAttributes.maxHealth).applyModifier(instance.getModifier());
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
		
		EntityLivingBase living = (EntityLivingBase)source;
		
		living.getEntityAttribute(SharedMonsterAttributes.maxHealth).removeModifier(instance.getModifier());
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
