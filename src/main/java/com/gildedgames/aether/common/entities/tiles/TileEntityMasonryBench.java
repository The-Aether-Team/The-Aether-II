package com.gildedgames.aether.common.entities.tiles;

import com.gildedgames.aether.api.registrar.BlocksAether;
import com.gildedgames.aether.common.blocks.containers.BlockMasonryBench;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;

public class TileEntityMasonryBench extends TileEntitySynced
{

	public Direction getFacing()
	{
		BlockState state = this.world.getBlockState(this.pos);

		if (state.getBlock() == BlocksAether.masonry_bench)
		{
			return state.get(BlockMasonryBench.PROPERTY_FACING);
		}

		return Direction.NORTH;
	}

}
