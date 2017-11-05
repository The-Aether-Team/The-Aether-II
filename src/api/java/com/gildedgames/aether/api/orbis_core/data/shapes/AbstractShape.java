package com.gildedgames.aether.api.orbis_core.data.shapes;

import com.gildedgames.aether.api.orbis.IRegion;
import com.gildedgames.aether.api.orbis.IShape;
import com.gildedgames.aether.api.orbis_core.OrbisCore;
import com.gildedgames.aether.api.orbis_core.data.region.IMutableRegion;
import com.google.common.collect.AbstractIterator;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Iterator;

public abstract class AbstractShape implements IShape
{
	private boolean createFromCenter, uniform;

	private World world;

	private IMutableRegion boundingBox;

	public AbstractShape()
	{

	}

	protected AbstractShape(final World world)
	{
		this.world = world;
	}

	public AbstractShape(final IMutableRegion boundingBox)
	{
		this.boundingBox = boundingBox;
	}

	public void setCreateFromCenter(final boolean flag)
	{
		this.createFromCenter = flag;
	}

	public boolean createFromCenter()
	{
		return this.createFromCenter;
	}

	public boolean isUniform()
	{
		return this.uniform;
	}

	public void setUniform(final boolean flag)
	{
		this.uniform = flag;
	}

	public IMutableRegion getMutableBB()
	{
		return this.boundingBox;
	}

	@Override
	public final Iterable<BlockPos.MutableBlockPos> createShapeData()
	{
		return new Iterable<BlockPos.MutableBlockPos>()
		{

			@Override
			public Iterator<BlockPos.MutableBlockPos> iterator()
			{
				final Iterator<BlockPos.MutableBlockPos> iter = AbstractShape.this.getBoundingBox().createShapeData().iterator();
				return new AbstractIterator<BlockPos.MutableBlockPos>()
				{

					@Override
					protected BlockPos.MutableBlockPos computeNext()
					{
						while (iter.hasNext())
						{
							final BlockPos.MutableBlockPos next = iter.next();

							if (AbstractShape.this.contains(next.getX(), next.getY(), next.getZ()))
							{
								return next;
							}
						}

						return this.endOfData();
					}
				};
			}
		};
	}

	@Override
	public IRegion getBoundingBox()
	{
		return this.boundingBox;
	}

	protected void setBoundingBox(IMutableRegion bb)
	{
		this.boundingBox = bb;
	}

	@Override
	public final void write(final NBTTagCompound tag)
	{
		tag.setBoolean("createFromCenter", this.createFromCenter);
		tag.setBoolean("uniform", this.uniform);

		tag.setTag("boundingBox", OrbisCore.io().write(this.boundingBox));

		this.writeShape(tag);
	}

	public abstract void writeShape(NBTTagCompound tag);

	@Override
	public final void read(final NBTTagCompound tag)
	{
		this.createFromCenter = tag.getBoolean("createFromCenter");
		this.uniform = tag.getBoolean("uniform");

		this.boundingBox = OrbisCore.io().read(this.world, tag.getCompoundTag("boundingBox"));

		this.readShape(tag);
	}

	public abstract void readShape(NBTTagCompound tag);

	public abstract BlockPos getRenderBoxMin();

	public abstract BlockPos getRenderBoxMax();

}
