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
		else //if (!this.isUniform())
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
		// TODO: Uniform spheres
		/*else
		{
			final int width = this.getBoundingBox().getWidth();
			final int height = this.getBoundingBox().getHeight();
			final int length = this.getBoundingBox().getLength();

			final boolean useWidth = width < length && width < height;
			final boolean useHeight = height < length && height < width;
			final boolean useLength = length < height && length < width;

			final int dif = Math.abs(width - length);

			final int xDif = useHeight ? Math.abs(height - width) : dif;
			final int zDif = useHeight ? Math.abs(height - length) : dif;

			final int yDif = Math.abs(height - (useWidth ? width : length));

			final int xMod = this.end.getX() < this.start.getX() ? 1 : -1;
			final int zMod = this.end.getZ() < this.start.getZ() ? 1 : -1;

			final BlockPos newEnd = this.end.add(useWidth ? 0 : xDif * xMod, useHeight ? 0 : -yDif, useLength ? 0 : zDif * zMod);

			final BlockPos center = RegionHelp.getCenter(new Region(this.start.add(-xMod, 1, -zMod), newEnd.add(0, -1, 0)));

			final int radiusSq = (int) center.distanceSq(newEnd);

			final double distSq = center.distanceSq(x, y, z);

			return distSq < radiusSq;
		}*/
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
