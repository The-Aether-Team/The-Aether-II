package com.gildedgames.aether.common.network.packets;

import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerCampfiresModule;
import com.gildedgames.aether.common.network.IMessage;
import com.gildedgames.aether.common.network.MessageHandlerServer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;

public class PacketSetShouldRespawnAtCampfire implements IMessage
{
	public static class HandlerServer extends MessageHandlerServer<PacketSetShouldRespawnAtCampfire>
	{
		@Override
		protected void onMessage(PacketSetShouldRespawnAtCampfire message, ServerPlayerEntity player)
		{
			if (player == null || player.world == null)
			{
				return;
			}

			PlayerAether playerAether = PlayerAether.getPlayer(player);
			playerAether.getModule(PlayerCampfiresModule.class).setShouldRespawnAtCampfire(true);
		}
	}

}
