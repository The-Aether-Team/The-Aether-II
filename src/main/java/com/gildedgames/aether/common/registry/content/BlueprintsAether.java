package com.gildedgames.aether.common.registry.content;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.orbis_api.OrbisAPI;
import com.gildedgames.orbis_api.data.blueprint.BlueprintData;
import com.gildedgames.orbis_api.data.management.IProject;
import com.gildedgames.orbis_api.data.management.IProjectManager;
import net.minecraft.util.ResourceLocation;

public class BlueprintsAether
{

	public static BlueprintData OUTPOST_A;

	public static BlueprintData OUTPOST_B;

	public static BlueprintData NECROMANCER_TOWER;

	public static BlueprintData ABAND_ANGEL_STOREROOM_1A;

	public static BlueprintData ABAND_ANGEL_WATCHTOWER_1A;

	public static BlueprintData ABAND_CAMPSITE_1A;

	public static BlueprintData ABAND_HUMAN_HOUSE_1A;

	public static BlueprintData ABAND_HUMAN_HOUSE_1B;

	public static BlueprintData SKYROOT_WATCHTOWER_1A;

	public static BlueprintData SKYROOT_WATCHTOWER_1B;

	public static BlueprintData SKYROOT_WATCHTOWER_2A;

	public static BlueprintData SKYROOT_WATCHTOWER_2B;

	public static BlueprintData SKYROOT_WATCHTOWER_3A;

	public static BlueprintData SKYROOT_WATCHTOWER_3B;

	public static BlueprintData WELL_1A;

	public static BlueprintData WELL_1B;

	public static BlueprintData WISPROOT_TREE_TALL;

	public static BlueprintData GREATROOT_TREE;

	public static BlueprintData SKYROOT_OAK_GREEN, SKYROOT_OAK_BLUE, SKYROOT_OAK_DARK_BLUE;

	public static BlueprintData AMBEROOT_TREE;

	private static IProject project;

	private BlueprintsAether()
	{

	}

	private static BlueprintData loadData(String name)
	{
		BlueprintData data = project.getCache().getData(project.getCache().getDataId(name.replace("/", "\\") + ".blueprint"));

		if (data == null)
		{
			throw new RuntimeException("Failed to load Blueprint Data: " + name + ".blueprint");
		}

		return data;
	}

	public static void load(IProjectManager projectManager)
	{
		ResourceLocation location = AetherCore.getResource("aetherii");

		project = OrbisAPI.services().loadProject(null, location, AetherCore.INSTANCE, "aether_ii");

		projectManager.cacheProject(location.getResourcePath(), project);

		OUTPOST_A = loadData("outpost_a");
		OUTPOST_B = loadData("outpost_b");
		NECROMANCER_TOWER = loadData("necromancer_tower");
		ABAND_ANGEL_STOREROOM_1A = loadData("aband_angel_storeroom_1a");
		ABAND_ANGEL_WATCHTOWER_1A = loadData("aband_angel_watchtower_1a");
		ABAND_CAMPSITE_1A = loadData("aband_campsite_1a");
		ABAND_HUMAN_HOUSE_1A = loadData("aband_human_house_1a");
		ABAND_HUMAN_HOUSE_1B = loadData("aband_human_house_1b");
		SKYROOT_WATCHTOWER_1A = loadData("skyroot_watchtower_1a");
		SKYROOT_WATCHTOWER_1B = loadData("skyroot_watchtower_1b");
		SKYROOT_WATCHTOWER_2A = loadData("skyroot_watchtower_2a");
		SKYROOT_WATCHTOWER_2B = loadData("skyroot_watchtower_2b");
		SKYROOT_WATCHTOWER_3A = loadData("skyroot_watchtower_3a");
		SKYROOT_WATCHTOWER_3B = loadData("skyroot_watchtower_3b");
		WELL_1A = loadData("well_1a");
		WELL_1B = loadData("well_1b");
		WISPROOT_TREE_TALL = loadData("wisproot_tree_tall");
		GREATROOT_TREE = loadData("greatroot_1");
		SKYROOT_OAK_GREEN = loadData("trees/skyroot/skyroot_oak_green");
		SKYROOT_OAK_BLUE = loadData("trees/skyroot/skyroot_oak_blue");
		SKYROOT_OAK_DARK_BLUE = loadData("trees/skyroot/skyroot_oak_dark_blue");
		AMBEROOT_TREE = loadData("trees/amberoot/amberoot_tree");
	}
}
