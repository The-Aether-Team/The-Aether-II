package com.gildedgames.aether.common.entities.effects.processors;

import com.gildedgames.aether.api.capabilites.entity.effects.EntityEffectInstance;
import com.gildedgames.aether.api.capabilites.entity.effects.EntityEffectRule;
import com.gildedgames.aether.common.entities.effects.AbstractEffectProcessor;
import com.gildedgames.aether.common.entities.effects.processors.RegenerateHealthEffect.Instance;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

import java.util.List;

public class RegenerateHealthEffect extends AbstractEffectProcessor<Instance>
{

	public static class Instance extends EntityEffectInstance
	{

		public Instance(int ticksBetweenHealing, EntityEffectRule... rules)
		{
			super(rules);

			this.getAttributes().setInteger("ticksBetweenHealing", ticksBetweenHealing);
		}

		@Override
		public EntityEffectInstance cloneInstance()
		{
			return new Instance(this.getAttributes().getInteger("ticksBetweenHealing"), this.getRules());
		}

	}

	public RegenerateHealthEffect()
	{
		super("ability.regenerateHealth.localizedName", "ability.regenerateHealth.desc");
	}

	@Override
	public void tick(Entity source, List<Instance> all)
	{
		if (!(source instanceof EntityLivingBase))
		{
			return;
		}

		EntityLivingBase living = (EntityLivingBase) source;

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
