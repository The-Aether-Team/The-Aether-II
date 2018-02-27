package com.gildedgames.aether.common.registry.content;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.orbis.api.OrbisAPI;
import com.gildedgames.orbis.api.data.blueprint.BlueprintData;
import com.gildedgames.orbis.api.data.management.IProject;

public class BlueprintsAether
{

	public static BlueprintData OUTPOST;

	public static BlueprintData NECROMANCER_TOWER;

	private static IProject project;

	private BlueprintsAether()
	{

	}

	public static void init()
	{
		project = OrbisAPI.services().loadProject(null, AetherCore.getResource("aetherii"), AetherCore.INSTANCE, "AetherII");

		OUTPOST = project.getCache().getData(1);
		NECROMANCER_TOWER = project.getCache().getData(0);
	}

}
