package com.gildedgames.aether.common.network.packets;

import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.network.MessageHandlerClient;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketMarkPlayerDeath implements IMessage
{

	private boolean flag = true;

	public PacketMarkPlayerDeath()
	{

	}

	public PacketMarkPlayerDeath(boolean flag)
	{
		this.flag = flag;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.flag = buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeBoolean(this.flag);
	}

	@SideOnly(Side.CLIENT)
	public static class HandlerClient extends MessageHandlerClient<PacketMarkPlayerDeath, IMessage>
	{
		@Override
		public IMessage onMessage(PacketMarkPlayerDeath message, EntityPlayer player)
		{
			if (player == null || player.world == null)
			{
				return null;
			}

			PlayerAether playerAether = PlayerAether.getPlayer(player);

			playerAether.setHasDiedInAetherBefore(message.flag);

			return null;
		}
	}
}
