package com.gildedgames.aether.api.orbis_core.data.management;

import com.gildedgames.aether.api.util.NBT;

import javax.annotation.Nullable;

/**
 * An object used to identify a unique data object
 * inside of a project.
 */
public interface IDataIdentifier extends NBT
{

	/**
	 * Used to identify where the data
	 * is stored inside of the project.
	 * @return The data id internal to the project.
	 */
	int getDataId();

	/**
	 * Might not have a project identifier if not attached
	 * to a project.
	 * @return The identifier of the project that this data is attached to.
	 */
	@Nullable
	IProjectIdentifier getProjectIdentifier();

}
