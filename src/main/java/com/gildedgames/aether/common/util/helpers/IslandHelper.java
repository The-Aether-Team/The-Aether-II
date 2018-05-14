package com.gildedgames.aether.common.util.helpers;

import com.gildedgames.aether.api.world.islands.IIslandData;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.world.aether.prep.PrepSectorDataAether;
import com.gildedgames.orbis_api.preparation.IPrepSector;
import com.gildedgames.orbis_api.preparation.impl.util.PrepHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.concurrent.Future;

public class IslandHelper
{

	public static BlockPos getOutpostPos(final World world, final BlockPos pos)
	{
		IIslandData island = get(world, pos.getX() >> 4, pos.getZ() >> 4);

		if (island != null && island.getOutpostPos() != null)
		{
			return island.getOutpostPos();
		}

		if (island != null)
		{
			AetherCore.LOGGER.info("SOMETHING IS VERY WRONG - AN ISLAND DIDN'T HAVE A RESPAWN POINT, PLAYER WILL SPAWN AT ISLAND CENTER NOW");

			return new BlockPos(island.getBounds().getCenterX(), world.getHeight((int) island.getBounds().getCenterX(), (int) island.getBounds().getCenterZ()),
					island.getBounds().getCenterZ());
		}

		AetherCore.LOGGER.info("SOMETHING IS VERY WRONG - AN ISLAND DIDN'T HAVE A RESPAWN POINT, PLAYER WILL SPAWN AT ORIGIN NOW");

		return BlockPos.ORIGIN;
	}

	public static IIslandData get(World world, int chunkX, int chunkZ)
	{
		IPrepSector sector = PrepHelper.getSector(world, chunkX, chunkZ);

		if (sector != null && sector.getData() instanceof PrepSectorDataAether)
		{
			PrepSectorDataAether data = (PrepSectorDataAether) sector.getData();

			return data.getIslandData();
		}

		return null;
	}

}
