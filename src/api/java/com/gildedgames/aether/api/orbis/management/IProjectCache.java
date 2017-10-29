package com.gildedgames.aether.api.orbis.management;

import com.gildedgames.aether.api.util.NBT;

import java.util.Collection;

public interface IProjectCache extends NBT
{

	/**
	 * @param project The project that is using this cache.
	 */
	void setProject(IProject project);

	/**
	 * @return All data currently cached.
	 */
	Collection<IData> getAllData();

	/**
	 * Clears the loaded cache to free up memory. Should be called
	 * when the project is not in use.
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
	IData getData(int dataId);

	/**
	 * Fetched the cached IDataMetadata reference that is attached to the
	 * IData object found via the provided dataId.
	 * @param dataId
	 * @return
	 */
	IDataMetadata getMetadata(int dataId);

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
	 * The location HAS TO BE RELATIVE TO THE PROJECT FOLDER.
	 *
	 * Does not actually save the data to the hard drive.
	 * @param data The data itself.
	 * @param location The location for the data within the project.
	 */
	void setData(IData data, String location);

	/**
	 * Used to set an existing data's file path inside of the project. This shouldn't
	 * be used to ACTUALLY move the file, but instead simply handle the mapped data
	 * inside of the project so it knows where each data file is stored.
	 * @param dataId
	 * @param location
	 */
	void setDataLocation(int dataId, String location);

	/**
	 * A way to get the location of data from its id alone.
	 * @param dataId The data id.
	 * @return The location of that data.
	 */
	String getDataLocation(int dataId);

	/**
	 * A way to get the data id from its location.
	 * @param location The location of the data.
	 * @return The data id.
	 */
	int getDataId(String location);

	/**
	 * @return The next data id used by the cache.
	 */
	int getNextDataId();

	/**
	 * Should ONLY BE USED by the project that this cache belongs to.
	 * @param nextDataId The next data id that will be used by this cache.
	 */
	void setNextDataId(int nextDataId);

	/**
	 * @return The next available data identifier.
	 */
	IDataIdentifier createNextIdentifier();

}
