package com.gildedgames.aether.common.world.aether.island.data;

import com.gildedgames.aether.api.world.islands.IIslandData;
import com.gildedgames.orbis_api.core.BlockDataChunk;
import com.gildedgames.orbis_api.core.PlacedBlueprint;
import com.gildedgames.orbis_api.preparation.IPrepChunkManager;
import com.gildedgames.orbis_api.preparation.IPrepSectorData;
import com.gildedgames.orbis_api.preparation.impl.ChunkMask;
import com.gildedgames.orbis_api.preparation.impl.util.BlockAccessPrep;
import com.gildedgames.orbis_api.util.mc.BlockUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class BlockAccessIsland extends BlockAccessPrep
{
	private final IIslandData island;

	public BlockAccessIsland(World world, IIslandData island, IPrepSectorData sectorData, IPrepChunkManager iPrepChunkManager)
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

	@Override
	public IBlockState getBlockState(BlockPos pos)
	{
		int chunkX = pos.getX() >> 4;
		int chunkZ = pos.getZ() >> 4;

		List<PlacedBlueprint> placedBlueprints = this.island.getPlacedBlueprintsInChunk(chunkX, chunkZ);

		for (PlacedBlueprint placed : placedBlueprints)
		{
			if (pos.getY() < placed.getCreationData().getPos().getY()
					|| pos.getY() > placed.getCreationData().getPos().getY() + placed.getBaked().getHeight() - 1)
			{
				continue;
			}

			for (final BlockDataChunk dataChunk : placed.getBaked().getDataChunks())
			{
				if (dataChunk.getPos().x == chunkX && dataChunk.getPos().z == chunkZ)
				{
					IBlockState state = dataChunk.getContainer()
							.getBlockState(pos.getX() & 15, pos.getY() - placed.getCreationData().getPos().getY(), pos.getZ() & 15);

					if (state != null && !BlockUtil.isVoid(state))
					{
						return state;
					}
				}
			}
		}

		ChunkMask chunk = this.getChunk(pos.getX() >> 4, pos.getZ() >> 4);

		return this.transformer.remapBlock(chunk.getBlock(pos.getX() & 15, pos.getY(), pos.getZ() & 15));
	}
}
