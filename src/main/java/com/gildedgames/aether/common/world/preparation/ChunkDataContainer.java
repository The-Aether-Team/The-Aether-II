package com.gildedgames.aether.common.world.preparation;

import com.gildedgames.aether.api.world.preparation.IChunkMaskTransformer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BitArray;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.BlockStateContainer;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ChunkDataContainer
{
	private final SegmentStorage[] segments = new SegmentStorage[16];

	private final HashMap<BlockPos, TileEntity> tileEntities = new HashMap<>();

	private final ArrayList<Entity> entities = new ArrayList<>();

	private final int chunkX, chunkZ;

	private final boolean hasSkylight;

	public ChunkDataContainer(int chunkX, int chunkZ, boolean hasSkylight)
	{
		this.chunkX = chunkX;
		this.chunkZ = chunkZ;
		this.hasSkylight = hasSkylight;
	}

	public IBlockState getBlockState(final BlockPos pos)
	{
		return this.getBlockState(pos.getX(), pos.getY(), pos.getZ());
	}

	public IBlockState getBlockState(final int x, final int y, final int z)
	{
		SegmentStorage segment = this.segments[y >> 4];

		if (segment == null)
		{
			return Blocks.AIR.getDefaultState();
		}

		return segment.blockStorage.get(x, y & 15, z);
	}

	public void setBlockState(final int x, final int y, final int z, final IBlockState state)
	{
		SegmentStorage segment = this.segments[y >> 4];

		if (segment == null)
		{
			this.segments[y >> 4] = segment = new SegmentStorage((y >> 4) * 16, this.hasSkylight);
		}

		segment.blockStorage.set(x, y & 15, z, state);

		segment.opacity[x << 8 | z << 4 | (y & 15)] = (byte) state.getLightOpacity();
	}

	private int getBlockOpacity(int x, int y, int z)
	{
		SegmentStorage segment = this.segments[y >> 4];

		if (segment == null)
		{
			return 0;
		}

		return Byte.toUnsignedInt(segment.opacity[x << 8 | z << 4 | (y & 15)]);
	}

	public void setBlockState(final BlockPos pos, final IBlockState state)
	{
		this.setBlockState(pos.getX(), pos.getY(), pos.getZ(), state);
	}

	public TileEntity getTileEntity(BlockPos pos)
	{
		return this.tileEntities.get(pos);
	}

	public void setTileEntity(BlockPos pos, TileEntity entity)
	{
		if (entity == null)
		{
			this.tileEntities.remove(pos);
		}
		else
		{
			entity.setPos(pos);

			this.tileEntities.put(pos, entity);
		}
	}

	public static ChunkDataContainer createFromMask(World world, ChunkMask mask, IChunkMaskTransformer transformer, int chunkX, int chunkZ)
	{
		ChunkDataContainer container = new ChunkDataContainer(chunkX, chunkZ, world.provider.hasSkyLight());

		BlockStateCacher cacher = new BlockStateCacher(transformer);

		for (int chunkY = 0; chunkY < 32; chunkY++)
		{
			ChunkMaskSegment src = mask.getSegment(chunkY);

			if (src == null)
			{
				continue;
			}

			SegmentStorage dest = container.segments[chunkY >> 1];

			if (dest == null)
			{
				dest = new SegmentStorage((chunkY >> 1) << 4, world.provider.hasSkyLight());

				container.segments[chunkY >> 1] = dest;
			}

			ExtendedBlockStorage blockStorage = dest.blockStorage;
			BitArray bitArray = blockStorage.data.storage;

			cacher.update(blockStorage.data);

			for (int x = 0; x < 16; x++)
			{
				for (int z = 0; z < 16; z++)
				{
					for (int y = 0, y2 = (chunkY & 0b1) * 8; y < 8; y++, y2++)
					{
						int block = src.getBlock(x, y, z);

						if (block == 0)
						{
							continue;
						}

						int key = cacher.getValue(transformer, block);

						dest.opacity[x << 8 | z << 4 | y2] = 127;

						bitArray.setAt(y2 << 8 | z << 4 | x, key);
						blockStorage.blockRefCount++;
					}
				}
			}
		}

		return container;
	}

	public Chunk createChunk(World world, int chunkX, int chunkZ)
	{
		Chunk chunk = new Chunk(world, chunkX, chunkZ);

		for (int chunkY = 0; chunkY < 16; chunkY++)
		{
			SegmentStorage segment = this.segments[chunkY];

			if (segment == null)
			{
				continue;
			}

			chunk.getBlockStorageArray()[chunkY] = segment.blockStorage;
		}

		for (TileEntity tileEntity : this.tileEntities.values())
		{
			chunk.addTileEntity(tileEntity.getPos(), tileEntity);
		}

		for (Entity entity : this.entities)
		{
			chunk.addEntity(entity);
		}

		this.prepareChunkLighting(world, chunk);

		return chunk;
	}

	private void prepareChunkLighting(World world, Chunk chunk)
	{
		int maxY = chunk.getTopFilledSegment();

		chunk.heightMapMinimum = Integer.MAX_VALUE;

		for (int x = 0; x < 16; ++x)
		{
			for (int z = 0; z < 16; ++z)
			{
				chunk.precipitationHeightMap[x + (z << 4)] = -999;

				for (int y = maxY + 16; y > 0; --y)
				{
					if (this.getBlockOpacity(x, y - 1, z) != 0)
					{
						chunk.heightMap[z << 4 | x] = y;

						if (y < chunk.heightMapMinimum)
						{
							chunk.heightMapMinimum = y;
						}

						break;
					}
				}

				if (world.provider.hasSkyLight())
				{
					int light = 15;

					int y2 = maxY + 16 - 1;

					do
					{
						int opacity = this.getBlockOpacity(x, y2, z);

						if (opacity == 0 && light != 15)
						{
							opacity = 1;
						}

						light -= opacity;

						if (light > 0)
						{
							ExtendedBlockStorage extendedblockstorage = chunk.getBlockStorageArray()[y2 >> 4];

							if (extendedblockstorage != Chunk.NULL_BLOCK_STORAGE)
							{
								extendedblockstorage.setSkyLight(x, y2 & 15, z, light);
							}
						}

						--y2;
					}
					while (y2 > 0 && light > 0);
				}
			}
		}

		chunk.markDirty();
	}

	public void addEntity(Entity entity)
	{
		this.entities.add(entity);
	}

	public int getChunkX()
	{
		return this.chunkX;
	}

	public int getChunkZ()
	{
		return this.chunkZ;
	}

	private static class BlockStateCacher
	{
		private final int[] cache;

		private BlockStateContainer container;

		public BlockStateCacher(IChunkMaskTransformer transformer)
		{
			this.cache = new int[transformer.getBlockCount()];
		}

		public void update(BlockStateContainer container)
		{
			this.container = container;

			this.reset();
		}

		public int getValue(IChunkMaskTransformer transformer, int index)
		{
			if (this.container == null)
			{
				throw new IllegalStateException("Not yet initialized");
			}

			int state = this.cache[index];

			if (state < 0)
			{
				int bits = this.container.bits;

				state = this.container.palette.idFor(transformer.getBlockState(index));

				if (bits != this.container.bits)
				{
					this.reset();
				}

				this.cache[index] = state;
			}

			return state;
		}

		private void reset()
		{
			Arrays.fill(this.cache, -1);
		}
	}

	private static class SegmentStorage
	{
		private final ExtendedBlockStorage blockStorage;

		private final byte[] opacity = new byte[16 * 16 * 16];

		public SegmentStorage(int y, boolean storeSkylight)
		{
			this.blockStorage = new ExtendedBlockStorage(y, storeSkylight);
		}
	}
}
