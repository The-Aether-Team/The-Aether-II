package com.gildedgames.aether.common.blocks.construction;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.tiles.TileEntityAltar;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockAltar extends Block implements ITileEntityProvider
{
	public static final PropertyDirection PROPERTY_FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

	public BlockAltar()
	{
		super(Material.ROCK);

		this.setHardness(2.0f);

		this.setSoundType(SoundType.STONE);

		this.setDefaultState(this.getBlockState().getBaseState().withProperty(PROPERTY_FACING, EnumFacing.NORTH));
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state)
	{
		TileEntityAltar altar = (TileEntityAltar) world.getTileEntity(pos);
		altar.dropContents();

		super.breakBlock(world, pos, state);
	}

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta,
			EntityLivingBase placer, ItemStack stack)
	{
		return this.getDefaultState().withProperty(PROPERTY_FACING, placer.getHorizontalFacing());
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
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack heldItem,
			EnumFacing side, float hitX, float hitY, float hitZ)
	{
		if (!world.isRemote)
		{
			TileEntityAltar altar = (TileEntityAltar) world.getTileEntity(pos);

			ItemStack heldStack = player.inventory.getCurrentItem();

			if (heldStack != null)
			{
				if (heldStack.isItemEqual(altar.getStackOnAltar()))
				{
					this.dropNextItem(altar, world);
				}
				else if (heldStack.getItem() == ItemsAether.ambrosium_shard)
				{
					if (altar.getAmbrosiumCount() < 16)
					{
						if (!player.capabilities.isCreativeMode)
						{
							heldStack.stackSize -= 1;
						}

						altar.addAmbrosiumShard();
					}
				}
				else if (AetherCore.PROXY.getRecipeManager().getAltarRegistry().isEnchantableItem(heldStack))
				{
					ItemStack stack = heldStack.copy();
					stack.stackSize = 1;

					if (altar.getStackOnAltar() != null)
					{
						world.spawnEntity(altar.createEntityItemAboveAltar(altar.getStackOnAltar()));
					}

					altar.setStackOnAltar(stack);

					heldStack.stackSize--;
				}
			}
			else
			{
				this.dropNextItem(altar, world);
			}

			altar.attemptCrafting();
		}

		return true;
	}

	private void dropNextItem(TileEntityAltar altar, World world)
	{
		ItemStack stack = null;

		if (altar.getStackOnAltar() != null)
		{
			stack = altar.getStackOnAltar();

			altar.setStackOnAltar(null);
		}
		else if (altar.getAmbrosiumCount() > 0)
		{
			stack = new ItemStack(ItemsAether.ambrosium_shard, 1);

			altar.removeAmbrosiumShard();
		}

		if (stack != null)
		{
			world.spawnEntity(altar.createEntityItemAboveAltar(stack));
		}
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
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TileEntityAltar();
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, PROPERTY_FACING);
	}
}
