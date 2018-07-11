package com.gildedgames.aether.common.registry.content;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.orbis_api.OrbisAPI;
import com.gildedgames.orbis_api.data.blueprint.BlueprintData;
import com.gildedgames.orbis_api.data.management.IProject;
import com.gildedgames.orbis_api.data.management.IProjectCache;
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

	private static IProject project;

	private BlueprintsAether()
	{

	}

	public static void load()
	{
		ResourceLocation location = AetherCore.getResource("aetherii");

		project = OrbisAPI.services().loadProject(null, location, AetherCore.INSTANCE, "aether_ii");

		OrbisAPI.services().getProjectManager().cacheProject(location.getResourcePath(), project);

		final IProjectCache c = project.getCache();

		OUTPOST_A = c.getData(c.getDataId("outpost_a.blueprint"));
		OUTPOST_B = c.getData(c.getDataId("outpost_b.blueprint"));
		NECROMANCER_TOWER = c.getData(c.getDataId("necromancer_tower.blueprint"));
		ABAND_ANGEL_STOREROOM_1A = c.getData(c.getDataId("aband_angel_storeroom_1a.blueprint"));
		ABAND_ANGEL_WATCHTOWER_1A = c.getData(c.getDataId("aband_angel_watchtower_1a.blueprint"));
		ABAND_CAMPSITE_1A = c.getData(c.getDataId("aband_campsite_1a.blueprint"));
		ABAND_HUMAN_HOUSE_1A = c.getData(c.getDataId("aband_human_house_1a.blueprint"));
		ABAND_HUMAN_HOUSE_1B = c.getData(c.getDataId("aband_human_house_1b.blueprint"));
		SKYROOT_WATCHTOWER_1A = c.getData(c.getDataId("skyroot_watchtower_1a.blueprint"));
		SKYROOT_WATCHTOWER_1B = c.getData(c.getDataId("skyroot_watchtower_1b.blueprint"));
		SKYROOT_WATCHTOWER_2A = c.getData(c.getDataId("skyroot_watchtower_2a.blueprint"));
		SKYROOT_WATCHTOWER_2B = c.getData(c.getDataId("skyroot_watchtower_2b.blueprint"));
		SKYROOT_WATCHTOWER_3A = c.getData(c.getDataId("skyroot_watchtower_3a.blueprint"));
		SKYROOT_WATCHTOWER_3B = c.getData(c.getDataId("skyroot_watchtower_3b.blueprint"));
		WELL_1A = c.getData(c.getDataId("well_1a.blueprint"));
		WELL_1B = c.getData(c.getDataId("well_1b.blueprint"));
		WISPROOT_TREE_TALL = c.getData(c.getDataId("wisproot_tree_tall.blueprint"));
	}

}
