package com.gildedgames.aether.common.blocks.containers;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.tiles.TileEntityIcestoneCooler;
import com.gildedgames.aether.common.network.AetherGuiHandler;
import net.minecraft.block.ContainerBlock;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class BlockIcestoneCooler extends ContainerBlock
{

	public static final PropertyDirection PROPERTY_FACING = PropertyDirection.create("facing", Direction.Plane.HORIZONTAL);

	public BlockIcestoneCooler()
	{
		super(Material.WOOD);

		this.setHardness(2.5f);

		this.setSoundType(SoundType.WOOD);

		this.setDefaultState(this.getBlockState().getBaseState().withProperty(PROPERTY_FACING, Direction.NORTH));
	}

	@Override
	public VoxelShape getBlockFaceShape(IBlockReader worldIn, BlockState state, BlockPos pos, Direction face)
	{
		return VoxelShape.UNDEFINED;
	}

	@Override
	public BlockRenderType getRenderType(BlockState state)
	{
		return BlockRenderType.MODEL;
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack)
	{
		world.setBlockState(pos, state.withProperty(PROPERTY_FACING, placer.getHorizontalFacing()), 2);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, BlockState state, PlayerEntity player, Hand hand, Direction facing,
			float hitX, float hitY, float hitZ)
	{
		if (!world.isRemote)
		{
			player.openGui(AetherCore.INSTANCE, AetherGuiHandler.FROSTPINE_COOLER_ID, world, pos.getX(), pos.getY(), pos.getZ());
		}

		return true;
	}

	@Override
	public int getMetaFromState(BlockState state)
	{
		return state.getValue(PROPERTY_FACING).getIndex();
	}

	@Override
	public BlockState getStateFromMeta(int meta)
	{
		Direction facing = Direction.byHorizontalIndex(meta);

		return this.getDefaultState().withProperty(PROPERTY_FACING, facing);
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, PROPERTY_FACING);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TileEntityIcestoneCooler();
	}

	@Override
	public void breakBlock(World world, BlockPos pos, BlockState state)
	{
		TileEntity tileEntity = world.getTileEntity(pos);

		if (tileEntity instanceof TileEntityIcestoneCooler)
		{
			InventoryHelper.dropInventoryItems(world, pos, (IInventory) tileEntity);
		}

		super.breakBlock(world, pos, state);
	}

	@Override
	public boolean isOpaqueCube(BlockState state)
	{
		return false;
	}

	@Override
	public boolean isFullCube(BlockState state)
	{
		return false;
	}

}
