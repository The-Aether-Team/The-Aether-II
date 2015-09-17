package com.gildedgames.aether.common.items.consumables;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemSwetJelly extends ItemFood
{

	public enum JellyType
	{
		BLUE("blue"), GOLDEN("golden"), DARK("dark");

		public String name;

		JellyType(String name)
		{
			this.name = name;
		}

		public static JellyType fromOrdinal(int ordinal)
		{
			JellyType[] jelly = values();

			return jelly[ordinal > jelly.length || ordinal < 0 ? 0 : ordinal];
		}
	}

	public ItemSwetJelly() 
	{
		super(5, false);

		this.setHasSubtypes(true);
	}

	@Override
	@SuppressWarnings("unchecked")
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List subItems)
	{
		for (JellyType types : JellyType.values())
		{
			subItems.add(new ItemStack(item, 1, types.ordinal()));
		}
	}

	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		return "item." + JellyType.fromOrdinal(stack.getMetadata()).name + "_swet_jelly";
	}

}
