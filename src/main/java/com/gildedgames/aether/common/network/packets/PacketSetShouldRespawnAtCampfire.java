package com.gildedgames.aether.common.network.packets;

import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerCampfiresModule;
import com.gildedgames.aether.common.network.MessageHandlerServer;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketSetShouldRespawnAtCampfire implements IMessage
{

	public PacketSetShouldRespawnAtCampfire()
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

	public static class HandlerServer extends MessageHandlerServer<PacketSetShouldRespawnAtCampfire, IMessage>
	{
		@Override
		public IMessage onMessage(final PacketSetShouldRespawnAtCampfire message, final EntityPlayer player)
		{
			if (player == null || player.world == null)
			{
				return null;
			}

			PlayerAether playerAether = PlayerAether.getPlayer(player);
			playerAether.getModule(PlayerCampfiresModule.class).setShouldRespawnAtCampfire(true);

			return null;
		}
	}

}
