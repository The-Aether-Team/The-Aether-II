package com.gildedgames.aether.api.util;

import net.minecraft.util.math.BlockPos;

/** TODO: MOVE THIS OUT OF THE API, FOR SOME REASON THE API CANNOT SEE THE MAIN AETHER SRC FILES **/
public class BlockPosDimension extends BlockPos
{

	private final int dimensionId;

	public BlockPosDimension(BlockPos pos, int dimensionId)
	{
		this(pos.getX(), pos.getY(), pos.getZ(), dimensionId);
	}

	public BlockPosDimension(int x, int y, int z, int dimensionId)
	{
		super(x, y, z);

		this.dimensionId = dimensionId;
	}

	public int dimId()
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

		if (object instanceof BlockPosDimension)
		{
			BlockPosDimension dungeonPosition = (BlockPosDimension) object;
			return dungeonPosition.getX() == this.getX() && dungeonPosition.getY() == this.getY() && dungeonPosition.getZ() == this.getZ() && dungeonPosition.dimensionId == this.dimensionId;
		}

		return false;
	}

	@Override
	public int hashCode()
	{
		return this.getX() + this.getY() << 8 + this.getZ() << 16;
	}

}
