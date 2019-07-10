package com.gildedgames.aether.common.items.other;

import com.gildedgames.aether.common.items.IDropOnDeath;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemWrappingPaper extends Item implements IDropOnDeath
{

	public static PresentDyeData getDyeData(final ItemStack stack)
	{
		return PresentDyeData.readFromNBT(stack.getTagCompound());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(final ItemStack stack, final World world, final List<String> tooltip, final ITooltipFlag flag)
	{
		final PresentDyeData data = ItemWrappingPaper.getDyeData(stack);

		if (data == null)
		{
			return;
		}

		tooltip.add(TextFormatting.YELLOW + I18n.format(data.getBoxColorName()));
		tooltip.add(TextFormatting.YELLOW + I18n.format(data.getBowColorName()));

		tooltip.add(TextFormatting.GRAY + I18n.format("item.aether.wrapping_paper.tooltip.craft"));
	}

	public static class PresentDyeData
	{
		public static final String[] dyeNames = new String[] { "black", "red", "green", "brown", "blue", "purple", "cyan", "silver", "gray",
				"pink", "lime", "yellow", "light_blue", "magenta", "orange", "white" };

		private byte bowColor = 1, boxColor = 15;

		public static PresentDyeData readFromNBT(final NBTTagCompound compound)
		{
			final PresentDyeData data = new PresentDyeData();

			if (compound == null)
			{
				return new PresentDyeData();
			}

			data.setBoxColor(compound.getByte("boxColor"));
			data.setBowColor(compound.getByte("bowColor"));

			return data;
		}

		public NBTTagCompound writeToNBT(final NBTTagCompound compound)
		{
			compound.setByte("boxColor", this.getBoxColor());
			compound.setByte("bowColor", this.getBowColor());

			return compound;
		}

		public String getBowColorName()
		{
			return "item.aether.wrapping_paper.bow." + dyeNames[this.getBowColor()] + ".name";
		}

		public String getBoxColorName()
		{
			return "item.aether.wrapping_paper.box." + dyeNames[this.getBoxColor()] + ".name";
		}

		public byte getBowColor()
		{
			return this.bowColor;
		}

		public void setBowColor(final byte bowColor)
		{
			this.bowColor = bowColor;
		}

		public byte getBoxColor()
		{
			return this.boxColor;
		}

		public void setBoxColor(final byte boxColor)
		{
			this.boxColor = boxColor;
		}
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
