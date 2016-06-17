package com.gildedgames.aether.common.blocks.dungeon;

import com.gildedgames.aether.common.blocks.util.multiblock.BlockMultiController;
import com.gildedgames.aether.common.tile_entities.TileEntityLabyrinthDoor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BlockLabyrinthDoor extends BlockMultiController
{

	public static final PropertyDirection PROPERTY_FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

	public BlockLabyrinthDoor(Material material)
	{
		super(material);

		this.setBlockUnbreakable();
		this.setDefaultState(this.getBlockState().getBaseState().withProperty(PROPERTY_FACING, EnumFacing.NORTH));
	}

	@Override
	public Iterable<BlockPos.MutableBlockPos> getMultiblockVolumeIterator(BlockPos pos, World world)
	{
		return BlockPos.getAllInBoxMutable(pos, pos);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityLabyrinthDoor();
	}


	// Code below is just for testing rotation. In final version, door won't actually be placed.

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		world.setBlockState(pos, state.withProperty(PROPERTY_FACING, placer.getHorizontalFacing()));
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(PROPERTY_FACING, EnumFacing.getHorizontal(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(PROPERTY_FACING).getIndex();
	}

	@Override
	protected BlockState createBlockState()
	{
		return new BlockState(this, PROPERTY_FACING);
	}
}
