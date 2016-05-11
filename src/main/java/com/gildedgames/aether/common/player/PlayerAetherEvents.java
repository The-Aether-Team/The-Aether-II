package com.gildedgames.aether.common.player;

import com.gildedgames.aether.common.world.chunk.AetherPlaceFlagChunkHook;
import com.gildedgames.aether.api.player.IPlayerAetherCapability;
import com.gildedgames.util.modules.chunk.ChunkModule;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PlayerAetherEvents
{
	@SubscribeEvent
	public void onDeath(LivingDeathEvent event)
	{
		IPlayerAetherCapability aePlayer = PlayerAether.getPlayer(event.entity);

		if (aePlayer != null)
		{
			aePlayer.onDeath(event);
		}
	}

	@SubscribeEvent
	public void onDrops(LivingDropsEvent event)
	{
		IPlayerAetherCapability aePlayer = PlayerAether.getPlayer(event.entity);

		if (aePlayer != null)
		{
			aePlayer.onDrops(event);
		}
	}

	@SubscribeEvent
	public void onUpdate(LivingUpdateEvent event)
	{
		IPlayerAetherCapability aePlayer = PlayerAether.getPlayer(event.entity);

		if (aePlayer != null)
		{
			aePlayer.onUpdate(event);
		}
	}

	@SubscribeEvent
	public void onFall(LivingFallEvent event)
	{
		IPlayerAetherCapability aePlayer = PlayerAether.getPlayer(event.entity);

		if (aePlayer != null)
		{
			aePlayer.onFall(event);
		}
	}

	@SubscribeEvent
	public void onJump(LivingJumpEvent event)
	{
		IPlayerAetherCapability aePlayer = PlayerAether.getPlayer(event.entity);

		if (aePlayer != null)
		{
			aePlayer.onJump(event);
		}
	}

	@SubscribeEvent
	public void onCalculateBreakSpeed(PlayerEvent.BreakSpeed event)
	{
		IPlayerAetherCapability aePlayer = PlayerAether.getPlayer(event.entity);

		if (aePlayer != null)
		{
			event.newSpeed = event.originalSpeed * aePlayer.getMiningSpeedMultiplier();
		}
	}

	@SubscribeEvent
	public void onLivingEntityHurt(LivingHurtEvent event)
	{
		IPlayerAetherCapability aePlayer = PlayerAether.getPlayer(event.entity);

		if (aePlayer != null)
		{
			aePlayer.onHurt(event);
		}
	}

	@SubscribeEvent
	public void onPlaceBlockEvent(BlockEvent.PlaceEvent event)
	{
		AetherPlaceFlagChunkHook data = ChunkModule.api().getHook(event.world, event.pos, AetherPlaceFlagChunkHook.class);

		int x = event.pos.getX(), y = event.pos.getY(), z = event.pos.getZ();

		if (data != null)
		{
			data.setExtendedBlockState(x, y, z, data.getExtendedBlockState(x, y, z).withProperty(AetherPlaceFlagChunkHook.PROPERTY_BLOCK_PLACED, true));
		}
		else
		{
			/*
			 * TODO: FIX THIS SHIT FUCK
			 */
			//System.out.println("Chunk hook is null, something is going wrong!");
		}
	}
}
