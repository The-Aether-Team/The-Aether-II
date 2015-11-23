package com.gildedgames.aether.common.blocks.construction;

import net.minecraft.block.Block;
import net.minecraft.block.BlockTorch;

public class BlockAmbrosiumTorch extends BlockTorch
{
	public BlockAmbrosiumTorch()
	{
		super();

		this.setHardness(0f);
		this.setLightLevel(0.9375f);

		this.setStepSound(Block.soundTypeWood);
	}
}
