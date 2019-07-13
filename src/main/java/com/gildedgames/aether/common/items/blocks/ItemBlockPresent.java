package com.gildedgames.aether.common.items.blocks;

import com.gildedgames.aether.common.items.other.ItemWrappingPaper;
import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Rarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class ItemBlockPresent extends BlockItem
{

	public ItemBlockPresent(final Block block, final Item.Properties properties)
	{
		super(block, properties);

		this.setMaxStackSize(1);
	}

	public static PresentData getData(final ItemStack stack)
	{
		if (stack != null && stack.getTag() != null)
		{
			return PresentData.readFromNBT(stack.getTag());
		}

		return new PresentData();
	}

	@Override
	public Rarity getRarity(final ItemStack stack)
	{
		return Rarity.RARE;
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void addInformation(final ItemStack stack, @Nullable final World worldIn, final List<ITextComponent> tooltip, final ITooltipFlag flagIn)
	{
		final PresentData data = ItemBlockPresent.getData(stack);

		if (data.getStack() != null)
		{
			tooltip.add(new TranslationTextComponent("item.aether.present.tooltip.hasitem").setStyle(new Style().setColor(TextFormatting.GRAY).setItalic(true)));
		}
		else
		{
			tooltip.add(new TranslationTextComponent("item.aether.present.tooltip.empty").setStyle(new Style().setColor(TextFormatting.DARK_GRAY).setItalic(true)));
		}
	}

	public static class PresentData
	{
		private ItemStack stack;

		private ItemWrappingPaper.PresentDyeData dye = new ItemWrappingPaper.PresentDyeData();

		public static PresentData readFromNBT(final CompoundNBT compound)
		{
			final PresentData data = new PresentData();

			if (compound.contains("item"))
			{
				data.stack = ItemStack.read(compound.getCompound("item"));
			}

			if (compound.contains("dye"))
			{
				data.dye = ItemWrappingPaper.PresentDyeData.readFromNBT(compound.getCompound("dye"));
			}

			return data;
		}

		public CompoundNBT writeToNBT(final CompoundNBT compound)
		{
			if (this.stack != null)
			{
				compound.put("item", this.stack.serializeNBT());
			}

			if (this.dye != null)
			{
				compound.put("dye", this.dye.writeToNBT(new CompoundNBT()));
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
