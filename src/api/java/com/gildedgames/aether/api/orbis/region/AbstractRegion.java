package com.gildedgames.aether.api.orbis.region;

import com.gildedgames.aether.api.orbis.shapes.IShape;
import com.gildedgames.aether.api.orbis.util.OrbisRotation;
import com.gildedgames.aether.api.orbis.util.RegionHelp;
import com.gildedgames.aether.api.orbis.util.RotationHelp;
import net.minecraft.util.math.BlockPos;

public abstract class AbstractRegion implements IRegion
{

	private Iterable<BlockPos.MutableBlockPos> data;

	private boolean dataChanged;

	@Override
	public IRegion getBoundingBox()
	{
		return this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterable<BlockPos.MutableBlockPos> createShapeData()
	{
		return BlockPos.getAllInBoxMutable(this.getMin(), this.getMax());
	}

	@Override
	public Iterable<BlockPos.MutableBlockPos> getShapeData()
	{
		if (this.data == null || this.dataChanged)
		{
			this.data = this.createShapeData();
		}

		return this.data;
	}

	@Override
	public boolean contains(final BlockPos pos)
	{
		return this.contains(pos.getX(), pos.getY(), pos.getZ());
	}

	@Override
	public boolean contains(final int x, final int y, final int z)
	{
		return RegionHelp.contains(this, x, y, z);
	}

	@Override
	public IShape translate(final BlockPos pos)
	{
		return this.translate(pos.getX(), pos.getY(), pos.getZ());
	}

	@Override
	public IShape translate(final int x, final int y, final int z)
	{
		return new Region(this.getMin().add(x, y, z), this.getMax().add(x, y, z));
	}

	@Override
	public IShape rotate(final OrbisRotation rotation, final IRegion in)
	{
		return RotationHelp.rotate(this, in, rotation);
	}

	public final void notifyDataChange()
	{
		this.dataChanged = true;
	}

}