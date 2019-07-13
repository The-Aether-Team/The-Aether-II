package com.gildedgames.aether.common.blocks.construction;

import com.gildedgames.aether.common.blocks.multiblock.BlockMultiController;
import com.gildedgames.aether.common.entities.tiles.TileEntityOutpostCampfire;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class BlockOutpostCampfire extends BlockMultiController
{

	protected static final AxisAlignedBB AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D);

	public BlockOutpostCampfire(Block.Properties properties)
	{
		super(properties);

		this.setLightOpacity(0);
	}

	@Override
	public VoxelShape getBlockFaceShape(IBlockReader worldIn, BlockState state, BlockPos pos, Direction face)
	{
		return VoxelShape.UNDEFINED;
	}

	@Override
	public Iterable<BlockPos.MutableBlockPos> getMultiblockVolumeIterator(final BlockPos pos, final World world)
	{
		return BlockPos.getAllInBoxMutable(pos, pos.south().east());
	}

	@Override
	public TileEntity createNewTileEntity(IBlockReader reader)
	{
		return new TileEntityOutpostCampfire();
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
}
