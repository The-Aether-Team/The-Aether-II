package com.gildedgames.orbis.common.network.packets;

import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.network.MessageHandlerClient;
import com.gildedgames.aether.common.network.MessageHandlerServer;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketOrbisExtendedReach implements IMessage
{

	private double extendedReach;

	public PacketOrbisExtendedReach()
	{

	}

	public PacketOrbisExtendedReach(final double extendedReach)
	{
		this.extendedReach = extendedReach;
	}

	@Override
	public void fromBytes(final ByteBuf buf)
	{
		this.extendedReach = buf.readDouble();
	}

	@Override
	public void toBytes(final ByteBuf buf)
	{
		buf.writeDouble(this.extendedReach);
	}

	public static class HandlerServer extends MessageHandlerServer<PacketOrbisExtendedReach, IMessage>
	{
		@Override
		public IMessage onMessage(final PacketOrbisExtendedReach message, final EntityPlayer player)
		{
			if (player == null || player.world == null)
			{
				return null;
			}

			final PlayerAether playerAether = PlayerAether.getPlayer(player);

			playerAether.getOrbisModule().setExtendedReach(message.extendedReach);

			return null;
		}
	}

	public static class HandlerClient extends MessageHandlerClient<PacketOrbisExtendedReach, IMessage>
	{
		@Override
		public IMessage onMessage(final PacketOrbisExtendedReach message, final EntityPlayer player)
		{
			if (player == null || player.world == null)
			{
				return null;
			}

			final PlayerAether playerAether = PlayerAether.getPlayer(player);

			if (playerAether.getOrbisModule().inDeveloperMode())
			{
				playerAether.getOrbisModule().setExtendedReach(message.extendedReach);
			}

			return null;
		}
	}
}
