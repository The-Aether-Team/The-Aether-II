package com.gildedgames.aether.common.containers.tiles;

import com.gildedgames.aether.common.containers.slots.SlotAmbrosiumChunk;
import com.gildedgames.aether.common.containers.slots.SlotMoaEgg;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ContainerIncubator extends Container
{

	private final IInventory tile;

	private float currentHeatingProgress;

	private int eggTimer;

	public ContainerIncubator(int id, final PlayerInventory playerInventory, final IInventory coolerInventory)
	{
		super(type, id);

		this.tile = coolerInventory;

		this.addSlot(new SlotAmbrosiumChunk(this.tile, 0, 80, 52));

		this.addSlot(new SlotMoaEgg(this.tile, 1, 80, 17));

		for (int i = 0; i < 3; ++i)
		{
			for (int j = 0; j < 9; ++j)
			{
				this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (int k = 0; k < 9; ++k)
		{
			this.addSlot(new Slot(playerInventory, k, 8 + k * 18, 142));
		}
	}

	@Override
	public void addListener(final IContainerListener listener)
	{
		super.addListener(listener);

		listener.sendAllWindowProperties(this, this.tile);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void updateProgressBar(final int id, final int data)
	{
		this.tile.setField(id, data);
	}

	@Override
	public boolean canInteractWith(@Nonnull final PlayerEntity playerIn)
	{
		return this.tile.isUsableByPlayer(playerIn);
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

			// move from container to inv
			if (index == 0 || index == 1)
			{
				if (!this.mergeItemStack(itemstack1, 2, 38, true))
				{
					return ItemStack.EMPTY;
				}

				slot.onSlotChange(itemstack1, itemstack);
			}
			// move from inv to container
			else if (index > 1)
			{
				if (!itemstack1.isEmpty())
				{
					if (!this.mergeItemStack(itemstack1, 0, 2, false))
					{
						return ItemStack.EMPTY;
					}
				}
			}

			if (itemstack1.isEmpty())
			{
				slot.putStack(ItemStack.EMPTY);
			}
			else
			{
				slot.onSlotChanged();
			}

			slot.onTake(playerIn, itemstack1);
		}

		return itemstack;
	}

	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();

		for (final IContainerListener iContainerListener : this.listeners)
		{
			if (this.currentHeatingProgress != this.tile.getField(0))
			{
				iContainerListener.sendWindowProperty(this, 0, this.tile.getField(0));
			}

			if (this.eggTimer != this.tile.getField(1))
			{
				iContainerListener.sendWindowProperty(this, 1, this.tile.getField(1));
			}
		}

		this.currentHeatingProgress = this.tile.getField(0);
		this.eggTimer = this.tile.getField(1);
	}
}
