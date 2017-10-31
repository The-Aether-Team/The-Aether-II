package com.gildedgames.orbis.common.world_objects;

import com.gildedgames.aether.api.io.NBTFunnel;
import com.gildedgames.aether.api.orbis.IRegion;
import com.gildedgames.aether.api.orbis.IShape;
import com.gildedgames.aether.api.orbis.IWorldRenderer;
import com.gildedgames.aether.api.orbis_core.OrbisCore;
import com.gildedgames.aether.api.orbis_core.data.management.IData;
import com.gildedgames.aether.api.world_object.IWorldObject;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.orbis.client.renderers.RenderShape;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Simple wrapper around an IShape so it can be used as a WorldObject.
 * This is used in for example the Select tool for shared non-box shape rendering
 */
public class WorldShape implements IShape, IWorldObject, IColored
{
	private final World world;

	private IShape shape;

	private IWorldRenderer renderer;

	private WorldShape(final World world)
	{
		this.world = world;
	}

	public WorldShape(final IShape shape, final World world)
	{
		this.shape = shape;
		this.world = world;
	}

	@Override
	public World getWorld()
	{
		return world;
	}

	@Override
	public BlockPos getPos()
	{
		return shape.getBoundingBox().getMin();
	}

	@Override
	public void setPos(final BlockPos pos)
	{
		final BlockPos current = this.getPos();
		final int dx = pos.getX() - current.getX();
		final int dy = pos.getY() - current.getY();
		final int dz = pos.getZ() - current.getZ();
		this.shape = shape.translate(dx, dy, dz);
	}

	@Override
	public IWorldRenderer getRenderer()
	{
		if (AetherCore.isClient() && this.renderer == null)
		{
			this.renderer = new RenderShape(this);
		}
		return this.renderer;
	}

	@Override
	public IData getData()
	{
		return null;
	}

	@Override
	public Iterable<BlockPos.MutableBlockPos> createShapeData()
	{
		return this.shape.createShapeData();
	}

	@Override
	public Iterable<BlockPos.MutableBlockPos> getShapeData()
	{
		return this.shape.getShapeData();
	}

	@Override
	public IShape rotate(final Rotation rotation, final IRegion in)
	{
		return this.shape.rotate(rotation, in);
	}

	@Override
	public IShape translate(final int x, final int y, final int z)
	{
		return this.shape.translate(x, y, z);
	}

	@Override
	public IShape translate(final BlockPos pos)
	{
		return this.shape.translate(pos);
	}

	@Override
	public IRegion getBoundingBox()
	{
		return this.shape.getBoundingBox();
	}

	@Override
	public boolean contains(final int x, final int y, final int z)
	{
		return this.shape.contains(x, y, z);
	}

	@Override
	public boolean contains(final BlockPos pos)
	{
		return this.shape.contains(pos);
	}

	@Override
	public void write(final NBTTagCompound tag)
	{
		final NBTFunnel funnel = OrbisCore.io().createFunnel(tag);
		funnel.set("shape", this.shape);
	}

	@Override
	public void read(final NBTTagCompound tag)
	{
		final NBTFunnel funnel = OrbisCore.io().createFunnel(tag);
		this.shape = funnel.get(this.world, "shape");
	}

	@Override
	public int getColor()
	{
		return 0x999999;
	}
}
