package com.gildedgames.aether.common.containers.slots;

import com.gildedgames.aether.api.recipes.simple.ISimpleRecipe;
import com.gildedgames.aether.common.recipes.simple.OreDictionaryRequirement;
import com.gildedgames.aether.common.util.helpers.RecipeUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeHooks;

public class SlotSimpleCrafting extends SlotCrafting
{

	private ISimpleRecipe recipe;

	private boolean isSimpleCrafting;

	public SlotSimpleCrafting(EntityPlayer player, InventoryCrafting craftingInventory, IInventory inventoryIn, int slotIndex, int xPosition, int yPosition)
	{
		super(player, craftingInventory, inventoryIn, slotIndex, xPosition, yPosition);
	}

	public boolean isSimpleCrafting()
	{
		return this.isSimpleCrafting;
	}

	public void setSimpleCrafting(boolean flag)
	{
		this.isSimpleCrafting = flag;
	}

	public ISimpleRecipe getRecipe()
	{
		return this.recipe;
	}

	public void setRecipe(ISimpleRecipe recipe)
	{
		this.recipe = recipe;
	}

	@Override
	public boolean canTakeStack(EntityPlayer player)
	{
		if (!this.isSimpleCrafting())
		{
			return super.canTakeStack(player);
		}

		if (!RecipeUtil.canCraft(player, this.recipe))
		{
			this.putStack(ItemStack.EMPTY);

			return false;
		}

		return true;
	}

	@Override
	public ItemStack onTake(EntityPlayer player, ItemStack stack)
	{
		if (!this.isSimpleCrafting())
		{
			return super.onTake(player, stack);
		}

		this.onCrafting(stack);

		ForgeHooks.setCraftingPlayer(player);
		//ItemStack[] aitemstack = CraftingManager.getInstance().getRemainingItems(this.craftMatrix, playerIn.worldObj);
		/** TODO: Implement remaining items like buckets ^ **/
		ForgeHooks.setCraftingPlayer(null);

		outerloop:
		for (int reqIndex = 0; reqIndex < this.getRecipe().getRequired().length; reqIndex++)
		{
			Object req = this.recipe.getRequired()[reqIndex];

			int reqAmount = 0;

			if (req instanceof ItemStack)
			{
				reqAmount = ((ItemStack) req).getCount();
			}
			else if (req instanceof OreDictionaryRequirement)
			{
				reqAmount = ((OreDictionaryRequirement) req).getCount();
			}

			int amountSoFar = 0;

			for (int i = 0; i < player.inventory.mainInventory.size(); i++)
			{
				ItemStack inventoryStack = player.inventory.mainInventory.get(i);

				if (RecipeUtil.areEqual(req, inventoryStack))
				{
					int oldSize = inventoryStack.getCount();

					inventoryStack.setCount(inventoryStack.getCount() - Math.min(reqAmount - amountSoFar, reqAmount));
					amountSoFar += Math.min(oldSize, reqAmount);

					if (inventoryStack.getCount() <= 0)
					{
						player.inventory.deleteStack(inventoryStack);
					}

					if (amountSoFar >= reqAmount)
					{
						continue outerloop;
					}
				}
			}
		}

		if (RecipeUtil.canCraft(player, this.recipe))
		{
			this.putStack(this.recipe.getResult());
		}

		return stack;
	}
}