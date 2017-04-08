package com.gildedgames.aether.common.containers;

import com.gildedgames.aether.api.registry.simple_crafting.ISimpleRecipe;
import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.containers.slots.SlotSimpleCrafting;
import com.gildedgames.aether.common.util.helpers.RecipeUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class ContainerMasonryBench extends Container
{

	private EntityPlayer player;

	private InventoryPlayer inventory;

	private World world;

	private BlockPos pos;

	public IInventory craftResult = new InventoryCraftResult();

	private SlotSimpleCrafting craftingResult;

	public ContainerMasonryBench(EntityPlayer player, BlockPos pos)
	{
		this.player = player;
		this.inventory = player.inventory;

		this.world = player.getEntityWorld();
		this.pos = pos;

		this.createSlots();
	}

	private void createSlots()
	{
		for (int k = 0; k < 3; ++k)
		{
			for (int i1 = 0; i1 < 9; ++i1)
			{
				this.addSlotToContainer(new Slot(this.inventory, i1 + k * 9 + 9, 8 + i1 * 18, 88 + (k * 18)));
			}
		}

		for (int l = 0; l < 9; ++l)
		{
			this.addSlotToContainer(new Slot(this.inventory, l, 8 + l * 18, 146));
		}

		this.craftingResult = new SlotSimpleCrafting(this.player, null, this.craftResult, 37, 80, 52);

		this.craftingResult.setSimpleCrafting(true);

		this.addSlotToContainer(this.craftingResult);
	}

	public void onNewRecipe(ISimpleRecipe recipe)
	{
		if (recipe == null || !RecipeUtil.canCraft(this.player, recipe))
		{
			this.craftResult.setInventorySlotContents(0, ItemStack.EMPTY);
			this.craftingResult.setRecipe(null);

			return;
		}

		this.craftingResult.setRecipe(recipe);
		this.craftResult.setInventorySlotContents(0, recipe.getResult());
	}

	@Override
	public boolean canInteractWith(@Nonnull EntityPlayer player)
	{
		return this.world.getBlockState(this.pos).getBlock() == BlocksAether.masonry_bench
				&& player.getDistanceSq(this.pos.getX() + 0.5D, this.pos.getY() + 0.5D, this.pos.getZ() + 0.5D) <= 64.0D;
	}

	@Override
	public boolean canMergeSlot(ItemStack stack, Slot slotIn)
	{
		return slotIn.inventory != this.craftResult && super.canMergeSlot(stack, slotIn);
	}

	@Override
	@Nonnull
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
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
					return ItemStack.EMPTY;
				}

				slot.onSlotChange(itemstack1, itemstack);
			}
			else if (index >= 0 && index < 27)
			{
				if (!this.mergeItemStack(itemstack1, 27, 36, false))
				{
					return ItemStack.EMPTY;
				}
			}
			else if (index >= 27 && index < 36)
			{
				if (!this.mergeItemStack(itemstack1, 0, 27, false))
				{
					return ItemStack.EMPTY;
				}
			}
			else if (!this.mergeItemStack(itemstack1, 0, 36, false))
			{
				return ItemStack.EMPTY;
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
				return ItemStack.EMPTY;
			}

			slot.onTake(playerIn, itemstack1);
		}

		return itemstack;
	}

}
