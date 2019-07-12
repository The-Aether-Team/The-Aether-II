package com.gildedgames.aether.common.items.other;

import com.gildedgames.aether.api.registrar.BlocksAether;
import com.gildedgames.aether.common.entities.monsters.EntitySwet;
import com.gildedgames.aether.common.items.IDropOnDeath;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemGroup;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.HashMap;

public class ItemSwetGel extends Item implements IDropOnDeath
{
	private static final HashMap<Block, BlockState> growables = new HashMap<>();

	static
	{
		ItemSwetGel.growables.put(Blocks.DIRT, Blocks.GRASS.getDefaultState());
		ItemSwetGel.growables.put(BlocksAether.aether_dirt, BlocksAether.aether_grass.getDefaultState());
	}

	public ItemSwetGel()
	{
		super();

		this.setHasSubtypes(true);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void getSubItems(final ItemGroup tab, final NonNullList<ItemStack> subItems)
	{
		if (!this.isInCreativeTab(tab))
		{
			return;
		}

		for (final EntitySwet.Type types : EntitySwet.Type.values())
		{
			subItems.add(new ItemStack(this, 1, types.ordinal()));
		}
	}

	@Override
	public ActionResultType onItemUse(final PlayerEntity player, final World world, final BlockPos pos, final Hand hand, final Direction facing,
			final float hitX, final float hitY, final float hitZ)
	{
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

					if (world.getBlockState(nPos).getBlock() == state.getBlock() && !world.getBlockState(nPos.up()).isNormalCube())
					{
						world.setBlockState(nPos, nState);
					}
				}
			}

			return ActionResultType.SUCCESS;
		}

		return ActionResultType.FAIL;
	}

	@Override
	public String getTranslationKey(final ItemStack stack)
	{
		return "item.aether.swet_gel." + EntitySwet.Type.fromOrdinal(stack.getMetadata()).name;
	}
}
