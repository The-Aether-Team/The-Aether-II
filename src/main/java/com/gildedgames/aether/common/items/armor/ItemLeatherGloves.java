package com.gildedgames.aether.common.items.armor;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class ItemLeatherGloves extends ItemAetherGloves
{

	private static final ResourceLocation OVERLAY_TEXTURE = new ResourceLocation("aether:textures/armor/leather_gloves_overlay.png");

	public ItemLeatherGloves()
	{
		super(GloveType.LEATHER);
	}

	public static boolean hasColor(ItemStack stack)
	{
		return stack.hasTagCompound() && (stack.getTagCompound().hasKey("display", 10)
				&& stack.getTagCompound().getCompoundTag("display").hasKey("color", 3));
	}

	public static int getColor(ItemStack stack)
	{
		NBTTagCompound compound = stack.getTagCompound();

		if (compound != null)
		{
			NBTTagCompound displayTag = compound.getCompoundTag("display");

			if (displayTag.hasKey("color", 3))
			{
				return displayTag.getInteger("color");
			}
		}

		return 10511680;
	}

	public static void setColor(ItemStack stack, int color)
	{
		NBTTagCompound itemTag = stack.getTagCompound();

		if (itemTag == null)
		{
			stack.setTagCompound(itemTag = new NBTTagCompound());
		}

		NBTTagCompound displayTag = itemTag.getCompoundTag("display");

		if (!itemTag.hasKey("display", 10))
		{
			itemTag.setTag("display", displayTag);
		}

		displayTag.setInteger("color", color);
	}

	public static void removeColor(ItemStack stack)
	{
		NBTTagCompound nbttagcompound = stack.getTagCompound();

		if (nbttagcompound != null)
		{
			NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");

			if (nbttagcompound1.hasKey("color"))
			{
				nbttagcompound1.removeTag("color");
			}
		}
	}

	@Override
	public ResourceLocation getGloveTexture(int layer)
	{
		if (layer > 0)
		{
			return ItemLeatherGloves.OVERLAY_TEXTURE;
		}

		return super.getGloveTexture(layer);
	}

}
