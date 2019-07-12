package com.gildedgames.aether.common.blocks.construction.walls;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWall;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

public class BlockCustomWall extends BlockWall
{

	public BlockCustomWall(final BlockState state, final float hardness, final float resistance)
	{
		super(state.getBlock());

		final Block block = state.getBlock();

		this.setHarvestLevel(block.getHarvestTool(state), block.getHarvestLevel(state));

		this.setDefaultState(this.blockState.getBaseState().withProperty(UP, Boolean.FALSE).withProperty(NORTH, Boolean.FALSE).withProperty(EAST, Boolean.FALSE)
				.withProperty(SOUTH, Boolean.FALSE).withProperty(WEST, Boolean.FALSE));
		this.setResistance(resistance / 3.0f);
		this.setHardness(hardness);
		this.setSoundType(state.getBlock().getSoundType());

	}

	public BlockCustomWall setGlows(final boolean glows)
	{
		this.setLightLevel(glows ? 0.75f : 0.0f);

		return this;
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
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, BlockCustomWall.UP, BlockCustomWall.NORTH, BlockCustomWall.EAST, BlockCustomWall.WEST, BlockCustomWall.SOUTH,
				BlockWall.VARIANT);
	}

	@Override
	public boolean canPlaceTorchOnTop(final BlockState state, final IBlockReader world, final BlockPos pos)
	{
		return true;
	}

}
