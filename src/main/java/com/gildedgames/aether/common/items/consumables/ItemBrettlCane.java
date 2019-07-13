package com.gildedgames.aether.common.items.consumables;

import com.gildedgames.aether.api.registrar.BlocksAether;
import com.gildedgames.aether.common.blocks.natural.plants.BlockBrettlPlant;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.PlantType;
import net.minecraftforge.common.IPlantable;

public class ItemBrettlCane extends Item implements IPlantable
{
	public ItemBrettlCane(Properties properties)
	{
		super(properties);
	}

	@Override
	public ActionResultType onItemUse(ItemUseContext context)
	{
		PlayerEntity player = context.getPlayer();

		if (player == null)
		{
			return ActionResultType.FAIL;
		}

		ItemStack stack = context.getItem();
		World world = context.getWorld();
		BlockPos pos = context.getPos();
		Direction facing = context.getFace();
		BlockState state = world.getBlockState(pos);
		if (facing == Direction.UP && player.canPlayerEdit(pos.offset(facing), facing, stack) && this.isPlantable(world, pos) && world
				.isAirBlock(pos.up()) && world.isAirBlock(pos.up(2)) && world.isAirBlock(pos.up(3)))
		{
			world.setBlockState(pos.up(), BlocksAether.brettl_plant.getStateForPlacement(new BlockItemUseContext(context)));
			world.setBlockState(pos.up(2), BlocksAether.brettl_plant.getDefaultState().with(BlockBrettlPlant.PROPERTY_VARIANT, BlockBrettlPlant.TOP)
					.with(BlockBrettlPlant.PROPERTY_HARVESTABLE, false));
			stack.shrink(1);
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
