package com.gildedgames.aether.api.world.preparation;

import com.gildedgames.orbis.lib.util.mc.NBT;

/**
 * A sector of variable size which holds data for future generation.
 * These are used to prepare the terrain data in memory, then use it to
 * coordinate structures and other data ahead of time.
 *
 * Sectors do not hold any chunks, but rather just the data relevant to that sector
 * (such as Blueprints).
 */
public interface IPrepSectorData extends NBT
{
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

	int getSectorX();

	int getSectorY();

	long getSeed();
}