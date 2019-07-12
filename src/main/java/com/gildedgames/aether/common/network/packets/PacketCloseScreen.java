package com.gildedgames.aether.common.network.packets;

import com.gildedgames.aether.client.gui.util.IRemoteClose;
import com.gildedgames.aether.common.network.MessageHandlerClient;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketCloseScreen implements IMessage
{

	private int guiID;

	public PacketCloseScreen()
	{

	}

	public PacketCloseScreen(int guiID)
	{
		this.guiID = guiID;
	}

	@Override
	public void fromBytes(final ByteBuf buf)
	{
		this.guiID = buf.readInt();
	}

	@Override
	public void toBytes(final ByteBuf buf)
	{
		buf.writeInt(this.guiID);
	}

	public static class HandlerClient extends MessageHandlerClient<PacketCloseScreen, IMessage>
	{
		@Override
		public IMessage onMessage(PacketCloseScreen message, PlayerEntity player)
		{
			Minecraft mc = Minecraft.getInstance();
			Screen screen = mc.currentScreen;

			if (screen instanceof IRemoteClose && message.guiID == ((IRemoteClose) screen).getConfirmID())
			{
				mc.currentScreen = null;
				player.closeScreen();
			}

			return null;
		}

	}
}
