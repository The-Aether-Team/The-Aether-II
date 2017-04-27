package com.gildedgames.aether.common.blocks.containers;

import com.gildedgames.aether.common.entities.tiles.TileEntityHolystoneFurnace;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockHolystoneFurnace extends BlockContainer
{
	public static final PropertyBool PROPERTY_IS_LIT = PropertyBool.create("is_lit");

	public static final PropertyDirection PROPERTY_FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

	public static final int UNLIT_META = 0, LIT_META = 1;

	public BlockHolystoneFurnace()
	{
		super(Material.ROCK);

		this.setHardness(3.5f);

		this.setDefaultState(this.getBlockState().getBaseState()
				.withProperty(PROPERTY_IS_LIT, Boolean.FALSE)
				.withProperty(PROPERTY_FACING, EnumFacing.NORTH));
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		world.setBlockState(pos, state.withProperty(PROPERTY_FACING, placer.getHorizontalFacing()), 2);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing,
			float hitX, float hitY, float hitZ)
	{
		if (!world.isRemote)
		{
			TileEntity tileEntity = world.getTileEntity(pos);

			if (tileEntity instanceof TileEntityHolystoneFurnace)
			{
				player.displayGUIChest((IInventory) tileEntity);
			}
		}

		return true;
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state)
	{
		TileEntity tileEntity = world.getTileEntity(pos);

		if (tileEntity instanceof TileEntityHolystoneFurnace)
		{
			InventoryHelper.dropInventoryItems(world, pos, (IInventory) tileEntity);
		}

		super.breakBlock(world, pos, state);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand)
	{
		if (state.getValue(PROPERTY_IS_LIT))
		{
			EnumFacing facing = state.getValue(PROPERTY_FACING).getOpposite();

			double x = pos.getX() + 0.5D;
			double y = pos.getY() + rand.nextDouble() * 6.0D / 16.0D + 0.125D;
			double z = pos.getZ() + 0.5D;

			double xOffset = 0.5D;
			double zOffset = rand.nextDouble() * 0.6D - 0.3D;

			if (rand.nextDouble() < 0.1D)
			{
				world.playSound(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
			}

			switch (facing)
			{
			case WEST:
				world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, x - xOffset, y, z + zOffset, 0.0D, 0.0D, 0.0D);
				world.spawnParticle(EnumParticleTypes.FLAME, x - xOffset, y, z + zOffset, 0.0D, 0.0D, 0.0D);
				break;
			case EAST:
				world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, x + xOffset, y, z + zOffset, 0.0D, 0.0D, 0.0D);
				world.spawnParticle(EnumParticleTypes.FLAME, x + xOffset, y, z + zOffset, 0.0D, 0.0D, 0.0D);
				break;
			case NORTH:
				world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, x + zOffset, y, z - xOffset, 0.0D, 0.0D, 0.0D);
				world.spawnParticle(EnumParticleTypes.FLAME, x + zOffset, y, z - xOffset, 0.0D, 0.0D, 0.0D);
				break;
			case SOUTH:
				world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, x + zOffset, y, z + xOffset, 0.0D, 0.0D, 0.0D);
				world.spawnParticle(EnumParticleTypes.FLAME, x + zOffset, y, z + xOffset, 0.0D, 0.0D, 0.0D);
			}
		}
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state)
	{
		return EnumBlockRenderType.MODEL;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return true;
	}

	@Override
	public boolean isFullCube(IBlockState state)
	{
		return true;
	}

	@Override
	public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos)
	{
		return state.getValue(PROPERTY_IS_LIT) ? 13 : 0;
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		int meta = state.getValue(PROPERTY_FACING).getIndex();

		if (state.getValue(PROPERTY_IS_LIT))
		{
			meta |= 8;
		}

		return meta;
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		EnumFacing facing = EnumFacing.getHorizontal(meta & 7);

		boolean isLit = (meta & 8) == 8;

		return this.getDefaultState().withProperty(PROPERTY_FACING, facing).withProperty(PROPERTY_IS_LIT, isLit);
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, PROPERTY_IS_LIT, PROPERTY_FACING);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TileEntityHolystoneFurnace();
	}
}
