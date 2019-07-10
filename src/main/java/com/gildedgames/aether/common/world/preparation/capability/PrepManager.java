package com.gildedgames.aether.common.world.preparation.capability;

import com.gildedgames.aether.api.world.preparation.IPrepManager;
import com.gildedgames.aether.api.world.preparation.IPrepRegistryEntry;
import com.gildedgames.aether.api.world.preparation.IPrepSectorAccess;
import com.gildedgames.aether.api.world.preparation.IPrepSectorData;
import com.gildedgames.aether.common.world.preparation.PrepSectorAccessClientImpl;
import com.gildedgames.aether.common.world.preparation.PrepSectorAccessServerImpl;
import com.gildedgames.orbis.lib.OrbisLib;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class PrepManager implements IPrepManager
{
	private final World world;

	private final IPrepRegistryEntry registry;

	private IPrepSectorAccess access;

	public PrepManager()
	{
		this.world = null;
		this.registry = null;
	}

	public PrepManager(World world, IPrepRegistryEntry registry)
	{
		this.world = world;
		this.registry = registry;

		if (world.isRemote)
		{
			this.access = new PrepSectorAccessClientImpl(this.world, this.registry);
		}
		else
		{
			this.access = new PrepSectorAccessServerImpl(this.world, this.registry, this, OrbisLib.services().getWorldDataManager(world));
		}
	}

	@Override
	public IPrepRegistryEntry getRegistryEntry()
	{
		return this.registry;
	}

	@Override
	public IPrepSectorAccess getAccess()
	{
		return this.access;
	}

	@Override
	public IPrepSectorData createSector(int sectorX, int sectorZ)
	{
		long seed = this.world.getSeed() ^ ((long) sectorX * 341873128712L + (long) sectorZ * 132897987541L);

		return this.registry.createData(this.world, seed, sectorX, sectorZ);
	}

	@Override
	public void decorateSectorData(IPrepSectorData data)
	{
		this.registry.postSectorDataCreate(this.world, data);
	}

	public static class Storage implements Capability.IStorage<IPrepManager>
	{
		@Nullable
		@Override
		public NBTBase writeNBT(final Capability<IPrepManager> capability, final IPrepManager instance, final EnumFacing side)
		{
			return null;
		}

		@Override
		public void readNBT(final Capability<IPrepManager> capability, final IPrepManager instance, final EnumFacing side, final NBTBase nbt)
		{

		}
	}
}
