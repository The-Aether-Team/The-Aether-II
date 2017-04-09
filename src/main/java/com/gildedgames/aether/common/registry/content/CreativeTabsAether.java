package com.gildedgames.aether.common.registry.content;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.construction.BlockSkyrootPlanks;
import com.gildedgames.aether.common.blocks.natural.BlockAetherGrass;
import com.gildedgames.aether.common.entities.EntitiesAether;
import com.gildedgames.aether.common.items.ItemsAether;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityList;
import net.minecraft.init.Items;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CreativeTabsAether
{

	public static final CreativeTab NATURAL_BLOCKS = new CreativeTab("aether.natural_blocks");

	public static final CreativeTab CONSTRUCTION = new CreativeTab("aether.construction");

	public static final CreativeTab UTILITY = new CreativeTab("aether.utility_blocks");

	public static final CreativeTab VISUAL_VARIANTS = new CreativeTab("aether.visual_variants");

	public static final CreativeTab MATERIALS = new CreativeTab("aether.materials");

	public static final CreativeTab CONSUMABLES = new CreativeTab("aether.consumables");

	public static final CreativeTab TOOLS = new CreativeTab("aether.tools");

	public static final CreativeTab WEAPONS = new CreativeTab("aether.weapons");

	public static final CreativeTab ARMOR = new CreativeTab("aether.armor");

	public static final CreativeTab COMPANIONS = new CreativeTab("aether.companions");

	public static final CreativeTab RINGS = new CreativeTab("aether.rings");

	public static final CreativeTab NECKWEAR = new CreativeTab("aether.neckwear");

	public static final CreativeTab RELICS = new CreativeTab("aether.relics");

	public static final CreativeTab CHARMS = new CreativeTab("aether.charms");

	public static final CreativeTab ARTIFACTS = new CreativeTab("aether.artifacts");

	public static final CreativeTab MISCELLANEOUS = new CreativeTabMisc("aether.miscellaneous");

	@SideOnly(Side.CLIENT)
	public static void registerTabIcons()
	{
		NATURAL_BLOCKS.setDisplayStack(new ItemStack(BlocksAether.aether_grass, 1, BlockAetherGrass.AETHER.getMeta()));
		CONSTRUCTION.setDisplayStack(new ItemStack(BlocksAether.holystone_brick));
		VISUAL_VARIANTS.setDisplayStack(new ItemStack(BlocksAether.skyroot_planks, 1, BlockSkyrootPlanks.HIGHLIGHT.getMeta()));
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
		CHARMS.setDisplayStack(new ItemStack(ItemsAether.blight_ward));
		ARTIFACTS.setDisplayStack(new ItemStack(ItemsAether.valkyrie_wings));
	}

	public static class CreativeTab extends CreativeTabs
	{
		private ItemStack stack = ItemStack.EMPTY;

		public CreativeTab(String unlocalizedName)
		{
			super(unlocalizedName);
		}

		@SideOnly(Side.CLIENT)
		public void setDisplayStack(ItemStack stack)
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

	public static class CreativeTabMisc extends CreativeTab
	{
		public CreativeTabMisc(String unlocalizedName)
		{
			super(unlocalizedName);
		}

		@Override
		@SideOnly(Side.CLIENT)
		public void displayAllRelevantItems(NonNullList<ItemStack> list)
		{
			super.displayAllRelevantItems(list);

			for (ResourceLocation res : EntitiesAether.getRegisteredSpawnEggs())
			{
				ItemStack stack = new ItemStack(Items.SPAWN_EGG);

				ItemMonsterPlacer.applyEntityIdToItemStack(stack, res);

				list.add(stack);
			}
		}
	}
}
