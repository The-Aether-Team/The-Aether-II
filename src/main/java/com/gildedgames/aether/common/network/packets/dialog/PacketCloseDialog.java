package com.gildedgames.aether.common.network.packets.dialog;

import com.gildedgames.aether.api.player.IPlayerAether;
import com.gildedgames.aether.common.capabilities.player.PlayerAether;
import com.gildedgames.aether.common.network.MessageHandlerServer;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketCloseDialog implements IMessage
{
	public PacketCloseDialog()
	{
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{

	}

	@Override
	public void toBytes(ByteBuf buf)
	{

	}

	public static class HandlerServer extends MessageHandlerServer<PacketCloseDialog, PacketCloseDialog>
	{
		@Override
		public PacketCloseDialog onMessage(PacketCloseDialog message, EntityPlayer player)
		{
			IPlayerAether aePlayer = PlayerAether.getPlayer(player);
			aePlayer.getDialogController().closeScene();

			return null;
		}
	}
}
