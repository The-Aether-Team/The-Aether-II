package com.gildedgames.aether.common.blocks.containers;

import com.gildedgames.aether.common.blocks.construction.walls.BlockCustomWall;
import com.gildedgames.aether.common.blocks.construction.walls.BlockSkyrootWall;
import com.gildedgames.aether.common.blocks.multiblock.BlockMultiController;
import com.gildedgames.aether.common.entities.tiles.TileEntityTeleporter;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockAetherTeleporter extends BlockMultiController
{

	public static final DirectionProperty PROPERTY_FACING = DirectionProperty.create("facing", Direction.Plane.HORIZONTAL);

	public BlockAetherTeleporter(Block.Properties properties)
	{
		super(properties);

		this.setDefaultState(this.stateContainer.getBaseState().with(PROPERTY_FACING, Direction.NORTH));
	}

	@Override
	public VoxelShape getBlockFaceShape(IBlockReader worldIn, BlockState state, BlockPos pos, Direction face)
	{
		return VoxelShape.UNDEFINED;
	}

	@Override
	public BlockState getStateForPlacement(final World world, final BlockPos pos, final Direction facing, final float hitX, final float hitY,
			final float hitZ, final int meta,
			final LivingEntity placer)
	{
		return this.getDefaultState().with(PROPERTY_FACING, placer.getHorizontalFacing());
	}

	@Override
	public BlockState getStateFromMeta(final int meta)
	{
		return this.getDefaultState().with(PROPERTY_FACING, Direction.byHorizontalIndex(meta));
	}

	@Override
	public int getMetaFromState(final BlockState state)
	{
		return state.get(PROPERTY_FACING).getIndex();
	}

	@Override
	public boolean isFullCube(final BlockState state)
	{
		return false;
	}

	@Override
	public boolean isOpaqueCube(final BlockState p_isOpaqueCube_1_)
	{
		return false;
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
	public Iterable<BlockPos.MutableBlockPos> getMultiblockVolumeIterator(BlockPos pos, World world)
	{
		return BlockPos.getAllInBoxMutable(pos, pos.up());
	}
}
