package com.gildedgames.aether.common.items.consumables;

import com.gildedgames.aether.common.entities.living.mobs.EntitySwet;
import com.gildedgames.aether.common.items.misc.ItemAetherFood;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemGummySwet extends ItemAetherFood
{

	public ItemGummySwet()
	{
		super(20, false);

		this.setHasSubtypes(true);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, NonNullList<ItemStack> subItems)
	{
		for (EntitySwet.Type types : EntitySwet.Type.values())
		{
			subItems.add(new ItemStack(this, 1, types.ordinal()));
		}
	}

	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		return "item.aether.gummy_swet." + EntitySwet.Type.fromOrdinal(stack.getMetadata()).name;
	}
}
