package com.gildedgames.aether.common.entities.player;

import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PlayerAetherEvents
{
	@SubscribeEvent
	public void onDeath(LivingDeathEvent event)
	{
		PlayerAetherBase aePlayer = PlayerAether.getPlayer(event.entity);

		if (aePlayer != null)
		{
			aePlayer.onDeath(event);
		}
	}

	@SubscribeEvent
	public void onDrops(LivingDropsEvent event)
	{
		PlayerAetherBase aePlayer = PlayerAether.getPlayer(event.entity);

		if (aePlayer != null)
		{
			aePlayer.onDrops(event);
		}
	}

	@SubscribeEvent
	public void onUpdate(LivingUpdateEvent event)
	{
		PlayerAetherBase aePlayer = PlayerAether.getPlayer(event.entity);

		if (aePlayer != null)
		{
			aePlayer.onUpdate(event);
		}
	}

	@SubscribeEvent
	public void onFall(LivingFallEvent event)
	{
		PlayerAetherBase aePlayer = PlayerAether.getPlayer(event.entity);

		if (aePlayer != null)
		{
			aePlayer.onFall(event);
		}
	}

	@SubscribeEvent
	public void onJump(LivingJumpEvent event)
	{
		PlayerAetherBase aePlayer = PlayerAether.getPlayer(event.entity);

		if (aePlayer != null)
		{
			aePlayer.onJump(event);
		}
	}

	@SubscribeEvent
	public void onCalculateBreakSpeed(PlayerEvent.BreakSpeed event)
	{
		PlayerAetherBase aePlayer = PlayerAether.getPlayer(event.entity);

		if (aePlayer != null)
		{
			event.newSpeed = event.originalSpeed * aePlayer.getMiningSpeedMultiplier();
		}
	}

	@SubscribeEvent
	public void onLivingEntityHurt(LivingHurtEvent event)
	{
		PlayerAetherBase aePlayer = PlayerAether.getPlayer(event.entity);

		if (aePlayer != null)
		{
			aePlayer.onHurt(event);
		}
	}
}
