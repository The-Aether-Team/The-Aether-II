package com.gildedgames.aether.common.world.aether;

import com.gildedgames.aether.api.world.islands.IIslandData;
import com.gildedgames.aether.api.world.preparation.IPrepSectorData;
import com.gildedgames.aether.common.world.island.IslandData;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.World;

public class PrepSectorDataAether implements IPrepSectorData
{
	private boolean dirty;

	private final World world;

	private int sectorX, sectorY;

	private long seed;

	private IIslandData islandData;

	public PrepSectorDataAether(World world, int sectorX, int sectorY)
	{
		this.world = world;
		this.sectorX = sectorX;
		this.sectorY = sectorY;
	}

	public PrepSectorDataAether(World world, CompoundNBT tag)
	{
		this.world = world;

		this.read(tag);
	}

	public IIslandData getIslandData()
	{
		return this.islandData;
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
	public void write(CompoundNBT tag)
	{
		tag.putInt("x", this.sectorX);
		tag.putInt("y", this.sectorY);
		tag.putLong("s", this.seed);

		CompoundNBT islandTag = new CompoundNBT();

		this.islandData.write(islandTag);

		tag.put("island", islandTag);
	}

	@Override
	public void read(CompoundNBT tag)
	{
		this.sectorX = tag.getInt("x");
		this.sectorY = tag.getInt("y");
		this.seed = tag.getLong("s");

		this.islandData = new IslandData(this, tag.getCompound("island"));
	}

	public void setIslandData(IIslandData island)
	{
		this.islandData = island;
	}

	public void tick()
	{
		this.islandData.tick();
	}
}
