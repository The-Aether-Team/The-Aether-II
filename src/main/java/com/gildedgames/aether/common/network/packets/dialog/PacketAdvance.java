package com.gildedgames.aether.common.network.packets.dialog;

import com.gildedgames.aether.api.player.IPlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerDialogModule;
import com.gildedgames.aether.common.network.MessageHandlerClient;
import com.gildedgames.aether.common.network.MessageHandlerServer;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketAdvance implements IMessage
{

	public PacketAdvance()
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

	public static class HandlerClient extends MessageHandlerClient<PacketAdvance, PacketAdvance>
	{
		@Override
		public PacketAdvance onMessage(final PacketAdvance message, final EntityPlayer player)
		{
			final IPlayerAether aePlayer = PlayerAether.getPlayer(player);

			if (aePlayer.getDialogController() instanceof PlayerDialogModule)
			{
				PlayerDialogModule module = (PlayerDialogModule) aePlayer.getDialogController();

				module.advanceClient();
			}

			return null;
		}
	}

	public static class HandlerServer extends MessageHandlerServer<PacketAdvance, PacketAdvance>
	{
		@Override
		public PacketAdvance onMessage(final PacketAdvance message, final EntityPlayer player)
		{
			final IPlayerAether aePlayer = PlayerAether.getPlayer(player);

			aePlayer.getDialogController().advance();

			return null;
		}
	}
}
