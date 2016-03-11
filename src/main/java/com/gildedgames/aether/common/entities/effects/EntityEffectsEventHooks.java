package com.gildedgames.aether.common.entities.effects;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerPickupXpEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EntityEffectsEventHooks
{

	@SubscribeEvent
	public void onLivingEntityHurt(LivingHurtEvent event)
	{
		EntityEffects effects = EntityEffects.get(event.entityLiving);

		if (effects != null)
		{
			effects.onHurt(event);
		}
	}

	@SubscribeEvent
	public void onLivingUpdate(LivingUpdateEvent event)
	{
		EntityEffects effects = EntityEffects.get(event.entity);
		
		if (effects != null)
		{
			effects.onUpdate();
		}
	}

	@SubscribeEvent
	public void onLivingDropsEvent(LivingDropsEvent event)
	{
		Entity entity = event.source.getSourceOfDamage();
		
		if (entity == null)
		{
			return;
		}
		
		EntityEffects effects = EntityEffects.get(entity);

		if (effects != null)
		{
			for (EffectPool<?> pool : effects.getEffectPools())
			{
				pool.onKill(event, entity);
			}
		}
	}
	
	@SubscribeEvent
	public void onPlayerInteract(PlayerInteractEvent event)
	{
		EntityPlayer entity = event.entityPlayer;
		EntityEffects effects = EntityEffects.get(entity);

		if (effects != null)
		{
			for (EffectPool<?> pool : effects.getEffectPools())
			{
				pool.onPlayerInteract(event, entity);
			}
		}
	}
	
	@SubscribeEvent
	public void onPickupXP(PlayerPickupXpEvent event)
	{
		EntityPlayer entity = event.entityPlayer;
		EntityEffects effects = EntityEffects.get(entity);

		if (effects != null)
		{
			for (EffectPool<?> pool : effects.getEffectPools())
			{
				pool.onPickupXP(event, entity);
			}
		}
	}
	
	@SubscribeEvent
	public void onLivingAttack(LivingHurtEvent event)
	{
		Entity entity = event.source.getSourceOfDamage();
		
		if (entity == null)
		{
			return;
		}
		
		EntityEffects effects = EntityEffects.get(entity);

		if (effects != null)
		{
			for (EffectPool<?> pool : effects.getEffectPools())
			{
				pool.onLivingAttack(event, entity);
			}
		}
	}

}
