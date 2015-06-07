package com.gildedgames.aether.common;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.natural.BlockAetherGrass;
import com.gildedgames.aether.common.items.ItemsAether;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AetherCreativeTabs
{
	private static class AetherCreativeTab extends CreativeTabs
	{
		private Item item;

		private int metadata;

		public AetherCreativeTab(String unlocalizedName)
		{
			super(unlocalizedName);
		}

		private void setItemToDisplay(Item item, int metadata)
		{
			this.item = item;
			this.metadata = metadata;
		}

		@Override
		public Item getTabIconItem()
		{
			return this.item;
		}

		@Override
		@SideOnly(Side.CLIENT)
		public int getIconItemDamage()
		{
			return this.metadata;
		}
	}

	public static final AetherCreativeTab tabBlocks = new AetherCreativeTab("aetherBlocks");

	public static final AetherCreativeTab tabMaterials = new AetherCreativeTab("aetherMaterials");

	public static final AetherCreativeTab tabTools = new AetherCreativeTab("aetherTools");

	public static final AetherCreativeTab tabWeapons = new AetherCreativeTab("aetherWeapons");

	public static final AetherCreativeTab tabArmor = new AetherCreativeTab("aetherArmor");

	public static final AetherCreativeTab tabConsumables = new AetherCreativeTab("aetherConsumables");

	public static void preInit()
	{
		tabBlocks.setItemToDisplay(Item.getItemFromBlock(BlocksAether.aether_grass), BlockAetherGrass.AETHER_GRASS.getMeta());
		tabMaterials.setItemToDisplay(ItemsAether.skyroot_stick, 0);
		tabTools.setItemToDisplay(ItemsAether.gravitite_pickaxe, 0);
		tabWeapons.setItemToDisplay(ItemsAether.gravitite_sword, 0);
		tabArmor.setItemToDisplay(ItemsAether.zanite_chestplate, 0);
		tabConsumables.setItemToDisplay(ItemsAether.orange, 0);
	}
}
