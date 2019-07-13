package com.gildedgames.aether.common.items.other;

import com.gildedgames.aether.api.registrar.BlocksAether;
import com.gildedgames.aether.common.entities.monsters.EntitySwet;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashMap;

public class ItemSwetGel extends Item
{
	private static final HashMap<Block, BlockState> growables = new HashMap<>();

	static
	{
		ItemSwetGel.growables.put(Blocks.DIRT, Blocks.GRASS.getDefaultState());
		ItemSwetGel.growables.put(BlocksAether.aether_dirt, BlocksAether.aether_grass.getDefaultState());
	}

	private final EntitySwet.Type type;

	public ItemSwetGel(EntitySwet.Type type, Item.Properties properties)
	{
		super(properties);

		this.type = type;
	}

	@Override
	public ActionResultType onItemUse(final ItemUseContext context)
	{
		final World world = context.getWorld();
		final BlockPos pos = context.getPos();
		final BlockState state = world.getBlockState(pos);

		if (ItemSwetGel.growables.containsKey(state.getBlock()))
		{
			final BlockState nState = ItemSwetGel.growables.get(state.getBlock());

			final int radius = 1;

			for (int x = pos.getX() - radius; x <= pos.getX() + radius; x++)
			{
				for (int z = pos.getZ() - radius; z <= pos.getZ() + radius; z++)
				{
					final BlockPos nPos = new BlockPos(x, pos.getY(), z);

					if (world.getBlockState(nPos).getBlock() == state.getBlock() && !world.getBlockState(nPos.up()).isNormalCube(world, nPos.up()))
					{
						world.setBlockState(nPos, nState);
					}
				}
			}

			return ActionResultType.SUCCESS;
		}

		return ActionResultType.FAIL;
	}
}
