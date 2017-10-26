package com.gildedgames.aether.api.orbis.management;

import com.gildedgames.aether.api.orbis.exceptions.OrbisMissingDataException;
import com.gildedgames.aether.api.orbis.exceptions.OrbisMissingProjectException;

import javax.annotation.Nullable;
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
	 * Attempts to find a project with the provided identifier.
	 * If it cannot find a project, it will return null.
	 * @param identifier The identifier for the project.
	 * @return The found project.
	 */
	@Nullable
	IProject findProject(IProjectIdentifier identifier) throws OrbisMissingProjectException;

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
	IProjectData findData(IDataIdentifier identifier) throws OrbisMissingDataException, OrbisMissingProjectException;

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
	IProject createProject(String name, IProjectIdentifier identifier);

}
