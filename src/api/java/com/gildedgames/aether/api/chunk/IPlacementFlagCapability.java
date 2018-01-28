package com.gildedgames.aether.api.chunk;

import com.gildedgames.orbis.api.util.mc.NBT;
import net.minecraft.util.math.BlockPos;

/**
 * Stores which blocks in a chunk have been player-modified. Used primarily by
 * Skyroot Tools to decide whether or not to drop extra items.
 */
public interface IPlacementFlagCapability extends NBT
{
	/**
	 * Marks the world coordinate as player-modified.
	 * @param pos The world coordinates of the block to mark
	 */
	void markModified(BlockPos pos);

	/**
	 * Clears the player-modified flag for the world coordinate.
	 * @param pos The world coordinates of the block to clear
	 */
	void clearModified(BlockPos pos);

	/**
	 * Returns whether or not the block at world coordinates {@param pos} has
	 * been player-modified.
	 * @param pos The world coordinates of the block to check
	 * @return True if the block was player-modified, otherwise false.
	 */
	boolean isModified(BlockPos pos);
}
