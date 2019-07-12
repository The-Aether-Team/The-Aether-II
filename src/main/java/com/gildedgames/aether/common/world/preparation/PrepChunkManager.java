package com.gildedgames.aether.common.world.preparation;

import com.gildedgames.aether.api.world.preparation.*;
import com.gildedgames.aether.common.world.preparation.mask.ChunkMask;
import com.gildedgames.orbis.lib.util.ChunkMap;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class PrepChunkManager<T extends IChunkInfo> implements IPrepChunkManager<T>
{
	private World world;

	private IPrepRegistryEntry<T> registryEntry;

	private final ChunkMap<ChunkMask> chunkCache = new ChunkMap<>();

	public PrepChunkManager()
	{

	}

	public PrepChunkManager(World world, IPrepRegistryEntry<T> registryEntry)
	{
		this.world = world;
		this.registryEntry = registryEntry;
	}

	@Override
	public World getWorld()
	{
		return this.world;
	}

	@Nullable
	@Override
	public ChunkMask getChunk(IPrepSectorData sectorData, int chunkX, int chunkZ)
	{
		ChunkMask mask = this.chunkCache.get(chunkX, chunkZ);

		if (mask != null)
		{
			return mask;
		}

		mask = new ChunkMask(chunkX, chunkZ);

		T info = this.registryEntry.generateChunkColumnInfo(this.world, sectorData, chunkX, chunkZ);

		this.registryEntry.threadSafeGenerateMask(info, this.world, sectorData, mask, chunkX, chunkZ);

		this.chunkCache.put(chunkX, chunkZ, mask);

		return mask;
	}

	@Override
	public IChunkMaskTransformer createMaskTransformer()
	{
		return this.registryEntry.createMaskTransformer();
	}

	public static class Storage implements Capability.IStorage<IPrepChunkManager>
	{
		@Nullable
		@Override
		public INBT writeNBT(final Capability<IPrepChunkManager> capability, final IPrepChunkManager instance, final Direction side)
		{
			return null;
		}

		@Override
		public void readNBT(final Capability<IPrepChunkManager> capability, final IPrepChunkManager instance, final Direction side, final INBT nbt)
		{

		}
	}
}
