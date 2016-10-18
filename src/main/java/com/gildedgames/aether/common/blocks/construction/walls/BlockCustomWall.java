package com.gildedgames.aether.common.blocks.construction.walls;

import net.minecraft.block.Block;
import net.minecraft.block.BlockWall;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class BlockCustomWall extends BlockWall
{

	public BlockCustomWall(IBlockState state, float hardness, float resistance)
	{
		super(state.getBlock());

		Block block = state.getBlock();

		this.setHarvestLevel(block.getHarvestTool(state), block.getHarvestLevel(state));

		this.setDefaultState(this.blockState.getBaseState().withProperty(UP, Boolean.FALSE).withProperty(NORTH, Boolean.FALSE).withProperty(EAST, Boolean.FALSE).withProperty(SOUTH, Boolean.FALSE).withProperty(WEST, Boolean.FALSE));
		this.setResistance(resistance / 3.0f);
		this.setHardness(hardness);
		this.setSoundType(state.getBlock().getSoundType());


	}

	public BlockCustomWall setGlows(boolean glows)
	{
		this.setLightLevel(glows ? 0.75f : 0.0f);

		return this;
	}

	@Override
	public String getLocalizedName()
	{
		return I18n.translateToLocal(this.getUnlocalizedName() + ".name");
	}

	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list)
	{
		list.add(new ItemStack(itemIn, 1, 0));
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState();
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return 0;
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, BlockCustomWall.UP, BlockCustomWall.NORTH, BlockCustomWall.EAST, BlockCustomWall.WEST, BlockCustomWall.SOUTH, BlockWall.VARIANT);
	}

	@Override
	public boolean canPlaceTorchOnTop(IBlockState state, IBlockAccess world, BlockPos pos)
	{
		return true;
	}

}
