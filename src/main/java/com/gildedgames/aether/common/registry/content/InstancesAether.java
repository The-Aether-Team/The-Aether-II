package com.gildedgames.aether.common.registry.content;

import com.gildedgames.aether.common.world.necromancer_tower.NecromancerTowerInstanceFactory;
import com.gildedgames.aether.common.world.necromancer_tower.NecromancerTowerInstanceHandler;
import com.gildedgames.orbis.api.OrbisAPI;

public class InstancesAether
{
	public static NecromancerTowerInstanceHandler NECROMANCER_TOWER_HANDLER;

	public static void init()
	{
		final NecromancerTowerInstanceFactory factory = new NecromancerTowerInstanceFactory(DimensionsAether.NECROMANCER_TOWER);

		NECROMANCER_TOWER_HANDLER = new NecromancerTowerInstanceHandler(OrbisAPI.instances().createAndRegisterInstanceHandler(factory));
	}
}
