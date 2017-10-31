package com.gildedgames.orbis_core.data.management;

import com.gildedgames.aether.api.util.NBT;

import javax.annotation.Nullable;
import java.util.Collection;

public interface ICache extends NBT
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
	Collection<NBT> getAllData();

	/**
	 * Clears the loaded cache to free up memory.
	 *
	 * It's recommended you don't clear the metadata cache since
	 * that might still be used by directory navigators and other
	 * interfaces.
	 */
	void clear();

	/**
	 * Fetches the cached IData reference with the provided dataId.
	 * @param dataId
	 * @return
	 */
	@Nullable
	NBT getData(int dataId);

	/**
	 * Removes the data associated with this id.
	 *
	 * This only removes it in the cache, not on disk.
	 * @param dataId The id for the data.
	 */
	void removeData(int dataId);

	/**
	 * Internal method to set data and its metadata to the cache,
	 * as well as allocating the correct project identifier.
	 *
	 * Does not actually save the data to the hard drive.
	 * @param data The data itself.
	 * @return The index the data is stored at.
	 */
	int addData(NBT data);

	String getCacheName();
}
