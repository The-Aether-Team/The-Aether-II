package com.gildedgames.aether.common.world.dimensions.aether.island.logic;

import com.gildedgames.util.core.nbt.NBTHelper;
import com.gildedgames.util.io_manager.io.NBT;
import net.minecraft.nbt.NBTTagCompound;

public class IslandSector implements NBT
{

	public final static int CHUNK_WIDTH_PER_SECTOR = 20;

	private int sectorX, sectorY;

	private long seed;

	private IslandData[] data;

	private IslandSector()
	{

	}

	public IslandSector(int sectorX, int sectorY, long seed, IslandData... data)
	{
		this.sectorX = sectorX;
		this.sectorY = sectorY;

		this.seed = seed;

		this.data = data;
	}

	public IslandData[] getAllIslandData()
	{
		return this.data;
	}

	public IslandData getIslandDataAtBlockPos(int x, int y)
	{
		for (IslandData data : this.data)
		{
			if (data != null && data.getBounds().intersects(x, y, 1, 1))
			{
				return data;
			}
		}

		return null;
	}

	public int getSectorX()
	{
		return this.sectorX;
	}

	public int getSectorY()
	{
		return this.sectorY;
	}

	public long getSeed()
	{
		return this.seed;
	}

	@Override
	public void write(NBTTagCompound tag)
	{
		tag.setInteger("x", this.getSectorX());
		tag.setInteger("y", this.getSectorY());

		tag.setLong("s", this.getSeed());

		NBTHelper.fullySerializeArray("d", this.data, tag);
	}

	@Override
	public void read(NBTTagCompound tag)
	{
		this.sectorX = tag.getInteger("x");
		this.sectorY = tag.getInteger("y");

		this.seed = tag.getLong("s");

		this.data = NBTHelper.fullyDeserializeArray("d", IslandData[].class, tag);
	}
}
