package com.gildedgames.orbis.common.network.packets;

import com.gildedgames.aether.api.orbis.IWorldObject;
import com.gildedgames.aether.api.orbis.IWorldObjectGroup;
import com.gildedgames.aether.api.orbis.IWorldObjectManager;
import com.gildedgames.aether.common.capabilities.world.WorldObjectManager;
import com.gildedgames.aether.common.network.MessageHandlerClient;
import com.gildedgames.aether.common.network.MessageHandlerServer;
import com.gildedgames.aether.common.network.NetworkingAether;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketOrbisWorldObjectRemove implements IMessage
{

	private int groupId, objectId, dimensionId;

	public PacketOrbisWorldObjectRemove()
	{

	}

	public PacketOrbisWorldObjectRemove(final int groupId, final int objectId, final int dimensionId)
	{
		this.groupId = groupId;
		this.objectId = objectId;
		this.dimensionId = dimensionId;
	}

	public PacketOrbisWorldObjectRemove(final World world, final IWorldObjectGroup group, final IWorldObject object)
	{
		final IWorldObjectManager manager = WorldObjectManager.get(world);

		this.groupId = manager.getID(group);
		this.objectId = group.getID(object);
		this.dimensionId = object.getWorld().provider.getDimension();
	}

	public static void onMessage(final PacketOrbisWorldObjectRemove message, final EntityPlayer player)
	{
		//TODO: This assumes the player sending this message is in the world we want to add the World Object
		//Clients cannot send a packet requestion a change in a different dimension.
		final IWorldObjectManager manager = WorldObjectManager.get(player.world);
		final IWorldObjectGroup group = manager.getGroup(message.groupId);

		group.removeObject(message.objectId);
	}

	@Override
	public void fromBytes(final ByteBuf buf)
	{
		this.groupId = buf.readInt();
		this.objectId = buf.readInt();
		this.dimensionId = buf.readInt();
	}

	@Override
	public void toBytes(final ByteBuf buf)
	{
		buf.writeInt(this.groupId);
		buf.writeInt(this.objectId);
		buf.writeInt(this.dimensionId);
	}

	public static class HandlerClient extends MessageHandlerClient<PacketOrbisWorldObjectRemove, IMessage>
	{
		@Override
		public IMessage onMessage(final PacketOrbisWorldObjectRemove message, final EntityPlayer player)
		{
			if (player == null || player.world == null)
			{
				return null;
			}

			PacketOrbisWorldObjectRemove.onMessage(message, player);

			return null;
		}
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

			PacketOrbisWorldObjectRemove.onMessage(message, player);

			NetworkingAether
					.sendPacketToPlayer(new PacketOrbisWorldObjectRemove(message.groupId, message.objectId, message.dimensionId), (EntityPlayerMP) player);

			return null;
		}
	}
}
