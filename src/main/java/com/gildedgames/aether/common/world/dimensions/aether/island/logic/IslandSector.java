package com.gildedgames.aether.common.world.dimensions.aether.island.logic;

import com.gildedgames.aether.api.util.NBT;
import com.gildedgames.aether.common.util.io.NBTHelper;
import com.google.common.collect.Lists;
import net.minecraft.nbt.NBTTagCompound;

import java.util.List;

public class IslandSector implements NBT
{

	public final static int CHUNK_WIDTH_PER_SECTOR = 40;

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

	public List<IslandData> getIslandDataAtBlockPos(int x, int y)
	{
		List<IslandData> data = Lists.newArrayList();

		for (IslandData island : this.data)
		{
			if (island != null && island.getBounds().intersects(x, y, 1, 1) && !data.contains(island))
			{
				data.add(island);
			}
		}

		return data;
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
