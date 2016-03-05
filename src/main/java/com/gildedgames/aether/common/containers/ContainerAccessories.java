package com.gildedgames.aether.common.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;

import com.gildedgames.aether.common.containers.inventory.InventoryAccessories;
import com.gildedgames.aether.common.containers.slots.SlotAccessory;
import com.gildedgames.aether.common.entities.effects.EntityEffect;
import com.gildedgames.aether.common.entities.effects.EntityEffects;
import com.gildedgames.aether.common.items.AccessoryType;
import com.gildedgames.aether.common.items.ItemAccessory;
import com.gildedgames.aether.common.player.PlayerAether;

public class ContainerAccessories extends ContainerPlayer
{
	// See GuiContainerCreative#field_147060_v
	private static InventoryBasic dumbInventory = new InventoryBasic("fake", true, 46);

	private final PlayerAether aePlayer;

	private final InventoryAccessories inventoryAccessories;

	private Slot binSlot;

	public ContainerAccessories(PlayerAether aePlayer)
	{
		super(aePlayer.getEntity().inventory, false, aePlayer.getEntity());

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

		this.binSlot = new Slot(ContainerAccessories.dumbInventory, this.inventorySlots.size(), 228, 25);

		if (this.aePlayer.getEntity().capabilities.isCreativeMode)
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
			this.aePlayer.getEntity().inventory.setItemStack(null);
		}
		
		if (slotId < this.inventorySlots.size() && slotId > 0)
		{
			Slot slot = this.inventorySlots.get(slotId);
			
			if (slot != null && slot.getHasStack())
			{
				ItemStack stack = slot.getStack();
				
				if (slot instanceof SlotAccessory && stack.getItem() instanceof ItemAccessory)
				{
					ItemAccessory acc = (ItemAccessory)stack.getItem();
					EntityEffects<EntityPlayer> effects = EntityEffects.get(this.aePlayer.getEntity());
					
					for (EntityEffect<EntityPlayer> effect : acc.getEffects())
					{
						if (effect.getAttributes().getInteger("modifier") >= 2)
						{
							effect.getAttributes().setInteger("modifier", effect.getAttributes().getInteger("modifier") - 1);
						}
						else
						{
							effects.removeEffect(effect);
						}
					}
				}
			}
		}

		return super.slotClick(slotId, clickedButton, mode, player);
	}

	private int getNextEmptySlot(AccessoryType type)
	{
		for (int i = 0; i < this.inventorySlots.size(); i++)
		{
			Slot slot = this.inventorySlots.get(i);

			if (slot.getStack() == null && slot instanceof SlotAccessory)
			{
				if (((SlotAccessory) slot).getType() == type)
				{
					return i;
				}
			}
		}

		return -1;
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotNumber)
	{
		Slot slot = this.inventorySlots.get(slotNumber);

		if (slotNumber == this.binSlot.slotNumber && this.aePlayer.getEntity().capabilities.isCreativeMode)
		{
			this.aePlayer.getEntity().inventory.clear();
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

					destIndex = this.getNextEmptySlot(accessory.getType());
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
				ItemAccessory acc = (ItemAccessory)stack.getItem();
				EntityEffects<EntityPlayer> effects = EntityEffects.get(this.aePlayer.getEntity());
				
				for (EntityEffect<EntityPlayer> effect : acc.getEffects())
				{
					if (effect.getAttributes().getInteger("modifier") >= 2)
					{
						effect.getAttributes().setInteger("modifier", effect.getAttributes().getInteger("modifier") - 1);
					}
					else
					{
						effects.removeEffect(effect);
					}
				}
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
