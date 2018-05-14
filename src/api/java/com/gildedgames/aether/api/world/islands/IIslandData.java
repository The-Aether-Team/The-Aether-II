package com.gildedgames.aether.api.world.islands;

import com.gildedgames.aether.api.world.generation.WorldDecoration;
import com.gildedgames.orbis_api.core.BlueprintDefinition;
import com.gildedgames.orbis_api.core.ICreationData;
import com.gildedgames.orbis_api.core.PlacedBlueprint;
import com.gildedgames.orbis_api.util.mc.NBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;

public interface IIslandData extends NBT
{
	/**
	 * The contained components have specialised data, usually
	 * based on the biome that this island uses.
	 */
	<T extends NBT> void addComponents(Collection<T> components);

	/**
	 * The contained components have specialised data, usually
	 * based on the biome that this island uses.
	 * @return The components for this Island
	 */
	Collection<NBT> getComponents();

	List<WorldDecoration> getBasicDecorations();

	List<WorldDecoration> getTreeDecorations();

	float getForestTreeCountModifier();

	float getOpenAreaDecorationGenChance();

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

	@Nonnull
	IIslandGenerator getGenerator();

	/**
	 * Returns the position of the Outpost on this island.
	 *
	 * @return The {@link BlockPos} of this island's Outpost, null if it doesn't exist
	 */
	@Nullable
	BlockPos getOutpostPos();

	/**
	 * Sets the position of the Outpost on this island.
	 *
	 * @param pos The {@link BlockPos} of the new respawn point, null to remove it.
	 */
	void setOutpostPos(@Nullable BlockPos pos);

	/**
	 * Returns the seed of the island used for generation.
	 *
	 * @return The seed of the island
	 */
	long getSeed();

	PlacedBlueprint placeBlueprint(BlueprintDefinition def, ICreationData<?> data);

	List<PlacedBlueprint> getPlacedBlueprints();

	void setPlacedBlueprints(List<PlacedBlueprint> instances);

}
