package com.gildedgames.aether.common.blocks.construction;

import com.gildedgames.aether.common.blocks.multiblock.BlockMultiController;
import com.gildedgames.aether.common.entities.tiles.TileEntityOutpostCampfire;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;

public class BlockOutpostCampfire extends BlockMultiController
{

	protected static final VoxelShape AABB = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D);

	public BlockOutpostCampfire(Block.Properties properties)
	{
		super(properties);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)	{
		return AABB;
	}

	@Override
	public Iterable<BlockPos> getMultiblockVolumeIterator(final BlockPos pos, final IWorldReader world)
	{
		return BlockPos.getAllInBoxMutable(pos, pos.south().east());
	}

	@Override
	public TileEntity createNewTileEntity(IBlockReader reader)
	{
		return new TileEntityOutpostCampfire();
	}
}
