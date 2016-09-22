package com.gildedgames.aether.common.world.features;

import net.minecraft.block.BlockVine;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class WorldGenCustomVines extends WorldGenerator
{

	private final IBlockState vines;

	public WorldGenCustomVines(IBlockState vines)
	{
		this.vines = vines;
	}

	public boolean generate(World worldIn, Random rand, BlockPos position)
	{
		for (; position.getY() < 128; position = position.up())
		{
			if (worldIn.isAirBlock(position))
			{
				for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL.facings())
				{
					if (Blocks.VINE.canPlaceBlockOnSide(worldIn, position, enumfacing))
					{
						IBlockState iblockstate = this.vines.withProperty(BlockVine.NORTH, Boolean.valueOf(enumfacing == EnumFacing.NORTH)).withProperty(BlockVine.EAST, Boolean.valueOf(enumfacing == EnumFacing.EAST)).withProperty(BlockVine.SOUTH, Boolean.valueOf(enumfacing == EnumFacing.SOUTH)).withProperty(BlockVine.WEST, Boolean.valueOf(enumfacing == EnumFacing.WEST));
						worldIn.setBlockState(position, iblockstate, 2);

						break;
					}
				}
			}
			else
			{
				position = position.add(rand.nextInt(4) - rand.nextInt(4), 0, rand.nextInt(4) - rand.nextInt(4));
			}
		}

		return true;
	}
}
