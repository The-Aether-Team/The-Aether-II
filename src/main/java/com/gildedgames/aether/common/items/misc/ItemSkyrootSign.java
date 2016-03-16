package com.gildedgames.aether.common.items.misc;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.construction.skyroot_sign.BlockStandingSkyrootSign;
import com.gildedgames.aether.common.blocks.construction.skyroot_sign.BlockWallSkyrootSign;
import com.gildedgames.aether.common.tile_entities.TileEntitySkyrootSign;
import net.minecraft.block.BlockStandingSign;
import net.minecraft.block.BlockWallSign;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemSign;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

/**
 * Created by Chris on 3/14/2016.
 */
public class ItemSkyrootSign extends Item
{
	public ItemSkyrootSign()
	{
		this.maxStackSize = 16;
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		if (side == EnumFacing.DOWN)
		{
			return false;
		}
		else if (!world.getBlockState(pos).getBlock().getMaterial().isSolid())
		{
			return false;
		}
		else
		{
			pos = pos.offset(side);

			if (!player.canPlayerEdit(pos, side, stack))
			{
				return false;
			}
			else if (!BlocksAether.standing_skyroot_sign.canPlaceBlockAt(world, pos))
			{
				return false;
			}
			else if (world.isRemote)
			{
				return true;
			}
			else
			{
				if (side == EnumFacing.UP)
				{
					int rotation = MathHelper.floor_double((double) ((player.rotationYaw + 180.0F) * 16.0F / 360.0F) + 0.5D) & 15;
					world.setBlockState(pos, BlocksAether.standing_skyroot_sign.getDefaultState().withProperty(BlockStandingSkyrootSign.ROTATION, rotation), 3);
				}
				else
				{
					world.setBlockState(pos, BlocksAether.wall_skyroot_sign.getDefaultState().withProperty(BlockWallSkyrootSign.FACING, side), 3);
				}

				--stack.stackSize;
				TileEntity tileentity = world.getTileEntity(pos);

				if (tileentity instanceof TileEntitySkyrootSign && !ItemBlock.setTileEntityNBT(world, player, pos, stack))
				{
					player.openEditSign((TileEntitySign) tileentity);
				}

				return true;
			}
		}
	}
}