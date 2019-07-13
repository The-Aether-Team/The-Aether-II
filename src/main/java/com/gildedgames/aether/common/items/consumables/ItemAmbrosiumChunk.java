package com.gildedgames.aether.common.items.consumables;

import com.gildedgames.aether.api.registrar.BlocksAether;
import com.gildedgames.aether.common.blocks.natural.BlockAetherGrass;
import com.gildedgames.aether.common.items.IDropOnDeath;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemAmbrosiumChunk extends Item implements IDropOnDeath
{
	@Override
	public ActionResultType onItemUse(PlayerEntity player, World world, BlockPos pos, Hand hand, Direction side,
			float hitX, float hitY, float hitZ)
	{
		ItemStack stack = player.getHeldItem(hand);

		BlockState state = world.getBlockState(pos);

		if (state.getBlock() == BlocksAether.aether_grass && state.get(BlockAetherGrass.PROPERTY_VARIANT) == BlockAetherGrass.AETHER)
		{
			if (player.canPlayerEdit(pos, side, stack))
			{
				world.setBlockState(pos,
						BlocksAether.aether_grass.getDefaultState().with(BlockAetherGrass.PROPERTY_VARIANT, BlockAetherGrass.ENCHANTED));

				if (!player.isCreative())
				{
					stack.shrink(1);
				}

				return ActionResultType.SUCCESS;
			}
		}

		return ActionResultType.FAIL;
	}

	@Override
	public int getItemBurnTime(ItemStack itemStack)
	{
		return 20000;
	}
}
