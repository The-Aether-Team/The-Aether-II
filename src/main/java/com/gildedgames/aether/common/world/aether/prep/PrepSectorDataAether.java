package com.gildedgames.aether.common.world.aether.prep;

import com.gildedgames.aether.api.world.islands.IIslandData;
import com.gildedgames.aether.common.world.aether.island.data.IslandData;
import com.gildedgames.orbis_api.preparation.IPrepSector;
import com.gildedgames.orbis_api.preparation.IPrepSectorData;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class PrepSectorDataAether implements IPrepSectorData
{
	private IPrepSector parent;

	private World world;

	private int sectorX, sectorY;

	private long seed;

	private IIslandData islandData;

	public PrepSectorDataAether(World world, IIslandData islandData, int sectorX, int sectorY)
	{
		this.world = world;
		this.sectorX = sectorX;
		this.sectorY = sectorY;
		this.islandData = islandData;
	}

	public PrepSectorDataAether(World world, NBTTagCompound tag)
	{
		this.world = world;
		this.read(tag);
	}

	public IIslandData getIslandData()
	{
		return this.islandData;
	}

	@Override
	public IPrepSector setParent(IPrepSector iPrepSector)
	{
		return this.parent;
	}

	@Override
	public int getSectorX()
	{
		return this.sectorX;
	}

	@Override
	public int getSectorY()
	{
		return this.sectorY;
	}

	@Override
	public long getSeed()
	{
		return this.seed;
	}

	@Override
	public boolean shouldPrepareChunk(int i, int i1)
	{
		return true;
	}

	@Override
	public void write(NBTTagCompound tag)
	{
		tag.setInteger("x", this.sectorX);
		tag.setInteger("y", this.sectorY);
		tag.setLong("s", this.seed);

		NBTTagCompound islandTag = new NBTTagCompound();

		this.islandData.write(islandTag);

		tag.setTag("island", islandTag);
	}

	@Override
	public void read(NBTTagCompound tag)
	{
		this.sectorX = tag.getInteger("x");
		this.sectorY = tag.getInteger("y");
		this.seed = tag.getLong("s");

		this.islandData = new IslandData(this.world, tag.getCompoundTag("island"));
	}
}
