package com.gildedgames.orbis_core.data.management;

import com.gildedgames.aether.api.util.NBT;

/**
 * An object used to identify a unique project.
 */
public interface IProjectIdentifier extends NBT
{

	/**
	 * Decided by authors.
	 * @return A unique id that represents the project.
	 */
	String getProjectId();

	/**
	 * @return The username of the player who first created this project.
	 */
	String getOriginalCreator();

}
