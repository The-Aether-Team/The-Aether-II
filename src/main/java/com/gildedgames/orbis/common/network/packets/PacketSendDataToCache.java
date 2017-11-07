package com.gildedgames.orbis.common.network.packets;

import com.gildedgames.aether.api.io.NBTFunnel;
import com.gildedgames.aether.api.orbis_core.OrbisCore;
import com.gildedgames.aether.api.orbis_core.data.management.IData;
import com.gildedgames.aether.api.orbis_core.data.management.IDataCache;
import com.gildedgames.aether.common.network.MessageHandlerClient;
import com.gildedgames.aether.common.network.util.PacketMultipleParts;
import com.gildedgames.orbis.common.Orbis;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketSendDataToCache extends PacketMultipleParts
{

	private String cacheId;

	private IData data;

	public PacketSendDataToCache()
	{

	}

	private PacketSendDataToCache(final byte[] data)
	{
		super(data);
	}

	public PacketSendDataToCache(final String cacheId, final IData data)
	{
		this.cacheId = cacheId;
		this.data = data;
	}

	@Override
	public void read(final ByteBuf buf)
	{
		final NBTTagCompound tag = ByteBufUtils.readTag(buf);
		final NBTFunnel funnel = OrbisCore.io().createFunnel(tag);

		this.cacheId = tag.getString("cacheId");
		this.data = funnel.get("data");
	}

	@Override
	public void write(final ByteBuf buf)
	{
		final NBTTagCompound tag = new NBTTagCompound();
		final NBTFunnel funnel = OrbisCore.io().createFunnel(tag);

		tag.setString("cacheId", this.cacheId);
		funnel.set("data", this.data);

		ByteBufUtils.writeTag(buf, tag);
	}

	@Override
	public PacketMultipleParts createPart(final byte[] data)
	{
		return new PacketSendDataToCache(data);
	}

	public static class HandlerClient extends MessageHandlerClient<PacketSendDataToCache, IMessage>
	{
		@Override
		public IMessage onMessage(final PacketSendDataToCache message, final EntityPlayer player)
		{
			if (player == null || player.world == null)
			{
				return null;
			}

			final IDataCache cache = Orbis.getDataCache().findCache(message.cacheId);

			cache.setData(message.data.getMetadata().getIdentifier().getDataId(), message.data);

			return null;
		}
	}
}
