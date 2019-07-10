package com.gildedgames.aether.common.world.island;

import com.gildedgames.aether.api.world.islands.IIslandData;
import com.gildedgames.aether.api.world.preparation.IChunkMask;
import com.gildedgames.aether.api.world.preparation.IPrepRegistryEntry;
import com.gildedgames.aether.api.world.preparation.IPrepSectorData;
import com.gildedgames.aether.common.world.access.BlockAccessPrep;
import com.gildedgames.orbis.lib.core.PlacedBlueprint;
import com.gildedgames.orbis.lib.util.mc.BlockUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class BlockAccessIsland extends BlockAccessPrep
{
	private final IIslandData island;

	public BlockAccessIsland(World world, IIslandData island, IPrepSectorData sectorData, IPrepRegistryEntry registryEntry)
	{
		super(world, sectorData, registryEntry);

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

	@Override
	public IBlockState getBlockState(BlockPos pos)
	{
		int chunkX = pos.getX() >> 4;
		int chunkZ = pos.getZ() >> 4;

		List<PlacedBlueprint> placedBlueprints = this.island.getPlacedBlueprintsInChunk(chunkX, chunkZ);

		for (PlacedBlueprint placed : placedBlueprints)
		{
			if (!placed.getRegion().contains(pos))
			{
				continue;
			}

			BlockPos min = placed.getRegion().getMin();

			IBlockState state = placed.getBaked().getBlockData().getBlockState(
					pos.getX() - min.getX(),
					pos.getY() - min.getY(),
					pos.getZ() - min.getZ()
			);

			if (!BlockUtil.isVoid(state))
			{
				return state;
			}
		}

		IChunkMask chunk = this.getChunk(chunkX, chunkZ);

		return this.transformer.getBlockState(chunk.getBlock(pos.getX() & 15, pos.getY(), pos.getZ() & 15));
	}
}
