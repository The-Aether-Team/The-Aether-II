package com.gildedgames.aether.api.orbis_core.data.management;

import com.gildedgames.aether.api.util.NBT;

import java.io.File;
import java.util.List;

/**
 * A project will be used to store data files such as Blueprints, Groups,
 * Filters, etc. The advantage of a project structure is other Blueprints that
 * reference external data can simply save a "data id" and then fetch the cached
 * reference from its project. Right now projects will not support referencing other
 * projects due to the difficulties of dependency resolution, but might come in the future.
 */
public interface IProject extends NBT
{

	/**
	 * @return The location of this project.
	 */
	File getLocation();

	/**
	 * Sets the directory/location of this project.
	 * @param file The location of this project.
	 */
	void setLocation(File file);

	/**
	 * These will be used to determine which files to ignore or load when
	 * loading in data from within the project.
	 * @param acceptedFileExtensions The file extensions accepted by this project.
	 */
	void setAcceptedFileExtensions(List<String> acceptedFileExtensions);

	/**
	 * Adds a listener to this project.
	 * @param listener
	 */
	void addListener(IProjectListener listener);

	/**
	 * Removes a listener from this project.
	 * @param listener
	 * @return
	 */
	boolean removeListener(IProjectListener listener);

	/**
	 * This unique identifier to distinguish it between other projects.
	 * Includes a name and authors.
	 * @return
	 */
	IProjectIdentifier getProjectIdentifier();

	/**
	 * Used for displaying information about this project to the user.
	 * @return The project's metadata.
	 */
	IProjectMetadata getMetadata();

	/**
	 * @return The cache for this project, if it's currently loaded.
	 * Returns null if the cache is not loaded, in which case you should
	 * call loadAndCacheData()
	 */
	IProjectCache getCache();

	/**
	 * Sets the cache internal to the project. Used when sending
	 * cache over packets to a player then setting it to a project
	 * on the client.
	 */
	void setCache(IProjectCache cache);

	/**
	 * Loads the data inside of the project and also fetches its
	 * own name. The name of the project should be decided based on
	 * the directory name - this is unrelated to the Project Identifier
	 * attached to this project, which should be used to actually distinguish
	 * it from other projects. The name is simply visual.
	 *
	 * Should scan through the project directory and assemble all data links
	 * in case data files have been moved around.
	 */
	void loadAndCacheData();

	/**
	 * @return Whether or not the dependencies of this project (such as mods) are met.
	 */
	boolean areModDependenciesMet();

}
