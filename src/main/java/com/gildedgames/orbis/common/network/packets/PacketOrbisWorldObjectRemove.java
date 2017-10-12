package com.gildedgames.orbis.common.network.packets;

import com.gildedgames.aether.api.orbis.IWorldObject;
import com.gildedgames.aether.api.orbis.IWorldObjectGroup;
import com.gildedgames.aether.api.orbis.IWorldObjectManager;
import com.gildedgames.aether.common.capabilities.world.WorldObjectManager;
import com.gildedgames.aether.common.network.MessageHandlerServer;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketOrbisWorldObjectRemove implements IMessage
{

	private int groupId, objectId;

	public PacketOrbisWorldObjectRemove()
	{

	}

	public PacketOrbisWorldObjectRemove(final World world, final IWorldObjectGroup group, final IWorldObject object)
	{
		final IWorldObjectManager manager = WorldObjectManager.get(world);

		this.groupId = manager.getID(group);
		this.objectId = group.getID(object);
	}

	@Override
	public void fromBytes(final ByteBuf buf)
	{
		this.groupId = buf.readInt();
		this.objectId = buf.readInt();
	}

	@Override
	public void toBytes(final ByteBuf buf)
	{
		buf.writeInt(this.groupId);
		buf.writeInt(this.objectId);
	}

	public static class HandlerServer extends MessageHandlerServer<PacketOrbisWorldObjectRemove, IMessage>
	{
		@Override
		public IMessage onMessage(final PacketOrbisWorldObjectRemove message, final EntityPlayer player)
		{
			if (player == null || player.world == null)
			{
				return null;
			}

			final IWorldObjectManager manager = WorldObjectManager.get(player.world);

			final IWorldObjectGroup group = manager.getGroup(message.groupId);

			group.removeObject(message.objectId);

			return null;
		}
	}
}
