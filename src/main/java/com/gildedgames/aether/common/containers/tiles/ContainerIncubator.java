package com.gildedgames.aether.common.containers.tiles;

import com.gildedgames.aether.common.containers.slots.SlotAmbrosium;
import com.gildedgames.aether.common.containers.slots.SlotAmbrosiumChunk;
import com.gildedgames.aether.common.containers.slots.SlotInventory;
import com.gildedgames.aether.common.containers.slots.SlotMoaEgg;
import com.gildedgames.aether.common.entities.tiles.TileEntityIncubator;
import com.gildedgames.aether.common.items.ItemsAether;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnaceFuel;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

public class ContainerIncubator extends Container
{

	private final IInventory tile;
	private float currentHeatingProgress;
	private int eggTimer;

	public ContainerIncubator(InventoryPlayer playerInventory, IInventory coolerInventory)
	{
		this.tile = coolerInventory;

		// ambrosium chunk slot
		this.addSlotToContainer(new SlotAmbrosiumChunk(this.tile, 0, 80, 52));

		// egg slot
		this.addSlotToContainer(new SlotMoaEgg(this.tile, 1, 80, 17));

		// player inventory
		for (int i = 0; i < 3; ++i)
		{
			for (int j = 0; j < 9; ++j)
			{
				this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (int k = 0; k < 9; ++k)
		{
			this.addSlotToContainer(new Slot(playerInventory, k, 8 + k * 18, 142));
		}
	}

	@Override
	public void addListener(IContainerListener listener)
	{
		super.addListener(listener);

		listener.sendAllWindowProperties(this, this.tile);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int data)
	{
		this.tile.setField(id, data);
	}

	@Override
	public boolean canInteractWith(@Nonnull EntityPlayer playerIn)
	{
		return this.tile.isUsableByPlayer(playerIn);
	}

	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
	{
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = (Slot)this.inventorySlots.get(index);

		if (slot != null && slot.getHasStack())
		{
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (index == 0 || index == 1)
			{
				if (!this.mergeItemStack(itemstack1, 2, 38, true))
				{
					return ItemStack.EMPTY;
				}

				slot.onSlotChange(itemstack1, itemstack);
			}
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

		for (int i = 0; i < this.listeners.size(); ++i)
		{
			IContainerListener iContainerListener = (IContainerListener)this.listeners.get(i);
			if (this.currentHeatingProgress != this.tile.getField(0))
			{
				iContainerListener.sendProgressBarUpdate(this, 0, this.tile.getField(0));
			}

			if (this.eggTimer != this.tile.getField(1))
			{
				iContainerListener.sendProgressBarUpdate(this, 1, this.tile.getField(1));
			}
		}

		this.currentHeatingProgress = this.tile.getField(0);
		this.eggTimer = this.tile.getField(1);
	}
}
