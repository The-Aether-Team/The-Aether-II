package com.gildedgames.aether.api.world;

import java.util.Optional;

/**
 * Server-side access interface for a world's sectors.
 */
public interface ISectorAccess
{
	/**
	 * Fetches an {@link ISector} from the world that belongs to the specified chunk.
	 *
	 * This will only return currently loaded sectors. Use {@link ISectorAccess#provideSector(int, int)}
	 * if you want to fetch offline sectors or generate new ones.
	 *
	 * @param chunkX The x-coordinate of the chunk
	 * @param chunkZ The y-coordinate of the chunk
	 *
	 * @return The {@link ISector}, if it exists in the world, otherwise empty
	 */
	Optional<ISector> getLoadedSector(int chunkX, int chunkZ);

	/**
	 * Fetches an {@link ISector} from the world that belongs to the specified chunk.
	 *
	 * If the sector is not already in memory, it will be loaded from disk synchronously and
	 * loaded. If the sector has not been created yet, the sector will be generated and marked as
	 * dirty for saving later.
	 *
	 * @param chunkX The x-coordinate of the chunk
	 * @param chunkZ The y-coordinate of the chunk
	 *
	 * @return The {@link ISector} for the chunk
	 */
	ISector provideSector(int chunkX, int chunkZ);

	/**
	 * Called when a chunk possibly containing a sector is loaded for the world.
	 *
	 * If the sector for the chunk is not already loaded, it will load the sector from disk
	 * synchronously. This will not generate new sectors.
	 *
	 * @param chunkX The x-coordinate of the chunk
	 * @param chunkZ The y-coordinate of the chunk
	 */
	void onChunkLoaded(int chunkX, int chunkZ);

	/**
	 * Called when a chunk is unloaded from the world, hinting to the access to
	 * unload the sector.
	 *
	 * @param chunkX The x-coordinate of the sector
	 * @param chunkZ The y-coordinate of the sector
	 */
	void onChunkUnloaded(int chunkX, int chunkZ);

	/**
	 * Flushes dirty sectors to disk. Should be called on world save.
	 */
	void flush();
}
