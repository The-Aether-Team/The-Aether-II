package com.gildedgames.aether.common.network.packets;

import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.network.MessageHandlerClient;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketTalkedToEdison implements IMessage
{

	private boolean flag = true;

	public PacketTalkedToEdison()
	{

	}

	public PacketTalkedToEdison(final boolean flag)
	{
		this.flag = flag;
	}

	@Override
	public void fromBytes(final ByteBuf buf)
	{
		this.flag = buf.readBoolean();
	}

	@Override
	public void toBytes(final ByteBuf buf)
	{
		buf.writeBoolean(this.flag);
	}

	public static class HandlerClient extends MessageHandlerClient<PacketTalkedToEdison, IMessage>
	{
		@Override
		public IMessage onMessage(final PacketTalkedToEdison message, final EntityPlayer player)
		{
			if (player == null || player.world == null)
			{
				return null;
			}

			final PlayerAether playerAether = PlayerAether.getPlayer(player);

			playerAether.getProgressModule().setHasTalkedToEdison(message.flag);

			return null;
		}
	}
}
