package com.gildedgames.aether.api.world.preparation;

import net.minecraft.block.BlockState;

public interface IChunkMaskTransformer
{
	BlockState getBlockState(int key);

	int getBlockCount();
}
