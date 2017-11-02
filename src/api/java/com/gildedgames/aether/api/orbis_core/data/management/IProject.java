package com.gildedgames.aether.api.orbis_core.data.management;

import com.gildedgames.aether.api.util.NBT;

import javax.annotation.Nullable;
import java.io.File;
import java.net.URI;

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
	 * Orbis client will always favour using the file location
	 * instead of the URI. getJarLocation() is only used for loading
	 * projects from a mod jar.
	 *
	 * @return If the URI location is a file, returns a file object.
	 * If not, returns null.
	 */
	File getLocationAsFile();

	/**
	 * Sets the directory/location of this project.
	 * @param location The location of this project.
	 */
	void setLocationAsFile(File location);

	/**
	 * @return The location of this project.
	 */
	@Nullable
	URI getJarLocation();

	/**
	 * Sets the URI location of this project.
	 *
	 * Should use this for streaming from a mod jar file.
	 * Otherwise, use setLocationAsFile()
	 * @param location The location of this project.
	 */
	void setJarLocation(URI location);

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
	 * Writes the provided data to disk.
	 *
	 * This DOES NOT set the data to the project's
	 * cache. It simply provides a universal method
	 * for writing to the project's directory.
	 *
	 * @param data The data we're writing.
	 * @param file The file the data will be written to.
	 */
	void writeData(IData data, File file);

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
