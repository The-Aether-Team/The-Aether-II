package com.gildedgames.aether.common;

import java.io.File;
import java.io.IOException;

import net.minecraftforge.common.config.Configuration;

public class AetherConfig
{

	public static File cfglocation;

	/*Menu Config*/
	public static boolean UseAetherMenu;

	/* Dimension IDs */
	public static int AetherDimensionID;

	public static int DungeonDimensionID;

	/* Biome IDs */

	public static int AetherBiomeID;

	public static int DungeonBiomeID;

	/* Potion IDs */

	public static int SlowfallPotionID;

	public static int HighStepsPotionID;

	/* Portal Generation */

	public static boolean GenerateAetherPortals;

	public static void initProps(File location)
	{
		File newFile = new File(location + "/aether" + "/Aether II.cfg");

		try
		{
			newFile.createNewFile();
		}
		catch (IOException e)
		{
			System.out.println("Could not create configuration file for Aether II. Reason: ");
			System.out.println(e);
		}

		Configuration config = new Configuration(newFile);
		cfglocation = location;
		config.load();

		String MenuConfig = "Menu Configuration";
		UseAetherMenu = config.getBoolean(MenuConfig, "Use Aether Menu System", true, "");

		String AetherDimID = "Dimension IDs";
		AetherDimensionID = config.get(AetherDimID, "Aether Dimension ID", 3).getInt(3);
		DungeonDimensionID = config.get(AetherDimID, "Dungeon Dimension ID", 4).getInt(4);

		String BiomeID = "Biome IDs";
		AetherBiomeID = config.get(BiomeID, "Aether Biome ID", 237).getInt(237);
		DungeonBiomeID = config.get(BiomeID, "Dungeon Biome ID", 200).getInt(200);

		String PotionID = "Potion IDs";
		SlowfallPotionID = config.get(PotionID, "Slowfall Potion ID", 25).getInt(25);
		HighStepsPotionID = config.get(PotionID, "High Steps Potion ID", 26).getInt(26);

		String AetherPortalGeneration = "Aether Portal Options";
		GenerateAetherPortals = config.getBoolean("Generate Aether Portals", AetherPortalGeneration, true,
				"Decides wether or not the Aether will generate portals. (allow player created portals only)");

		config.save();
	}
}
