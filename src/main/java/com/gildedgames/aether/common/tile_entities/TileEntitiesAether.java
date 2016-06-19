package com.gildedgames.aether.common.tile_entities;

import net.minecraftforge.fml.common.registry.GameRegistry;

import com.gildedgames.aether.common.tile_entities.multiblock.TileEntityMultiblockDummy;

public class TileEntitiesAether
{
	
	public static final String
			ALTAR_ID = "aether.altar",
			HOLYSTONE_FURNACE_ID = "aether.holystone_furnace",
			SKYROOT_CHEST_ID = "aether.skyroot_chest",
			SKYROOT_SIGN_ID = "aether.skyroot_sign",
			MULTIBLOCK_DUMMY = "aether.multiblock_dummy",
			LABYRINTH_TOTEM = "aether.labyrinth_totem",
			LABYRINTH_EYE = "aether.labyrinth_eye",
			LABYRINTH_DOOR = "aether.labyrinth_door",
			WILDCARD = "aether.wildcard",
			BOUNDARY = "aether.boundary",
			LABYRINTH_CHEST_ID = "aether.labyrinth_chest";

	public static void preInit()
	{
		GameRegistry.registerTileEntity(TileEntityAltar.class, ALTAR_ID);
		GameRegistry.registerTileEntity(TileEntityHolystoneFurnace.class, HOLYSTONE_FURNACE_ID);
		GameRegistry.registerTileEntity(TileEntitySkyrootChest.class, SKYROOT_CHEST_ID);
		GameRegistry.registerTileEntity(TileEntitySkyrootSign.class, SKYROOT_SIGN_ID);
		GameRegistry.registerTileEntity(TileEntityMultiblockDummy.class, MULTIBLOCK_DUMMY);
		GameRegistry.registerTileEntity(TileEntityLabyrinthTotem.class, LABYRINTH_TOTEM);
		GameRegistry.registerTileEntity(TileEntityLabyrinthDoor.class, LABYRINTH_DOOR);
		GameRegistry.registerTileEntity(TileEntityLabyrinthEye.class, LABYRINTH_EYE);
		GameRegistry.registerTileEntity(TileEntityWildcard.class, WILDCARD);
		GameRegistry.registerTileEntity(TileEntityBoundary.class, BOUNDARY);
		GameRegistry.registerTileEntity(TileEntityLabyrinthChest.class, LABYRINTH_CHEST_ID);
	}
	
}
