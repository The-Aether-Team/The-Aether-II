package com.gildedgames.aether.common.network.packets;

import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.network.MessageHandlerClient;
import com.gildedgames.aether.common.network.MessageHandlerServer;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketSetPlayedIntro implements IMessage
{

	private boolean flag = true;

	public PacketSetPlayedIntro()
	{

	}

	public PacketSetPlayedIntro(final boolean flag)
	{
		this.flag = flag;
	}

	@Override
	public void fromBytes(final ByteBuf buf)
	{
		this.flag = buf.readBoolean();
	}

	@Override
	public void toBytes(final ByteBuf buf)
	{
		buf.writeBoolean(this.flag);
	}

	public static class HandlerClient extends MessageHandlerClient<PacketSetPlayedIntro, IMessage>
	{
		@Override
		public IMessage onMessage(final PacketSetPlayedIntro message, final EntityPlayer player)
		{
			if (player == null || player.world == null)
			{
				return null;
			}

			final PlayerAether playerAether = PlayerAether.getPlayer(player);

			playerAether.getTeleportingModule().setPlayedIntro(message.flag);

			return null;
		}
	}

	public static class HandlerServer extends MessageHandlerServer<PacketSetPlayedIntro, IMessage>
	{
		@Override
		public IMessage onMessage(final PacketSetPlayedIntro message, final EntityPlayer player)
		{
			if (player == null || player.world == null)
			{
				return null;
			}

			final PlayerAether playerAether = PlayerAether.getPlayer(player);

			playerAether.getTeleportingModule().setPlayedIntro(message.flag);

			return null;
		}
	}
}
