package com.gildedgames.aether.common.network.packets;

import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerTeleportingModule;
import com.gildedgames.aether.common.network.IMessage;
import com.gildedgames.aether.common.network.MessageHandlerClient;
import com.gildedgames.aether.common.network.MessageHandlerServer;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;

public class PacketSetPlayedIntro implements IMessage
{

	private boolean flag;

	public PacketSetPlayedIntro(final boolean flag)
	{
		this.flag = flag;
	}

	@Override
	public void fromBytes(final PacketBuffer buf)
	{
		this.flag = buf.readBoolean();
	}

	@Override
	public void toBytes(final PacketBuffer buf)
	{
		buf.writeBoolean(this.flag);
	}

	public static class HandlerClient extends MessageHandlerClient<PacketSetPlayedIntro>
	{
		@Override
		protected void onMessage(PacketSetPlayedIntro message, ClientPlayerEntity player)
		{
			if (player == null || player.world == null)
			{
				return;
			}

			final PlayerAether playerAether = PlayerAether.getPlayer(player);
			playerAether.getModule(PlayerTeleportingModule.class).setPlayedIntro(message.flag);
		}
	}

	public static class HandlerServer extends MessageHandlerServer<PacketSetPlayedIntro>
	{
		@Override
		protected void onMessage(PacketSetPlayedIntro message, ServerPlayerEntity player)
		{
			if (player == null || player.world == null)
			{
				return;
			}

			final PlayerAether playerAether = PlayerAether.getPlayer(player);
			playerAether.getModule(PlayerTeleportingModule.class).setPlayedIntro(message.flag);
		}
	}
}
