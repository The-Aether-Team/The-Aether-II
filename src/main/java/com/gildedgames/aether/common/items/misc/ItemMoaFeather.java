package com.gildedgames.aether.common.items.misc;

import com.gildedgames.aether.common.AetherCore;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.awt.Color;
import java.util.List;

public class ItemMoaFeather extends Item
{

	public ItemMoaFeather()
	{
		super();

		this.setHasSubtypes(true);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems)
	{
		ItemStack feather = new ItemStack(itemIn);
		ItemMoaFeather.setColor(feather, I18n.format("moa.feathers.dawn"), new Color(0x83c4e2).getRGB());

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
	public String getUnlocalizedName(ItemStack stack)
	{
		String colorName = ItemMoaFeather.getColorName(stack);

		if (!colorName.isEmpty() && AetherCore.isClient())
		{
			return colorName + " " + I18n.format(super.getUnlocalizedName(stack) + ".name");
		}

		return I18n.format(super.getUnlocalizedName(stack) + ".name");
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack)
	{
		return this.getUnlocalizedName(stack);
	}

	@Override
	public boolean getShareTag()
	{
		return true;
	}

}
