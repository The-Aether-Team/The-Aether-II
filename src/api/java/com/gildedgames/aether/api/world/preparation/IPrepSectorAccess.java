package com.gildedgames.aether.api.world.preparation;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.Collection;
import java.util.Optional;

public interface IPrepSectorAccess
{
	/**
	 * Fetches an {@link IPrepSector} from the world that belongs to the chunk at the specified coordinates.
	 *
	 * This will only return currently loaded sectors. Use {@link IPrepSectorAccess#provideSectorForChunk(int, int, boolean)}
	 * if you want to fetch offline sectors or generate new ones.
	 *
	 * @param sectorX The x-coordinate of the sector
	 * @param sectorZ The z-coordinate of the sector
	 *
	 * @return The {@link IPrepSector}, if it exists and is loaded in the world, otherwise empty
	 */
	Optional<IPrepSector> getLoadedSector(int sectorX, int sectorZ);

	/**
	 * Fetches an {@link IPrepSector} from the world at the specified sector coordinates.
	 *
	 * This will only return currently loaded sectors. Use {@link IPrepSectorAccess#provideSectorForChunk(int, int, boolean)}
	 * if you want to fetch offline sectors or generate new ones.
	 *
	 * @param sectorX The x-coordinate of the chunk
	 * @param sectorZ The z-coordinate of the chunk
	 *
	 * @return The {@link IPrepSector}, if it exists and is loaded in the world, otherwise empty
	 */
	Optional<IPrepSector> getLoadedSectorForChunk(int sectorX, int sectorZ);

	/**
	 * Fetches an {@link IPrepSector} from the world that belongs to the specified chunk.
	 *
	 * If the sector is not already in memory, it will be loaded from disk synchronously and
	 * loaded. If the sector has not been created yet, the sector will be generated and marked as
	 * dirty for saving later.
	 *
	 * @param sectorX The x-coordinate of the chunk
	 * @param sectorZ The y-coordinate of the chunk
	 * @param background True if the sector should be generated on another thread, or False if it should be
	 *                      immediately completed
	 *
	 * @return The {@link IPrepSector} for the chunk
	 */
	ListenableFuture<IPrepSector> provideSector(int sectorX, int sectorZ, boolean background);

	/**
	 * Fetches an {@link IPrepSector} from the world that belongs to the specified chunk.
	 *
	 * If the sector is not already in memory, it will be loaded from disk synchronously and
	 * loaded. If the sector has not been created yet, the sector will be generated and marked as
	 * dirty for saving later.
	 *
	 * @param chunkX The x-coordinate of the chunk
	 * @param chunkZ The y-coordinate of the chunk
	 * @param background True if the sector should be generated on another thread, or False if it should be
	 *                      immediately completed
	 *
	 * @return The {@link IPrepSector} for the chunk
	 */
	ListenableFuture<IPrepSector> provideSectorForChunk(int chunkX, int chunkZ, boolean background);

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
	 * Called to retain a sector in memory. If no entities are watching the sector, this method will do nothing. If
	 * a sector no longer has any watching entities when it is saved, it will be unloaded.
	 */
	void retainSector(IPrepSector sector);

	Collection<IPrepSector> getLoadedSectors();

	void update();
}
