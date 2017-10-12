package com.gildedgames.orbis.common.block;

import net.minecraft.util.math.BlockPos;
import org.apache.commons.lang3.builder.EqualsBuilder;

public class BlockInstance
{

	private final BlockData blockData;

	private final BlockPos pos;

	public BlockInstance(final BlockData blockData, final BlockPos pos)
	{
		this.blockData = blockData;
		this.pos = pos;
	}

	public BlockData getBlockData()
	{
		return this.blockData;
	}

	public BlockPos getPos()
	{
		return this.pos;
	}

	@Override
	public boolean equals(final Object obj)
	{
		boolean flag = false;

		if (obj == this)
		{
			flag = true;
		}
		else if (obj instanceof BlockInstance)
		{
			final BlockInstance o = (BlockInstance) obj;
			final EqualsBuilder builder = new EqualsBuilder();

			builder.append(this.blockData, o.blockData);
			builder.append(this.pos, o.blockData);

			flag = builder.isEquals();
		}

		return flag;
	}

	@Override
	public String toString()
	{
		return "BlockInstance - POS: " + this.pos.toString() + ", BLOCKDATA: " + this.blockData.toString();
	}

}
