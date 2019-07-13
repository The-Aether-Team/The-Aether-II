package com.gildedgames.aether.common.items.consumables;

import com.gildedgames.aether.api.registrar.BlocksAether;
import com.gildedgames.aether.common.blocks.natural.plants.BlockBrettlPlant;
import com.gildedgames.aether.common.items.IDropOnDeath;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.PlantType;
import net.minecraftforge.common.IPlantable;

public class ItemBrettlCane extends Item implements IPlantable, IDropOnDeath
{
	@Override
	public ActionResultType onItemUse(PlayerEntity player, World worldIn, BlockPos pos, Hand hand, Direction facing, float hitX, float hitY, float hitZ)
	{
		ItemStack itemstack = player.getHeldItem(hand);
		BlockState state = worldIn.getBlockState(pos);
		if (facing == Direction.UP && player.canPlayerEdit(pos.offset(facing), facing, itemstack) && this.isPlantable(worldIn, pos) && worldIn
				.isAirBlock(pos.up()) && worldIn.isAirBlock(pos.up(2)) && worldIn.isAirBlock(pos.up(3)))
		{
			worldIn.setBlockState(pos.up(), BlocksAether.brettl_plant.getStateForPlacement(worldIn, pos, facing, hitX, hitY, hitZ, 0, player));
			worldIn.setBlockState(pos.up(2), BlocksAether.brettl_plant.getDefaultState().with(BlockBrettlPlant.PROPERTY_VARIANT, BlockBrettlPlant.TOP)
					.with(BlockBrettlPlant.PROPERTY_HARVESTABLE, false));
			itemstack.shrink(1);
			return ActionResultType.SUCCESS;
		}
		else
		{
			return ActionResultType.FAIL;
		}
	}

	@Override
	public PlantType getPlantType(IBlockReader world, BlockPos pos)
	{
		return null;
	}

	@Override
	public BlockState getPlant(IBlockReader world, BlockPos pos)
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
