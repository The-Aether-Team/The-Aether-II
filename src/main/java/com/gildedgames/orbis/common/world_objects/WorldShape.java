package com.gildedgames.orbis.common.world_objects;

import com.gildedgames.aether.api.io.NBTFunnel;
import com.gildedgames.aether.api.orbis.IWorldObject;
import com.gildedgames.aether.api.orbis.IWorldRenderer;
import com.gildedgames.aether.api.orbis.management.IData;
import com.gildedgames.aether.api.orbis.region.IRegion;
import com.gildedgames.aether.api.orbis.shapes.IShape;
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
public class WorldShape implements IShape, IWorldObject
{
	private World world;

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
	public void setPos(BlockPos pos)
	{
		BlockPos current = this.getPos();
		int dx = pos.getX() - current.getX();
		int dy = pos.getY() - current.getY();
		int dz = pos.getZ() - current.getZ();
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
	public IShape rotate(Rotation rotation, IRegion in)
	{
		return this.shape.rotate(rotation, in);
	}

	@Override
	public IShape translate(int x, int y, int z)
	{
		return this.shape.translate(x, y, z);
	}

	@Override
	public IShape translate(BlockPos pos)
	{
		return this.shape.translate(pos);
	}

	@Override
	public IRegion getBoundingBox()
	{
		return this.shape.getBoundingBox();
	}

	@Override
	public boolean contains(int x, int y, int z)
	{
		return this.shape.contains(x, y, z);
	}

	@Override
	public boolean contains(BlockPos pos)
	{
		return this.shape.contains(pos);
	}

	@Override
	public void write(NBTTagCompound tag)
	{
		NBTFunnel funnel = AetherCore.io().createFunnel(tag);
		funnel.set("shape", this.shape);
	}

	@Override
	public void read(NBTTagCompound tag)
	{
		NBTFunnel funnel = AetherCore.io().createFunnel(tag);
		this.shape = funnel.get(this.world, "shape");
	}
}
