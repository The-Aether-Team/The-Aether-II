package com.gildedgames.aether.common.containers.tiles;

import com.gildedgames.aether.common.containers.slots.incubator.SlotIncubatorFuel;
import com.gildedgames.aether.common.containers.slots.incubator.SlotMoaEgg;
import com.gildedgames.aether.common.entities.tiles.TileEntityIcestoneCooler;
import com.gildedgames.aether.common.entities.tiles.TileEntityIncubator;
import com.gildedgames.aether.common.recipes.CoolerRecipes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

public class ContainerIncubator extends Container
{

	private final IInventory tileIncubator;

	private int incubationTimeMax;

	private int incubationTime;

	private int heatingTimeMax;

	private int heatingTime;

	public ContainerIncubator(final InventoryPlayer playerInventory, final IInventory incubatorInventory)
	{
		this.tileIncubator = incubatorInventory;

		this.addSlotToContainer(new SlotIncubatorFuel(this.tileIncubator, 0, 67, 53));

		this.addSlotToContainer(new SlotMoaEgg(this.tileIncubator, 1, 67, 17));

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
	public void addListener(final IContainerListener listener)
	{
		super.addListener(listener);

		listener.sendAllWindowProperties(this, this.tileIncubator);
	}

	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();

		for (final IContainerListener iContainerListener : this.listeners)
		{
			if (this.incubationTimeMax != this.tileIncubator.getField(0))
			{
				iContainerListener.sendWindowProperty(this, 0, this.tileIncubator.getField(0));
			}

			if (this.incubationTime != this.tileIncubator.getField(1))
			{
				iContainerListener.sendWindowProperty(this, 1, this.tileIncubator.getField(1));
			}

			if (this.heatingTimeMax != this.tileIncubator.getField(2))
			{
				iContainerListener.sendWindowProperty(this, 2, this.tileIncubator.getField(2));
			}

			if (this.heatingTime != this.tileIncubator.getField(3))
			{
				iContainerListener.sendWindowProperty(this, 3, this.tileIncubator.getField(3));
			}
		}

		this.incubationTimeMax = this.tileIncubator.getField(0);
		this.incubationTime = this.tileIncubator.getField(1);
		this.heatingTimeMax = this.tileIncubator.getField(2);
		this.heatingTime = this.tileIncubator.getField(3);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(final int id, final int data)
	{
		this.tileIncubator.setField(id, data);
	}

	@Override
	public boolean canInteractWith(@Nonnull final EntityPlayer playerIn)
	{
		return this.tileIncubator.isUsableByPlayer(playerIn);
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

			if (index != 0 && index != 1)
			{
				if (TileEntityIncubator.isItemFuel(itemstack1))
				{
					if (!this.mergeItemStack(itemstack1, 0, 1, true))
					{
						return ItemStack.EMPTY;
					}
				}
				else if (TileEntityIncubator.isItemEgg(itemstack1))
				{
					if (!this.mergeItemStack(itemstack1, 1, 2, true))
					{
						return ItemStack.EMPTY;
					}
				}
			}
			else if (!this.mergeItemStack(itemstack1, 2, 38, false))
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
