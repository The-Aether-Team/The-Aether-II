package com.gildedgames.aether.client;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.analytics.GAUser;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class SessionEventHandler
{
	@SubscribeEvent
	public void onClientJoinServer(FMLNetworkEvent.ClientConnectedToServerEvent event)
	{
		GAUser user = AetherCore.ANALYTICS.getUser();
		user.startSession(AetherCore.ANALYTICS);
	}

	@SubscribeEvent
	public void onClientLeaveServer(FMLNetworkEvent.ClientDisconnectionFromServerEvent event)
	{
		GAUser user = AetherCore.ANALYTICS.getUser();
		user.endSession(AetherCore.ANALYTICS);

		AetherCore.ANALYTICS.flush();
	}
}
