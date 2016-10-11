package com.gildedgames.aether.common.crafting.recipes;

import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.items.misc.ItemWrappingPaper;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

public class RecipeWrappingPaper implements IRecipe
{
	@Override
	public boolean matches(InventoryCrafting inventory, World world)
	{
		int paperLoc = -1;

		for (int i = 3; i < (inventory.getSizeInventory() - 3); i++)
		{
			ItemStack stack = inventory.getStackInSlot(i);

			if (stack != null && stack.getItem() == Items.PAPER)
			{
				if (paperLoc != -1)
				{
					return false;
				}

				paperLoc = i;
			}
		}

		if (paperLoc < 0 || paperLoc > 8)
		{
			return false;
		}

		ItemStack bowDye = inventory.getStackInSlot(paperLoc - 3), boxDye = inventory.getStackInSlot(paperLoc + 3);

		return this.isValidDye(bowDye) && this.isValidDye(boxDye);
	}

	private boolean isValidDye(ItemStack stack)
	{
		return stack != null && stack.getItem() instanceof ItemDye;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inventory)
	{
		int paperLoc = -1;

		for (int i = 3; i < (inventory.getSizeInventory() - 3); i++)
		{
			ItemStack stack = inventory.getStackInSlot(i);

			if (stack != null && stack.getItem() == Items.PAPER)
			{
				paperLoc = i;

				break;
			}
		}

		ItemStack bowDye = inventory.getStackInSlot(paperLoc - 3), boxDye = inventory.getStackInSlot(paperLoc + 3);

		ItemWrappingPaper.PresentDyeData data = new ItemWrappingPaper.PresentDyeData();

		if (bowDye != null)
		{
			data.setBowColor((byte) bowDye.getMetadata());
		}

		if (boxDye != null)
		{
			data.setBoxColor((byte) boxDye.getMetadata());
		}

		ItemStack output = new ItemStack(ItemsAether.wrapping_paper, 8);

		output.setTagCompound(new NBTTagCompound());
		data.writeToNBT(output.getTagCompound());

		return output;
	}

	@Override
	public int getRecipeSize()
	{
		return 3;
	}

	@Override
	public ItemStack getRecipeOutput()
	{
		return null;
	}

	@Override
	public ItemStack[] getRemainingItems(InventoryCrafting inv)
	{
		ItemStack[] stacks = new ItemStack[inv.getSizeInventory()];

		for (int i = 0; i < stacks.length; ++i)
		{
			ItemStack stack = inv.getStackInSlot(i);

			stacks[i] = ForgeHooks.getContainerItem(stack);
		}

		return stacks;
	}
}
