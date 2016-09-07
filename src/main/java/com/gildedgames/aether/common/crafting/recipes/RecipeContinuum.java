package com.gildedgames.aether.common.crafting.recipes;

import com.gildedgames.aether.api.capabilites.AetherCapabilities;
import com.gildedgames.aether.api.capabilites.items.IItemBreakable;
import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.items.armor.ItemLeatherGloves;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

import java.util.ArrayList;
import java.util.List;

public class RecipeContinuum implements IRecipe
{
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
					if (invStack.getItem() != ItemsAether.continuum_orb)
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
				if (invStack.getItem() == ItemsAether.continuum_orb)
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

		for (int i = 0; i < stacks.length; ++i)
		{
			stacks[i] = list[i];
		}

		return stacks;
	}
}
