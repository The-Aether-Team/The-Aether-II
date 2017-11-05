package com.gildedgames.aether.api.orbis_core.data.shapes;

import com.gildedgames.aether.api.io.NBTFunnel;
import com.gildedgames.aether.api.orbis.IRegion;
import com.gildedgames.aether.api.orbis.IShape;
import com.gildedgames.aether.api.orbis_core.OrbisCore;
import com.gildedgames.aether.api.orbis_core.data.region.Region;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PyramidShape extends AbstractShape
{

	private BlockPos center;

	private int radius;

	private Iterable<BlockPos.MutableBlockPos> data;

	private PyramidShape(final World world)
	{
		super(world);
	}

	public PyramidShape(final BlockPos center, final int radius)
	{
		super((IRegion) new Region(new BlockPos(-radius, -radius, -radius), new BlockPos(radius, radius, radius)).translate(center));
		this.center = center;

		this.radius = radius;
	}

	@Override
	public void writeShape(final NBTTagCompound tag)
	{
		final NBTFunnel funnel = OrbisCore.io().createFunnel(tag);

		tag.setInteger("radius", this.radius);
		funnel.setPos("center", this.center);
	}

	@Override
	public void readShape(final NBTTagCompound tag)
	{
		final NBTFunnel funnel = OrbisCore.io().createFunnel(tag);

		this.radius = tag.getInteger("radius");
		this.center = funnel.getPos("center");
	}

	@Override
	public IShape rotate(final Rotation rotation, final IRegion in)
	{
		return this;
	}

	@Override
	public IShape translate(final int x, final int y, final int z)
	{
		return new PyramidShape(this.center.add(x, y, z), this.radius);
	}

	@Override
	public IShape translate(final BlockPos pos)
	{
		return this.translate(pos.getX(), pos.getY(), pos.getZ());
	}

	@Override
	public boolean contains(final int x, final int y, final int z)
	{
		final int width = this.radius - (y - this.center.getY());

		final int xDif = Math.abs(x - this.center.getX());
		final int zDif = Math.abs(z - this.center.getZ());

		if (xDif <= width && zDif <= width && y >= this.center.getY())
		{
			return true;
		}

		return false;
	}

	@Override
	public boolean contains(final BlockPos pos)
	{
		return this.contains(pos.getX(), pos.getY(), pos.getZ());
	}

	@Override
	public Iterable<BlockPos.MutableBlockPos> getShapeData()
	{
		if (this.data == null)
		{
			this.data = this.createShapeData();
		}

		return this.data;
	}

}
