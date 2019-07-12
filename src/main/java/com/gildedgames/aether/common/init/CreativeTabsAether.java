package com.gildedgames.aether.common.init;

import com.gildedgames.aether.api.registrar.BlocksAether;
import com.gildedgames.aether.api.registrar.ItemsAether;
import com.gildedgames.aether.common.blocks.decorative.BlockSkyrootDecorative;
import com.gildedgames.aether.common.blocks.decorative.BlockTherastoneDecorative;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.Items;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class CreativeTabsAether
{
	public static final ItemGroup TAB_NATURAL_BLOCKS = new ItemGroup("aether.natural_blocks")
	{
		@Override
		public ItemStack createIcon()
		{
			return new ItemStack(BlocksAether.aether_grass);
		}
	};

	public static final ItemGroup TAB_CONSTRUCTION = new ItemGroup("aether.construction")
	{
		@Override
		public ItemStack createIcon()
		{
			return new ItemStack(BlocksAether.holystone_brick);
		}
	};

	public static final ItemGroup TAB_UTILITY = new ItemGroup("aether.utility_blocks")
	{
		@Override
		public ItemStack createIcon()
		{
			return new ItemStack(BlocksAether.altar);
		}
	};

	public static final ItemGroup TAB_DECORATIVE_BLOCKS = new ItemGroup("aether.visual_variants")
	{
		@Override
		public ItemStack createIcon()
		{
			return new ItemStack(BlocksAether.skyroot_decorative, 1, BlockSkyrootDecorative.HIGHLIGHT.getMeta());
		}
	};

	public static final ItemGroup TAB_MISCELLANEOUS = new CreativeTabsMisc("aether.miscellaneous")
	{
		@Override
		public ItemStack createIcon()
		{
			return new ItemStack(ItemsAether.skyroot_poison_bucket);
		}
	};

	public static final ItemGroup TAB_MATERIALS = new ItemGroup("aether.materials")
	{
		@Override
		public ItemStack createIcon()
		{
			return new ItemStack(ItemsAether.ambrosium_shard);
		}
	};

	public static final ItemGroup TAB_CONSUMABLES = new ItemGroup("aether.consumables")
	{
		@Override
		public ItemStack createIcon()
		{
			return new ItemStack(ItemsAether.orange);
		}
	};

	public static final ItemGroup TAB_TOOLS = new ItemGroup("aether.tools")
	{
		@Override
		public ItemStack createIcon()
		{
			return new ItemStack(ItemsAether.zanite_pickaxe);
		}
	};

	public static final ItemGroup TAB_WEAPONS = new ItemGroup("aether.weapons")
	{
		@Override
		public ItemStack createIcon()
		{
			return new ItemStack(ItemsAether.gravitite_sword);
		}
	};

	public static final ItemGroup TAB_ARMOR = new ItemGroup("aether.armor")
	{
		@Override
		public ItemStack createIcon()
		{
			return new ItemStack(ItemsAether.zanite_helmet);
		}
	};

	public static final ItemGroup TAB_THERA = new ItemGroup("aether.thera")
	{
		@Override
		public ItemStack createIcon()
		{
			return new ItemStack(BlocksAether.therastone_brick_decorative, 1, BlockTherastoneDecorative.KEYSTONE.getMeta());
		}
	};

	private static abstract class CreativeTabsMisc extends ItemGroup
	{
		private CreativeTabsMisc(final String unlocalizedName)
		{
			super(unlocalizedName);
		}

		@Override
		@OnlyIn(Dist.CLIENT)
		public void displayAllRelevantItems(final NonNullList<ItemStack> list)
		{
			super.displayAllRelevantItems(list);

			list.sort((o1, o2) -> {
				final boolean b1 = o1.getItem() instanceof BlockItem;
				final boolean b2 = o2.getItem() instanceof BlockItem;

				return (b2 == b1 ? 0 : (b1 ? 1 : -1));
			});

			for (final ResourceLocation res : EntitiesAether.getRegisteredSpawnEggs())
			{
				final ItemStack stack = new ItemStack(Items.SPAWN_EGG);

				ItemMonsterPlacer.applyEntityIdToItemStack(stack, res);

				list.add(stack);
			}
		}
	}
}
