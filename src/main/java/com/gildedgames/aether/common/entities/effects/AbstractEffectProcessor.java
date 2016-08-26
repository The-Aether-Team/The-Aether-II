package com.gildedgames.aether.common.entities.effects;

import com.gildedgames.aether.api.capabilites.entity.effects.EntityEffectInstance;
import com.gildedgames.aether.api.capabilites.entity.effects.EntityEffectProcessor;
import net.minecraft.entity.Entity;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import java.util.List;

public abstract class AbstractEffectProcessor<I extends EntityEffectInstance> implements EntityEffectProcessor<I>
{

	private String unlocalizedName;

	private String[] desc;

	public AbstractEffectProcessor(String unlocalizedName, String... desc)
	{
		this.unlocalizedName = unlocalizedName;
		this.desc = desc;
	}

	@Override
	public String getUnlocalizedName(Entity source, I instance)
	{
		return this.unlocalizedName;
	}

	@Override
	public String[] getUnlocalizedDesc(Entity source, I instance)
	{
		return this.desc;
	}

	@Override
	public String[] getFormatParameters(Entity source, EntityEffectInstance instance)
	{
		return new String[] {};
	}

	@Override
	public void apply(Entity source, I instance, List<I> all)
	{
	}

	@Override
	public void tick(Entity source, List<I> all)
	{
	}

	@Override
	public void cancel(Entity source, I instance, List<I> all)
	{
	}

	@Override
	public void onKill(LivingDropsEvent event, Entity source, List<I> all)
	{
	}

	@Override
	public void onAttack(LivingHurtEvent event, Entity source, List<I> all)
	{
	}

}
