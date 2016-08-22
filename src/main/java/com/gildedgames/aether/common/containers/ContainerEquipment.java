package com.gildedgames.aether.common.containers;

import com.gildedgames.aether.api.capabilites.AetherCapabilities;
import com.gildedgames.aether.api.capabilites.items.properties.IItemPropertiesCapability;
import com.gildedgames.aether.api.capabilites.items.properties.ItemEquipmentType;
import com.gildedgames.aether.api.player.IPlayerAetherCapability;
import com.gildedgames.aether.api.player.inventory.IInventoryEquipment;
import com.gildedgames.aether.common.containers.slots.SlotEquipment;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;

public class ContainerEquipment extends ContainerPlayer
{
	/** See {@link GuiContainerCreative#basicInventory} **/
	private static InventoryBasic dumbInventory = new InventoryBasic("fake", true, 52);

	private final IPlayerAetherCapability aePlayer;

	private final IInventoryEquipment inventoryEquipment;

	private Slot binSlot;

	public ContainerEquipment(IPlayerAetherCapability aePlayer)
	{
		super(aePlayer.getPlayer().inventory, false, aePlayer.getPlayer());

		this.aePlayer = aePlayer;
		this.inventoryEquipment = aePlayer.getEquipmentInventory();

		this.createSlots();
	}

	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();

		for (int i = 0; i < this.inventorySlots.size(); ++i)
		{
			ItemStack itemstack = this.inventorySlots.get(i).getStack();

			if (itemstack != null)
			{
				itemstack.getItem().onUpdate(itemstack, this.aePlayer.getPlayer().getEntityWorld(), this.aePlayer.getPlayer(), i, false);
			}
		}
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

		Slot helmet = this.inventorySlots.get(5);
		Slot chestplate = this.inventorySlots.get(6);
		Slot leggings = this.inventorySlots.get(7);
		Slot boots = this.inventorySlots.get(8);
		Slot shield = this.inventorySlots.get(45);

		int offsetX = -1;
		int offsetY = -25;

		shield.xDisplayPosition = 64 + offsetX;
		shield.yDisplayPosition = 91 + offsetY;

		helmet.xDisplayPosition = 39 + offsetX;
		helmet.yDisplayPosition = 28 + offsetY;

		chestplate.xDisplayPosition = 39 + offsetX;
		chestplate.yDisplayPosition = 49 + offsetY;

		leggings.xDisplayPosition = 39 + offsetX;
		leggings.yDisplayPosition = 70 + offsetY;

		boots.xDisplayPosition = 39 + offsetX;
		boots.yDisplayPosition = 91 + offsetY;

		Slot craftResult = this.inventorySlots.get(0);

		Slot craft1 = this.inventorySlots.get(1);
		Slot craft2 = this.inventorySlots.get(2);
		Slot craft3 = this.inventorySlots.get(3);
		Slot craft4 = this.inventorySlots.get(4);

		this.binSlot = new Slot(ContainerEquipment.dumbInventory, this.inventorySlots.size(), 213, 26);

		if (this.aePlayer.getPlayer().capabilities.isCreativeMode)
		{
			this.addSlotToContainer(this.binSlot);

			craftResult.xDisplayPosition -= 27;

			craft1.xDisplayPosition -= 27;
			craft2.xDisplayPosition -= 27;
			craft3.xDisplayPosition -= 27;
			craft4.xDisplayPosition -= 27;

			craftResult.yDisplayPosition += 8;

			craft1.yDisplayPosition += 8;
			craft2.yDisplayPosition += 8;
			craft3.yDisplayPosition += 8;
			craft4.yDisplayPosition += 8;
		}
		else
		{
			craftResult.xDisplayPosition -= 46;

			craft1.xDisplayPosition -= 46;
			craft2.xDisplayPosition -= 46;
			craft3.xDisplayPosition -= 46;
			craft4.xDisplayPosition -= 46;

			craftResult.yDisplayPosition += 8;

			craft1.yDisplayPosition += 8;
			craft2.yDisplayPosition += 8;
			craft3.yDisplayPosition += 8;
			craft4.yDisplayPosition += 8;
		}

		int inventorySlotId = 0;

		this.addSlotToContainer(new SlotEquipment(this.inventoryEquipment, ItemEquipmentType.RELIC, inventorySlotId++, 14 + offsetX, 28 + offsetY));
		this.addSlotToContainer(new SlotEquipment(this.inventoryEquipment, ItemEquipmentType.RELIC, inventorySlotId++, 64 + offsetX, 28 + offsetY));

		this.addSlotToContainer(new SlotEquipment(this.inventoryEquipment, ItemEquipmentType.HANDWEAR, inventorySlotId++, 64 + offsetX, 49 + offsetY));

		this.addSlotToContainer(new SlotEquipment(this.inventoryEquipment, ItemEquipmentType.RING, inventorySlotId++, 14 + offsetX, 70 + offsetY));
		this.addSlotToContainer(new SlotEquipment(this.inventoryEquipment, ItemEquipmentType.RING, inventorySlotId++, 14 + offsetX, 91 + offsetY));
		this.addSlotToContainer(new SlotEquipment(this.inventoryEquipment, ItemEquipmentType.NECKWEAR, inventorySlotId++, 14 + offsetX, 49 + offsetY));

		this.addSlotToContainer(new SlotEquipment(this.inventoryEquipment, ItemEquipmentType.COMPANION, inventorySlotId++, 64 + offsetX, 70 + offsetY));
		this.addSlotToContainer(new SlotEquipment(this.inventoryEquipment, ItemEquipmentType.ARTIFACT, inventorySlotId++, 39 + offsetX, 122 + offsetY));

		for (int x = 0; x < 6; x++)
		{
			int x1 = 35 + (x * 18);
			int y1 = 128;

			this.addSlotToContainer(new SlotEquipment(this.inventoryEquipment, ItemEquipmentType.CHARM, inventorySlotId, x1, y1));
			inventorySlotId++;
		}
	}

	@Override
	public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, EntityPlayer player)
	{
		if (slotId == this.binSlot.slotNumber && player.capabilities.isCreativeMode)
		{
			this.aePlayer.getPlayer().inventory.setItemStack(null);
		}

		return super.slotClick(slotId, dragType, clickTypeIn, player);
	}

	private int getNextEmptySlot(ItemEquipmentType type)
	{
		for (int i = 0; i < this.inventorySlots.size(); i++)
		{
			Slot slot = this.inventorySlots.get(i);

			if (slot.getStack() == null && slot instanceof SlotEquipment)
			{
				if (((SlotEquipment) slot).getEquipmentType() == type)
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

		if (slotNumber == this.binSlot.slotNumber && this.aePlayer.getPlayer().capabilities.isCreativeMode)
		{
			this.aePlayer.getPlayer().inventory.clear();
			this.aePlayer.getEquipmentInventory().clear();
		}

		if (slot != null && slot.getHasStack())
		{
			ItemStack stack = slot.getStack();

			if (!(slot instanceof SlotEquipment) && !(slot instanceof SlotCrafting))
			{
				int destIndex = -1;

				if (stack.hasCapability(AetherCapabilities.ITEM_PROPERTIES, null))
				{
					IItemPropertiesCapability properties = stack.getCapability(AetherCapabilities.ITEM_PROPERTIES, null);

					destIndex = this.getNextEmptySlot(properties.getEquipmentType());
				}

				if (destIndex != -1)
				{
					Slot accessorySlot = this.inventorySlots.get(destIndex);
					accessorySlot.putStack(stack);

					slot.putStack(null);

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
