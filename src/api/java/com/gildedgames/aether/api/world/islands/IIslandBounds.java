package com.gildedgames.aether.api.world.islands;

import net.minecraft.nbt.NBTBase;

public interface IIslandBounds
{

	/**
	 * Tests if this bounding box intersects the specified bounding box
	 *
	 * @param minX The minimum X value of the box
	 * @param minY The minimum Y value of the boz
	 * @param minZ The minimum Z value of the box
	 * @param maxX The maximum X value of the box
	 * @param maxY The maximum Y value of the box
	 * @param maxZ The maximum Z value of the box
	 * @return True if the boxes intersect
	 */
	boolean intersects(int minX, int minY, int minZ, int maxX, int maxY, int maxZ);

	/**
	 * Tests if this bounding box contains the specified bounding box
	 *
	 * @param minX The minimum X value of the box
	 * @param minY The minimum Y value of the boz
	 * @param minZ The minimum Z value of the box
	 * @param maxX The maximum X value of the box
	 * @param maxY The maximum Y value of the box
	 * @param maxZ The maximum Z value of the box
	 * @return True if the box is contained
	 */
	boolean contains(int minX, int minY, int minZ, int maxX, int maxY, int maxZ);

	/**
	 * Tests if this bounding box contains the point.
	 *
	 * @param x The x-coordinate to test
	 * @param y The y-coordinate to test
	 * @param z The z-coordinate to test
	 * @return True if this bounding box contains the point
	 */
	boolean contains(int x, int y, int z);

	/**
	 * @return The x-coordinate this bounding box begins at in sector coordinates
	 */
	int getMinX();

	/**
	 * @return The y-coordinate this bounding box begins at in sector coordinates
	 */
	int getMinY();

	/**
	 * @return The z-coordinate this bounding box begins at in sector coordinates
	 */
	int getMinZ();

	/**
	 * @return The width of this bounding box in sector coordinates
	 */
	int getMaxX();

	/**
	 * @return The height of this bounding box in sector coordinates
	 */
	int getMaxY();

	/**
	 * @return The length of this bounding box in sector coordinates
	 */
	int getMaxZ();

	/**
	 * @return The width of this bounding box
	 */
	int getWidth();

	/**
	 * @return The height of this bounding box
	 */
	int getHeight();

	/**
	 * @return The length of this bounding box
	 */
	int getLength();

	/**
	 * @return The center x-coordinate of this bounding box
	 */
	double getCenterX();

	/**
	 * @return The center y-coordinate of this bounding box
	 */
	double getCenterY();

	/**
	 * @return The center z-coordinate of this bounding box
	 */
	double getCenterZ();

	/**
	 * Serializes this bounding box to NBT.
	 *
	 * @return A {@link NBTBase} representing this bounding box
	 */
	NBTBase serialize();
}
