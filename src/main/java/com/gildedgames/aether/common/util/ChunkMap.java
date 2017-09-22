package com.gildedgames.aether.common.util;

import gnu.trove.map.TLongObjectMap;
import gnu.trove.map.hash.TLongObjectHashMap;
import net.minecraft.util.math.ChunkPos;

import java.util.Collection;

/**
 * A fast map for storing entries on a 2D integer coordinate system.
 *
 * @param <T> The type that will be stored in this map
 */
public class ChunkMap<T>
{
	private TLongObjectMap<T> map = new TLongObjectHashMap<>();

	/**
	 * Returns whether or not this map contains the value at the coordinates.
	 *
	 * @param chunkX The x-coordinate to check
	 * @param chunkZ The z-coordinate to check
	 * @return True if the map contains an entry, otherwise false
	 */
	public boolean containsKey(int chunkX, int chunkZ)
	{
		return this.map.containsKey(ChunkPos.asLong(chunkX, chunkZ));
	}

	/**
	 * Gets an entry from the map at the specified coordinates.
	 *
	 * @param chunkX The entry's x-coordinate
	 * @param chunkZ The entry's z-coordinate
	 * @return The value in the map at the specified coordinates, null if none
	 */
	public T get(int chunkX, int chunkZ)
	{
		return this.map.get(ChunkPos.asLong(chunkX, chunkZ));
	}

	/**
	 * Puts an entry into the map, replacing the existing value if it exists. If
	 * the value is null, the entry will be removed.
	 *
	 * @param chunkX The entry's x-coordinate
	 * @param chunkZ The entry's z-coordinate
	 * @param value The value to put, null to remove the entry from the map
	 * @return The previous value at the coordinates specified, null if none
	 */
	public T put(int chunkX, int chunkZ, T value)
	{
		if (value == null)
		{
			return this.remove(chunkX, chunkZ);
		}

		long key = ChunkPos.asLong(chunkX, chunkZ);

		return this.map.put(key, value);
	}

	/**
	 * Removes an entry from the map.
	 *
	 * @param chunkX The entry's x-coordinate
	 * @param chunkZ The entry's z-coordinate
	 * @return The entry removed from the map, null if none.
	 */
	public T remove(int chunkX, int chunkZ)
	{
		return this.map.remove(ChunkPos.asLong(chunkX, chunkZ));
	}

	/**
	 * @return The number of entries in the map
	 */
	public int size()
	{
		return this.map.size();
	}

	/**
	 * @return True if this map is empty, otherwise true
	 */
	public boolean isEmpty()
	{
		return this.map.size() == 0;
	}

	/**
	 * Returns the {@link Collection<T>} of values in this map.
	 * @return A {@link Collection<T>} of values in this map, empty if none
	 */
	@Deprecated
	public Collection<T> getValues()
	{
		return this.map.valueCollection();
	}

	/**
	 * Clears the map.
	 */
	public void clear()
	{
		this.map.clear();
	}
}
