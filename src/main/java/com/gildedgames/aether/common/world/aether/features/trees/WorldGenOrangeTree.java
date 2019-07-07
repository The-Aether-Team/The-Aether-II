package com.gildedgames.aether.common.world.aether.features.trees;

import com.gildedgames.aether.api.registrar.BlocksAether;
import com.gildedgames.aether.common.blocks.natural.plants.BlockOrangeTree;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class WorldGenOrangeTree extends WorldGenerator
{
	private static final int COUNT = 10;

	private final BlockOrangeTree treeBlock = (BlockOrangeTree) BlocksAether.orange_tree;

	@Override
	public boolean generate(final World world, final Random rand, final BlockPos position)
	{
		BlockPos.PooledMutableBlockPos pos = BlockPos.PooledMutableBlockPos.retain();
		BlockPos.PooledMutableBlockPos posUp = BlockPos.PooledMutableBlockPos.retain();

		int i = 0;

		while (i < COUNT)
		{
			final int x = rand.nextInt(8) - rand.nextInt(8);
			final int y = rand.nextInt(4) - rand.nextInt(4);
			final int z = rand.nextInt(8) - rand.nextInt(8);

			pos.setPos(position.getX() + x, position.getY() + y, position.getZ() + z);
			posUp.setPos(pos.getX(), pos.getY() + 1, pos.getZ());

			i++;

			if (!world.isBlockLoaded(pos))
			{
				continue;
			}

			if (world.isAirBlock(pos) && world.isAirBlock(posUp) && this.treeBlock.isSuitableSoilBlock(world, pos, world.getBlockState(pos.down())))
			{
				final int stage = 1 + rand.nextInt(4);

				final IBlockState state = BlocksAether.orange_tree.getDefaultState().withProperty(BlockOrangeTree.PROPERTY_STAGE, stage);

				if (stage >= 3)
				{
					world.setBlockState(posUp, state.withProperty(BlockOrangeTree.PROPERTY_IS_TOP_BLOCK, Boolean.TRUE), 2 | 16);
				}

				world.setBlockState(pos, state.withProperty(BlockOrangeTree.PROPERTY_IS_TOP_BLOCK, Boolean.FALSE), 2 | 16);
			}
		}

		posUp.release();
		pos.release();

		return true;
	}
}
