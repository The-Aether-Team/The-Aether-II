package com.gildedgames.aether.common;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.natural.BlockAetherGrass;
import com.gildedgames.aether.common.items.ItemsAether;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AetherCreativeTabs
{
	public static final CreativeTab tabBlocks = new CreativeTab("aetherBlocks");

	public static final CreativeTab tabMaterials = new CreativeTab("aetherMaterials");

	public static final CreativeTab tabTools = new CreativeTab("aetherTools");

	public static final CreativeTab tabWeapons = new CreativeTab("aetherWeapons");

	public static final CreativeTab tabArmor = new CreativeTab("aetherArmor");

	public static final CreativeTab tabConsumables = new CreativeTab("aetherConsumables");

	@SideOnly(Side.CLIENT)
	public static void registerTabIcons()
	{
		tabBlocks.setDisplayStack(new ItemStack(BlocksAether.aether_grass, BlockAetherGrass.AETHER_GRASS.getMeta()));
		tabMaterials.setDisplayStack(new ItemStack(ItemsAether.skyroot_stick));
		tabTools.setDisplayStack(new ItemStack(ItemsAether.gravitite_pickaxe));
		tabWeapons.setDisplayStack(new ItemStack(ItemsAether.gravitite_sword));
		tabArmor.setDisplayStack(new ItemStack(ItemsAether.zanite_chestplate));
		tabConsumables.setDisplayStack(new ItemStack(ItemsAether.orange));
	}

	public static class CreativeTab extends CreativeTabs
	{
		private ItemStack stack;

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
		public Item getTabIconItem()
		{
			return this.stack.getItem();
		}

		@Override
		@SideOnly(Side.CLIENT)
		public int getIconItemDamage()
		{
			return this.stack.getItemDamage();
		}
	}
}
