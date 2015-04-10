package com.gildedgames.aether;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.gildedgames.aether.blocks.natural.BlockAetherDirt;

public class AetherCreativeTabs
{
	private class AetherCreativeTab extends CreativeTabs
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

	public AetherCreativeTab tabBlocks = new AetherCreativeTab("aetherBlocks");

	public AetherCreativeTab tabMaterials = new AetherCreativeTab("aetherMaterials");

	public AetherCreativeTab tabTools = new AetherCreativeTab("aetherTools");

	public AetherCreativeTab tabArmor = new AetherCreativeTab("aetherArmor");

	public void preInit()
	{
		this.tabBlocks.setItemToDisplay(Item.getItemFromBlock(Aether.getBlocks().aether_dirt), BlockAetherDirt.AETHER_GRASS.getMeta());
		this.tabMaterials.setItemToDisplay(Aether.getItems().skyroot_stick, 0);
		this.tabTools.setItemToDisplay(Aether.getItems().gravitite_pickaxe, 0);
		this.tabArmor.setItemToDisplay(Aether.getItems().zanite_chestplate, 0);
	}
}
