package com.gildedgames.aether.common.entities.effects;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EntityEffectsEventHooks
{

	@SubscribeEvent
	public void onLivingEntityHurt(LivingHurtEvent event)
	{
		EntityEffects<EntityLivingBase> effects = EntityEffects.get(event.entityLiving);

		if (effects != null)
		{
			effects.onHurt(event);
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
		
		EntityEffects<Entity> effects = EntityEffects.get(entity);

		if (effects != null)
		{
			for (EntityEffect<Entity> effect : effects.getEffects())
			{
				boolean isMet = true;
				
				for (AbilityRule<Entity> rule : effect.getRules())
				{
					if (!rule.isMet(entity))
					{
						isMet = false;
						break;
					}
				}
				
				if (isMet)
				{
					for (Ability<Entity> ability : effect.getAbilities())
					{
						ability.onKill(event, entity, effect, effect.getAttributes());
					}
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onPlayerInteract(PlayerInteractEvent event)
	{
		EntityPlayer entity = event.entityPlayer;
		EntityEffects<EntityPlayer> effects = EntityEffects.get(entity);

		if (effects != null)
		{
			for (EntityEffect<EntityPlayer> effect : effects.getEffects())
			{
				for (Ability<EntityPlayer> ability : effect.getAbilities())
				{
					if (ability instanceof PlayerAbility)
					{
						boolean isMet = true;
						
						for (AbilityRule<EntityPlayer> rule : effect.getRules())
						{
							if (!rule.isMet(entity))
							{
								isMet = false;
								break;
							}
						}
						
						if (isMet)
						{
							PlayerAbility pability = (PlayerAbility)ability;
							
							pability.onInteract(event, event.entityPlayer, effect, effect.getAttributes());
						}
					}
				}
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
		
		EntityEffects<Entity> effects = EntityEffects.get(entity);

		if (effects != null)
		{
			for (EntityEffect<Entity> effect : effects.getEffects())
			{
				boolean isMet = true;
				
				for (AbilityRule<Entity> rule : effect.getRules())
				{
					if (!rule.isMet(entity))
					{
						isMet = false;
						break;
					}
				}
				
				if (isMet)
				{
					for (Ability<Entity> ability : effect.getAbilities())
					{
						ability.onAttack(event, entity, effect, effect.getAttributes());
					}
				}
			}
		}
	}

}
