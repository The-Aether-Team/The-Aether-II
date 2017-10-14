package com.gildedgames.orbis.common.data;

import com.gildedgames.aether.api.io.NBTFunnel;
import com.gildedgames.aether.api.orbis.region.IDimensions;
import com.gildedgames.aether.api.orbis.region.IRegion;
import com.gildedgames.aether.api.orbis.shapes.IShape;
import com.gildedgames.aether.api.orbis.util.OrbisRotation;
import com.gildedgames.aether.api.util.NBT;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.orbis.common.block.BlockData;
import com.gildedgames.orbis.common.block.BlockDataContainer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
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

		funnel.set("dataContainer", this.dataContainer);
	}

	@Override
	public void read(final NBTTagCompound tag)
	{
		final NBTFunnel funnel = AetherCore.io().createFunnel(tag);

		this.dataContainer = funnel.get(this.world, "dataContainer");
	}

	public void fetchBlocksInside(final IShape shape, final World world, final OrbisRotation rotation)
	{
		//TODO: Test. May need reversed rotation or sth (East -> West, West -> East)
		//final IRegion rotated = RotationHelp.rotate(shape, rotation);
		final BlockDataContainer container = new BlockDataContainer(world, shape.getBoundingBox());

		final BlockPos min = shape.getBoundingBox().getMin();

		for (final BlockPos pos : shape.createShapeData(world))//RotationHelp.getAllInRegionRotated(region, rotation))
		{
			final BlockData blockData = new BlockData().getDataFrom(pos, world);

			final BlockPos translated = pos.add(-min.getX(), -min.getY(), -min.getZ());

			container.set(blockData, translated);
		}

		this.dataContainer = container;
	}
}
