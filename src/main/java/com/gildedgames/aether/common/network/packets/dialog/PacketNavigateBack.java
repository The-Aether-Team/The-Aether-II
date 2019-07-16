package com.gildedgames.aether.common.network.packets.dialog;

import com.gildedgames.aether.api.player.IPlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerDialogModule;
import com.gildedgames.aether.common.network.IMessage;
import com.gildedgames.aether.common.network.MessageHandlerClient;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;

public class PacketNavigateBack implements IMessage
{
	public static class HandlerClient extends MessageHandlerClient<PacketNavigateBack>
	{
		@Override
		protected void onMessage(PacketNavigateBack message, ClientPlayerEntity player)
		{
			final IPlayerAether aePlayer = PlayerAether.getPlayer(player);
			final PlayerDialogModule module = aePlayer.getModule(PlayerDialogModule.class);

			module.navigateBackClient();
		}
	}
}
