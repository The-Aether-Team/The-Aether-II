package com.gildedgames.aether.common.entities.effects.processors;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

import com.gildedgames.aether.common.entities.effects.AbstractEffectProcessor;
import com.gildedgames.aether.common.entities.effects.EffectInstance;
import com.gildedgames.aether.common.entities.effects.EffectRule;
import com.gildedgames.aether.common.entities.effects.processors.RegenerateHealthEffect.Instance;

public class RegenerateHealthEffect extends AbstractEffectProcessor<Instance>
{
	
	public static class Instance extends EffectInstance
	{

		public Instance(int ticksBetweenHealing, EffectRule... rules)
		{
			super(rules);
		
			this.getAttributes().setInteger("ticksBetweenHealing", ticksBetweenHealing);
		}
		
		@Override
		public EffectInstance cloneInstance()
		{
			return new Instance(this.getAttributes().getInteger("ticksBetweenHealing"), this.getRules());
		}

	}
	
	public RegenerateHealthEffect()
	{
		super("ability.regenerateHealth.name", "ability.regenerateHealth.desc");
	}

	@Override
	public void tick(Entity source, List<Instance> all)
	{
		if (!(source instanceof EntityLivingBase))
		{
			return;
		}
		
		EntityLivingBase living = (EntityLivingBase)source;
		
		int ticksBetween = -20;
		
		for (Instance instance : all)
		{
			ticksBetween += instance.getAttributes().getInteger("ticksBetweenHealing");
		}

		if (source.ticksExisted % ticksBetween == 0)
		{
			living.heal(0.5F);
		}
	}

}