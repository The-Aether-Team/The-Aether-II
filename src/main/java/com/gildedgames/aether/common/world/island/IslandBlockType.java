package com.gildedgames.aether.common.world.island;

/**
 * Blocks that an island's basic structure can contain. See {@link IslandChunkMaskTransformer} for implementing the conversion methods.
 */
public enum IslandBlockType
{
	AIR_BLOCK,
	STONE_MOSSY_BLOCK,
	STONE_BLOCK,
	TOPSOIL_BLOCK,
	SOIL_BLOCK,
	COAST_BLOCK,
	SNOW_BLOCK,
	FERROSITE_BLOCK,
	WATER_BLOCK,
	CLOUD_BED_BLOCK,
	VEIN_BLOCK;

	public static final IslandBlockType[] VALUES = IslandBlockType.values();
}
