package com.gildedgames.aether.client.events.listeners.network;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.analytics.GAUser;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import net.minecraftforge.api.distmarker.Dist;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class ClientNetworkStateListener
{
	@SubscribeEvent
	public static void onClientJoinServer(FMLNetworkEvent.ClientConnectedToServerEvent event)
	{
		GAUser user = AetherCore.ANALYTICS.getUser();
		user.startSession(AetherCore.ANALYTICS);
	}

	@SubscribeEvent
	public static void onClientLeaveServer(FMLNetworkEvent.ClientDisconnectionFromServerEvent event)
	{
		GAUser user = AetherCore.ANALYTICS.getUser();
		user.endSession(AetherCore.ANALYTICS);

		AetherCore.ANALYTICS.flush();
	}
}
