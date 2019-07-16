package com.gildedgames.aether.common.network.packets.dialog;

import com.gildedgames.aether.api.player.IPlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerDialogModule;
import com.gildedgames.aether.common.network.MessageHandlerClient;
import com.gildedgames.aether.common.network.MessageHandlerServer;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import com.gildedgames.aether.common.network.IMessage;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;

public class PacketAdvance implements IMessage
{
	public static class HandlerClient extends MessageHandlerClient<PacketAdvance>
	{
		@Override
		protected void onMessage(PacketAdvance message, ClientPlayerEntity player)
		{
			final IPlayerAether aePlayer = PlayerAether.getPlayer(player);

			final PlayerDialogModule dialogModule = aePlayer.getModule(PlayerDialogModule.class);
			dialogModule.advanceClient();
		}
	}

	public static class HandlerServer extends MessageHandlerServer<PacketAdvance>
	{
		@Override
		protected void onMessage(PacketAdvance message, ServerPlayerEntity player)
		{
			final IPlayerAether aePlayer = PlayerAether.getPlayer(player);

			final PlayerDialogModule dialogModule = aePlayer.getModule(PlayerDialogModule.class);
			dialogModule.advance();
		}
	}
}
