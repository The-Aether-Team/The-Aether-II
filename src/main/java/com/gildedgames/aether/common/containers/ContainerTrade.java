package com.gildedgames.aether.common.containers;

import com.gildedgames.aether.common.containers.slots.SlotDynamic;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.LinkedList;
import java.util.Queue;

public class ContainerTrade extends Container
{
	private final IInventory tradeInventory = new InventoryBasic("Trade", false, 16);

	private final InventoryPlayer playerInventory;

	private final Queue<ConfirmationQueueItem> queue = new LinkedList<ConfirmationQueueItem>();

	private final boolean[][] containCheck = new boolean[4][9];

	private int openSlots;

	public ContainerTrade(final InventoryPlayer playerInventory)
	{
		this.playerInventory = playerInventory;

		for (int i = 0; i < 3; ++i)
		{
			for (int j = 0; j < 9; ++j)
			{
				this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 3 + j * 18, 28 + i * 18));
			}
		}

		for (int k = 0; k < 9; ++k)
		{
			this.addSlotToContainer(new Slot(playerInventory, k, 3 + k * 18, 86));
		}

		for (int i = 0; i < 4; ++i)
		{
			for (int j = 0; j < 4; ++j)
			{
				this.addSlotToContainer(new SlotDynamic(tradeInventory, j + i * 4, 200 + j * 18, 30 + i * 18));
			}
		}

		this.updateSlotCheck();
	}

	@Override
	public void onContainerClosed(EntityPlayer playerIn)
	{
		super.onContainerClosed(playerIn);

		if (!playerIn.world.isRemote)
		{
			clearContainer(playerIn, playerIn.world, tradeInventory);

			for (int i = 0; i < 4; ++i)
			{
				for (int j = 0; j < 4; ++j)
				{
					ItemStack itemstack = this.tradeInventory.removeStackFromSlot(j + i * 4);
				}
			}

			ConfirmationQueueItem cur;

			while ((cur = this.queue.poll()) != null)
			{
				if (!playerIn.isEntityAlive() || playerIn instanceof EntityPlayerMP && ((EntityPlayerMP)playerIn).hasDisconnected())
				{
					playerIn.dropItem(cur.item, false);
				}
				else
				{
					playerIn.inventory.placeItemBackInInventory(playerIn.world, cur.item);
				}
			}
		}
	}

	@Override
	public void addListener(final IContainerListener listener)
	{
		super.addListener(listener);
//		listener.sendAllWindowProperties(this, this.shopInventory);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(final int id, final int data)
	{
	}

	public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, EntityPlayer player)
	{
		if (clickTypeIn == ClickType.PICKUP && slotId == -999)
		{
			ItemStack stack = this.playerInventory.getItemStack();

			if (!stack.isEmpty())
			{
				if (dragType == 0)
				{
					this.queue.add(new ConfirmationQueueItem(stack, false));
					this.playerInventory.setItemStack(ItemStack.EMPTY);
				}

				if (dragType == 1)
				{
					this.queue.add(new ConfirmationQueueItem(stack.splitStack(1), false));
				}
			}

			return ItemStack.EMPTY;
		}
		else if (clickTypeIn == ClickType.THROW && this.playerInventory.getItemStack().isEmpty() && slotId >= 0)
		{
			Slot slot2 = this.inventorySlots.get(slotId);

			if (slot2 != null && slot2.getHasStack() && slot2.canTakeStack(player))
			{
				ItemStack itemstack4 = slot2.decrStackSize(dragType == 0 ? 1 : slot2.getStack().getCount());
				slot2.onTake(player, itemstack4);
				this.queue.add(new ConfirmationQueueItem(itemstack4, false));
			}

			return ItemStack.EMPTY;
		}
		else
		{
			ItemStack stack = super.slotClick(slotId, dragType, clickTypeIn, player);

			this.updateSlotCheck();

			return stack;
		}
	}

	public void updateSlotCheck()
	{
		int totalSlots = 0;

		for (int i = 0; i < 4; ++i)
		{
			for (int j = 0; j < 9; ++j)
			{
				boolean taken = !this.playerInventory.getStackInSlot(i * 9 + j).isEmpty();

				this.containCheck[i][j] = taken;

				if (!taken)
				{
					totalSlots++;
				}
			}
		}

		for (int i = 0; i < 16; i++)
		{
			((SlotDynamic) getSlot(36 + i)).setEnabled(i < totalSlots);
		}
	}

	public void addItemToQueue(ItemStack itemStack)
	{
		for (int i = 0; i < 4; ++i)
		{
			for (int j = 0; j < 9; ++j)
			{
				ItemStack stack = this.playerInventory.getStackInSlot(i * 9 + j);

				if (stack.getItem().equals(itemStack.getItem()) && !containCheck[i][j])
				{
					this.queue.add(new ConfirmationQueueItem(stack.copy(), true));

					stack.setCount(0);

					this.playerInventory.markDirty();

					break;
				}
			}
		}
	}

	@Override
	public boolean canInteractWith(final EntityPlayer playerIn)
	{
		return true;
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

			if (index < 36)
			{
				if (!this.mergeItemStack(itemstack1, 36, 52, false))
				{
					return ItemStack.EMPTY;
				}

				slot.onSlotChange(itemstack1, itemstack);
			}
			else
			{
				if (!this.mergeItemStack(itemstack1, 0, 36, true))
				{
					return ItemStack.EMPTY;
				}

				slot.onSlotChange(itemstack1, itemstack);
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

	class ConfirmationQueueItem
	{
		public ItemStack item;

		public boolean pickUp;

		public ConfirmationQueueItem(ItemStack item, boolean pickUp)
		{
			this.item = item;
			this.pickUp = pickUp;
		}
	}

}
