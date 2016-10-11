package com.gildedgames.aether.common.items.misc;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemWrappingPaper extends Item
{

	public static class PresentDyeData
	{
		public static final String[] dyeNames = new String[] { "Black", "Red", "Green", "Brown", "Blue", "Purple", "Cyan", "Silver", "Gray", "Pink", "Lime", "Yellow", "Light Blue", "Magenta", "Orange", "White" };

		private byte bowColor = 1, boxColor = 15;

		public static PresentDyeData readFromNBT(NBTTagCompound compound)
		{
			PresentDyeData data = new PresentDyeData();

			if (compound == null)
			{
				return new PresentDyeData();
			}

			data.setBoxColor(compound.getByte("boxColor"));
			data.setBowColor(compound.getByte("bowColor"));

			return data;
		}

		public NBTTagCompound writeToNBT(NBTTagCompound compound)
		{
			compound.setByte("boxColor", this.getBoxColor());
			compound.setByte("bowColor", this.getBowColor());

			return compound;
		}

		public String getBowColorName()
		{
			return dyeNames[this.getBowColor()];
		}

		public String getBoxColorName()
		{
			return dyeNames[this.getBoxColor()];
		}

		public byte getBowColor()
		{
			return this.bowColor;
		}

		public void setBowColor(byte bowColor)
		{
			this.bowColor = bowColor;
		}

		public byte getBoxColor()
		{
			return this.boxColor;
		}

		public void setBoxColor(byte boxColor)
		{
			this.boxColor = boxColor;
		}
	}

	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean adv)
	{
		PresentDyeData data = ItemWrappingPaper.getDyeData(stack);

		if (data == null)
		{
			return;
		}

		tooltip.add(TextFormatting.YELLOW + data.getBowColorName() + " Bow, " + data.getBoxColorName() + " Box");
		tooltip.add(TextFormatting.GRAY + "Craft with items!");
	}

	public static PresentDyeData getDyeData(ItemStack stack)
	{
		return PresentDyeData.readFromNBT(stack.getTagCompound());
	}

/*	@SideOnly(Side.CLIENT)
	@Override
	public int getColorFromItemStack(ItemStack stack, int phase)
	{
		PresentDyeData data = ItemWrappingPaper.getDyeData(stack);

		if (phase == 0)
		{
			return ItemDye.dyeColors[data.getBoxColor()];
		}
		else if (phase == 1)
		{
			return ItemDye.dyeColors[data.getBowColor()];
		}
		else
		{
			return super.getColorFromItemStack(stack, phase);
		}
	}*/

}
