package com.gildedgames.aether.common.containers.tiles;

import com.gildedgames.aether.common.containers.ContainerTypesAether;
import com.gildedgames.aether.common.containers.slots.SlotAmbrosiumChunk;
import com.gildedgames.aether.common.containers.slots.SlotMoaEgg;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIntArray;

import javax.annotation.Nonnull;

public class ContainerIncubator extends Container
{
	private final IInventory inventory;

	private final IIntArray fields;

	public ContainerIncubator(int id, PlayerInventory playerInventory, IInventory coolerInventory, IIntArray fields)
	{
		super(ContainerTypesAether.INCUBATOR, id);

		this.func_216961_a(fields);

		this.inventory = coolerInventory;
		this.fields = fields;

		this.addSlot(new SlotAmbrosiumChunk(this.inventory, 0, 80, 52));

		this.addSlot(new SlotMoaEgg(this.inventory, 1, 80, 17));

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
	public boolean canInteractWith(@Nonnull final PlayerEntity playerIn)
	{
		return this.inventory.isUsableByPlayer(playerIn);
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
			else
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

	public int getCurrentHeatingProgress()
	{
		return this.fields.get(0);
	}

	public int getEggTimer()
	{
		return this.fields.get(1);
	}

	public ItemStack getMoaEggStack()
	{
		return this.inventory.getStackInSlot(1);
	}
}
