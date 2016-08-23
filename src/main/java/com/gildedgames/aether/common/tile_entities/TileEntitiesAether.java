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
			LABYRINTH_CHEST_ID = "aether.labyrinth_chest",
			MOA_EGG_ID = "aether.moa_egg";

	public static void preInit()
	{
		GameRegistry.registerTileEntity(TileEntityAltar.class, ALTAR_ID);
		GameRegistry.registerTileEntity(TileEntityHolystoneFurnace.class, HOLYSTONE_FURNACE_ID);
		GameRegistry.registerTileEntity(TileEntitySkyrootChest.class, SKYROOT_CHEST_ID);
		GameRegistry.registerTileEntity(TileEntitySkyrootSign.class, SKYROOT_SIGN_ID);
		GameRegistry.registerTileEntity(TileEntityMultiblockDummy.class, MULTIBLOCK_DUMMY);
		GameRegistry.registerTileEntity(TileEntityLabyrinthTotem.class, LABYRINTH_TOTEM);
		GameRegistry.registerTileEntity(TileEntityLabyrinthChest.class, LABYRINTH_CHEST_ID);
		GameRegistry.registerTileEntity(TileEntityMoaEgg.class, MOA_EGG_ID);
	}
	
}
