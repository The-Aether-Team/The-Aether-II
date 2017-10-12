package com.gildedgames.orbis.common.data.shapes;

import com.gildedgames.aether.api.io.NBTFunnel;
import com.gildedgames.aether.api.orbis.region.IRegion;
import com.gildedgames.aether.api.orbis.region.Region;
import com.gildedgames.aether.api.orbis.shapes.IShape;
import com.gildedgames.aether.api.orbis.util.OrbisRotation;
import com.gildedgames.aether.common.AetherCore;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EllipsoidShape extends AbstractShape
{

	private BlockPos center;

	private int radiusX, radiusY, radiusZ;

	private Iterable<BlockPos.MutableBlockPos> data;

	private EllipsoidShape(final World world)
	{
		super(world);
	}

	public EllipsoidShape(final BlockPos center, final int radiusX, final int radiusY, final int radiusZ)
	{
		super((IRegion) new Region(new BlockPos(-radiusX, -radiusY, -radiusZ), new BlockPos(radiusX, radiusY, radiusZ)).translate(center));
		this.center = center;

		this.radiusX = radiusX;
		this.radiusY = radiusY;
		this.radiusZ = radiusZ;
	}

	@Override
	public void writeShape(final NBTTagCompound tag)
	{
		final NBTFunnel funnel = AetherCore.io().createFunnel(tag);

		tag.setInteger("radiusX", this.radiusX);
		tag.setInteger("radiusY", this.radiusY);
		tag.setInteger("radiusZ", this.radiusZ);

		funnel.setPos("center", this.center);
	}

	@Override
	public void readShape(final NBTTagCompound tag)
	{
		final NBTFunnel funnel = AetherCore.io().createFunnel(tag);

		this.radiusX = tag.getInteger("radiusX");
		this.radiusY = tag.getInteger("radiusY");
		this.radiusZ = tag.getInteger("radiusZ");

		this.center = funnel.getPos("center");
	}

	@Override
	public IShape rotate(final OrbisRotation rotation, final IRegion in)
	{
		return this;
	}

	@Override
	public IShape translate(final int x, final int y, final int z)
	{
		return new EllipsoidShape(this.center.add(x, y, z), this.radiusX, this.radiusY, this.radiusZ);
	}

	@Override
	public IShape translate(final BlockPos pos)
	{
		return this.translate(pos.getX(), pos.getY(), pos.getZ());
	}

	@Override
	public boolean contains(final int x, final int y, final int z)
	{
		if (this.radiusX == 0 || this.radiusY == 0 || this.radiusZ == 0)
		{
			return false;
		}

		final BlockPos point = this.center.add(-x, -y, -z);

		final double squareX = point.getX() * (1.0D / this.radiusX);
		final double squareY = point.getY() * (1.0D / this.radiusY);
		final double squareZ = point.getZ() * (1.0D / this.radiusZ);

		final double dist = Math.sqrt(new BlockPos(0, 0, 0).distanceSq(squareX, squareY, squareZ));

		return dist < 1;
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
			this.data = this.createShapeData(null);
		}

		return this.data;
	}

}
