package com.gildedgames.aether.common.containers;

import com.gildedgames.aether.api.shop.IShopInstance;
import com.gildedgames.aether.common.containers.slots.SlotSell;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerShop extends Container
{
	private final IInventory shopInventory;

	private final IShopInstance shopInstance;

	public ContainerShop(final InventoryPlayer playerInventory, IShopInstance shopInstance)
	{
		this.shopInstance = shopInstance;
		this.shopInventory = shopInstance.getInventory(playerInventory.player);

		this.addSlotToContainer(new SlotSell(this.shopInventory, 0, 257, 7));

		for (int i = 0; i < 3; ++i)
		{
			for (int j = 0; j < 9; ++j)
			{
				this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 186 + 7 + j * 18, 19 + 14 + i * 18));
			}
		}

		for (int k = 0; k < 9; ++k)
		{
			this.addSlotToContainer(new Slot(playerInventory, k, 186 + 7 + k * 18, 77 + 14));
		}
	}

	public IShopInstance getShopInstance()
	{
		return this.shopInstance;
	}

	@Override
	public void onContainerClosed(EntityPlayer playerIn)
	{
		super.onContainerClosed(playerIn);

		if (!playerIn.world.isRemote)
		{
			ItemStack itemstack = this.shopInventory.removeStackFromSlot(0);

			if (!itemstack.isEmpty())
			{
				//TODO: Figure out why this is destroying the item - you can pick it up, but it's a fake entity I believe
				//playerIn.dropItem(itemstack, false);
			}
		}
	}

	@Override
	public void addListener(final IContainerListener listener)
	{
		super.addListener(listener);
		listener.sendAllWindowProperties(this, this.shopInventory);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(final int id, final int data)
	{
		this.shopInventory.setField(id, data);
	}

	@Override
	public boolean canInteractWith(final EntityPlayer playerIn)
	{
		return this.shopInventory.isUsableByPlayer(playerIn);
	}

	@Override
	public ItemStack transferStackInSlot(final EntityPlayer playerIn, final int index)
	{
		ItemStack itemstack = ItemStack.EMPTY;
		final Slot slot = this.inventorySlots.get(index);

		if (slot != null && slot.getHasStack())
		{
			final ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (index == 0)
			{
				if (!this.mergeItemStack(itemstack1, 1, 37, true))
				{
					return ItemStack.EMPTY;
				}

				slot.onSlotChange(itemstack1, itemstack);
			}
			if (index >= 1 && index < 28)
			{
				if (!this.mergeItemStack(itemstack1, 0, 1, false) && !this.mergeItemStack(itemstack1, 28, 37, false))
				{
					return ItemStack.EMPTY;
				}
			}
			else if (index >= 28 && index < 37 && !this.mergeItemStack(itemstack1, 0, 28, false))
			{
				return ItemStack.EMPTY;
			}
			else if (!this.mergeItemStack(itemstack1, 0, 37, false))
			{
				return ItemStack.EMPTY;
			}

			if (itemstack1.isEmpty())
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
