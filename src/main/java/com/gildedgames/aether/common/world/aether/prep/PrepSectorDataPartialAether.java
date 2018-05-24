package com.gildedgames.aether.common.world.aether.prep;

import com.gildedgames.aether.api.world.islands.IIslandDataPartial;
import com.gildedgames.aether.common.world.aether.island.data.IslandDataPartial;
import com.gildedgames.orbis_api.preparation.IPrepSector;
import com.gildedgames.orbis_api.preparation.IPrepSectorData;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class PrepSectorDataPartialAether implements IPrepSectorData
{
	private boolean dirty;

	private IPrepSector parent;

	private World world;

	private int sectorX, sectorY;

	private long seed;

	private IIslandDataPartial islandData;

	public PrepSectorDataPartialAether(World world, NBTTagCompound tag)
	{
		this.world = world;
		this.read(tag);
	}

	public IIslandDataPartial getIslandData()
	{
		return this.islandData;
	}

	@Override
	public IPrepSector setParent(IPrepSector iPrepSector)
	{
		this.parent = iPrepSector;

		return this.parent;
	}

	@Override
	public IPrepSector getParent()
	{
		return this.parent;
	}

	@Override
	public boolean isDirty()
	{
		return this.dirty;
	}

	@Override
	public void markDirty()
	{
		this.dirty = true;
	}

	@Override
	public void markClean()
	{
		this.dirty = false;
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

		this.islandData = new IslandDataPartial(this.world, this, tag.getCompoundTag("island"));
	}
}
