package com.gildedgames.aether.common.util.helpers;

import com.gildedgames.aether.api.world.ISector;
import com.gildedgames.aether.api.world.IslandSectorHelper;
import com.gildedgames.aether.api.world.islands.IIslandData;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Collection;

public class IslandHelper
{

	public static BlockPos getRespawnPoint(final World world, final BlockPos pos)
	{
		final ISector loadedSector = IslandSectorHelper.getAccess(world)
				.getLoadedSector(pos.getX() >> 4, pos.getZ() >> 4)
				.orElseThrow(() -> new IllegalArgumentException("Couldn't find island sector:"));
		final Collection<IIslandData> islands = loadedSector
				.getIslandsForRegion(pos.getX(), 0, pos.getZ(), 1, 255, 1);

		boolean shouldSpawnAtRespawnPoint = false;
		IIslandData island = null;

		for (final IIslandData data : islands)
		{
			if (data != null && data.getRespawnPoint() != null)
			{
				shouldSpawnAtRespawnPoint = true;
				island = data;
				break;
			}
		}

		if (shouldSpawnAtRespawnPoint)
		{
			return island.getRespawnPoint();
		}

		return null;
	}

}
