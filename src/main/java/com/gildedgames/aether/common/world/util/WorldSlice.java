package com.gildedgames.aether.common.world.util;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public class WorldSlice
{
	private final World world;

	private final Chunk[][] chunks;

	private final int offsetX, offsetZ;

	private final IBlockState defaultBlockState = Blocks.AIR.getDefaultState();

	public WorldSlice(World world, ChunkPos pos)
	{
		this.world = world;

		this.chunks = new Chunk[3][3];

		this.offsetX = (pos.x * 16) - 16;
		this.offsetZ = (pos.z * 16) - 16;

		for (int x = -1; x <= 1; x++)
		{
			for (int z = -1; z <= 1; z++)
			{
				int chunkX = pos.x + x;
				int chunkZ = pos.z + z;

				BlockPos from = new BlockPos(chunkX * 16, 0, chunkZ * 16);
				BlockPos to = new BlockPos((chunkX * 16) + 15, 255, (chunkZ * 16) + 15);

				if (world.isAreaLoaded(from, to))
				{
					this.chunks[x + 1][z + 1] = world.getChunk(chunkX, chunkZ);
				}
			}
		}
	}

	public IBlockState getBlockState(BlockPos pos)
	{
		return this.getBlockState(pos.getX(), pos.getY(), pos.getZ());
	}

	public IBlockState getBlockState(int x, int y, int z)
	{
		int x2 = (x - this.offsetX);
		int z2 = (z - this.offsetZ);

		int chunkX = x2 >> 4;
		int chunkZ = z2 >> 4;

		Chunk chunk = this.chunks[chunkX][chunkZ];

		if (chunk == null)
		{
			return this.defaultBlockState;
		}

		return chunk.getBlockState(x & 15, y, z & 15);
	}

	public World getWorld()
	{
		return this.world;
	}

	public boolean isAirBlock(BlockPos pos)
	{
		int x2 = (pos.getX() - this.offsetX);
		int z2 = (pos.getZ() - this.offsetZ);

		int chunkX = x2 >> 4;
		int chunkZ = z2 >> 4;

		Chunk chunk = this.chunks[chunkX][chunkZ];

		if (chunk == null)
		{
			return this.defaultBlockState.getMaterial() == Material.AIR;
		}

		return chunk.getBlockState(pos.getX() & 15, pos.getY(), pos.getZ() & 15).getMaterial() == Material.AIR;
	}
}
