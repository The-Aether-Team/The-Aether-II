package com.gildedgames.aether.common.containers;

import com.gildedgames.aether.common.containers.inventory.InventoryAccessories;
import com.gildedgames.aether.common.containers.slots.SlotAccessory;
import com.gildedgames.aether.common.items.accessories.AccessoryType;
import com.gildedgames.aether.common.items.accessories.ItemAccessory;
import com.gildedgames.aether.common.player.PlayerAether;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;

public class ContainerAccessories extends ContainerPlayer
{
	private final PlayerAether aePlayer;

	private final InventoryAccessories inventoryAccessories;

	private Slot binSlot;

	public ContainerAccessories(PlayerAether aePlayer)
	{
		super(aePlayer.getPlayer().inventory, false, aePlayer.getPlayer());

		this.aePlayer = aePlayer;
		this.inventoryAccessories = aePlayer.getInventoryAccessories();

		this.createSlots();
	}

	private void createSlots()
	{
		int widthOffset = 180;
		int heightOffset = -10;

		for (Slot slot : this.inventorySlots)
		{
			slot.xDisplayPosition += widthOffset;
			slot.yDisplayPosition += heightOffset;
		}

		this.binSlot = new Slot(this.aePlayer.getInventoryAccessories(), this.inventorySlots.size(), 228, 25);

		if (this.aePlayer.getPlayer().capabilities.isCreativeMode)
		{
			this.addSlotToContainer(this.binSlot);
		}

		int slotID = 0;

		for (int x = 0; x < 2; x++)
		{
			for (int y = 0; y < 4; y++)
			{
				int x1 = 23 + (x * 114);
				int y1 = 30 + (y * 23);

				AccessoryType type = InventoryAccessories.slotTypes[slotID];

				this.addSlotToContainer(new SlotAccessory(this.inventoryAccessories, type, slotID, x1, y1));
				slotID++;
			}
		}
	}

	@Override
	public ItemStack slotClick(int slotId, int clickedButton, int mode, EntityPlayer player)
	{
		if (slotId == this.binSlot.slotNumber && player.capabilities.isCreativeMode)
		{
			this.aePlayer.getPlayer().inventory.setItemStack(null);
		}

		return super.slotClick(slotId, clickedButton, mode, player);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotNumber)
	{
		Slot slot = this.inventorySlots.get(slotNumber);

		if (slotNumber == this.binSlot.slotNumber && this.aePlayer.getPlayer().capabilities.isCreativeMode)
		{
			this.aePlayer.getPlayer().inventory.clear();
			this.aePlayer.getInventoryAccessories().clear();
		}

		if (slot != null && slot.getHasStack())
		{
			ItemStack stack = slot.getStack();

			if (!(slot instanceof SlotAccessory) && !(slot instanceof SlotCrafting))
			{
				int destIndex = -1;

				if (stack.getItem() instanceof ItemAccessory)
				{
					ItemAccessory accessory = (ItemAccessory) stack.getItem();

					destIndex = 46 + this.inventoryAccessories.getNextEmptySlotForType(accessory.getType());
				}

				if (destIndex != -1)
				{
					Slot accessorySlot = this.inventorySlots.get(destIndex);
					accessorySlot.putStack(stack);

					slot.putStack(null);

					return stack;
				}
			}
			else if (slot instanceof SlotAccessory && stack.getItem() instanceof ItemAccessory)
			{
				((ItemAccessory) stack.getItem()).onAccessoryUnequipped(this.aePlayer, stack);
			}
		}

		return super.transferStackInSlot(player, slotNumber);
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn)
	{
		return true;
	}
}
