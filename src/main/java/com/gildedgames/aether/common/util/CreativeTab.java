package com.gildedgames.aether.common.util;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CreativeTab extends CreativeTabs
{
	
	private ItemStack stack;

	public CreativeTab(String unlocalizedName)
	{
		super(unlocalizedName);
	}

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