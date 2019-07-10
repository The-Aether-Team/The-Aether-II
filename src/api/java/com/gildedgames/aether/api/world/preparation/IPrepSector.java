package com.gildedgames.aether.api.world.preparation;

public interface IPrepSector
{
	/**
	 * @return This sector's data.
	 */
	IPrepSectorData getData();

	/**
	 * Adds a belonging loaded chunk to this sector.
	 * @param chunkX The chunk's x-coordinate
	 * @param chunkZ The chunk's z-coordinate
	 *
	 * @return True if the chunk wasn't watching before
	 */
	boolean addWatchingChunk(int chunkX, int chunkZ);

	/**
	 * Removes a loaded chunk belonging to this sector.
	 * @param chunkX The chunk's x-coordinate
	 * @param chunkZ The chunk's z-coordinate
	 *
	 * @return True if the chunk was watching
	 */
	boolean removeWatchingChunk(int chunkX, int chunkZ);

	void addWatchingPlayer(int entityId);

	void removeWatchingPlayer(int entityId);

	/**
	 * @return True if the sector has currently watching chunks or watching players
	 */
	boolean hasWatchers();

	int getDormantTicks();

	void tick();
}
