package com.gildedgames.aether.api.structure;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public interface IStructureLoader
{
	/**
	 * Loads a structure from the specified resource path. For example, a {@link ResourceLocation}
	 * of "mymod:awesome" should be located in "/assets/mymod/structures/awesome.aestruct".
	 *
	 * @param path The {@link ResourceLocation} of your structure
	 * @param allowCaching Whether or not to load a cached version
	 *
	 * @return A {@link IBakedStructure}, null if an error occurred.
	 */
	IBakedStructure getStructure(ResourceLocation path, boolean allowCaching);

	/**
	 * Saves a structure to disk in the same folder as the world it belongs to.
	 * @param world The world the structure belongs to
	 * @param structure The structure to save
	 */
	void saveStructure(World world, IBakedStructure structure);
}
