package com.gildedgames.aether.common.capabilities.entity.effects;

import com.gildedgames.aether.api.capabilites.entity.effects.EntityEffectInstance;
import com.gildedgames.aether.api.capabilites.entity.effects.EntityEffectProcessor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerPickupXpEvent;

import java.util.List;

public abstract class AbstractEffectProcessorPlayer<I extends EntityEffectInstance> implements EffectProcessorPlayer<I>
{

	private String unlocalizedName;

	private String[] desc;

	private State state;

	public AbstractEffectProcessorPlayer(String unlocalizedName, String... desc)
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
	public String[] getFormatParameters(Entity source, I instance)
	{
		return new String[] {};
	}

	@Override
	public State getState()
	{
		return this.state;
	}

	@Override
	public void setState(State state)
	{
		this.state = state;
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
	public void onHurt(LivingHurtEvent event, Entity source, List<I> all)
	{
	}

	@Override
	public void onAttacked(LivingAttackEvent event, Entity source, List<I> all)
	{

	}

	@Override
	public void onAttack(LivingAttackEvent event, Entity source, List<I> all)
	{

	}

	@Override
	public void onInteract(PlayerInteractEvent event, EntityPlayer source, List<I> instances)
	{

	}

	@Override
	public void onPickupXP(PlayerPickupXpEvent event, EntityPlayer source, List<I> instances)
	{

	}
}
