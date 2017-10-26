package com.gildedgames.orbis.common.util;

import com.gildedgames.aether.common.util.helpers.BlockUtil;
import com.gildedgames.orbis.common.block.BlockDataWithConditions;
import com.gildedgames.orbis.common.block.BlockFilter;
import com.gildedgames.orbis.common.block.BlockFilterLayer;
import com.gildedgames.orbis.common.block.BlockFilterType;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.IShearable;

public class BlockFilterHelper
{

	public static Block[] getBlocksFromSpecialItems(final ItemStack stack)
	{
		Block[] blocks = null;

		if (stack.getItem() instanceof ItemBlock)
		{
			final IBlockState state = BlockUtil.getBlockState(stack);

			if (state.getBlock() instanceof IShearable)
			{
				blocks = new Block[1];

				blocks[0] = state.getBlock();
			}
		}
		else if (stack.getItem() == Items.LAVA_BUCKET)
		{
			blocks = new Block[2];

			blocks[0] = Blocks.LAVA;
			blocks[1] = Blocks.FLOWING_LAVA;
		}
		else if (stack.getItem() == Items.WATER_BUCKET)
		{
			blocks = new Block[2];

			blocks[0] = Blocks.WATER;
			blocks[1] = Blocks.FLOWING_WATER;
		}

		return blocks;
	}

	public static BlockFilterLayer getNewDeleteLayer(final ItemStack stack)
	{
		if (!(stack.getItem() instanceof ItemBlock) && !(stack.getItem() instanceof ItemBucket))
		{
			final BlockFilterLayer layer = new BlockFilterLayer();

			layer.setFilterType(BlockFilterType.ALL);

			layer.getReplacementBlocks().add(new BlockDataWithConditions(Blocks.AIR.getDefaultState(), 1.0f));

			return layer;
		}

		final BlockFilterLayer layer = new BlockFilterLayer();

		layer.setFilterType(BlockFilterType.ONLY);

		final IBlockState state = BlockUtil.getBlockState(stack);
		final Block[] blocks = getBlocksFromSpecialItems(stack);

		if (blocks != null)
		{
			for (final Block s : blocks)
			{
				layer.getRequiredBlocks().add(new BlockDataWithConditions(s, 1.0f));
			}
		}
		else
		{
			layer.getRequiredBlocks().add(new BlockDataWithConditions(state, 1.0f));
		}

		layer.getReplacementBlocks().add(new BlockDataWithConditions(Blocks.AIR.getDefaultState(), 1.0f));

		return layer;
	}

	/**
	 * @return The default fill layer
	 */
	public static BlockFilterLayer getNewFillLayer(final ItemStack stack)
	{
		if (!(stack.getItem() instanceof ItemBlock) && !(stack.getItem() instanceof ItemBucket))
		{
			throw new NullPointerException("ItemStack given to getNewFillLayer() is not a Block. Aborting.");
		}

		final IBlockState state = BlockUtil.getBlockState(stack);

		final BlockFilterLayer layer = new BlockFilterLayer();

		layer.setFilterType(BlockFilterType.ALL);

		final Block[] blocks = getBlocksFromSpecialItems(stack);

		if (blocks != null)
		{
			for (final Block s : blocks)
			{
				layer.getReplacementBlocks().add(new BlockDataWithConditions(s, 1.0f));
			}
		}
		else
		{
			layer.getReplacementBlocks().add(new BlockDataWithConditions(state, 1.0f));
		}

		return layer;
	}

	public static BlockFilterLayer getNewReplaceLayer(final ItemStack mainHand, final ItemStack offHand)
	{
		if (!(mainHand.getItem() instanceof ItemBlock) && !(mainHand.getItem() instanceof ItemBucket)
				&& !(offHand.getItem() instanceof ItemBlock) && !(offHand.getItem() instanceof ItemBucket))
		{
			throw new NullPointerException("ItemStack given to getNewFillLayer() is not a Block. Aborting.");
		}

		final IBlockState mainHandState = BlockUtil.getBlockState(mainHand);
		IBlockState offHandState = BlockUtil.getBlockState(offHand);

		if (offHand.getItem() == Items.STRING)
		{
			offHandState = Blocks.AIR.getDefaultState();
		}

		final BlockFilterLayer layer = new BlockFilterLayer();

		final Block[] mainStates = getBlocksFromSpecialItems(mainHand);
		final Block[] offStates = getBlocksFromSpecialItems(offHand);

		if (offHandState == null && offStates == null)
		{
			layer.setFilterType(BlockFilterType.ALL_EXCEPT);

			if (mainStates != null)
			{
				for (final Block s : mainStates)
				{
					layer.getReplacementBlocks().add(new BlockDataWithConditions(s, 1.0f));
				}
			}
			else
			{
				layer.getReplacementBlocks().add(new BlockDataWithConditions(mainHandState, 1.0f));
			}

			layer.getRequiredBlocks().add(new BlockDataWithConditions(Blocks.AIR.getDefaultState(), 1.0f));

		}
		else
		{
			layer.setFilterType(BlockFilterType.ONLY);

			if (mainStates != null)
			{
				for (final Block s : mainStates)
				{
					layer.getReplacementBlocks().add(new BlockDataWithConditions(s, 1.0f));
				}
			}
			else
			{
				layer.getReplacementBlocks().add(new BlockDataWithConditions(mainHandState, 1.0f));
			}

			if (offStates != null)
			{
				for (final Block s : offStates)
				{
					layer.getRequiredBlocks().add(new BlockDataWithConditions(s, 1.0f));
				}
			}
			else
			{
				layer.getRequiredBlocks().add(new BlockDataWithConditions(offHandState, 1.0f));
			}
		}

		return layer;
	}

	public static BlockFilterLayer getNewVoidLayer()
	{
		final IBlockState state = Blocks.STRUCTURE_VOID.getDefaultState();

		final BlockFilterLayer layer = new BlockFilterLayer();

		layer.setFilterType(BlockFilterType.ONLY);

		layer.getRequiredBlocks().add(new BlockDataWithConditions(Blocks.AIR.getDefaultState(), 1.0f));
		layer.getReplacementBlocks().add(new BlockDataWithConditions(state, 1.0f));

		return layer;
	}

	/**
	 * The default Delete layer
	 * @author Emile
	 *
	 */
	public static class BlockDeleteFilter extends BlockFilter
	{
		public BlockDeleteFilter()
		{
			final BlockFilterLayer layer = new BlockFilterLayer();

			layer.setFilterType(BlockFilterType.ALL);
			layer.getReplacementBlocks().add(new BlockDataWithConditions(Blocks.AIR.getDefaultState(), 1.0f));
			this.add(layer);
		}
	}

}
