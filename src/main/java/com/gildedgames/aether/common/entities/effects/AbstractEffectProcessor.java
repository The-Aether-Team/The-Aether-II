package com.gildedgames.aether.common.entities.effects;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public abstract class AbstractEffectProcessor<I extends EffectInstance> implements EffectProcessor<I>
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
	public String[] getFormatParameters(Entity source, EffectInstance instance) { return new String[] {}; }

	@Override
	public void apply(Entity source, I instance, List<I> all) {}

	@Override
	public void tick(Entity source, List<I> all) {}

	@Override
	public void cancel(Entity source, I instance, List<I> all) {}

	@Override
	public void onKill(LivingDropsEvent event, Entity source, List<I> all) {}

	@Override
	public void onAttack(LivingHurtEvent event, Entity source, List<I> all) {}

}
