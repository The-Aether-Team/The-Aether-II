package com.gildedgames.aether.common.items.consumables;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemGummySwet extends ItemFood
{
	public enum GummyType
	{
		BLUE("blue"), GOLDEN("golden"), DARK("dark");

		public final String name;

		GummyType(String name)
		{
			this.name = name;
		}
		
		public static GummyType fromOrdinal(int ordinal)
		{
			GummyType[] gummy = values();

			return gummy[ordinal > gummy.length || ordinal < 0 ? 0 : ordinal];
		}
	}

	public ItemGummySwet() 
	{
		super(20, false);

		this.setHasSubtypes(true);
	}

	@Override
	@SuppressWarnings("unchecked")
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List subItems)
	{
		for (GummyType types : GummyType.values())
		{
			subItems.add(new ItemStack(this, 1, types.ordinal()));
		}
	}

	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		return "item.gummy_swet." + GummyType.fromOrdinal(stack.getMetadata()).name;
	}
}
