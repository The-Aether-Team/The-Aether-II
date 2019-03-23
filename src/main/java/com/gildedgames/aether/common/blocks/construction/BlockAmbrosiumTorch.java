package com.gildedgames.aether.common.blocks.construction;

import net.minecraft.block.BlockTorch;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

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
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
	{

	}

}
