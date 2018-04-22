package com.gildedgames.aether.api.world;

import com.gildedgames.aether.api.world.islands.IIslandData;
import com.gildedgames.orbis_api.util.mc.NBT;

import java.util.Collection;

public interface ISector extends NBT
{
	/**
	 * Gets the list of islands in the specified region.
	 *
	 * @param x The x-coordinate of where the region starts
	 * @param y The y-coordinate of where the region starts
	 * @param z The z-coordinate of where the region starts
	 * @param width The width of the region
	 * @param height The height of the region
	 * @param length The length of the region
	 *
	 * @return A collection of {@link IIslandData} belonging to the region, empty if none.
	 */
	Collection<IIslandData> getIslandsForRegion(int x, int y, int z, int width, int height, int length);

	/**
	 * @return The sector's X coordinate in the world.
	 */
	int getX();

	/**
	 * @return The sector's Z coordinate in the world.
	 */
	int getZ();

	/**
	 * @return This sector's seed used for world generation.
	 */
	long getSeed();

	/**
	 * @return True if this sector should be saved again, such as if it's data has changed.
	 */
	boolean isDirty();

	/**
	 * Marks the sector as dirty for saving later.
	 */
	void markDirty();

	/**
	 * Marks the sector as clean. Should be called after saving.
	 */
	void markClean();

	/**
	 * Adds a belonging loaded chunk to this sector.
	 * @param chunkX The chunk's x-coordinate
	 * @param chunkZ The chunk's z-coordinate
	 */
	void addWatchingChunk(int chunkX, int chunkZ);

	/**
	 * Removes a loaded chunk belonging to this sector.
	 * @param chunkX The chunk's x-coordinate
	 * @param chunkZ The chunk's z-coordinate
	 */
	void removeWatchingChunk(int chunkX, int chunkZ);

	/**
	 * @return True if the sector has currently watching chunks
	 */
	boolean hasWatchers();
}
