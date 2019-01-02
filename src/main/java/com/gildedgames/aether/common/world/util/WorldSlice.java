package com.gildedgames.aether.common.world.util;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;

public class WorldSlice
{
	private final World world;

	private final Chunk[] chunks;

	private final int offsetX, offsetZ;

	private final IBlockState defaultBlockState = Blocks.AIR.getDefaultState();

	public WorldSlice(World world, ChunkPos pos)
	{
		this.world = world;

		this.chunks = new Chunk[3 * 3];

		this.offsetX = (pos.x * 16) - 16;
		this.offsetZ = (pos.z * 16) - 16;

		for (int x = 0; x < 3; x++)
		{
			for (int z = 0; z < 3; z++)
			{
				int chunkX = pos.x + x - 1;
				int chunkZ = pos.z + z - 1;

				BlockPos from = new BlockPos(chunkX * 16, 0, chunkZ * 16);
				BlockPos to = new BlockPos((chunkX * 16) + 15, 255, (chunkZ * 16) + 15);

				if (world.isAreaLoaded(from, to))
				{
					this.chunks[(x * 3) + z] = world.getChunk(chunkX, chunkZ);
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
		int chunkX = (x - this.offsetX) >> 4;
		int chunkZ = (z - this.offsetZ) >> 4;

		Chunk chunk = this.chunks[(chunkX * 3) + chunkZ];

		if (chunk != null && y >= 0 && y >> 4 < chunk.getBlockStorageArray().length)
		{
			ExtendedBlockStorage extendedblockstorage = chunk.getBlockStorageArray()[y >> 4];

			if (extendedblockstorage != Chunk.NULL_BLOCK_STORAGE)
			{
				return extendedblockstorage.get(x & 15, y & 15, z & 15);
			}
		}

		return this.defaultBlockState;
	}

	/**
	 * This method is intended to be used when you are doing "check and replace" generation. Rather than
	 * performing an additional fetch operation, we can avoid it entirely. Care must be taken to ensure
	 * that {@param before} is the block previously queried at the same {@param pos} using
	 * {@link WorldSlice#getBlockState(BlockPos)}.
	 *
	 * It is also possible to control whether or not replacing the block should prompt a lighting
	 * update.
	 *
	 * You should NEVER use this method if you are replacing an air block. Only use it if the block
	 * you are replacing is non-air.
	 *
	 * @param pos The {@link BlockPos} of the block to replace
	 * @param before The state of the block at {@link BlockPos} before it was replaced
	 * @param after The state of the block at {@link BlockPos} after it is replaced
	 * @param updateLight Whether or not to perform block/sky lighting updates
	 */
	public void replaceBlockState(BlockPos pos, IBlockState before, IBlockState after, boolean updateLight)
	{
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();

		int chunkX = (x - this.offsetX) >> 4;
		int chunkZ = (z - this.offsetZ) >> 4;

		Chunk chunk = this.chunks[(chunkX * 3) + chunkZ];

		if (chunk != null && y >= 0 && y >> 4 < chunk.getBlockStorageArray().length)
		{
			ExtendedBlockStorage extendedblockstorage = chunk.getBlockStorageArray()[y >> 4];

			if (extendedblockstorage != Chunk.NULL_BLOCK_STORAGE)
			{
				extendedblockstorage.set(x & 15, y & 15, z & 15, after);

				if (updateLight)
				{
					this.world.checkLight(pos);
				}

				this.world.notifyBlockUpdate(pos, before, after, 2 | 16);
			}
		}
	}


	public World getWorld()
	{
		return this.world;
	}

	public boolean isAirBlock(BlockPos pos)
	{
		return this.getBlockState(pos).getMaterial() == Material.AIR;
	}
}
