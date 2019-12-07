package com.gildedgames.aether.common.items.consumables;

import com.gildedgames.aether.common.entities.monsters.EntitySwet;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashMap;

public class ItemSwetJelly extends ItemAetherFood
{

	public ItemSwetJelly()
	{
		super(5, false);

		this.setHasSubtypes(true);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(final CreativeTabs tab, final NonNullList<ItemStack> subItems)
	{
		if (!this.isInCreativeTab(tab))
		{
			return;
		}

		for (final EntitySwet.Type types : EntitySwet.Type.values())
		{
			subItems.add(new ItemStack(this, 1, types.ordinal()));
		}
	}

	@Override
	public String getTranslationKey(final ItemStack stack)
	{
		return "item.aether.swet_jelly." + EntitySwet.Type.fromOrdinal(stack.getMetadata()).name;
	}
}
