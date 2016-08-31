package com.gildedgames.aether.common.entities.effects.processors;

import com.gildedgames.aether.api.capabilites.entity.effects.EntityEffectInstance;
import com.gildedgames.aether.api.capabilites.entity.effects.EntityEffectRule;
import com.gildedgames.aether.common.entities.effects.AbstractEffectProcessor;
import com.gildedgames.aether.common.entities.effects.RangedAttributeModifier;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.util.text.TextFormatting;

import java.util.List;
import java.util.Random;

public class ModifyDamageEffect extends AbstractEffectProcessor<ModifyDamageEffect.Instance>
{

	public static class Instance extends EntityEffectInstance
	{

		private AttributeModifier modifier;

		public Instance(double damage, EntityEffectRule... rules)
		{
			this(damage, damage, false, rules);
		}

		public Instance(int min, int max, EntityEffectRule... rules)
		{
			this(min, max, false, rules);
		}

		public Instance(double min, double max, boolean floatRanges, EntityEffectRule... rules)
		{
			super(rules);

			this.getAttributes().setDouble("min", min);
			this.getAttributes().setDouble("max", max);

			this.getAttributes().setBoolean("floatRanges", floatRanges);

			this.modifier = new RangedAttributeModifier("Extra Attack Damage", min, max, floatRanges, new Random(), 0).setSaved(false);
		}

		public AttributeModifier getModifier()
		{
			return this.modifier;
		}

		@Override
		public EntityEffectInstance cloneInstance()
		{
			return new Instance(this.getAttributes().getFloat("min"), this.getAttributes().getFloat("max"), this.getAttributes().getBoolean("floatRanges"), this.getRules());
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

		String prefix = min >= 0 ? (TextFormatting.BLUE + "+") : (TextFormatting.RED + "");

		String par;

		if (min == max)
		{
			par = prefix + this.displayValue(min);
		}
		else
		{
			par = prefix + this.displayValue(min) + "-" + this.displayValue(max);
		}

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

		IAttributeInstance attribute = living.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);

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

		IAttributeInstance attribute = living.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);

		if (attribute.hasModifier(instance.getModifier()))
		{
			attribute.removeModifier(instance.getModifier());
		}
	}

}
