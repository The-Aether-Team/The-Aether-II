package com.gildedgames.aether.client;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.analytics.GAUser;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(Side.CLIENT)
public class SessionEventHandler
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
