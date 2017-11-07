package com.gildedgames.aether.api.orbis_core.data.shapes;

import com.gildedgames.aether.api.io.NBTFunnel;
import com.gildedgames.aether.api.orbis.IRegion;
import com.gildedgames.aether.api.orbis.IShape;
import com.gildedgames.aether.api.orbis_core.OrbisCore;
import com.gildedgames.aether.api.orbis_core.data.region.Region;
import com.gildedgames.aether.api.orbis_core.util.RegionHelp;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SphereShape extends AbstractShape
{

	private BlockPos start;

	private BlockPos end;

	private boolean centered;

	private Iterable<BlockPos.MutableBlockPos> data;

	private BlockPos renderMin, renderMax;

	private SphereShape(final World world)
	{
		super(world);
	}

	public SphereShape(final BlockPos start, final BlockPos end, final boolean centered)
	{
		this.start = start;
		this.end = end;

		final int radius = (int) Math.sqrt(this.start.distanceSq(this.end));

		this.setBoundingBox(centered ?
				new Region(new BlockPos(-radius, -radius, -radius).add(this.start), new BlockPos(radius, radius, radius).add(this.start)) :
				new Region(start, end));

		this.centered = centered;

		this.renderMin = this.getBoundingBox().getMin();
		this.renderMax = this.getBoundingBox().getMax();
	}

	@Override
	public BlockPos getRenderBoxMin()
	{
		return this.renderMin;
	}

	@Override
	public BlockPos getRenderBoxMax()
	{
		return this.renderMax;
	}

	@Override
	public void writeShape(final NBTTagCompound tag)
	{
		final NBTFunnel funnel = OrbisCore.io().createFunnel(tag);

		funnel.setPos("start", this.start);
		funnel.setPos("end", this.end);

		tag.setBoolean("centered", this.centered);
	}

	@Override
	public void readShape(final NBTTagCompound tag)
	{
		final NBTFunnel funnel = OrbisCore.io().createFunnel(tag);

		this.start = funnel.getPos("start");
		this.end = funnel.getPos("end");

		this.centered = tag.getBoolean("centered");
	}

	@Override
	public IShape rotate(final Rotation rotation, final IRegion in)
	{
		return this;
	}

	@Override
	public IShape translate(final int x, final int y, final int z)
	{
		return new SphereShape(this.start.add(x, y, z), this.end.add(x, y, z), this.centered);
	}

	@Override
	public IShape translate(final BlockPos pos)
	{
		return this.translate(pos.getX(), pos.getY(), pos.getZ());
	}

	@Override
	public boolean contains(final int x, final int y, final int z)
	{
		if (this.createFromCenter())
		{
			final int radiusSq = (int) this.start.distanceSq(this.end);

			final double distSq = this.start.distanceSq(x, y, z);

			return distSq < radiusSq;
		}
		else if (!this.isUniform())
		{
			final BlockPos center = RegionHelp.getCenter(this.getBoundingBox());

			final BlockPos point = center.add(-x, -y, -z);

			final int radiusX = (this.getBoundingBox().getWidth() / 2);
			final int radiusY = (this.getBoundingBox().getHeight() / 2);
			final int radiusZ = (this.getBoundingBox().getLength() / 2);

			final double squareX = point.getX() * (1.0D / radiusX);
			final double squareY = point.getY() * (1.0D / radiusY);
			final double squareZ = point.getZ() * (1.0D / radiusZ);

			final double dist = Math.sqrt(new BlockPos(0, 0, 0).distanceSq(squareX, squareY, squareZ));

			return dist < 1;
		}
		else
		{
			final int size = Math.min(this.getBoundingBox().getWidth(), this.getBoundingBox().getLength());

			final int xDif = this.start.getX() + (this.start.getX() <= this.end.getX() ? size : -size);
			final int zDif = this.start.getZ() + (this.start.getZ() <= this.end.getZ() ? size : -size);

			final BlockPos newEnd = new BlockPos(xDif, Math.max(this.end.getY(), this.start.getY()), zDif);

			final BlockPos center = RegionHelp.getCenter(new Region(this.start, newEnd));

			final BlockPos point = center.add(-x, -y, -z);

			final int radius = (size / 2);

			final int radiusY = (this.getBoundingBox().getHeight() / 2);

			final double squareX = point.getX() * (1.0D / radius);
			final double squareY = point.getY() * (1.0D / radiusY);
			final double squareZ = point.getZ() * (1.0D / radius);

			final double dist = Math.sqrt(new BlockPos(0, 0, 0).distanceSq(squareX, squareY, squareZ));

			return dist < 1;
		}
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
