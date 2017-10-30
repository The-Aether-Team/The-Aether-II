package com.gildedgames.orbis.common.data.management;

import com.gildedgames.aether.api.io.NBTFunnel;
import com.gildedgames.aether.api.orbis.management.*;
import com.gildedgames.aether.common.AetherCore;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Maps;
import net.minecraft.nbt.NBTTagCompound;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;

public class OrbisProjectCache implements IProjectCache
{

	private final Map<Integer, IDataMetadata> idToMetadata = Maps.newHashMap();

	private IProject project;

	private BiMap<Integer, IData> idToData = HashBiMap.create();

	private BiMap<Integer, String> idToLocation = HashBiMap.create();

	private int nextDataId;

	private OrbisProjectCache()
	{

	}

	public OrbisProjectCache(final IProject project)
	{
		this.project = project;
	}

	@Override
	public void setProject(final IProject project)
	{
		this.project = project;
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
		this.idToLocation.clear();
		this.idToMetadata.clear();
	}

	@Override
	public IData getData(final int dataId)
	{
		final IData data = this.idToData.get(dataId);

		if (data == null)
		{
			throw new RuntimeException("Attempted to fetch data that either hasn't been loaded yet, or doesn't exist in this project! Something's wrong!");
		}

		return data;
	}

	@Override
	public IDataMetadata getMetadata(final int dataId)
	{
		final IDataMetadata data = this.idToMetadata.get(dataId);

		if (data == null)
		{
			throw new RuntimeException("Attempted to fetch metadata that either hasn't been loaded yet, or doesn't exist in this project! Something's wrong!");
		}

		return data;
	}

	@Override
	public void removeData(final int dataId)
	{
		this.idToData.remove(dataId);
		this.idToLocation.remove(dataId);
	}

	@Override
	public void setData(final IData data, final String location)
	{
		if (data.getMetadata().getIdentifier() == null || this.idToData.containsKey(data.getMetadata().getIdentifier().getDataId()))
		{
			data.getMetadata().setIdentifier(this.createNextIdentifier());
		}

		if (this.idToLocation.inverse().containsKey(data))
		{
			return;
		}

		final int id = data.getMetadata().getIdentifier().getDataId();

		this.idToData.put(id, data);
		this.idToMetadata.put(id, data.getMetadata());

		this.setDataLocation(id, location);

		final int index = location.contains(String.valueOf(File.separatorChar)) ? location.lastIndexOf(File.separatorChar) + 1 : 0;

		data.getMetadata().setName(location.substring(index).replace("." + data.getFileExtension(), ""));

		this.project.getMetadata().setLastChanged(LocalDateTime.now());
	}

	@Override
	public void setDataLocation(final int dataId, final String location)
	{
		this.idToLocation.put(dataId, location);
	}

	@Override
	public String getDataLocation(final int dataId)
	{
		return this.idToLocation.get(dataId);
	}

	@Override
	public int getDataId(final String location)
	{
		return this.idToLocation.inverse().get(location);
	}

	@Override
	public int getNextDataId()
	{
		return this.nextDataId;
	}

	@Override
	public void setNextDataId(final int nextDataId)
	{
		this.nextDataId = nextDataId;
	}

	@Override
	public IDataIdentifier createNextIdentifier()
	{
		return new DataIdentifier(this.project.getProjectIdentifier(), this.nextDataId++);
	}

	@Override
	public void write(final NBTTagCompound tag)
	{
		final NBTFunnel funnel = AetherCore.io().createFunnel(tag);

		funnel.setIntMap("idToData", this.idToData);
		funnel.setIntToStringMap("idToLocation", this.idToLocation);

		tag.setInteger("nextDataId", this.nextDataId);
	}

	@Override
	public void read(final NBTTagCompound tag)
	{
		final NBTFunnel funnel = AetherCore.io().createFunnel(tag);

		this.idToData = HashBiMap.create(funnel.getIntMap("idToData"));
		this.idToLocation = HashBiMap.create(funnel.getIntToStringMap("idToLocation"));

		for (final Map.Entry<Integer, IData> entry : this.idToData.entrySet())
		{
			final int id = entry.getKey();
			final IData data = entry.getValue();

			this.idToMetadata.put(id, data.getMetadata());
		}

		this.nextDataId = tag.getInteger("nextDataId");
	}
}
