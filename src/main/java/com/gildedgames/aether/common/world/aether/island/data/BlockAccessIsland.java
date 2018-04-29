package com.gildedgames.aether.common.world.aether.island.data;

import com.gildedgames.aether.api.world.islands.IIslandData;
import com.gildedgames.orbis_api.preparation.IPrepSectorData;
import com.gildedgames.orbis_api.preparation.impl.util.BlockAccessPrep;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockAccessIsland extends BlockAccessPrep
{
	private IIslandData island;

	public BlockAccessIsland(World world, IIslandData island, IPrepSectorData sectorData)
	{
		super(world, sectorData);

		this.island = island;
	}

	@Override
	public boolean canAccess(BlockPos pos)
	{
		return this.island.getBounds().contains(pos.getX(), pos.getY(), pos.getZ());
	}

	@Override
	public boolean canAccess(int x, int z)
	{
		return this.island.getBounds().contains(x, 0, z);
	}

	@Override
	public boolean canAccess(int minX, int minY, int minZ, int maxX, int maxY, int maxZ)
	{
		return this.island.getBounds().contains(minX, minY, minZ, maxX, maxY, maxZ);
	}
}
