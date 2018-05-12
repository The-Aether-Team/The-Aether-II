package com.gildedgames.aether.common.registry.content;

import com.gildedgames.aether.common.world.necromancer_tower.NecromancerTowerInstanceFactory;
import com.gildedgames.aether.common.world.necromancer_tower.NecromancerTowerInstanceHelper;
import com.gildedgames.orbis_api.OrbisAPI;

public class InstancesAether
{
	public static NecromancerTowerInstanceHelper NECROMANCER_TOWER_HANDLER;

	public static void init()
	{
		final NecromancerTowerInstanceFactory factory = new NecromancerTowerInstanceFactory(DimensionsAether.NECROMANCER_TOWER);

		NECROMANCER_TOWER_HANDLER = new NecromancerTowerInstanceHelper(OrbisAPI.instances().createInstanceHandler(factory));
	}
}
