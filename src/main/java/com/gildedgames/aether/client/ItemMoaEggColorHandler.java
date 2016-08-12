package com.gildedgames.aether.client;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemMoaEggColorHandler implements IItemColor
{

	@Override
	public int getColorFromItemstack(ItemStack stack, int tintIndex)
	{
		NBTTagCompound tag = stack.getTagCompound();

		if (tag != null)
		{
			if (tag.getBoolean("genericDisplay"))
			{
				return -1;
			}
			else if (tintIndex == 0)
			{
				return tag.getInteger("bodyColor");
			}
			else if (tintIndex == 1)
			{
				return tag.getInteger("legColor");
			}
			else if (tintIndex == 2)
			{
				return tag.getInteger("beakColor");
			}
			else if (tintIndex == 3)
			{
				return tag.getInteger("markColor");
			}
		}

		return 0xFFFFFF;
	}

}
