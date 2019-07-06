package com.gildedgames.aether.common.network.packets;

import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerSwetTrackerModule;
import com.gildedgames.aether.common.entities.living.mobs.EntitySwet;
import com.gildedgames.aether.common.network.MessageHandlerClient;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketLatchSwet implements IMessage
{

	private EntitySwet.Type type;

	private int id;

	public PacketLatchSwet()
	{

	}

	public PacketLatchSwet(final EntitySwet.Type type, final int id)
	{
		this.id = id;
		this.type = type;
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

	public static class HandlerClient extends MessageHandlerClient<PacketLatchSwet, IMessage>
	{
		@Override
		public IMessage onMessage(final PacketLatchSwet message, final EntityPlayer player)
		{
			if (player == null || player.world == null)
			{
				return null;
			}

			final Entity entity = player.world.getEntityByID(message.id);

			if (!(entity instanceof EntityPlayer))
			{
				throw new IllegalArgumentException("Entity is not a player");
			}

			final PlayerAether playerAether = PlayerAether.getPlayer((EntityPlayer) entity);

			final EntitySwet swet = new EntitySwet(player.world);

			swet.setPosition(player.posX, player.posY, player.posZ);
			swet.setType(message.type);

			playerAether.getModule(PlayerSwetTrackerModule.class).latchSwet(swet);

			return null;
		}
	}
}
