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

public class SphereShape extends AbstractShape
{

	private BlockPos center;

	private int radiusSq;

	private Iterable<BlockPos.MutableBlockPos> data;

	private SphereShape(final World world)
	{
		super(world);
	}

	public SphereShape(final BlockPos center, final int radius)
	{
		super((IRegion) new Region(new BlockPos(-radius, -radius, -radius), new BlockPos(radius, radius, radius)).translate(center));
		this.center = center;

		this.radiusSq = radius * radius;
	}

	@Override
	public void writeShape(final NBTTagCompound tag)
	{
		final NBTFunnel funnel = OrbisCore.io().createFunnel(tag);

		tag.setInteger("radiusSq", this.radiusSq);
		funnel.setPos("center", this.center);
	}

	@Override
	public void readShape(final NBTTagCompound tag)
	{
		final NBTFunnel funnel = OrbisCore.io().createFunnel(tag);

		this.radiusSq = tag.getInteger("radiusSq");
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
		return new SphereShape(this.center.add(x, y, z), (int) Math.sqrt(this.radiusSq));
	}

	@Override
	public IShape translate(final BlockPos pos)
	{
		return this.translate(pos.getX(), pos.getY(), pos.getZ());
	}

	@Override
	public boolean contains(final int x, final int y, final int z)
	{
		final double distSq = this.center.distanceSq(x, y, z);

		return distSq < this.radiusSq;
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
