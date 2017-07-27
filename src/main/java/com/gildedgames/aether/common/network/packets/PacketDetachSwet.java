package com.gildedgames.aether.common.network.packets;

import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.entities.living.mobs.EntitySwet;
import com.gildedgames.aether.common.network.MessageHandlerClient;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketDetachSwet implements IMessage
{

	private EntitySwet.Type type;

	public PacketDetachSwet()
	{

	}

	public PacketDetachSwet(final EntitySwet.Type type)
	{
		this.type = type;
	}

	@Override
	public void fromBytes(final ByteBuf buf)
	{
		this.type = EntitySwet.Type.fromOrdinal(buf.readInt());
	}

	@Override
	public void toBytes(final ByteBuf buf)
	{
		buf.writeInt(this.type.ordinal());
	}

	@SideOnly(Side.CLIENT)
	public static class HandlerClient extends MessageHandlerClient<PacketDetachSwet, IMessage>
	{
		@Override
		public IMessage onMessage(final PacketDetachSwet message, final EntityPlayer player)
		{
			if (player == null || player.world == null)
			{
				return null;
			}

			final PlayerAether playerAether = PlayerAether.getPlayer(player);

			EntitySwet remove = null;

			for (final EntitySwet swet : playerAether.getSwetTracker().getLatchedSwets())
			{
				if (swet.getType() == message.type)
				{
					remove = swet;
				}
			}

			if (remove != null)
			{
				playerAether.getSwetTracker().detachSwet(remove);
			}

			return null;
		}
	}
}
