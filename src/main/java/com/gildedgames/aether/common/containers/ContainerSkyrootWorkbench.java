package com.gildedgames.aether.common.containers;

import com.gildedgames.aether.common.blocks.BlocksAether;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerSkyrootWorkbench extends Container
{
	private InventoryCrafting craftMatrix = new InventoryCrafting(this, 3, 3);

	private IInventory craftResult = new InventoryCraftResult();

	private final World world;

	private final BlockPos pos;

	public ContainerSkyrootWorkbench(InventoryPlayer playerInventory, World worldIn, BlockPos posIn)
	{
		this.world = worldIn;
		this.pos = posIn;

		this.addSlotToContainer(new SlotCrafting(playerInventory.player, this.craftMatrix, this.craftResult, 0, 274, 41));

		// Crafting Matrix
		for (int x = 0; x < 3; ++x)
		{
			for (int y = 0; y < 3; ++y)
			{
				this.addSlotToContainer(new Slot(this.craftMatrix, y + x * 3, 180 + y * 18, 23 + x * 18));
			}
		}

		// Inventory Slots
		for (int x = 0; x < 3; ++x)
		{
			for (int y = 0; y < 9; ++y)
			{
				this.addSlotToContainer(new Slot(playerInventory, y + x * 9 + 9, 158 + y * 18, 90 + x * 18));
			}
		}

		// Hot Bar Inventory Slots
		for (int i = 0; i < 9; ++i)
		{
			this.addSlotToContainer(new Slot(playerInventory, i, 158 + i * 18, 148));
		}

		this.onCraftMatrixChanged(this.craftMatrix);
	}

	@Override
	public void onCraftMatrixChanged(IInventory inventoryIn)
	{
		this.craftResult.setInventorySlotContents(0, CraftingManager.getInstance().findMatchingRecipe(this.craftMatrix, this.world));
	}

	@Override
	public void onContainerClosed(EntityPlayer playerIn)
	{
		super.onContainerClosed(playerIn);

		if (!this.world.isRemote)
		{
			for (int i = 0; i < 9; ++i)
			{
				ItemStack itemstack = this.craftMatrix.removeStackFromSlot(i);

				if (!itemstack.isEmpty())
				{
					playerIn.dropItem(itemstack, false);
				}
			}
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn)
	{
		return this.world.getBlockState(this.pos).getBlock() == BlocksAether.aether_crafting_table &&
				playerIn.getDistanceSq(this.pos.getX() + 0.5D, this.pos.getY() + 0.5D, this.pos.getZ() + 0.5D) <= 64.0D;
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
	{
		ItemStack returnStack = ItemStack.EMPTY;

		Slot slot = this.inventorySlots.get(index);

		if (slot != null && slot.getHasStack())
		{
			ItemStack stack = slot.getStack();

			returnStack = stack.copy();

			if (index == 0)
			{
				stack.getItem().onCreated(stack, this.world, playerIn);

				if (!this.mergeItemStack(stack, 10, 46, true))
				{
					return ItemStack.EMPTY;
				}

				slot.onSlotChange(stack, returnStack);
			}
			else if (index >= 10 && index < 37)
			{
				if (!this.mergeItemStack(stack, 37, 46, false))
				{
					return ItemStack.EMPTY;
				}
			}
			else if (index >= 37 && index < 46)
			{
				if (!this.mergeItemStack(stack, 10, 37, false))
				{
					return ItemStack.EMPTY;
				}
			}
			else if (!this.mergeItemStack(stack, 10, 46, false))
			{
				return ItemStack.EMPTY;
			}

			if (stack.isEmpty())
			{
				slot.putStack(ItemStack.EMPTY);
			}
			else
			{
				slot.onSlotChanged();
			}

			if (stack.getCount() == returnStack.getCount())
			{
				return ItemStack.EMPTY;
			}

			ItemStack takeStack = slot.onTake(playerIn, stack);

			if (index == 0)
			{
				playerIn.dropItem(takeStack, false);
			}
		}

		return returnStack;
	}

	@Override
	public boolean canMergeSlot(ItemStack stack, Slot slotIn)
	{
		return slotIn.inventory != this.craftResult && super.canMergeSlot(stack, slotIn);
	}

}
