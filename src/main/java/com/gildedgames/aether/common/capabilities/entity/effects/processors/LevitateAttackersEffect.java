package com.gildedgames.aether.common.capabilities.entity.effects.processors;

import com.gildedgames.aether.api.capabilites.entity.effects.EntityEffectInstance;
import com.gildedgames.aether.api.capabilites.entity.effects.EntityEffectRule;
import com.gildedgames.aether.common.capabilities.entity.effects.AbstractEffectProcessor;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import java.util.List;

public class LevitateAttackersEffect extends AbstractEffectProcessor<LevitateAttackersEffect.Instance>
{

	public static class Instance extends EntityEffectInstance
	{

		public Instance(double percentChance, EntityEffectRule... rules)
		{
			super(rules);

			this.getAttributes().setDouble("percentChance", percentChance);
		}

		public double getPercentChance()
		{
			return this.getAttributes().getDouble("percentChance");
		}

		@Override
		public EntityEffectInstance cloneInstance()
		{
			return new Instance(this.getPercentChance(), this.getRules());
		}

	}

	public LevitateAttackersEffect()
	{
		super("ability.levitateAttackers.name", "ability.levitateAttackers.desc1", "ability.levitateAttackers.desc2");
	}

	@Override
	public String[] getFormatParameters(Entity source, Instance instance)
	{
		return new String[] { "\u2022" + TextFormatting.ITALIC + " +" + String.valueOf((int) (instance.getPercentChance() * 100.0D))  };
	}

	@Override
	public void onHurt(LivingHurtEvent event, Entity source, List<Instance> all)
	{
		if (!(source instanceof EntityLivingBase) || !(event.getSource().getSourceOfDamage() instanceof EntityLivingBase))
		{
			return;
		}

		EntityLivingBase living = (EntityLivingBase)source;
		EntityLivingBase attackerLiving = (EntityLivingBase)event.getSource().getSourceOfDamage();

		double overallChance = 0.0D;

		for (Instance instance : all)
		{
			overallChance += instance.getPercentChance();
		}

		overallChance *= 100;

		if (living.getRNG().nextInt(100) <= (int)overallChance)
		{
			attackerLiving.addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 50, 3));
		}
	}

}
