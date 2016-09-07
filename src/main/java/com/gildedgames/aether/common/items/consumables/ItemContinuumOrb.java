package com.gildedgames.aether.common.items.consumables;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemContinuumOrb extends Item
{

	public ItemContinuumOrb()
	{

	}

	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
	{
		tooltip.add("");
		tooltip.add(TextFormatting.GRAY + "" + TextFormatting.ITALIC + "Craft with other items");
		tooltip.add(TextFormatting.GRAY + "" + TextFormatting.ITALIC + "to make them Unbreakable");
	}

}
