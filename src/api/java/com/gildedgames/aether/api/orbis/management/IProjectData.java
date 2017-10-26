package com.gildedgames.aether.api.orbis.management;

import com.gildedgames.aether.api.util.NBT;

/**
 * A data object that is stored inside of a project.
 * It has a unique identifier to signify which project
 * it is attached to and where inside of the project.
 *
 * It also stores metadata and a file extension.
 */
public interface IProjectData extends NBT
{

	/**
	 * The file extension for this data.
	 * @return
	 */
	String getFileExtension();

	/**
	 * The metadata for this project data. Used to display
	 * information about the file to the user in a GUI, plus
	 * also used to identify the project its associated with and
	 * its internal id.
	 * @return
	 */
	IProjectMetadata getMetadata();

}
