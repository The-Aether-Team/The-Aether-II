package com.gildedgames.aether.common.network.packets;

import com.gildedgames.aether.client.gui.util.IRemoteClose;
import com.gildedgames.aether.common.network.IMessage;
import com.gildedgames.aether.common.network.MessageHandlerClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;

public class PacketCloseScreen implements IMessage
{

	private int guiID;

	public PacketCloseScreen(int guiID)
	{
		this.guiID = guiID;
	}

	@Override
	public void fromBytes(final PacketBuffer buf)
	{
		this.guiID = buf.readInt();
	}

	@Override
	public void toBytes(final PacketBuffer buf)
	{
		buf.writeInt(this.guiID);
	}

	public static class HandlerClient extends MessageHandlerClient<PacketCloseScreen>
	{
		@Override
		protected void onMessage(PacketCloseScreen message, ClientPlayerEntity player)
		{
			Minecraft mc = Minecraft.getInstance();

			Screen screen = mc.currentScreen;

			if (screen instanceof IRemoteClose && message.guiID == ((IRemoteClose) screen).getConfirmID())
			{
				mc.currentScreen = null;

				player.closeScreen();
			}
		}
	}
}
