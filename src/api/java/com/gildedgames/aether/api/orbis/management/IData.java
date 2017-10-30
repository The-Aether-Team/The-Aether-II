package com.gildedgames.aether.api.orbis.management;

import com.gildedgames.aether.api.orbis.IWorldObject;
import com.gildedgames.aether.api.util.NBT;

/**
 * A data object that is stored inside of a project.
 * It has a unique identifier to signify which project
 * it is attached to and where inside of the project.
 *
 * It also stores metadata and a file extension.
 */
public interface IData extends NBT
{

	/**
	 * Called just before the data is saved to disk
	 * from a world object.
	 */
	void preSaveToDisk(IWorldObject object);

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
	IDataMetadata getMetadata();

	/**
	 * @return A clone of this data.
	 */
	IData clone();

}
