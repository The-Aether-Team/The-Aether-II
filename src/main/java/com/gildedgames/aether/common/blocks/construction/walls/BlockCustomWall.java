package com.gildedgames.aether.common.blocks.construction.walls;

import com.gildedgames.aether.common.blocks.construction.signs.BlockWallSkyrootSign;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWall;
import net.minecraft.block.WallBlock;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateContainer;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

public class BlockCustomWall extends WallBlock
{

	public BlockCustomWall(final Block.Properties properties)
	{
		super(properties);

		this.setDefaultState(this.getStateContainer().getBaseState().with(UP, Boolean.FALSE).with(NORTH, Boolean.FALSE).with(EAST, Boolean.FALSE)
				.with(SOUTH, Boolean.FALSE).with(WEST, Boolean.FALSE));
	}

	@Override
	public String getLocalizedName()
	{
		return new TranslationTextComponent(this.getTranslationKey() + ".name").getFormattedText();
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void getSubBlocks(final ItemGroup tab, final NonNullList<ItemStack> list)
	{
		list.add(new ItemStack(this, 1, 0));
	}

	@Override
	public BlockState getStateFromMeta(final int meta)
	{
		return this.getDefaultState();
	}

	@Override
	public int getMetaFromState(final BlockState state)
	{
		return 0;
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
	{
		builder.add(BlockCustomWall.UP, BlockCustomWall.NORTH, BlockCustomWall.EAST, BlockCustomWall.WEST, BlockCustomWall.SOUTH,
				BlockWall.VARIANT);
	}

	@Override
	public boolean canPlaceTorchOnTop(final BlockState state, final IBlockReader world, final BlockPos pos)
	{
		return true;
	}

}
