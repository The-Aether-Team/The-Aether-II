package com.gildedgames.aether.common.items.consumables;

import com.gildedgames.aether.api.registrar.BlocksAether;
import com.gildedgames.aether.common.blocks.natural.BlockAetherDirt;
import com.gildedgames.aether.common.entities.monsters.EntitySwet;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.HashMap;

public class ItemSwetJelly extends ItemAetherFood
{
	private static final HashMap<BlockState, BlockState> growables = new HashMap<>();

	static
	{
		ItemSwetJelly.growables.put(Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.DIRT), Blocks.GRASS.getDefaultState());
		ItemSwetJelly.growables.put(BlocksAether.aether_dirt.getDefaultState().withProperty(BlockAetherDirt.PROPERTY_VARIANT, BlockAetherDirt.DIRT),
				BlocksAether.aether_grass.getDefaultState());
	}

	public ItemSwetJelly()
	{
		super(5, false);

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

		if (ItemSwetJelly.growables.containsKey(state))
		{
			final BlockState nState = ItemSwetJelly.growables.get(state);

			final int radius = 1;

			for (int x = pos.getX() - radius; x <= pos.getX() + radius; x++)
			{
				for (int z = pos.getZ() - radius; z <= pos.getZ() + radius; z++)
				{
					final BlockPos nPos = new BlockPos(x, pos.getY(), z);

					if (world.getBlockState(nPos) == state && !world.getBlockState(nPos.up()).isNormalCube())
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
		return "item.aether.swet_jelly." + EntitySwet.Type.fromOrdinal(stack.getMetadata()).name;
	}
}
