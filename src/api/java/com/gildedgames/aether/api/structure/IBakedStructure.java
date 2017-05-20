package com.gildedgames.aether.api.structure;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;

/**
 * An optimized structure format that stores blocks in a flattened array, allowing for custom
 * generation hints and special behaviors.
 */
public interface IBakedStructure
{
	/**
	 * Gets the block at the specified position.
	 * @param pos The position
	 * @return An {@link IBlockState} of the block
	 */
	IBlockState getBlock(BlockPos pos);

	/**
	 * Returns the dimensions of the structure.
	 * @return An {@link BlockPos} representing this structure's dimensions
	 */
	BlockPos getDimensions();

	/**
	 * Returns the name of the structure.
	 * @return The name of this structure as a {@link String}. A String with a length of zero implies no name.
	 */
	String getName();

	/**
	 * Writes the structure to the {@link NBTTagCompound}.
	 * @param compound The {@link NBTTagCompound} to write to
	 */
	void read(NBTTagCompound compound);

	/**
	 * Reads the structure from a {@link NBTTagCompound}.
	 * @param compound The {@link NBTTagCompound} to read from
	 */
	void write(NBTTagCompound compound);
}
