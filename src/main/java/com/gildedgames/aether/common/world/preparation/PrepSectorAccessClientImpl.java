package com.gildedgames.aether.common.world.preparation;

import com.gildedgames.aether.api.world.preparation.IPrepRegistryEntry;
import com.gildedgames.aether.api.world.preparation.IPrepSector;
import com.gildedgames.aether.api.world.preparation.IPrepSectorAccessClient;
import com.gildedgames.orbis.lib.util.ChunkMap;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import net.minecraft.world.World;

import java.util.Collection;
import java.util.Optional;

public class PrepSectorAccessClientImpl implements IPrepSectorAccessClient
{
	private final ChunkMap<IPrepSector> loaded = new ChunkMap<>();

	private final World world;

	private final IPrepRegistryEntry registry;

	public PrepSectorAccessClientImpl(World world, IPrepRegistryEntry registry)
	{
		this.world = world;
		this.registry = registry;
	}

	@Override
	public Optional<IPrepSector> getLoadedSector(int sectorX, int sectorZ)
	{
		return Optional.ofNullable(this.loaded.get(sectorX, sectorZ));
	}

	@Override
	public Optional<IPrepSector> getLoadedSectorForChunk(int chunkX, int chunkZ)
	{
		final int sectorX = Math.floorDiv(chunkX, this.registry.getSectorChunkArea());
		final int sectorZ = Math.floorDiv(chunkZ, this.registry.getSectorChunkArea());

		return this.getLoadedSector(sectorX, sectorZ);
	}

	@Override
	public ListenableFuture<IPrepSector> provideSector(int sectorX, int sectorZ, boolean background)
	{
		return Futures.immediateFuture(this.loaded.get(sectorX, sectorZ));
	}

	@Override
	public ListenableFuture<IPrepSector> provideSectorForChunk(int chunkX, int chunkZ, boolean background)
	{
		final int sectorX = Math.floorDiv(chunkX, this.registry.getSectorChunkArea());
		final int sectorZ = Math.floorDiv(chunkZ, this.registry.getSectorChunkArea());

		return this.provideSector(sectorX, sectorZ, background);
	}

	@Override
	public void onChunkLoaded(int chunkX, int chunkZ)
	{

	}

	@Override
	public void onChunkUnloaded(int chunkX, int chunkZ)
	{

	}

	@Override
	public void retainSector(IPrepSector sector)
	{

	}

	@Override
	public Collection<IPrepSector> getLoadedSectors()
	{
		return this.loaded.getValues();
	}

	@Override
	public void update()
	{

	}

	@Override
	public void addSector(IPrepSector sector)
	{
		this.loaded.put(sector.getData().getSectorX(), sector.getData().getSectorY(), sector);
	}

	@Override
	public void removeSector(IPrepSector sector)
	{
		this.loaded.remove(sector.getData().getSectorX(), sector.getData().getSectorY());
	}
}
