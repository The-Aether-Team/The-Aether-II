package com.gildedgames.aether.common.util;

import net.minecraft.util.math.BlockPos;

public class WorldPos extends BlockPos
{

	private final int dimensionId;

	public WorldPos(BlockPos pos, int dimensionId)
	{
		this(pos.getX(), pos.getY(), pos.getZ(), dimensionId);
	}

	public WorldPos(int x, int y, int z, int dimensionId)
	{
		super(x, y, z);

		this.dimensionId = dimensionId;
	}

	/**
	 * @return This position's dimension ID
	 */
	public int getDimension()
	{
		return this.dimensionId;
	}

	@Override
	public boolean equals(Object object)
	{
		if (object == this)
		{
			return true;
		}

		if (object instanceof WorldPos)
		{
			WorldPos other = (WorldPos) object;

			return other.getX() == this.getX() && other.getY() == this.getY() && other.getZ() == this.getZ()
					&& other.getDimension() == this.getDimension();
		}

		return false;
	}

	@Override
	public int hashCode()
	{
		return this.getX() + (this.getY() << 8) + (this.getZ() << 16);
	}

}
