package com.gildedgames.orbis.common.data.shapes;

import com.gildedgames.aether.api.orbis.region.IRegion;
import com.gildedgames.aether.api.orbis.shapes.IShape;
import com.gildedgames.aether.common.AetherCore;
import com.google.common.collect.AbstractIterator;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Iterator;

public abstract class AbstractShape implements IShape
{
	private World world;

	private IRegion boundingBox;

	protected AbstractShape(final World world)
	{
		this.world = world;
	}

	public AbstractShape(final IRegion boundingBox)
	{
		this.boundingBox = boundingBox;
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

	@Override
	public final void write(final NBTTagCompound tag)
	{
		tag.setTag("boundingBox", AetherCore.io().write(this.boundingBox));

		this.writeShape(tag);
	}

	public abstract void writeShape(NBTTagCompound tag);

	@Override
	public final void read(final NBTTagCompound tag)
	{
		this.boundingBox = AetherCore.io().read(this.world, tag.getCompoundTag("boundingBox"));

		this.readShape(tag);
	}

	public abstract void readShape(NBTTagCompound tag);

}
