package com.gildedgames.aether.api.orbis.management;

import com.gildedgames.aether.api.util.IText;
import com.gildedgames.aether.api.util.NBT;

import java.util.List;

public interface IProjectMetadata extends NBT
{

	/**
	 * Used for displaying this metadata to users in the GUI.
	 * @return The text that will be displayed.
	 */
	List<IText> getMetadataDisplay();

	/**
	 * If the project manager cannot find the required dependencies,
	 * this file cannot be used.
	 * @return The data files that this data requires to function.
	 */
	List<IDataIdentifier> getDependencies();

	/**
	 * @return An identifier that signals which project this data is attached to,
	 * as well as its internal data id.
	 */
	IDataIdentifier getIdentifier();

	/**
	 * Sets an identifier that signals which project this data is attached to,
	 * as well as its internal data id.
	 * @param projectIdentifier The project identifier
	 */
	void setIdentifier(IDataIdentifier projectIdentifier);

}
