package com.gildedgames.orbis.common.network.packets;

import com.gildedgames.aether.common.network.MessageHandlerServer;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.aether.common.network.util.PacketMultipleParts;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketOrbisRequestProjectListing extends PacketMultipleParts
{

	public PacketOrbisRequestProjectListing()
	{

	}

	private PacketOrbisRequestProjectListing(final byte[] data)
	{
		super(data);
	}

	@Override
	public void read(final ByteBuf buf)
	{

	}

	@Override
	public void write(final ByteBuf buf)
	{

	}

	@Override
	public PacketMultipleParts createPart(final byte[] data)
	{
		return new PacketOrbisRequestProjectListing(data);
	}

	public static class HandlerServer extends MessageHandlerServer<PacketOrbisRequestProjectListing, IMessage>
	{
		@Override
		public IMessage onMessage(final PacketOrbisRequestProjectListing message, final EntityPlayer player)
		{
			if (player == null || player.world == null)
			{
				return null;
			}

			NetworkingAether.sendPacketToPlayer(new PacketOrbisSendProjectListing(), (EntityPlayerMP) player);

			return null;
		}
	}
}
