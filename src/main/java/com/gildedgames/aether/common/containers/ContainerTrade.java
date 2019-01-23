package com.gildedgames.aether.common.containers;

import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerTradeModule;
import com.gildedgames.aether.common.containers.slots.SlotDynamic;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.aether.common.network.packets.trade.PacketSendInventorySize;
import com.gildedgames.aether.common.network.packets.trade.PacketTradeInventory;
import com.gildedgames.aether.common.network.packets.trade.PacketTradeMessage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;

import java.util.LinkedList;
import java.util.Queue;

public class ContainerTrade extends Container
{
	private final InventoryBasic tradeInventory = new InventoryBasic("Trade", false, 16);

	private final InventoryPlayer playerInventory;

	private final PlayerTradeModule tradeModule;

	private final Queue<ConfirmationQueueItem> queue = new LinkedList<>();

	private final boolean[][] containCheck = new boolean[4][9];

	private boolean sentError = false;

	public ContainerTrade(final InventoryPlayer playerInventory)
	{
		this.playerInventory = playerInventory;
		this.tradeModule = PlayerAether.getPlayer(playerInventory.player).getTradingModule();

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
				this.addSlotToContainer(new SlotDynamic(this.tradeInventory, j + i * 4, 200 + j * 18, 30 + i * 18));
			}
		}

		this.updateSlotCheck(true);
	}

	@Override
	public void onContainerClosed(EntityPlayer playerIn)
	{
		super.onContainerClosed(playerIn);

		if (!playerIn.world.isRemote)
		{
			this.clearContainer(playerIn, playerIn.world, this.tradeInventory);

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

			if (this.tradeModule.isTrading())
			{
				PlayerTradeModule otherTrade = this.tradeModule.getTarget().getTradingModule();

				otherTrade.endTrade(this.tradeModule.getPlayer());

				this.tradeModule.setTrading(null);
			}
		}
	}

	@Override
	public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, EntityPlayer player)
	{
		ItemStack returnStack;

		if (this.tradeModule.isLockedIn() && slotId >= 36)
		{
			return ItemStack.EMPTY;
		}

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

			returnStack = ItemStack.EMPTY;
		}
		else if (clickTypeIn == ClickType.THROW && this.playerInventory.getItemStack().isEmpty() && slotId >= 0)
		{
			Slot slot2 = this.inventorySlots.get(slotId);

			if (slot2 != null && slot2.getHasStack() && slot2.canTakeStack(player))
			{
				ItemStack itemStack4 = slot2.decrStackSize(dragType == 0 ? 1 : slot2.getStack().getCount());
				slot2.onTake(player, itemStack4);
				this.queue.add(new ConfirmationQueueItem(itemStack4, false));
			}

			returnStack = ItemStack.EMPTY;
		}
		else
		{
			ItemStack stack = super.slotClick(slotId, dragType, clickTypeIn, player);

			this.updateSlotCheck(false);

			returnStack = stack;
		}

		this.compressAndSendStacks(clickTypeIn == ClickType.PICKUP_ALL || slotId >= 36);

		return returnStack;
	}

	private void compressAndSendStacks(boolean forceSend)
	{
		boolean change = false;

		for (int i = 36; i < 52; i++)
		{
			if (this.mergeItemStack(this.getSlot(i).getStack(), 36, i, false))
			{
				change = true;
			}
		}

		if (change || forceSend)
		{
			this.sendInventory();
		}
	}

	public void updateSlotCheck(boolean forceSend)
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

		if (!this.tradeModule.getWorld().isRemote)
		{
			if (totalSlots != this.tradeModule.getOpenSlots() || forceSend)
			{
				NetworkingAether.sendPacketToPlayer(new PacketSendInventorySize((byte) totalSlots), this.tradeModule.getTargetMP());
			}

			this.tradeModule.setOpenSlots(totalSlots);
			this.tradeModule.getTarget().getTradingModule().setTradeSlots(totalSlots);
		}

		this.tradeModule.setOpenSlots(totalSlots);
	}

	private void sendInventory()
	{
		if (!this.tradeModule.getWorld().isRemote)
		{
			NetworkingAether.sendPacketToPlayer(new PacketTradeInventory(this.tradeInventory), this.tradeModule.getTargetMP());
		}
	}

	public void updateSlots(int totalSlots)
	{
		boolean error = false;

		for (int i = 0; i < 16; i++)
		{
			boolean enabled = i < totalSlots;
			SlotDynamic slot = ((SlotDynamic) this.getSlot(36 + i));

			if (!enabled && slot.getHasStack())
			{
				error = true;
			}

			slot.setEnabled(enabled);
		}

		if (!this.tradeModule.getWorld().isRemote)
		{
			if (error && !this.sentError)
			{
				this.tradeModule.sizeError(true);
				this.tradeModule.getTarget().getTradingModule().sizeError(true);
				NetworkingAether.sendPacketToPlayer(new PacketTradeMessage("aether.trade.message.tradewarn"), this.tradeModule.getPlayerMP());
				NetworkingAether.sendPacketToPlayer(new PacketTradeMessage("aether.trade.message.inventorywarn"), this.tradeModule.getTargetMP());
				error = true;
			}
			else if (!error && this.sentError)
			{
				this.tradeModule.sizeError(false);
				this.tradeModule.getTarget().getTradingModule().sizeError(false);
			}
		}

		this.sentError = error;
	}

	public void addItemToQueue(ItemStack itemStack)
	{
		for (int i = 0; i < 4; ++i)
		{
			for (int j = 0; j < 9; ++j)
			{
				ItemStack stack = this.playerInventory.getStackInSlot(i * 9 + j);

				if (stack.getItem().equals(itemStack.getItem()) && !this.containCheck[i][j])
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
		return playerIn.equals(this.playerInventory.player);
	}

	@Override
	public ItemStack transferStackInSlot(final EntityPlayer playerIn, final int index)
	{
		ItemStack itemstack = ItemStack.EMPTY;
		final Slot slot = this.inventorySlots.get(index);

		if (slot != null && slot.getHasStack() && !this.tradeModule.isLockedIn())
		{
			final ItemStack itemStack1 = slot.getStack();
			itemstack = itemStack1.copy();

			if (index < 36)
			{
				if (this.tradeModule.getTradeSlots() == 0 || !this.mergeItemStack(itemStack1, 36, (this.tradeModule.getTradeSlots() < 16 ? 36 + this.tradeModule.getTradeSlots() : 52), false))
				{
					return ItemStack.EMPTY;
				}

				slot.onSlotChange(itemStack1, itemstack);
			}
			else
			{
				if (!this.mergeItemStack(itemStack1, 0, 36, true))
				{
					return ItemStack.EMPTY;
				}

				slot.onSlotChange(itemStack1, itemstack);
			}

			if (itemStack1.isEmpty())
			{
				slot.putStack(ItemStack.EMPTY);
			}
			else
			{
				slot.onSlotChanged();
			}

			if (itemStack1.getCount() == itemstack.getCount())
			{
				return ItemStack.EMPTY;
			}

			slot.onTake(playerIn, itemStack1);

			this.sendInventory();
		}

		return itemstack;
	}

	class ConfirmationQueueItem
	{
		private ItemStack item;

		private boolean pickUp;

		private ConfirmationQueueItem(ItemStack item, boolean pickUp)
		{
			this.item = item;
			this.pickUp = pickUp;
		}

		public ItemStack getItem()
		{
			return this.item;
		}

		public boolean isPickUp()
		{
			return this.pickUp;
		}
	}
}
