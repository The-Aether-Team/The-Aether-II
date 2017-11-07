package com.gildedgames.aether.api.orbis_core.data.management;

import com.gildedgames.aether.api.util.NBT;

import javax.annotation.Nullable;
import java.util.Collection;

public interface IDataCache extends NBT
{
	/**
	 * @param dataId The data id for the data.
	 * @return Whether or not the cache has a
	 * piece of data stored with that data id.
	 */
	boolean hasData(int dataId);

	/**
	 * @return All data currently cached.
	 */
	Collection<IData> getAllData();

	/**
	 * Clears the loaded cache to free up memory.
	 */
	void clear();

	/**
	 * Fetches the cached ICachedData reference with the provided dataId.
	 * @param dataId The data id associated with the data.
	 * @return The found data. If not found, returns null.
	 */
	@Nullable
	<T extends IData> T getData(int dataId);

	/**
	 * Removes the data associated with this id.
	 *
	 * This only removes it in the cache, not on disk.
	 * @param dataId The id for the data.
	 */
	void removeData(int dataId);

	/**
	 * Internal method to set data to the cache,
	 * as well as allocating the correct data id.
	 *
	 * If data already has an id assigned to it,
	 * simply sets instead of providing id.
	 *
	 * Does not actually save the data to the hard drive.
	 * @param data The data itself.
	 * @return The index the data is stored at.
	 */
	int addData(IData data);

	/**
	 * Sets the data directly to the cache's map.
	 * @param dataId The data id.
	 * @param data The data itself.
	 */
	void setData(int dataId, IData data);

	/**
	 * Used to identify the cache in a cache pool
	 * @return The cache id.
	 */
	String getCacheId();
}
