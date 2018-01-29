package com.gildedgames.aether.common.items.companions;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class ItemDeathSeal extends ItemCompanion
{
	public static void setDisabledTimer(final ItemStack stack, final World world, final int timer)
	{
		NBTTagCompound compound = stack.getTagCompound();

		if (compound == null)
		{
			stack.setTagCompound(compound = new NBTTagCompound());
		}

		compound.setLong("disabledTimer", world.getTotalWorldTime() + timer);

		stack.setItemDamage(1);
	}

	public static long getTicksUntilEnabled(final ItemStack stack, final World world)
	{
		final NBTTagCompound compound = stack.getTagCompound();

		if (compound == null || !compound.hasKey("disabledTimer"))
		{
			return 0;
		}

		return compound.getLong("disabledTimer") - world.getTotalWorldTime();
	}

	@Override
	public void onUpdate(final ItemStack stack, final World worldIn, final Entity entityIn, final int itemSlot, final boolean isSelected)
	{
		final long disabledTime = ItemDeathSeal.getTicksUntilEnabled(stack, worldIn);

		if (disabledTime <= 0)
		{
			stack.setItemDamage(0);
		}
		else
		{
			stack.setItemDamage(1);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(final ItemStack stack, @Nullable final World worldIn, final List<String> tooltip, final ITooltipFlag flagIn)
	{
		super.addInformation(stack, worldIn, tooltip, flagIn);

		final long disabledTime = ItemDeathSeal.getTicksUntilEnabled(stack, worldIn);

		if (disabledTime > 0)
		{
			tooltip.add(TextFormatting.RED + "" + TextFormatting.ITALIC + "Disabled! Ready in " + this.parseTicks(disabledTime));
		}
	}
}
