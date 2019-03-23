package com.gildedgames.aether.common.items.misc;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.entities.living.mobs.EntitySwet;
import com.gildedgames.aether.common.items.IDropOnDeath;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.HashMap;

public class ItemSwetGel extends Item implements IDropOnDeath
{
	private static final HashMap<Block, IBlockState> growables = new HashMap<>();

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
	public void getSubItems(final CreativeTabs tab, final NonNullList<ItemStack> subItems)
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
	public EnumActionResult onItemUse(final EntityPlayer player, final World world, final BlockPos pos, final EnumHand hand, final EnumFacing facing,
			final float hitX, final float hitY, final float hitZ)
	{
		final IBlockState state = world.getBlockState(pos);

		if (ItemSwetGel.growables.containsKey(state.getBlock()))
		{
			final IBlockState nState = ItemSwetGel.growables.get(state.getBlock());

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

			return EnumActionResult.SUCCESS;
		}

		return EnumActionResult.FAIL;
	}

	@Override
	public String getTranslationKey(final ItemStack stack)
	{
		return "item.aether.swet_gel." + EntitySwet.Type.fromOrdinal(stack.getMetadata()).name;
	}
}
