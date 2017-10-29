package com.gildedgames.aether.api.orbis.management;

import java.util.List;

public interface IDataMetadata extends IMetadata
{

	/**
	 * @return The name of the data.
	 */
	String getName();

	/**
	 * Sets the name of the data.
	 * @param name The name of the data.
	 */
	void setName(String name);

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
