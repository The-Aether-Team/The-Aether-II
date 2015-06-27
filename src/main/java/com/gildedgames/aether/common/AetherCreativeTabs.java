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
	private static class AetherCreativeTab extends CreativeTabs
	{
		private ItemStack stack;

		public AetherCreativeTab(String unlocalizedName)
		{
			super(unlocalizedName);
		}

		private void setDisplayStack(ItemStack stack)
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

	public static final AetherCreativeTab tabBlocks = new AetherCreativeTab("aetherBlocks");

	public static final AetherCreativeTab tabMaterials = new AetherCreativeTab("aetherMaterials");

	public static final AetherCreativeTab tabTools = new AetherCreativeTab("aetherTools");

	public static final AetherCreativeTab tabWeapons = new AetherCreativeTab("aetherWeapons");

	public static final AetherCreativeTab tabArmor = new AetherCreativeTab("aetherArmor");

	public static final AetherCreativeTab tabConsumables = new AetherCreativeTab("aetherConsumables");

	public static void registerTabIcons()
	{
		tabBlocks.setDisplayStack(new ItemStack(BlocksAether.aether_grass, BlockAetherGrass.AETHER_GRASS.getMeta()));
		tabMaterials.setDisplayStack(new ItemStack(ItemsAether.skyroot_stick));
		tabTools.setDisplayStack(new ItemStack(ItemsAether.gravitite_pickaxe));
		tabWeapons.setDisplayStack(new ItemStack(ItemsAether.gravitite_sword));
		tabArmor.setDisplayStack(new ItemStack(ItemsAether.zanite_chestplate));
		tabConsumables.setDisplayStack(new ItemStack(ItemsAether.orange));
	}
}
