package com.gildedgames.aether;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.gildedgames.aether.blocks.natural.BlockAetherDirt;

public class AetherCreativeTabs
{
	// TODO: Make this not terrible.

	public static final CreativeTabs tabBlocks = new CreativeTabs("aetherBlocks")
	{
		@Override
		@SideOnly(Side.CLIENT)
		public Item getTabIconItem()
		{
			return Item.getItemFromBlock(Aether.getBlocks().aether_dirt);
		}

		@Override
		@SideOnly(Side.CLIENT)
		public int getIconItemDamage()
		{
			return BlockAetherDirt.AETHER_GRASS.getMeta();
		}
	};

	public static final CreativeTabs tabMaterials = new CreativeTabs("aetherMaterials")
	{
		@Override
		@SideOnly(Side.CLIENT)
		public Item getTabIconItem()
		{
			return Aether.getItems().skyroot_stick;
		}
	};

	public static final CreativeTabs tabTools = new CreativeTabs("aetherTools")
	{
		@Override
		@SideOnly(Side.CLIENT)
		public Item getTabIconItem()
		{
			return Aether.getItems().zanite_pickaxe;
		}
	};
	
	public static final CreativeTabs tabArmor = new CreativeTabs("aetherArmor")
	{
		@Override
		@SideOnly(Side.CLIENT)
		public Item getTabIconItem()
		{
			return Aether.getItems().zanite_chestplate;
		}
	};
}
