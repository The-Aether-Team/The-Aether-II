package com.gildedgames.aether.common.containers;

import com.gildedgames.aether.api.shop.IShopInstance;
import com.gildedgames.aether.common.containers.slots.SlotSell;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

public class ContainerShop extends Container
{
	private final IInventory shopInventory;

	private final IShopInstance shopInstance;

	public ContainerShop(@Nullable ContainerType<?> type, int id, PlayerInventory playerInventory, IShopInstance shopInstance)
	{
		super(type, id);

		this.shopInstance = shopInstance;
		this.shopInventory = shopInstance.getInventory(playerInventory.player);

		this.addSlot(new SlotSell(this.shopInventory, 0, 257, 7));

		for (int i = 0; i < 3; ++i)
		{
			for (int j = 0; j < 9; ++j)
			{
				this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 186 + 7 + j * 18, 19 + 14 + i * 18));
			}
		}

		for (int k = 0; k < 9; ++k)
		{
			this.addSlot(new Slot(playerInventory, k, 186 + 7 + k * 18, 77 + 14));
		}
	}

	public IShopInstance getShopInstance()
	{
		return this.shopInstance;
	}

	@Override
	public void onContainerClosed(PlayerEntity playerIn)
	{
		super.onContainerClosed(playerIn);

		if (!playerIn.world.isRemote())
		{
			if (!playerIn.isAlive() || playerIn instanceof ServerPlayerEntity && ((ServerPlayerEntity)playerIn).hasDisconnected())
			{
				playerIn.dropItem(this.shopInventory.removeStackFromSlot(0), false);
			}
			else
			{
				playerIn.inventory.placeItemBackInInventory(playerIn.world, this.shopInventory.removeStackFromSlot(0));
			}
		}
	}

	@Override
	public boolean canInteractWith(final PlayerEntity playerIn)
	{
		return this.shopInventory.isUsableByPlayer(playerIn);
	}

	@Override
	public ItemStack transferStackInSlot(final PlayerEntity playerIn, final int index)
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
