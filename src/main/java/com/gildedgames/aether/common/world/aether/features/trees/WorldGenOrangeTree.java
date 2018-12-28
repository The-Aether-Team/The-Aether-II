package com.gildedgames.aether.common.world.aether.features.trees;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.natural.plants.BlockOrangeTree;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class WorldGenOrangeTree extends WorldGenerator
{
	private static final int COUNT = 10;

	private final BlockOrangeTree treeBlock = BlocksAether.orange_tree;

	@Override
	public boolean generate(final World world, final Random rand, final BlockPos position)
	{
		final Object[] stages = BlockOrangeTree.PROPERTY_STAGE.getAllowedValues().toArray();

		int i = 0;

		while (i < COUNT)
		{
			final int x = rand.nextInt(8) - rand.nextInt(8);
			final int y = rand.nextInt(4) - rand.nextInt(4);
			final int z = rand.nextInt(8) - rand.nextInt(8);

			final BlockPos pos = position.add(x, y, z);

			i++;

			if (!world.isBlockLoaded(pos))
			{
				continue;
			}

			if (world.isAirBlock(pos) && world.isAirBlock(pos.up()) && this.treeBlock.isSuitableSoilBlock(world.getBlockState(pos.down())))
			{
				final int stage = (Integer) stages[rand.nextInt(stages.length)];

				final IBlockState state = BlocksAether.orange_tree.getDefaultState().withProperty(BlockOrangeTree.PROPERTY_STAGE, stage);

				if (stage >= 3)
				{
					world.setBlockState(pos.up(), state.withProperty(BlockOrangeTree.PROPERTY_IS_TOP_BLOCK, Boolean.TRUE));
				}

				world.setBlockState(pos, state.withProperty(BlockOrangeTree.PROPERTY_IS_TOP_BLOCK, Boolean.FALSE));
			}
		}

		return true;
	}
}
