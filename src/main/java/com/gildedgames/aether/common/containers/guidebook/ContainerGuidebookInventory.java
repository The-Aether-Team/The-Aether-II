package com.gildedgames.aether.common.containers.guidebook;

import com.gildedgames.aether.api.AetherAPI;
import com.gildedgames.aether.api.items.equipment.ItemEquipmentSlot;
import com.gildedgames.aether.api.items.properties.IItemProperties;
import com.gildedgames.aether.api.player.IPlayerAether;
import com.gildedgames.aether.api.player.inventory.IInventoryEquipment;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerEquipmentModule;
import com.gildedgames.aether.common.containers.slots.SlotEquipment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;

public class ContainerGuidebookInventory extends ContainerPlayer
{
	private static final InventoryBasic dumbInventory = new InventoryBasic("tmp", true, 52);

	private final IPlayerAether aePlayer;

	private final IInventoryEquipment inventoryEquipment;


	private Slot binSlot;

	public ContainerGuidebookInventory(IPlayerAether aePlayer)
	{
		super(aePlayer.getEntity().inventory, false, aePlayer.getEntity());

		this.aePlayer = aePlayer;
		this.inventoryEquipment = aePlayer.getModule(PlayerEquipmentModule.class).getInventory();

		this.createSlots();
	}

	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();

		for (int i = 0; i < this.inventorySlots.size(); ++i)
		{
			ItemStack itemstack = this.inventorySlots.get(i).getStack();

			if (!itemstack.isEmpty())
			{
				itemstack.getItem().onUpdate(itemstack, this.aePlayer.getEntity().getEntityWorld(), this.aePlayer.getEntity(), i, false);
			}
		}
	}

	private void createSlots()
	{
		int widthOffset = 180 - 16;
		int heightOffset = -15;

		for (Slot slot : this.inventorySlots)
		{
			slot.xPos += widthOffset;
			slot.yPos += heightOffset;
		}

		Slot helmet = this.inventorySlots.get(5);
		Slot chestplate = this.inventorySlots.get(6);
		Slot leggings = this.inventorySlots.get(7);
		Slot boots = this.inventorySlots.get(8);
		Slot shield = this.inventorySlots.get(45);

		shield.xPos = 49;
		shield.yPos = 57;

		helmet.xPos = 31;
		helmet.yPos = 3;

		chestplate.xPos = 31;
		chestplate.yPos = 21;

		leggings.xPos = 31;
		leggings.yPos = 39;

		boots.xPos = 31;
		boots.yPos = 57;

		Slot craftResult = this.inventorySlots.get(0);

		Slot craft1 = this.inventorySlots.get(1);
		Slot craft2 = this.inventorySlots.get(2);
		Slot craft3 = this.inventorySlots.get(3);
		Slot craft4 = this.inventorySlots.get(4);

		this.binSlot = new Slot(ContainerGuidebookInventory.dumbInventory, this.inventorySlots.size(), 197, 27);

		if (this.aePlayer.getEntity().capabilities.isCreativeMode)
		{
			this.addSlotToContainer(this.binSlot);

			craftResult.xPos -= 27;

			craft1.xPos -= 27;
			craft2.xPos -= 27;
			craft3.xPos -= 27;
			craft4.xPos -= 27;

			craftResult.yPos += 14;

			craft1.yPos += 14;
			craft2.yPos += 14;
			craft3.yPos += 14;
			craft4.yPos += 14;
		}
		else
		{
			craftResult.xPos -= 46;

			craft1.xPos -= 46;
			craft2.xPos -= 46;
			craft3.xPos -= 46;
			craft4.xPos -= 46;

			craftResult.yPos += 14;

			craft1.yPos += 14;
			craft2.yPos += 14;
			craft3.yPos += 14;
			craft4.yPos += 14;
		}

		int inventorySlotId = 0;

		this.addSlotToContainer(new SlotEquipment(this.inventoryEquipment, ItemEquipmentSlot.RELIC, inventorySlotId++,
				13, 3));
		this.addSlotToContainer(new SlotEquipment(this.inventoryEquipment, ItemEquipmentSlot.RELIC, inventorySlotId++,
				49, 3));

		this.addSlotToContainer(new SlotEquipment(this.inventoryEquipment, ItemEquipmentSlot.HANDWEAR, inventorySlotId++,
				49, 21));

		this.addSlotToContainer(new SlotEquipment(this.inventoryEquipment, ItemEquipmentSlot.RING, inventorySlotId++,
				13, 39));
		this.addSlotToContainer(new SlotEquipment(this.inventoryEquipment, ItemEquipmentSlot.RING, inventorySlotId++,
				13, 57));

		this.addSlotToContainer(new SlotEquipment(this.inventoryEquipment, ItemEquipmentSlot.NECKWEAR, inventorySlotId++,
				13, 21));

		this.addSlotToContainer(new SlotEquipment(this.inventoryEquipment, ItemEquipmentSlot.COMPANION, inventorySlotId++,
				49, 39));

		for (int x = 0; x < 6; x++)
		{
			int x1 = 24 + (x * 18);
			int y1 = 127;

			this.addSlotToContainer(new SlotEquipment(this.inventoryEquipment, ItemEquipmentSlot.CHARM, inventorySlotId, x1, y1));
			inventorySlotId++;
		}
	}

	@Override
	public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, EntityPlayer player)
	{
		if (slotId == this.binSlot.slotNumber && player.capabilities.isCreativeMode)
		{
			this.aePlayer.getEntity().inventory.setItemStack(ItemStack.EMPTY);
		}

		return super.slotClick(slotId, dragType, clickTypeIn, player);
	}

	private int getNextEmptySlot(ItemEquipmentSlot type)
	{
		for (int i = 0; i < this.inventorySlots.size(); i++)
		{
			Slot slot = this.inventorySlots.get(i);

			if (slot.getStack().isEmpty() && slot instanceof SlotEquipment)
			{
				if (((SlotEquipment) slot).getEquipmentType() == type)
				{
					return i;
				}
			}

			if (type == ItemEquipmentSlot.OFFHAND && slot.getStack().isEmpty() && slot.getSlotIndex() == 40)
			{
				return i;
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

			this.inventoryEquipment.clear();
		}

		if (slot != null && slot.getHasStack())
		{
			ItemStack stack = slot.getStack();

			if (!(slot instanceof SlotEquipment) && !(slot instanceof SlotCrafting))
			{
				int destIndex = -1;

				IItemProperties properties = AetherAPI.content().items().getProperties(stack.getItem());

				if (properties.getEquipmentSlot() != ItemEquipmentSlot.NONE)
				{
					destIndex = this.getNextEmptySlot(properties.getEquipmentSlot());
				}

				if (destIndex >= 0)
				{
					Slot accessorySlot = this.inventorySlots.get(destIndex);
					accessorySlot.putStack(stack);

					slot.putStack(ItemStack.EMPTY);

					return stack;
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
