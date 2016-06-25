package com.gildedgames.aether.common.entities.effects;

import com.gildedgames.aether.api.entities.effects.EntityEffectProcessor;
import com.gildedgames.aether.api.entities.effects.EntityEffectInstance;
import com.gildedgames.aether.api.entities.effects.EntityEffectRule;
import com.gildedgames.aether.api.entities.effects.IEffectPool;
import com.google.common.collect.Lists;
import net.minecraft.entity.Entity;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerPickupXpEvent;

import java.util.List;

public class EffectPool<I extends EntityEffectInstance> implements IEffectPool<I>
{
	private EntityEffectProcessor<I> processor;

	private List<I> instances;

	public EffectPool(EntityEffectProcessor<I> processor, List<I> instances)
	{
		this.processor = processor;
		this.instances = instances;
	}

	public EntityEffectProcessor<I> getProcessor()
	{
		return this.processor;
	}

	public List<I> getInstances()
	{
		return this.instances;
	}

	@Override
	public <S extends Entity> void tick(S entity)
	{
		List<I> instancesRulesMet = Lists.newArrayList();

		for (I instance : this.getInstances())
		{
			boolean isMet = true;

			for (EntityEffectRule rule : instance.getRules())
			{
				if (!rule.isMet(entity))
				{
					isMet = false;
					break;
				}
			}

			if (isMet || instance.getRules().length <= 0)
			{
				instancesRulesMet.add(instance);
			}
		}

		this.getProcessor().tick(entity, instancesRulesMet);
	}

	@Override
	public <S extends Entity> void onKill(LivingDropsEvent event, S entity)
	{
		List<I> instancesRulesMet = Lists.newArrayList();

		for (I instance : this.getInstances())
		{
			boolean isMet = true;

			for (EntityEffectRule rule : instance.getRules())
			{
				if (!rule.isMet(entity))
				{
					isMet = false;
					break;
				}
			}

			if (isMet || instance.getRules().length <= 0)
			{
				instancesRulesMet.add(instance);
			}
		}

		this.getProcessor().onKill(event, entity, instancesRulesMet);
	}

	@Override
	public <S extends Entity> void onPlayerInteract(PlayerInteractEvent event, S entity)
	{
		if (!(this.getProcessor() instanceof EffectProcessorPlayer))
		{
			return;
		}

		List<I> instancesRulesMet = Lists.newArrayList();

		for (I instance : this.getInstances())
		{
			boolean isMet = true;

			for (EntityEffectRule rule : instance.getRules())
			{
				if (!rule.isMet(entity))
				{
					isMet = false;
					break;
				}
			}

			if (isMet || instance.getRules().length <= 0)
			{
				instancesRulesMet.add(instance);
			}
		}

		EffectProcessorPlayer<I> pability = (EffectProcessorPlayer<I>) this.getProcessor();

		pability.onInteract(event, event.getEntityPlayer(), instancesRulesMet);
	}

	@Override
	public <S extends Entity> void onPickupXP(PlayerPickupXpEvent event, S entity)
	{
		if (!(this.getProcessor() instanceof EffectProcessorPlayer))
		{
			return;
		}

		List<I> instancesRulesMet = Lists.newArrayList();

		for (I instance : this.getInstances())
		{
			boolean isMet = true;

			for (EntityEffectRule rule : instance.getRules())
			{
				if (!rule.isMet(entity))
				{
					isMet = false;
					break;
				}
			}

			if (isMet || instance.getRules().length <= 0)
			{
				instancesRulesMet.add(instance);
			}
		}

		EffectProcessorPlayer<I> pability = (EffectProcessorPlayer<I>) this.getProcessor();

		pability.onPickupXP(event, event.getEntityPlayer(), instancesRulesMet);
	}

	@Override
	public <S extends Entity> void onLivingAttack(LivingHurtEvent event, S entity)
	{
		List<I> instancesRulesMet = Lists.newArrayList();

		for (I instance : this.getInstances())
		{
			boolean isMet = true;

			for (EntityEffectRule rule : instance.getRules())
			{
				if (!rule.isMet(entity))
				{
					isMet = false;
					break;
				}
			}

			if (isMet || instance.getRules().length <= 0)
			{
				instancesRulesMet.add(instance);
			}
		}

		this.getProcessor().onAttack(event, entity, instancesRulesMet);
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof EffectPool)
		{
			EffectPool<?> pool = (EffectPool<?>) obj;

			return pool.getProcessor() == this.getProcessor() && pool.getInstances() == this.getInstances();

		}

		return obj == this.processor;
	}
}
