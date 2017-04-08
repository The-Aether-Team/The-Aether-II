package com.gildedgames.aether.common.blocks.misc;

import com.gildedgames.aether.common.blocks.util.multiblock.BlockMultiController;
import com.gildedgames.aether.common.tiles.TileEntityOutpostCampfire;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockOutpostCampfire extends BlockMultiController
{

	protected static final AxisAlignedBB AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D);

	public BlockOutpostCampfire(Material materialIn)
	{
		super(materialIn);

		this.setBlockUnbreakable();
		this.setLightOpacity(0);
	}

	@Override
	public Iterable<BlockPos.MutableBlockPos> getMultiblockVolumeIterator(BlockPos pos, World world)
	{
		Iterable<BlockPos.MutableBlockPos> positions = BlockPos.getAllInBoxMutable(pos, pos.south().east());

		return positions;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityOutpostCampfire();
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return AABB;
	}

	@Override
	@Deprecated
	public boolean isFullBlock(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean isFullyOpaque(IBlockState state)
	{
		return false;
	}

}
