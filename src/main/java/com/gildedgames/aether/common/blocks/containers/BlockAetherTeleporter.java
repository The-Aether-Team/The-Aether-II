package com.gildedgames.aether.common.blocks.containers;

import com.gildedgames.aether.common.blocks.multiblock.BlockMultiController;
import com.gildedgames.aether.common.entities.tiles.TileEntityTeleporter;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
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

	public static final PropertyDirection PROPERTY_FACING = PropertyDirection.create("facing", Direction.Plane.HORIZONTAL);

	public BlockAetherTeleporter()
	{
		super(Material.ROCK);

		this.setHardness(2.5f);
		this.setSoundType(SoundType.STONE);
		this.setDefaultState(this.getBlockState().getBaseState().withProperty(PROPERTY_FACING, Direction.NORTH));
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
		return this.getDefaultState().withProperty(PROPERTY_FACING, placer.getHorizontalFacing());
	}

	@Override
	public BlockState getStateFromMeta(final int meta)
	{
		return this.getDefaultState().withProperty(PROPERTY_FACING, Direction.byHorizontalIndex(meta));
	}

	@Override
	public int getMetaFromState(final BlockState state)
	{
		return state.getValue(PROPERTY_FACING).getIndex();
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
	public TileEntity createNewTileEntity(final World world, final int i)
	{
		return new TileEntityTeleporter();
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, PROPERTY_FACING);
	}

	@Override
	public Iterable<BlockPos.MutableBlockPos> getMultiblockVolumeIterator(BlockPos pos, World world)
	{
		return BlockPos.getAllInBoxMutable(pos, pos.up());
	}
}
