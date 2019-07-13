package com.gildedgames.aether.common.blocks.containers;

import com.gildedgames.aether.common.blocks.multiblock.BlockMultiController;
import com.gildedgames.aether.common.entities.tiles.TileEntityTeleporter;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;

import javax.annotation.Nullable;

public class BlockAetherTeleporter extends BlockMultiController
{

	public static final DirectionProperty PROPERTY_FACING = DirectionProperty.create("facing", Direction.Plane.HORIZONTAL);

	public BlockAetherTeleporter(Block.Properties properties)
	{
		super(properties);

		this.setDefaultState(this.getStateContainer().getBaseState().with(PROPERTY_FACING, Direction.NORTH));
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context)
	{
		return this.getDefaultState().with(PROPERTY_FACING, context.getPlacementHorizontalFacing());
	}

	@Nullable
	@Override
	public TileEntity createNewTileEntity(IBlockReader reader)
	{
		return new TileEntityTeleporter();
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
	{
		builder.add(PROPERTY_FACING);
	}

	@Override
	public Iterable<BlockPos> getMultiblockVolumeIterator(BlockPos pos, IWorldReader world)
	{
		return BlockPos.getAllInBoxMutable(pos, pos.up());
	}
}
