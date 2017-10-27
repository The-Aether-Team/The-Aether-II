package com.gildedgames.aether.api.orbis.management;

import com.gildedgames.aether.api.util.IText;
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
	 * Used for displaying this project's metadata to users in the GUI.
	 * @return
	 */
	List<IText> getMetadataDisplay();

	/**
	 * Fetches the cached IProjectData reference with the provided dataId.
	 * @param dataId
	 * @return
	 */
	IProjectData getCachedData(int dataId);

	/**
	 * Fetched the cached IProjectMetadata reference that is attached to the
	 * IProjectData object found via the provided dataId.
	 * @param dataId
	 * @return
	 */
	IProjectMetadata getCachedMetadata(int dataId);

	/**
	 * Saves the data to the project and provides it with a data id internal
	 * to the project. This is where the project should get the provided data's
	 * metadata and call IProjectMetadata.setDataId()
	 * @param data
	 */
	void saveNewData(IProjectData data, String location);

	/**
	 * Used to set an existing data's file path inside of the project. This shouldn't
	 * be used to ACTUALLY move the file, but instead simply handle the mapped data
	 * inside of the project so it knows where each data file is stored.
	 * @param dataId
	 * @param location
	 */
	void setDataLocation(int dataId, String location);

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
	 * Clears the loaded cache to free up memory. Should be called
	 * when the project is not in use.
	 *
	 * It's recommended you don't clear the metadata cache since
	 * that might still be used by directory navigators and other
	 * interfaces.
	 */
	void clearDataCache();

	/**
	 * @return Whether or not the dependencies of this project (such as mods) are met.
	 */
	boolean areModDependenciesMet();

}
