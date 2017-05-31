package com.gildedgames.aether.client;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.analytics.GAStorage;
import com.gildedgames.aether.common.analytics.GAUser;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

public class SessionEventHandler
{
	private GAUser user;


	@SubscribeEvent
	public void onClientJoinServer(FMLNetworkEvent.ClientConnectedToServerEvent event)
	{
		this.user = GAStorage.init();

		if (this.user != null)
		{
			this.user.startSession();

			AetherCore.ANALYTICS.uploadPending();
		}
	}

	@SubscribeEvent
	public void onClientLeaveServer(FMLNetworkEvent.ClientDisconnectionFromServerEvent event)
	{
		if (this.user != null)
		{
			this.user.endSession();
			this.user = null;

			AetherCore.ANALYTICS.uploadPending();
		}
	}
}
