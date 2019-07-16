package com.gildedgames.aether.common.network.packets;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.network.IMessage;
import com.gildedgames.aether.common.network.MessageHandlerClient;
import com.gildedgames.aether.common.network.NetworkingAether;
import net.minecraft.client.entity.player.ClientPlayerEntity;

public class PacketRequestClientInfo implements IMessage
{
	public static class HandlerClient extends MessageHandlerClient<PacketRequestClientInfo>
	{
		@Override
		protected void onMessage(PacketRequestClientInfo message, ClientPlayerEntity player)
		{
			NetworkingAether.sendPacketToServer(new PacketSetPlayerConfig(AetherCore.CONFIG));
		}
	}
}
