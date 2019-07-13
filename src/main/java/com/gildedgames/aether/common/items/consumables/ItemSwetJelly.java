package com.gildedgames.aether.common.items.consumables;

import com.gildedgames.aether.api.registrar.BlocksAether;
import com.gildedgames.aether.common.blocks.natural.BlockAetherDirt;
import com.gildedgames.aether.common.entities.monsters.EntitySwet;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashMap;

public class ItemSwetJelly extends Item
{
	private static final HashMap<BlockState, BlockState> growables = new HashMap<>();

	static
	{
		ItemSwetJelly.growables.put(Blocks.DIRT.getDefaultState(), Blocks.GRASS.getDefaultState());
		ItemSwetJelly.growables.put(BlocksAether.aether_dirt.getDefaultState().with(BlockAetherDirt.PROPERTY_VARIANT, BlockAetherDirt.DIRT),
				BlocksAether.aether_grass.getDefaultState());
	}

	private final EntitySwet.Type type;

	public ItemSwetJelly(EntitySwet.Type type, Properties properties)
	{
		super(properties);

		this.type = type;
	}

	@Override
	public ActionResultType onItemUse(final ItemUseContext context)
	{
		World world = context.getWorld();
		BlockPos pos = context.getPos();

		final BlockState state = world.getBlockState(pos);

		if (ItemSwetJelly.growables.containsKey(state))
		{
			final BlockState nState = ItemSwetJelly.growables.get(state);

			final int radius = 1;

			for (int x = pos.getX() - radius; x <= pos.getX() + radius; x++)
			{
				for (int z = pos.getZ() - radius; z <= pos.getZ() + radius; z++)
				{
					final BlockPos nPos = new BlockPos(x, pos.getY(), z);

					if (world.getBlockState(nPos) == state && !world.getBlockState(nPos.up()).isNormalCube(world, nPos.up()))
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
