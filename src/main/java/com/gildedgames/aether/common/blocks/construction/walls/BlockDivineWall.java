package com.gildedgames.aether.common.blocks.construction.walls;

import net.minecraft.block.state.IBlockState;

public class BlockDivineWall extends BlockAetherWall
{
	public BlockDivineWall(IBlockState state, float hardness, float resistance)
	{
		super(state, hardness, resistance);

		this.setBlockUnbreakable();
	}
}
