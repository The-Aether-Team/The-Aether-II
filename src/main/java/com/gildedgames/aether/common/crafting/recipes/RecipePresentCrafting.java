package com.gildedgames.aether.common.crafting.recipes;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.items.blocks.ItemBlockPresent;
import com.gildedgames.aether.common.items.misc.ItemWrappingPaper;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

public class RecipePresentCrafting implements IRecipe
{

	@Override
	public boolean matches(InventoryCrafting inventory, World world)
	{
		ItemWrappingPaper.PresentDyeData masterDye = null;

		for (int i = 0; i < inventory.getSizeInventory(); i++)
		{
			if (i == 4)
			{
				continue;
			}

			ItemStack stack = inventory.getStackInSlot(i);

			if (stack == null || stack.getItem() != ItemsAether.wrapping_paper)
			{
				return false;
			}

			ItemWrappingPaper.PresentDyeData dye = ItemWrappingPaper.PresentDyeData.readFromNBT(stack.getTagCompound());

			if (masterDye != null)
			{
				if (dye.getBowColor() != masterDye.getBowColor() || dye.getBoxColor() != masterDye.getBoxColor())
				{
					return false;
				}
			}

			masterDye = dye;
		}

		ItemStack centerItem = inventory.getStackInSlot(4);

		return centerItem != null && centerItem.getItem() != Item.getItemFromBlock(BlocksAether.present);
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inventory)
	{
		ItemStack stack = inventory.getStackInSlot(4).copy();
		stack.stackSize = 1;

		ItemWrappingPaper.PresentDyeData dye = ItemWrappingPaper.getDyeData(inventory.getStackInSlot(0));

		ItemBlockPresent.PresentData data = new ItemBlockPresent.PresentData();
		data.setStack(stack);
		data.setDye(dye);

		ItemStack present = new ItemStack(BlocksAether.present);
		present.setTagCompound(data.writeToNBT(new NBTTagCompound()));

		return present;
	}

	@Override
	public int getRecipeSize()
	{
		return 9;
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
