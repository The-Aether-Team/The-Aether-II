package com.gildedgames.aether.common.items.weapons;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemDart extends Item
{
	public enum DartType
	{
		GOLDEN(1.5f, "golden"), ENCHANTED(2.0f, "enchanted"), POISON(1.5f, "poison"), PHOENIX(2.5f, "phoenix");

		private final float damage;

		private final String name;

		DartType(float damage, String name)
		{
			this.damage = damage;
			this.name = name;
		}

		public float getDamage()
		{
			return this.damage;
		}

		public static DartType fromOrdinal(int ordinal)
		{
			DartType[] darts = values();

			return darts[ordinal > darts.length || ordinal < 0 ? 0 : ordinal];
		}
	}

	private static final DartType[] ITEM_VARIANTS = new DartType[] { DartType.GOLDEN, DartType.ENCHANTED, DartType.POISON };

	public ItemDart()
	{
		this.setHasSubtypes(true);
	}

	@Override
	@SuppressWarnings("unchecked")
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List subItems)
	{
		for (DartType type : ITEM_VARIANTS)
		{
			subItems.add(new ItemStack(item, 1, type.ordinal()));
		}
	}

	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		return super.getUnlocalizedName(stack) + "." + DartType.fromOrdinal(stack.getMetadata()).name;
	}
}
