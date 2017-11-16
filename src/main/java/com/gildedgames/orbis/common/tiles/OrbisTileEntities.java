package com.gildedgames.orbis.common.tiles;

import net.minecraftforge.fml.common.registry.GameRegistry;

public class OrbisTileEntities
{
	public static final String BLOCK_DATA_CONTAINER_ID = "orbis.block_data_container";

	public static final String BLOCK_PALETTE_ID = "orbis.block_palette";

	public static final String BLUEPRINT_ID = "orbis.blueprint";

	public static final String BLUEPRINT_PALETTE_ID = "orbis.blueprint_palette";

	public static void preInit()
	{
		GameRegistry.registerTileEntity(TileEntityBlockDataContainer.class, BLOCK_DATA_CONTAINER_ID);
		GameRegistry.registerTileEntity(TileEntityBlockPalette.class, BLOCK_PALETTE_ID);
		GameRegistry.registerTileEntity(TileEntityBlueprint.class, BLUEPRINT_ID);
		GameRegistry.registerTileEntity(TileEntityBlueprintPalette.class, BLUEPRINT_PALETTE_ID);
	}
}