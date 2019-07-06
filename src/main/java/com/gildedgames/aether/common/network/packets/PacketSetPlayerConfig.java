package com.gildedgames.aether.common.network.packets;

import com.gildedgames.aether.common.ConfigAether;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerConfigModule;
import com.gildedgames.aether.common.network.MessageHandlerServer;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketSetPlayerConfig implements IMessage
{

	private boolean skipIntro;

	public PacketSetPlayerConfig()
	{

	}

	public PacketSetPlayerConfig(ConfigAether config)
	{
		this.skipIntro = config.skipIntro();
	}

	@Override
	public void fromBytes(final ByteBuf buf)
	{
		this.skipIntro = buf.readBoolean();
	}

	@Override
	public void toBytes(final ByteBuf buf)
	{
		buf.writeBoolean(this.skipIntro);
	}

	public static class HandlerServer extends MessageHandlerServer<PacketSetPlayerConfig, IMessage>
	{
		@Override
		public IMessage onMessage(final PacketSetPlayerConfig message, final EntityPlayer player)
		{
			if (player == null || player.world == null)
			{
				return null;
			}

			PlayerAether playerAether = PlayerAether.getPlayer(player);
			playerAether.getModule(PlayerConfigModule.class).setSkipIntro(message.skipIntro);

			return null;
		}
	}

}
