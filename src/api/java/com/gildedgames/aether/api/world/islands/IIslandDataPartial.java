package com.gildedgames.aether.api.world.islands;

import com.gildedgames.aether.api.world.islands.precipitation.IPrecipitationManager;
import com.gildedgames.orbis_api.preparation.IPrepSectorData;
import com.gildedgames.orbis_api.util.mc.NBT;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.biome.Biome;

import javax.annotation.Nonnull;

public interface IIslandDataPartial extends NBT
{
	/**
	 * Returns the bounding box of the island. These coordinates are normalized
	 * to the sector's region.
	 *
	 * @return The bounding box of the island
	 */
	@Nonnull
	IIslandBounds getBounds();

	/**
	 * @return The biome of this island.
	 */
	@Nonnull
	Biome getBiome();

	IPrecipitationManager getPrecipitation();

	IPrepSectorData getParentSectorData();

	void writePartial(NBTTagCompound tag);

	void readPartial(NBTTagCompound tag);

	void tick();
}
