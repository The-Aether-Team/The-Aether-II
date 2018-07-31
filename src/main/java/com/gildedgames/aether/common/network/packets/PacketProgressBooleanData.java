package com.gildedgames.aether.common.network.packets;

import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.network.MessageHandlerClient;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketProgressBooleanData implements IMessage
{

	private String key;

	private boolean flag = true;

	public PacketProgressBooleanData()
	{

	}

	public PacketProgressBooleanData(String key, final boolean flag)
	{
		this.key = key;
		this.flag = flag;
	}

	@Override
	public void fromBytes(final ByteBuf buf)
	{
		this.key = ByteBufUtils.readUTF8String(buf);
		this.flag = buf.readBoolean();
	}

	@Override
	public void toBytes(final ByteBuf buf)
	{
		ByteBufUtils.writeUTF8String(buf, this.key);
		buf.writeBoolean(this.flag);
	}

	public static class HandlerClient extends MessageHandlerClient<PacketProgressBooleanData, IMessage>
	{
		@Override
		public IMessage onMessage(final PacketProgressBooleanData message, final EntityPlayer player)
		{
			if (player == null || player.world == null)
			{
				return null;
			}

			final PlayerAether playerAether = PlayerAether.getPlayer(player);

			playerAether.getProgressModule().setBoolean(message.key, message.flag);

			return null;
		}
	}
}
