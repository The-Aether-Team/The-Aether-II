package com.gildedgames.orbis.common.network.packets;

import com.gildedgames.aether.api.io.NBTFunnel;
import com.gildedgames.aether.api.orbis.IWorldObject;
import com.gildedgames.aether.api.orbis.IWorldObjectGroup;
import com.gildedgames.aether.api.orbis.IWorldObjectManager;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.world.WorldObjectManager;
import com.gildedgames.aether.common.network.MessageHandlerServer;
import com.gildedgames.aether.common.network.util.PacketMultipleParts;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketOrbisWorldObjectAdd extends PacketMultipleParts
{
	private int groupId;

	private IWorldObject worldObject;

	private NBTFunnel funnel;

	public PacketOrbisWorldObjectAdd()
	{
		super();
	}

	/**
	 * Packet part constructor.
	 * @param data The data.
	 */
	private PacketOrbisWorldObjectAdd(final byte[] data)
	{
		super(data);
	}

	public PacketOrbisWorldObjectAdd(final World world, final IWorldObjectGroup group, final IWorldObject object)
	{
		super();

		final IWorldObjectManager manager = WorldObjectManager.get(world);

		this.groupId = manager.getID(group);
		this.worldObject = object;
	}

	@Override
	public void read(final ByteBuf buf)
	{
		final NBTTagCompound tag = ByteBufUtils.readTag(buf);

		this.funnel = AetherCore.io().createFunnel(tag);

		this.groupId = buf.readInt();
	}

	@Override
	public void write(final ByteBuf buf)
	{
		final NBTTagCompound tag = new NBTTagCompound();
		final NBTFunnel funnel = AetherCore.io().createFunnel(tag);

		funnel.set("worldObject", this.worldObject);

		ByteBufUtils.writeTag(buf, tag);

		buf.writeInt(this.groupId);
	}

	@Override
	public PacketMultipleParts createPart(final byte[] data)
	{
		return new PacketOrbisWorldObjectAdd(data);
	}

	public static class HandlerServer extends MessageHandlerServer<PacketOrbisWorldObjectAdd, IMessage>
	{
		@Override
		public IMessage onMessage(final PacketOrbisWorldObjectAdd message, final EntityPlayer player)
		{
			if (player == null || player.world == null)
			{
				return null;
			}

			final IWorldObject object = message.funnel.get(player.world, "worldObject");

			final IWorldObjectManager manager = WorldObjectManager.get(player.world);
			final IWorldObjectGroup group = manager.getGroup(message.groupId);

			group.addObject(object);

			return null;
		}
	}
}
