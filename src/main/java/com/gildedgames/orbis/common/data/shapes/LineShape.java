package com.gildedgames.orbis.common.data.shapes;

import com.gildedgames.aether.api.io.NBTFunnel;
import com.gildedgames.aether.api.orbis.region.IRegion;
import com.gildedgames.aether.api.orbis.region.Region;
import com.gildedgames.aether.api.orbis.shapes.IShape;
import com.gildedgames.aether.api.orbis.util.RotationHelp;
import com.gildedgames.aether.common.AetherCore;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class LineShape implements IShape
{
	private World world;

	private BlockPos start;

	private BlockPos end;

	private int lineRadius;

	private Iterable<BlockPos.MutableBlockPos> data;

	private LineShape(final World world)
	{
		this.world = world;
	}

	public LineShape(final BlockPos start, final BlockPos end)
	{
		this(start, end, 2);
	}

	public LineShape(final BlockPos start, final BlockPos end, final int lineRadius)
	{
		this.start = start;
		this.end = end;

		this.lineRadius = lineRadius;
	}

	@Override
	public Iterable<BlockPos.MutableBlockPos> createShapeData()
	{
		final List<BlockPos.MutableBlockPos> lineData = new ArrayList<>();

		for (int lineX = -this.lineRadius + 1; lineX < this.lineRadius - (this.lineRadius == 1 ? 0 : 1); lineX++)
		{
			for (int lineY = -this.lineRadius + 1; lineY < this.lineRadius - (this.lineRadius == 1 ? 0 : 1); lineY++)
			{
				for (int lineZ = -this.lineRadius + 1; lineZ < this.lineRadius - (this.lineRadius == 1 ? 0 : 1); lineZ++)
				{
					BlockPos start = this.start.add(lineX, lineY, lineZ);
					BlockPos end = this.end.add(lineX, lineY, lineZ);

					final boolean steepXY = Math.abs(end.getY() - start.getY()) > Math.abs(end.getX() - start.getX());

					if (steepXY)
					{
						int tempY = start.getY();

						start = new BlockPos(tempY, start.getX(), start.getZ());

						tempY = end.getY();

						end = new BlockPos(tempY, end.getX(), end.getZ());
					}

					final boolean steepXZ = Math.abs(end.getZ() - start.getZ()) > Math.abs(end.getX() - start.getX());

					if (steepXZ)
					{
						int tempZ = start.getZ();

						start = new BlockPos(tempZ, start.getY(), start.getX());

						tempZ = end.getZ();

						end = new BlockPos(tempZ, end.getY(), end.getX());
					}

					final int deltaX = Math.abs(end.getX() - start.getX());
					final int deltaY = Math.abs(end.getY() - start.getY());
					final int deltaZ = Math.abs(end.getZ() - start.getZ());

					int errorXY = deltaX / 2;
					int errorXZ = deltaX / 2;

					final int stepX = start.getX() > end.getX() ? -1 : 1;
					final int stepY = start.getY() > end.getY() ? -1 : 1;
					final int stepZ = start.getZ() > end.getZ() ? -1 : 1;

					int z = start.getZ();
					int y = start.getY();

					int lineSegments = 0;

					for (int x = start.getX(); x != end.getX(); x += stepX)
					{
						int xCopy = x, yCopy = y, zCopy = z;

						if (steepXZ)
						{
							final int tempZ = zCopy;

							zCopy = xCopy;
							xCopy = tempZ;
						}

						if (steepXY)
						{
							final int tempY = yCopy;

							yCopy = xCopy;
							xCopy = tempY;
						}

						for (int x1 = 0; x1 != stepX; x1 += stepX)
						{
							for (int z1 = 0; z1 != stepZ; z1 += stepZ)
							{
								for (int y1 = 0; y1 != stepY; y1 += stepY)
								{
									lineData.add(new BlockPos.MutableBlockPos(xCopy + x1, yCopy + y1, zCopy + z1));
								}
							}
						}

						lineSegments++;

						if (lineSegments % 5 == 0)
						{
							//size = Math.max(1, size - 1);
						}

						errorXY -= deltaY;
						errorXZ -= deltaZ;

						if (errorXY < 0)
						{
							y += stepY;
							errorXY += deltaX;
						}

						if (errorXZ < 0)
						{
							z += stepZ;
							errorXZ += deltaX;
						}
					}
				}
			}
		}

		return lineData;
	}

	@Override
	public void write(final NBTTagCompound tag)
	{
		final NBTFunnel funnel = AetherCore.io().createFunnel(tag);

		funnel.setPos("start", this.start);
		funnel.setPos("end", this.end);

		tag.setInteger("lineRadius", this.lineRadius);
	}

	@Override
	public void read(final NBTTagCompound tag)
	{
		final NBTFunnel funnel = AetherCore.io().createFunnel(tag);

		this.start = funnel.getPos("start");
		this.end = funnel.getPos("end");

		this.lineRadius = tag.getInteger("lineRadius");
	}

	@Override
	public IShape rotate(final Rotation rotation, final IRegion regionIn)
	{
		final BlockPos newStart = RotationHelp.rotate(this.start, regionIn, rotation);
		final BlockPos newEnd = RotationHelp.rotate(this.end, regionIn, rotation);

		return new LineShape(newStart, newEnd, this.lineRadius);
	}

	@Override
	public IShape translate(final int x, final int y, final int z)
	{
		return new LineShape(this.start.add(x, y, z), this.end.add(x, y, z), this.lineRadius);
	}

	@Override
	public IShape translate(final BlockPos pos)
	{
		return this.translate(pos.getX(), pos.getY(), pos.getZ());
	}

	@Override
	public IRegion getBoundingBox()
	{
		return new Region(this.start, this.end);
	}

	@Override
	public boolean contains(final int x, final int y, final int z)
	{
		boolean flag = false;

		for (final BlockPos.MutableBlockPos pos : this.getShapeData())
		{
			if (pos.getX() == x && pos.getY() == y && pos.getZ() == z)
			{
				flag = true;
				break;
			}
		}

		return flag;
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
