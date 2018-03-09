package com.gildedgames.aether.common.recipes;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.items.blocks.ItemBlockPresent;
import com.gildedgames.aether.common.items.misc.ItemWrappingPaper;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.registries.IForgeRegistryEntry;

//TODO:
public class RecipePresentCrafting extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe
{
	private static final NonNullList<Ingredient> ingredients = CraftingHelper.parseShaped("PPP", "P P", "PPP", 'P', new ItemStack(ItemsAether.wrapping_paper)).input;

	@Override
	public boolean matches(final InventoryCrafting inventory, final World world)
	{
		ItemWrappingPaper.PresentDyeData masterDye = null;

		for (int i = 0; i < inventory.getSizeInventory(); i++)
		{
			if (i == 4)
			{
				continue;
			}

			final ItemStack stack = inventory.getStackInSlot(i);

			if (stack.getItem() != ItemsAether.wrapping_paper)
			{
				return false;
			}

			final ItemWrappingPaper.PresentDyeData dye = ItemWrappingPaper.PresentDyeData.readFromNBT(stack.getTagCompound());

			if (masterDye != null)
			{
				if (dye.getBowColor() != masterDye.getBowColor() || dye.getBoxColor() != masterDye.getBoxColor())
				{
					return false;
				}
			}

			masterDye = dye;
		}

		final ItemStack centerItem = inventory.getStackInSlot(4);

		return centerItem.getItem() != Item.getItemFromBlock(BlocksAether.present);
	}

	@Override
	public ItemStack getCraftingResult(final InventoryCrafting inventory)
	{
		final ItemStack stack = inventory.getStackInSlot(4).copy();
		stack.setCount(1);

		final ItemWrappingPaper.PresentDyeData dye = ItemWrappingPaper.getDyeData(inventory.getStackInSlot(0));

		final ItemBlockPresent.PresentData data = new ItemBlockPresent.PresentData();
		data.setStack(stack);
		data.setDye(dye);

		final ItemStack present = new ItemStack(BlocksAether.present);
		present.setTagCompound(data.writeToNBT(new NBTTagCompound()));

		return present;
	}

	@Override
	public boolean canFit(final int width, final int height)
	{
		return width >= 3 && height >= 3;
	}

	@Override
	public ItemStack getRecipeOutput()
	{
		return new ItemStack(BlocksAether.present);
	}

	@Override
	public NonNullList<Ingredient> getIngredients()
	{
		return ingredients;
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
