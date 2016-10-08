package com.gildedgames.aether.common.capabilities.entity.effects.processors;

import com.gildedgames.aether.api.capabilites.entity.effects.EntityEffectInstance;
import com.gildedgames.aether.api.capabilites.entity.effects.EntityEffectRule;
import com.gildedgames.aether.common.capabilities.entity.effects.AbstractEffectProcessor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.text.TextFormatting;

import java.util.List;

public class LeechLifeEffect extends AbstractEffectProcessor<LeechLifeEffect.Instance>
{

	public static class Instance extends EntityEffectInstance
	{

		public Instance(double leechChance, EntityEffectRule... rules)
		{
			super(rules);

			this.getAttributes().setDouble("leechChance", leechChance);
		}

		public double getChance()
		{
			return this.getAttributes().getDouble("leechChance");
		}

		@Override
		public EntityEffectInstance cloneInstance()
		{
			return new Instance(this.getChance(), this.getRules());
		}

	}

	public LeechLifeEffect()
	{
		super("ability.leechLife.name", "ability.leechLife.desc1", "ability.leechLife.desc2");
	}

	@Override
	public String[] getFormatParameters(Entity source, Instance instance)
	{
		return new String[] { "\u2022" + TextFormatting.ITALIC + " +" + String.valueOf((int) (instance.getChance() * 10.0f)) };
	}

	@Override
	public void onAttack(float amount, Entity target, Entity source, List<Instance> all)
	{
		if (!(source instanceof EntityLivingBase))
		{
			return;
		}

		EntityLivingBase living = (EntityLivingBase) source;

		double totalPercent = 0.0D;

		for (Instance instance : all)
		{
			totalPercent += instance.getChance();
		}

		float chance = (float) living.getRNG().nextInt(10);

		if (chance < totalPercent)
		{
			if (((EntityLivingBase) source).swingProgressInt <= 2)
			{
				((EntityLivingBase) source).heal(amount);
			}
		}
	}

}
