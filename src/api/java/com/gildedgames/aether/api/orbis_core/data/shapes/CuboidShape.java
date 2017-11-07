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

public class CuboidShape extends AbstractShape
{

	private BlockPos start;

	private BlockPos end;

	private boolean centered;

	private Iterable<BlockPos.MutableBlockPos> data;

	private BlockPos renderMin, renderMax;

	private CuboidShape(final World world)
	{
		super(world);
	}

	public CuboidShape(final BlockPos start, final BlockPos end, final boolean centered)
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
		return this.getBoundingBox().getMin();
	}

	@Override
	public BlockPos getRenderBoxMax()
	{
		return this.getBoundingBox().getMax();
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
		return new CuboidShape(this.start.add(x, y, z), this.end.add(x, y, z), this.centered);
	}

	@Override
	public IShape translate(final BlockPos pos)
	{
		return this.translate(pos.getX(), pos.getY(), pos.getZ());
	}

	@Override
	public boolean contains(final int x, final int y, final int z)
	{
		if (this.createFromCenter() || !this.isUniform())
		{
			return RegionHelp.contains(this.getBoundingBox(), x, y, z);
		}
		else
		{
			final int minSize = Math.min(this.getBoundingBox().getWidth(), this.getBoundingBox().getLength());

			final int xDif = Math.abs(x - this.start.getX());
			final int zDif = Math.abs(z - this.start.getZ());

			return RegionHelp.contains(this.getBoundingBox(), x, y, z) && xDif < minSize && zDif < minSize;
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
