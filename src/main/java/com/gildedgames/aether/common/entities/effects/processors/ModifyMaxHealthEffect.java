package com.gildedgames.aether.common.entities.effects.processors;

import com.gildedgames.aether.api.capabilites.entity.effects.EntityEffectInstance;
import com.gildedgames.aether.api.capabilites.entity.effects.EntityEffectProcessor;
import com.gildedgames.aether.api.capabilites.entity.effects.EntityEffectRule;
import com.gildedgames.aether.common.entities.effects.processors.ModifyMaxHealthEffect.Instance;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import java.util.List;
import java.util.UUID;

public class ModifyMaxHealthEffect implements EntityEffectProcessor<Instance>
{

	public static class Instance extends EntityEffectInstance
	{

		private AttributeModifier modifier;

		public Instance(float maxHealthMod, EntityEffectRule... rules)
		{
			super(rules);

			this.getAttributes().setFloat("maxHealthMod", maxHealthMod);
			this.modifier = new AttributeModifier(UUID.randomUUID(), "Extra Max Health", maxHealthMod / 10, 2).setSaved(false);
		}

		public AttributeModifier getModifier()
		{
			return this.modifier;
		}

		@Override
		public EntityEffectInstance cloneInstance()
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
		return "ability.maxHealthMod.localizedName";
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

		String prefix = maxHealthMod > 0 ? (TextFormatting.BLUE + "+") : (TextFormatting.RED + "");

		String par = prefix + (maxHealthMod == (int) Math.floor(maxHealthMod) ? String.valueOf((int) Math.floor(maxHealthMod)) : String.valueOf(maxHealthMod));

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

		living.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).applyModifier(instance.getModifier());
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

		living.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).removeModifier(instance.getModifier());
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
