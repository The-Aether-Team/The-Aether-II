package com.gildedgames.aether.common.network.packets.dialog;

import com.gildedgames.aether.api.player.IPlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerDialogModule;
import com.gildedgames.aether.common.network.IMessage;
import com.gildedgames.aether.common.network.MessageHandlerServer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;

public class PacketCloseScene implements IMessage
{
	public static class HandlerServer extends MessageHandlerServer<PacketCloseScene>
	{
		@Override
		protected void onMessage(PacketCloseScene message, ServerPlayerEntity player)
		{
			final IPlayerAether aePlayer = PlayerAether.getPlayer(player);

			final PlayerDialogModule dialogModule = aePlayer.getModule(PlayerDialogModule.class);
			dialogModule.closeCurrentScene();
		}
	}
}
