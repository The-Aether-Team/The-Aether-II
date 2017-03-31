package com.gildedgames.aether.api.util;

import net.minecraft.nbt.NBTTagCompound;

/**
 * Helper interface for NBT-serializable objects.
 */
public interface NBT
{
	/**
	 * Writes this object's state to a {@link NBTTagCompound}
	 * @param tag The tag to write to
	 */
	void write(NBTTagCompound tag);

	/**
	 * Reads this object's state from a {@link NBTTagCompound}
	 * @param tag The tag to write to
	 */
	void read(NBTTagCompound tag);
}
