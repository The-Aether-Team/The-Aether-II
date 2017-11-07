package com.gildedgames.aether.api.orbis_core.data.management.impl;

import com.gildedgames.aether.api.io.NBTFunnel;
import com.gildedgames.aether.api.orbis_core.OrbisCore;
import com.gildedgames.aether.api.orbis_core.data.management.IData;
import com.gildedgames.aether.api.orbis_core.data.management.IDataCache;
import com.google.common.collect.Maps;
import net.minecraft.nbt.NBTTagCompound;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Map;

public class DataCache implements IDataCache
{
	private String cacheId;

	private Map<Integer, IData> idToData = Maps.newHashMap();

	private int nextId;

	private DataCache()
	{
		
	}

	public DataCache(final String cacheId)
	{
		this.cacheId = cacheId;
	}

	@Override
	public void write(final NBTTagCompound tag)
	{
		final NBTFunnel funnel = OrbisCore.io().createFunnel(tag);

		tag.setString("cacheId", this.cacheId);
		tag.setInteger("nextId", this.nextId);

		funnel.setIntMap("idToData", this.idToData);
	}

	@Override
	public void read(final NBTTagCompound tag)
	{
		final NBTFunnel funnel = OrbisCore.io().createFunnel(tag);

		this.cacheId = tag.getString("cacheId");
		this.nextId = tag.getInteger("nextId");

		this.idToData = funnel.getIntMap("idToData");
	}

	@Override
	public boolean hasData(final int dataId)
	{
		return this.idToData.containsKey(dataId);
	}

	@Override
	public Collection<IData> getAllData()
	{
		return this.idToData.values();
	}

	@Override
	public void clear()
	{
		this.idToData.clear();
	}

	@Nullable
	@Override
	public <T extends IData> T getData(final int dataId)
	{
		return (T) this.idToData.get(dataId);
	}

	@Override
	public void removeData(final int dataId)
	{
		this.idToData.remove(dataId);
	}

	@Override
	public int addData(final IData data)
	{
		final int id = this.nextId++;

		if (data.getMetadata().getIdentifier() == null)
		{
			data.getMetadata().setIdentifier(new DataIdentifier(null, id));
		}

		this.setData(id, data);

		return id;
	}

	@Override
	public void setData(final int dataId, final IData data)
	{
		this.idToData.put(dataId, data);
	}

	@Override
	public String getCacheId()
	{
		return this.cacheId;
	}
}
