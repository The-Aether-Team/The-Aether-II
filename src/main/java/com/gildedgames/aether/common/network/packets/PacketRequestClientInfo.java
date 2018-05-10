package com.gildedgames.aether.common.network.packets;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.network.MessageHandlerClient;
import com.gildedgames.aether.common.network.NetworkingAether;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketRequestClientInfo implements IMessage
{

	public PacketRequestClientInfo()
	{
	}

	@Override
	public void fromBytes(final ByteBuf buf)
	{

	}

	@Override
	public void toBytes(final ByteBuf buf)
	{

	}

	public static class HandlerClient extends MessageHandlerClient<PacketRequestClientInfo, PacketRequestClientInfo>
	{
		@Override
		public PacketRequestClientInfo onMessage(final PacketRequestClientInfo message, final EntityPlayer player)
		{
			NetworkingAether.sendPacketToServer(new PacketSetPlayerConfig(AetherCore.CONFIG));

			return null;
		}
	}
}
