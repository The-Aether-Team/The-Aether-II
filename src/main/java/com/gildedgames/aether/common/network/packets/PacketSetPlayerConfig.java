package com.gildedgames.aether.common.network.packets;

import com.gildedgames.aether.common.ConfigAether;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerConfigModule;
import com.gildedgames.aether.common.network.MessageHandlerServer;
import net.minecraft.entity.player.PlayerEntity;
import com.gildedgames.aether.common.network.IMessage;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;

public class PacketSetPlayerConfig implements IMessage
{
	private boolean skipIntro;

	public PacketSetPlayerConfig(ConfigAether config)
	{
		this.skipIntro = config.skipIntro();
	}

	@Override
	public void fromBytes(final PacketBuffer buf)
	{
		this.skipIntro = buf.readBoolean();
	}

	@Override
	public void toBytes(final PacketBuffer buf)
	{
		buf.writeBoolean(this.skipIntro);
	}

	public static class HandlerServer extends MessageHandlerServer<PacketSetPlayerConfig>
	{
		@Override
		protected void onMessage(PacketSetPlayerConfig message, ServerPlayerEntity player)
		{
			if (player == null || player.world == null)
			{
				return;
			}

			PlayerAether playerAether = PlayerAether.getPlayer(player);
			playerAether.getModule(PlayerConfigModule.class).setSkipIntro(message.skipIntro);
		}
	}

}
