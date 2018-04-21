package com.gildedgames.aether.common.network.packets;

import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.network.MessageHandlerClient;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketSwitchToAetherInventory implements IMessage
{

	public PacketSwitchToAetherInventory()
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

	public static class HandlerClient extends MessageHandlerClient<PacketSwitchToAetherInventory, IMessage>
	{
		@Override
		public IMessage onMessage(final PacketSwitchToAetherInventory message, final EntityPlayer player)
		{
			if (player == null || player.world == null)
			{
				return null;
			}

			final PlayerAether playerAether = PlayerAether.getPlayer(player);

			playerAether.getSeparateInventoryModule().switchToAetherInventory();

			return null;
		}
	}

}
