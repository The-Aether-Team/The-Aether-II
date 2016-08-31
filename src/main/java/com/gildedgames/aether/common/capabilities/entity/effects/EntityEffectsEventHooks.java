package com.gildedgames.aether.common.capabilities.entity.effects;

import com.gildedgames.aether.api.capabilites.entity.effects.IEffectPool;
import com.gildedgames.aether.api.capabilites.entity.effects.IEntityEffectsCapability;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerPickupXpEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EntityEffectsEventHooks
{
	
	@SubscribeEvent
	public static void onLivingUpdate(LivingUpdateEvent event)
	{
		IEntityEffectsCapability effects = EntityEffects.get(event.getEntityLiving());

		if (effects != null)
		{
			effects.onUpdate(event);
		}
	}

	@SubscribeEvent
	public static void onLivingEntityHurt(LivingHurtEvent event)
	{
		IEntityEffectsCapability effects = EntityEffects.get(event.getEntityLiving());

		if (effects != null)
		{
			effects.onHurt(event);
		}
	}

	@SubscribeEvent
	public static void onLivingAttack(LivingAttackEvent event)
	{
		IEntityEffectsCapability effects = EntityEffects.get(event.getEntityLiving());

		if (effects != null)
		{
			effects.onAttack(event);
		}
	}

	@SubscribeEvent
	public static void onLivingDropsEvent(LivingDropsEvent event)
	{
		Entity entity = event.getSource().getSourceOfDamage();

		if (entity == null)
		{
			return;
		}

		IEntityEffectsCapability effects = EntityEffects.get(entity);

		if (effects != null)
		{
			for (IEffectPool<?> pool : effects.getEffectPools())
			{
				pool.onKill(event, entity);
			}
		}
	}

	@SubscribeEvent
	public static void onPlayerInteract(PlayerInteractEvent event)
	{
		EntityPlayer entity = event.getEntityPlayer();

		IEntityEffectsCapability effects = EntityEffects.get(entity);

		if (effects != null)
		{
			for (IEffectPool<?> pool : effects.getEffectPools())
			{
				pool.onPlayerInteract(event, entity);
			}
		}
	}

	@SubscribeEvent
	public static void onPickupXP(PlayerPickupXpEvent event)
	{
		EntityPlayer entity = event.getEntityPlayer();

		IEntityEffectsCapability effects = EntityEffects.get(entity);

		if (effects != null)
		{
			for (IEffectPool<?> pool : effects.getEffectPools())
			{
				pool.onPickupXP(event, entity);
			}
		}
	}

	@SubscribeEvent
	public static void onLivingHurt(LivingHurtEvent event)
	{
		Entity entity = event.getEntity();

		if (entity == null)
		{
			return;
		}

		IEntityEffectsCapability effects = EntityEffects.get(entity);

		if (effects != null)
		{
			for (IEffectPool<?> pool : effects.getEffectPools())
			{
				pool.onLivingHurt(event, entity);
			}
		}
	}

	@SubscribeEvent
	public static void onLivingAttacked(LivingAttackEvent event)
	{
		Entity attacker = event.getSource().getSourceOfDamage();

		if (attacker != null)
		{
			IEntityEffectsCapability effects = EntityEffects.get(attacker);

			if (effects != null)
			{
				for (IEffectPool<?> pool : effects.getEffectPools())
				{
					pool.onLivingAttack(event, attacker);
				}
			}
		}

		Entity entity = event.getEntity();

		if (entity == null)
		{
			return;
		}

		IEntityEffectsCapability effects = EntityEffects.get(entity);

		if (effects != null)
		{
			for (IEffectPool<?> pool : effects.getEffectPools())
			{
				pool.onLivingAttacked(event, entity);
			}
		}
	}

}
