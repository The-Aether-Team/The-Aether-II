package com.gildedgames.aether.common.world.features.trees;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.natural.plants.BlockOrangeTree;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class WorldGenOrangeTree extends WorldGenerator
{
	private static final int count = 10;

	private final BlockOrangeTree treeBlock = BlocksAether.orange_tree;

	@Override
	public boolean generate(World world, Random rand, BlockPos position)
	{
		Object[] stages = BlockOrangeTree.PROPERTY_STAGE.getAllowedValues().toArray();

		for (int i = 0; i < count; i++)
		{
			int x = rand.nextInt(8) - rand.nextInt(8);
			int y = rand.nextInt(4) - rand.nextInt(4);
			int z = rand.nextInt(8) - rand.nextInt(8);

			BlockPos pos = position.add(x, y, z);

			if (world.isAirBlock(pos) && world.isAirBlock(pos.up()) && this.treeBlock.isSuitableSoilBlock(world.getBlockState(pos.down())))
			{
				int stage = (Integer) stages[rand.nextInt(stages.length)];

				IBlockState state = BlocksAether.orange_tree.getDefaultState().withProperty(BlockOrangeTree.PROPERTY_STAGE, stage);

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
