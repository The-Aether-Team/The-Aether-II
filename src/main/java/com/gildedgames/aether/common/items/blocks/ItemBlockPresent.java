package com.gildedgames.aether.common.items.blocks;

import com.gildedgames.aether.common.items.misc.ItemWrappingPaper;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemBlockPresent extends ItemBlock
{

	public static class PresentData
	{
		private ItemStack stack;

		private ItemWrappingPaper.PresentDyeData dye = new ItemWrappingPaper.PresentDyeData();

		public static PresentData readFromNBT(NBTTagCompound compound)
		{
			PresentData data = new PresentData();

			if (compound.hasKey("item"))
			{
				data.stack = ItemStack.loadItemStackFromNBT(compound.getCompoundTag("item"));
			}

			if (compound.hasKey("dye"))
			{
				data.dye = ItemWrappingPaper.PresentDyeData.readFromNBT(compound.getCompoundTag("dye"));
			}

			return data;
		}

		public NBTTagCompound writeToNBT(NBTTagCompound compound)
		{
			if (this.stack != null)
			{
				compound.setTag("item", this.stack.writeToNBT(new NBTTagCompound()));
			}

			if (this.dye != null)
			{
				compound.setTag("dye", this.dye.writeToNBT(new NBTTagCompound()));
			}

			return compound;
		}

		public ItemStack getStack()
		{
			return this.stack;
		}

		public void setStack(ItemStack stack)
		{
			this.stack = stack;
		}

		public ItemWrappingPaper.PresentDyeData getDye()
		{
			return this.dye;
		}

		public void setDye(ItemWrappingPaper.PresentDyeData data)
		{
			this.dye = data;
		}
	}

	public ItemBlockPresent(Block block)
	{
		super(block);

		this.setMaxStackSize(1);
	}

	public static PresentData getData(ItemStack stack)
	{
		if (stack != null && stack.getTagCompound() != null)
		{
			return PresentData.readFromNBT(stack.getTagCompound());
		}

		return new PresentData();
	}

	@Override
	public EnumRarity getRarity(ItemStack stack)
	{
		return EnumRarity.RARE;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advancedTooltips)
	{
		PresentData data = ItemBlockPresent.getData(stack);

		if (data.getStack() != null)
		{
			tooltip.add(TextFormatting.GRAY + "" + TextFormatting.ITALIC + "Contains an item inside");
		}
		else
		{
			tooltip.add(TextFormatting.DARK_GRAY + "(empty)");
		}
	}

}
