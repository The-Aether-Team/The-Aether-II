package com.gildedgames.aether.api.world.preparation;

import net.minecraft.block.state.IBlockState;

public interface IChunkMaskTransformer
{
	IBlockState getBlockState(int key);

	int getBlockCount();
}
