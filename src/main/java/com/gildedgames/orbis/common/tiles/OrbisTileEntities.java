package com.gildedgames.orbis.common.tiles;

import net.minecraftforge.fml.common.registry.GameRegistry;

public class OrbisTileEntities
{
	public static final String BLOCK_DATA_CONTAINER_ID = "orbis.block_data_container";

	public static void preInit()
	{
		GameRegistry.registerTileEntity(TileEntityBlockDataContainer.class, BLOCK_DATA_CONTAINER_ID);
	}
}