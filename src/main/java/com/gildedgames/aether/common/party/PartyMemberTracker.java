package com.gildedgames.aether.common.party;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import java.util.HashMap;
import java.util.UUID;

public class PartyMemberTracker
{
	public static final PartyMemberTracker INSTANCE = new PartyMemberTracker();

	private final HashMap<UUID, EntityPlayer> tracked = new HashMap<>();

	public EntityPlayer getTracked(UUID uuid)
	{
		return this.tracked.get(uuid);
	}

	@SubscribeEvent
	public void onEntityJoinWorld(EntityJoinWorldEvent event)
	{
		if (event.entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) event.entity;

			this.tracked.put(player.getUniqueID(), player);
		}
	}

	@SubscribeEvent
	public void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event)
	{
		this.tracked.remove(event.player.getUniqueID());
	}
}
