package com.gildedgames.aether.common.containers.tiles;

import com.gildedgames.aether.api.recipes.simple.ISimpleRecipe;
import com.gildedgames.aether.api.registrar.BlocksAether;
import com.gildedgames.aether.common.containers.ContainerTypesAether;
import com.gildedgames.aether.common.containers.inventory.InventoryCraftResultSimple;
import com.gildedgames.aether.common.containers.slots.SlotSimpleCrafting;
import com.gildedgames.aether.common.util.helpers.RecipeUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.MathHelper;

import javax.annotation.Nullable;

public class ContainerMasonryBench extends Container
{

	public final IInventory craftResult = new InventoryCraftResultSimple();

	public SlotSimpleCrafting craftingResult;

	private final PlayerInventory inventory;

	private final IWorldPosCallable posCallable;

	private int inputCount = 1;

	protected ContainerMasonryBench(int id, PlayerEntity player, IWorldPosCallable posCallable)
	{
		super(ContainerTypesAether.MASONRY_BENCH, id);

		this.inventory = player.inventory;
		this.posCallable = posCallable;

		for (int y = 0; y < 3; ++y)
		{
			for (int x = 0; x < 9; ++x)
			{
				this.addSlot(new Slot(this.inventory, x + y * 9 + 9, 8 + x * 18, 88 + (y * 18)));
			}
		}

		for (int x = 0; x < 9; ++x)
		{
			this.addSlot(new Slot(this.inventory, x, 8 + x * 18, 146));
		}

		this.craftingResult = new SlotSimpleCrafting(player, null, this.craftResult, 37, 106, 37, this);
		this.craftingResult.setSimpleCrafting(true);

		this.addSlot(this.craftingResult);
	}

	public int getInputCount()
	{
		int actual = this.craftingResult.getRecipe() != null ? this.craftingResult.getActualAmountOfReq(this.inventory) : this.inputCount;

		return MathHelper.clamp(this.inputCount, 1, actual);
	}

	public void setInputCount(int inputCount)
	{
		this.inputCount = MathHelper.clamp(inputCount, 1, this.craftResult.getSizeInventory());

		ItemStack stack = this.craftResult.getStackInSlot(0);

		if (!stack.isEmpty())
		{
			stack.setCount(this.getInputCount());
		}
	}

	public void onNewRecipe(ISimpleRecipe recipe)
	{
		if (!RecipeUtil.canCraft(this.inventory, recipe))
		{
			this.craftResult.setInventorySlotContents(0, ItemStack.EMPTY);
			this.craftingResult.setRecipe(null);

			return;
		}

		recipe.getResult().setCount(this.getInputCount());

		this.craftingResult.setRecipe(recipe);
		this.craftResult.setInventorySlotContents(0, recipe.getResult());
	}

	@Override
	public boolean canInteractWith(PlayerEntity player)
	{
		return isWithinUsableDistance(this.posCallable, player, BlocksAether.masonry_bench);
	}

	@Override
	public boolean canMergeSlot(ItemStack stack, Slot slotIn)
	{
		return slotIn.inventory != this.craftResult && super.canMergeSlot(stack, slotIn);
	}

	@Override
	@Nullable
	public ItemStack transferStackInSlot(PlayerEntity playerIn, int index)
	{
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.inventorySlots.get(index);

		if (slot != null && slot.getHasStack())
		{
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (index == 36)
			{
				if (!this.mergeItemStack(itemstack1, 0, 36, true))
				{
					return null;
				}

				slot.onSlotChange(itemstack1, itemstack);
			}
			else if (index >= 0 && index < 27)
			{
				if (!this.mergeItemStack(itemstack1, 27, 36, false))
				{
					return null;
				}
			}
			else if (index >= 27 && index < 36)
			{
				if (!this.mergeItemStack(itemstack1, 0, 27, false))
				{
					return null;
				}
			}
			else if (!this.mergeItemStack(itemstack1, 0, 36, false))
			{
				return null;
			}

			if (itemstack1.getCount() == 0)
			{
				slot.putStack(ItemStack.EMPTY);
			}
			else
			{
				slot.onSlotChanged();
			}

			if (itemstack1.getCount() == itemstack.getCount())
			{
				return null;
			}

			slot.onTake(playerIn, itemstack1);
		}

		return itemstack;
	}

}