package com.gildedgames.aether.common.world.features;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.natural.BlockTallAetherGrass;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class WorldGenAetherTallGrass extends WorldGenerator
{
	private final IBlockState state;

	public WorldGenAetherTallGrass()
	{
		this.state = BlocksAether.tall_aether_grass.getDefaultState();
	}

	@Override
	public boolean generate(World world, Random rand, BlockPos pos)
	{
		Block block;

		while (pos.getY() > 0)
		{
			block = world.getBlockState(pos).getBlock();

			if (!block.isAir(world, pos) && !block.isLeaves(world, pos))
			{
				break;
			}

			pos = pos.down();
		}

		for (int i = 0; i < 128; ++i)
		{
			BlockPos randomPos = pos.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));

			if (world.isAirBlock(randomPos) && ((BlockTallAetherGrass) BlocksAether.tall_aether_grass).canBlockStay(world, randomPos, this.state))
			{
				world.setBlockState(randomPos, this.state, 2);
			}
		}

		return true;
	}
}
