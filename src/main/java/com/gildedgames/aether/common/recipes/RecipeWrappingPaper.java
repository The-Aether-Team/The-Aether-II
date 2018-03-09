package com.gildedgames.aether.common.recipes;

import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.items.misc.ItemWrappingPaper;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistryEntry;

//TODO: COMPLETELY WRONG, RECIPE SYSTEM CHANGED
public class RecipeWrappingPaper extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe
{
	private static final NonNullList<Ingredient> ingredients = CraftingHelper.parseShaped(" 1 ", " P ", " 2 ",
			'1', new ItemStack(Items.DYE, 1, OreDictionary.WILDCARD_VALUE),
			'2', new ItemStack(Items.DYE, 1, OreDictionary.WILDCARD_VALUE),
			'P', new ItemStack(Items.PAPER)).input;

	@Override
	public boolean matches(final InventoryCrafting inventory, final World world)
	{
		int paperLoc = -1;

		for (int i = 3; i < (inventory.getSizeInventory() - 3); i++)
		{
			final ItemStack stack = inventory.getStackInSlot(i);

			if (stack.getItem() == Items.PAPER)
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

		final ItemStack bowDye = inventory.getStackInSlot(paperLoc - 3);
		final ItemStack boxDye = inventory.getStackInSlot(paperLoc + 3);

		return this.isValidDye(bowDye) && this.isValidDye(boxDye);
	}

	private boolean isValidDye(final ItemStack stack)
	{
		return stack != null && stack.getItem() instanceof ItemDye;
	}

	@Override
	public ItemStack getCraftingResult(final InventoryCrafting inventory)
	{
		int paperLoc = -1;

		for (int i = 3; i < (inventory.getSizeInventory() - 3); i++)
		{
			final ItemStack stack = inventory.getStackInSlot(i);

			if (stack.getItem() == Items.PAPER)
			{
				paperLoc = i;

				break;
			}
		}

		final ItemStack bowDye = inventory.getStackInSlot(paperLoc - 3);
		final ItemStack boxDye = inventory.getStackInSlot(paperLoc + 3);

		final ItemWrappingPaper.PresentDyeData data = new ItemWrappingPaper.PresentDyeData();

		if (!bowDye.isEmpty())
		{
			data.setBowColor((byte) bowDye.getMetadata());
		}

		if (!boxDye.isEmpty())
		{
			data.setBoxColor((byte) boxDye.getMetadata());
		}

		final ItemStack output = new ItemStack(ItemsAether.wrapping_paper, 8);

		output.setTagCompound(new NBTTagCompound());
		data.writeToNBT(output.getTagCompound());

		return output;
	}

	@Override
	public NonNullList<Ingredient> getIngredients()
	{
		return ingredients;
	}

	@Override
	public boolean canFit(final int width, final int height)
	{
		return width >= 1 && height >= 3;
	}

	@Override
	public ItemStack getRecipeOutput()
	{
		return new ItemStack(ItemsAether.wrapping_paper, 8);
	}

	@Override
	public NonNullList<ItemStack> getRemainingItems(final InventoryCrafting inv)
	{
		final NonNullList<ItemStack> stacks = NonNullList.withSize(inv.getSizeInventory(), ItemStack.EMPTY);

		for (int i = 0; i < stacks.size(); ++i)
		{
			final ItemStack stack = inv.getStackInSlot(i);
			stacks.set(i, ForgeHooks.getContainerItem(stack));
		}

		return stacks;
	}
}
