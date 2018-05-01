package com.gildedgames.aether.common.items.consumables;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.natural.plants.BlockBrettlPlant;
import com.gildedgames.aether.common.items.IDropOnDeath;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;

public class ItemBrettlCane extends Item implements IPlantable, IDropOnDeath
{
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		ItemStack itemstack = player.getHeldItem(hand);
		IBlockState state = worldIn.getBlockState(pos);
		if (facing == EnumFacing.UP && player.canPlayerEdit(pos.offset(facing), facing, itemstack) && this.isPlantable(worldIn, pos) && worldIn
				.isAirBlock(pos.up()) && worldIn.isAirBlock(pos.up(2)) && worldIn.isAirBlock(pos.up(3)))
		{
			worldIn.setBlockState(pos.up(), BlocksAether.brettl_plant.getStateForPlacement(worldIn, pos, facing, hitX, hitY, hitZ, 0, player));
			worldIn.setBlockState(pos.up(2), BlocksAether.brettl_plant.getDefaultState().withProperty(BlockBrettlPlant.PROPERTY_VARIANT, BlockBrettlPlant.TOP)
					.withProperty(BlockBrettlPlant.PROPERTY_HARVESTABLE, false));
			itemstack.shrink(1);
			return EnumActionResult.SUCCESS;
		}
		else
		{
			return EnumActionResult.FAIL;
		}
	}

	@Override
	public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos)
	{
		return null;
	}

	@Override
	public IBlockState getPlant(IBlockAccess world, BlockPos pos)
	{
		return null;
	}

	private boolean isPlantable(World world, BlockPos pos)
	{
		if (world.isAirBlock(pos.up()) && world.isAirBlock(pos.up(2)))
		{
			return world.getBlockState(pos).getBlock() == BlocksAether.quicksoil;
		}
		return false;
	}
}
