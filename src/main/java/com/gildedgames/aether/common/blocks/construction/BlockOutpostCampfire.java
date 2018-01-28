package com.gildedgames.aether.common.blocks.construction;

import com.gildedgames.aether.common.blocks.multiblock.BlockMultiController;
import com.gildedgames.aether.common.entities.tiles.TileEntityOutpostCampfire;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockOutpostCampfire extends BlockMultiController
{

	protected static final AxisAlignedBB AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D);

	public BlockOutpostCampfire(final Material materialIn)
	{
		super(materialIn);

		this.setBlockUnbreakable();
		this.setLightOpacity(0);
	}

	@Override
	public Iterable<BlockPos.MutableBlockPos> getMultiblockVolumeIterator(final BlockPos pos, final World world)
	{
		final Iterable<BlockPos.MutableBlockPos> positions = BlockPos.getAllInBoxMutable(pos, pos.south().east());

		return positions;
	}

	@Override
	public TileEntity createNewTileEntity(final World worldIn, final int meta)
	{
		return new TileEntityOutpostCampfire();
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
