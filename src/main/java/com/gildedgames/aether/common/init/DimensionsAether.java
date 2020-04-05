package com.gildedgames.aether.common.init;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.world.WorldProviderAether;
import com.gildedgames.aether.common.world.WorldProviderNecromancerTower;
import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;

public class DimensionsAether
{
	public static DimensionType AETHER;

	public static DimensionType NECROMANCER_TOWER;

	public static void preInit()
	{
		// Register dimension types
		DimensionsAether.AETHER = DimensionType.register("Aether", "_aether",
				AetherCore.CONFIG.aetherDimID, WorldProviderAether.class, false);

		DimensionsAether.NECROMANCER_TOWER = DimensionType.register("NecromancerTower", "_necromancer_tower",
				AetherCore.CONFIG.necromancerDimId, WorldProviderNecromancerTower.class, false);

		// Register dimensions
		DimensionManager.registerDimension(AetherCore.CONFIG.aetherDimID, DimensionsAether.AETHER);
	}

}
