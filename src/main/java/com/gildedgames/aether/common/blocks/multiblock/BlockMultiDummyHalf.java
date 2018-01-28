package com.gildedgames.aether.common.blocks.multiblock;

import com.gildedgames.aether.common.entities.tiles.multiblock.TileEntityMultiblockDummy;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockMultiDummyHalf extends BlockMultiBase
{

	protected static final AxisAlignedBB AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D);

	public BlockMultiDummyHalf()
	{
		super(Material.ROCK);

		this.setBlockUnbreakable();
		this.setResistance(6000000.0F);
		this.setLightOpacity(0);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumBlockRenderType getRenderType(final IBlockState state)
	{
		return EnumBlockRenderType.INVISIBLE;
	}

	@Override
	public TileEntity createNewTileEntity(final World worldIn, final int meta)
	{
		return new TileEntityMultiblockDummy();
	}

	@Override
	public AxisAlignedBB getBoundingBox(final IBlockState state, final IBlockAccess source, final BlockPos pos)
	{
		return AABB;
	}

	@Override
	@Deprecated
	public boolean isFullBlock(final IBlockState state)
	{
		return false;
	}

	@Override
	public boolean isFullCube(final IBlockState state)
	{
		return false;
	}

	@Override
	public boolean isOpaqueCube(final IBlockState state)
	{
		return false;
	}

	@Override
	public boolean doesSideBlockRendering(final IBlockState state, final IBlockAccess world, final BlockPos pos, final EnumFacing face)
	{
		return false;
	}

}
