package com.gildedgames.aether.common.capabilities.entity.effects.processors;

import com.gildedgames.aether.api.capabilites.entity.effects.EntityEffectInstance;
import com.gildedgames.aether.api.capabilites.entity.effects.EntityEffectRule;
import com.gildedgames.aether.common.capabilities.entity.effects.AbstractEffectProcessor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import java.util.List;

public class LeechLifeEffect extends AbstractEffectProcessor<LeechLifeEffect.Instance>
{

	public static class Instance extends EntityEffectInstance
	{

		public Instance(double leechAmount, EntityEffectRule... rules)
		{
			super(rules);

			this.getAttributes().setDouble("leechAmount", leechAmount);
		}

		public double getLeechAmount()
		{
			return this.getAttributes().getDouble("leechAmount");
		}

		@Override
		public EntityEffectInstance cloneInstance()
		{
			return new Instance(this.getLeechAmount(), this.getRules());
		}

	}

	public LeechLifeEffect()
	{
		super("ability.leechLife.localizedName", "ability.leechLife.desc");
	}

	@Override
	public String[] getFormatParameters(Entity source, Instance instance)
	{
		double leechAmount = instance.getLeechAmount();

		String prefix = leechAmount > 0 ? (TextFormatting.BLUE + "+") : (TextFormatting.RED + "");

		String par = prefix + (leechAmount == (int) Math.floor(leechAmount) ? String.valueOf((int) Math.floor(leechAmount)) : String.valueOf(leechAmount));

		return new String[] { par };
	}

	@Override
	public void onAttack(LivingAttackEvent event, Entity source, List<Instance> all)
	{
		if (!(source instanceof EntityLivingBase))
		{
			return;
		}

		EntityLivingBase living = (EntityLivingBase) source;

		double leechTotal = 0.0D;

		for (Instance instance : all)
		{
			leechTotal += instance.getLeechAmount();
		}

		EntityLivingBase target = event.getEntityLiving();

		if (((EntityLivingBase) source).swingProgressInt <= 2)
		{
			((EntityLivingBase) source).heal((float) leechTotal * 2);
		}
	}

}
