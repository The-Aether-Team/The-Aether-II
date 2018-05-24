package com.gildedgames.aether.common.world.aether.island.data;

import com.gildedgames.aether.api.world.islands.IIslandDataPartial;
import com.gildedgames.orbis_api.preparation.IPrepChunkManager;
import com.gildedgames.orbis_api.preparation.IPrepSectorData;
import com.gildedgames.orbis_api.preparation.impl.util.BlockAccessPrep;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockAccessIsland extends BlockAccessPrep
{
	private IIslandDataPartial island;

	public BlockAccessIsland(World world, IIslandDataPartial island, IPrepSectorData sectorData, IPrepChunkManager iPrepChunkManager)
	{
		super(world, sectorData, iPrepChunkManager);

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
