package com.gildedgames.aether.common.items.companions;

import com.gildedgames.aether.common.items.InformationProvider;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class ItemDeathSeal extends ItemCompanion
{
	public ItemDeathSeal(Properties properties)
	{
		super(properties);
	}

	public ItemDeathSeal(Properties properties, InformationProvider informationProvider)
	{
		super(properties, informationProvider);
	}

	public static void setDisabledTimer(final ItemStack stack, final World world, final int timer)
	{
		CompoundNBT compound = stack.getTag();

		if (compound == null)
		{
			stack.setTag(compound = new CompoundNBT());
		}

		compound.putLong("disabledTimer", world.getGameTime() + timer);

		stack.setDamage(1);
	}

	public static long getTicksUntilEnabled(final ItemStack stack, final World world)
	{
		final CompoundNBT compound = stack.getTag();

		if (compound == null || !compound.contains("disabledTimer"))
		{
			return 0;
		}

		return compound.getLong("disabledTimer") - world.getGameTime();
	}

	@Override
	public void inventoryTick(final ItemStack stack, final World worldIn, final Entity entityIn, final int itemSlot, final boolean isSelected)
	{
		final long disabledTime = ItemDeathSeal.getTicksUntilEnabled(stack, worldIn);

		if (disabledTime <= 0)
		{
			stack.setDamage(0);
		}
		else
		{
			stack.setDamage(1);
		}
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void addInformation(final ItemStack stack, @Nullable final World worldIn, final List<ITextComponent> tooltip, final ITooltipFlag flagIn)
	{
		super.addInformation(stack, worldIn, tooltip, flagIn);

		final long disabledTime = ItemDeathSeal.getTicksUntilEnabled(stack, worldIn);

		if (disabledTime > 0)
		{
			tooltip.add(new StringTextComponent("Disabled! Ready in " + this.parseTicks(disabledTime)).setStyle(new Style().setColor(TextFormatting.RED).setItalic(true)));
		}
	}
}
