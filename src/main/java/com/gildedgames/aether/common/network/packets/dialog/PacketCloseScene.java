package com.gildedgames.aether.common.network.packets.dialog;

import com.gildedgames.aether.api.player.IPlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.network.MessageHandlerServer;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketCloseScene implements IMessage
{
	public PacketCloseScene()
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

	public static class HandlerServer extends MessageHandlerServer<PacketCloseScene, PacketCloseScene>
	{
		@Override
		public PacketCloseScene onMessage(final PacketCloseScene message, final EntityPlayer player)
		{
			final IPlayerAether aePlayer = PlayerAether.getPlayer(player);
			aePlayer.getDialogController().closeScene(aePlayer.getDialogController().getCurrentSceneInstance());

			return null;
		}
	}
}
