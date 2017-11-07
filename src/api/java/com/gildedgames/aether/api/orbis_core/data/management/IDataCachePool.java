package com.gildedgames.aether.api.orbis_core.data.management;

import net.minecraft.nbt.NBTTagCompound;

import javax.annotation.Nullable;

/**
 * Simple interface used to group
 * a number of IDataCache objects together.
 * This has methods to save it to the
 * disk as well as fetching the cache
 * back through its cache id.
 */
public interface IDataCachePool
{

	/**
	 * Flushes all data to the
	 * implementation's specified
	 * disk location.
	 */
	void flushToDisk();

	/**
	 * Reads all data from the
	 * implementation's specified
	 * disk location.
	 */
	void readFromDisk();

	/**
	 * Used to register the cache
	 * to this pool. This means you can
	 * reference this pool and attempt
	 * to fetch it back with the cache's
	 * id.
	 * @param cache The cache to register.
	 */
	void registerCache(IDataCache cache);

	/**
	 * Attempts to find a cache stored within
	 * this pool. If it cannot find one with the
	 * provided cache id, it will return null.
	 * @param cacheID The cache id.
	 * @return The found cache. Null if nothing
	 * is found.
	 */
	@Nullable
	<T extends IDataCache> T findCache(String cacheID);

	/**
	 * Writes the data inside this cache to a
	 * NBTTagCompound. Used to send over packets.
	 * @return The cache's data.
	 */
	NBTTagCompound writeCacheData();

	/**
	 * Reads the NBTTagCompound for its cache
	 * data, should be created from writeCacheData().
	 * @param tag The cache data being read.
	 */
	void readCacheData(NBTTagCompound tag);

}
