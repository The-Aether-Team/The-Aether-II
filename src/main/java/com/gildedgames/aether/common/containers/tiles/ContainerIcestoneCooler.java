package com.gildedgames.aether.common.containers.tiles;

import com.gildedgames.aether.common.containers.slots.SlotCoolerOutput;
import com.gildedgames.aether.common.containers.slots.SlotCoolingItem;
import com.gildedgames.aether.common.containers.slots.SlotIrradiatedItem;
import com.gildedgames.aether.common.entities.tiles.TileEntityIcestoneCooler;
import com.gildedgames.aether.common.items.crafting.CoolerRecipes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerIcestoneCooler extends Container
{
	private final IInventory tileCooler;
	private int coolTime;
	private int totalCoolTime;
	private int coolerCoolTime;
	private int currentItemCoolTime;

	public ContainerIcestoneCooler(InventoryPlayer playerInventory, IInventory coolerInventory)
	{
		this.tileCooler = coolerInventory;
		this.addSlotToContainer(new SlotIrradiatedItem(coolerInventory, 0, 56, 17));
		this.addSlotToContainer(new SlotCoolingItem(coolerInventory, 1, 56, 53));
		this.addSlotToContainer(new SlotCoolerOutput(coolerInventory, 2, 116, 35));

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
		listener.sendAllWindowProperties(this, this.tileCooler);
	}

	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();
		for (int i = 0; i < this.listeners.size(); ++i)
		{
			IContainerListener iContainerListener = (IContainerListener)this.listeners.get(i);

			if (this.coolerCoolTime != this.tileCooler.getField(0))
			{
				iContainerListener.sendProgressBarUpdate(this, 0, this.tileCooler.getField(0));
			}

			if (this.currentItemCoolTime != this.tileCooler.getField(1))
			{
				iContainerListener.sendProgressBarUpdate(this, 1, this.tileCooler.getField(1));
			}

			if (this.coolTime != this.tileCooler.getField(2))
			{
				iContainerListener.sendProgressBarUpdate(this, 2, this.tileCooler.getField(2));
			}

			if (this.totalCoolTime != this.tileCooler.getField(3))
			{
				iContainerListener.sendProgressBarUpdate(this, 3, this.tileCooler.getField(3));
			}

			this.coolerCoolTime = this.tileCooler.getField(0);
			this.currentItemCoolTime = this.tileCooler.getField(1);
			this.coolTime = this.tileCooler.getField(2);
			this.totalCoolTime = this.tileCooler.getField(3);

		}
	}

	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int data)
	{
		this.tileCooler.setField(id, data);
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn)
	{
		return this.tileCooler.isUsableByPlayer(playerIn);
	}


	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
	{
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = (Slot)this.inventorySlots.get(index);

		if (slot != null && slot.getHasStack())
		{
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (index == 2)
			{
				if (!this.mergeItemStack(itemstack1, 3, 39, true))
				{
					return ItemStack.EMPTY;
				}

				slot.onSlotChange(itemstack1, itemstack);
			}
			else if (index != 1 && index != 0)
			{
				if (!CoolerRecipes.instance().getCoolingResult(itemstack1).isEmpty())
				{
					if (!this.mergeItemStack(itemstack1, 0, 1, false))
					{
						return ItemStack.EMPTY;
					}
				}
				else if (TileEntityIcestoneCooler.isItemCooling(itemstack1))
				{
					if (!this.mergeItemStack(itemstack1, 1, 2, false))
					{
						return ItemStack.EMPTY;
					}
				}
				else if (index >= 3 && index < 30)
				{
					if (!this.mergeItemStack(itemstack1, 30, 39, false))
					{
						return ItemStack.EMPTY;
					}
				}
				else if (index >= 30 && index < 39 && !this.mergeItemStack(itemstack1, 3, 30, false))
				{
					return ItemStack.EMPTY;
				}
			}
			else if (!this.mergeItemStack(itemstack1, 3, 39, false))
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
