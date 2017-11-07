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

public class ConeShape extends AbstractShape
{

	private BlockPos start;

	private BlockPos end;

	private boolean centered;

	private Iterable<BlockPos.MutableBlockPos> data;

	private BlockPos renderMin, renderMax;

	private ConeShape(final World world)
	{
		super(world);
	}

	public ConeShape(final BlockPos start, final BlockPos end, final boolean centered)
	{
		this.start = start;
		this.end = end;

		final int radius = (int) Math.sqrt(this.start.distanceSq(this.end));

		this.setBoundingBox(centered ?
				new Region(new BlockPos(-radius, -radius, -radius).add(this.start), new BlockPos(radius, radius, radius).add(this.start)) :
				new Region(start, new BlockPos(end.getX(), Math.max(end.getY(), start.getY()), end.getZ())));

		this.centered = centered;

		this.renderMin = new BlockPos(this.getBoundingBox().getMin().getX(), this.start.getY(), this.getBoundingBox().getMin().getZ());
		this.renderMax = this.getBoundingBox().getMax();
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
	public IShape rotate(final Rotation rotation, final IRegion in)
	{
		return this;
	}

	@Override
	public IShape translate(final int x, final int y, final int z)
	{
		return new ConeShape(this.start.add(x, y, z), this.end.add(x, y, z), this.centered);
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
			final int radius = (int) Math.sqrt(this.start.distanceSq(this.end));

			final int radiusDif = 1 + radius - (y - this.start.getY());

			final int radiusClimb = radiusDif * radiusDif;

			final double dist = this.start.add(0, y - this.start.getY(), 0).distanceSq(x, y, z);

			return dist < radiusClimb && y >= this.start.getY();
		}
		else if (!this.isUniform())
		{
			final BlockPos center = RegionHelp.getCenter(this.getBoundingBox());

			final BlockPos point = center.add(-x, -y, -z);

			final int yDif = y - this.start.getY();

			final float percentDif = ((float) this.getBoundingBox().getHeight() - (float) yDif) / (float) this.getBoundingBox().getHeight();
			final float percent = 1.0F - percentDif;

			final int radiusX = 1 + (this.getBoundingBox().getWidth() / 2) - (int) (percent * this.getBoundingBox().getWidth() / 2.0F);
			final int radiusZ = 1 + (this.getBoundingBox().getLength() / 2) - (int) (percent * this.getBoundingBox().getLength() / 2.0F);

			final double squareX = point.getX() * (1.0D / radiusX);
			final double squareZ = point.getZ() * (1.0D / radiusZ);

			final double dist = Math.sqrt(new BlockPos(0, 0, 0).distanceSq(squareX, 0, squareZ));

			return dist < 1 && y >= this.start.getY();
		}
		else
		{
			final int size = Math.min(this.getBoundingBox().getWidth(), this.getBoundingBox().getLength());

			final int xDif = this.start.getX() + (this.start.getX() <= this.end.getX() ? size : -size);
			final int zDif = this.start.getZ() + (this.start.getZ() <= this.end.getZ() ? size : -size);

			final BlockPos newEnd = new BlockPos(xDif, this.end.getY(), zDif);

			final BlockPos center = RegionHelp.getCenter(new Region(this.start, newEnd));

			final BlockPos point = center.add(-x, -y, -z);

			final int yDif = y - this.start.getY();

			final float percentDif = ((float) this.getBoundingBox().getHeight() - (float) yDif) / (float) this.getBoundingBox().getHeight();
			final float percent = 1.0F - percentDif;

			final int radius = 1 + (size / 2) - (int) (percent * size / 2.0F);

			final double squareX = point.getX() * (1.0D / radius);
			final double squareZ = point.getZ() * (1.0D / radius);

			final double dist = Math.sqrt(new BlockPos(0, 0, 0).distanceSq(squareX, 0, squareZ));

			return dist < 1 && y >= this.start.getY();
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
