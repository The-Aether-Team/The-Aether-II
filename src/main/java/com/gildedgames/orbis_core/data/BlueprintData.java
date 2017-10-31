package com.gildedgames.orbis_core.data;

import com.gildedgames.aether.api.io.NBTFunnel;
import com.gildedgames.aether.api.orbis.IDimensions;
import com.gildedgames.aether.api.orbis.IRegion;
import com.gildedgames.aether.api.orbis.IShape;
import com.gildedgames.aether.api.util.NBT;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.orbis.common.world_object.IWorldObject;
import com.gildedgames.orbis_core.block.BlockData;
import com.gildedgames.orbis_core.block.BlockDataContainer;
import com.gildedgames.orbis_core.data.management.IData;
import com.gildedgames.orbis_core.data.management.IDataMetadata;
import com.gildedgames.orbis_core.data.management.impl.DataMetadata;
import com.gildedgames.orbis_core.data.region.IRotateable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlueprintData implements IDimensions, NBT, IData
{
	private IDataMetadata metadata;

	private BlockDataContainer dataContainer;

	private BlueprintData()
	{
		this.metadata = new DataMetadata();
	}

	public BlueprintData(final IRegion region)
	{
		this.metadata = new DataMetadata();
		this.dataContainer = new BlockDataContainer(region);
	}

	public BlueprintData(final BlockDataContainer container)
	{
		this();
		this.dataContainer = container;
	}

	public BlockDataContainer getBlockDataContainer()
	{
		return this.dataContainer;
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
		funnel.set("metadata", this.metadata);
	}

	@Override
	public void read(final NBTTagCompound tag)
	{
		final NBTFunnel funnel = AetherCore.io().createFunnel(tag);

		this.dataContainer = funnel.get("dataContainer");
		this.metadata = funnel.get("metadata");
	}

	public void fetchBlocksInside(final IShape shape, final World world, final Rotation rotation)
	{
		final BlockDataContainer container = new BlockDataContainer(shape.getBoundingBox());

		final BlockPos min = shape.getBoundingBox().getMin();

		for (final BlockPos pos : shape.createShapeData())
		{
			final BlockData blockData = new BlockData().getDataFrom(pos, world);

			final BlockPos translated = pos.add(-min.getX(), -min.getY(), -min.getZ());

			container.set(blockData, translated);
		}

		this.dataContainer = container;
	}

	@Override
	public void preSaveToDisk(final IWorldObject object)
	{
		if (object instanceof IShape)
		{
			final IShape shape = (IShape) object;
			Rotation rotation = Rotation.NONE;

			if (object instanceof IRotateable)
			{
				final IRotateable rotateable = (IRotateable) object;

				rotation = rotateable.getRotation();
			}

			this.fetchBlocksInside(shape, object.getWorld(), rotation);
		}
	}

	@Override
	public String getFileExtension()
	{
		return "blueprint";
	}

	@Override
	public IDataMetadata getMetadata()
	{
		return this.metadata;
	}

	@Override
	public IData clone()
	{
		final BlueprintData data = new BlueprintData();

		final NBTTagCompound tag = new NBTTagCompound();

		this.write(tag);

		data.read(tag);

		return data;
	}
}
