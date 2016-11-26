package com.gildedgames.aether.common.blocks.util.variants;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

public interface IBlockVariants
{

	String getUnlocalizedName(ItemStack stack);

	void addItemsToCreativeTab(Item item, CreativeTabs tab, List<ItemStack> stackList);

}
