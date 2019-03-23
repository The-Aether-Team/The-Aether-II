package com.gildedgames.aether.common.items.blocks;

import com.gildedgames.aether.common.items.misc.ItemWrappingPaper;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class ItemBlockPresent extends ItemBlock
{

	public ItemBlockPresent(final Block block)
	{
		super(block);

		this.setMaxStackSize(1);
	}

	public static PresentData getData(final ItemStack stack)
	{
		if (stack != null && stack.getTagCompound() != null)
		{
			return PresentData.readFromNBT(stack.getTagCompound());
		}

		return new PresentData();
	}

	@Override
	public EnumRarity getRarity(final ItemStack stack)
	{
		return EnumRarity.RARE;
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void addInformation(final ItemStack stack, @Nullable final World worldIn, final List<String> tooltip, final ITooltipFlag flagIn)
	{
		final PresentData data = ItemBlockPresent.getData(stack);

		if (data.getStack() != null)
		{
			tooltip.add(TextFormatting.GRAY + "" + TextFormatting.ITALIC + I18n.format("item.aether.present.tooltip.hasitem"));
		}
		else
		{
			tooltip.add(TextFormatting.DARK_GRAY + "" + TextFormatting.ITALIC + I18n.format("item.aether.present.tooltip.empty"));
		}
	}

	public static class PresentData
	{
		private ItemStack stack;

		private ItemWrappingPaper.PresentDyeData dye = new ItemWrappingPaper.PresentDyeData();

		public static PresentData readFromNBT(final NBTTagCompound compound)
		{
			final PresentData data = new PresentData();

			if (compound.hasKey("item"))
			{
				data.stack = new ItemStack(compound.getCompoundTag("item"));
			}

			if (compound.hasKey("dye"))
			{
				data.dye = ItemWrappingPaper.PresentDyeData.readFromNBT(compound.getCompoundTag("dye"));
			}

			return data;
		}

		public NBTTagCompound writeToNBT(final NBTTagCompound compound)
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

		public void setStack(final ItemStack stack)
		{
			this.stack = stack;
		}

		public ItemWrappingPaper.PresentDyeData getDye()
		{
			return this.dye;
		}

		public void setDye(final ItemWrappingPaper.PresentDyeData data)
		{
			this.dye = data;
		}
	}

}
