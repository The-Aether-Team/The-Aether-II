package com.gildedgames.orbis.common.world_objects;

import com.gildedgames.aether.api.orbis.IWorldObject;
import com.gildedgames.aether.api.orbis.IWorldRenderer;
import com.gildedgames.aether.api.orbis.region.IRegion;
import com.gildedgames.aether.api.orbis.region.Region;
import com.gildedgames.orbis.client.renderers.RenderShape;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WorldRegion extends Region implements IWorldObject
{
	private final IWorldRenderer renderer = new RenderShape(this);

	protected WorldRegion(final World world)
	{
		super(world);
	}

	public WorldRegion(final IRegion region, final World world)
	{
		super(region);

		this.setWorld(world);
	}

	public WorldRegion(final BlockPos pos, final World world)
	{
		super(pos);

		this.setWorld(world);
	}

	public WorldRegion(final BlockPos corner1, final BlockPos corner2, final World world)
	{
		super(corner1, corner2);

		this.setWorld(world);
	}

	@Override
	public boolean equals(final Object o)
	{
		final boolean flag = super.equals(o);

		if (flag)
		{
			return true;
		}

		if (!(o instanceof Region))
		{
			return false;
		}

		final WorldRegion region = (WorldRegion) o;

		if (this.getMin().getX() == region.getMin().getX() && this.getMax().getX() == region.getMax().getX() && this.getMin().getY() == region.getMin().getY()
				&& this.getMax().getY() == region.getMax().getY() && this.getMin().getZ() == region.getMin().getZ() && this.getMax().getZ() == region.getMax()
				.getZ())
		{
			return this.getWorld().equals(region.getWorld());
		}

		return false;
	}

	@Override
	public BlockPos getPos()
	{
		return this.getMin();
	}

	@Override
	public void setPos(final BlockPos pos)
	{
		this.translate(pos);
	}

	@Override
	public IWorldRenderer getRenderer()
	{
		return this.renderer;
	}

	@Override
	public void write(final NBTTagCompound tag)
	{
		super.write(tag);
	}

	@Override
	public void read(final NBTTagCompound tag)
	{
		super.read(tag);
	}
}
