package com.gildedgames.orbis.common.data;

import com.gildedgames.aether.api.orbis.region.IDimensions;
import com.gildedgames.aether.api.orbis.region.IRegion;
import com.gildedgames.aether.api.util.NBT;
import com.gildedgames.orbis.common.block.BlockDataContainer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class BlueprintData implements IDimensions, NBT
{
	private final BlockDataContainer dataContainer;

	private World world;

	private BlueprintData(World world)
	{
		this.world = world;
		this.dataContainer = null;
	}

	public BlueprintData(final World world, final IRegion region)
	{
		this.dataContainer = new BlockDataContainer(world, region);
	}

	@Override
	public int getWidth()
	{
		return this.dataContainer.getWidth();
	}

	@Override
	public int getHeight()
	{
		return this.dataContainer.getHeight();
	}

	@Override
	public int getLength()
	{
		return this.dataContainer.getLength();
	}

	@Override
	public void write(final NBTTagCompound tag)
	{

	}

	@Override
	public void read(final NBTTagCompound tag)
	{

	}
}
