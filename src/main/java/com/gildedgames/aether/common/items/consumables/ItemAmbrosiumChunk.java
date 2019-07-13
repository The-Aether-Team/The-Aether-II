package com.gildedgames.aether.common.items.consumables;

import com.gildedgames.aether.api.registrar.BlocksAether;
import com.gildedgames.aether.common.blocks.natural.BlockAetherGrass;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemAmbrosiumChunk extends Item
{
	public ItemAmbrosiumChunk(Properties properties)
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

		BlockPos pos = context.getPos();
		ItemStack stack = context.getItem();
		Direction side = context.getFace();

		World world = context.getWorld();

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
	public int getBurnTime(ItemStack itemStack)
	{
		return 20000;
	}
}
