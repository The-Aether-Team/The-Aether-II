package com.gildedgames.aether.api.orbis.region;

import com.gildedgames.aether.api.orbis.shapes.IShape;
import com.gildedgames.aether.api.orbis.util.OrbisRotation;
import com.gildedgames.aether.api.orbis.util.RegionHelp;
import com.gildedgames.aether.api.orbis.util.RotationHelp;
import com.gildedgames.aether.api.util.NBTHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Region implements IMutableRegion
{

	private World world;

	private BlockPos min, max;

	private Iterable<BlockPos.MutableBlockPos> data;

	private boolean dataChanged;

	protected Region(final World world)
	{
		this.world = world;
	}

	public Region(final IRegion region)
	{
		this.min = region.getMin();
		this.max = region.getMax();
	}

	public Region(final BlockPos pos)
	{
		if (pos == null)
		{
			throw new NullPointerException("Tried to make a Region of an empty position");
		}

		this.min = pos;
		this.max = pos;
	}

	public Region(final BlockPos corner1, final BlockPos corner2)
	{
		this.setBounds(corner1, corner2);
	}

	public World getWorld()
	{
		return this.world;
	}

	public void setWorld(final World world)
	{
		this.world = world;
	}

	@Override
	public void setBounds(final BlockPos corner1, final BlockPos corner2)
	{
		this.min = RegionHelp.getMin(corner1, corner2);
		this.max = RegionHelp.getMax(corner1, corner2);

		this.dataChanged = true;
	}

	@Override
	public void setBounds(final IRegion resized)
	{
		this.min = resized.getMin();
		this.max = resized.getMax();

		this.dataChanged = true;
	}

	@Override
	public boolean equals(final Object o)
	{
		final boolean flag = super.equals(o);

		if (flag)
		{
			return true;
		}

		if (!(o instanceof IRegion))
		{
			return false;
		}

		final IRegion region = (IRegion) o;

		final BlockPos rMin = region.getMin();
		final BlockPos rMax = region.getMax();

		return this.min.getX() == rMin.getX() && this.max.getX() == rMax.getX() && this.min.getY() == rMin.getY() && this.max.getY() == rMax.getY()
				&& this.min.getZ() == rMin.getZ() && this.max.getZ() == rMax.getZ();
	}

	@Override
	public String toString()
	{
		return "(" + this.min.getX() + ", " + this.min.getY() + ", " + this.min.getZ() + "; " + this.max.getX() + ", " + this.max.getY() + ", " + this.max
				.getZ() + ")";
	}

	/**
	 * Returns true if the given region intersects somewhere with this region
	 */
	public boolean intersectsWith(final Region region)
	{
		return RegionHelp.intersects(this, region);
	}

	/**
	 * Returns true if the given region is fully contained in this region
	 */
	public boolean contains(final Region region)
	{
		return RegionHelp.contains(region, this);
	}

	@Override
	public boolean contains(final BlockPos pos)
	{
		return this.contains(pos.getX(), pos.getY(), pos.getZ());
	}

	/**
	 * Returns true if the position lies in this region
	 */
	@Override
	public boolean contains(final int x, final int y, final int z)
	{
		return RegionHelp.contains(this, x, y, z);
	}

	/**
	 * Sets the min coordinate of the region to the given pos,
	 * and the max so that the current dimensions are kept.
	 */
	public void relocate(final BlockPos pos)
	{
		RegionHelp.relocate(this, pos);
		this.dataChanged = true;
	}

	@Override
	public IShape translate(final BlockPos pos)
	{
		return this.translate(pos.getX(), pos.getY(), pos.getZ());
	}

	@Override
	public IShape translate(final int x, final int y, final int z)
	{
		final Region region = new Region(this);
		region.add(x, y, z);

		return region;
	}

	public void add(final int x, final int y, final int z)
	{
		RegionHelp.translate(this, x, y, z);

		this.dataChanged = true;
	}

	public void subtract(final int x, final int y, final int z)
	{
		RegionHelp.translate(this, -x, -y, -z);

		this.dataChanged = true;
	}

	@SuppressWarnings("unchecked")
	public Iterable<BlockPos.MutableBlockPos> getMutableBlockPosInRegion()
	{
		return BlockPos.getAllInBoxMutable(this.min, this.max);
	}

	public int getXSize()
	{
		return this.max.getX() - this.min.getX() + 1;
	}

	public int getYSize()
	{
		return this.max.getY() - this.min.getY() + 1;
	}

	public int getZSize()
	{
		return this.max.getZ() - this.min.getZ() + 1;
	}

	public BlockPos getBottomCenter()
	{
		return new BlockPos(this.getCenterX(), this.min.getY(), this.getCenterZ());
	}

	public BlockPos getCenter()
	{
		return new BlockPos(this.getCenterX(), this.getCenterY(), this.getCenterZ());
	}

	public int getCenterX()
	{
		return this.min.getX() + (this.max.getX() - this.min.getX() + 1) / 2;
	}

	public int getCenterY()
	{
		return this.min.getY() + (this.max.getY() - this.min.getY() + 1) / 2;
	}

	public int getCenterZ()
	{
		return this.min.getZ() + (this.max.getZ() - this.min.getZ() + 1) / 2;
	}

	public double getCenterXD()
	{
		return this.min.getX() + (double) (this.max.getX() - this.min.getX() + 1) / 2;
	}

	public double getCenterZD()
	{
		return this.min.getZ() + (double) (this.max.getZ() - this.min.getZ() + 1) / 2;
	}

	@Override
	public int getLength()
	{
		return this.getZSize();
	}

	@Override
	public int getWidth()
	{
		return this.getXSize();
	}

	@Override
	public int getHeight()
	{
		return this.getYSize();
	}

	public int getVolume()
	{
		return this.getLength() * this.getWidth() * this.getHeight();
	}

	@Override
	public void write(final NBTTagCompound tag)
	{
		tag.setTag("min", NBTHelper.writeBlockPos(this.min));
		tag.setTag("max", NBTHelper.writeBlockPos(this.max));
	}

	@Override
	public void read(final NBTTagCompound tag)
	{
		this.min = NBTHelper.readBlockPos(tag.getCompoundTag("min"));
		this.max = NBTHelper.readBlockPos(tag.getCompoundTag("max"));
	}

	public boolean isACorner(final BlockPos pos)
	{
		return RegionHelp.isACorner(pos, this);
	}

	public void rotate90(final boolean clockwise)
	{
		final int width = this.getWidth();
		final int length = this.getLength();

		this.min = new BlockPos(this.min.getX() + width / 2 - length / 2, this.min.getY(), this.min.getZ() + length / 2 - width / 2);
		this.max = new BlockPos(this.min.getX() + length - 1, this.max.getY(), this.min.getZ() + width - 1);

		this.dataChanged = true;
	}

	@Override
	public BlockPos getMin()
	{
		return this.min;
	}

	@Override
	public BlockPos getMax()
	{
		return this.max;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterable<BlockPos.MutableBlockPos> createShapeData(final World world)
	{
		return BlockPos.getAllInBoxMutable(this.min, this.max);
	}

	@Override
	public IShape rotate(final OrbisRotation rotation, final IRegion regionIn)
	{
		final IShape rotated = RotationHelp.rotate(this, regionIn, rotation);

		return rotated;
	}

	@Override
	public IRegion getBoundingBox()
	{
		return this;
	}

	@Override
	public Iterable<BlockPos.MutableBlockPos> getShapeData()
	{
		if (this.data == null || this.dataChanged)
		{
			this.data = this.createShapeData(null);
		}

		return this.data;
	}

}
