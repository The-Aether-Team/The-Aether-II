package com.gildedgames.aether.common.registry.content;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.decorative.BlockSkyrootDecorative;
import com.gildedgames.aether.common.entities.EntitiesAether;
import com.gildedgames.aether.common.items.ItemsAether;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CreativeTabsAether
{
	public static final CreativeTabs NATURAL_BLOCKS = new CreativeTabs("aether.natural_blocks")
	{
		@Override
		public ItemStack getTabIconItem()
		{
			return new ItemStack(BlocksAether.aether_grass);
		}
	};

	public static final CreativeTabs CONSTRUCTION = new CreativeTabs("aether.construction")
	{
		@Override
		public ItemStack getTabIconItem()
		{
			return new ItemStack(BlocksAether.holystone_brick);
		}
	};

	public static final CreativeTabs UTILITY = new CreativeTabs("aether.utility_blocks")
	{
		@Override
		public ItemStack getTabIconItem()
		{
			return new ItemStack(BlocksAether.altar);
		}
	};

	public static final CreativeTabs DECORATIVE_BLOCKS = new CreativeTabs("aether.visual_variants")
	{
		@Override
		public ItemStack getTabIconItem()
		{
			return new ItemStack(BlocksAether.skyroot_decorative, 1, BlockSkyrootDecorative.HIGHLIGHT.getMeta());
		}
	};

	public static final CreativeTabs MISCELLANEOUS = new CreativeTabsMisc("aether.miscellaneous")
	{
		@Override
		public ItemStack getTabIconItem()
		{
			return new ItemStack(ItemsAether.skyroot_poison_bucket);
		}
	};

	public static final CreativeTabs MATERIALS = new CreativeTabs("aether.materials")
	{
		@Override
		public ItemStack getTabIconItem()
		{
			return new ItemStack(ItemsAether.ambrosium_shard);
		}
	};

	public static final CreativeTabs CONSUMABLES = new CreativeTabs("aether.consumables")
	{
		@Override
		public ItemStack getTabIconItem()
		{
			return new ItemStack(ItemsAether.orange);
		}
	};

	public static final CreativeTabs TOOLS = new CreativeTabs("aether.tools")
	{
		@Override
		public ItemStack getTabIconItem()
		{
			return new ItemStack(ItemsAether.zanite_pickaxe);
		}
	};

	public static final CreativeTabs WEAPONS = new CreativeTabs("aether.weapons")
	{
		@Override
		public ItemStack getTabIconItem()
		{
			return new ItemStack(ItemsAether.gravitite_sword);
		}
	};

	public static final CreativeTabs ARMOR = new CreativeTabs("aether.armor")
	{
		@Override
		public ItemStack getTabIconItem()
		{
			return new ItemStack(ItemsAether.zanite_helmet);
		}
	};

	public static final CreativeTabs COMPANIONS = new CreativeTabs("aether.companions")
	{
		@Override
		public ItemStack getTabIconItem()
		{
			return new ItemStack(ItemsAether.pink_baby_swet);
		}
	};

	public static final CreativeTabs RINGS = new CreativeTabs("aether.rings")
	{
		@Override
		public ItemStack getTabIconItem()
		{
			return new ItemStack(ItemsAether.zanite_ring);
		}
	};

	public static final CreativeTabs NECKWEAR = new CreativeTabs("aether.neckwear")
	{
		@Override
		public ItemStack getTabIconItem()
		{
			return new ItemStack(ItemsAether.amulet_of_growth);
		}
	};

	public static final CreativeTabs RELICS = new CreativeTabs("aether.relics")
	{
		@Override
		public ItemStack getTabIconItem()
		{
			return new ItemStack(ItemsAether.regeneration_stone);
		}
	};

	public static final CreativeTabs CHARMS = new CreativeTabs("aether.charms")
	{
		@Override
		public ItemStack getTabIconItem()
		{
			return new ItemStack(ItemsAether.charm_mve_spd_03);
		}
	};

	private static abstract class CreativeTabsMisc extends CreativeTabs
	{
		private CreativeTabsMisc(final String unlocalizedName)
		{
			super(unlocalizedName);
		}

		@Override
		@SideOnly(Side.CLIENT)
		public void displayAllRelevantItems(final NonNullList<ItemStack> list)
		{
			super.displayAllRelevantItems(list);

			list.sort((o1, o2) -> {
				final boolean b1 = o1.getItem() instanceof ItemBlock;
				final boolean b2 = o2.getItem() instanceof ItemBlock;

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
