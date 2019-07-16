package com.gildedgames.aether.common.network.packets;

import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerSwetTrackerModule;
import com.gildedgames.aether.common.entities.monsters.EntitySwet;
import com.gildedgames.aether.common.network.IMessage;
import com.gildedgames.aether.common.network.MessageHandlerClient;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;

public class PacketDetachSwet implements IMessage
{
	private EntitySwet.Type type;

	private int id;

	public PacketDetachSwet(final EntitySwet.Type type, final int id)
	{
		this.type = type;
		this.id = id;
	}

	@Override
	public void fromBytes(final PacketBuffer buf)
	{
		this.type = EntitySwet.Type.fromOrdinal(buf.readInt());
		this.id = buf.readInt();
	}

	@Override
	public void toBytes(final PacketBuffer buf)
	{
		buf.writeInt(this.type.ordinal());
		buf.writeInt(this.id);
	}

	public static class HandlerClient extends MessageHandlerClient<PacketDetachSwet>
	{
		@Override
		protected void onMessage(PacketDetachSwet message, ClientPlayerEntity player)
		{
			if (player == null || player.world == null)
			{
				return;
			}

			Entity entity = player.world.getEntityByID(message.id);

			if (!(entity instanceof PlayerEntity))
			{
				throw new IllegalArgumentException("Entity is not a player");
			}

			final PlayerAether playerAether = PlayerAether.getPlayer((PlayerEntity) entity);

			EntitySwet remove = null;

			for (final EntitySwet swet : playerAether.getModule(PlayerSwetTrackerModule.class).getLatchedSwets())
			{
				if (swet.getSwetType() == message.type)
				{
					remove = swet;
				}
			}

			if (remove != null)
			{
				playerAether.getModule(PlayerSwetTrackerModule.class).detachSwet(remove);
			}
		}
	}
}
