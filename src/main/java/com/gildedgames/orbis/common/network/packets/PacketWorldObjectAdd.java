package com.gildedgames.orbis.common.network.packets;

import com.gildedgames.aether.api.io.NBTFunnel;
import com.gildedgames.aether.api.orbis.IWorldObject;
import com.gildedgames.aether.api.orbis.IWorldObjectGroup;
import com.gildedgames.aether.api.orbis.IWorldObjectManager;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.world.WorldObjectManager;
import com.gildedgames.aether.common.network.MessageHandlerClient;
import com.gildedgames.aether.common.network.MessageHandlerServer;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.aether.common.network.util.PacketMultipleParts;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketWorldObjectAdd extends PacketMultipleParts
{
	private int groupId, dimensionId;

	private IWorldObject worldObject;

	private NBTFunnel funnel;

	public PacketWorldObjectAdd()
	{
		super();
	}

	/**
	 * Packet part constructor.
	 * @param data The data.
	 */
	private PacketWorldObjectAdd(final byte[] data)
	{
		super(data);
	}

	public PacketWorldObjectAdd(final int groupId, final IWorldObject object, final int dimensionId)
	{
		this.groupId = groupId;
		this.worldObject = object;
		this.dimensionId = dimensionId;
	}

	public PacketWorldObjectAdd(final World world, final IWorldObjectGroup group, final IWorldObject object)
	{
		final IWorldObjectManager manager = WorldObjectManager.get(world);

		this.groupId = manager.getID(group);
		this.worldObject = object;
		this.dimensionId = object.getWorld().provider.getDimension();
	}

	public static void onMessage(final PacketWorldObjectAdd message, final EntityPlayer player)
	{
		//TODO: This assumes the player sending this message is in the world we want to add the World Object
		//Clients cannot send a packet requestion a change in a different dimension.
		final IWorldObject object = message.funnel.get(player.world, "worldObject");

		final IWorldObjectManager manager = WorldObjectManager.get(message.dimensionId, player);
		final IWorldObjectGroup group = manager.getGroup(message.groupId);

		group.addObject(object);
	}

	@Override
	public void read(final ByteBuf buf)
	{
		final NBTTagCompound tag = ByteBufUtils.readTag(buf);

		this.funnel = AetherCore.io().createFunnel(tag);

		this.groupId = buf.readInt();
		this.dimensionId = buf.readInt();
	}

	@Override
	public void write(final ByteBuf buf)
	{
		final NBTTagCompound tag = new NBTTagCompound();
		final NBTFunnel funnel = AetherCore.io().createFunnel(tag);

		funnel.set("worldObject", this.worldObject);

		ByteBufUtils.writeTag(buf, tag);

		buf.writeInt(this.groupId);
		buf.writeInt(this.dimensionId);
	}

	@Override
	public PacketMultipleParts createPart(final byte[] data)
	{
		return new PacketWorldObjectAdd(data);
	}

	public static class HandlerServer extends MessageHandlerServer<PacketWorldObjectAdd, IMessage>
	{
		@Override
		public IMessage onMessage(final PacketWorldObjectAdd message, final EntityPlayer player)
		{
			if (player == null || player.world == null)
			{
				return null;
			}

			PacketWorldObjectAdd.onMessage(message, player);

			NetworkingAether
					.sendPacketToDimension(new PacketWorldObjectAdd(message.groupId, message.worldObject, message.dimensionId), message.dimensionId);

			return null;
		}
	}

	public static class HandlerClient extends MessageHandlerClient<PacketWorldObjectAdd, IMessage>
	{
		@Override
		public IMessage onMessage(final PacketWorldObjectAdd message, final EntityPlayer player)
		{
			if (player == null || player.world == null)
			{
				return null;
			}

			PacketWorldObjectAdd.onMessage(message, player);

			return null;
		}
	}
}
