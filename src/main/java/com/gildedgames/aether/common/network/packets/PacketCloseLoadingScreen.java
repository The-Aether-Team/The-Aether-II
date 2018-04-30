package com.gildedgames.aether.common.network.packets;

import com.gildedgames.aether.client.ClientEventHandler;
import com.gildedgames.aether.common.network.MessageHandlerClient;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketCloseLoadingScreen implements IMessage
{

	public PacketCloseLoadingScreen()
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

	public static class HandlerClient extends MessageHandlerClient<PacketCloseLoadingScreen, IMessage>
	{
		@Override
		public IMessage onMessage(final PacketCloseLoadingScreen message, final EntityPlayer player)
		{
			if (player == null || player.world == null)
			{
				return null;
			}

			Minecraft.getMinecraft().displayGuiScreen(null);

			ClientEventHandler.setDrawLoading(false);
			ClientEventHandler.setDrawBlackScreen(false);

			return null;
		}
	}

}
