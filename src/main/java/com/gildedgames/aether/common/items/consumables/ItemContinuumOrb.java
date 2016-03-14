package com.gildedgames.aether.common.items.consumables;

import com.gildedgames.aether.common.recipes.loot.IItemSelector;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ItemContinuumOrb extends Item
{
	private final ContinuumItemSelector selector = new ContinuumItemSelector();

	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		if (world.isRemote)
		{
			return stack;
		}

		ItemStack randStack = selector.getRandomItem(world.rand);

		if (stack.stackSize > 1)
		{
			stack.stackSize--;

			if (!player.inventory.addItemStackToInventory(randStack))
			{
				player.dropPlayerItemWithRandomChoice(randStack, false);
			}

			return stack;
		}

		return randStack;
	}

	public class ContinuumItemSelector implements IItemSelector
	{
		private ArrayList<IRecipe> validRecipeCache;

		@Override
		public ItemStack getRandomItem(Random random)
		{
			if (this.validRecipeCache == null)
			{
				this.validRecipeCache = new ArrayList<>();

				for (IRecipe recipe : CraftingManager.getInstance().getRecipeList())
				{
					if (recipe instanceof ShapedRecipes || recipe instanceof ShapelessRecipes)
					{
						validRecipeCache.add(recipe);
					}
				}
			}

			IRecipe recipe = this.validRecipeCache.get(random.nextInt(this.validRecipeCache.size()));

			ItemStack stack = recipe.getRecipeOutput();
			stack.stackSize = 1;

			return stack;
		}
	}

}
