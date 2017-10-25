package com.gildedgames.orbis.common.util;

import com.gildedgames.aether.common.util.helpers.BlockUtil;
import com.gildedgames.orbis.common.block.BlockDataWithConditions;
import com.gildedgames.orbis.common.block.BlockFilter;
import com.gildedgames.orbis.common.block.BlockFilterLayer;
import com.gildedgames.orbis.common.block.BlockFilterType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class BlockFilterHelper
{

	public static BlockFilterLayer getNewDeleteLayer(final ItemStack stack)
	{
		if (!(stack.getItem() instanceof ItemBlock))
		{
			final BlockFilterLayer layer = new BlockFilterLayer();

			layer.setFilterType(BlockFilterType.ALL);

			layer.getReplacementBlocks().add(new BlockDataWithConditions(Blocks.AIR.getDefaultState(), 1.0f));

			return layer;
		}

		final IBlockState state = BlockUtil.getBlockState(stack);

		final BlockFilterLayer layer = new BlockFilterLayer();

		layer.setFilterType(BlockFilterType.ONLY);

		layer.getRequiredBlocks().add(new BlockDataWithConditions(state, 1.0f));
		layer.getReplacementBlocks().add(new BlockDataWithConditions(Blocks.AIR.getDefaultState(), 1.0f));

		return layer;
	}

	/**
	 * @return The default fill layer
	 */
	public static BlockFilterLayer getNewFillLayer(final ItemStack stack)
	{
		if (!(stack.getItem() instanceof ItemBlock))
		{
			throw new NullPointerException("ItemStack given to getNewFillLayer() is not a Block. Aborting.");
		}

		final IBlockState state = BlockUtil.getBlockState(stack);

		final BlockFilterLayer layer = new BlockFilterLayer();

		layer.setFilterType(BlockFilterType.ALL);

		//layer.getRequiredBlocks().add(new BlockDataWithConditions(Blocks.AIR.getDefaultState(), 1.0f));
		layer.getReplacementBlocks().add(new BlockDataWithConditions(state, 1.0f));

		return layer;
	}

	public static BlockFilterLayer getNewReplaceLayer(final ItemStack stack)
	{
		if (!(stack.getItem() instanceof ItemBlock))
		{
			throw new NullPointerException("ItemStack given to getNewFillLayer() is not a Block. Aborting.");
		}

		final IBlockState state = BlockUtil.getBlockState(stack);

		final BlockFilterLayer layer = new BlockFilterLayer();

		layer.setFilterType(BlockFilterType.ALL_EXCEPT);

		layer.getRequiredBlocks().add(new BlockDataWithConditions(Blocks.AIR.getDefaultState(), 1.0f));
		layer.getReplacementBlocks().add(new BlockDataWithConditions(state, 1.0f));

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
