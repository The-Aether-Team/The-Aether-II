package com.gildedgames.aether.common.blocks.construction;

import net.minecraft.block.BlockState;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.SoundType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import java.util.Random;

public class BlockAmbrosiumTorch extends BlockTorch
{
	public BlockAmbrosiumTorch()
	{
		super();

		this.setHardness(0f);
		this.setLightLevel(0.9375f);

		this.setSoundType(SoundType.WOOD);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void randomDisplayTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand)
	{

	}

}
