package com.gildedgames.aether.common.blocks.util;

import com.gildedgames.aether.common.ReflectionAether;
import com.gildedgames.aether.common.blocks.BlocksAether;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockCustomCarpet extends Block
{

	protected static final AxisAlignedBB CARPET_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.0625D, 1.0D);

	private IBlockState modelState;

	public BlockCustomCarpet(IBlockState modelState)
	{
		super(modelState.getMaterial());

		this.modelState = modelState;

		final Block block = modelState.getBlock();

		BlocksAether.applyPostRegistration(() -> BlockCustomCarpet.this.setHarvestLevel(block.getHarvestTool(modelState), block.getHarvestLevel(modelState)));

		this.setLightOpacity(0);

		this.setSoundType(block.getSoundType());

		float blockHardness = ObfuscationReflectionHelper.getPrivateValue(Block.class, block, ReflectionAether.BLOCK_HARDNESS.getMappings());

		this.setHardness(blockHardness);

		this.setTickRandomly(true);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return CARPET_AABB;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
	{
		return super.canPlaceBlockAt(worldIn, pos) && this.canBlockStay(worldIn, pos);
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
	{
		this.checkForDrop(worldIn, pos, state);
	}

	private void checkForDrop(World worldIn, BlockPos pos, IBlockState state)
	{
		if (!this.canBlockStay(worldIn, pos))
		{
			this.dropBlockAsItem(worldIn, pos, state, 0);
			worldIn.setBlockToAir(pos);
		}
	}

	private boolean canBlockStay(World worldIn, BlockPos pos)
	{
		return !worldIn.isAirBlock(pos.down());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
	{
		return side == EnumFacing.UP || blockAccess.getBlockState(pos.offset(side)).getBlock() == this
				|| super.shouldSideBeRendered(blockState, blockAccess, pos, side);
	}

	@Override
	public int damageDropped(IBlockState state)
	{
		return 0;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> list)
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
		return new BlockStateContainer(this);
	}

	@Override
	public MapColor getMapColor(IBlockState state)
	{
		return this.modelState.getMapColor();
	}

}
