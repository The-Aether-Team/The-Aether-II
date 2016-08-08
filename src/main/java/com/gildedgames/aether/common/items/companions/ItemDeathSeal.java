package com.gildedgames.aether.common.items.companions;

import com.gildedgames.aether.common.entities.companions.EntityCompanion;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemDeathSeal extends ItemCompanion
{
	public ItemDeathSeal(Class<? extends EntityCompanion> companionClass)
	{
		super(companionClass);
	}

	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
	{
		long disabledTime = ItemDeathSeal.getTicksUntilEnabled(stack, worldIn);

		if (disabledTime <= 0)
		{
			stack.setItemDamage(0);
		}
		else
		{
			stack.setItemDamage(1);
		}
	}

	public static void setDisabledTimer(ItemStack stack, World world, int timer)
	{
		NBTTagCompound compound = stack.getTagCompound();

		if (compound == null)
		{
			stack.setTagCompound(compound = new NBTTagCompound());
		}

		compound.setLong("disabledTimer", world.getTotalWorldTime() + timer);

		stack.setItemDamage(1);
	}

	public static long getTicksUntilEnabled(ItemStack stack, World world)
	{
		NBTTagCompound compound = stack.getTagCompound();

		if (compound == null || !compound.hasKey("disabledTimer"))
		{
			return 0;
		}

		return compound.getLong("disabledTimer") - world.getTotalWorldTime();
	}

	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced)
	{
		long disabledTime = ItemDeathSeal.getTicksUntilEnabled(stack, player.worldObj);

		if (disabledTime > 0)
		{
			tooltip.add(TextFormatting.RED + "Usable in " + this.parseTicks(disabledTime));
		}
	}
}
