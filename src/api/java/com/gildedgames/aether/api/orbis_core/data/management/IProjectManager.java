package com.gildedgames.aether.api.orbis_core.data.management;

import com.gildedgames.aether.api.orbis_core.api.exceptions.OrbisMissingDataException;
import com.gildedgames.aether.api.orbis_core.api.exceptions.OrbisMissingProjectException;

import javax.annotation.Nullable;
import java.io.File;
import java.util.Collection;

/**
 * A management object for managing the projects within a specific directory.
 * Projects will be loaded into memory, but their data should only be loaded
 * and cached if all mod dependencies are verified. If not, the project will
 * persist for directory viewing but cannot be opened.
 */
public interface IProjectManager
{

	/**
	 * @return The location of all the projects this is managing.
	 */
	File getLocation();

	/**
	 * Called on server stop. Should be implemented to save
	 * all project data.
	 */
	void flushProjects();

	/**
	 * Should check if there are any projects that have
	 * been deleted or changed on the disk, and update
	 * the cache to reflect these changes.
	 */
	void refreshCache();

	/**
	 * Scans the directory this manager is attached to for any
	 * projects. Project structure should be a single folder with
	 * a hidden .project file inside which contains all the necessary
	 * metadata to handle the project.
	 *
	 * Once scanned, it should load all the projects into memory.
	 */
	void scanAndCacheProjects();

	/**
	 * @return The projects currently loaded into memory.
	 */
	Collection<IProject> getCachedProjects();

	/**
	 * Attempts to find a project with the provided folder name.
	 * If it cannot find a project, it will return null.
	 * @param folderName The name of the folder that the project is in.
	 * @return The found project.
	 */
	@Nullable
	<T extends IProject> T findProject(String folderName) throws OrbisMissingProjectException;

	/**
	 * Attempts to find a project with the provided identifier.
	 * If it cannot find a project, it will return null.
	 * @param identifier The identifier for the project.
	 * @return The found project.
	 */
	@Nullable
	<T extends IProject> T findProject(IProjectIdentifier identifier) throws OrbisMissingProjectException;

	/**
	 * Attempts to find a data with the provided location.
	 *
	 * If the project cannot be found or the data file itself,
	 * this will return null.
	 * @param project The project that the data belongs to.
	 * @param location The location of the data.
	 * @return The found project.
	 */
	@Nullable
	<T extends IData> T findData(IProject project, File location) throws OrbisMissingDataException, OrbisMissingProjectException;

	/**
	 * Same as findProject(), but instead attempts to find
	 * a data file. First it should attempt to find the project.
	 * Then, the data file inside of that project.
	 *
	 * If the project cannot be found or the data file itself,
	 * this will return null.
	 * @param identifier The identifier for the data.
	 * @return The found data.
	 */
	@Nullable
	<T extends IData> T findData(IDataIdentifier identifier) throws OrbisMissingDataException, OrbisMissingProjectException;

	/**
	 * Same as findData(), but instead attempts to find
	 * a metadata file. First it should attempt to find the project.
	 * Then, the metadata file cached inside of that project.
	 *
	 * If the project cannot be found or the metadata itself,
	 * this will return null.
	 * @param identifier The identifier for the data.
	 * @return The found data.
	 */
	@Nullable
	<T extends IDataMetadata> T findMetadata(IDataIdentifier identifier) throws OrbisMissingDataException, OrbisMissingProjectException;

	/**
	 * Creates a new project with the provided name and identfier.
	 * The name simply represents the folder name, not the project id.
	 *
	 * This will create the folder and .project file inside of the directory
	 * that this manager is attached to.
	 * @param name Name of the folder and project.
	 * @param identifier Identifier for the project.
	 * @return Newly created project.
	 */
	<T extends IProject> T createAndSaveProject(String name, IProjectIdentifier identifier);

	/**
	 * Creates a project folder on disk and saves the project object.
	 * The name represents the folder name.
	 *
	 * If the project folder already exists, it will return before saving.
	 *
	 * This will create the folder and .project file inside of the directory
	 * that this manager is attached to.
	 *
	 * If the project DOES already exist, it will move that project
	 * to the requested folder (name).
	 * @param project The project that will be saved.
	 *
	 * @return If there is an existing project, it will return it.
	 * If not, it returns null.
	 */
	@Nullable
	<T extends IProject> T saveProjectIfDoesntExist(String name, IProject project);

	/**
	 * @param name The name of the folder that the project is in.
	 * @return Whether or not that folder name is taken already by another project.
	 */
	boolean projectNameExists(String name);

	/**
	 * @param id The id for the project.
	 * @return Whether or not the project exists in this manager.
	 */
	boolean projectExists(IProjectIdentifier id);

}
