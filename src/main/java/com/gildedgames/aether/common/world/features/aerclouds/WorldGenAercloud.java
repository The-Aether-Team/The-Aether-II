package com.gildedgames.aether.common.world.features.aerclouds;

import com.gildedgames.aether.common.world.chunk.ChunkGeneratorAether;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class WorldGenAercloud extends WorldGenerator
{
	protected final IBlockState state;

	protected final int numberOfBlocks;

	protected final boolean isFlat;

	public WorldGenAercloud(IBlockState state, int numberOfBlocks, boolean isFlat)
	{
		this.state = state;
		this.numberOfBlocks = numberOfBlocks;
		this.isFlat = isFlat;
	}

	@Override
	public boolean generate(World worldIn, Random rand, BlockPos position)
	{
		IBlockState state = this.getAercloudState(rand);

		BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos(position.getX(), position.getY(), position.getZ());

		int x = position.getX();
		int y = position.getY();
		int z = position.getZ();

		int xTendency = rand.nextInt(3) - 1;
		int zTendency = rand.nextInt(3) - 1;

		for (int n = 0; n < this.numberOfBlocks; n++)
		{
			x += (rand.nextInt(3) - 1 + xTendency);

			if ((rand.nextBoolean() && !this.isFlat) || (this.isFlat && rand.nextInt(10) == 0))
			{
				y += (rand.nextInt(3) - 1);
			}

			z += (rand.nextInt(3) - 1 + zTendency);

			for (int x1 = x; x1 < x + rand.nextInt(4) + 3 * (this.isFlat ? 3 : 1); x1++)
			{
				for (int y1 = y; y1 < y + rand.nextInt(1) + 2; y1++)
				{
					for (int z1 = z; z1 < z + rand.nextInt(4) + 3 * (this.isFlat ? 3 : 1); z1++)
					{
						pos.setPos(x1, y1, z1);

						if (worldIn.getBlockState(pos).getBlock().isReplaceable(worldIn, pos) &&
								Math.abs(x1 - x) + Math.abs(y1 - y) + Math.abs(z1 - z) < 4 * (this.isFlat ? 3 : 1) + rand.nextInt(2))
						{
							worldIn.setBlockState(pos, state, ChunkGeneratorAether.PLACEMENT_FLAG_TYPE);
						}
					}
				}
			}
		}

		return true;
	}

	public IBlockState getAercloudState(Random random)
	{
		return this.state;
	}
}
