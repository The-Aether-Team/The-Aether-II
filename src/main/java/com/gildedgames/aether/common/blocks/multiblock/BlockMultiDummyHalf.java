package com.gildedgames.aether.common.blocks.multiblock;

import com.gildedgames.aether.common.blocks.IInternalBlock;
import com.gildedgames.aether.common.entities.tiles.multiblock.TileEntityMultiblockDummy;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.block.BlockRenderType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BlockMultiDummyHalf extends BlockMultiBase implements IInternalBlock
{

	protected static final AxisAlignedBB AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D);

	public BlockMultiDummyHalf()
	{
		this.setLightOpacity(0);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public BlockRenderType getRenderType(final BlockState state)
	{
		return BlockRenderType.INVISIBLE;
	}

	@Override
	public TileEntity createNewTileEntity(IBlockReader reader)
	{
		return new TileEntityMultiblockDummy();
	}

	@Override
	public AxisAlignedBB getBoundingBox(final BlockState state, final IBlockReader source, final BlockPos pos)
	{
		return AABB;
	}

	@Override
	@Deprecated
	public boolean isFullBlock(final BlockState state)
	{
		return false;
	}

	@Override
	public boolean isFullCube(final BlockState state)
	{
		return false;
	}

	@Override
	public boolean isOpaqueCube(final BlockState state)
	{
		return false;
	}

	@Override
	public boolean doesSideBlockRendering(final BlockState state, final IBlockReader world, final BlockPos pos, final Direction face)
	{
		return false;
	}

	@Override
	public VoxelShape getBlockFaceShape(IBlockReader worldIn, BlockState state, BlockPos pos, Direction face)
	{
		return VoxelShape.UNDEFINED;
	}

}
