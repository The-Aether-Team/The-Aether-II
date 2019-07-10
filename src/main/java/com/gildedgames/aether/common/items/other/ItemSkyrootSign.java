package com.gildedgames.aether.common.items.other;

import com.gildedgames.aether.api.registrar.BlocksAether;
import com.gildedgames.aether.common.blocks.construction.signs.BlockStandingSkyrootSign;
import com.gildedgames.aether.common.blocks.construction.signs.BlockWallSkyrootSign;
import com.gildedgames.aether.common.entities.tiles.TileEntitySkyrootSign;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ItemSkyrootSign extends Item
{
	public ItemSkyrootSign()
	{
		this.maxStackSize = 16;
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side,
			float hitX, float hitY, float hitZ)
	{
		if (side == EnumFacing.DOWN)
		{
			return EnumActionResult.FAIL;
		}
		else if (!world.getBlockState(pos).getMaterial().isSolid())
		{
			return EnumActionResult.FAIL;
		}
		else
		{
			pos = pos.offset(side);

			ItemStack stack = player.getHeldItem(hand);

			if (!player.canPlayerEdit(pos, side, stack))
			{
				return EnumActionResult.FAIL;
			}
			else if (!BlocksAether.standing_skyroot_sign.canPlaceBlockAt(world, pos))
			{
				return EnumActionResult.FAIL;
			}
			else
			{
				if (side == EnumFacing.UP)
				{
					int rotation = MathHelper.floor((double) ((player.rotationYaw + 180.0F) * 16.0F / 360.0F) + 0.5D) & 15;
					world.setBlockState(pos, BlocksAether.standing_skyroot_sign.getDefaultState().withProperty(BlockStandingSkyrootSign.ROTATION, rotation), 3);
				}
				else
				{
					world.setBlockState(pos, BlocksAether.wall_skyroot_sign.getDefaultState().withProperty(BlockWallSkyrootSign.FACING, side), 3);
				}

				stack.shrink(1);
				TileEntity tileentity = world.getTileEntity(pos);

				if (tileentity instanceof TileEntitySkyrootSign && !ItemBlock.setTileEntityNBT(world, player, pos, stack))
				{
					player.openEditSign((TileEntitySkyrootSign) tileentity);
				}

				return EnumActionResult.SUCCESS;
			}
		}
	}
}
