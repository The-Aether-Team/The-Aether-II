package com.gildedgames.aether.common.tiles;

import com.gildedgames.aether.common.tiles.multiblock.TileEntityMultiblockDummy;
import net.minecraftforge.fml.common.registry.GameRegistry;

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
			MOA_EGG_ID = "aether.moa_egg",
			STRUCTURE_EXTENDED_ID = "aether.structure_extended",
			FROSTPINE_COOLER_ID = "aether.frostpine_cooler",
			INCUBATOR_ID = "aether.incubator",
			PRESENT_ID = "aether.present",
			LABYRINTH_BRIDGE_ID = "aether.labyrinth_bridge",
			WILDCARD_ID = "aether.wildcard";

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
		GameRegistry.registerTileEntity(TileEntityStructureExtended.class, STRUCTURE_EXTENDED_ID);
		GameRegistry.registerTileEntity(TileEntityFrostpineCooler.class, FROSTPINE_COOLER_ID);
		GameRegistry.registerTileEntity(TileEntityIncubator.class, INCUBATOR_ID);
		GameRegistry.registerTileEntity(TileEntityPresent.class, PRESENT_ID);
		GameRegistry.registerTileEntity(TileEntityLabyrinthBridge.class, LABYRINTH_BRIDGE_ID);
		GameRegistry.registerTileEntity(TileEntityWildcard.class, WILDCARD_ID);
	}
	
}
