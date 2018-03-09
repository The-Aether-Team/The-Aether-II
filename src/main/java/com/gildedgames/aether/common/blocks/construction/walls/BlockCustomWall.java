package com.gildedgames.aether.common.blocks.construction.walls;

import net.minecraft.block.Block;
import net.minecraft.block.BlockWall;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockCustomWall extends BlockWall
{

	public BlockCustomWall(final IBlockState state, final float hardness, final float resistance)
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
		return new TextComponentTranslation(this.getUnlocalizedName() + ".name").getFormattedText();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(final CreativeTabs tab, final NonNullList<ItemStack> list)
	{
		list.add(new ItemStack(this, 1, 0));
	}

	@Override
	public IBlockState getStateFromMeta(final int meta)
	{
		return this.getDefaultState();
	}

	@Override
	public int getMetaFromState(final IBlockState state)
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
	public boolean canPlaceTorchOnTop(final IBlockState state, final IBlockAccess world, final BlockPos pos)
	{
		return true;
	}

}
