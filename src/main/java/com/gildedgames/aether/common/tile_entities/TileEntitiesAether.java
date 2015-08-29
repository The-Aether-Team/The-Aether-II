package com.gildedgames.aether.common.tile_entities;

import net.minecraftforge.fml.common.registry.GameRegistry;

public class TileEntitiesAether
{
	public static final String
			ALTAR_ID = "aether.altar",
			HOLYSTONE_FURNACE_ID = "aether.holystone_furnace";

	public static void preInit()
	{
		GameRegistry.registerTileEntity(TileEntityAltar.class, ALTAR_ID);
		GameRegistry.registerTileEntity(TileEntityHolystoneKiln.class, HOLYSTONE_FURNACE_ID);
	}
}
