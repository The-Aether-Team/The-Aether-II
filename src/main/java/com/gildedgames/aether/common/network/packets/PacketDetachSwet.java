package com.gildedgames.aether.common.network.packets;

import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerSwetTrackerModule;
import com.gildedgames.aether.common.entities.monsters.EntitySwet;
import com.gildedgames.aether.common.network.MessageHandlerClient;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketDetachSwet implements IMessage
{

	private EntitySwet.Type type;

	private int id;

	public PacketDetachSwet()
	{

	}

	public PacketDetachSwet(final EntitySwet.Type type, final int id)
	{
		this.type = type;
		this.id = id;
	}

	@Override
	public void fromBytes(final ByteBuf buf)
	{
		this.type = EntitySwet.Type.fromOrdinal(buf.readInt());
		this.id = buf.readInt();
	}

	@Override
	public void toBytes(final ByteBuf buf)
	{
		buf.writeInt(this.type.ordinal());
		buf.writeInt(this.id);
	}

	public static class HandlerClient extends MessageHandlerClient<PacketDetachSwet, IMessage>
	{
		@Override
		public IMessage onMessage(final PacketDetachSwet message, final EntityPlayer player)
		{
			if (player == null || player.world == null)
			{
				return null;
			}

			Entity entity = player.world.getEntityByID(message.id);

			if (!(entity instanceof EntityPlayer))
			{
				throw new IllegalArgumentException("Entity is not a player");
			}

			final PlayerAether playerAether = PlayerAether.getPlayer((EntityPlayer) entity);

			EntitySwet remove = null;

			for (final EntitySwet swet : playerAether.getModule(PlayerSwetTrackerModule.class).getLatchedSwets())
			{
				if (swet.getType() == message.type)
				{
					remove = swet;
					break;
				}
			}

			if (remove != null)
			{
				playerAether.getModule(PlayerSwetTrackerModule.class).detachSwet(remove);
			}

			return null;
		}
	}
}
