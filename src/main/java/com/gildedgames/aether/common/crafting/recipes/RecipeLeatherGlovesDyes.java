package com.gildedgames.aether.common.crafting.recipes;

import com.gildedgames.aether.common.items.armor.ItemLeatherGloves;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

import java.util.ArrayList;
import java.util.List;

public class RecipeLeatherGlovesDyes implements IRecipe
{
	@Override
	public boolean matches(InventoryCrafting inventory, World world)
	{
		ItemStack glovesStack = null;

		List<ItemStack> list = new ArrayList<>();

		for (int i = 0; i < inventory.getSizeInventory(); ++i)
		{
			ItemStack invStack = inventory.getStackInSlot(i);

			if (invStack != null)
			{
				if (invStack.getItem() instanceof ItemLeatherGloves)
				{
					glovesStack = invStack;
				}
				else
				{
					if (invStack.getItem() != Items.DYE)
					{
						return false;
					}

					list.add(invStack);
				}
			}
		}

		return glovesStack != null && !list.isEmpty();
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv)
	{
		ItemStack stack = null;
		int[] colors = new int[3];
		int intensity = 0;
		int j = 0;

		ItemLeatherGloves leatherGloves = null;

		for (int k = 0; k < inv.getSizeInventory(); ++k)
		{
			ItemStack invStack = inv.getStackInSlot(k);

			if (invStack != null)
			{
				if (invStack.getItem() instanceof ItemLeatherGloves)
				{
					leatherGloves = (ItemLeatherGloves) invStack.getItem();

					stack = invStack.copy();
					stack.stackSize = 1;

					if (ItemLeatherGloves.hasColor(invStack))
					{
						int color = ItemLeatherGloves.getColor(stack);

						float r = (float)(color >> 16 & 255) / 255.0F;
						float g = (float)(color >> 8 & 255) / 255.0F;
						float b = (float)(color & 255) / 255.0F;

						intensity = (int)((float)intensity + Math.max(r, Math.max(g, b)) * 255.0F);

						colors[0] = (int)((float)colors[0] + r * 255.0F);
						colors[1] = (int)((float)colors[1] + g * 255.0F);
						colors[2] = (int)((float)colors[2] + b * 255.0F);

						++j;
					}
				}
				else
				{
					if (invStack.getItem() != Items.DYE)
					{
						return null;
					}

					float[] afloat = EntitySheep.getDyeRgb(EnumDyeColor.byDyeDamage(invStack.getMetadata()));

					int r2 = (int)(afloat[0] * 255.0F);
					int g2 = (int)(afloat[1] * 255.0F);
					int b2 = (int)(afloat[2] * 255.0F);

					intensity += Math.max(r2, Math.max(g2, b2));

					colors[0] += r2;
					colors[1] += g2;
					colors[2] += b2;

					++j;
				}
			}
		}

		if (leatherGloves == null)
		{
			return null;
		}
		else
		{
			int r = colors[0] / j;
			int g = colors[1] / j;
			int b = colors[2] / j;

			float f3 = (float)intensity / (float)j;
			float f4 = (float)Math.max(r, Math.max(g, b));

			r = (int)((float)r * f3 / f4);
			g = (int)((float)g * f3 / f4);
			b = (int)((float)b * f3 / f4);

			int color = (r << 8) + g;
			color = (color << 8) + b;

			ItemLeatherGloves.setColor(stack, color);

			return stack;
		}
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

		for (int i = 0; i < stacks.length; ++i)
		{
			ItemStack stack = inv.getStackInSlot(i);

			stacks[i] = ForgeHooks.getContainerItem(stack);
		}

		return stacks;
	}
}
