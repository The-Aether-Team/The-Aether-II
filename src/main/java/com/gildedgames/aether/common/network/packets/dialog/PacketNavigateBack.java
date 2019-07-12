package com.gildedgames.aether.common.network.packets.dialog;

import com.gildedgames.aether.api.player.IPlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerDialogModule;
import com.gildedgames.aether.common.network.MessageHandlerClient;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketNavigateBack implements IMessage
{

	public PacketNavigateBack()
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

	public static class HandlerClient extends MessageHandlerClient<PacketNavigateBack, PacketNavigateBack>
	{
		@Override
		public PacketNavigateBack onMessage(final PacketNavigateBack message, final PlayerEntity player)
		{
			final IPlayerAether aePlayer = PlayerAether.getPlayer(player);
			final PlayerDialogModule module = aePlayer.getModule(PlayerDialogModule.class);

			module.navigateBackClient();

			return null;
		}
	}
}
