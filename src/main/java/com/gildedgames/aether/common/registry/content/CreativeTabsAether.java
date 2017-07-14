package com.gildedgames.aether.common.registry.content;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.decorative.BlockSkyrootDecorative;
import com.gildedgames.aether.common.blocks.natural.BlockAetherGrass;
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
	public static final Tab NATURAL_BLOCKS = new Tab("aether.natural_blocks");

	public static final Tab CONSTRUCTION = new Tab("aether.construction");

	public static final Tab UTILITY = new Tab("aether.utility_blocks");

	public static final Tab DECORATIVE_BLOCKS = new Tab("aether.visual_variants");

	public static final Tab MISCELLANEOUS = new TabMisc("aether.miscellaneous");

	public static final Tab MATERIALS = new Tab("aether.materials");

	public static final Tab CONSUMABLES = new Tab("aether.consumables");

	public static final Tab TOOLS = new Tab("aether.tools");

	public static final Tab WEAPONS = new Tab("aether.weapons");

	public static final Tab ARMOR = new Tab("aether.armor");

	public static final Tab COMPANIONS = new Tab("aether.companions");

	public static final Tab RINGS = new Tab("aether.rings");

	public static final Tab NECKWEAR = new Tab("aether.neckwear");

	public static final Tab RELICS = new Tab("aether.relics");

	public static final Tab CHARMS = new Tab("aether.charms");

	@SideOnly(Side.CLIENT)
	public static void registerTabIcons()
	{
		NATURAL_BLOCKS.setDisplayStack(new ItemStack(BlocksAether.aether_grass, 1, BlockAetherGrass.AETHER.getMeta()));
		CONSTRUCTION.setDisplayStack(new ItemStack(BlocksAether.holystone_brick));
		DECORATIVE_BLOCKS.setDisplayStack(new ItemStack(BlocksAether.skyroot_decorative, 1, BlockSkyrootDecorative.HIGHLIGHT.getMeta()));
		UTILITY.setDisplayStack(new ItemStack(BlocksAether.altar));
		MATERIALS.setDisplayStack(new ItemStack(ItemsAether.ambrosium_shard));
		TOOLS.setDisplayStack(new ItemStack(ItemsAether.zanite_pickaxe));
		WEAPONS.setDisplayStack(new ItemStack(ItemsAether.gravitite_sword));
		ARMOR.setDisplayStack(new ItemStack(ItemsAether.zanite_helmet));
		CONSUMABLES.setDisplayStack(new ItemStack(ItemsAether.orange));
		COMPANIONS.setDisplayStack(new ItemStack(ItemsAether.pink_baby_swet));
		MISCELLANEOUS.setDisplayStack(new ItemStack(ItemsAether.skyroot_poison_bucket));

		RINGS.setDisplayStack(new ItemStack(ItemsAether.zanite_ring));
		NECKWEAR.setDisplayStack(new ItemStack(ItemsAether.amulet_of_growth));
		RELICS.setDisplayStack(new ItemStack(ItemsAether.regeneration_stone));
		CHARMS.setDisplayStack(new ItemStack(ItemsAether.charm_mve_spd_03));
	}

	private static class Tab extends CreativeTabs
	{
		private ItemStack stack = ItemStack.EMPTY;

		private Tab(String unlocalizedName)
		{
			super(unlocalizedName);
		}

		@SideOnly(Side.CLIENT)
		private void setDisplayStack(ItemStack stack)
		{
			this.stack = stack;
		}

		@Override
		@SideOnly(Side.CLIENT)
		public ItemStack getTabIconItem()
		{
			return this.stack;
		}
	}

	private static class TabMisc extends Tab
	{
		private TabMisc(String unlocalizedName)
		{
			super(unlocalizedName);
		}

		@Override
		@SideOnly(Side.CLIENT)
		public void displayAllRelevantItems(NonNullList<ItemStack> list)
		{
			super.displayAllRelevantItems(list);

			list.sort((o1, o2) -> {
				boolean b1 = o1.getItem() instanceof ItemBlock;
				boolean b2 = o2.getItem() instanceof ItemBlock;

				return (b2 == b1 ? 0 : (b1 ? 1 : -1));
			});

			for (ResourceLocation res : EntitiesAether.getRegisteredSpawnEggs())
			{
				ItemStack stack = new ItemStack(Items.SPAWN_EGG);

				ItemMonsterPlacer.applyEntityIdToItemStack(stack, res);

				list.add(stack);
			}
		}
	}
}
