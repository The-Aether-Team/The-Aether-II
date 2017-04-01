package com.gildedgames.aether.common.items.misc;

import com.gildedgames.aether.common.AetherCore;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.awt.Color;
import java.util.List;

public class ItemMoaFeather extends Item
{

	public ItemMoaFeather()
	{
		this.setHasSubtypes(true);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems)
	{
		ItemStack feather = new ItemStack(itemIn);
		ItemMoaFeather.setColor(feather, "moa.feathers.dawn", new Color(0x83c4e2).getRGB());

		subItems.add(feather);
	}

	public static void setColor(ItemStack stack, String colorName, int color)
	{
		if (stack.getTagCompound() == null)
		{
			stack.setTagCompound(new NBTTagCompound());
		}

		NBTTagCompound tag = new NBTTagCompound();

		tag.setString("colorName", colorName);
		tag.setInteger("color", color);

		stack.getTagCompound().setTag("featherColor", tag);
	}

	public static int getColor(ItemStack stack)
	{
		if (stack.getTagCompound() == null)
		{
			ItemMoaFeather.setColor(stack, "", 0xFFFFFF);
		}

		NBTTagCompound tag = stack.getTagCompound().getCompoundTag("featherColor");

		return tag.getInteger("color");
	}

	@SideOnly(Side.CLIENT)
	public static String getColorName(ItemStack stack)
	{
		if (stack.getTagCompound() == null)
		{
			ItemMoaFeather.setColor(stack, "", 0xFFFFFF);
		}

		NBTTagCompound tag = stack.getTagCompound().getCompoundTag("featherColor");

		return tag.getString("colorName");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
	{
		String colorName = ItemMoaFeather.getColorName(stack);

		if (!colorName.isEmpty())
		{
			tooltip.add(TextFormatting.GRAY + "" + TextFormatting.ITALIC + "Color: " + I18n.format(colorName));
		}
	}

	@Override
	public boolean getShareTag()
	{
		return true;
	}

}
