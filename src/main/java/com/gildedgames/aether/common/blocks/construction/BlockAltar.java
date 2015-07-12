package com.gildedgames.aether.common.blocks.construction;

import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.tile_entities.TileEntityAltar;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BlockAltar extends Block implements ITileEntityProvider
{
	public BlockAltar()
	{
		super(Material.rock);

		this.setHardness(2.0f);

		this.setStepSound(Block.soundTypeStone);
	}

	@Override
	public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity tileEntity)
	{
		TileEntityAltar altar = (TileEntityAltar) tileEntity;

		if (altar.getAmbrosiumCount() > 0)
		{
			EntityItem entityItem = new EntityItem(world, pos.getX() + 0.5D, pos.getY() + 1.25D, pos.getZ() + 0.5D, new ItemStack(ItemsAether.ambrosium_shard, altar.getAmbrosiumCount()));
			world.spawnEntityInWorld(entityItem);

			altar.setAmbrosiumCount(0);
		}

		if (altar.getItemToEnchant() != null)
		{
			EntityItem entityItem = new EntityItem(world, pos.getX() + 0.5D, pos.getY() + 1.25D, pos.getZ() + 0.5D, altar.getItemToEnchant());
			world.spawnEntityInWorld(entityItem);

			altar.setItemToEnchant(null);
		}

		super.harvestBlock(world, player, pos, state, tileEntity);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		TileEntityAltar altar = (TileEntityAltar) world.getTileEntity(pos);

		ItemStack stack = player.inventory.getCurrentItem();

		if (!world.isRemote)
		{
			if (stack != null)
			{
				if (stack.getItem() == ItemsAether.ambrosium_shard && altar.getAmbrosiumCount() < 20)
				{
					altar.setAmbrosiumCount(altar.getAmbrosiumCount() + 1);

					stack.stackSize -= 1;
				}
				else if (altar.getItemToEnchant() == null)
				{
					ItemStack newStack = stack.copy();
					newStack.stackSize = 1;

					altar.setItemToEnchant(newStack);

					stack.stackSize--;
				}
			}
			else
			{
				ItemStack stackToDrop = null;

				if (altar.getItemToEnchant() != null)
				{
					stackToDrop = altar.getItemToEnchant();
					altar.setItemToEnchant(null);
				}
				else
				{
					int count = player.isSneaking() ? altar.getAmbrosiumCount() : 1;

					if (altar.getAmbrosiumCount() - count >= 0)
					{
						stackToDrop = new ItemStack(ItemsAether.ambrosium_shard, count);

						altar.setAmbrosiumCount(altar.getAmbrosiumCount() - count);
					}
				}

				if (stackToDrop != null)
				{
					EntityItem entityItem = new EntityItem(world, pos.getX() + 0.5D, pos.getY() + 1.3D, pos.getZ() + 0.5D, stackToDrop);
					world.spawnEntityInWorld(entityItem);
				}
			}

			altar.update();
		}

		return true;
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public boolean isFullCube()
	{
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TileEntityAltar();
	}
}
