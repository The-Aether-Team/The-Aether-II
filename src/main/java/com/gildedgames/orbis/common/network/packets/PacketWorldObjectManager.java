package com.gildedgames.orbis.common.network.packets;

import com.gildedgames.aether.common.capabilities.world.WorldObjectManager;
import com.gildedgames.aether.common.network.MessageHandlerClient;
import com.gildedgames.aether.common.network.util.PacketMultipleParts;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketWorldObjectManager extends PacketMultipleParts
{

	private WorldObjectManager manager;

	private NBTTagCompound tag;

	public PacketWorldObjectManager()
	{

	}

	private PacketWorldObjectManager(final byte[] data)
	{
		super(data);
	}

	public PacketWorldObjectManager(final WorldObjectManager manager)
	{
		this.manager = manager;
	}

	@Override
	public PacketMultipleParts createPart(final byte[] data)
	{
		return new PacketWorldObjectManager(data);
	}

	@Override
	public void read(final ByteBuf buf)
	{
		this.tag = ByteBufUtils.readTag(buf);
	}

	@Override
	public void write(final ByteBuf buf)
	{
		final NBTTagCompound tag = new NBTTagCompound();

		this.manager.writeToNBT(tag);

		ByteBufUtils.writeTag(buf, tag);
	}

	public static class HandlerClient extends MessageHandlerClient<PacketWorldObjectManager, IMessage>
	{
		@Override
		public IMessage onMessage(final PacketWorldObjectManager message, final EntityPlayer player)
		{
			if (player == null || player.world == null)
			{
				return null;
			}

			final WorldObjectManager manager = WorldObjectManager.get(player.world);

			manager.setWorld(player.world);

			manager.readFromNBT(message.tag);

			return null;
		}
	}
}
