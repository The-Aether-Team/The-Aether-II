package com.gildedgames.orbis.common.data.management;

import com.gildedgames.aether.api.io.NBTFunnel;
import com.gildedgames.aether.api.orbis.management.IData;
import com.gildedgames.aether.api.util.NBT;
import com.gildedgames.aether.api.util.NBTHelper;
import com.gildedgames.aether.common.AetherCore;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import net.minecraft.nbt.NBTTagCompound;

import javax.annotation.Nullable;
import java.io.File;
import java.util.Collection;

/**
 * Class that stores IData. Clients can dynamically access this repository.
 * Note: This only works with static IData. If the IData object is changed,
 * it is not resynced in this cache.
 */
public class OrbisCache implements ICache
{
	private BiMap<Integer, NBT> idToData = HashBiMap.create();

	private int nextDataId = 0;

	private boolean isServer;

	private File directory;

	private String extension, cacheName;

	public OrbisCache(boolean isServer, File directory, String extension, String cacheName)
	{
		this.isServer = isServer;
		this.directory = directory;
		this.extension = extension;
		this.cacheName = cacheName;
		directory.mkdirs();
	}

	@Override
	public boolean hasData(int dataId)
	{
		return this.idToData.containsKey(dataId);
	}

	@Override
	public Collection<NBT> getAllData()
	{
		return this.idToData.values();
	}

	@Override
	public void clear()
	{
		this.idToData.clear();
	}

	private File getFile(int dataId)
	{
		return new File(this.directory.getPath() + File.separator + dataId + "." + this.extension);
	}

	@Nullable
	@Override
	public NBT getData(int dataId)
	{
		NBT data = this.idToData.get(dataId);
		if (data != null)
		{
			return data;
		}
		if (this.isServer)
		{
			File f = this.getFile(dataId);
			if (f.isFile())
			{
				NBTTagCompound tag = NBTHelper.readNBTFromFile(f);
				NBTFunnel funnel = AetherCore.io().createFunnel(tag);
				data = funnel.get("data");
				int id = tag.getInteger("id");
				this.idToData.put(id, data);
				return data;
			}
		}
		return null;
	}

	@Override
	public void removeData(int dataId)
	{
		this.idToData.remove(dataId);
	}

	@Override
	public int addData(NBT data)
	{
		if (!this.isServer)
		{
			// Don't do this?? Or sync??? Yeah probably that.
			return -1;
		}
		this.idToData.put(this.nextDataId, data);

		NBTTagCompound tag = new NBTTagCompound();
		NBTFunnel funnel = AetherCore.io().createFunnel(tag);

		funnel.set("data", data);
		tag.setInteger("id", this.nextDataId);

		NBTHelper.writeNBTToFile(tag, this.getFile(this.nextDataId));

		this.nextDataId++;
		return this.nextDataId - 1;
	}

	@Override
	public String getCacheName()
	{
		return this.cacheName;
	}

	@Override
	public void write(NBTTagCompound tag)
	{
		tag.setInteger("nextId", this.nextDataId);
	}

	@Override
	public void read(NBTTagCompound tag)
	{
		this.nextDataId = tag.getInteger("nextId");
	}
}
