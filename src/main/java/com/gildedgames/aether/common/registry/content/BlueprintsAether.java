package com.gildedgames.aether.common.registry.content;

import com.gildedgames.aether.api.orbis_core.api.core.OrbisAPI;
import com.gildedgames.aether.api.orbis_core.data.BlueprintData;
import com.gildedgames.aether.api.orbis_core.data.management.IProject;
import com.gildedgames.aether.common.AetherCore;

public class BlueprintsAether
{
	public static BlueprintData ABAND_ANGEL_STOREROOM_1A;

	public static BlueprintData ABAND_ANGEL_WATCHTOWER_1A;

	public static BlueprintData ABAND_CAMPSITE_1A;

	public static BlueprintData ABAND_HUMAN_HOUSE_1A;

	public static BlueprintData ABAND_HUMAN_HOUSE_1B;

	public static BlueprintData OUTPOST_HIGHLANDS_A;

	public static BlueprintData OUTPOST_HIGHLANDS_B;

	public static BlueprintData SKYROOT_WATCHTOWER_1A;

	public static BlueprintData SKYROOT_WATCHTOWER_1B;

	public static BlueprintData SKYROOT_WATCHTOWER_2A;

	public static BlueprintData SKYROOT_WATCHTOWER_2B;

	public static BlueprintData SKYROOT_WATCHTOWER_3A;

	public static BlueprintData SKYROOT_WATCHTOWER_3B;

	public static BlueprintData WELL_1A;

	public static BlueprintData WELL_1B;

	private static IProject project;

	private BlueprintsAether()
	{

	}

	public static void init()
	{
		project = OrbisAPI.services().loadProject(null, AetherCore.getResource("aether_ii"));

		ABAND_ANGEL_STOREROOM_1A = project.getCache().getData(0);

		ABAND_ANGEL_WATCHTOWER_1A = project.getCache().getData(1);

		ABAND_CAMPSITE_1A = project.getCache().getData(2);

		ABAND_HUMAN_HOUSE_1A = project.getCache().getData(3);
		ABAND_HUMAN_HOUSE_1B = project.getCache().getData(4);

		OUTPOST_HIGHLANDS_A = project.getCache().getData(5);
		OUTPOST_HIGHLANDS_B = project.getCache().getData(6);

		SKYROOT_WATCHTOWER_1A = project.getCache().getData(7);
		SKYROOT_WATCHTOWER_1B = project.getCache().getData(8);

		SKYROOT_WATCHTOWER_2A = project.getCache().getData(9);
		SKYROOT_WATCHTOWER_2B = project.getCache().getData(10);

		SKYROOT_WATCHTOWER_3A = project.getCache().getData(11);
		SKYROOT_WATCHTOWER_3B = project.getCache().getData(12);

		WELL_1A = project.getCache().getData(13);
		WELL_1B = project.getCache().getData(14);
	}

}
