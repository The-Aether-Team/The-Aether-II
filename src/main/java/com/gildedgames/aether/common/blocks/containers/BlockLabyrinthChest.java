package com.gildedgames.aether.common.blocks.containers;

import com.gildedgames.aether.common.entities.dungeon.labyrinth.EntityChestMimic;
import com.gildedgames.aether.common.tile_entities.TileEntityLabyrinthChest;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.ILockableContainer;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class BlockLabyrinthChest extends BlockContainer
{
	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
	public final int chestType;

	public BlockLabyrinthChest()
	{
		super(Material.ROCK);
		this.chestType = 1;
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		if (world.isRemote)
		{
			return true;
		}
		else
		{
			TileEntity te = world.getTileEntity(pos);

			if (te instanceof TileEntityLabyrinthChest)
			{
				TileEntityLabyrinthChest chest = (TileEntityLabyrinthChest)te;

				if (chest.isMimic())
				{
					chest.clear();

					EntityChestMimic mimic = new EntityChestMimic(world);

					mimic.setPositionAndUpdate(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D);

					mimic.motionY = 0.5D;

					world.spawnEntityInWorld(mimic);

					world.setBlockToAir(pos);

					return true;
				}
			}

			ILockableContainer ilockablecontainer = this.getLockableContainer(world, pos);

			if (ilockablecontainer != null)
			{
				playerIn.displayGUIChest(ilockablecontainer);
			}

			return true;
		}
	}

	public ILockableContainer getLockableContainer(World worldIn, BlockPos pos)
	{
		TileEntity tileentity = worldIn.getTileEntity(pos);

		if (tileentity instanceof TileEntityLabyrinthChest)
		{
			ILockableContainer ilockablecontainer = (TileEntityLabyrinthChest)tileentity;

			if (this.isBlocked(worldIn, pos))
			{
				return null;
			}
			else
			{
				for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL)
				{
					BlockPos blockpos = pos.offset(enumfacing);
					Block block = worldIn.getBlockState(blockpos).getBlock();

					if (block == this)
					{
						if (this.isBlocked(worldIn, blockpos))
						{
							return null;
						}
					}
				}

				return ilockablecontainer;
			}
		}
		else
		{
			return null;
		}
	}

	private boolean isBlocked(World worldIn, BlockPos pos)
	{
		return this.isBelowSolidBlock(worldIn, pos) || this.isOcelotSittingOnChest(worldIn, pos);
	}

	private boolean isBelowSolidBlock(World worldIn, BlockPos pos)
	{
		return worldIn.isSideSolid(pos.up(), EnumFacing.DOWN, false);
	}

	private boolean isOcelotSittingOnChest(World worldIn, BlockPos pos)
	{
		for (Entity entity : worldIn.getEntitiesWithinAABB(EntityOcelot.class, new AxisAlignedBB((double)pos.getX(), (double)(pos.getY() + 1), (double)pos.getZ(), (double)(pos.getX() + 1), (double)(pos.getY() + 2), (double)(pos.getZ() + 1))))
		{
			EntityOcelot entityocelot = (EntityOcelot)entity;

			if (entityocelot.isSitting())
			{
				return true;
			}
		}

		return false;
	}

	@Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		EnumFacing enumfacing = EnumFacing.getHorizontal(MathHelper.floor_double((double)(placer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3).getOpposite();
		BlockPos blockpos = pos.north();
		BlockPos blockpos1 = pos.south();
		BlockPos blockpos2 = pos.west();
		BlockPos blockpos3 = pos.east();
		boolean flag = this == worldIn.getBlockState(blockpos).getBlock();
		boolean flag1 = this == worldIn.getBlockState(blockpos1).getBlock();
		boolean flag2 = this == worldIn.getBlockState(blockpos2).getBlock();
		boolean flag3 = this == worldIn.getBlockState(blockpos3).getBlock();

		if (!flag && !flag1 && !flag2 && !flag3)
		{
			worldIn.setBlockState(pos, state, 3);
		}
		else if (enumfacing.getAxis() != EnumFacing.Axis.X || !flag && !flag1)
		{
			if (enumfacing.getAxis() == EnumFacing.Axis.Z && (flag2 || flag3))
			{
				if (flag2)
				{
					worldIn.setBlockState(blockpos2, state, 3);
				}
				else
				{
					worldIn.setBlockState(blockpos3, state, 3);
				}

				worldIn.setBlockState(pos, state, 3);
			}
		}
		else
		{
			if (flag)
			{
				worldIn.setBlockState(blockpos, state, 3);
			}
			else
			{
				worldIn.setBlockState(blockpos1, state, 3);
			}

			worldIn.setBlockState(pos, state, 3);
		}


		TileEntity tileentity = worldIn.getTileEntity(pos);

		if (tileentity instanceof TileEntityLabyrinthChest)
		{
			TileEntityLabyrinthChest chest = (TileEntityLabyrinthChest)tileentity;

			if (stack.hasDisplayName())
			{
				chest.setCustomName(stack.getDisplayName());
			}

			chest.setIsMimic(false);
		}
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state)
	{
		TileEntity chest = world.getTileEntity(pos);

		if (chest instanceof TileEntityLabyrinthChest)
		{
			InventoryHelper.dropInventoryItems(world, pos, (IInventory) chest);
		}

		super.breakBlock(world, pos, state);
	}

	@Override
	public int getComparatorInputOverride(IBlockState blockState, World worldIn, BlockPos pos)
	{
		return Container.calcRedstoneFromInventory(this.getLockableContainer(worldIn, pos));
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityLabyrinthChest();
	}

	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, FACING);
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		EnumFacing facing = EnumFacing.getHorizontal(meta & 7);

		return this.getDefaultState().withProperty(FACING, facing);
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(FACING).getIndex();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumBlockRenderType getRenderType(IBlockState state)
	{
		return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
	}
}
