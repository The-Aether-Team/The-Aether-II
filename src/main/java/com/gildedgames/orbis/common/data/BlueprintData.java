package com.gildedgames.orbis.common.data;

import com.gildedgames.aether.api.io.NBTFunnel;
import com.gildedgames.aether.api.orbis.region.IDimensions;
import com.gildedgames.aether.api.orbis.region.IRegion;
import com.gildedgames.aether.api.util.NBT;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.orbis.common.block.BlockDataContainer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class BlueprintData implements IDimensions, NBT
{
	private BlockDataContainer dataContainer;

	private World world;

	private BlueprintData(final World world)
	{
		this.world = world;
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
		final NBTFunnel funnel = AetherCore.io().createFunnel(tag);

		tag.setInteger("width", this.getWidth());
		tag.setInteger("height", this.getHeight());
		tag.setInteger("length", this.getLength());
	}

	@Override
	public void read(final NBTTagCompound tag)
	{
		final NBTFunnel funnel = AetherCore.io().createFunnel(tag);

		final int width = tag.getInteger("width");
		final int height = tag.getInteger("height");
		final int length = tag.getInteger("length");

		this.dataContainer = new BlockDataContainer(this.world, width, height, length);
	}
}
