package com.gildedgames.aether.common.network.packets;

import com.gildedgames.aether.client.gui.container.guidebook.discovery.DiscoveryTab;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.network.MessageHandlerServer;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketDiscoveryTabType implements IMessage
{

	private DiscoveryTab.DiscoveryTabType type;

	public PacketDiscoveryTabType()
	{

	}

	public PacketDiscoveryTabType(final DiscoveryTab.DiscoveryTabType type)
	{
		this.type = type;
	}

	@Override
	public void fromBytes(final ByteBuf buf)
	{
		final String typeString = ByteBufUtils.readUTF8String(buf);

		this.type = DiscoveryTab.DiscoveryTabType.valueOf(typeString);
	}

	@Override
	public void toBytes(final ByteBuf buf)
	{
		ByteBufUtils.writeUTF8String(buf, this.type.toString());
	}

	public static class HandlerServer extends MessageHandlerServer<PacketDiscoveryTabType, IMessage>
	{
		@Override
		public IMessage onMessage(final PacketDiscoveryTabType message, final EntityPlayer player)
		{
			if (player == null || player.world == null)
			{
				return null;
			}

			final PlayerAether playerAether = PlayerAether.getPlayer(player);

			playerAether.getProgressModule().setOpenedDiscoveryTabType(message.type);

			return null;
		}
	}
}
