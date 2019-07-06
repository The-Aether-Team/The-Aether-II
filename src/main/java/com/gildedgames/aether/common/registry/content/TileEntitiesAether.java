package com.gildedgames.aether.common.registry.content;

import com.gildedgames.aether.common.entities.tiles.*;
import com.gildedgames.aether.common.entities.tiles.multiblock.TileEntityMultiblockDummy;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class TileEntitiesAether
{

	public static final String
			ALTAR_ID = "aether.altar",
			HOLYSTONE_FURNACE_ID = "aether.holystone_furnace",
			SKYROOT_CHEST_ID = "aether.skyroot_chest",
			SKYROOT_SIGN_ID = "aether.skyroot_sign",
			MULTIBLOCK_DUMMY = "aether.multiblock_dummy",
			MOA_EGG_ID = "aether.moa_egg",
			ICESTONE_COOLER_ID = "aether.icestone_cooler",
			INCUBATOR_ID = "aether.incubator",
			PRESENT_ID = "aether.present",
			WILDCARD_ID = "aether.wildcard",
			MASONRY_BENCH_ID = "aether.masonry_bench",
			OUTPOST_CAMPFIRE_ID = "aether.outpost_campfire",
			TELEPORTER_ID = "aether.aether_teleporter",
			SKYROOT_BED_ID = "aether.skyroot_bed";

	public static void preInit()
	{
		GameRegistry.registerTileEntity(TileEntityAltar.class, ALTAR_ID);
		GameRegistry.registerTileEntity(TileEntityHolystoneFurnace.class, HOLYSTONE_FURNACE_ID);
		GameRegistry.registerTileEntity(TileEntitySkyrootChest.class, SKYROOT_CHEST_ID);
		GameRegistry.registerTileEntity(TileEntitySkyrootSign.class, SKYROOT_SIGN_ID);
		GameRegistry.registerTileEntity(TileEntityMultiblockDummy.class, MULTIBLOCK_DUMMY);
		GameRegistry.registerTileEntity(TileEntityMoaEgg.class, MOA_EGG_ID);
		GameRegistry.registerTileEntity(TileEntityIcestoneCooler.class, ICESTONE_COOLER_ID);
		GameRegistry.registerTileEntity(TileEntityIncubator.class, INCUBATOR_ID);
		GameRegistry.registerTileEntity(TileEntityPresent.class, PRESENT_ID);
		GameRegistry.registerTileEntity(TileEntityWildcard.class, WILDCARD_ID);
		GameRegistry.registerTileEntity(TileEntityMasonryBench.class, MASONRY_BENCH_ID);
		GameRegistry.registerTileEntity(TileEntityOutpostCampfire.class, OUTPOST_CAMPFIRE_ID);
		GameRegistry.registerTileEntity(TileEntityTeleporter.class, TELEPORTER_ID);
	}

}
