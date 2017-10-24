package com.gildedgames.orbis.common.containers;

import com.gildedgames.aether.api.player.IPlayerAether;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerGenericInventory extends ContainerPlayer
{
	/** See {@link net.minecraft.client.gui.inventory.GuiContainerCreative#basicInventory} **/
	private static final InventoryBasic dumbInventory = new InventoryBasic("tmp", true, 52);

	private final IPlayerAether aePlayer;

	private Slot binSlot;

	public ContainerGenericInventory(final IPlayerAether aePlayer)
	{
		super(aePlayer.getEntity().inventory, false, aePlayer.getEntity());

		this.aePlayer = aePlayer;

		this.createSlots();
	}

	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();

		for (int i = 0; i < this.inventorySlots.size(); ++i)
		{
			final ItemStack itemstack = this.inventorySlots.get(i).getStack();

			if (!itemstack.isEmpty())
			{
				itemstack.getItem().onUpdate(itemstack, this.aePlayer.getEntity().getEntityWorld(), this.aePlayer.getEntity(), i, false);
			}
		}
	}

	private void createSlots()
	{
		final int widthOffset = 180;
		final int heightOffset = -10;

		for (final Slot slot : this.inventorySlots)
		{
			slot.xPos += widthOffset;
			slot.yPos += heightOffset;
		}

		final Slot helmet = this.inventorySlots.get(5);
		final Slot chestplate = this.inventorySlots.get(6);
		final Slot leggings = this.inventorySlots.get(7);
		final Slot boots = this.inventorySlots.get(8);
		final Slot shield = this.inventorySlots.get(45);

		helmet.xPos = -2000;
		helmet.yPos = -2000;

		chestplate.xPos = -2000;
		chestplate.yPos = -2000;

		leggings.xPos = -2000;
		leggings.yPos = -2000;

		boots.xPos = -2000;
		boots.yPos = -2000;

		shield.xPos = -2000;
		shield.yPos = -2000;

		final Slot craftResult = this.inventorySlots.get(0);

		final Slot craft1 = this.inventorySlots.get(1);
		final Slot craft2 = this.inventorySlots.get(2);
		final Slot craft3 = this.inventorySlots.get(3);
		final Slot craft4 = this.inventorySlots.get(4);

		this.binSlot = new Slot(ContainerGenericInventory.dumbInventory, this.inventorySlots.size(), 213, 26);

		if (this.aePlayer.getEntity().capabilities.isCreativeMode)
		{
			this.addSlotToContainer(this.binSlot);

			craftResult.xPos -= 27;

			craft1.xPos -= 27;
			craft2.xPos -= 27;
			craft3.xPos -= 27;
			craft4.xPos -= 27;

			craftResult.yPos += 8;

			craft1.yPos += 8;
			craft2.yPos += 8;
			craft3.yPos += 8;
			craft4.yPos += 8;
		}
		else
		{
			craftResult.xPos -= 46;

			craft1.xPos -= 46;
			craft2.xPos -= 46;
			craft3.xPos -= 46;
			craft4.xPos -= 46;

			craftResult.yPos += 8;

			craft1.yPos += 8;
			craft2.yPos += 8;
			craft3.yPos += 8;
			craft4.yPos += 8;
		}
	}

	@Override
	public ItemStack slotClick(final int slotId, final int dragType, final ClickType clickTypeIn, final EntityPlayer player)
	{
		if (slotId == this.binSlot.slotNumber && player.capabilities.isCreativeMode)
		{
			this.aePlayer.getEntity().inventory.setItemStack(ItemStack.EMPTY);
		}

		return super.slotClick(slotId, dragType, clickTypeIn, player);
	}

	@Override
	public ItemStack transferStackInSlot(final EntityPlayer player, final int slotNumber)
	{
		if (slotNumber == this.binSlot.slotNumber && this.aePlayer.getEntity().capabilities.isCreativeMode)
		{
			this.aePlayer.getEntity().inventory.clear();
			this.aePlayer.getEquipmentModule().getInventory().clear();
		}

		return super.transferStackInSlot(player, slotNumber);
	}

	@Override
	public boolean canInteractWith(final EntityPlayer playerIn)
	{
		return true;
	}
}
