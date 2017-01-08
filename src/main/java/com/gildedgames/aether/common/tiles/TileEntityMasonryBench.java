package com.gildedgames.aether.common.tiles;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.containers.BlockMasonryBench;
import com.gildedgames.aether.common.tiles.util.TileEntitySynced;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;

public class TileEntityMasonryBench extends TileEntitySynced
{

	public EnumFacing getFacing()
	{
		IBlockState state = this.worldObj.getBlockState(this.pos);

		if (state.getBlock() == BlocksAether.masonry_bench)
		{
			return state.getValue(BlockMasonryBench.PROPERTY_FACING);
		}

		return EnumFacing.NORTH;
	}

}
