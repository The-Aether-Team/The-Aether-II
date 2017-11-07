package com.gildedgames.orbis.common.network.packets;

import com.gildedgames.aether.api.orbis_core.data.management.IDataCachePool;
import com.gildedgames.aether.common.network.MessageHandlerClient;
import com.gildedgames.aether.common.network.util.PacketMultipleParts;
import com.gildedgames.orbis.common.Orbis;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketSendDataCachePool extends PacketMultipleParts
{

	private IDataCachePool pool;

	private NBTTagCompound cacheData;

	public PacketSendDataCachePool()
	{

	}

	private PacketSendDataCachePool(final byte[] data)
	{
		super(data);
	}

	public PacketSendDataCachePool(final IDataCachePool pool)
	{
		this.pool = pool;
	}

	@Override
	public void read(final ByteBuf buf)
	{
		this.cacheData = ByteBufUtils.readTag(buf);
	}

	@Override
	public void write(final ByteBuf buf)
	{
		ByteBufUtils.writeTag(buf, this.pool.writeCacheData());
	}

	@Override
	public PacketMultipleParts createPart(final byte[] data)
	{
		return new PacketSendDataCachePool(data);
	}

	public static class HandlerClient extends MessageHandlerClient<PacketSendDataCachePool, IMessage>
	{
		@Override
		public IMessage onMessage(final PacketSendDataCachePool message, final EntityPlayer player)
		{
			if (player == null || player.world == null)
			{
				return null;
			}

			Orbis.getDataCache().readCacheData(message.cacheData);
			Orbis.getDataCache().flushToDisk();

			return null;
		}
	}
}
