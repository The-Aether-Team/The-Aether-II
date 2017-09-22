package com.gildedgames.aether.api.world.islands;

import com.gildedgames.aether.api.util.NBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface IIslandData extends NBT
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

	/**
	 * Returns the respawn point of this island.
	 *
	 * @return The {@link BlockPos} of this island's respawn point, null if it doesn't exist
	 */
	@Nullable
	BlockPos getRespawnPoint();

	/**
	 * Sets the respawn point of this island and marks this island for saving.
	 *
	 * @param pos The {@link BlockPos} of the new respawn point, null to remove it.
	 */
	void setRespawnPoint(@Nullable BlockPos pos);

	/**
	 * Returns the seed of the island used for generation.
	 *
	 * @return The seed of the island
	 */
	long getSeed();

	/**
	 * Returns the virtual manager of the island. This is used to prepare the
	 * generation data of this island for future generation.
	 * @return The virtual manager of the island
	 */
	@Nonnull
	IVirtualDataManager getVirtualDataManager();

}
