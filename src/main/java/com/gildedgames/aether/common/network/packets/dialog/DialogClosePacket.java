package com.gildedgames.aether.common.network.packets.dialog;

import com.gildedgames.aether.api.player.IPlayerAether;
import com.gildedgames.aether.common.capabilities.player.PlayerAether;
import com.gildedgames.aether.common.network.MessageHandlerServer;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class DialogClosePacket implements IMessage
{
	public DialogClosePacket() { }

	@Override
	public void fromBytes(ByteBuf buf)
	{

	}

	@Override
	public void toBytes(ByteBuf buf)
	{

	}

	public static class HandlerServer extends MessageHandlerServer<DialogClosePacket, DialogClosePacket>
	{
		@Override
		public DialogClosePacket onMessage(DialogClosePacket message, EntityPlayer player)
		{
			IPlayerAether aePlayer = PlayerAether.getPlayer(player);
			aePlayer.getDialogController().closeScene();

			return null;
		}
	}
}
