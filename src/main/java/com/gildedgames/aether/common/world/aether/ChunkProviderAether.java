package com.gildedgames.aether.common.world.aether;

import com.gildedgames.aether.api.world.islands.IIslandData;
import com.gildedgames.aether.common.util.helpers.IslandHelper;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.EmptyChunk;
import net.minecraft.world.gen.ChunkProviderServer;

import javax.annotation.Nullable;

public class ChunkProviderAether extends ChunkProviderServer
{
	private ChunkProviderServer original;

	private EmptyChunk EMPTY = new EmptyChunk(this.world, 0, 0);

	public ChunkProviderAether(WorldServer world, ChunkProviderServer original)
	{
		super(world, original.chunkLoader, original.chunkGenerator);

		this.original = original;
	}

	@Override
	public void queueUnload(Chunk chunkIn)
	{
		if (chunkIn != null)
		{
			IIslandData island = IslandHelper.get(this.world, chunkIn.x, chunkIn.z);

			if (island != null)
			{
				this.original.queueUnload(chunkIn);
			}
		}
	}

	@Override
	public Chunk provideChunk(int x, int z)
	{
		IIslandData island = IslandHelper.get(this.world, x, z);

		if (island == null)
		{
			return this.EMPTY;
		}

		return this.original.provideChunk(x, z);
	}

	@Nullable
	@Override
	public Chunk getLoadedChunk(int x, int z)
	{
		IIslandData island = IslandHelper.get(this.world, x, z);

		if (island == null)
		{
			return null;
		}

		return this.original.getLoadedChunk(x, z);
	}

	/**
	 * Unloads chunks that are marked to be unloaded. This is not guaranteed to unload every such chunk.
	 */
	@Override
	public boolean tick()
	{
		if (this.id2ChunkMap.isEmpty())
		{
			return false;
		}

		return this.original.tick();
	}

	/**
	 * Converts the instance data to a readable string.
	 */
	@Override
	public String makeString()
	{
		return this.original.makeString();
	}

	@Override
	public boolean isChunkGeneratedAt(int x, int z)
	{
		IIslandData island = IslandHelper.get(this.world, x, z);

		if (island == null)
		{
			return false;
		}

		return this.original.isChunkGeneratedAt(x, z);
	}

	@Nullable
	@Override
	public Chunk loadChunk(int x, int z)
	{
		return this.loadChunk(x, z, null);
	}

	@Nullable
	@Override
	public Chunk loadChunk(int x, int z, @Nullable Runnable runnable)
	{
		IIslandData island = IslandHelper.get(this.world, x, z);

		if (island == null)
		{
			return null;
		}

		return this.original.loadChunk(x, z, runnable);
	}

	@Override
	public boolean chunkExists(int x, int z)
	{
		IIslandData island = IslandHelper.get(this.world, x, z);

		if (island == null)
		{
			return false;
		}

		return this.original.chunkExists(x, z);
	}
}
