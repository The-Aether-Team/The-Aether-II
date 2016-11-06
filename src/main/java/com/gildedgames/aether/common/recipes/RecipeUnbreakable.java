package com.gildedgames.aether.common.recipes;

import com.gildedgames.aether.api.capabilites.AetherCapabilities;
import com.gildedgames.aether.api.capabilites.items.IItemBreakable;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class RecipeUnbreakable implements IRecipe
{

	private Item itemToCraftWith;

	public RecipeUnbreakable(Item itemToCraftWith)
	{
		this.itemToCraftWith = itemToCraftWith;
	}

	@Override
	public boolean matches(InventoryCrafting inventory, World world)
	{
		ItemStack breakableStack = null;

		List<ItemStack> list = new ArrayList<>();

		for (int i = 0; i < inventory.getSizeInventory(); ++i)
		{
			ItemStack invStack = inventory.getStackInSlot(i);

			if (invStack != null)
			{
				if (invStack.isItemStackDamageable())
				{
					breakableStack = invStack;
				}
				else
				{
					if (invStack.getItem() != this.itemToCraftWith)
					{
						return false;
					}

					list.add(invStack);
				}
			}
		}

		return breakableStack != null && !list.isEmpty();
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv)
	{
		ItemStack stack = null;

		for (int i = 0; i < inv.getSizeInventory(); ++i)
		{
			ItemStack invStack = inv.getStackInSlot(i);

			if (invStack != null)
			{
				if (invStack.isItemStackDamageable())
				{
					stack = invStack.copy();
					stack.stackSize = 1;
				}
			}
		}

		if (stack != null && stack.hasCapability(AetherCapabilities.ITEM_BREAKABLE, null))
		{
			IItemBreakable breakable = stack.getCapability(AetherCapabilities.ITEM_BREAKABLE, null);

			if (!stack.hasTagCompound())
			{
				stack.setTagCompound(new NBTTagCompound());
			}

			if (!stack.getTagCompound().getBoolean("Unbreakable"))
			{
				stack.getTagCompound().setBoolean("Unbreakable", true);
				stack.setItemDamage(stack.getMaxDamage());
			}
		}

		return stack;
	}

	@Override
	public int getRecipeSize()
	{
		return 10;
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

		ItemStack[] list = new ItemStack[inv.getSizeInventory()];

		boolean found = false;

		for (int i = 0; i < inv.getSizeInventory(); ++i)
		{
			ItemStack invStack = inv.getStackInSlot(i);

			if (invStack != null)
			{
				if (invStack.getItem() == this.itemToCraftWith)
				{
					list[i] = invStack.copy();
					list[i].stackSize = 1;

					if (!found)
					{
						list[i].stackSize = -1;

						if (list[i].stackSize <= 0)
						{
							list[i] = null;
						}

						found = true;
					}
				}
			}
		}

		System.arraycopy(list, 0, stacks, 0, stacks.length);

		return stacks;
	}
}
